package com.hike.digitalgymnastic;

import android.app.Activity;
import android.widget.TextView;

import com.hiko.enterprisedigital.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by liqing on 2016/3/30.
 */
@EActivity(R.layout.layout_activity_power)
public class ActivityPowerExerciser extends BaseActivity {
    /**
     * 标题文字
     */
    @ViewById(R.id.title)
    TextView mTitleText;


    @AfterViews
    void initView(){
        mTitleText.setText(R.string.string_power_equipment);
    }

    /**
     * 返回按钮事件
     */
    @Click(R.id.ll_btn_left)
    void back(){
        finish();
    }
}
