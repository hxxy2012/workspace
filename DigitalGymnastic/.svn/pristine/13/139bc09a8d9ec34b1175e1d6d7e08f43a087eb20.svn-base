package com.hike.digitalgymnastic;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.Timer;
import java.util.TimerTask;
/*
 * changqi
 */

@ContentView(R.layout.activity_validcode)
public class ValidCodeActivity extends BaseActivity implements OnClickListener{
	@ViewInject(R.id.tv_alarm)
	TextView tv_alarm;
	@ViewInject(R.id.et_code)
	EditText et_code;

	@ViewInject(R.id.btn_identifying_code)
	Button btn_identifying_code;

	@ViewInject(R.id.btn_confirm)
	Button btn_confirm;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;

	@ViewInject(R.id.ll_ss)
	private LinearLayout ll_ss;
	@ViewInject(R.id.tv_ss)
	private TextView tv_ss;
	@ViewInject(R.id.tv_ss_last)
	private TextView tv_ss_last;
	
	private boolean isWaiting = false;
	private int peroid = 60;
	private Timer timer;
	private TimerTask task;
	private String mobile;
	private String vertifyCode;
	private boolean isFormResetPwd;
	
	private BaseDao dao;
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_confirm:
			Utils.closeInputMetherUI(this);
			String code = et_code.getText().toString();
			if(!TextUtils.isEmpty(code)&&code.trim().length()==4){
				showProgress(this,true);
				vertifyCode=code;
				dao.CheckVerifyCode(mobile, "1", code);
			}else if(TextUtils.isEmpty(code)){
				Utils.showMessage(ValidCodeActivity.this,getString(R.string.validcode_input_blank));
			}else{
				Utils.showMessage(ValidCodeActivity.this,getString(R.string.validcode_input_error));
			}
			break;
		case R.id.btn_left:
			Utils.closeInputMetherUI(this);
			finish();
			break;
		case R.id.ll_btn_left:
			Utils.closeInputMetherUI(this);
			finish();
			break;
			
		case R.id.btn_identifying_code:
			Utils.closeInputMetherUI(this);
			if (!isWaiting) {
				beforeSendValidCodeState();
				String type ;
				if(isFormResetPwd)
					    type = Constants.isForgetPwd;
					else
						type = Constants.isRegister;
				showProgress(this,true);
				dao.GetVerifyCode(mobile,type);
			}
			break;
		default:
			break;
		}
	}

	class MyTimerTask extends TimerTask {

		@Override
		public void run() {
			Message msg = new Message();
			msg.what = 0;
			handler.sendMessage(msg);
		}
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (peroid < 0) {
				ll_ss.setVisibility(View.GONE);
				btn_identifying_code.setVisibility(View.VISIBLE);
				btn_identifying_code.setBackgroundResource(R.mipmap.resend_3x);
				btn_identifying_code.setTextColor(getResources().getColor(R.color.light_green));
				btn_identifying_code.setText(getString(R.string.resend));

				tv_alarm.setTextColor(getResources().getColor(R.color.grey_ae));
				peroid = 60;
				isWaiting = false;
				if (timer != null)
					timer.cancel();

			} else {
				btn_identifying_code.setVisibility(View.GONE);
				ll_ss.setVisibility(View.VISIBLE);
				tv_ss.setTextColor(getResources().getColor(R.color.light_green));
				tv_ss.setText(peroid + "");
				tv_ss_last.setTextColor(getResources().getColor(R.color.grey_ae));
				tv_ss_last.setText(getString(R.string.validcode_resend));
//				btn_identifying_code.setBackgroundResource(R.mipmap.btn_resend_wating);
//				btn_identifying_code.setText(peroid + getString(R.string.validcode_resend));
//				btn_identifying_code.setTextColor(getResources().getColor(R.color.light_green));

				tv_alarm.setTextColor(getResources().getColor(R.color.light_green));
				peroid--;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.closeInputMetherUI(this);
		ViewUtils.inject(this);
		PwdSetActivity.activityList.add(this);
		init(savedInstanceState);
	}

	private void init(Bundle savedInstanceState) {

		dao = new BaseDao(this,this);
		if(savedInstanceState!=null){
			mobile=savedInstanceState.getString(Constants.mobile);
			vertifyCode=savedInstanceState.getString(Constants.vertifyCode);
			isFormResetPwd=savedInstanceState.getBoolean(Constants.isFormResetPwd);
		}else{
			mobile=getIntent().getStringExtra(Constants.mobile);
			isFormResetPwd=getIntent().getBooleanExtra(Constants.isFormResetPwd, false);
			vertifyCode=getIntent().getStringExtra(Constants.vertifyCode);
			afterSendValidCodeState();
		}
		title.setText(getString(R.string.validcode_input));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		btn_confirm.setOnClickListener(null);
		btn_confirm.setBackgroundResource(R.mipmap.btn_disabled);
		btn_left.setOnClickListener(this);
		ll_btn_left.setOnClickListener(this);
		btn_identifying_code.setOnClickListener(this);
		btn_confirm.setOnClickListener(ValidCodeActivity.this);
		btn_confirm.setBackgroundResource(R.drawable.btn_selector);
//		et_code.addTextChangedListener(new TextWatcher() {
//			
//			@Override
//			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				
//			}
//			
//			@Override
//			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
//				
//			}
//			
//			@Override
//			public void afterTextChanged(Editable editable) {
//				if(editable.toString().length()==4){
//					btn_confirm.setOnClickListener(ValidCodeActivity.this);
//					btn_confirm.setBackgroundResource(R.mipmap.btn_selector);
//					
//				}else{
//					btn_confirm.setOnClickListener(null);
//					btn_confirm.setBackgroundResource(R.mipmap.btn_disabled);
//				}
//			}
//		});
		
//		Utils.showMessage(this, vertifyCode);
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(Constants.mobile, mobile);
		outState.putString(Constants.vertifyCode, vertifyCode);
		outState.putBoolean(Constants.isFormResetPwd, isFormResetPwd);
	}
	private void beforeSendValidCodeState(){
//		tv_alarm.setVisibility(View.INVISIBLE);
	}
	
	private void afterSendValidCodeState(){
		tv_alarm.setVisibility(View.VISIBLE);
		tv_alarm.setTextColor(getResources().getColor(R.color.grey_ae));
		tv_alarm.setText(getString(R.string.validcode_sended)+mobile);
		timer = new Timer();
		task = new MyTimerTask();
		btn_identifying_code.setBackgroundResource(R.mipmap.btn_resend_wating);
		timer.schedule(task, 1000, 1000);
		isWaiting = true;
	}
	//成功获取数据
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(this,false);
		if (requestCode == 67) {
			Intent intent=new Intent(ValidCodeActivity.this,PwdSetActivity.class);
			intent.putExtra(Constants.mobile, mobile);
			intent.putExtra(Constants.isFormResetPwd, isFormResetPwd);
			intent.putExtra(Constants.vertifyCode, et_code.getText().toString());
			startActivity(intent);
		}else if(requestCode==66){
			Utils.showMessage(getBaseContext(), "发送成功！");
			afterSendValidCodeState();
		}
	}
	//获取数据失败
	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		super.onRequestFaild(errorNo, errorMessage);
		showProgress(this,false);
//		afterSendValidCodeState();
		if(errorNo.equals("3")){
			try {
				
				Intent intent =new Intent(this,ValidCodeImageActivity.class);
				intent.putExtra("phone", mobile);
				intent.putExtra("isFormResetPwd", isFormResetPwd);
				startActivity(intent);
				finish();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			Utils.showMessage(getApplicationContext(), errorMessage);
		}
		
	}
}
