package com.hike.digitalgymnastic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.http.ResponseInfo;
import com.umeng.message.PushAgent;
public class BaseActivity extends Activity implements INetResult{

    ProgressHUD mProgressHUD;

    HikoDigitalgyApplication application;

	private String TAG = "BaseActivity";
    public Customer customer;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);

    	application=(HikoDigitalgyApplication) getApplication();

    	HikoDigitalgyApplication.map.put(this.getClass().getName(), this);
    	 
    	PushAgent.getInstance(this).onAppStart();

        customer = LocalDataUtils.readCustomer(this);


    }
    


    @Override
    public void onRequestSuccess(int requestCode) {

    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(this,false);
        if (this.isFinishing())
			return;
        if(errorNo.equals("0")) {
			errorMessage = getString(R.string.string_less_net);
		}
        Utils.showMessage(this, errorMessage);
    }

    @Override
    public void onNoConnect() {
        showProgress(this,false);
        if (this.isFinishing())
			return;
        Utils.showMessage(getApplicationContext(), getString(R.string.no_connect_str));
    }

    public void showProgress(Context pContext,boolean show) {
        showProgressWithText(pContext,show, getString(R.string.string_loading));
    }

    public void showProgressWithText(Context pContext, boolean show, String message) {
        if (show) {
            mProgressHUD = ProgressHUD.show(pContext, message, true, false, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }
    public boolean isShowingProgressDialog(){
    	if (mProgressHUD != null) {
           return mProgressHUD.isShowing();
        }
    	return false;
    }
	@Override
	public void onResponseReceived(int requestCode) {
	}
	@Override
	protected void onResume() {
		super.onResume();
		UtilLog.e(TAG, " app 从后台唤醒 进入前台");
	}

	@Override
	public void onRequestSuccess(ResponseInfo responseInfo) {
		if (this.isFinishing())
			return;
		
	}
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);

        overridePendingTransition(R.anim.base_slide_right_in,
                R.anim.base_slide_remain);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.base_slide_remain, R.anim.slide_out_right);
    }
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(HikoDigitalgyApplication.map.containsKey(this.getClass().getName())){
			HikoDigitalgyApplication.map.remove(this.getClass().getName());
		}
	}
}
