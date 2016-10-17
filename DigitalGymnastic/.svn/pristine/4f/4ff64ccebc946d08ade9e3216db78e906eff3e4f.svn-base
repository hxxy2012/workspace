package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.GuiderActivity;
import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.MainMenuActivity;
import com.hike.digitalgymnastic.db.DBManager;
import com.hike.digitalgymnastic.entitiy.AllRemind;
import com.hike.digitalgymnastic.entitiy.BodyRemind;
import com.hike.digitalgymnastic.entitiy.SedentaryRemind;
import com.hike.digitalgymnastic.entitiy.SportRemind;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.UISwitchButton;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;

import java.util.Map;
import java.util.Set;

public class SetFragment extends BaseFragment {
	// 整个平台的Controller,负责管理整个SDK的配置、操作等处理
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Contants.DESCRIPTOR);
	private View v;
	@ViewInject(R.id.btn_left)
	private ImageView btn_left;
	@ViewInject(R.id.title)
	private TextView title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	@ViewInject(R.id.menu_set_rl_feedback)
	private RelativeLayout menu_set_rl_feedback;
//	@ViewInject(R.id.menu_set_rl_sitlong_alarm)
//	private RelativeLayout menu_set_rl_sitlong_alarm;
//	@ViewInject(R.id.menu_set_rl_sport_alarm)
//	private RelativeLayout menu_set_rl_sport_alarm;
//	@ViewInject(R.id.menu_set_rl_bodytest_alarm)
//	private RelativeLayout menu_set_rl_bodytest_alarm;
//	@ViewInject(R.id.menu_set_rl_qq_bind)
//	private RelativeLayout menu_set_rl_qq_bind;
//	//自定义按钮
//	@ViewInject(R.id.switch_button_sit_alarm)
//	private UISwitchButton switch_button_sit_alarm;
//	@ViewInject(R.id.tv_sit_long_alarm)
//	private TextView tv_sit_long_alarm;
//	@ViewInject(R.id.switch_button_sport_alarm)
//	private UISwitchButton switch_button_sport_alarm;
//	@ViewInject(R.id.tv_sport_alarm)
//	private TextView tv_sport_alarm;
//	@ViewInject(R.id.switch_button_bodytest_alarm)
//	private UISwitchButton switch_button_bodytest_alarm;
//	@ViewInject(R.id.tv_bodytest_alarm)
//	private TextView tv_bodytest_alarm;
	
	@ViewInject(R.id.menu_set_rl_aboutme)
	private RelativeLayout menu_set_rl_aboutme;
	@ViewInject(R.id.menu_set_bt_loginout)
	private Button menu_set_bt_loginout;
	private BaseDao dao;
	private String MM_body_test_value;
	private String HH_body_test_value;
	private BodyRemind bodyremind;
	private SedentaryRemind sedentaryRemind;
	private SportRemind sportRemind;
	private String HH_sport_value;
	private String MM_sport_value;
	private int remindWeek;
	private String MM_sit_long_value;
	private String start_HH_sit_long;
	private String start_MM_sit_long;
	private String end_HH_sit_long;
	private String end_MM_sit_long;
	private boolean ischecked_sit;
	private boolean ischecked_sport;
	private boolean ischecked_body;
	private MainMenuActivity ma;

//	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.menu_set_rl_feedback,
//			R.id.menu_set_bt_loginout,R.id.menu_set_rl_sitlong_alarm,R.id.menu_set_rl_sport_alarm,
//			R.id.menu_set_rl_bodytest_alarm,R.id.menu_set_rl_qq_bind,R.id.menu_set_rl_aboutme})
	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left, R.id.menu_set_rl_feedback,
			R.id.menu_set_bt_loginout,R.id.menu_set_rl_aboutme})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;
		case R.id.ll_btn_left:
			((MainMenuActivity) activity).fragmentBack();
			break;
			
		case R.id.menu_set_rl_feedback:
			((MainMenuActivity) activity).setTabSelection(
					getString(R.string.menu_feedback),
					MainMenuActivity.mode_enter);
			break;
		case R.id.menu_set_bt_loginout:
//			LocalDataUtils.saveBindMAC(activity, null);//清除绑定的设备mac
			DBManager.getIntance(activity,null);//清除DBManager实例化对象
			
			showProgress(true);
			
			dao.Logout();
			break;
//		case R.id.menu_set_rl_sitlong_alarm:
//			((MainMenuActivity) activity).setTabSelection(
//					getString(R.string.sit_long_alarm),
//					MainMenuActivity.mode_enter);
//
//			break;
//		case R.id.menu_set_rl_sport_alarm:
//			((MainMenuActivity) activity).setTabSelection(
//					getString(R.string.sport_alarm),
//					MainMenuActivity.mode_enter);
//			break;
//		case R.id.menu_set_rl_bodytest_alarm:
//			((MainMenuActivity) activity).setTabSelection(
//					getString(R.string.body_test_alarm),
//					MainMenuActivity.mode_enter);
//			break;
//		case R.id.menu_set_rl_qq_bind:
//			login(SHARE_MEDIA.QQ);
//			break;
		case R.id.menu_set_rl_aboutme:
//			ToastUtil.showToast(application, "该功能暂未实现！");
			((MainMenuActivity) activity).setTabSelection(
					getString(R.string.about_us),
					MainMenuActivity.mode_enter);
			break;

		default:
			break;
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.inflater = inflater;
		this.ma = (MainMenuActivity) activity;
//		Utils.toolBarManager(ma, R.color.app_bg_login);
		this.v = inflater.inflate(R.layout.activity_menu_set, container, false);
		ViewUtils.inject(this, v);
		return v;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 添加QQ、QZone平台
		addQQQZonePlatform();
		dao = new BaseDao(this, activity);
		dao.getAllRemind("SportRemind", "SedentaryRemind", "BodyRemind");
		initView();
		initData();
		//setListner();
	}

//	private void setListner() {
//		switch_button_sit_alarm.setOnCheckedChangeListener(this);
//		switch_button_sport_alarm.setOnCheckedChangeListener(this);
//		switch_button_bodytest_alarm.setOnCheckedChangeListener(this);
//	}
	private void initView() {
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
	}
	private void initData() {

		title.setText(getString(R.string.menu_set_str));
		AllRemind allremind = PreferencesUtils.getAllRemind((MainMenuActivity) activity, Contants.ALLREMIND);
		if(allremind!=null){
			bodyremind = allremind.getBodyRemind();
			sedentaryRemind = allremind.getSedentaryRemind();
			sportRemind = allremind.getSportRemind();
		}
		System.out.println(bodyremind+"====="+sedentaryRemind+"======"+sportRemind);
		if(sedentaryRemind!=null){
			MM_sit_long_value = sedentaryRemind.getSedentaryTime()+"";
			String startTime[] = sedentaryRemind.getBremindTime().split(":");
			String endTime[] = sedentaryRemind.getEremindTime().split(":");
			ischecked_sit=sedentaryRemind.isEnabled();
			start_HH_sit_long = startTime[0];
			start_MM_sit_long = startTime[1];
			end_HH_sit_long = endTime[0];
			end_MM_sit_long = endTime[1];
		}else{
			MM_sit_long_value = "29";
			start_HH_sit_long = "08";
			start_MM_sit_long = "05";
			end_HH_sit_long = "18";
			end_MM_sit_long = "05";
			ischecked_sit=false;
		}
//		switch_button_sit_alarm.setChecked(ischecked_sit);
//		tv_sit_long_alarm.setText(Integer.parseInt(MM_sit_long_value)+"分钟，"+start_HH_sit_long+":"+start_MM_sit_long+"--"+end_HH_sit_long+":"+end_MM_sit_long);
		PreferencesUtils.putString((MainMenuActivity) activity, "MM_sit_long",MM_sit_long_value);
		PreferencesUtils.putString((MainMenuActivity) activity, "start_HH_sit_long",start_HH_sit_long);
		PreferencesUtils.putString((MainMenuActivity) activity, "start_MM_sit_long",start_MM_sit_long);
		PreferencesUtils.putString((MainMenuActivity) activity, "end_HH_sit_long",end_HH_sit_long);
		PreferencesUtils.putString((MainMenuActivity) activity, "end_MM_sit_long",end_MM_sit_long);
		if(sportRemind!=null){
			String remindTime[] = sportRemind.getRemindTime().split(":");;
			ischecked_sport=sportRemind.isEnabled();
			HH_sport_value = remindTime[0];
			MM_sport_value = remindTime[1];
			
			
		}else{
			HH_sport_value = "12";
			MM_sport_value = "05";
			ischecked_sport = false;
		}
		PreferencesUtils.putString(
				(MainMenuActivity) activity, "MM_sport_value", MM_sport_value);
		 PreferencesUtils.putString(
				(MainMenuActivity) activity, "HH_sport_value", HH_sport_value);
//		tv_sport_alarm.setText(HH_sport_value+":"+MM_sport_value);
//		switch_button_sport_alarm.setChecked(ischecked_sport);
		if(bodyremind!=null){
			ischecked_body=bodyremind.isEnabled();
			remindWeek = bodyremind.getRemindWeekday();
		}else{
			remindWeek = 1;
			ischecked_body=false;
		}
//		tv_bodytest_alarm.setText("每周"+getWeek(remindWeek)+"提醒");
//		PreferencesUtils.putString((MainMenuActivity) activity, "body_state_test_week",remindWeek+"");
//		switch_button_bodytest_alarm.setChecked(ischecked_body);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
//		Utils.toolBarManager(ma, R.color.app_bg_login);

		Utils.closeInputMetherUI(activity);
		if(!hidden){
			if(application.isNeedUpdated){
				Log.i("onHiddenChanged", "application.isNeedUpdated = "+application.isNeedUpdated);
				String MM_sit_long_value = PreferencesUtils.getString((MainMenuActivity) activity, "MM_sit_long_value","60");
				String start_HH_sit_long = PreferencesUtils.getString((MainMenuActivity) activity, "start_HH_sit_long","09");
				String start_MM_sit_long = PreferencesUtils.getString((MainMenuActivity) activity, "start_MM_sit_long","00");
				String end_HH_sit_long = PreferencesUtils.getString((MainMenuActivity) activity, "end_HH_sit_long","18");
				String end_MM_sit_long = PreferencesUtils.getString((MainMenuActivity) activity, "end_MM_sit_long","00");
				//tv_sit_long_alarm.setText(Integer.parseInt(MM_sit_long_value)+"分钟，"+start_HH_sit_long+":"+start_MM_sit_long+"--"+end_HH_sit_long+":"+end_MM_sit_long);
				String MM_sport_value = PreferencesUtils.getString(
						(MainMenuActivity) activity, "MM_sport_value", "05");
				String HH_sport_value = PreferencesUtils.getString(
						(MainMenuActivity) activity, "HH_sport_value", "12");
				//tv_sport_alarm.setText(HH_sport_value+":"+MM_sport_value);
				String body_state_test_week = PreferencesUtils.getString((MainMenuActivity) activity, "body_state_test_week","1");
				//tv_bodytest_alarm.setText("每周"+getWeek(Integer.parseInt(body_state_test_week))+"提醒");

			}
		}
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		if(activity.isFinishing())
			return ;
		showProgress(false);
//		LocalDataUtils.saveLoginInfo(activity, false);
//		Intent intent=new Intent(activity,LoginActivity.class);
//		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		startActivity(intent);
//		activity.finish();

		if(this.isDetached())
			return ;
		if(requestCode==69){
		}else if(requestCode==70){
			
		}else if(requestCode==71){
			
		}else if(requestCode==72){
			initData();
			Log.d("MyLog", "setting page--------");
		}else{
			logout();
		}
		
	};
	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
//		LocalDataUtils.saveLoginInfo(activity, false);
//		startActivity(new Intent(activity,LoginActivity.class));
//		activity.finish();
		logout();
	}
	
	void logout(){
		try{
			LocalDataUtils.saveLoginInfo(activity, false);
			Intent intent=new Intent(activity,GuiderActivity.class);
			startActivity(intent);
			Set<String> set=HikoDigitalgyApplication.map.keySet();
			String[] nameArray=new String[100];
			set.toArray(nameArray);
			
			for(String activityName:nameArray){
				Activity ay=HikoDigitalgyApplication.map.get(activityName);
				if(ay!=null){
					ay.finish();
				}
			}
			application.logout();
			System.gc();
			if(activity instanceof Activity) {
				((Activity) activity).finish();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

//	@Override
//	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
//		String MM_sit_long_value;
//		String start_HH_sit_long;
//		String start_MM_sit_long;
//		String end_HH_sit_long;
//		String end_MM_sit_long;
//		switch(arg0.getId()){
//		case R.id.switch_button_sit_alarm:
//
//			if(arg1){
//				tv_sit_long_alarm.setVisibility(View.VISIBLE);
//			}else{
//				tv_sit_long_alarm.setVisibility(View.INVISIBLE);
//			}
//			PreferencesUtils.putBoolean(activity, "sit_bt_is_enable", arg1);
//			MM_sit_long_value = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "MM_sit_long", "00");
//			start_HH_sit_long = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "start_HH_sit_long", "09");
//			start_MM_sit_long = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "start_MM_sit_long", "00");
//			end_HH_sit_long = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "end_HH_sit_long", "18");
//			end_MM_sit_long = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "end_MM_sit_long", "00");
//			 dao.setSedentaryRemind(arg1+"",
//					 Integer.parseInt(MM_sit_long_value)+"", start_HH_sit_long+":"+start_MM_sit_long+":00", end_HH_sit_long+":"+end_MM_sit_long+":00");
//			break;
//		case R.id.switch_button_sport_alarm:
//			if(arg1){
//				tv_sport_alarm.setVisibility(View.VISIBLE);
//			}else{
//				tv_sport_alarm.setVisibility(View.INVISIBLE);
//			}
//			String MM_sport_value = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "MM_sport_value", "00");
//			String HH_sport_value = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "HH_sport_value", "09");
//			PreferencesUtils.putBoolean((MainMenuActivity) activity, "sport_bt_is_enable", arg1);
//			dao.setSportRemind(arg1+"", HH_sport_value+":"+MM_sport_value+":00");
//			break;
//		case R.id.switch_button_bodytest_alarm:
//
//			if(arg1){
//				tv_bodytest_alarm.setVisibility(View.VISIBLE);
//			}else{
//				tv_bodytest_alarm.setVisibility(View.INVISIBLE);
//			}
//			PreferencesUtils.putBoolean((MainMenuActivity) activity, "body_test_bt_is_enable", arg1);
//			String body_state_test_week = PreferencesUtils.getString((MainMenuActivity) activity, "body_state_test_week","1");
//			HH_body_test_value = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "HH_body_test_value", "12");
//			MM_body_test_value = PreferencesUtils.getString(
//					(MainMenuActivity) activity, "MM_body_test_value", "00");
//			dao.setBodyRemind(arg1+"", body_state_test_week, HH_body_test_value+":"+MM_body_test_value+":00");
//			break;
//		default:
//			break;
//		}
//
//	}
	public String getWeek(int index){
		String weekStr = "";
		switch (index) {
		case 1:
			weekStr = "一";
			break;
		case 2:
			weekStr = "二";
			break;
		case 3:
			weekStr = "三";
			break;
		case 4:
			weekStr = "四";
			break;
		case 5:
			weekStr = "五";
			break;
		case 6:
			weekStr = "六";
			break;
		case 7:
			weekStr = "日";
			break;
		default:
			break;
		}
		return weekStr;
	}
	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
		String appId = "1104196320";
		String appKey = "fNqdv6qeiztm9fZO";
		// 添加QQ支持, 并且设置QQ分享内容的target url
		UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((MainMenuActivity) activity,
				appId, appKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				(MainMenuActivity) activity, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}
	/**
	 * 授权。如果授权成功，则获取用户信息
	 * 
	 * @param platform
	 */
	private void login(final SHARE_MEDIA platform) {
		mController.doOauthVerify((MainMenuActivity) activity, platform,
				new UMAuthListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
//						Toast.makeText(LoginActivity.this, "授权开始",
//								Toast.LENGTH_SHORT).show();
						System.out.println("授权开始");
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
//						Toast.makeText(LoginActivity.this, "授权失败",
//								Toast.LENGTH_SHORT).show();
						System.out.println("授权失败");
					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						// 获取uid
						System.out.println("授权成功，获取用户信息");
						System.out.println("value第三方" + value);
						String uid = value.getString("uid");
						String access_token = value.getString("access_token");
						String openid = value.getString("openid");
						
						PreferencesUtils.putString(ma, "QQ_oauth_consumer_key", uid);
						PreferencesUtils.putString(ma, "QQ_access_token", access_token);
						PreferencesUtils.putString(ma, "QQ_openid", openid);
						
						if (!TextUtils.isEmpty(uid)) {
							// uid不为空，获取用户信息
							getUserInfo(platform);
						} else {
//							Toast.makeText(LoginActivity.this, "授权失败...",
//									Toast.LENGTH_LONG).show();
							System.out.println("授权失败");
						}
					}

					@Override
					public void onCancel(SHARE_MEDIA platform) {
//						Toast.makeText(LoginActivity.this, "授权取消",
//								Toast.LENGTH_SHORT).show();
						System.out.println("授权取消");
					}
				});
	}

	/**
	 * 获取用户信息
	 * 
	 * @param platform
	 */
	private void getUserInfo(SHARE_MEDIA platform) {
		final SHARE_MEDIA platform_type = platform;
		mController.getPlatformInfo((MainMenuActivity) activity, platform,
				new UMDataListener() {

					private String thirdType;
					private String name;
					private String avatar;
					private String access_token;

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(int status, Map<String, Object> info) {
						// String showText = "";
						// if (status == StatusCode.ST_CODE_SUCCESSED) {
						// showText = "用户名：" +
						// info.get("screen_name").toString();
						// Log.d("#########", "##########" + info.toString());
						// } else {
						// showText = "获取用户信息失败";
						// }

						if (info != null) {
//							Toast.makeText(LoginActivity.this, info.toString(),
//									Toast.LENGTH_SHORT).show();
							System.out.println("第三方登录" + info.toString());
							if (SHARE_MEDIA.QQ.equals(platform_type)) {
								thirdType = "2";
								name = info.get("screen_name").toString();
								avatar = info.get("profile_image_url")
										.toString();
							}
//							dao.doThirdLogin(thirdType, uid, access_token,
//									name, avatar);
						}
					}
				});
	}
	// 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 根据requestCode获取对应的SsoHandler
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				resultCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

}
