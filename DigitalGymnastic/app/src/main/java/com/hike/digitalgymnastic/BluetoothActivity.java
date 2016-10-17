package com.hike.digitalgymnastic;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.message.PushAgent;

import java.lang.reflect.Method;

//import com.crashlytics.android.Crashlytics;
//import io.fabric.sdk.android.Fabric;
//import com.crashlytics.android.Crashlytics;
public class BluetoothActivity extends FragmentActivity implements INetResult {
    ProgressHUD mProgressHUD;
    HikoDigitalgyApplication application;
    private String TAG ="BluetoothActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		registerBoradcastReceiver();
//		Fabric.with(this, new Crashlytics());
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        application = (HikoDigitalgyApplication) getApplication();
        HikoDigitalgyApplication.map.put(this.getClass().getName(), this);
        PushAgent.getInstance(this).onAppStart();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (HikoDigitalgyApplication.map.containsKey(this.getClass().getName())) {
            HikoDigitalgyApplication.map.remove(this.getClass().getName());
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (HikoDigitalgyApplication.map.containsKey(this.getClass().getName())) {
            HikoDigitalgyApplication.map.remove(this.getClass().getName());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private void registerBoradcastReceiver() {
        IntentFilter stateChangeFilter = new IntentFilter(
                BluetoothAdapter.ACTION_STATE_CHANGED);
        IntentFilter connectedFilter = new IntentFilter(
                BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter disConnectedFilter = new IntentFilter(
                BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(stateChangeReceiver, stateChangeFilter);
        registerReceiver(stateChangeReceiver, connectedFilter);
        registerReceiver(stateChangeReceiver, disConnectedFilter);
    }

    private BroadcastReceiver stateChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            }
            if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            }
            if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {

                if (checkState()) {

                }
            }
        }
    };

    private boolean checkState() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "没有找到蓝牙硬件或驱动！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "蓝牙已关闭！", Toast.LENGTH_SHORT).show();
            return false;
        } else if (mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "蓝牙已开启！", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private void openBlueTools() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter
                .getDefaultAdapter();
        // 如果本地蓝牙没有开启，则开启
        if (!mBluetoothAdapter.isEnabled()) {
            // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，  
            // 那么将会收到RESULT_OK的结果，  
            // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙  
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(mIntent, 1);
            // 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。  
            // mBluetoothAdapter.enable();  
            // mBluetoothAdapter.disable();//关闭蓝牙  
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "蓝牙已经开启", Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "不允许蓝牙开启", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        if (errorNo.equals("0"))
            errorMessage = "网络不给力哦";
        Utils.showMessage(this, errorMessage);
    }

    @Override
    public void onNoConnect() {
        showProgress(false);
        Utils.showMessage(this, getString(R.string.no_connect_str));
    }

    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }

    public void showProgressWithText(boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(this, message, true, false, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }

    @Override
    public void onRequestSuccess(int requestCode) {

    }

    @Override
    public void onResponseReceived(int requestCode) {
        // TODO Auto-generated method stub

    }


    @Override
    protected void onResume() {
        super.onResume();
    }



    /**
     * Recursively sets a {@link Typeface} to all
     * {@link TextView}s in a {@link ViewGroup}.
     */
    public static final void setAppFont(ViewGroup mContainer, Typeface mFont, boolean reflect) {
        if (mContainer == null || mFont == null) return;

        final int mCount = mContainer.getChildCount();

        // Loop through all of the children.
        for (int i = 0; i < mCount; ++i) {
            final View mChild = mContainer.getChildAt(i);
            if (mChild instanceof TextView) {
                // Set the font if it is a TextView.
                ((TextView) mChild).setTypeface(mFont);
            } else if (mChild instanceof ViewGroup) {
                // Recursively attempt another ViewGroup.
                setAppFont((ViewGroup) mChild, mFont, true);
            } else if (reflect) {
                try {
                    Method mSetTypeface = mChild.getClass().getMethod("setTypeface", Typeface.class);
                    mSetTypeface.invoke(mChild, mFont);
                } catch (Exception e) { /* Do something... */ }
            }
        }
    }

    @Override
    public void onRequestSuccess(ResponseInfo responseInfo) {
        // TODO Auto-generated method stub

    }
}
