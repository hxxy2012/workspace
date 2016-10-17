package com.hike.digitalgymnastic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.db.DBManager;
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
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.bean.StatusCode;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.controller.listener.SocializeListeners.SocializeClientListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMAuthListener;
import com.umeng.socialize.controller.listener.SocializeListeners.UMDataListener;
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

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {
	// 整个平台的Controller,负责管理整个SDK的配置、操作等处理
	private UMSocialService mController = UMServiceFactory
			.getUMSocialService(Contants.DESCRIPTOR);
	@SuppressWarnings("rawtypes")
	String telRegex = "[1][3578]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
	@ViewInject(R.id.et_mobile)
	private EditText et_mobile;
	@ViewInject(R.id.et_password)
	private EditText et_password;
	@ViewInject(R.id.btn_login)
	private Button btn_login;
	@ViewInject(R.id.btn_register)
	private Button btn_register;
	@ViewInject(R.id.btn_forget_pwd)
	private Button btn_forget_pwd;
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
	// 第三方登录
	@ViewInject(R.id.iv_qq_login)
	private ImageView iv_qq_login;
	@ViewInject(R.id.iv_wb_login)
	private ImageView iv_wb_login;
	@ViewInject(R.id.iv_wx_login)
	private ImageView iv_wx_login;

	private ProgressHUD progress;
	BaseDao dao;

	@OnClick(value = { R.id.btn_login, R.id.btn_forget_pwd, R.id.btn_left,
			R.id.ll_btn_left, R.id.btn_right, R.id.iv_mobile, R.id.iv_pwd,
			R.id.iv_qq_login, R.id.iv_wb_login, R.id.iv_wx_login })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_login:
			if (check()) {// 开始登陆
				showProgress(this,true);
				dao.Login(et_mobile.getText().toString(),
						Utils.md5(et_password.getText().toString()));
			}
			break;
		case R.id.btn_forget_pwd:
			Intent intent = new Intent(LoginActivity.this,
					RegisterActivity.class);
			intent.putExtra(Constants.isFormResetPwd, true);
			startActivity(intent);
			break;
		case R.id.btn_left:
			btnBack();
			break;
		case R.id.ll_btn_left:
			btnBack();
			break;
		case R.id.iv_mobile:
			Utils.showMessage(this, getString(R.string.mobile_insert));
			break;
		case R.id.iv_pwd:
			Utils.showMessage(this, getString(R.string.pwd_insert));
			break;
		case R.id.iv_qq_login:
			boolean isqqInstall = qqSsoHandler.isClientInstalled();
			if(isqqInstall){
				
				login(SHARE_MEDIA.QQ);
			}else{
				Utils.showMessage(LoginActivity.this, "您未安装QQ客户端！");
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
				Utils.showMessage(LoginActivity.this, "您未安装微信客户端！");
			}
			
			break;
		default:
			break;
		}
	}

	private void btnBack() {
		startActivity(new Intent(LoginActivity.this, GuiderActivity.class));
		finish();
		AnimUtil.intentPushDown(LoginActivity.this);
	}

	private boolean check() {
		if (et_mobile.getText().toString().length() == 0) {
			// Utils.showMessage(this, getString(R.string.error_mobilerule));
			Utils.showMessage(this, "用户名不能为空");
			return false;
		} else if (et_mobile.getText().toString().length() < 11) {
			Utils.showMessage(this, getString(R.string.error_mobilerule));
			return false;
		} else if (!et_mobile.getText().toString().matches(telRegex)) {
			Utils.showMessage(this, getString(R.string.error_mobilerule));
			return false;
		} else if (et_password.getText().toString().length() == 0) {
			// Utils.showMessage(this, getString(R.string.error_pwd));
			Utils.showMessage(this, "密码不能为空");
			return false;
		}
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
//
//		getWindow().setSoftInputMode(
//				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//			Utils.setTranslucentStatus(true,this);
//		}
		ViewUtils.inject(this);
		init();
		// initDBClass();
	}

	private void init() {
//		progress = new ProgressHUD(this,R.style.ProgressHUD);
		initDBClass();
		dao = new BaseDao(this, this);
//		title.setTextSize(this.getResources().getDimension(R.dimen.x6));

		title.setText(getString(R.string.login));
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.INVISIBLE);
		et_mobile.setText(LocalDataUtils.readLoginInfo(this));
		et_password.setText(LocalDataUtils.readLoginPwdInfo(this));
		addListner();
		// 添加新浪sso授权
		 mController.getConfig().setSsoHandler(new SinaSsoHandler());
		// 添加QQ、QZone平台
		addQQQZonePlatform();

		// 添加微信、微信朋友圈平台
		addWXPlatform();
	}

	private void addListner() {
		// TODO Auto-generated method stub
		et_mobile.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				//用户名更改时，密码清空
				et_password.setText("");
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				
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
	List<Class> cList = new ArrayList<Class>();

	private void initDBClass() {
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);

		new Thread(new Runnable() {

			@Override
			public void run() {
				LocalDataUtils.saveLoginInfo(LoginActivity.this, et_mobile
						.getText().toString().trim(), et_password.getText()
						.toString().trim());
				// TODO Auto-generated method stub
				DBManager manager = DBManager
						.getIntance(LoginActivity.this, LocalDataUtils
								.readCustomer(LoginActivity.this).getId());
				manager.createTable(cList);
				handler.sendEmptyMessage(0);
			}
		}).start();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			btnBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			if (TextUtils.isEmpty(dao.getCustomer().getGender())
					|| TextUtils.equals("0", dao.getCustomer().getGender())) {
				intent = new Intent(LoginActivity.this, MainFragment.class);
				intent.putExtra("fromWhichPage", MainFragment.fromRegister);
			}
			startActivity(intent);
			finish();
			Utils.showMessage(LoginActivity.this,
					getString(R.string.suss_login));
//			showProgress(false);
		}
	};


	private String uid;
	private String access_token;
	private String openid;
	private UMQQSsoHandler qqSsoHandler;
	private UMWXHandler wxHandler;
	private boolean isThirdLogin=false;
	/**
	 * 授权。如果授权成功，则获取用户信息
	 * 
	 * @param platform
	 */
	private void login(final SHARE_MEDIA platform) {
		mController.doOauthVerify(LoginActivity.this, platform,
				new UMAuthListener() {

					@Override
					public void onStart(SHARE_MEDIA platform) {
						System.out.println("授权开始");
						progress=ProgressHUD.show(LoginActivity.this, "加载中", true, true, null);
						isThirdLogin = true;
					}

					@Override
					public void onError(SocializeException e,
							SHARE_MEDIA platform) {
						System.out.println("授权失败"+e);
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
		mController.getPlatformInfo(LoginActivity.this, platform,
				new UMDataListener() {

					private String thirdType;
					private String name;
					private String avatar;

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
						System.out.println("status=="+status);
						if (info != null) {
//							Toast.makeText(LoginActivity.this, info.toString(),
//									Toast.LENGTH_SHORT).show();
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
		qqSsoHandler = new UMQQSsoHandler(LoginActivity.this,
				appId, appKey);
		qqSsoHandler.setTargetUrl("http://www.umeng.com");
		qqSsoHandler.addToSocialSDK();

		// 添加QZone平台
		QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(
				LoginActivity.this, appId, appKey);
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
		wxHandler = new UMWXHandler(LoginActivity.this, appId,
				appSecret);
		wxHandler.addToSocialSDK();

		// 支持微信朋友圈
		UMWXHandler wxCircleHandler = new UMWXHandler(LoginActivity.this,
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
		mController.deleteOauth(LoginActivity.this, platform,
				new SocializeClientListener() {

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
}
