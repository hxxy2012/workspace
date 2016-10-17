package com.hike.digitalgymnastic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.db.DBManager;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.tools.AnimUtil;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.ProgressHUD;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners;
import com.umeng.socialize.exception.SocializeException;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.sso.UMSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/*
 * changqi
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity implements
		View.OnClickListener {
	// 整个平台的Controller,负责管理整个SDK的配置、操作等处理
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Contants.DESCRIPTOR);
	Intent intent;
	Boolean isFormResetPwd = false;
	HttpConnectUtils utils;
	String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。

	/**
	 * 手机号码 移动：134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188
	 * 联通：130,131,132,152,155,156,185,186 电信：133,1349,153,180,189
	 */
	String MOBILE_Regex = "^1(3[0-9]|5[0-35-9]|8[025-9]|78|47)\\d{8}$";
	/**
	 * 10 * 中国移动：China Mobile 11 *
	 * 134[0-8],135,136,137,138,139,150,151,157,158,159,182,187,188 12
	 */
	String CM_Regex = "^1(34[0-8]|(3[5-9]|5[017-9]|8[2378]|45|76)\\d)\\d{7}$";
	/**
	 * 15 * 中国联通：China Unicom 16 * 130,131,132,152,155,156,185,186 17
	 */
	String CU_Regex = "^1(3[0-2]|5[256]|8[56]|81|77|70)\\d{8}$";
	/**
	 * 20 * 中国电信：China Telecom 21 * 133,1349,153,180,189 22
	 */
	String CT_Regex = "^1((33|53|8[09])[0-9]|349)\\d{7}$";
	/**
	 * 25 * 大陆地区固话及小灵通 26 * 区号：010,020,021,022,023,024,025,027,028,029 27 *
	 * 号码：七位或八位 28
	 */
	// NSString * PHS = @"^0(10|2[0-5789]|\\d{3})\\d{7,8}$";

	@ViewInject(R.id.et_mobile)
	TextView et_mobile;

	@ViewInject(R.id.tv_clause)
	TextView tv_clause;

	@ViewInject(R.id.btn_register)
	Button btn_register;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	// 第三方登录

	@ViewInject(R.id.ll_other_text)
	private LinearLayout ll_other_text;
	@ViewInject(R.id.rl_other_login)
	private RelativeLayout rl_other_login;

	@ViewInject(R.id.iv_qq_login)
	private ImageView iv_qq_login;
	@ViewInject(R.id.iv_wb_login)
	private ImageView iv_wb_login;
	@ViewInject(R.id.iv_wx_login)
	private ImageView iv_wx_login;
	private BaseDao dao;
	private String uid;
	private String access_token;
	private String openid;
	private UMQQSsoHandler qqSsoHandler;
	private UMWXHandler wxHandler;
	private boolean isThirdLogin=false;
	private ProgressHUD progress;
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_register:
			Utils.closeInputMetherUI(this);
			String name = et_mobile.getText().toString();
			if (TextUtils.isEmpty(name)) {
				Utils.showMessage(this, "请输入正确手机号");
				// }else if(et_mobile.getText().toString().matches(telRegex)){
			} else if (et_mobile.getText().toString().matches(MOBILE_Regex)
					|| et_mobile.getText().toString().matches(CM_Regex)
					|| et_mobile.getText().toString().matches(CU_Regex)
					|| et_mobile.getText().toString().matches(CT_Regex)) {
				String type;
				if (isFormResetPwd)
					type = Constants.isForgetPwd;
				else
					type = Constants.isRegister;
				showProgress(this,true);
				dao.GetVerifyCode(et_mobile.getText().toString(), type);
			} else {
				Utils.showMessage(RegisterActivity.this,
						getString(R.string.error_mobile_insert));
			}
			break;
		case R.id.btn_left:
			BtnBack();
			break;
		case R.id.ll_btn_left:
			BtnBack();
			break;

			case R.id.iv_qq_login:
				boolean isqqInstall = qqSsoHandler.isClientInstalled();
				if(isqqInstall){

					login(SHARE_MEDIA.QQ);
				}else{
					Utils.showMessage(this, "您未安装QQ客户端！");
				}
				break;
			case R.id.iv_wb_login:
				login(SHARE_MEDIA.SINA);
				break;
			case R.id.iv_wx_login:
				boolean iswxInstall = wxHandler.isClientInstalled();
				if(iswxInstall){
					login(SHARE_MEDIA.WEIXIN);
				}else{
					Utils.showMessage(this, "您未安装微信客户端！");
				}

				break;
		default:
			break;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Utils.closeInputMetherUI(this);
		ViewUtils.inject(this);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Utils.setTranslucentStatus(true,this);
//		}
		PwdSetActivity.activityList.add(this);
		init(savedInstanceState);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putBoolean(Constants.isFormResetPwd, isFormResetPwd);
	}

	private void init(Bundle savedInstanceState) {
//		progress = new ProgressHUD(this);
		dao = new BaseDao(this, this);
		if (savedInstanceState == null) {
			isFormResetPwd = getIntent().getBooleanExtra(
					Constants.isFormResetPwd, isFormResetPwd);
		} else {
			isFormResetPwd = savedInstanceState
					.getBoolean(Constants.isFormResetPwd);
		}
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		if (isFormResetPwd) {
			// 忘记密码页面进入
			title.setText(getString(R.string.title_pwd_find));
			et_mobile.setHint("请输入你的注册手机号");
			btn_register.setText(getString(R.string.validcode_get));
			btn_register.setBackgroundResource(R.mipmap.btn_disabled);
			tv_clause.setVisibility(View.INVISIBLE);
			rl_other_login.setVisibility(View.INVISIBLE);
			ll_other_text.setVisibility(View.INVISIBLE);
		} else {// 注册按钮进入
			title.setText(getString(R.string.register));
			et_mobile.setHint("请输入11位手机号");
			btn_register.setBackgroundResource(R.mipmap.btn_disabled);
			tv_clause.setVisibility(View.VISIBLE);
		}
		// dialog=new MyProgressDialog(RegisterActivity.this,
		// R.style.dialogTheme);
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		SpannableString sp = new SpannableString(getString(R.string.alarminfo));
		// utils=HttpConnectUtils.getInstance(this);
		String url = HttpConnectUtils.ip + "gym-api/agreements/protocol.html";
		sp.setSpan(new URLSpan(url), 17, 21, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		sp.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.light_green)), 16, 22,
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_clause.setText(sp);
		tv_clause.setMovementMethod(LinkMovementMethod.getInstance());

		btn_left.setOnClickListener(this);
		ll_btn_left.setOnClickListener(this);
		// et_mobile.addTextChangedListener(watcher);
		btn_register.setOnClickListener(RegisterActivity.this);
		btn_register.setBackgroundResource(R.drawable.btn_selector);

		iv_qq_login.setOnClickListener(this);
		iv_wb_login.setOnClickListener(this);
		iv_wx_login.setOnClickListener(this);

		// 添加新浪sso授权
		mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加QQ、QZone平台
		addQQQZonePlatform();

		// 添加微信、微信朋友圈平台
		addWXPlatform();
	}
	/**
	 * @功能描述 : 添加QQ平台支持 QQ分享的内容， 包含四种类型， 即单纯的文字、图片、音乐、视频. 参数说明 : title, summary,
	 *       image url中必须至少设置一个, targetUrl必须设置,网页地址必须以"http://"开头 . title :
	 *       要分享标题 summary : 要分享的文字概述 image url : 图片地址 [以上三个参数至少填写一个] targetUrl
	 *       : 用户点击该分享时跳转到的目标地址 [必填] ( 若不填写则默认设置为友盟主页 )
	 * @return
	 */
	private void addQQQZonePlatform() {
//		String appId = "1104196320";
//		String appKey = "fNqdv6qeiztm9fZO";
//		getString(R.string.qq_appid_fit), getString(R.string.qq_appkey_fit)
		String appId = getString(R.string.qq_appid);
		String appKey = getString(R.string.qq_appkey);
		// 添加QQ支持, 并且设置QQ分享内容的target url
		qqSsoHandler = new UMQQSsoHandler(this,
				appId, appKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				this, appId, appKey);
		qZoneSsoHandler.addToSocialSDK();
	}
	/**
	 * @功能描述 : 添加微信平台分享
	 * @return
	 */
	private void addWXPlatform() {
		// 注意：在微信授权的时候，必须传递appSecret
		// wx967daebe835fbeac是你在微信开发平台注册应用的AppID, 这里需要替换成你注册的AppID
		//
//		String appId = "wxbaeb54da45c2178d";
//		String appSecret = "4dc4635a81bd5d84f1ead13aacab08e1";
//		getString(R.string.wx_appid_fit), getString(R.string.wx_secret_fit)
		//techfit
//		String appId = getString(R.string.wx_appid_fit);
//		String appSecret = getString(R.string.wx_secret_fit);
		String appId=getString(R.string.wx_appid);
		String appSecret = getString(R.string.wx_secret);
		// 添加微信平台
		wxHandler = new UMWXHandler(this, appId,
				appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(this,
				appId, appSecret);
		wxCircleHandler.setToCircle(true);
		wxCircleHandler.addToSocialSDK();
	}
	// 如果有使用任一平台的SSO授权, 则必须在对应的activity中实现onActivityResult方法, 并添加如下代码
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 根据requestCode获取对应的SsoHandler
		UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(
				requestCode);
		if (ssoHandler != null) {
			ssoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 注销本次登陆
	 *
	 * @param platform
	 */
	private void logout_third(final SHARE_MEDIA platform) {
		mController.deleteOauth(this, platform,
				new SocializeListeners.SocializeClientListener() {

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(int status, SocializeEntity entity) {
						String showText = "解除" + platform.toString() + "平台授权成功";
						if (status != StatusCode.ST_CODE_SUCCESSED) {
							showText = "解除" + platform.toString() + "平台授权失败["
									+ status + "]";
						}
//						Toast.makeText(LoginActivity.this, showText,
//								Toast.LENGTH_SHORT).show();
					}
				});
	}
	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
		}

		@Override
		public void beforeTextChanged(CharSequence value, int arg1, int arg2,
				int arg3) {

		}

		@Override
		public void afterTextChanged(Editable editable) {
			if (editable.toString().length() == 11) {
				btn_register.setOnClickListener(RegisterActivity.this);
				btn_register.setBackgroundResource(R.drawable.btn_selector);
			} else {
				btn_register.setOnClickListener(null);
				btn_register.setBackgroundResource(R.mipmap.btn_disabled);
			}
		}
	};

	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		super.onRequestFaild(errorNo, errorMessage);
		showProgress(this,false);
		if (errorNo.equals("3")) {
			try {
				Intent intent = new Intent(this, ValidCodeImageActivity.class);
				intent.putExtra("phone", et_mobile.getText().toString().trim());
				intent.putExtra("isFormResetPwd", isFormResetPwd);
				startActivity(intent);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (errorNo.equals("9999")) {
			if (TextUtils.equals("验证码已发送,请您耐心等待", errorMessage)) {
				Intent intent = new Intent(RegisterActivity.this,
						ValidCodeActivity.class);
				intent.putExtra(Constants.mobile, et_mobile.getText()
						.toString());
				intent.putExtra(Constants.isFormResetPwd, isFormResetPwd);
				startActivity(intent);
			} 
		} 
	}

	List<Class> cList = new ArrayList<Class>();

	private void initDBClass() {
	}
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(this,false);
		switch (requestCode){
			case 0:
				new Thread(new Runnable() {

					@Override
					public void run() {
						LocalDataUtils.saveLoginInfo(RegisterActivity.this, et_mobile
								.getText().toString().trim(), LocalDataUtils.getLoginToken(RegisterActivity.this).trim());
						// TODO Auto-generated method stub
						DBManager manager = DBManager
								.getIntance(RegisterActivity.this, LocalDataUtils
										.readCustomer(RegisterActivity.this).getId());
						manager.createTable(cList);
						handler.sendEmptyMessage(0);
					}
				}).start();
				break;
			case 66:
				Intent intent = new Intent(RegisterActivity.this,
						ValidCodeActivity.class);
				intent.putExtra(Constants.mobile, et_mobile.getText().toString());
				intent.putExtra(Constants.isFormResetPwd, isFormResetPwd);
				intent.putExtra(Constants.vertifyCode, dao.getVerifyCode()
						.getVerifyCode());
				startActivity(intent);
				break;
		}

	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
			if (TextUtils.isEmpty(dao.getCustomer().getGender())
					|| TextUtils.equals("0", dao.getCustomer().getGender())) {
				intent = new Intent(RegisterActivity.this, MainFragment.class);
				intent.putExtra("fromWhichPage", MainFragment.fromRegister);
			}
			startActivity(intent);
			finish();
			Utils.showMessage(RegisterActivity.this,
					getString(R.string.suss_login));
//			showProgress(false);
		}
	};
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			BtnBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void BtnBack() {
		if (isFormResetPwd) {// 忘记密码页面进入
			finish();
		} else {
			startActivity(new Intent(this, GuiderActivity.class));
			finish();
			AnimUtil.intentPushDown(this);
		}
	}
	/**
	 * 授权。如果授权成功，则获取用户信息
	 *
	 * @param platform
	 */
	private void login(final SHARE_MEDIA platform) {
		mController.doOauthVerify(this, platform,
				new SocializeListeners.UMAuthListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
						System.out.println("授权开始");
						progress = ProgressHUD.show(RegisterActivity.this, "加载中", true, true, null);
						isThirdLogin = true;
					}

					@Override
					public void onError(SocializeException e,
										SHARE_MEDIA platform) {
						System.out.println("授权失败" + e);
						progressdismiss();

					}

					@Override
					public void onComplete(Bundle value, SHARE_MEDIA platform) {
						isThirdLogin = false;
						// 获取uid
						System.out.println("授权成功，获取用户信息");
						System.out.println("value第三方" + value);
						uid = value.getString("uid");
						access_token = value.getString("access_token");
						openid = value.getString("openid");

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
						progressdismiss();
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
		mController.getPlatformInfo(this, platform,
				new SocializeListeners.UMDataListener() {

					private String thirdType;
					private String name;
					private String avatar;

					@Override
					public void onStart() {

					}

					@Override
					public void onComplete(int status, Map<String, Object> info) {

						System.out.println("status==" + status);
						if (info != null) {

							System.out.println("第三方登录" + info.toString());
							if (SHARE_MEDIA.QQ.equals(platform_type)) {
								thirdType = "2";
								name = info.get("screen_name").toString();
								avatar = info.get("profile_image_url")
										.toString();
							} else if (SHARE_MEDIA.WEIXIN.equals(platform_type)) {
								thirdType = "1";
								name = info.get("nickname").toString();
								avatar = info.get("headimgurl").toString();
							} else if (SHARE_MEDIA.SINA.equals(platform_type)) {
								thirdType = "3";
								name = info.get("screen_name").toString();
								avatar = info.get("profile_image_url")
										.toString();
								access_token = info.get("access_token")
										.toString();
							}

							dao.doThirdLogin(thirdType, uid, access_token,
									name, avatar);
							progressdismiss();
						}
					}
				});
	}
	@Override
	protected void onResume() {
		super.onResume();
		if (isThirdLogin){
			progressdismiss();
		}
	}
	private synchronized void progressdismiss(){
		try{
			if(progress!=null&&progress.isShowing())
				progress.dismiss();
		}catch(Exception e){

		}
	}
}
