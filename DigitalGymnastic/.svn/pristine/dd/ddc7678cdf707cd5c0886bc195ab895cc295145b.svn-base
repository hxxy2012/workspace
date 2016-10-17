package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.UIHandler;
import com.hike.digitalgymnastic.entitiy.MyClock;
import com.hike.digitalgymnastic.service.BLEDataType;
import com.hike.digitalgymnastic.tools.EventNotifacation;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.BluetoothUtils;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.view.ClockItemView;
import com.hike.digitalgymnastic.view.ClockItemView.StateChangedListener;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     闹钟界面
 *     如果没有闹钟，显示连接，但是现在的手环自带闹钟，只不过是不开而已，所以添加闹钟的功能可以删除。
 * </pre>
 */
public class AddClockFrament extends BaseFragment implements
        StateChangedListener, UIHandler {

    private View v;
    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.btn_right)
    private ImageView btn_right;

    @ViewInject(R.id.btn_right_text)
    private Button btn_right_text;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;

    @ViewInject(R.id.title)
    private TextView title;
    // 未连接手环显示页面
    @ViewInject(R.id.rl_clock_noconnected)
    private RelativeLayout rl_clock_noconnected;
    @ViewInject(R.id.btn_connect2bond)
    private Button btn_connect2bond;

    // 空页面显示
    @ViewInject(R.id.rl_clock_blank)
    private RelativeLayout rl_clock_blank;

    @ViewInject(R.id.ll_my_clock)
    private LinearLayout ll_my_clock;
    private ArrayList<MyClock> myClockList = new ArrayList<MyClock>();
    private MainActivity mMainActivity;
    private String TAG = "AddClockFrament";


    @OnClick(value = {R.id.btn_left, R.id.btn_right_text, R.id.ll_btn_left,
            R.id.ll_btn_right, R.id.btn_right_text})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                mMainActivity.getManager().popBackStack();
                break;
            case R.id.ll_btn_left:
                mMainActivity.getManager().popBackStack();
                break;

            default:
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UtilLog.e(TAG, "onAttach");
        mMainActivity = (MainActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        this.v = inflater.inflate(R.layout.activity_menu_add_my_clock,
                container, false);
        ViewUtils.inject(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    private void initData() {
        myClockList.clear();
        view_list.clear();
        ll_my_clock.removeAllViews();
        title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
        btn_left.setImageResource(R.mipmap.back_login_3x);
        title.setText(getString(R.string.menu_clock_str));
        getAlarmList();

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            initData();
        }
    }

    /**
     * @category 获取手环闹钟列表
     */
    private void getAlarmList() {
        showProgress(true);
        myClockList = LocalDataUtils.getClockData(application.getApplicationContext());
        buildView();
    }

    List<ClockItemView> view_list = new ArrayList<ClockItemView>();

    /**
     * @category 构建视图
     */
    private void buildView() {
        ll_my_clock.removeAllViews();


        if (!TextUtils.isEmpty(application.bindMAC) && myClockList.size() > 0) {
            application.registerUIHandler(this);
            EventBus.getDefault().register(this);
            rl_clock_noconnected.setVisibility(View.GONE);
            rl_clock_blank.setVisibility(View.GONE);
            ll_my_clock.setVisibility(View.VISIBLE);
            MyClock _clockOne = getClockObject(0);
            setClockView(_clockOne);
            MyClock _clockTwo = getClockObject(1);
            setClockView(_clockTwo);
            showProgress(false);
        } else {
            //如果没有闹钟，显示连接
            rl_clock_noconnected.setVisibility(View.GONE);
            rl_clock_blank.setVisibility(View.VISIBLE);
            ll_my_clock.setVisibility(View.GONE);
        }
    }
    void setClockView(MyClock pClock){
        ClockItemView clockItemView = new ClockItemView(activity);
        view_list.add(clockItemView);
        ll_my_clock.addView(clockItemView);
        clockItemView.setData(pClock);
        clockItemView.setListener(this);
    }
    MyClock getClockObject(int pID)
    {
        for (MyClock clock : myClockList) {

            int _id = clock.getGroup_id();
            if (_id == pID) {
                return clock;
            }
        }
        return  null;
    }
    private void setAlarm() {
        showProgress(true);
        try {
            if (BluetoothUtils.checkDeviceConnectStatus()) {
                mTimeOutHandler.postDelayed(mTimeOutRunnable, 15000);
                application.setAlarm(myClockList);
            } else {
                application.scanDevice(Contants.scanbyuuid);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTimePickerExpandedStateChanged(boolean isExpanded,
                                                 MyClock pClock) {
        if (!isExpanded){
            int _x = pClock.getGroup_id();
            for (MyClock _clock : myClockList) {
                int _groupID = _clock.getGroup_id();
                if (_groupID == _x) {
                    _clock.setMin(pClock.getMin());
                    _clock.setHour(pClock.getHour());
                    _clock.setWorkTime(pClock.getWorkTime());
                    break;
                }
            }
            setAlarm();
        }
    }
    @Override
    public void onTunerStateChanged(boolean isChecked, MyClock pClock) {
        int _x = pClock.getGroup_id();
        for (MyClock _clock : myClockList) {
            int _groupID = _clock.getGroup_id();
            if (_groupID == _x) {
                _clock.setOpenorclose(isChecked);
                setAlarm();
            }

        }
    }
    @Subscribe
    public void onEvent(EventNotifacation pEvent) {
        if (pEvent != null) {
            int _type = pEvent.getmEventType();
            if (_type == EventNotifacation.Event_clock_set_ok) {

                UtilLog.e(TAG, "clock set ok ");
                LocalDataUtils.saveClockData(myClockList);
                application.getConfig();

                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showProgress(false);
                            }
                        },1000);
                        mTimeOutHandler.removeCallbacksAndMessages(null);
                        Toast.makeText(mMainActivity.getApplicationContext(), R.string.string_clock_set_ok, Toast.LENGTH_LONG).show();
                    }
                });


            }
        }
    }



    Handler mTimeOutHandler = new Handler();
    Runnable mTimeOutRunnable = new Runnable() {
        @Override
        public void run() {
            UtilLog.e(TAG, "time out ");
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showProgress(false);
                    Toast.makeText(mMainActivity.getApplicationContext(), R.string.string_clock_set_fail, Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    /**
     * @category 蓝牙回调结果
     */
    @Override
    public void handlerUI(Message msg) {
        super.handlerUI(msg);
        int what = msg.what;
        Object obj = msg.obj;
        UtilLog.e(TAG, "what=[" + what + "]; obj=[" + obj + "]");
        switch (what) {
            case BLEDataType.BLE_SCAN_CODE: {

                UtilLog.e(TAG, "BLE_SCAN_CODE");

                BluetoothDevice _Devices = (BluetoothDevice) msg.obj;
                String _mac = _Devices.getAddress();

                UtilLog.e(TAG, "bind mac is " + application.bindMAC + "-->scan " + _mac);
                /**
                 * 判断如果搜索到的设备的mac地址和绑定的设备地址相同，则停止搜索，开始连接。
                 */
                if (_mac.equals(application.bindMAC)) {
                    application.stopScan();
                    if (application.connecting(application.bindMAC)) {

                    } else {
                        UtilLog.e(TAG, "BLE_SCAN_CODE没找到设备");
                        application.close();
                        showProgress(false);
                        Toast.makeText(mMainActivity.getApplicationContext(), R.string.string_clock_set_fail, Toast.LENGTH_LONG).show();
                    }
                    return;
                }

                break;
            }
            case BLEDataType.BLE_SCAN_END: {
                UtilLog.e(TAG, "BLE_SCAN_END没找到设备");
                showProgress(false);
                Toast.makeText(mMainActivity.getApplicationContext(), R.string.string_clock_set_fail, Toast.LENGTH_LONG).show();
                application.close();
                break;
            }
            case BLEDataType.BLE_ISREADY_CODE: {
                UtilLog.e(TAG, "BLEDataType");
                application.setAlarm(myClockList);
                break;
            }

        }
    }


}
