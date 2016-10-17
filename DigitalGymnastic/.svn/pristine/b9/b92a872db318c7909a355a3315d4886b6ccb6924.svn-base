package com.hike.digitalgymnastic;

import android.content.Intent;
import android.widget.TextView;

import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.SportType;
import com.hiko.enterprisedigital.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by liqing on 2016/3/30.
 */
@EActivity(R.layout.layout_activity_aerobice)
public class ActivityAerobicExerciser extends BaseActivity {
    private static String TAG = "ActivityAerobicExerciser";

    /**
     * 标题文字
     */
    @ViewById(R.id.title)
    TextView mTitleText;


    @AfterViews
    void initView(){
        mTitleText.setText(R.string.string_aerobic_exerciser);
    }

    /**
     * 返回按钮事件
     */
    @Click(R.id.ll_btn_left)
    void back(){
        finish();
    }

    /**
     * 步行点击事件
     */
    @Click(R.id.id_walk_image_view)
    void onClickWalk(){
        Intent _intent = new Intent(this, ActivitySportAllDetail.class);
        _intent.putExtra(Constants.oncesporttype, SportType.HKSportTypeBuXing);
        startActivity(_intent);


    }
}
