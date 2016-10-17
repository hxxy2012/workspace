package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.view.DiverRuleAgeLayout;
import com.hike.digitalgymnastic.view.YearVerticalRuler;
import com.hike.digitalgymnastic.view.YearVerticalRuler.OnValueChangeListener;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.Date;

public class YearFragment extends BaseFragment {
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.btn_right_text)
	private Button btn_right_text;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	
	@ViewInject(R.id.root)
	private LinearLayout root_age;
	@ViewInject(R.id.top_ly)
	private LinearLayout top_ly;
	@ViewInject(R.id.yearruler)
	private YearVerticalRuler yearruler;
	@ViewInject(R.id.yeartext)
	private TextView yeartext;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.btn_next_two)
	private Button btn;
	private View v;
	private String yearstr;
	private DiverRuleAgeLayout diverRuleAgeLayout;
	private String currentYear;

	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.btn_next_two, R.id.btn_right_text })
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
//			((MainMenuActivity) activity).setYear(diverRuleAgeLayout.currentAge + "");// 数据提交给主activity
			break;
		case R.id.btn_next_two:
			((MainFragment) activity).setYear(diverRuleAgeLayout.currentAge);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		this.inflater = inflater;
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		this.v = inflater.inflate(R.layout.personalinfo_two, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		btn_left.setImageResource(R.mipmap.back_login_3x);
		InitData();
	}

	private void InitData() {
		title.setTextColor(getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		title.setText(getString(R.string.per_info));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_right_text.setVisibility(View.INVISIBLE);
		diverRuleAgeLayout = new DiverRuleAgeLayout(activity);
		root_age.addView(diverRuleAgeLayout);
//		diverRuleAgeLayout.init();
		
		onHiddenChangedAction();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		currentYear = format.format(new Date());
		yearruler.initViewParam(1930, Integer.parseInt(yearstr),Integer.parseInt(currentYear), 10);
		yeartext.setText(yearstr);
		yearruler.setValueChangeListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(float value) {
				// TODO Auto-generated method stub
				System.out.println("mvalue==" + value);
				yearstr = (int)value+"";
				yeartext.setText(yearstr);
			}
		});
//		if (activity instanceof MainFragment) {
//			yearstr = ((MainFragment) activity).getCustomer().getYear();
//			btn.setVisibility(View.VISIBLE);
//		}
//		if (activity instanceof MainMenuActivity) {
//			yearstr = ((MainMenuActivity) activity).getYear();
//			top_ly.setVisibility(View.VISIBLE);
////			btn_right_text.setVisibility(View.VISIBLE);
////			btn_right_text.setText("保存");
//		}
//		if (TextUtils.isEmpty(yearstr) || "0".equals(yearstr)) // 初始化年龄
//			diverRuleAgeLayout.setDefaultYear(Constants.defaultAge);
//		else
//			diverRuleAgeLayout.setDefaultYear(Integer.parseInt(yearstr));
//
//		diverRuleAgeLayout.initKeduScroll();
	}
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		if(!hidden){
			
			if(activity instanceof MainFragment){
				MainFragment mf=(MainFragment) activity;
				if(mf.fromWhich==MainFragment.fromMenu)
					onHiddenChangedAction();
				else{
//					onHiddenChangedAction();
				}
			}else if(activity instanceof MainMenuActivity){
					onHiddenChangedAction();
			}
		}
	}
	
	private void onHiddenChangedAction(){
		if (activity  instanceof MainFragment) {
			yearstr = ((MainFragment) activity).getCustomer().getYear();
			btn.setVisibility(View.VISIBLE);
		}
		if (activity instanceof MainMenuActivity) {
			yearstr = ((MainMenuActivity) activity).getYear();
			top_ly.setVisibility(View.VISIBLE);
//			btn_right_text.setVisibility(View.VISIBLE);
//			btn_right_text.setText("保存");
		}
		if (TextUtils.isEmpty(yearstr)|| TextUtils.equals("0", yearstr)) {
		// 初始化年龄

			yearstr = Constants.defaultAge+"";
		}else {
			yearstr = Integer.parseInt(yearstr)+"";
		}
	}
	
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if(activity instanceof MainMenuActivity){
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setAgeDataCommucation(new AgeDataCommucation() {

				@Override
				public void onKeyDown() {
					// TODO Auto-generated method stub
					submit();
				}
			});
		}
	}
	void submit(){
		if(activity instanceof MainMenuActivity){
			((MainMenuActivity) activity).setYear(yearstr);// 数据提交给主activity
			PreferencesUtils.putInt((MainMenuActivity) activity, "customer_age", Integer.parseInt(currentYear) - Integer.parseInt(yearstr));

		}
		}
	public interface AgeDataCommucation {
		public void onKeyDown();
	}
}
