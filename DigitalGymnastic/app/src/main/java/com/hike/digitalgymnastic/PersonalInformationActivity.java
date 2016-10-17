package com.hike.digitalgymnastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.view.WeightRingView;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/*
 * @auth  changqi
 * @description 
 */

@ContentView(R.layout.activity_weigth_add)
public class PersonalInformationActivity extends BaseActivity implements View.OnClickListener {
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right_text)
	Button btn_right_text;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@ViewInject(R.id.view_weight)
	private WeightRingView view_weight;

	@OnClick(value={R.id.btn_left, R.id.btn_right_text,R.id.ll_btn_left,R.id.ll_btn_right})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
				finish();
			break;
		case R.id.ll_btn_left:
			finish();
		break;

		case R.id.btn_right_text:
			Intent intent =new Intent();
			intent.putExtra("value", Double.parseDouble(view_weight.getCurrentValue()+""));
			System.out.println("weightValue==" + Double.parseDouble(view_weight.getCurrentValue() + ""));

			setResult(Activity.RESULT_OK, intent);
			finish();
			break;
		default:
			break;
		}

	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ViewUtils.inject(this);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	Customer customer;
	private void init() {
//		title.setText(getString(R.string.per_info));
//		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
//		btn_left.setImageResource(R.mipmap.back_login_3x);
//		btn_left.setVisibility(View.VISIBLE);
//		btn_right_text.setVisibility(View.INVISIBLE);
		customer = (Customer) LocalDataUtils.readCustomer(this);
		title.setText("新增体重");
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setOnClickListener(this);
		btn_right_text.setVisibility(View.VISIBLE);
		btn_right_text.setText("提交");
		btn_right_text.setTextColor(this.getResources().getColor(R.color.light_green));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		String weightValue = customer.getWeight();
		if(!TextUtils.isEmpty(weightValue)){
			view_weight.setCurrentValue(Float.parseFloat(weightValue));
		}else {
			if (!TextUtils.isEmpty(customer.getGender())) {
				int gender = Integer.parseInt(customer.getGender());
				if (gender == 1) {// 男
					view_weight.setCurrentValue(Constants.defaultMaleWeightint);
				} else {// 女
					view_weight.setCurrentValue(Constants.defaultSexWeightint);
				}
			}
		}
	}
	

}
