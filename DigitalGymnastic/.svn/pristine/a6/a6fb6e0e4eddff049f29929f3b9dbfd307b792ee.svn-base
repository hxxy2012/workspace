package com.hike.digitalgymnastic;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.TargetSetImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * changqi
 */

@ContentView(R.layout.activity_targetset)
public class TargetSetActivity extends BaseActivity {
	public static List<Activity> activityList = new ArrayList<Activity>();
	int stepRate = 26;
	float runRate = (float) 0.018;
	float SwipRate = (float) 0.11;

	private BaseDao dao;
	private int gender;
	private int year;
	private int height;
	private double weight;
	private String imgpath;
	private String name;

	@ViewInject(R.id.iv_targetSetImageView)
	TargetSetImageView iv_targetSetImageView;
//	@ViewInject(R.id.tv_title_center)
//	TextView tv_title_center;
	@ViewInject(R.id.tv_target)
	TextView tv_target;

	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	@ViewInject(R.id.btn_finish)
	Button btn_finish;

	@ViewInject(R.id.tv_target_walk)
	TextView tv_target_walk;
	@ViewInject(R.id.tv_target_run)
	TextView tv_target_run;
	@ViewInject(R.id.tv_target_swip)
	TextView tv_target_swip;

	@OnClick(value = { R.id.btn_left, R.id.btn_right, R.id.btn_finish,R.id.ll_btn_left })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			finish();
			break;
		case R.id.ll_btn_left:
			finish();
			break;
			
		case R.id.btn_finish:
			customer.setGoalCalories(tv_target.getText().toString());
			dao.ModifyCustomer(customer);
			showProgressWithText(this,true, "正在提交....");
			break;
		default:
			break;
		}
	}

	Customer customer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.closeInputMetherUI(this);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		dao = new BaseDao(this, this);
		title.setText(getString(R.string.target_day));
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		customer = getIntent().getParcelableExtra(Constants.customer);

		gender = getIntent().getIntExtra("gender", 1);
		year = getIntent().getIntExtra("year", 1985);
		height = getIntent().getIntExtra("height", 175);
		weight = getIntent().getDoubleExtra("weight", 75.0);
		imgpath = getIntent().getStringExtra("imgpath");
		name = getIntent().getStringExtra("name");

		customer.setGender(String.valueOf(gender));
		customer.setYear(String.valueOf(year));
		customer.setHeight(String.valueOf(height));
		customer.setWeight(String.valueOf(weight));
		customer.setAvatar(String.valueOf(imgpath));
		customer.setName(String.valueOf(name));
//		title.setText("每日目标");

		iv_targetSetImageView.setNotifyDataListener(new TargetSetImageView.NotifyDataListener() {
					@Override
					public void notifyData(int value) {
						DecimalFormat decimalFormat = new DecimalFormat("#.#");
						tv_target.setText(String.valueOf(value));
						tv_target_walk.setText(String.valueOf(value * stepRate)+ "步");
						tv_target_run.setText(String.valueOf(decimalFormat.format(value * runRate)) + "km");
						tv_target_swip.setText(String.valueOf(decimalFormat.format(value * SwipRate)) + "分钟");
					}
				});
		// 需要在设置回调监听之后调用才能刷新页面
		if (!TextUtils.isEmpty(customer.getGoalCalories()))
			iv_targetSetImageView.init(Double.parseDouble(customer.getGoalCalories()));
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(this,false);
		Utils.showMessage(TargetSetActivity.this, "提交成功！");
		for (Activity a : activityList) {
			if (a != null)
				a.finish();
		}
		LocalDataUtils.saveCustomer(TargetSetActivity.this, customer);
		Intent intent = new Intent(TargetSetActivity.this,DeviceScanActivity.class);
		intent.putExtra(Constants.customer, customer);
		startActivity(intent);
		finish();
	}
}
