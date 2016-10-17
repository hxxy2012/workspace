package com.hike.digitalgymnastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.MyProgressDialog;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;
/*
 * changqi
 */

@ContentView(R.layout.activity_setpassword)
public class PwdSetActivity extends BaseActivity {
	public static List<Activity> activityList=new ArrayList<Activity>();
	@ViewInject(R.id.et_newpwd)
	EditText et_newpwd;
	@ViewInject(R.id.et_newpwd_confirm)
	EditText et_newpwd_confirm;
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
	@OnClick(value = { R.id.btn_confirm, R.id.btn_left, R.id.btn_right, R.id.ll_btn_left })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_confirm:
			Utils.closeInputMetherUI(PwdSetActivity.this);
			onclick_btn_confirm();
			break;
		case R.id.btn_left:
			finish();
			break;
		case R.id.ll_btn_left:
			finish();
			break;
		default:
			break;
		}
	}
	String mobile;
	String vertifyCode;
	boolean isFormResetPwd;
	MyProgressDialog dialog;
	BaseDao dao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.closeInputMetherUI(this);
		ViewUtils.inject(this);
		init(savedInstanceState);
	}

	private void onclick_btn_confirm() {
		if(check()){
			Log.v("MyLog","isFormResetPwd----------->"+ isFormResetPwd);
			if (isFormResetPwd) {//忘记密码
				dao.ResetPassword(mobile,vertifyCode,Utils.md5(et_newpwd.getText().toString()));
			}else{
				dao.Register(mobile,vertifyCode,Utils.md5(et_newpwd.getText().toString()));//注册
			}
		}
	}

	private boolean check(){
		 if(et_newpwd.getText().toString().length()==0){
			Utils.showMessage(PwdSetActivity.this, getString(R.string.pwdisnull));
			return false;
		}else if(et_newpwd.getText().toString().length()<6||et_newpwd.getText().toString().length()>18){
			Utils.showMessage(PwdSetActivity.this, getString(R.string.newpwd_insert));
			return false;
		}else
			if(et_newpwd_confirm.getText().toString().length()==0){
			Utils.showMessage(PwdSetActivity.this, getString(R.string.newpwd_insert_confirm));
			return false;
		}else if(!et_newpwd.getText().toString().equals(et_newpwd_confirm.getText().toString())){
			Utils.showMessage(PwdSetActivity.this, getString(R.string.error_pwd_input));
			return false;
		}
		return true;
		
	}
	private void init(Bundle savedInstanceState) {
		dao = new BaseDao(this,this);
		if (savedInstanceState != null) {
			mobile = savedInstanceState.getString(Constants.mobile);
			isFormResetPwd = savedInstanceState.getBoolean(Constants.isFormResetPwd);
			vertifyCode = savedInstanceState.getString(Constants.vertifyCode);
		} else {
			mobile = getIntent().getStringExtra(Constants.mobile);
			isFormResetPwd = getIntent().getBooleanExtra(Constants.isFormResetPwd, false);
			vertifyCode = getIntent().getStringExtra(Constants.vertifyCode);
		}
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		if (isFormResetPwd) {
			title.setText(getString(R.string.pwd_resset));
		} else {
			title.setText(getString(R.string.pwd_setting));
		}
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		dialog = new MyProgressDialog(this, R.style.dialogTheme);
	}
	
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(this,false);
		Intent intent = null;
		if (isFormResetPwd) {//忘记密码
			Utils.showMessage(PwdSetActivity.this, getString(R.string.suss_pwd_set));
			intent = new Intent(PwdSetActivity.this, LoginActivity.class);
			for(Activity a:activityList){
				if(a!=null)
					a.finish();
			}
		} else {//注册
			intent = new Intent(PwdSetActivity.this, MainFragment.class);
			LocalDataUtils.saveCustomer(PwdSetActivity.this, dao.getCustomer());
			intent.putExtra("fromWhichPage", MainFragment.fromRegister);
			Utils.showMessage(PwdSetActivity.this, getString(R.string.suss_reg));
			for(Activity a:activityList){
				if(a!=null)
					a.finish();
			}								
		}
		
		startActivity(intent);
		finish();
	}
	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		super.onRequestFaild(errorNo, errorMessage);
		showProgress(this,false);
	}
	
}
