package com.hike.digitalgymnastic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hike.digitalgymnastic.adapter.MessageAdapter;
import com.hike.digitalgymnastic.entitiy.Message;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huwei on 16/3/25.
 * 日记消息页面
 */


@ContentView(R.layout.activity_diarymessage)
public class DiaryMessageActivity extends BaseActivity implements AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {
    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;
    @ViewInject(R.id.ll_blank)
    LinearLayout ll_blank;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.listView)
    PullToRefreshListView listView;
//    @ViewInject(R.id.tv_hint)
//    TextView tv_hint;

    private View v;
    private BaseDao dao;
    private MessageAdapter adapter;

    @OnClick(value = {R.id.btn_left, R.id.ll_btn_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
            case R.id.ll_btn_left:
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == 95) {
            //查询社区关注动态，点赞动态返回的消息
            showProgress(this, false);
            listView.onRefreshComplete();
            List<Message> messageList = dao.getMscontext().getMessageList();
            buildView(messageList);
        }
    }


    List<Message> totallist = new ArrayList<Message>();

    private void buildView(List<Message> messageList) {
        // 下拉刷新时执行到此刷新列表
        if (isDownFresh) {
            totallist.clear();
            startIndex = 0;
        }
        if (messageList != null) {
            if (messageList.size() < pageSize)
                listView.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
            else
                listView.setMode(PullToRefreshBase.Mode.BOTH);
            totallist.addAll(messageList);
            if (messageList.size() > 0) {
                listView.setVisibility(View.VISIBLE);
                if (adapter == null) {
                    adapter = new MessageAdapter(totallist, this,
                            bitmapUtils, dao);
                    listView.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            } else {
                listView.setVisibility(View.GONE);
                ll_blank.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
    }

    private void init() {
        title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
        btn_left.setImageResource(R.mipmap.back_login_3x);
        dao = new BaseDao(this, this);
        title.setText(getString(R.string.menu_message_str));
        initListView();
        initBitmapUtils();

        getMessage(startIndex, pageSize);
    }

    BitmapUtils bitmapUtils;

    private void initBitmapUtils() {
        String path = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + getString(R.string.app_name) + "/cache";
        bitmapUtils = new BitmapUtils(this, path);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.boy_head);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(this));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);

    }


    private void initListView() {
        // 得到加载刷新的布局，,可以设置全局的
        ILoadingLayout loadingLayoutProxy = listView.getLoadingLayoutProxy(
                true, false);
        // 设置释放时的文字提示
        loadingLayoutProxy.setReleaseLabel("松开刷新数据");
        // 设置下拉时的文字提示
        loadingLayoutProxy.setPullLabel("下拉刷新");
        // 设置最后一次更新的时间
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String time = sdf.format(new Date());
        loadingLayoutProxy.setLastUpdatedLabel("更新时间:" + time);
        // 设置刷新中的文字提示
        loadingLayoutProxy.setRefreshingLabel("正在刷新....");
        // 设置listView的模式为既能上啦加载数据和下拉加载数据
        listView.setOnRefreshListener(this);
        listView.setOnItemClickListener(this);
    }


    int startIndex = 0;
    int pageSize = 10;

    int unread;

    private void getMessage(int startIndex, int pageSize) {
        showProgress(DiaryMessageActivity.this, true);
        dao.queryMessage(startIndex, pageSize);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HeaderViewListAdapter adapter = (HeaderViewListAdapter) parent
                .getAdapter();// 此处需要转成HeaderViewListAdapter，会报类型转换异常
        MessageAdapter msadapter = (MessageAdapter) adapter
                .getWrappedAdapter();
        Message message = (Message) msadapter
                .getItem(position - 1);
        long diaryId = message.getDiaryId();
        Intent intent = new Intent(this, VenueDiaryDetailActivity.class);
        intent.putExtra(Constants.diaryId, diaryId);
        intent.putExtra(Constants.customerId, Long.parseLong(customer.getId()));
        startActivity(intent);
    }

    boolean isDownFresh = false;

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isDownFresh = true;
        getMessage(0, pageSize);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        startIndex += pageSize;
        isDownFresh = false;
        getMessage(startIndex, pageSize);
    }
}
