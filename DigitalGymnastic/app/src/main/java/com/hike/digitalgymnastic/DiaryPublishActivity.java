package com.hike.digitalgymnastic;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.NetworkUtil;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.KeyboardLayout;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nineoldandroids.animation.ValueAnimator;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huwei on 2016/2/22.
 */

@ContentView(R.layout.activity_diary_publish)
public class DiaryPublishActivity extends BaseActivity implements View.OnLayoutChangeListener, View.OnClickListener {
    @ViewInject(R.id.root)
    KeyboardLayout root;
    @ViewInject(R.id.ll_btn_left)
    LinearLayout ll_btn_left;
    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    @ViewInject(R.id.ll_btn_right)
    LinearLayout ll_btn_right;
    @ViewInject(R.id.tv_right)
    TextView tv_right;
    @ViewInject(R.id.iv_pic)
    ImageView iv_pic;
    @ViewInject(R.id.et_description)
    EditText et_description;

    @ViewInject(R.id.iv_pyq)
    ImageView iv_pyq;
    @ViewInject(R.id.iv_sina)
    ImageView iv_sina;
    BaseDao dao;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
        initShareSetting();
    }

    private void init() {
//        root = findViewById(R.id.root);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        title = getString(R.string.app_name);

//        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
//        keyHeight = screenHeight / 3;
//        params = (LinearLayout.LayoutParams) iv_pic.getLayoutParams();
        application = (HikoDigitalgyApplication) getApplication();
        filePath = getIntent().getStringExtra("imagePath");
        WindowManager windowManager = this.getWindowManager();

        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();

        params = new LinearLayout.LayoutParams(screenWidth, screenWidth);
        iv_pic.setLayoutParams(params);
        initPics();
        initTop();
//        setListener();
//        addListener();

    }

    private void addListener() {
        et_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isImageScale = true;
                ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.7f);
                valueAnimator.setDuration(500);
                valueAnimator.start();
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue();
                        params.width = (int) ((1.0f - value) * iv_pic_width);
                        params.height = (int) ((1.0f - value) * iv_pic_height);
                        iv_pic.setLayoutParams(params);
                        iv_pic.requestLayout();
                    }
                });
                et_description.setClickable(false);
                et_description.setEnabled(false);
            }
        });

        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_description.setClickable(true);
                et_description.setEnabled(true);
                hideKb();
            }

        });

    }

    /**
     * @category 添加键盘监听
     */
    private void setListener() {
        //键盘添加监听
        root.setOnkbdStateListener(new KeyboardLayout.onKybdsChangeListener() {

            @Override
            public void onKeyBoardStateChange(int state) {
                switch (state) {
                    case KeyboardLayout.KEYBOARD_STATE_HIDE:

                        if (isImageScale) {
                            ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0.3f, 1);
                            valueAnimator.setDuration(500);
                            valueAnimator.start();
                            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                @Override
                                public void onAnimationUpdate(ValueAnimator animation) {
                                    float value = (float) animation.getAnimatedValue();
                                    params.width = (int) (value * iv_pic_width);
                                    params.height = (int) (value * iv_pic_height);
                                    iv_pic.setLayoutParams(params);
                                    iv_pic.requestLayout();
                                }
                            });
                        }
                        break;
                    case KeyboardLayout.KEYBOARD_STATE_SHOW:
//                        isImageScale = true;
//                        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 0.7f);
//                        valueAnimator.setDuration(500);
//                        valueAnimator.start();
//                        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                            @Override
//                            public void onAnimationUpdate(ValueAnimator animation) {
//                                float value = (float) animation.getAnimatedValue();
//                                params.width = (int) ((1.0f - value) * iv_pic_width);
//                                params.height = (int) ((1.0f - value) * iv_pic_height);
//                                iv_pic.setLayoutParams(params);
//                                iv_pic.requestLayout();
//                            }
//                        });
                        break;
                }
            }
        });
        iv_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isImageScale) {
//                    ValueAnimator valueAnimator = new ValueAnimator().ofFloat(0.3f, 1);
//                    valueAnimator.setDuration(500);
//                    valueAnimator.start();
//                    valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                        @Override
//                        public void onAnimationUpdate(ValueAnimator animation) {
//                            float value = (float) animation.getAnimatedValue();
//                            params.width = (int) (value * iv_pic_width);
//                            params.height = (int) (value * iv_pic_height);
//                            iv_pic.setLayoutParams(params);
//                            iv_pic.requestLayout();
//                        }
//                    });
                    hideKb();
                }
            }
        });


    }


    boolean isImageScale;

    private void initTop() {
        dao = new BaseDao(this, this);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("发布");
    }

    String imagePath;
    int degree;
    Bitmap bm;

    private Bitmap myBitmap;

    private void initPics() {
        if (getIntent() != null)
            imagePath = getIntent().getStringExtra("imagePath");
        Display _display = getWindowManager().getDefaultDisplay();
        int _dw = _display.getWidth();
        int _dh = _display.getHeight();
        android.graphics.BitmapFactory.Options _option = new android.graphics.BitmapFactory.Options();
        _option.inJustDecodeBounds = true;
        myBitmap = android.graphics.BitmapFactory.decodeFile(imagePath, _option);
        int _heightRatio = (int) Math.ceil(_option.outHeight / (float) _dh);
        int _widthRatio = (int) Math.ceil(_option.outWidth / (float) _dw);
        if (_heightRatio > 1 && _widthRatio > 1) {
            if (_heightRatio > _widthRatio) {
                _option.inSampleSize = _heightRatio;
            } else {
                _option.inSampleSize = _widthRatio;
            }
        }
        _option.inJustDecodeBounds = false;
        myBitmap = BitmapFactory.decodeFile(imagePath, _option);
        degree = Utils.readPictureDegree(imagePath);

        if (degree != 0) {
            android.graphics.Matrix matrix = new android.graphics.Matrix();
            matrix.reset();
            //竖屏拍照支持
            matrix.postRotate(90);
            myBitmap = Bitmap.createBitmap(myBitmap, 0, 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix, true);
            iv_pic.setImageBitmap(myBitmap);
        } else {
            iv_pic.setImageBitmap(myBitmap);
        }
        if (imagePath != null) {
            uploadImage(imagePath);
        }
    }


    // 上传图片
    private void uploadImage(final String imagePath) {
//        showProgress(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                File file = new File(imagePath);
                myBitmap = Utils.imageFile2Bitmap(file);
                String filePath = Utils.savePic(myBitmap, "picHead");
                dao.uploadFile(2, filePath);
            }
        }).start();
    }

    //分享图片的宽高
    double iv_pic_width, iv_pic_height;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        iv_pic_width = iv_pic.getMeasuredWidth();
        iv_pic_height = iv_pic.getMeasuredHeight();
    }

    //保存日记
    private void saveDiary() {
        String diaryTime = sdf.format(new Date());
        dao.saveDiary(3, diaryTime, 1,
                et_description.getText().toString(), imageUrl);
    }

    @OnClick(value = {R.id.ll_btn_left, R.id.ll_btn_right, R.id.btn_left, R.id.tv_right, R.id.iv_pyq, R.id.iv_sina})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right:
            case R.id.ll_btn_right:
                //发布日记
                saveDiary();
                break;
            case R.id.btn_left:
            case R.id.ll_btn_left:
                finish();
                break;
            //分享日记到朋友圈和微博
            case R.id.iv_pyq:
                if (light_pyq) {
                    iv_pyq.setImageResource(R.mipmap.pyq_normal);
                    light_pyq = false;
                } else {
                    iv_pyq.setImageResource(R.mipmap.pyq_pressed);
                    light_pyq = true;
                    if (NetworkUtil.isNetwork(DiaryPublishActivity.this))
                        share_weixin_circle();
                    else
                        Utils.showMessage(DiaryPublishActivity.this, "网络不给力哦");
                }
                break;
            case R.id.iv_sina:
                if (light_weibo) {
                    iv_sina.setImageResource(R.mipmap.weibo_normal);
                    light_weibo = false;
                } else {
                    iv_sina.setImageResource(R.mipmap.weibo_pressed);
                    light_weibo = true;
                    if (NetworkUtil.isNetwork(DiaryPublishActivity.this))
                        share_sina();
                    else
                        Utils.showMessage(DiaryPublishActivity.this, "网络不给力哦");
                }
                break;
            default:
                break;
        }
    }

    boolean light_pyq = false;
    boolean light_weibo = false;

    String avatar;
    String imageUrl;

    @Override
    protected void onResume() {
        super.onResume();
//        root.addOnLayoutChangeListener(this);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == 7 || requestCode == 83) {// 上传图片或上传文件
            imageUrl = dao.getImageurl().getImageUrl();
            dao.ModifyCustomer(customer);
        }
        if (requestCode == 9) {// 修改个人信息
            application.isCustomerModify = true;
            LocalDataUtils.saveCustomer(this, customer);
        }
        if (requestCode == 82) {
            //发布日记成功，关掉当前页面
            application.isNeedUpdated = true;
            finish();
        }
    }

    //    private View root;
    private int screenHeight = 0;//屏幕高度
    private int screenWidth = 0;
    //软件盘弹起后所占高度
    private int keyHeight = 0;
    LinearLayout.LayoutParams params;

    @Override
    public void onLayoutChange(View v, int left, int top, int right,
                               int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {

        //old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值

//		System.out.println(oldLeft + " " + oldTop +" " + oldRight + " " + oldBottom);
//		System.out.println(left + " " + top +" " + right + " " + bottom);

        //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            Toast.makeText(DiaryPublishActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();


        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
            Toast.makeText(DiaryPublishActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();

        }

    }

    UMSocialService mController;
    String title;

    private void initShareSetting() {

        String appID = "wx967daebe835fbeac";
        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";

        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(this,
                getString(R.string.wx_appid), appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        // 首先在您的Activity中添加如下成员变量
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");

        // 设置新浪SSO handler
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        mController.getConfig().closeToast();

    }

    // 微信朋友圈
    private void share_weixin_circle() {
        // 设置微信朋友圈分享纯文本内容
        // CircleShareContent circleMedia = new CircleShareContent();
        // circleMedia.setShareContent("来自友盟社会化组件（SDK）让移动应用快速整合社交分享功能，朋友圈");
        // //设置朋友圈title
        // circleMedia.setTitle("友盟社会化分享组件-朋友圈");
        // circleMedia.setTargetUrl("你的URL链接");
        // mController.setShareMedia(circleMedia);

        // 设置微信朋友圈分享纯图片内容
        CircleShareContent circleMedia = new CircleShareContent();
        circleMedia.setShareImage(new UMImage(this, filePath));
        circleMedia.setTargetUrl("");
        mController.setShareMedia(circleMedia);

        // 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
        mController.postShare(this, SHARE_MEDIA.WEIXIN_CIRCLE,
                new SocializeListeners.SnsPostListener() {
                    @Override
                    public void onStart() {
                        // Toast.makeText(SocialShareActivity.this, "开始分享.",
                        // Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete(SHARE_MEDIA platform, int eCode,
                                           SocializeEntity entity) {
                        if (eCode == 200) {
                            Toast.makeText(DiaryPublishActivity.this, "分享成功.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            String eMsg = "";
                            if (eCode == -101) {
                                eMsg = "没有授权";
                            } else if (eCode == 40000) {

                            } else {
                                Toast.makeText(DiaryPublishActivity.this,
                                        "分享失败[" + eCode + "] " + eMsg,
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    // 新浪微博分享
    private void share_sina() {
        // 设置分享内容
        mController.setShareContent(getString(R.string.app_name));
        // 设置分享图片, 参数2为图片的url地址
        mController.setShareMedia(new UMImage(this, filePath));
        // 参数1为Context类型对象， 参数2为要分享到的目标平台， 参数3为分享操作的回调接口
        mController.postShare(this, SHARE_MEDIA.SINA, new SocializeListeners.SnsPostListener() {
            @Override
            public void onStart() {
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int eCode,
                                   SocializeEntity entity) {
                if (eCode == 200) {
                    Toast.makeText(DiaryPublishActivity.this, "分享成功.",
                            Toast.LENGTH_SHORT).show();

                } else {
                    String eMsg = "";
                    if (eCode == -101) {
                        eMsg = "没有授权";
                    } else if (eCode == 40000) {

                    } else {
                        Toast.makeText(DiaryPublishActivity.this,
                                "分享失败[" + eCode + "] " + eMsg,
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        /**使用SSO授权必须添加如下代码 */
        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
        if (ssoHandler != null) {
            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }

    private void hideKb() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
