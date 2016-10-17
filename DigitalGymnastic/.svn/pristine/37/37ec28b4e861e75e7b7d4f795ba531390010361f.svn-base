package com.hike.digitalgymnastic.fragment;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.request.MessageDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MenuFragment extends BaseFragment {

    @ViewInject(R.id.main_ly)
    LinearLayout main_ly;
    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;

    @ViewInject(R.id.picture)
    ImageView picture;
    @ViewInject(R.id.tv_message_right)
    TextView tv_message_right;
    @ViewInject(R.id.menu_rl_my_watch)
    private RelativeLayout menu_rl_my_watch;
    @ViewInject(R.id.tv_watch_right)
    private TextView tv_watch_right;

    @ViewInject(R.id.menu_rl_my_goal)
    private RelativeLayout menu_rl_my_goal;
    @ViewInject(R.id.menu_rl_my_message)
    private RelativeLayout menu_rl_my_message;
    @ViewInject(R.id.menu_rl_set)
    private RelativeLayout menu_rl_set;
    @ViewInject(R.id.menu_rl_my_clock)
    private RelativeLayout menu_rl_my_clock;
    @ViewInject(R.id.menu_rl_message)
    private RelativeLayout menu_rl_message;

    @ViewInject(R.id.menu_rl_test)
    private RelativeLayout menu_rl_test;

    private View v;
    private MessageDao dao;
    private BaseDao baseDao;

    @OnClick(value = {R.id.menu_rl_test, R.id.btn_left, R.id.ll_btn_left,
            R.id.menu_rl_my_watch, R.id.menu_rl_my_goal,
            R.id.menu_rl_my_message, R.id.menu_rl_set, R.id.menu_rl_my_clock,
            R.id.menu_rl_message})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                ((MainMenuActivity) activity).fragmentBack();
                break;
            case R.id.ll_btn_left:
                ((MainMenuActivity) activity).fragmentBack();
                break;

            case R.id.menu_rl_my_watch:

                if (android.os.Build.VERSION.SDK_INT >= 18) {
                    if (!application.isCommunication) {
                        ((MainMenuActivity) activity).setTabSelection(
                                getString(R.string.menu_my_watch),
                                MainMenuActivity.mode_enter);
                    }
                } else {
                    new AlertDialog.Builder(activity).setMessage("系统版本过低，无法使用该功能！")
                            .setNegativeButton("确定", null).show();
                }
                break;
            case R.id.menu_rl_my_goal:
                ((MainMenuActivity) activity).setTabSelection(
                        getString(R.string.menu_my_goal),
                        MainMenuActivity.mode_enter);
                break;
            case R.id.menu_rl_my_message:
                ((MainMenuActivity) activity).setTabSelection(
                        getString(R.string.menu_my_message),
                        MainMenuActivity.mode_enter);
                break;
            case R.id.menu_rl_set:

                ((MainMenuActivity) activity).setTabSelection(
                        getString(R.string.menu_set), MainMenuActivity.mode_enter);
                break;
            case R.id.menu_rl_my_clock:
                //手环已经连接并且没有在进行蓝牙通信，跳转页面
                if (!application.isCommunication) {//
                    ((MainMenuActivity) activity).setTabSelection(
                            getString(R.string.menu_add_my_clock),
                            MainMenuActivity.mode_enter);
                }

                break;
            case R.id.menu_rl_message:
                ((MainMenuActivity) activity).setTabSelection(
                        getString(R.string.menu_message),
                        MainMenuActivity.mode_enter);
                break;
            case R.id.menu_rl_test:
//			Intent intent = new Intent(activity, ActivityTest.class);
//			startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
//		Utils.toolBarManager((MainMenuActivity) activity,R.color.app_bg_login);
        this.v = inflater.inflate(R.layout.activity_menu_fragment, container,
                false);
        ViewUtils.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initView();

    }

    void initData() {
        dao = new MessageDao(this, activity);
        baseDao = new BaseDao(this, (MainMenuActivity) activity);
        initBroadcastReceiver();
    }

    synchronized void initView() {
        btn_left.setImageResource(R.mipmap.icon_guanbi);
        // btn_left.setImageDrawable(null);
        // btn_left.setBackgroundResource(R.mipmap.icon_guanbi);
        application = HikoDigitalgyApplication.getInstance();

        if (TextUtils.isEmpty(application.bindMAC)) {
            tv_watch_right.setText(R.string.string_not_bind_device);
        } else {
            tv_watch_right.setText(R.string.string_had_bind);
        }
        // showProgress(true);


        if (dao.isRunning()) {
            dao.cancel();
        }
        dao.GetUnreadMessageCount();

    }

    HikoDigitalgyApplication application;

    @Override
    public void onResume() {
//		Utils.toolBarManager((MainMenuActivity) activity,R.color.app_bg_login);
        super.onResume();
        if (!this.isHidden()) {
            initView();
        }

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
        Log.v("MyLog", "MenuFragment onHiddenChanged");
        if (!hidden) {
            initView();
        } else {
            if (dao.isRunning()) {
                dao.cancel();
            }
        }

    }

    @Override
    public void onNoConnect() {
        // TODO Auto-generated method stub
        super.onNoConnect();
        showProgress(false);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if (requestCode == 72) {
            System.out.println("获取所有提醒成功");
        }
//		int num = dao.getMessageCount().getMessageCount();
//		if (num > 0) {
//			tv_message_right.setVisibility(View.VISIBLE);
//			if (num > 99) {
//				tv_message_right.setText("99+");
//			} else {
//				tv_message_right.setText(dao.getMessageCount()
//						.getMessageCount().toString());
//			}
//		} else {
//			tv_message_right.setVisibility(View.INVISIBLE);
//			tv_message_right.setText("");
//		}
    }

    MyReceiver receiver;

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            if (Constants.watchconnected.equals(intent.getAction())) {
                if (getActivity() != null && !getActivity().isFinishing() && !MenuFragment.this.isHidden()) {
                    initView();
                }
            }

        }

    }

    private void initBroadcastReceiver() {
        receiver = new MyReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constants.watchconnected);
        getActivity().registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        try {
            if (receiver != null) {
                getActivity().unregisterReceiver(receiver);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
