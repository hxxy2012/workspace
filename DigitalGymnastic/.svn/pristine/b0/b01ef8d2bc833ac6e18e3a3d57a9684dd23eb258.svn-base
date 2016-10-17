package com.hike.digitalgymnastic;

import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.DailySportData;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.entitiy.ShareImage;
import com.hike.digitalgymnastic.entitiy.ShareImageData;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ImageViewWihtBonder;
import com.hike.digitalgymnastic.view.ImageViewWihtBonder.OnCheckStateChangedListener;
import com.hiko.enterprisedigital.SocialShareActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;
import com.hiko.enterprisedigital.R;

/**
 * @author changqi
 * @category 分享页面编辑
 */
@ContentView(R.layout.activity_sharededitpage)
public class SharedEditedPage extends BaseActivity implements
        OnCheckStateChangedListener {

    @ViewInject(R.id.fl_top)
    FrameLayout fl_top;
    @ViewInject(R.id.hs_type)
    HorizontalScrollView hs_type;
    @ViewInject(R.id.hs_cal)
    HorizontalScrollView hs_cal;
    @ViewInject(R.id.hs_wall)
    HorizontalScrollView hs_wall;

    @ViewInject(R.id.ll_type)
    // 运动类型列表选项
            LinearLayout ll_type;
    @ViewInject(R.id.ll_cal)
    // cal消耗列表
            LinearLayout ll_cal;
    @ViewInject(R.id.ll_wall)
    // 个性贴纸
            LinearLayout ll_wall;

    @ViewInject(R.id.iv_close)
    // 关闭
            ImageView iv_close;
    @ViewInject(R.id.iv_cofirm)
    // 确定
            ImageView iv_cofirm;

    @ViewInject(R.id.rb_sport_pro)
    // 运动类型
            RadioButton rb_sport_pro;
    @ViewInject(R.id.rb_sport_cal)
    // cal消耗
            RadioButton rb_sport_cal;
    @ViewInject(R.id.rb_sport_wall)
    // 个性贴纸
            RadioButton rb_sport_wall;
    // 拍照图片
    @ViewInject(R.id.iv_picture)
    ImageView iv_picture;
    @ViewInject(R.id.iv_top_right)
    // 右上角选择的cal消耗和个性贴纸
            ImageView iv_top_right;
    @ViewInject(R.id.tv_count)
    // 右上角选择的约等于个数
            TextView tv_count;
    @ViewInject(R.id.tv_total)
    // 右上角选择的cal总数
            TextView tv_total;
    @ViewInject(R.id.tv_yuedeng)
    // 右上角选择的cal总数
            TextView tv_yuedeng;

    @ViewInject(R.id.ll_wall_selected)
    // 已选择的列表
            LinearLayout ll_wall_selected;

    @OnClick(value = {R.id.iv_close, R.id.iv_cofirm})
    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:// 关闭弹出提示对话框
                close();
                break;
            case R.id.iv_cofirm:// 确定直接跳到分享页面
                jump2ShareActivity();
                break;
            default:
                break;
        }
    }

    @OnCompoundButtonCheckedChange(value = {R.id.rb_sport_pro,
            R.id.rb_sport_cal, R.id.rb_sport_wall})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_sport_pro:
                    hs_type.setVisibility(View.VISIBLE);
                    hs_cal.setVisibility(View.INVISIBLE);
                    hs_wall.setVisibility(View.INVISIBLE);
                    type = 1;
                    if (shareImageData_three == null) {
                        getImage(type, 2);
                    } else {
                        buildView();
                    }
                    break;

                case R.id.rb_sport_wall:
                    hs_type.setVisibility(View.INVISIBLE);
                    hs_cal.setVisibility(View.INVISIBLE);
                    hs_wall.setVisibility(View.VISIBLE);
                    type = 2;
                    if (shareImageData_two == null) {
                        getImage(type, 2);
                    } else {
                        buildView();
                    }
                    break;
                case R.id.rb_sport_cal:
                    hs_type.setVisibility(View.INVISIBLE);
                    hs_cal.setVisibility(View.VISIBLE);
                    hs_wall.setVisibility(View.INVISIBLE);
                    type = 3;
                    if (shareImageData_three == null) {
                        getImage(type, 2);
                    } else {
                        buildView();
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);

        init();
    }

    HomeSportData hsd;
    String path;
    ShareImageData shareImageData_one;
    ShareImageData shareImageData_two;
    ShareImageData shareImageData_three;

    private void init() {
        initBitmapUtils();
        dao = new BaseDao(this, this);
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/share/sport_icon/";
        hsd = (HomeSportData) getIntent().getSerializableExtra(
                Constants.homesport);
        path = getIntent().getStringExtra(Constants.path);
        rb_sport_pro.setChecked(true);

    }

    private void getImage(int type, int size) {
        showProgress(this,true);
        dao.getShareImageData(type, 2);
    }

    int type = 2;//图片类型标识(1:icon 2:贴纸 3:运动消耗)

    private void buildView() {
        if (hsd != null && hsd.getDailyList() != null) {
            tv_total.setText(hsd.getTotalCalories()+"kcal");
        }
        fillPicture();
        if (type == 1) {
            fillSportTypeListLast(shareImageData_one);
//			fillSportTypeList();
        } else if (type == 2) {
            fillTiezhiListLast(shareImageData_two);
//			fillTiezhiList();
        }
        if (type == 3) {
            fillCalListLast(shareImageData_three);
//			fillCalList();
        }
    }

    private void fillPicture() {
        int degree = Utils.readPictureDegree(path);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bm = BitmapFactory.decodeFile(path, options);
        bm = Utils.rotaingImageView(degree, bm);
        iv_picture.setImageBitmap(bm);
    }

    // 填充底部的运动类型列表last
    private void fillSportTypeListLast(ShareImageData shareImageData) {
        if(ll_type.getChildCount()>0)
            return;
        if (hsd != null && hsd.getDailyList() != null) {
            int size = (int) getResources().getDimension(R.dimen.x45);
            int margin = (int) getResources().getDimension(R.dimen.x5);
            List<DailySportData> sport_list = hsd.getDailyList();
            String name;
            int i = 0;
            for (DailySportData dsd : sport_list) {
                name = dsd.getSportName();
                for (ShareImage shareImage : shareImageData.getImageList()) {
                    if (TextUtils.equals(name, shareImage.getImageName())) {
                        ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                        iv.setTag(shareImageData.getImageList().get(i));
                        iv.setListener(this);
                        ll_type.addView(iv);

                        if (i < 5) {
                            iv.setChecked(true);
                            ImageView iv_checked = new ImageView(this);
                            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                                    size, size);
                            param.setMargins(margin, margin, margin, margin);
                            iv_checked.setLayoutParams(param);
                            ll_wall_selected.addView(iv_checked);
                        }
                        i++;
                    }
                }
            }
        }
    }

    // 填充底部的运动类型列表pre
    private void fillSportTypeList() {
        if (hsd != null && hsd.getDailyList() != null) {
            int size = (int) getResources().getDimension(R.dimen.x45);
            int margin = (int) getResources().getDimension(R.dimen.x5);
            List<DailySportData> sport_list = hsd.getDailyList();
            String name;
            int drawable_id;
            int i = 0;
            for (DailySportData dsd : sport_list) {
                name = "icon_sport_type_" + dsd.getSportType();
                drawable_id = getResources().getIdentifier(name, "mipmap",
                        getApplicationContext().getPackageName());
                ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                iv.setImageResouce(drawable_id);
                iv.setListener(this);
                ll_type.addView(iv);

                if (i < 5) {
                    iv.setChecked(true);
                    ImageView iv_checked = new ImageView(this);
                    iv_checked.setImageResource(drawable_id);
                    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                            size, size);
                    param.setMargins(margin, margin, margin, margin);
                    iv_checked.setLayoutParams(param);
                    ll_wall_selected.addView(iv_checked);
                }
                i++;
            }
        }
    }

    /**
     * @category 填充kcal列表last
     */
    private void fillCalListLast(ShareImageData shareImageData) {
        if(ll_cal.getChildCount()>0)
            return;
        for (int i = 0; i < shareImageData.getImageList().size(); i++) {
            try {
                ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                iv.setTag(shareImageData.getImageList().get(i));
                iv.setListener(this);
                ll_cal.addView(iv);
                displayIcon(iv, shareImageData.getImageList().get(i).getImageUrl());
            } catch (Exception e) {
                break;
            }

        }
    }

    /**
     * @category 填充kcal列表pre
     */
    private void fillCalList() {
        String name;
        int drawable_id;
        for (int i = 0; i < 9; i++) {
            try {
                name = "icon_cal_" + (i + 1);
                drawable_id = getResources().getIdentifier(name, "mipmap",
                        getApplicationContext().getPackageName());
                if (drawable_id == 0)
                    continue;
                ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                iv.setImageResouce(drawable_id);
                iv.setListener(this);
                ll_cal.addView(iv);
            } catch (Exception e) {
                break;
            }

        }
    }

    /**
     * @category 填充个性贴纸的列表last
     */
    private void fillTiezhiListLast(ShareImageData shareImageData) {
        if(ll_wall.getChildCount()>0)
            return;
        for (int i = 0; i < shareImageData.getImageList().size(); i++) {
            try {
                ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                iv.setTag(shareImageData.getImageList().get(i));
                iv.setListener(this);
                ll_wall.addView(iv);
                displayIcon(iv, shareImageData.getImageList().get(i).getImageUrl());
            } catch (Exception e) {
                break;
            }

        }
    }

    /**
     * @category 填充个性贴纸的列表
     */
    private void fillTiezhiList() {
        String name;
        int drawable_id;
        for (int i = 0; ; i++) {
            try {
                name = "icon_tiezhi_" + (i + 1);
                drawable_id = getResources().getIdentifier(name, "mipmap",
                        getApplicationContext().getPackageName());
                if (drawable_id == 0)
                    break;
                ImageViewWihtBonder iv = new ImageViewWihtBonder(this);
                iv.setImageResouce(drawable_id);
                iv.setListener(this);
                ll_wall.addView(iv);
            } catch (Exception e) {
                break;
            }

        }
    }



    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
    }
    private void updateKcal(int kcal){
        if(type==2){
            tv_total.setVisibility(View.INVISIBLE);
            tv_count.setVisibility(View.INVISIBLE);
            tv_yuedeng.setVisibility(View.INVISIBLE);
        }else {
            if (hsd != null && hsd.getDailyList() != null) {
                if (!TextUtils.isEmpty(hsd.getTotalCalories())) {
                    double total = Double.parseDouble(hsd.getTotalCalories());
                    tv_count.setText("X  " + (int) (total / kcal));
                    tv_yuedeng.setText("≈");
                    tv_count.setVisibility(View.VISIBLE);
                    tv_yuedeng.setVisibility(View.VISIBLE);
                } else {
                    tv_count.setVisibility(View.INVISIBLE);
                    tv_yuedeng.setVisibility(View.INVISIBLE);
                }
            }
        }
    }
    @Override
    public void onCheckStateChanged(boolean isChecked, ImageViewWihtBonder iv) {
        // TODO Auto-generated method stub
        ShareImage si= (ShareImage) iv.getTag();
        if (isChecked) {
            if (rb_sport_pro.isChecked()) {// 当前选择的是运动项目-处理
                ImageViewWihtBonder child;
                if (ll_wall_selected.getChildCount() == 5) {
                    for (int i = 0; i < ll_type.getChildCount(); i++) {// 更新ll_type的显示状态
                        child = (ImageViewWihtBonder) ll_type.getChildAt(i);
                        if (child.isChecked()) {
                            if (!iv.equals(child)) {
                                child.setChecked(false);
                                break;
                            }
                        }
                    }
                }
                // 更新选中的列表
                ll_wall_selected.removeAllViews();
                ImageView iv_checked;
                LinearLayout.LayoutParams param;
                int size = (int) getResources().getDimension(R.dimen.x45);
                int margin = (int) getResources().getDimension(R.dimen.x5);
                for (int i = 0; i < ll_type.getChildCount(); i++) {// 更新ll_type的显示状态
                    child = (ImageViewWihtBonder) ll_type.getChildAt(i);
                    if (child.isChecked()) {
                        iv_checked = new ImageView(this);
//                        iv_checked.setImageResource(child.getResId());
//                        iv_checked.setImageBitmap(child.getBitmap());
                        iv_checked.setBackground(Utils.bitmap2Drawable(child.getBitmap()));
                        param = new LinearLayout.LayoutParams(size, size);
                        param.setMargins(margin, margin, margin, margin);
                        iv_checked.setLayoutParams(param);
                        ll_wall_selected.addView(iv_checked);
                    }
                }

            } else if (rb_sport_cal.isChecked()) {// 当前选择的是运动消耗-处理

                ImageViewWihtBonder child;
//                iv_top_right.setBackgroundResource(iv.getResId());
                iv_top_right.setBackground(Utils.bitmap2Drawable(iv.getBitmap()));
                for (int i = 0; i < ll_cal.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_cal.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }

                updateKcal(si.getKaluli());
            } else {// 当前选择的是个性贴图-处理
                ImageViewWihtBonder child;
//                iv_top_right.setBackgroundResource(iv.getResId());
                iv_top_right.setBackground(Utils.bitmap2Drawable(iv.getBitmap()));
                for (int i = 0; i < ll_wall.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_wall.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }
                updateKcal(si.getKaluli());
            }
        } else {
            if (rb_sport_pro.isChecked()) {// 当前选择的是运动项目-处理
                ImageViewWihtBonder child;
                // 更新选中的列表
                ll_wall_selected.removeAllViews();
                ImageView iv_checked;
                LinearLayout.LayoutParams param;
                int size = (int) getResources().getDimension(R.dimen.x45);
                int margin = (int) getResources().getDimension(R.dimen.x5);
                for (int i = 0; i < ll_type.getChildCount(); i++) {// 更新ll_type的显示状态
                    child = (ImageViewWihtBonder) ll_type.getChildAt(i);
                    if (child.isChecked()) {
                        iv_checked = new ImageView(this);
//                      iv_checked.setImageResource(child.getResId());
//                        iv_checked.setImageBitmap(child.getBitmap());
                        iv_checked.setBackground(Utils.bitmap2Drawable(child.getBitmap()));
                        param = new LinearLayout.LayoutParams(size, size);
                        param.setMargins(margin, margin, margin, margin);
                        iv_checked.setLayoutParams(param);
                        ll_wall_selected.addView(iv_checked);
                    }
                }

            } else if (rb_sport_cal.isChecked()) {// 当前选择的是运动消耗-处理
                ImageViewWihtBonder child;
                iv_top_right.setBackgroundResource(getResources().getColor(R.color.transparent));
                for (int i = 0; i < ll_cal.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_cal.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }
                for (int i = 0; i < ll_wall.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_wall.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }
            } else {// 当前选择的是个性贴图-处理
                ImageViewWihtBonder child;
                iv_top_right.setBackgroundResource(getResources().getColor(R.color.transparent));
                for (int i = 0; i < ll_cal.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_cal.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }
                for (int i = 0; i < ll_wall.getChildCount(); i++) {
                    child = (ImageViewWihtBonder) ll_wall.getChildAt(i);
                    if (!child.equals(iv)) {
                        child.setChecked(false);
                    }
                }
            }
        }

    }

    boolean isSaving = false;

    private void jump2ShareActivity() {
        if (!isSaving) {
            showProgress(this,true);
            isSaving = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    String filePath = Utils.savePic(
                            Utils.getBitmapByView(fl_top, 0), "sport_xuanyao");
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = filePath;
                    handler.sendMessage(msg);
                }
            }).start();

        }
    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

           // showProgress(this,false);
            switch (msg.what) {
                case 0:
                    if (msg.obj != null) {
                        String filePath = (String) msg.obj;
                        Intent intent = new Intent(SharedEditedPage.this,
                                SocialShareActivity.class);
                        intent.putExtra("filePath", filePath);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        isSaving = false;
                    }
                    break;

                default:
                    break;
            }

        }

        ;
    };

    private void close() {
        new AlertDialog.Builder(this).setMessage("是否确定放弃编辑图片")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
    }

    BitmapUtils bitmapUtils;
    String imageCachePath;

    /**
     * 初始化图片加载器
     */
    private void initBitmapUtils() {
        bitmapUtils = new BitmapUtils(this, imageCachePath);
//		bitmapUtils.configDefaultLoadFailedImage(R.mipmap.ic_launcher);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(this));
//		bitmapUtils.configDefaultLoadingImage(R.mipmap.ic_launcher);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);

    }

    private void displayIcon(final ImageViewWihtBonder ivwb, String uri) {
        String url = HttpConnectUtils.image_ip + uri;
        bitmapUtils.display(ivwb, url,
                new BitmapLoadCallBack<View>() {

                    @Override
                    public void onLoadCompleted(View container, String uri,
                                                Bitmap bitmap, BitmapDisplayConfig config,
                                                BitmapLoadFrom from) {
                        ivwb.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onLoadFailed(View container, String uri,
                                             Drawable drawable) {

                    }
                });

    }


    BaseDao dao;

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(this,false);
        if (requestCode == 78) {
            if (type == 1)
                shareImageData_one = dao.getShareImageData();
            else if (type == 2)
                shareImageData_two = dao.getShareImageData();
            else
                shareImageData_three = dao.getShareImageData();

            buildView();
        }

    }
}
