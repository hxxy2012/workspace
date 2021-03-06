package com.hike.digitalgymnastic;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.utils.Constants;
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

/**
 * @author changqi
 * @category 查看大图页面
 */
@ContentView(R.layout.activity_image_detail)
public class ImageDetailPage extends BaseActivity {
    @ViewInject(R.id.image)
    ImageView image;
    @ViewInject(R.id.fl_root)
    FrameLayout fl_root;
    @ViewInject(R.id.top)
    View top;
    @ViewInject(R.id.btn_delete)
    Button btn_delete;
    @ViewInject(R.id.title)
    TextView title;
    @ViewInject(R.id.ll_btn_left)
    LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    LinearLayout ll_btn_right;

    @OnClick(value = {R.id.fl_root, R.id.image, R.id.btn_delete,
            R.id.ll_btn_left, R.id.ll_btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.image:{
                finish();
                break;
            }
            case R.id.fl_root:
                finish();
                break;
            case R.id.ll_btn_left:
                finish();
                break;
            case R.id.ll_btn_right:
//			application.isNeedUpdate = true;
                finish();
                break;
            default:
                break;
        }
    }

    BitmapUtils bitmapUtils;
    int defaultID;
    String url;
    boolean isHasTop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(Constants.image_url);
            defaultID = savedInstanceState.getInt(Constants.defaultID);
            isHasTop = savedInstanceState.getBoolean(Constants.isHasTop);
        } else {
            url = getIntent().getStringExtra(Constants.image_url);
            defaultID = getIntent().getIntExtra(Constants.defaultID,
                    R.mipmap.boy_head);
            isHasTop = getIntent().getBooleanExtra(Constants.isHasTop, false);
        }
        if (!isHasTop) {
            top.setVisibility(View.GONE);
        } else {
            top.setVisibility(View.VISIBLE);
            fl_root.setOnClickListener(null);
            btn_delete.setVisibility(View.VISIBLE);
            title.setText("照片预览");
        }
        initBitmapUtils(defaultID);
        display(image, url);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.image_url, url);
        outState.putInt(Constants.defaultID, defaultID);
        outState.putBoolean(Constants.isHasTop, isHasTop);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    String imageCachePath;

    /**
     * 初始化图片加载器
     */
    private void initBitmapUtils(int defaultID) {
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/image";
        bitmapUtils = new BitmapUtils(this, imageCachePath);
        bitmapUtils.clearCache();
        // bitmapUtils.configDefaultLoadFailedImage(defaultID);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(this));
        // bitmapUtils.configDefaultLoadingImage(defaultID);
        bitmapUtils.configThreadPoolSize(4);

    }

    @SuppressWarnings("unchecked")
    private void display(ImageView container, String uri) {
        DefaultBitmapLoadCallBack bitmapLoadCallBack = new DefaultBitmapLoadCallBack<ImageView>() {
            @Override
            public void onLoadStarted(ImageView container, String uri,
                                      BitmapDisplayConfig config) {
                super.onLoadStarted(container, uri, config);
                // Toast.makeText(getApplicationContext(), uri, 300).show();
            }

            //
            // @Override
            // public void onPreLoad(ImageView container, String uri,
            // BitmapDisplayConfig config) {
            // super.onPreLoad(container, uri, config);
            // Drawable drawable=config.getLoadingDrawable();
            // float
            // rate=(float)drawable.getIntrinsicWidth()/drawable.getIntrinsicHeight();
            // // float rate=(float)bitmap.getHeight()/bitmap.getWidth();
            // int screenWidth =
            // getWindowManager().getDefaultDisplay().getWidth();
            // ViewGroup.LayoutParams lp = container.getLayoutParams();
            // lp.width = screenWidth;
            // lp.height = (int) (rate*screenWidth);
            // container.setLayoutParams(lp);
            // }

            @Override
            public void onLoadCompleted(ImageView container, String uri,
                                        Bitmap bitmap, BitmapDisplayConfig config,
                                        BitmapLoadFrom from) {
                super.onLoadCompleted(container, uri, bitmap, config, from);
                float rate = (float) bitmap.getHeight() / bitmap.getWidth();
                int screenWidth = getWindowManager().getDefaultDisplay()
                        .getWidth();
                ViewGroup.LayoutParams lp = container.getLayoutParams();
                lp.width = screenWidth;
                lp.height = (int) (rate * screenWidth);
                container.setLayoutParams(lp);
            }
        };
        // uri = uri;
        bitmapUtils.display(container, uri, bitmapLoadCallBack);

    }
}
