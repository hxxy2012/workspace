package com.hike.digitalgymnastic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.inputmethod.InputMethodManager;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.message.PushAgent;

public class BaseFragmentActivity extends FragmentActivity implements
        INetResult {

    ProgressHUD mProgressHUD;
    HikoDigitalgyApplication application;
    private String TAG = "BaseFragmentActivity";
    public Customer customer;

    @Override
    protected void onCreate(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onCreate(arg0);
        application = (HikoDigitalgyApplication) getApplication();
        HikoDigitalgyApplication.map.put(this.getClass().getName(), this);
        PushAgent.getInstance(this).onAppStart();
        customer = LocalDataUtils.readCustomer(this);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in,
                R.anim.base_slide_remain);
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
        if (HikoDigitalgyApplication.map.containsKey(this.getClass().getName())) {
            HikoDigitalgyApplication.map.remove(this.getClass().getName());
        }
        System.gc();
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
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.base_slide_remain, R.anim.slide_out_right);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        if (this.isFinishing())
            return;
        if (errorNo.equals("0"))
            errorMessage = "网络不给力哦";
        Utils.showMessage(this, errorMessage);
    }

    @Override
    public void onNoConnect() {
        showProgress(false);
        if (this.isFinishing())
            return;
        Utils.showMessage(getApplicationContext(),
                getString(R.string.no_connect_str));
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

    public boolean isShowingProgressDialog() {
        if (mProgressHUD != null) {
            return mProgressHUD.isShowing();
        }
        return false;
    }

    @Override
    public void onResponseReceived(int requestCode) {
        // TODO Auto-generated method stub

    }


    boolean isActive = true;

    @Override
    protected void onResume() {
        super.onResume();
        customer = LocalDataUtils.readCustomer(this);
//        if (!isActive) {
//            //app 从后台唤醒，进入前台
//            Log.d("MyLog", "app 从后台唤醒，进入前台------------------");
//            isActive = true;
//            loginDevice();
//        }

    }
//    private void loginDevice() {
//        // TODO Auto-generated method stub
//        if (!application.isWatchConnected()) {
//            application.scanDevice();
//        } else {
//            application.syncWatchData();
//        }
//    }

    @Override
    public void onRequestSuccess(ResponseInfo responseInfo) {
        if (this.isFinishing())
            return;
    }

    /**
     * 如果有获取焦点的对象，移除焦点，关闭软键盘
     */
    public void forgotFirstRequest() {

        InputMethodManager _inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (this.getCurrentFocus() != null
                && this.getCurrentFocus().getWindowToken() != null) {
            _inputmanger.hideSoftInputFromWindow(this.getCurrentFocus()
                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
