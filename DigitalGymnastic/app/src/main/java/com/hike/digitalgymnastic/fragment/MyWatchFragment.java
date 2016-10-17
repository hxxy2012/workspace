package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.DeviceListActivity;
import com.hike.digitalgymnastic.DeviceScanActivity;
import com.hike.digitalgymnastic.DeviceUnBindActivity;
import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.UIHandler;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.service.BLEDataType;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.BluetoothUtils;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.DashCircleProgressWatch;
import com.hike.digitalgymnastic.view.DeviceSearchImageView;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

public class MyWatchFragment extends BaseFragment implements UIHandler,
        DeviceSearchImageView.OnTouchListener, DashCircleProgressWatch.OnTouchSyncListener {

    @ViewInject(R.id.root)
    private RelativeLayout root;

    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;

    @ViewInject(R.id.tv_open_watch)
    TextView tv_open_watch;
    @ViewInject(R.id.fl_firmwallversion)
    private RelativeLayout fl_firmwallversion;
    @ViewInject(R.id.tv_watch_updata)
    TextView tv_watch_updata;
    @ViewInject(R.id.iv_firmwallversion)
    ImageView iv_firmwallversion;

    @ViewInject(R.id.iv_power_icon)
    ImageView iv_power_icon;
    @ViewInject(R.id.tv_powerrate)
    TextView tv_powerrate;
    @ViewInject(R.id.ll_watch_connect)
    LinearLayout ll_watch_connect;
    @ViewInject(R.id.ll_watch_state)
    LinearLayout ll_watch_state;
    @ViewInject(R.id.tv_staus)
    TextView tv_staus;
    @ViewInject(R.id.tv_alert)
    TextView tv_alert;
    @ViewInject(R.id.iv_device_connect)
    DeviceSearchImageView iv_device_connect;
    @ViewInject(R.id.iv_CircleProgressWatch)
    DashCircleProgressWatch iv_CircleProgressWatch;

    int requestCodeUnbinder = 100;
    int requestCodeUpdate = 101;
    BaseDao baseDao;
    private String TAG = "MyWatchFragment";
    private static boolean mIsFoundBindDevice = false;
    HikoDigitalgyApplication application;
    public static int curState = 0;// 0：显示电量，1：显示连接设备，2:显示开始同步数据，3：显示正在同步数据
    private MainActivity mMainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_menu_my_watch, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    private void init() {
        baseDao = new BaseDao(this, activity);
        mList = new ArrayList<BluetoothDevice>();
        title.setText("我的手环");
        title.setTextColor(getResources().getColor(R.color.device_rel_text_normal));
        btn_left.setImageResource(R.mipmap.back_login_3x);
        application = (HikoDigitalgyApplication) activity.getApplication();
        application.registerUIHandler(this);
        //当判断 没有绑定蓝牙mac地址的时候，显示搜索设备。
        if (TextUtils.isEmpty(application.bindMAC)) {
            showDeviceSearch();
        } else {
            //当判断系统中存在已经绑定的蓝牙mac地址 显示同步数据界面

            mIsFoundBindDevice = false;
            showSyncInit();

            if (application.bandVersion != null) {
                String lastestVersion = application.bandVersion.getLatestVersion();
                if (application.version > 0 && !TextUtils.isEmpty(lastestVersion)
                        && String.valueOf(application.version).compareToIgnoreCase(lastestVersion) < 0) {
                    //更新固件升级布局
                    iv_firmwallversion.setVisibility(View.VISIBLE);
                } else {
                    //更新固件升级布局
                    iv_firmwallversion.setVisibility(View.INVISIBLE);
                }
            }

        }
        if (!isConfirmed)
            check();
    }

    @Override
    public void handlerUI(Message msg) {
        switch (msg.what) {
            case BLEDataType.BLE_SCAN_CODE: {

                UtilLog.e(TAG, "BLE_SCAN_CODE");

                BluetoothDevice _Devices = (BluetoothDevice) msg.obj;
                String _mac = _Devices.getAddress();
                /**
                 * 去重处理
                 */
                if (!mList.contains(_Devices)) {
                    mList.add(_Devices);
                }

                UtilLog.e(TAG, "bind mac is " + application.bindMAC + "-->scan " + _mac);
                /**
                 * 判断如果搜索到的设备的mac地址和绑定的设备地址相同，则停止搜索，开始连接。
                 */
                if (_mac.equals(application.bindMAC)) {
                    mIsFoundBindDevice = true;
                    showSyncIng(0);
                    application.stopScan();
                    if (application.connecting(application.bindMAC)) {

                    } else {
                        showSyncInit();
                    }
                    return;
                }

                break;
            }
            case BLEDataType.BLE_SCAN_END: {
                /**
                 * 手动搜索结束后回调，能走到此步骤，有两种情况
                 * 1.没有绑定的设备，搜索结束后显示蓝牙列表页面。
                 * 2.有绑定的设备，但是搜索周围没有此设备。
                 */
                UtilLog.e(TAG, "ble scan end");
                /**
                 * 搜索结束后 if 用户没有绑定设备，那就直接跳转到蓝牙列表界面
                 */
                if (TextUtils.isEmpty(application.bindMAC)) {
                    if (curState == 1) {
                        onSearchResult();
                    }
                } else {
                    /**
                     * 搜索结束后 if 用户有绑定的设备，那刚才的搜索是搜索指定的mac，没有找到，要提示同步失败。
                     */
                    if (!mIsFoundBindDevice) {
                        UtilLog.e(TAG, "not found");
                        application.close();
                        showDeviceResearch();
                    }
                }

                break;
            }
            case BLEDataType.BLE_ISREADY_CODE: {
                UtilLog.e(TAG, "BLEDataType");
                application.getRecordCount();//同步数据先获取记录统计信息
                break;
            }
            case BLEDataType.BLE_RECORDCOUNT_CODE:// RecordCount
                UtilLog.e(TAG, "BLE_RECORDCOUNT_CODE");
                Integer rate = (Integer) (msg.obj);
                if (rate == Constants.NONE) {//无数据
                    showSyncFailed();
                } else if (rate == Constants.ISTIMEOUT) {//超时
                    showSyncFailed();
                }
                break;
            case BLEDataType.BLE_SPORTDATA_CODE://  SportData
                UtilLog.e(TAG, "BLE_SPORTDATA_CODE");

                break;
            case BLEDataType.BLE_GETSLEEP_CODE://  SportData
                UtilLog.e(TAG, "BLE_GETSLEEP_CODE");
                showSyncEd();
                showSyncIng(100);
                break;
            default:
                break;
        }

    }


    @OnClick(value = {R.id.btn_left, R.id.ll_btn_left, R.id.tv_open_watch, R.id.tv_watch_updata, R.id.fl_firmwallversion})
    public void onClick(View v) {
        Intent intent = new Intent(activity, DeviceUnBindActivity.class);
        switch (v.getId()) {
            case R.id.btn_left:
                mMainActivity.getManager().popBackStack();
                break;
            case R.id.ll_btn_left:
                mMainActivity.getManager().popBackStack();
                break;

            case R.id.tv_open_watch:
                intent.putExtra("unbind", true);
                startActivityForResult(intent, requestCodeUnbinder);
                break;
            case R.id.tv_watch_updata:
                if (application.bandVersion != null) {
                    if (application.version > 0 && String.valueOf(application.version).compareTo(application.bandVersion.getLatestVersion()) < 0) {
                        intent.putExtra("unbind", false);
                        startActivityForResult(intent, requestCodeUpdate);
                    } else {
                        Utils.showMessage(activity, "手环固件已为最新版本");
                    }
                } else {
                    showProgress(true);
                    baseDao.GetWatchVersion();
                }

                break;
            case R.id.fl_firmwallversion:
                if (application.bandVersion != null) {
                    if (application.version > 0 && String.valueOf(application.version).compareTo(application.bandVersion.getLatestVersion()) < 0) {
                        intent.putExtra("unbind", false);
                        startActivityForResult(intent, requestCodeUpdate);
                    } else {
                        Utils.showMessage(activity, "手环固件已为最新版本");
                    }
                } else {
                    showProgress(true);
                    baseDao.GetWatchVersion();
                }
                break;
            default:
                break;
        }
    }


    boolean isConfirmed = false;

    @Override
    public void onResume() {

        super.onResume();
        if (!this.isHidden()) {
            init();

        } else {
            isConfirmed = false;
            application.unRegisterUIHandler();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            init();
        } else {
            isConfirmed = false;
            application.unRegisterUIHandler();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        UtilLog.e(TAG, "onAttach");
        mMainActivity = (MainActivity)activity;
    }

    @Override
    public void onStop() {
        super.onStop();
        UtilLog.e(TAG, "clear");
        mList.clear();
        application.stopScan();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        UtilLog.e(TAG, "onDetach");
        application.unRegisterUIHandler();
    }

    //显示数据同步初始化
    private void showSyncInit() {
        curState = 2;
        tv_open_watch.setVisibility(View.VISIBLE);
//        fl_firmwallversion.setVisibility(View.VISIBLE);
        ll_watch_state.setVisibility(View.VISIBLE);
        ll_watch_connect.setVisibility(View.GONE);
        iv_CircleProgressWatch.showSynDataInit();
        iv_CircleProgressWatch.setOnTouchListener(this);
    }


    /**
     * @param rate 0-100
     *             显示数据同步中
     */
    private void showSyncIng(int rate) {
        curState = 2;
        tv_open_watch.setVisibility(View.VISIBLE);
//        fl_firmwallversion.setVisibility(View.VISIBLE);
        ll_watch_state.setVisibility(View.VISIBLE);
        ll_watch_connect.setVisibility(View.GONE);
        iv_CircleProgressWatch.showSynDataIng(rate);
    }

    /**
     * 显示数据同步完成
     */
    private void showSyncEd() {
        curState = 2;
        tv_open_watch.setVisibility(View.VISIBLE);
//        fl_firmwallversion.setVisibility(View.VISIBLE);
        ll_watch_state.setVisibility(View.VISIBLE);
        ll_watch_connect.setVisibility(View.GONE);
        iv_CircleProgressWatch.showSynDataEd();
        iv_CircleProgressWatch.postDelayed(new Runnable() {
            @Override
            public void run() {
                showPowerRate();
            }
        }, 1000);
    }

    /**
     * 显示数据同步完成
     */
    private void showSyncFailed() {
        curState = 2;
        tv_open_watch.setVisibility(View.VISIBLE);
//        fl_firmwallversion.setVisibility(View.VISIBLE);
        ll_watch_state.setVisibility(View.VISIBLE);
        ll_watch_connect.setVisibility(View.GONE);
        iv_CircleProgressWatch.showSynDataFailed();
    }

    //显示电量百分比
    private void showPowerRate() {
        curState = 0;
        tv_open_watch.setVisibility(View.VISIBLE);
//        fl_firmwallversion.setVisibility(View.VISIBLE);
        ll_watch_state.setVisibility(View.VISIBLE);
        ll_watch_connect.setVisibility(View.GONE);
        iv_CircleProgressWatch.setPowerRate(application.powerRate, 100);
    }


    //显示搜索设备页面
    private void showDeviceSearch() {
        curState = 1;
        tv_staus.setText("请连接手环");
        tv_open_watch.setVisibility(View.INVISIBLE);
//        fl_firmwallversion.setVisibility(View.INVISIBLE);
        ll_watch_state.setVisibility(View.GONE);
        ll_watch_connect.setVisibility(View.VISIBLE);
        iv_device_connect.initSearch();
        iv_device_connect.setOnTouchListener(new DeviceSearchImageView.OnTouchListener() {
                                                 @Override
                                                 public void onTouch() {
                                                     iv_device_connect.startSearch();
                                                     application.scanDevice(Contants.scanbyuuid);
                                                 }
                                             }
        );
    }

    //未搜索到可用设备显示重新搜索页面
    private void showDeviceResearch() {
        tv_staus.setText("未找到手环");
        mIsFoundBindDevice = false;
        curState = 1;
        tv_open_watch.setVisibility(View.INVISIBLE);
//        fl_firmwallversion.setVisibility(View.INVISIBLE);
        ll_watch_state.setVisibility(View.GONE);
        ll_watch_connect.setVisibility(View.VISIBLE);
        iv_device_connect.stopSearch();
        iv_device_connect.setOnTouchListener(new DeviceSearchImageView.OnTouchListener() {
                                                 @Override
                                                 public void onTouch() {
                                                     iv_device_connect.startSearch();
                                                     application.scanDevice(Contants.scanbyname);
                                                 }
                                             }
        );
    }

    private ArrayList<BluetoothDevice> mList;

    //搜索结果处理
    private void onSearchResult() {

        if (mList.size() == 0) {// 如果搜索失败，显示重新搜索页面
            showDeviceResearch();
        } else {
            showDeviceSearch();
            if (activity != null && !activity.isFinishing() && !this.isDetached()) {
                application.unRegisterUIHandler();
                Intent intent = new Intent(activity,
                        DeviceListActivity.class);
                intent.putParcelableArrayListExtra(Constants.deviceList,
                        mList);
                intent.putExtra(Constants.isRegister, false);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onTouch() {
        if (checkBluetoothState()) {
            Intent intent = new Intent(activity, DeviceScanActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onTouchSyncData() {


        if (checkBluetoothState()) {
            showSyncIng(0);
            try{
                if (BluetoothUtils.checkDeviceConnectStatus()) {
                    curState = 3;
                    application.getRecordCount();//同步数据先获取记录统计信息
                } else {
                    curState = 4;
                    application.scanDevice(Contants.scanbyuuid);
                }
            }catch (Exception e){
                UtilLog.e(TAG,"onTouchSyncData");
                e.printStackTrace();
            }


        }

    }


    /**
     * 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
     */
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    /**
     * 弹出系统弹框提示用户打开 Bluetooth
     */
    private void turnOnBluetooth() {
        // 请求打开 Bluetooth
        Intent requestBluetoothOn = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);


        // 请求开启 Bluetooth
        this.startActivityForResult(requestBluetoothOn,
                REQUEST_CODE_BLUETOOTH_ON);
    }

    private void check() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                isConfirmed = true;
                checkBluetoothState();
            }
        }, 500);
    }

    @SuppressLint("NewApi")
    private boolean checkBluetoothState() {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            BluetoothManager bluetoothManager =
                    (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

            if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                this.turnOnBluetooth();
                return false;
            }
        }
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启
                    UtilLog.e(TAG, "点击确认按钮");
                    application.scanDevice(Contants.scanbyuuid);
                }

                // 点击确认按钮
                case 250: {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启

                    //application.scanDevice();
                }
                break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                }
                break;
                default:
                    break;
            }
        } else if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestCodeUnbinder) {
                showDeviceSearch();
            } else if (requestCode == requestCodeUpdate) {
                // 获取手环的电量信息
            }
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(false);
        if (baseDao.getBandVersion() != null) {
            application.bandVersion = baseDao.getBandVersion();
            Intent intent = new Intent(activity, DeviceUnBindActivity.class);
            if (application.version > 0 && String.valueOf(application.version).compareTo(application.bandVersion.getLatestVersion()) < 0) {
                intent.putExtra("unbind", false);
                startActivityForResult(intent, requestCodeUpdate);
            } else {
                Utils.showMessage(activity, "手环固件已为最新版本");
            }

        }
    }


    public char Hex2Char(byte b) {
        if ((b >= 0) && (b <= 9)) {
            return (char) (b + '0');
        } else {
            return (char) ((b - 0x0A) + 'A');
        }
    }

    public String Bytes2String(byte[] bts) {
        StringBuffer str = new StringBuffer(bts.length * 2);
        for (int i = 0; i < bts.length; i++) {

            str.append(Hex2Char((byte) ((bts[i] >> 4) & 0x0F)));
            str.append(Hex2Char((byte) (bts[i] & 0x0F)));
        }

        return str.toString();
    }


}
