package com.hike.digitalgymnastic.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hike.digitalgymnastic.DeviceScanActivity;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.MainFragment;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyGooalFrament extends BaseFragment implements View.OnClickListener{
	private int stepRate = 26;
	private float runRate = (float) 0.018;
	private float SwipRate = (float) 0.11;
//	@ViewInject(R.id.tv_target)
//	private TextView tv_target;
//	@ViewInject(R.id.iv_targetSetImageView)
//	private TargetSetImageView iv_targetSetImageView;
//	@ViewInject(R.id.tv_target_walk)
//	private TextView tv_target_walk;
//	@ViewInject(R.id.tv_target_run)
//	private TextView tv_target_run;
//	@ViewInject(R.id.tv_target_swip)
//	private TextView tv_target_swip;
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@ViewInject(R.id.top)
	private View top;
	
	
	@ViewInject(R.id.btn_finish)
	private Button btn_finish;
	
	private BaseDao dao;
	private int goalCalories ;
	private Customer changecustomer;

	@ViewInject(R.id.kcal_value)
	private TextView kcal_value;
	@ViewInject(R.id.tv_step)
	private TextView tv_step;
	@ViewInject(R.id.tv_run)
	private TextView tv_run;
	@ViewInject(R.id.tv_swim)
	private TextView tv_swim;
	@ViewInject(R.id.seek_bar)
	private SeekBar seek_bar;



	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left,R.id.btn_finish})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			btn_back();
			break;
		case R.id.ll_btn_left:
			btn_back();
			
			break;
			
		case R.id.btn_finish:
			if(goalCalories>0){
				showProgress(true);
				changecustomer=LocalDataUtils.readCustomer(activity);
				if(activity instanceof MainFragment)
				{
					changecustomer.setGender(String.valueOf(((MainFragment) activity).getGender()));
					changecustomer.setYear(String.valueOf(((MainFragment) activity).getYear()));
					changecustomer.setHeight(String.valueOf(((MainFragment) activity).getHeight()));
					changecustomer.setWeight(String.valueOf(((MainFragment) activity).getWeight()));
					changecustomer.setName(String.valueOf(((MainFragment) activity).getName()));
					SimpleDateFormat format = new SimpleDateFormat("yyyy");
					String currentYear = format.format(new Date());
					PreferencesUtils.putInt((MainFragment) activity,"customer_age",Integer.parseInt(currentYear)-((MainFragment) activity).getYear());
				}
				if(activity instanceof MainMenuActivity){
					
				}
			    changecustomer.setGoalCalories(goalCalories+"");
				dao.ModifyCustomer(changecustomer);
				
			}else{
				Utils.showMessage(activity, "请选择目标");
			}
			break;
		default:
			break;
		}
	}
	View mView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.mView = inflater.inflate(R.layout.activity_targetset, container, false);
		ViewUtils.inject(this,mView);
		return mView;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
		initData();
	}
	public void initView(){


		kcal_value=	(TextView) mView.findViewById(R.id.kcal_value);
		tv_step=(TextView) mView.findViewById(R.id.tv_step);
		tv_run=(TextView) mView.findViewById(R.id.tv_run);
		tv_swim=(TextView) mView.findViewById(R.id.tv_swim);
		  btn_left=(ImageView) mView.findViewById(R.id.btn_left);
		  title=(TextView) mView.findViewById(R.id.title);
		  ll_btn_left=(LinearLayout) mView.findViewById(R.id.ll_btn_left);
		  ll_btn_right=(LinearLayout) mView.findViewById(R.id.ll_btn_right);
		  top=mView.findViewById(R.id.top);
		  btn_finish=(Button) mView.findViewById(R.id.btn_finish);

		  ll_btn_left.setOnClickListener(this);
		  btn_finish.setOnClickListener(this);
	}
	private void initData() {
		btn_left.setImageResource(R.mipmap.back_login_3x);
		title.setText("每日运动目标");
		title.setTextColor(getResources().getColor(R.color.umeng_socialize_text_share_content));
		dao = new BaseDao(this,activity);
		String goalCaloriesValue=null;
		
		if(activity instanceof MainMenuActivity){
			goalCaloriesValue=((MainMenuActivity)activity).getGoalCalories();
		}

		
		if(TextUtils.isEmpty(goalCaloriesValue)){
			goalCaloriesValue="500";
		}

//		goalCalories =Integer.parseInt(goalCaloriesValue);
		BigDecimal dd = new BigDecimal(goalCaloriesValue).setScale(0, BigDecimal.ROUND_HALF_UP);
		goalCalories = dd.intValue();
		int kcalValue= goalCalories;
		kcal_value.setText(kcalValue+"");

		DecimalFormat decimalFormat = new DecimalFormat("#.#");
		tv_step.setText(String.valueOf(kcalValue * stepRate)+ "步");
		tv_run.setText(String.valueOf(decimalFormat.format(kcalValue * runRate)) + "km");
		tv_swim.setText(String.valueOf(decimalFormat.format(kcalValue * SwipRate)) + "分钟");

		seek_bar.setMax(1500);
		seek_bar.setProgress(500);
		seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				goalCalories = progress;
				DecimalFormat decimalFormat = new DecimalFormat("#.#");
				kcal_value.setText(String.valueOf(progress));
				tv_step.setText(String.valueOf(progress * stepRate) + "步");
				tv_run.setText(String.valueOf(decimalFormat.format(progress * runRate)) + "km");
				tv_swim.setText(String.valueOf(decimalFormat.format(progress * SwipRate)) + "分钟");
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});

	}
	
	@Override
	public void onNoConnect() {
		// TODO Auto-generated method stub
		super.onNoConnect();
		showProgress(false);
	}
	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
		showProgress(false);
	}
	public void onRequestSuccess(int requestCode) {
		showProgress(false);
		if (requestCode == 9) {
			if(activity instanceof MainMenuActivity){
				Utils.showMessage(activity, "修改成功");
				LocalDataUtils.saveCustomer(activity, changecustomer);
				((MainMenuActivity)activity).fragmentBack();
			}
			if(activity instanceof MainFragment){//注册之后进入方式
				Utils.showMessage(activity, "提交成功！");
				LocalDataUtils.saveCustomer(activity, changecustomer);
				Intent intent = new Intent(activity,DeviceScanActivity.class);
				if(android.os.Build.VERSION.SDK_INT<18){
					intent = new Intent(activity,MainActivity.class);
				}else{
					intent.putExtra(Constants.customer, changecustomer);
					intent.putExtra(Constants.isRegister, true);
				}
				startActivity(intent);
				activity.finish();
			}
			
		}
	}
	
	public void btn_back(){
		if(activity instanceof MainMenuActivity){
			((MainMenuActivity)activity).fragmentBack();
		}
		if(activity instanceof MainFragment){
			((MainFragment)activity).exitTargetAtivity();
		}
	}
}
