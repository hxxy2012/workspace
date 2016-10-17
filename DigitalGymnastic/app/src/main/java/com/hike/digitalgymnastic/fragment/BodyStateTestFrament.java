package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.NetworkUtil;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.view.PickerView;
import com.hike.digitalgymnastic.view.PickerView.onSelectListener;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

/*
 * 体测提醒
 */

public class BodyStateTestFrament extends BaseFragment{

	private View v;
//	@ViewInject(R.id.iv_left)
//	private ImageView iv_left;
//	@ViewInject(R.id.tv_setting_title)
//	private TextView tv_setting_title;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.btn_right)
	private ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@ViewInject(R.id.ll_body_test_time)
	private LinearLayout ll_body_test_time;
	@ViewInject(R.id.HH_body_test)
	private PickerView HH_body_test;
	@ViewInject(R.id.MM_body_test)
	private PickerView MM_body_test;

	@ViewInject(R.id.rg_week)
	private RadioGroup rg_week;
	@ViewInject(R.id.rb_monday)
	private CheckBox rb_monday;
	@ViewInject(R.id.rb_tuesday)
	private CheckBox rb_tuesday;
	@ViewInject(R.id.rb_wednesday)
	private CheckBox rb_wednesday;
	@ViewInject(R.id.rb_thursday)
	private CheckBox rb_thursday;
	@ViewInject(R.id.rb_friday)
	private CheckBox rb_friday;
	@ViewInject(R.id.rb_saturday)
	private CheckBox rb_saturday;
	@ViewInject(R.id.rb_sunday)
	private CheckBox rb_sunday;
	String MM_body_test_value;
	private String HH_body_test_value;
	private BaseDao dao;
	private int week_id=1;
	// ,R.id.rb_tuesday,R.id.rb_wednesday,R.id.rb_thursday,R.id.rb_friday,R.id.rb_saturday
	@OnClick(value = { R.id.btn_left, R.id.rb_sunday,R.id.rb_monday})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			submit();
			break;

		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.menu_set_bodytest_alarm, container,
				false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		init();
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			init();
		}
	}

	private void init() {

		dao = new BaseDao(this, (MainMenuActivity) activity);
		setListner();
		List<String> mmdatas = new ArrayList<String>();
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				mmdatas.add("0" + i);
			} else {
				mmdatas.add(i + "");
			}
		}
		List<String> hhdatas = new ArrayList<String>();
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				hhdatas.add("0" + i);
			} else {
				hhdatas.add(i + "");
			}
		}
		MM_body_test.setData(mmdatas);
		HH_body_test.setData(hhdatas);
		
		MM_body_test_value = PreferencesUtils.getString(
				(MainMenuActivity) activity, "MM_body_test_value", "00");
		HH_body_test_value = PreferencesUtils.getString(
				(MainMenuActivity) activity, "HH_body_test_value", "09");

		MM_body_test.setSelected(Integer.parseInt(MM_body_test_value));
		HH_body_test.setSelected(Integer.parseInt(HH_body_test_value));

		PreferencesUtils.putString((MainMenuActivity) activity,
				"MM_body_test_value", MM_body_test_value);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"HH_body_test_value", HH_body_test_value);
		body_state_test_week = PreferencesUtils.getString((MainMenuActivity) activity, "body_state_test_week", "1");
		System.out.println("body_state_test_week:"+body_state_test_week);
		RadioButton rb_checked = (RadioButton)rg_week.findViewById(getWeekId(Integer.parseInt(body_state_test_week)));
		rb_checked.setChecked(true);
//		rg_week.check(getWeekId(Integer.parseInt(body_state_test_week)));

		title.setText(getString(R.string.body_test_alarm));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(false);
		// dao.getFeedback();
		// Utils.showMessage(activity, "提交成功,谢谢!");
		// ((MainMenuActivity) activity).fragmentBack();
		if(requestCode==70){
			application.isNeedUpdated=true;
			PreferencesUtils.putString((MainMenuActivity) activity,
			"body_state_test_week", body_state_test_week);
			PreferencesUtils.putString((MainMenuActivity) activity,
					"MM_body_test_value", MM_body_test_value);
			PreferencesUtils.putString((MainMenuActivity) activity,
					"HH_body_test_value", HH_body_test_value);

			Log.i("BodyStateTestFrament", "体测数据上传服务器成功！"+application.isNeedUpdated);
		}
		((MainMenuActivity) activity).fragmentBack();
	}
	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
		application.isNeedUpdated=false;
		((MainMenuActivity) activity).fragmentBack();
	}
	private String body_state_test_week;
	public void setListner() {
		MM_body_test.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				MM_body_test_value = MM_body_test.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"MM_body_test_value", MM_body_test.getMiddleText());
			}
		});
		HH_body_test.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {
				HH_body_test_value = HH_body_test.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"HH_body_test_value", HH_body_test.getMiddleText());
			}
		});

		rg_week.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			private int week_index;


			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.rb_sunday:
					week_index = 7;
					break;

				case R.id.rb_monday:
					week_index = 1;
					break;
				case R.id.rb_tuesday:
					week_index = 2;
					break;
				case R.id.rb_wednesday:
					week_index = 3;
					break;
				case R.id.rb_thursday:
					week_index = 4;
					break;
				case R.id.rb_friday:
					week_index = 5;
					break;
				case R.id.rb_saturday:
					week_index = 6;
					break;
				default:
					break;
				}
				body_state_test_week = week_index+"";
//				PreferencesUtils.putString((MainMenuActivity) activity, "body_state_test_week", week_index+"");
			}
		});

	}
	public int getWeekId(int index){
		int week_id = 1;
	
		switch (index) {
		case 7:
			week_id = R.id.rb_sunday;
			
			break;

		case 1:
			week_id = R.id.rb_monday;
			break;
		case 2:
			week_id = R.id.rb_tuesday;
			break;
		case 3:
			week_id = R.id.rb_wednesday;
			break;
		case 4:
			week_id = R.id.rb_thursday;
			break;
		case 5:
			week_id = R.id.rb_friday;
			break;
		case 6:
			week_id = R.id.rb_saturday;
			break;
		default:
			break;
		}
		return week_id;
	}




	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setBodyTestCommucation(new BodyTestCommucation() {

				@Override
				public void onKeyDown() {
					// TODO Auto-generated method stub
					submit();
				}
			});
		}
	}
	protected void submit() {
		if (NetworkUtil.isNetwork((MainMenuActivity) activity)){
			//请求更新体测提醒时间
			dao.setBodyRemind(String.valueOf(PreferencesUtils.getBoolean((MainMenuActivity) activity, "body_test_bt_is_enable",false)),
					body_state_test_week, HH_body_test_value+":"+MM_body_test_value+":00");
		}else{
			((MainMenuActivity) activity).fragmentBack();
		}
	}
	private int week_index;
	String weekStr = "";
	ArrayList weekList = new ArrayList();


	public interface BodyTestCommucation {
		public void onKeyDown();
	}
}
