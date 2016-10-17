package com.hike.digitalgymnastic;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hike.digitalgymnastic.adapter.AdapterItemClickListener;
import com.hike.digitalgymnastic.adapter.PinglunAdapter;
import com.hike.digitalgymnastic.entitiy.DiaryComment;
import com.hike.digitalgymnastic.entitiy.DiaryDetail;
import com.hike.digitalgymnastic.entitiy.PraiseCustomer;
import com.hike.digitalgymnastic.entitiy.VenueDiary;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hike.digitalgymnastic.view.KeyboardLayout;
import com.hike.digitalgymnastic.view.ZanButton;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.bitmap.callback.DefaultBitmapLoadCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//关注日记详情页
@ContentView(R.layout.activity_venue_diary_detail)
public class VenueDiaryDetailActivity extends BaseActivity implements
        OnClickListener, AdapterItemClickListener, OnItemClickListener {
    @ViewInject(R.id.root)
    KeyboardLayout root;

    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    // title栏
    @ViewInject(R.id.view_one)
    View view_one;
    @ViewInject(R.id.view_two)
    View view_two;
    @ViewInject(R.id.ll_top)
    LinearLayout ll_top;
    @ViewInject(R.id.ll_zantop)
    LinearLayout ll_zantop;
    @ViewInject(R.id.ll_btn_left)
    LinearLayout ll_btn_left;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.ll_btn_right)
    RelativeLayout ll_btn_right;
    @ViewInject(R.id.btn_right)
    ImageView btn_right;

    // 顶部个人头像和名称
    @ViewInject(R.id.iv_owner_picture)
    ImageView iv_owner_picture;
    @ViewInject(R.id.tv_owner_name)
    TextView tv_owner_name;

    // 日记图片
    @ViewInject(R.id.iv_diarypic)
    ImageView iv_diarypic;
    // 日记相关描述信息
    @ViewInject(R.id.tv_time)
    TextView tv_time;
    @ViewInject(R.id.tv_description)
    TextView tv_description;
    // 点赞人数视图
    @ViewInject(R.id.btn_ishaszan)
    ZanButton btn_ishaszan;
    //     @ViewInject(R.id.tv_zan_count)
//     TextView tv_zan_count;
    @ViewInject(R.id.ll_zan)
    LinearLayout ll_zan;

    // 底部的回复评论信息
    // @ViewInject(R.id.tv_pinglun_count)
    // TextView tv_pinglun_count;
    @ViewInject(R.id.listView)
    ListView listView;

    // 发送评论部分
    @ViewInject(R.id.btn_send)
    Button btn_send;
    @ViewInject(R.id.et_pinglun)
    EditText et_pinglun;

    // 阴影部分

    @ViewInject(R.id.ll_item_bottom)
    LinearLayout ll_item_bottom;

    private DiaryComment diaryComment;
    @ViewInject(R.id.tv_zan)
    ImageView rb_zan;
    @ViewInject(R.id.tv_comment)
    ImageView rb_comment;
    @ViewInject(R.id.tv_kuohao)
    TextView tv_kuohao;
    @ViewInject(R.id.count_comment)
    TextView count_comment;
    @ViewInject(R.id.count_zan)
    TextView count_zan;
    @ViewInject(R.id.tv_pinglun)
    TextView tv_pinglun;
    @ViewInject(R.id.rl_two)
    RelativeLayout rl_two;

    @OnClick(value = {R.id.iv_owner_picture, R.id.ll_btn_left,
            R.id.ll_btn_right, R.id.iv_diarypic, R.id.btn_ishaszan,
            R.id.btn_send, R.id.tv_zan, R.id.tv_comment, R.id.tv_pinglun,
            R.id.tv_owner_name, R.id.btn_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_owner_picture:
                // 点击详情页面的头像
                jumpToPersonalDiaryList(diaryDetail.getCustomerId(),
                        diaryDetail.getName(), diaryDetail.getAvatar());
                break;
            case R.id.tv_owner_name:
                jumpToPersonalDiaryList(diaryDetail.getCustomerId(),
                        diaryDetail.getName(), diaryDetail.getAvatar());
                break;
            case R.id.ll_btn_left:
            case R.id.btn_left:
                hintKbTwo();
                close();
                break;
            case R.id.ll_btn_right:
                showTopMenu();
                break;
            case R.id.iv_diarypic:
                jump2ImagePage();
                break;

            case R.id.btn_ishaszan:
                if (checkZanInfo()) {
                    ll_zan.setVisibility(View.VISIBLE);
                    praiseDiary(diaryId);
                } else {
                    unpraiseDiary(diaryId);
                }

            case R.id.btn_send:
                String content = et_pinglun.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    if (et_pinglun.getTag() != null) {
                        diaryComment = (DiaryComment) et_pinglun.getTag();
                        commentDiary(diaryId, diaryComment.getCustomerId(), content);
                    } else {
                        commentDiary(diaryId, null, content);
                    }
                }

                break;

            case R.id.tv_pinglun:
                // 点击评论触发的事件
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager m = (InputMethodManager) tv_pinglun
                                .getContext().getSystemService(
                                        Context.INPUT_METHOD_SERVICE);
                        m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 300);
                break;
            default:
                break;
        }
    }

    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initKeyBoard();
    }

    private void initKeyBoard() {
        et_pinglun.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    String content = et_pinglun.getText().toString();
                    if (!TextUtils.isEmpty(content)) {
                        if (et_pinglun.getTag() != null) {
                            diaryComment = (DiaryComment) et_pinglun.getTag();
                            commentDiary(diaryId, diaryComment.getCustomerId(),
                                    content);
                        } else {
                            commentDiary(diaryId, null, content);
                        }
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.customerName, customerName);
        outState.putLong(Constants.diaryId, diaryId);
        outState.putLong(Constants.customerId, customerId);
    }


    VenueDiary venueDiary;
    BaseDao dao;
    BitmapUtils bitmapUtils;
    long customerId;
    long diaryId;
    List<PraiseCustomer> praiseCustomerList;
    private DiaryDetail diaryDetail;
    String customerName;

    private void init(Bundle savedInstanceState) {
        dao = new BaseDao(this, this);
        btn_left.setBackgroundResource(R.mipmap.back_login_3x);
        title.setText("详情");
        title.setTextColor(getResources().getColor(R.color.picker_tophint));
        btn_right.setBackgroundResource(R.mipmap.btn_deletediary);
        btn_right.setVisibility(View.VISIBLE);
        addListener();
        initBitmapUtils();

        if (savedInstanceState != null) {
            diaryId = savedInstanceState.getLong(Constants.diaryId);
            customerId = savedInstanceState.getLong(Constants.customerId);
            customerName = savedInstanceState.getString(Constants.customerName);
        } else {
            diaryId = getIntent().getLongExtra(Constants.diaryId, 0);
            customerId = getIntent().getLongExtra(Constants.customerId, 0);
            customerName = getIntent().getStringExtra(Constants.customerName);
        }
        if (customer != null && customerId != Long.parseLong(customer.getId()))
            isDiaryDeletable(true);
        else
            isDiaryDeletable(false);
        showProgress(this, true);
        getDiaryDetail(diaryId);
    }

    private void isDiaryDeletable(boolean canDelete) {
        btn_right.setVisibility(canDelete ? View.GONE : View.VISIBLE);
    }

    /**
     * @param diaryId
     * @category 获取日记详情
     */
    private void getDiaryDetail(long diaryId) {
        dao.getDiaryDetail(null, diaryId);
    }

    /**
     * @param diaryId
     * @category 点赞
     */
    private void praiseDiary(long diaryId) {
        dao.praiseDiary(null, diaryId);
    }

    /**
     * @param diaryId
     * @category 取消点赞
     */
    private void unpraiseDiary(long diaryId) {
        dao.unpraiseDiary(null, diaryId);
    }

    /**
     * @param diaryId
     * @param replyId
     * @param content
     * @category 评论日记
     */
    public void commentDiary(long diaryId, Long replyId, String content) {
        dao.commentDiary(null, diaryId, replyId, content);
    }

    /**
     * @category 取消评论日记
     */
    public void uncommentDiary(long commentId) {
        dao.uncommentDiary(null, commentId);
    }

    /**
     * @param diaryId
     * @category 删除日记
     */
    public void deleteDiary(long diaryId) {
        dao.deleteDiary(null, diaryId);
    }

    /**
     * @param diaryDetail
     * @category 构建视图
     */
    private void buildView(DiaryDetail diaryDetail) {
        ll_item_bottom.setVisibility(View.VISIBLE);
        view_one.setVisibility(View.VISIBLE);
        view_two.setVisibility(View.VISIBLE);
        fillOwnerInfo(diaryDetail);
        fillDiaryPicture(diaryDetail);
        fillDiaryRelInfo(diaryDetail);
        fillDianzanView(diaryDetail);
        fillPinglunView(diaryDetail);
    }


    /**
     * @param diaryDetail
     * @category 填充个人头像和名称
     */
    private void fillOwnerInfo(DiaryDetail diaryDetail) {
        if (!TextUtils.isEmpty(diaryDetail.getAvatar())) {
            bitmapUtils.display(iv_owner_picture, HttpConnectUtils.image_ip + diaryDetail.getAvatar());
        } else {
            iv_owner_picture.setImageResource(R.mipmap.boy_head);
        }
        if (!TextUtils.isEmpty(diaryDetail.getName())) {
            tv_owner_name.setText(diaryDetail.getName());
        }
    }

    /**
     * @param diaryDetail
     * @category 填充日记照片
     */
    private void fillDiaryPicture(DiaryDetail diaryDetail) {
        if (!TextUtils.isEmpty(diaryDetail.getPhoto())) {
            bitmapUtils.display(iv_diarypic,
                    HttpConnectUtils.image_ip + diaryDetail.getPhoto());
        } else {
            iv_diarypic.setVisibility(View.GONE);
        }

    }

    /**
     * @param
     * @category 填充日记相关的文本信息
     */
    private void fillDiaryRelInfo(DiaryDetail diary) {
        String idea = diary.getIdea();// 描述信息
        String photo = diary.getPhoto();
        if (!TextUtils.isEmpty(photo) && TextUtils.isEmpty(idea)) {
            // 有图片，没有运动说说
            rl_two.setVisibility(View.VISIBLE);
            bitmapUtils.display(iv_diarypic, HttpConnectUtils.image_ip + photo);
            tv_description.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(photo) && !TextUtils.isEmpty(idea)) {
            // 两者都有
            rl_two.setVisibility(View.VISIBLE);
            bitmapUtils.display(iv_diarypic, HttpConnectUtils.image_ip + photo);
            tv_description.setVisibility(View.VISIBLE);
            tv_description.setText(idea);
        }

    }

    private boolean checkZanInfo() {
        boolean isZan = true;
        if (praiseCustomerList != null) {
            for (PraiseCustomer cust : praiseCustomerList) {
                // 便利点赞列表，如果是自己点赞的话，则取消赞，否则点赞
                if (cust.getCustomerId() == Long.parseLong(customer.getId())) {
                    isZan = false;
                    break;
                }
            }
        }
        return isZan;
    }


    /**
     * @param diaryDetail
     * @category 填充点赞视图
     */
    private void fillDianzanView(final DiaryDetail diaryDetail) {
        ll_zan.removeAllViews();
        praiseCustomerList = diaryDetail.getCustomerList();
        if (diaryDetail.getCustomerList() != null
                && diaryDetail.getCustomerList().size() > 0) {
            // 如果点赞列表大于0，则显示点赞的数量和赞button的状态
            if (checkZanInfo()) {
                // 不是自己点赞
                btn_ishaszan.setZanCount(0);
                count_zan.setVisibility(View.VISIBLE);
                count_zan.setText(String.valueOf(diaryDetail.getCustomerList()
                        .size()));
                if (diaryDetail.getCustomerList().size() > 0) {
                    ll_zantop.setVisibility(View.VISIBLE);
                } else {
                    ll_zantop.setVisibility(View.GONE);
                }
            } else {
                btn_ishaszan.setZanCount(diaryDetail.getCustomerList().size());
                count_zan.setVisibility(View.VISIBLE);
                if (diaryDetail.getCustomerList().size() > 0) {
                    ll_zantop.setVisibility(View.VISIBLE);
                } else {
                    ll_zantop.setVisibility(View.GONE);
                }
                count_zan.setText(String.valueOf(diaryDetail.getCustomerList()
                        .size()));
            }
            int width = (int) getResources().getDimension(R.dimen.x40);
            int leftMargin = (int) getResources().getDimension(R.dimen.x3);
            if (diaryDetail.getCustomerList().size() > 6) {
                // 点赞人数大于6个以上
                for (int i = 0; i < 5; i++) {
                    final PraiseCustomer praiseCustomer = diaryDetail
                            .getCustomerList().get(i);
                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            width, width);
                    params.leftMargin = leftMargin;
                    iv.setLayoutParams(params);
                    ll_zan.addView(iv);
                    iv.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {// 跳转到日记页面
                            jumpToPersonalDiaryList(praiseCustomer.getCustomerId(),
                                    praiseCustomer.getName(), praiseCustomer.getAvatar());
                        }
                    });
//                    bitmapUtils.display(iv, HttpConnectUtils.image_ip
//                            + diaryDetail.getCustomerList().get(i).getAvatar());
                    display(iv, HttpConnectUtils.image_ip
                            + diaryDetail.getCustomerList().get(i).getAvatar());
                }
                ImageView iv = new ImageView(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        width, width);
                params.leftMargin = leftMargin;
                iv.setLayoutParams(params);
                iv.setImageResource(R.mipmap.icon_more);
                iv.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {// 跳转到点赞列表页面
//                        jump2Customerlist(diaryDetail);
                    }
                });
                ll_zan.addView(iv);
                // 点赞人数大于6个以上
            } else {
                for (int i = 0; i < diaryDetail.getCustomerList().size(); i++) {
                    final PraiseCustomer praiseCustomer = diaryDetail
                            .getCustomerList().get(i);
                    ImageView iv = new ImageView(this);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            width, width);
                    params.leftMargin = leftMargin;
                    iv.setLayoutParams(params);
                    ll_zan.addView(iv);
                    iv.setOnClickListener(new OnClickListener() {// 跳转到日记页面

                        @Override
                        public void onClick(View v) {
                            jumpToPersonalDiaryList(praiseCustomer.getCustomerId(),
                                    praiseCustomer.getName(), praiseCustomer.getAvatar());
                        }
                    });
//                    bitmapUtils.display(iv, HttpConnectUtils.image_ip
//                            + diaryDetail.getCustomerList().get(i).getAvatar());
                    display(iv, HttpConnectUtils.image_ip
                            + diaryDetail.getCustomerList().get(i).getAvatar());
                }
            }

        } else {
            btn_ishaszan.setZanCount(0);
            count_zan.setVisibility(View.GONE);
            ll_zantop.setVisibility(View.GONE);
        }

        btn_ishaszan.setOnClickListener(this);
    }

    /**
     * @param diaryDetail
     * @category 填充评论视图
     */
    PinglunAdapter adapter;

    private void fillPinglunView(DiaryDetail diaryDetail) {
        List<DiaryComment> commentList = diaryDetail.getCommentList();
        if (commentList != null
                && (commentList.size() > 0 || commentList.size() == 0)) {
            if (commentList.size() == 0) {
                count_comment.setVisibility(View.GONE);
            } else {
                count_comment.setVisibility(View.VISIBLE);
                count_comment.setText(String.valueOf(commentList.size()));
            }
            adapter = new PinglunAdapter(commentList, this);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            Utils.setListViewHeightBasedOnChildren(listView);
            adapter.setAdapterItemClickListener(this);
            listView.setSelection(adapter.getCount());
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == 84) {
            hintKbTwo();
            showProgress(this, false);
            diaryDetail = dao.getDiaryDetail();
            buildView(diaryDetail);
        } else if (requestCode == 85) {
            getDiaryDetail(diaryId);
        } else if (requestCode == 86) {
            getDiaryDetail(diaryId);
        } else if (requestCode == 87) {
            et_pinglun.setText("");
            et_pinglun.setTag(null);
            getDiaryDetail(diaryId);
        } else if (requestCode == 88) {
            getDiaryDetail(diaryId);
        } else if (requestCode == 89) {
            application.isNeedUpdated = true;
            finish();
        }
    }

    /**
     * 初始化图片加载器
     */
    private void initBitmapUtils() {
        bitmapUtils = new BitmapUtils(this);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.boy_head);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(this));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(4);

    }

    private void display(ImageView container, String uri) {

        DefaultBitmapLoadCallBack bitmapLoadCallBack = new DefaultBitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadStarted(ImageView container, String uri,
                                      BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
                // Toast.makeText(getApplicationContext(), uri, 300).show();
            }

            @Override
            public void onLoadCompleted(ImageView container, String uri,
                                        Bitmap bitmap, BitmapDisplayConfig config,
                                        BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
                container.setImageBitmap(ImageHelper.toRoundBitmap(bitmap));
            }
        };
//        uri = uri + Constants.suffix;

        bitmapUtils.display(container, uri, bitmapLoadCallBack);

    }
    /**
     * @category 进入点赞用户列表页面
     */
//    private void jump2Customerlist(DiaryDetail diaryDetail) {
//        Intent intent = new Intent(this, CustomerListActivity.class);
//        intent.putExtra(Constants.diaryDetail, diaryDetail);
//        startActivity(intent);
//    }

    /**
     * @category 进入图片大图页面
     */
//    private void jump2ImagePage() {
//        Intent intent = new Intent(this, ImageDetailPage.class);
//        intent.putExtra(Constants.image_url,
//                HttpConnectUtils.image_ip + diaryDetail.getPhoto());
//        intent.putExtra(Constants.defaultID, R.drawable.icon_photo_default);
//        startActivity(intent);
//        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
//    }

    /**
     * @category 头像点击动作
     */
    @Override
    public void onPictureClick(Object object) {
        DiaryComment diaryComment = (DiaryComment) object;
        jumpToPersonalDiaryList(diaryComment.getCustomerId(),
                diaryComment.getName(), diaryComment.getAvatar());

    }

    /**
     * @category 昵称点击动作
     */
    @Override
    public void onNickClick(Object object) {

        DiaryComment diaryComment = (DiaryComment) object;
        jumpToPersonalDiaryList(diaryComment.getCustomerId(),
                diaryComment.getName(), diaryComment.getAvatar());
    }

    boolean isHuifu = false;

    /**
     * @category 点击整个条目相应回复或者删除自己的评论
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        PinglunAdapter adapter = (PinglunAdapter) parent.getAdapter();
        DiaryComment diaryComment = (DiaryComment) adapter.getItem(position);
        if (customerId == diaryComment.getCustomerId()) {// 点击自己的评论，弹出底部操作按钮
            showBottomPicker(diaryComment);
        } else {// 弹出键盘，显示回复 xxx
            et_pinglun.requestFocus();
            et_pinglun.setTag(diaryComment);// 此处必须设置tag，不然监听器里面无法判断是回复还是评论
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);

        }
    }

    /**
     * @category 添加键盘监听
     */
    public void addListener() {
        root.setOnkbdStateListener(new KeyboardLayout.onKybdsChangeListener() {

            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case KeyboardLayout.KEYBOARD_STATE_HIDE:
                        Toast.makeText(getApplicationContext(), "软键盘隐藏",
                                Toast.LENGTH_SHORT).show();
                        if (et_pinglun.getTag() == null) {// 正常评论隐藏
                            isHuifu = false;
                            et_pinglun.setHint("评论");
                        } else {// 回复动作,如果关闭时输入框内容为空，那么切换到正常评论
                            if (TextUtils.isEmpty(et_pinglun.getText().toString())) {
                                isHuifu = false;
                                et_pinglun.setTag(null);
                                et_pinglun.setHint("评论");
                            }
                        }
                        break;
                    case KeyboardLayout.KEYBOARD_STATE_SHOW:
                        Toast.makeText(getApplicationContext(), "软键盘弹起",
                                Toast.LENGTH_SHORT).show();
                        if (et_pinglun.getTag() != null) {
                            isHuifu = true;
                            DiaryComment diaryComment = (DiaryComment) et_pinglun
                                    .getTag();
                            et_pinglun.setHint("回复：" + diaryComment.getName());
                        } else {
                            isHuifu = false;
                            et_pinglun.setHint("评论");
                        }
                        break;
                }
            }
        });

    }

    PopupWindow popupWindow;
    View view;
    LinearLayout ll_delete;

    /**
     * @category 弹出顶部操作菜单
     */
    private void showTopMenu() {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.pop_top_menu, null);
            ll_delete = (LinearLayout) view.findViewById(R.id.ll_delete);
            int width = (int) getResources().getDimension(R.dimen.x95);
            int height = (int) getResources().getDimension(R.dimen.x55);
            popupWindow = new PopupWindow(view, width, height);
        }
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        btn_right.getLocationInWindow(location);

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        int xPos = location[0] + btn_right.getWidth() - popupWindow.getWidth();
        ;
        popupWindow.showAsDropDown(ll_top, xPos, 4);

        ll_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (popupWindow != null)
                    popupWindow.dismiss();
//                showProgressDialog();
                deleteDiary(diaryId);

            }
        });
    }

    /**
     * @category 弹出底部操作菜单
     */
    private void showBottomPicker(final DiaryComment diaryComment) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.pop_pinglunpicker,
                null);
        final RelativeLayout rl_delete_mypinglun = (RelativeLayout) view
                .findViewById(R.id.rl_delete_mypinglun);

        final RelativeLayout rl_delete = (RelativeLayout) view
                .findViewById(R.id.rl_delete);

        final RelativeLayout rl_cancel = (RelativeLayout) view
                .findViewById(R.id.rl_cancel);

        // 添加删除我的评论点击事件
        rl_delete_mypinglun.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });
        // 添加删除点击事件
        rl_delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                dialog.dismiss();
//                showProgressDialog();
                uncommentDiary(diaryComment.getCommentId());
            }
        });
        // 添加取消点击事件
        rl_cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public void close() {
        if (diaryDetail != null) {
            int count_Customer = diaryDetail.getCustomerList().size();
            int count_Comment = diaryDetail.getCommentList().size();
            Intent intent = new Intent();
            intent.putExtra("count_Customer", count_Customer);
            intent.putExtra("count_Comment", count_Comment);
            intent.putExtra("isprised", !checkZanInfo());
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                close();
                break;
            default:
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void jumpToPersonalDiaryList(long customerID, String customerName, String customerAvater) {
        Intent intent = new Intent(this, DiaryPageActivity.class);
        intent.putExtra(Constants.customerId, customerID);
        intent.putExtra(Constants.customerName, customerName);
        intent.putExtra(Constants.customerAvator, customerAvater);
        startActivity(intent);
    }

    /**
     * @category 进入图片大图页面
     */
    private void jump2ImagePage() {
        Intent intent = new Intent(this, ImageDetailPage.class);
        intent.putExtra(Constants.image_url,
                HttpConnectUtils.image_ip + diaryDetail.getPhoto());
        intent.putExtra(Constants.defaultID, R.mipmap.boy_head);
        startActivity(intent);
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }
}
