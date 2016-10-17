package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import com.umeng.socialize.utils.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * 运动提醒
 */

public class SportAlarmFrament extends BaseFragment {

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
	@ViewInject(R.id.rl_sport_time)
	private RelativeLayout rl_sport_time;
	@ViewInject(R.id.tv_sport_time)
	private TextView tv_sport_time;
	@ViewInject(R.id.ll_sport_time)
	private LinearLayout ll_sport_time;
	@ViewInject(R.id.sport_HH_pv)
	private PickerView sport_HH_pv;
	@ViewInject(R.id.sport_MM_pv)
	private PickerView sport_MM_pv;
	private int visible;
	private String MM_sport_value;
	private String HH_sport_value;
	private BaseDao dao;

	@OnClick(value = { R.id.btn_left, R.id.rl_sport_time})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			
			submit();
			break;

		case R.id.rl_sport_time:
			visible = ll_sport_time.getVisibility();
			if (visible == View.GONE || visible == View.INVISIBLE) {
				ll_sport_time.setVisibility(View.VISIBLE);

			} else {
				ll_sport_time.setVisibility(View.GONE);
			}
			break;
		
		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.v = inflater.inflate(R.layout.menu_set_sport_alarm, container,
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
		sport_MM_pv.setData(mmdatas);
		sport_HH_pv.setData(hhdatas);
		
		MM_sport_value = PreferencesUtils.getString(
				(MainMenuActivity) activity, "MM_sport_value", "00");
		HH_sport_value = PreferencesUtils.getString(
				(MainMenuActivity) activity, "HH_sport_value", "09");


		sport_HH_pv.setSelected(Integer.parseInt(HH_sport_value));
		sport_MM_pv.setSelected(Integer.parseInt(MM_sport_value));


		PreferencesUtils.putString((MainMenuActivity) activity,
				"HH_sport_value", HH_sport_value);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"MM_sport_value", MM_sport_value);

		

		tv_sport_time.setText(HH_sport_value+":"+MM_sport_value);

		title.setText(getString(R.string.sport_alarm));
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
		if(requestCode==71){
			application.isNeedUpdated=true;
			PreferencesUtils.putString((MainMenuActivity) activity,
					"MM_sport_value", MM_sport_value);
			PreferencesUtils.putString((MainMenuActivity) activity,
					"HH_sport_value", HH_sport_value);
			Log.i("SportAlarmFrament", "运动数据上传服务器成功！"+application.isNeedUpdated);
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

	public void setListner() {
		sport_MM_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sport_time.setText(sport_HH_pv.getMiddleText()
						+ ":" + sport_MM_pv.getMiddleText());
				MM_sport_value = sport_MM_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"MM_sport_value", sport_MM_pv.getMiddleText());
			}
		});
		sport_HH_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sport_time.setText(sport_HH_pv.getMiddleText()
						+ ":" + sport_MM_pv.getMiddleText()); 
				HH_sport_value = sport_HH_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"HH_sport_value", sport_HH_pv.getMiddleText());
			}
		});
		
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setSportDataCommucation(new SportDataCommucation() {

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
			//请求更新运动提醒时间
			dao.setSportRemind(String.valueOf(PreferencesUtils.getBoolean((MainMenuActivity) activity, "sport_bt_is_enable",false)), HH_sport_value+":"+MM_sport_value+":00");
		}else{
			((MainMenuActivity) activity).fragmentBack();
		}
	}
	public interface SportDataCommucation {
		public void onKeyDown();
	}
}
