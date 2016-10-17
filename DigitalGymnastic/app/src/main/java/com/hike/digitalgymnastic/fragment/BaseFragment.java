package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.hike.digitalgymnastic.BaseFragmentActivity;
import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.UIHandler;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.http.ResponseInfo;

public class BaseFragment extends Fragment implements INetResult,UIHandler{

    private ProgressHUD mProgressHUD;
    protected LayoutInflater inflater;
	protected Activity activity;
	public  Customer customer;
	public String imageCachePath;

	HikoDigitalgyApplication application;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		application=(HikoDigitalgyApplication) activity.getApplication();
	}
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.inflater = activity.getLayoutInflater();
		this.activity = activity;
		customer = ((BaseFragmentActivity) activity).getCustomer();
		activity.overridePendingTransition(R.anim.base_slide_right_in,
				R.anim.base_slide_remain);
	}
	@Override
	public void onDetach() {
		super.onDetach();
	}
	@Override
	public void onResume() {
		super.onResume();

	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if (!hidden) {
			customer = ((BaseFragmentActivity) activity).getCustomer();
		}
	}
	public  void notifyUpdateTitle(){

	}
	public static BaseFragment newInstance(Context context,String tag){
		BaseFragment baseFragment =  null;
		//个人信息
		if(TextUtils.equals(tag, context.getString(R.string.year))){
			baseFragment = new YearFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.gender))){
			baseFragment = new GenderFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.height))){
			baseFragment = new HeightFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.weight))){
			baseFragment = new WeightFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.pichead))){
			baseFragment = new PicHeadFragment();
		}
		//menu
		else if (TextUtils.equals(tag, context.getString(R.string.menu_frament))) {
			baseFragment = new MenuFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.menu_my_watch))){
			baseFragment = new MyWatchFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.menu_message))){
			baseFragment = new MessageFrament();
		}else if(TextUtils.equals(tag, context.getString(R.string.menu_set))){
			baseFragment = new SetFragment();
		}else if(TextUtils.equals(tag, context.getString(R.string.menu_feedback))){
			baseFragment = new FeedBackFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.menu_my_goal))) {
			baseFragment = new MyGooalFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.menu_my_message))) {
//			baseFragment = new MyMessageFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.menu_my_clock))) {
//			baseFragment = new MyClockFrament();
		}else if (TextUtils.equals(tag, context.getString(R.string.menu_add_my_clock))) {
			baseFragment = new AddClockFrament();
		}else if (TextUtils.equals(tag, context.getString(R.string.sit_long_alarm))) {
			baseFragment = new SitLongTimeFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.body_test_alarm))) {
			baseFragment = new BodyStateTestFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.sport_alarm))) {
			baseFragment = new SportAlarmFrament();
		} else if (TextUtils.equals(tag, context.getString(R.string.about_us))) {
			baseFragment = new AboutUsFrament();
		}

		//主页
		else if (TextUtils.equals(tag, context.getString(R.string.home_frament))) {
			baseFragment = new HomeFragment();
		}
		return baseFragment;
	}
    @Override
    public void onRequestSuccess(int requestCode) {
    	 showProgress(false);
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        showProgress(false);
        if (activity == null
				|| activity.isFinishing())
			return;
        Utils.showMessage(activity, errorMessage);
    }

    @Override
    public void onNoConnect() {
        showProgress(false);
        if (activity == null
				|| activity.isFinishing())
			return;
    	Utils.showMessage(activity, getString(R.string.no_connect_str));
    }

    public void showProgress(boolean show) {
        showProgressWithText(show, "加载中...");
    }
    public void showProgress(boolean show,boolean cancelable) {
        showProgressWithText(show, "加载中...",cancelable);
    }
    public synchronized void showProgressWithText(boolean show, String message) {
		if(activity==null||activity.isFinishing())
			return ;
        if (show) {
            mProgressHUD = ProgressHUD.show(activity, message, true, false, null);
        } else {
            if (mProgressHUD != null) {
                mProgressHUD.dismiss();
            }
        }
    }

    public void showProgressWithText(boolean show, String message,boolean cancelable) {
        if (show) {
            mProgressHUD = ProgressHUD.show(activity, message, true, cancelable, null);
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
	public void handlerUI(Message msg) {

	}
	@Override
	public void onRequestSuccess(ResponseInfo responseInfo) {

	}
}
