package com.hike.digitalgymnastic;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.FirewallUpdateView;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

/*
 * changqi
 */
@ContentView(R.layout.activity_deviceunbind)
public class DeviceUnBindActivity extends BaseActivity implements UIHandler {
    @ViewInject(R.id.fr_root)
    FrameLayout fr_root;

    // 升级状态布局开始
    @ViewInject(R.id.ll_bond_update)
    LinearLayout ll_bond_update;
    @ViewInject(R.id.iv_firmwallupdat)
    FirewallUpdateView iv_firmwallupdat;
    @ViewInject(R.id.tv_update_result)
    TextView tv_update_result;

    @ViewInject(R.id.btn_retry)
    Button btn_retry;

    // 升级状态布局结束

    @ViewInject(R.id.btn_unbind)
    Button btn_unbind;
    @ViewInject(R.id.btn_unbind_cancel)
    Button btn_unbind_cancel;
    @ViewInject(R.id.btn_update)
    Button btn_update;
    @ViewInject(R.id.btn_update_cancel)
    Button btn_update_cancel;


    @ViewInject(R.id.rl_unbind)
    RelativeLayout rl_unbind;
    @ViewInject(R.id.rl_sync_data)
    RelativeLayout rl_sync_data;
    @ViewInject(R.id.rl_bond_update)
    RelativeLayout rl_bond_update;
    private String TAG = "DeviceUnBindActivity";

    @OnClick(value = {R.id.fr_root, R.id.btn_unbind, R.id.btn_unbind_cancel,
            R.id.btn_update, R.id.btn_update_cancel})
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fr_root:
                if (isAllowFinish) {
                    finish();
                }

                break;
            case R.id.btn_unbind:
                if (checkBluetoothState())
                    unbind();
                break;
            case R.id.btn_unbind_cancel:
                finish();
                break;
            case R.id.btn_update:
                if (checkBluetoothState())
                    update();
                break;
            case R.id.btn_update_cancel:
                updateCancel();
                break;
            default:
                break;
        }
    }

    HikoDigitalgyApplication application;
    Customer customer;
    boolean isAllowFinish = true;
    BaseDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ViewUtils.inject(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        init();

    }

    boolean unbind;

    private void init() {
        dao = new BaseDao(this, this);
        application = (HikoDigitalgyApplication) getApplication();
        application.registerUIHandler(this);
        customer = LocalDataUtils.readCustomer(this);
        unbind = getIntent().getBooleanExtra("unbind", true);
        if (unbind) {
            rl_unbind.setVisibility(View.VISIBLE);
            rl_sync_data.setVisibility(View.GONE);
        } else {
            rl_unbind.setVisibility(View.GONE);
            rl_bond_update.setVisibility(View.GONE);
            rl_sync_data.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        application.unRegisterUIHandler();
    }

    private void unbind() {
        showProgress(this,true);
        dao.UnBindWach(Utils.disposeAdress(LocalDataUtils.getBindTag(this)));
        application.close();
        application.unBinder();
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        showProgress(this,false);
        Utils.showMessage(this, "解绑成功！");
        LocalDataUtils.saveBindMAC(this, null);
        LocalDataUtils.saveBindName(this, null);
        LocalDataUtils.saveConnectedInfo(this, false);
        //用户解除绑定
        application.unBinder();
        Intent intent = new Intent();
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        // TODO Auto-generated method stub
        super.onRequestFaild(errorNo, errorMessage);
        Utils.showMessage(this, "解绑失败！");
    }

    private void update() {
        isAllowFinish = false;
        rl_bond_update.setVisibility(View.VISIBLE);
        rl_sync_data.setVisibility(View.GONE);
        iv_firmwallupdat.setText(0, 100);
        tv_update_result.setText("正在升级固件...");
        application.registerUIHandler(this);
        application.startBondUpdate();
    }

    private void updateCancel() {
        isAllowFinish = true;
        finish();
    }

    @Override
    public void handlerUI(Message msg) {


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isAllowFinish)
                return super.onKeyDown(keyCode, event);
            else
                return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @SuppressLint("NewApi")
    private boolean checkBluetoothState() {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            BluetoothManager bluetoothManager =
                    (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();

            boolean isEnabled = bluetoothAdapter.isEnabled();
            if (isEnabled) {
                return true;
            } else {
                Utils.showMessage(this, "蓝牙连接已断开");
                return false;
            }
        }
        return false;
    }
}
