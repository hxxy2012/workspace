package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.HeaderViewListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.hike.digitalgymnastic.DiaryMessageActivity;
import com.hike.digitalgymnastic.DiaryPublishActivity;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.VenueDiaryDetailActivity;
import com.hike.digitalgymnastic.adapter.VenueCircleAdapter;
import com.hike.digitalgymnastic.entitiy.MessageCount;
import com.hike.digitalgymnastic.entitiy.QueryVenueDiary;
import com.hike.digitalgymnastic.entitiy.VenueDiary;
import com.hike.digitalgymnastic.entitiy.VenueList;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.ImageUtil;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by huwei on 16/3/23.
 * 圈子
 */
public class FragmentCircle extends BaseFragment implements View.OnClickListener
        , PullToRefreshBase.OnRefreshListener2<ListView>, AdapterView.OnItemClickListener, ReqeustCode {
    @ViewInject(R.id.rl_unread)
    RelativeLayout rl_unread;
    @ViewInject(R.id.tv_mscount)
    TextView tv_mscount;
    @ViewInject(R.id.tv_nomoredata)
    TextView tv_nomoredata;
    @ViewInject(R.id.ll_top)
    LinearLayout ll_top;
    @ViewInject(R.id.ll_btn_left)
    LinearLayout ll_btn_left;
    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.tv_changguan)
    TextView tv_changguan;
    @ViewInject(R.id.ll_btn_right)
    LinearLayout ll_btn_right;
    @ViewInject(R.id.btn_right)
    ImageView btn_right;
    @ViewInject(R.id.ib_backtotop)
    ImageButton ib_backtotop;
    @ViewInject(R.id.lv_venue)
    PullToRefreshListView lv_venue;
    VenueCircleAdapter adapter;
    BaseDao dao;
    static FragmentCircle mCircle;
    int unread;

    public static FragmentCircle newInstance(int unread) {
        if (mCircle == null) {
            mCircle = new FragmentCircle();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.unread, unread);
            mCircle.setArguments(bundle);
        }
        return mCircle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View _view = inflater.inflate(R.layout.fragment_circle, container, false);
        ViewUtils.inject(this, _view);
        return _view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initTopBar();
        initBitmapUtils();
        initListView();
        buildViewFromLocalDb();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!this.isHidden()) {
            dao.GetUnreadMessageCount();
        }
    }

    private void buildViewFromLocalDb() {

        if (getArguments() != null) {
            unread = getArguments().getInt(Constants.unread);
            if (unread == 0) {
                rl_unread.setVisibility(View.GONE);
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).checkUnread();
                }
            } else
                tv_mscount.setText(unread + "条新消息");
        }
//        showProgress(true);
//        DBManager dbManager = DBManager.getIntance(activity, customer.getId());
//        List<VenueDiary> diaryList = dbManager.getAllObject(VenueDiary.class);
//        Message msg = new Message();
//        msg.obj = diaryList;
//        handler.sendMessage(msg);


        getData();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showProgress(false);

            List<VenueDiary> list = (List<VenueDiary>) msg.obj;
            if (list != null && list.size() > 0) {
                buildView(list);
            }
        }
    };

    List<VenueDiary> totallist = new ArrayList<VenueDiary>();
    int startIndex = 0;
    int pageSize = 10;


    private void getData() {
        showProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dao.GetVenueList();
            }
        }, 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        dao.GetUnreadMessageCount();
        if (application.isNeedUpdated) {
            application.isNeedUpdated = false;
            getData();
        }
    }

    int id = 3;

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if (requestCode == 94) {
            //获取消息的未读数量
            MessageCount msCount = dao.getMsCount();
            unread = msCount.getMessageCount();
            rl_unread.setVisibility(unread > 0 ? View.VISIBLE : View.GONE);
            if (unread > 0) {
                if (activity instanceof MainActivity) {
                    ((MainActivity) activity).checkUnread();
                }
            }
        } else if (requestCode == 81) {
            VenueList res = dao.getVenueList();
            //获取场管圈列表
            if (res.getVenueList() != null && res.getVenueList().size() > 0) {
                id = res.getVenueList().get(0).getId();
                String name = res.getVenueList().get(0).getName();
                tv_changguan.setText(name);
                dao.queryVenueDiary(startIndex, pageSize, id);
            }
        } else if (requestCode == 80) {
            //查询场馆圈的日记
            lv_venue.onRefreshComplete();
            QueryVenueDiary res = dao.getQueryVenueDiary();
            List<VenueDiary> diaryList = res.getDiaryList();
            buildView(diaryList);
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        super.onRequestFaild(errorNo, errorMessage);
        lv_venue.onRefreshComplete();
//        Utils.showMessage(activity, "没有数据");
    }

    boolean isDownFresh = false;

    private void buildView(List<VenueDiary> diaryList) {
        if (isDownFresh) {
            totallist.clear();
            startIndex = 0;
        }
        if (diaryList != null) {
            if (diaryList.size() < pageSize) {
                lv_venue.setMode(PullToRefreshBase.Mode.PULL_DOWN_TO_REFRESH);
            } else {
                lv_venue.setMode(PullToRefreshBase.Mode.BOTH);
            }
            totallist.addAll(diaryList);
            adapter = new VenueCircleAdapter(totallist,
                    activity, bitmapUtils, dao);
            lv_venue.setAdapter(adapter);
            if (adapter != null) {
                adapter.notifyDataSetChanged();
            }
        }
//        if (adapter == null) {
//            adapter = new VenueCircleAdapter(totallist,
//                    activity, bitmapUtils, dao);
//            lv_venue.setAdapter(adapter);
//        } else {
//            adapter.notifyDataSetChanged();
//        }

    }

    private void initTopBar() {
        ll_top.setBackgroundResource(R.color.white);
        ll_btn_left.setVisibility(View.GONE);
        ll_btn_left.setClickable(false);
        title.setText("场馆圈");
        title.setTextColor(getResources().getColor(R.color.black));
        btn_right.setVisibility(View.VISIBLE);
        btn_right.setImageResource(R.mipmap.camera_venue);
        dao = new BaseDao(this, activity);
    }

    private void initListView() {
        ILoadingLayout loadingLayoutProxy = lv_venue.getLoadingLayoutProxy(
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
        // 设置lv_personalDaily的模式为既能上啦加载数据和下拉加载数据
        lv_venue.setOnRefreshListener(this);
        lv_venue.setOnItemClickListener(this);
    }

    File captureFile;

    @OnClick(value = {R.id.rl_unread, R.id.tv_count, R.id.ll_btn_left, R.id.ib_backtotop, R.id.ll_btn_right, R.id.btn_left, R.id.btn_right})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
            case R.id.ll_btn_right:
                //跳转到拍照，需要自定义相机
                showImagePicker();
//                Intent it = new Intent(activity, PhotoAlbumActivity.class);
//                startActivityForResult(it, Constants.requestCode_pickpicture);
//                activity.overridePendingTransition(R.anim.push_up, R.anim.push_down);
                break;
            case R.id.ib_backtotop:
                lv_venue.getRefreshableView().setSelection(0);
                break;
            case R.id.rl_unread:
            case R.id.tv_count:
                Intent intent = new Intent(activity, DiaryMessageActivity.class);
                intent.putExtra(Constants.unread, unread);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    BitmapUtils bitmapUtils;

    private void initBitmapUtils() {
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/image";
        bitmapUtils = new BitmapUtils(activity, imageCachePath);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.boy_head);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(activity));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HeaderViewListAdapter adapter = (HeaderViewListAdapter) parent
                .getAdapter();// 此处需要转成HeaderViewListAdapter，会报类型转换异常
        VenueCircleAdapter venueCircleAdapter = (VenueCircleAdapter) adapter
                .getWrappedAdapter();
        venueDiary = (VenueDiary) venueCircleAdapter
                .getItem(position - 1);
        long diaryId = venueDiary.getDiaryId();
        Intent intent = new Intent(getActivity(), VenueDiaryDetailActivity.class);
        intent.putExtra(Constants.diaryId, diaryId);
        intent.putExtra(Constants.customerId, venueDiary.getCustomerId());
        startActivityForResult(intent, Constants.diaryDetail);
    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        isDownFresh = true;
        startIndex = 0;
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        startIndex += pageSize;
        isDownFresh = false;
        getData();
    }

    private VenueDiary venueDiary;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.diaryDetail) {
                int count_Customer = data.getIntExtra("count_Customer", 0);
                int count_Comment = data.getIntExtra("count_Comment", 0);
                venueDiary.setCommentCount(count_Comment);
                venueDiary.setPraiseCount(count_Customer);
                venueDiary.setPraised(data.getBooleanExtra("isprised",
                        false));
                adapter.notifyDataSetChanged();
            } else if (requestCode == FROM_CAPTURE) {
                onCaptureComplete(captureFile);
            } else if (requestCode == FROM_GALLERY) {
                if (data != null) {
                    String path = PhotoPicker.getPhotoPathByLocalUri(activity, data);
                    onGalleryComplete(path);
                }
            }
        }

    }

    protected void onGalleryComplete(String filepath) {
        String path = ImageUtil.getTargetImage(activity, filepath, null, 1024, false, 0);
        startIntent(path, DiaryPublishActivity.class);
    }

    protected void onCaptureComplete(File captureFile) {
        String path = ImageUtil.getTargetImage(activity, captureFile.getPath(), null, 1024, false, 0);
        startIntent(path, DiaryPublishActivity.class);
    }

    public void startIntent(String path, Class class1) {
        Intent intent = new Intent(activity, class1);
        intent.putExtra("imagePath", path);
        startActivity(intent);
    }

    /**
     * @category 头像选择方式菜单
     */
    private void showImagePicker() {
        // 拍照/从相册选择
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = activity.getLayoutInflater().inflate(R.layout.pop_image_picker,
                null);
        final RelativeLayout rl_galary = (RelativeLayout) view
                .findViewById(R.id.rl_galary);
        final RelativeLayout rl_camera = (RelativeLayout) view
                .findViewById(R.id.rl_camera);
        final RelativeLayout rl_cancel = (RelativeLayout) view
                .findViewById(R.id.rl_cancel);
        rl_cancel.setVisibility(View.VISIBLE);

        // 相ji添加点击事件
        rl_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    captureFile = FileUtil.getCaptureFile(activity);
                    PhotoPicker.launchCamera(activity, FROM_CAPTURE, captureFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showMessage(activity, "无法使用拍照功能");
                }
            }
        });
        // 相ce添加点击事件
        rl_galary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    PhotoPicker.launchGallery(activity, FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    Utils.showMessage(activity, "无法查看图片浏览器");
                }
            }
        });

        rl_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

}
