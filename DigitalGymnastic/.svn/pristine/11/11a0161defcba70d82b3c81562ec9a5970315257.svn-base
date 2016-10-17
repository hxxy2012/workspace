package com.hike.digitalgymnastic;

import android.widget.TextView;

import com.hiko.enterprisedigital.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by liqing on 2016/3/30.
 */
@EActivity(R.layout.layout_activity_set_alert)
public class ActivitySetAlert extends BaseActivity{

    @ViewById(R.id.title)
    TextView mTitleTextView;

    @AfterViews
    void initView(){
        mTitleTextView.setText(R.string.string_alert);
    }
    @Click(R.id.btn_left)
    void leftLayoutBack(){
        finish();
    }
    @Click(R.id.ll_btn_left)
    void back(){
        finish();
    }

}
