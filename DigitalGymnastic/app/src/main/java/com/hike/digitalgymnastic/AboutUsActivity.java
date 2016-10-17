package com.hike.digitalgymnastic;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by huwei on 16/3/29.
 */
@ContentView(R.layout.activity_aboutus)
public class AboutUsActivity extends BaseActivity implements View.OnClickListener {
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.btn_right)
    private ImageView btn_right;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;
    @ViewInject(R.id.tv_version_code)
    private TextView tv_version_code;

    @OnClick(value = {R.id.btn_left, R.id.ll_btn_left})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
            case R.id.ll_btn_left:
                // 标记是否需要更新页面
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        init();
    }


    private void init() {

        tv_version_code.setText(getVersion());
        title.setText(getString(R.string.about_us));
        title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
        btn_left.setImageResource(R.mipmap.back_login_3x);
        btn_left.setVisibility(View.VISIBLE);
        btn_right.setVisibility(View.INVISIBLE);
    }

    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = this.getPackageManager();
            PackageInfo info = manager.getPackageInfo(
                    (this.getPackageName()), 0);
            String version = info.versionName;
            return this.getString(R.string.version_name) + version;
        } catch (Exception e) {
            e.printStackTrace();
            return this.getString(R.string.can_not_find_version_name);
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(this, false);
    }

}
