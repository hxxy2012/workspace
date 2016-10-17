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
import com.hike.digitalgymnastic.view.DiverRuleHeightLayout;
import com.hike.digitalgymnastic.view.HeightVerticalRuler;
import com.hike.digitalgymnastic.view.HeightVerticalRuler.OnValueChangeListener;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
public class HeightFragment extends BaseFragment {
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
	@ViewInject(R.id.btn_next_three)
	private Button btn;
	@ViewInject(R.id.tv_height)
	private TextView tv_height;
	@ViewInject(R.id.iv_sex)
	private ImageView iv_sex;
	@ViewInject(R.id.height_vertical_ruler)
	private HeightVerticalRuler height_vertical_ruler;
	private View v;
	private DiverRuleHeightLayout diverRuleHeightLayout;
	private int gender;
	private String heightstr;

	@OnClick(value = { R.id.btn_left, R.id.ll_btn_left, R.id.btn_next_three, R.id.btn_right_text })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			submit();
			
			break;
		case R.id.ll_btn_left:
			submit();
			
			break;
		case R.id.btn_right_text:
			// ((MainMenuActivity) activity)
			// .setHeight(diverRuleHeightLayout.currentHeight + "");//
			// 数据提交给主activity
			break;
		case R.id.btn_next_three:
			((MainFragment) activity)
					.setHeight(diverRuleHeightLayout.currentHeight);
			break;
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		this.v = inflater
				.inflate(R.layout.personalinfo_three, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		InitData();
	}

	private void InitData() {
		title.setTextColor(getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		title.setText(getString(R.string.per_info));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_right_text.setVisibility(View.INVISIBLE);
//		diverRuleHeightLayout = new DiverRuleHeightLayout(activity);
//		root_age.addView(diverRuleHeightLayout);
//		diverRuleHeightLayout.init();
		changeData();
		tv_height.setText(heightstr);
		height_vertical_ruler.initViewParam(50,Integer.parseInt(heightstr), 250, 10);
		height_vertical_ruler.setValueChangeListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(float value) {
				// TODO Auto-generated method stub
				System.out.println("mvalue=="+value);
				heightstr = (int)value+"";
				tv_height.setText(heightstr);
			}
		});
//		diverRuleHeightLayout.initKeduScroll();
	}

	private void changeData() {
		if (activity instanceof MainFragment) {
			gender = ((MainFragment) activity).getGender();
			heightstr = ((MainFragment) activity).getCustomer()
					.getHeight();
			btn.setVisibility(View.VISIBLE);
		}
		if (activity instanceof MainMenuActivity) {
			gender = Integer.parseInt(((MainMenuActivity) activity)
					.getGender());
			heightstr = ((MainMenuActivity) activity).getHeight();
			top_ly.setVisibility(View.VISIBLE);
//			btn_right_text.setVisibility(View.VISIBLE);
//			btn_right_text.setText("保存");
		}
		if (gender == 1) {// 男
			Log.v("MyLog", "hhhhhhhhhh男");
			iv_sex.setImageResource(R.mipmap.boy_dody);
			if (TextUtils.isEmpty(heightstr) || "0".equals(heightstr)) {// 初始化身高
				heightstr = Constants.defaultMaleHeight+"";
//				diverRuleHeightLayout
//						.setDefaultYear(Constants.defaultMaleHeight);
			} else {
//				diverRuleHeightLayout.setDefaultYear(Integer
//						.parseInt(heightstr));
			}
		} else {// 女
			Log.v("MyLog", "hhhhhhhhh女");
			iv_sex.setImageResource(R.mipmap.girl_body);
			if (TextUtils.isEmpty(heightstr)|| "0".equals(heightstr)) {
				heightstr = Constants.defaultSexHeight+"";
//				diverRuleHeightLayout
//						.setDefaultYear(Constants.defaultSexHeight);
			} else {

//				diverRuleHeightLayout.setDefaultYear(Integer
//						.parseInt(heightstr));
			}
		}
//		diverRuleHeightLayout.initKeduScroll();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		Utils.toolBarManager((MainMenuActivity) activity, R.color.app_bg_login);
		if (activity instanceof MainFragment) {
			MainFragment mf = (MainFragment) activity;
			if (mf.fromWhich == MainFragment.fromMenu)
				changeData();
			else{
//				changeData();
			}
		} else if (activity instanceof MainMenuActivity) {
			changeData();
		}

	}

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setHeightDataCommucation(new HeightDataCommucation() {

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
			((MainMenuActivity) activity).setHeight(heightstr);// 数据提交给主activity
		}
	}

	public interface HeightDataCommucation {
		public void onKeyDown();
	}
}
