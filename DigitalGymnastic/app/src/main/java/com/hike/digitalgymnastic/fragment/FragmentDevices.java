package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.ActivityAerobicExerciser_;
import com.hike.digitalgymnastic.ActivityPowerExerciser_;
import com.hike.digitalgymnastic.ActivitySetAlert_;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * Created by liqing on 16/3/23.
 * 装备
 */

public class FragmentDevices extends  BaseFragment  {

    private String TAG = "FragmentDevices";

    private MainActivity mMainActivity;
    /**
     * 标题栏左边返回按钮 此处不做显示
     */
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout mTitleLeftButton;
    /**
     * 装备标题
     */
    @ViewInject(R.id.title)
    TextView mTitleText;
    /**
     * 添加绑定设备界面
     */
    @ViewInject(R.id.tv_right)
    TextView mRightAddDeviceButton;
    /**
     * 手环名字
     */
    @ViewInject(R.id.id_device_name)
    TextView mDeviceNameTextView;
    /**
     * 如果没有绑定手环显示此文本
     */
    @ViewInject(R.id.id_no_device)
    TextView mNoDeviceTextView;
    /**
     * 有设备的箭头
     */
    @ViewInject(R.id.id_device_arrow)
    ImageView mDeviceArrow;

    /**
     * 设备图标
     */
    @ViewInject(R.id.iv_homepage)
    ImageView mDeviceIcon;
    /**
     * 设备布局
     */
    @ViewInject(R.id.rl_homepage)
    RelativeLayout mDeviceLayout;

    public static FragmentDevices newInstance() {
        return new FragmentDevices();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View _view = inflater.inflate(R.layout.layout_fragment_devices, container, false);
        ViewUtils.inject(this, _view);
        return _view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    /**
     * 初始化界面
     */
    private void initView() {
        mTitleLeftButton.setVisibility(View.GONE);
        mTitleText.setText(R.string.string_device);
        mRightAddDeviceButton.setVisibility(View.VISIBLE);
        mRightAddDeviceButton.setText(R.string.string_add);
        /**
         * 判断如果有绑定手环 则显示手环item
         */
        String _bindMac = application.bindMAC;
        if (!TextUtils.isEmpty(_bindMac)){
            mNoDeviceTextView.setVisibility(View.GONE);
            mDeviceNameTextView.setVisibility(View.VISIBLE);
            mDeviceArrow.setVisibility(View.VISIBLE);
            mDeviceIcon.setVisibility(View.VISIBLE);
        }else{
            mDeviceLayout.setOnClickListener(null);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UtilLog.e(TAG, "attach");
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMainActivity.mMainTabView.setVisibility(View.VISIBLE);
    }

    @OnClick(value = { R.id.id_power_equipment,R.id.id_aerobic_exerciser_layout, R.id.id_alert_image_view,R.id.tv_right,R.id.id_clock_image_view,R.id.iv_homepage,R.id.rl_homepage})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_right: {
                //点击添加按钮
                goBindDevicePage();
                break;
            }
            case R.id.id_clock_image_view:{
                //点击闹钟图标
                FragmentTransaction _ft = mMainActivity.getManager().beginTransaction();
                _ft.replace(R.id.id_rlyt_fragment, new AddClockFrament(), R.id.id_clock_image_view + "");
                _ft.addToBackStack(null);
                _ft.commit();
                mMainActivity.mMainTabView.setVisibility(View.GONE);
                break;
            }
            case R.id.rl_homepage:{
                //点击手环item
                goBindDevicePage();
                break;
            }
            case R.id.id_alert_image_view:{
                //设置app提醒
                startActivity(new Intent(mMainActivity,ActivitySetAlert_.class));
                break;
            }
            case R.id.id_aerobic_exerciser_layout:{
                //有氧运动
                startActivity(new Intent(mMainActivity,ActivityAerobicExerciser_.class));
                break;
            }
            case R.id.id_power_equipment:{
                //力量运动
                startActivity(new Intent(mMainActivity,ActivityPowerExerciser_.class));
                break;
            }
        }
    }

    private void goBindDevicePage() {
        FragmentTransaction _ft = mMainActivity.getManager().beginTransaction();
        _ft.replace(R.id.id_rlyt_fragment, new MyWatchFragment(), R.id.tv_right + "");
        _ft.addToBackStack(null);
        _ft.commit();
        mMainActivity.mMainTabView.setVisibility(View.GONE);
    }

    public void setCurrentFragment() {

    }
}
