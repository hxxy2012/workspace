package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.MainFragment;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.view.WeightRingView;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class WeightFragment extends BaseFragment {
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.btn_right_text)
	private Button btn_right_text;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	
	
	@ViewInject(R.id.root)
	private LinearLayout root_age;
	@ViewInject(R.id.top_ly)
	private LinearLayout top_ly;
	@ViewInject(R.id.btn_next_four)
	private Button btn;
	
	@ViewInject(R.id.view_weight)
	private WeightRingView view_weight;
	
	
	private View v;
	private int gender;
	private String weightstr;
	private float weightvalue;
//	private DiverRuleWeightLayout diverRuleWeightLayout;

	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.btn_next_four, R.id.btn_right_text })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			submit();
//			((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.ll_btn_left:
			submit();
//			((MainMenuActivity) activity).fragmentBack();
			break;
			
		case R.id.btn_right_text:
//			((MainMenuActivity) activity).setWeight(diverRuleWeightLayout.currentWeight + "");// 数据提交给主activity
//			((MainMenuActivity) activity).setWeight(view_weight.currentValue + "");// 数据提交给主activity
			break;
		case R.id.btn_next_four:
//			((MainFragment) activity).setWeight(diverRuleWeightLayout.currentWeight);
			((MainFragment) activity).setWeight(view_weight.getCurrentValue() );
			break;

		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.personalinfo_four, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	private void init() {
//		diverRuleWeightLayout = new DiverRuleWeightLayout(activity);
//		root_age.addView(diverRuleWeightLayout);
//		diverRuleWeightLayout.init();

		title.setText(getString(R.string.per_info));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right_text.setVisibility(View.INVISIBLE);
		changeData();
	}

	private void changeData() {
		if (activity instanceof MainFragment) {
			gender = ((MainFragment) activity).getGender();
			weightstr = ((MainFragment) activity).getCustomer().getWeight();
			weightvalue =Float.parseFloat(weightstr);
			btn.setVisibility(View.VISIBLE);
		}
		if (activity instanceof MainMenuActivity ) {
			gender = Integer.parseInt(((MainMenuActivity) activity).getGender());
			weightstr = ((MainMenuActivity) activity).getWeight();
			weightvalue = Float.parseFloat(weightstr);
			top_ly.setVisibility(View.VISIBLE);
		}
		if (gender == 1) {// 男
			Log.v("MyLog", "wwwwww男");
			if (TextUtils.isEmpty(weightstr)||TextUtils.equals("0", weightstr)) {
				view_weight.setCurrentValue(Constants.defaultMaleWeightint);
			} else {
				view_weight.setCurrentValue(weightvalue);
			}
		} else {// 女
			Log.v("MyLog", "wwwwww女");
			if (TextUtils.isEmpty(weightstr)||TextUtils.equals("0", weightstr)) {
				view_weight.setCurrentValue(Constants.defaultSexWeightint);
			} else {
				view_weight.setCurrentValue(weightvalue);
			}
		}
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		if(activity instanceof MainFragment){
			MainFragment mf=(MainFragment) activity;
			if(mf.fromWhich==MainFragment.fromMenu)
				changeData();
			else{
//				changeData();
			}
		}else if(activity instanceof MainMenuActivity){
			changeData();
		}
		
	}
	
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setWeightDataCommucation(new WeightDataCommucation() {

				@Override
				public void onKeyDown() {
					// TODO Auto-generated method stub
					submit();
				}
			});
		}
	}

	void submit() {
		if (activity instanceof MainMenuActivity) {
			String value=String.valueOf(view_weight.getCurrentValue());
			if(view_weight.getCurrentValue()==0)
				if (gender == 1) {// 男
					if (TextUtils.isEmpty(value)||TextUtils.equals("0", value)) {
						value=String.valueOf(Constants.defaultMaleWeight);
					}
				} else {// 女
					if (TextUtils.isEmpty(value)||TextUtils.equals("0", value)) {
						value=String.valueOf(Constants.defaultSexWeight);
					} 
				}
			((MainMenuActivity) activity).setWeight(value);// 数据提交给主activity
			
		}
	}

	public interface WeightDataCommucation {
		public void onKeyDown();
	}
}
