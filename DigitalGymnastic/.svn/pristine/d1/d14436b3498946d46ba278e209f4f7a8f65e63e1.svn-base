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
import com.umeng.common.message.Log;

import java.util.ArrayList;
import java.util.List;

/*
 * 意见反馈不能为空
 */

public class SitLongTimeFrament extends BaseFragment{

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
	@ViewInject(R.id.rl_sit_long_time)
	private RelativeLayout rl_sit_long_time;
	@ViewInject(R.id.tv_sit_long_time)
	private TextView tv_sit_long_time;
	@ViewInject(R.id.MM_sit_long)
	private PickerView MM_sit_long;
	@ViewInject(R.id.rl_sit_long_start)
	private RelativeLayout rl_sit_long_start;
	@ViewInject(R.id.tv_sit_long_time_start)
	private TextView tv_sit_long_time_start;
	@ViewInject(R.id.ll_sit_start_time)
	private LinearLayout ll_sit_start_time;
	@ViewInject(R.id.start_HH_pv)
	private PickerView start_HH_pv;
	@ViewInject(R.id.start_MM_pv)
	private PickerView start_MM_pv;
	@ViewInject(R.id.rl_sit_long_end)
	private RelativeLayout rl_sit_long_end;
	@ViewInject(R.id.tv_sit_long_time_end)
	private TextView tv_sit_long_time_end;
	@ViewInject(R.id.ll_end_time)
	private LinearLayout ll_end_time;
	@ViewInject(R.id.ll_sit_long_time)
	private LinearLayout ll_sit_long_time;
	@ViewInject(R.id.end_HH_pv)
	private PickerView end_HH_pv;
	@ViewInject(R.id.end_MM_pv)
	private PickerView end_MM_pv;
	private BaseDao dao;
	private int visible;
	private String MM_sit_long_value;
	private String start_HH_sit_long;
	private String start_MM_sit_long;
	private String end_HH_sit_long;
	private String end_MM_sit_long;

	@OnClick(value = { R.id.btn_left, R.id.rl_sit_long_time,
			R.id.rl_sit_long_start, R.id.rl_sit_long_end })
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			submit();
			
			break;

		case R.id.rl_sit_long_time:
			visible = ll_sit_long_time.getVisibility();
			if (visible == View.GONE || visible == View.INVISIBLE) {
				ll_sit_long_time.setVisibility(View.VISIBLE);
				ll_sit_start_time.setVisibility(View.GONE);
				ll_end_time.setVisibility(View.GONE);
			} else {
				ll_sit_long_time.setVisibility(View.GONE);
			}
			break;
		case R.id.rl_sit_long_start:
			visible = ll_sit_start_time.getVisibility();

			if (visible == View.GONE || visible == View.INVISIBLE) {
				ll_sit_start_time.setVisibility(View.VISIBLE);
				ll_sit_long_time.setVisibility(View.GONE);
				ll_end_time.setVisibility(View.GONE);
			} else {
				ll_sit_start_time.setVisibility(View.GONE);
			}
			break;
		case R.id.rl_sit_long_end:
			visible = ll_end_time.getVisibility();
			if (visible == View.GONE || visible == View.INVISIBLE) {
				ll_end_time.setVisibility(View.VISIBLE);
				ll_sit_start_time.setVisibility(View.GONE);
				ll_sit_long_time.setVisibility(View.GONE);
			} else {
				ll_end_time.setVisibility(View.GONE);
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
		this.v = inflater.inflate(R.layout.menu_set_sitlong_alarm, container,
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
		List<String> sitLongDatas = new ArrayList<String>();
		for (int i = 1; i <= 60; i++) {
			if (i < 10) {
				sitLongDatas.add("0" + i);
			} else {
				sitLongDatas.add(i + "");
			}
		}
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
		MM_sit_long.setData(sitLongDatas);
		start_HH_pv.setData(hhdatas);
		start_MM_pv.setData(mmdatas);
		end_HH_pv.setData(hhdatas);
		end_MM_pv.setData(mmdatas);
		MM_sit_long_value = PreferencesUtils.getString(
				(MainMenuActivity) activity, "MM_sit_long_value", "00");
		start_HH_sit_long = PreferencesUtils.getString(
				(MainMenuActivity) activity, "start_HH_sit_long", "09");
		start_MM_sit_long = PreferencesUtils.getString(
				(MainMenuActivity) activity, "start_MM_sit_long", "00");
		end_HH_sit_long = PreferencesUtils.getString(
				(MainMenuActivity) activity, "end_HH_sit_long", "18");
		end_MM_sit_long = PreferencesUtils.getString(
				(MainMenuActivity) activity, "end_MM_sit_long", "00");
		MM_sit_long.setSelected(Integer.parseInt(MM_sit_long_value));
		start_HH_pv.setSelected(Integer.parseInt(start_HH_sit_long));
		start_MM_pv.setSelected(Integer.parseInt(start_MM_sit_long));
		end_HH_pv.setSelected(Integer.parseInt(end_HH_sit_long));
		end_MM_pv.setSelected(Integer.parseInt(end_MM_sit_long));

		PreferencesUtils.putString((MainMenuActivity) activity, "MM_sit_long_value",
				MM_sit_long_value);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"start_HH_sit_long", start_HH_sit_long);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"start_MM_sit_long", start_MM_sit_long);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"end_HH_sit_long", end_HH_sit_long);

		PreferencesUtils.putString((MainMenuActivity) activity,
				"end_MM_sit_long", end_MM_sit_long);

		tv_sit_long_time.setText(Integer.parseInt(MM_sit_long_value) + "分钟");

		tv_sit_long_time_start.setText(start_HH_sit_long + ":"
				+ start_MM_sit_long);

		tv_sit_long_time_end.setText(end_HH_sit_long + ":" + end_MM_sit_long);

		title.setText(getString(R.string.sit_long_alarm));
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
		if(requestCode==69){
			application.isNeedUpdated=true;
			PreferencesUtils.putString((MainMenuActivity) activity,
			"MM_sit_long_value", MM_sit_long_value);
			PreferencesUtils.putString((MainMenuActivity) activity,
			"start_HH_sit_long", start_HH_sit_long);
			PreferencesUtils.putString((MainMenuActivity) activity,
			"start_MM_sit_long", start_MM_sit_long);
			PreferencesUtils.putString((MainMenuActivity) activity,
			"end_HH_sit_long", end_HH_sit_long);
			PreferencesUtils.putString((MainMenuActivity) activity,
			"end_MM_sit_long", end_MM_sit_long);
			Log.a("SitLongTimeFrament", "久坐数据上传服务器成功！"+application.isNeedUpdated);
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
		MM_sit_long.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sit_long_time.setText(MM_sit_long.getMiddleText() + "分钟");
				MM_sit_long_value = MM_sit_long.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"MM_sit_long", MM_sit_long.getMiddleText());
			}
		});
		start_HH_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sit_long_time_start.setText(start_HH_pv.getMiddleText()
						+ ":" + start_MM_pv.getMiddleText());
				start_HH_sit_long = start_HH_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"start_HH_sit_long", start_HH_pv.getMiddleText());
			}
		});
		start_MM_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sit_long_time_start.setText(start_HH_pv.getMiddleText()
						+ ":" + start_MM_pv.getMiddleText());
				start_MM_sit_long = start_MM_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"start_MM_sit_long", start_MM_pv.getMiddleText());
			}
		});
		end_HH_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sit_long_time_end.setText(end_HH_pv.getMiddleText() + ":"
						+ end_MM_pv.getMiddleText());
				end_HH_sit_long = end_HH_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"end_HH_sit_long", end_HH_pv.getMiddleText());
			}
		});
		end_MM_pv.setOnSelectListener(new onSelectListener() {

			@Override
			public void onSelect(String text) {

				tv_sit_long_time_end.setText(end_HH_pv.getMiddleText() + ":"
						+ end_MM_pv.getMiddleText());
				end_MM_sit_long = end_MM_pv.getMiddleText();
//				PreferencesUtils.putString((MainMenuActivity) activity,
//						"end_MM_sit_long", end_MM_pv.getMiddleText());
			}
		});
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		if (activity instanceof MainMenuActivity) {
			MainMenuActivity mma = (MainMenuActivity) activity;
			mma.setSitLongDataCommucation(new SitLongDataCommucation() {
				
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
			//请求更新久坐提醒时间
			 dao.setSedentaryRemind(String.valueOf(PreferencesUtils.getBoolean((MainMenuActivity) activity, "sit_bt_is_enable",false)),
					 Integer.parseInt(MM_sit_long_value)+"", start_HH_sit_long+":"+start_MM_sit_long+":00", end_HH_sit_long+":"+end_MM_sit_long+":00");
		}else{
			((MainMenuActivity) activity).fragmentBack();
		}
		
	}
	public interface SitLongDataCommucation {
		public void onKeyDown();
	}
}
