package com.hiko.enterprisedigital;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;

import com.hike.digitalgymnastic.BaseActivity;
import com.hike.digitalgymnastic.GuiderActivity;
import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.MainFragment;
import com.hike.digitalgymnastic.entitiy.AppVersion;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.NetworkUtil;
import com.hike.digitalgymnastic.utils.Utils;
import com.lidroid.xutils.http.ResponseInfo;

/**
 * @author way 预先加载数据的SplashActivity
 */
public class SpalshActivity extends BaseActivity {
	HikoDigitalgyApplication application;
	Customer customer;
	private TextView tv_version_code;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// initService();

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
		tv_version_code = (TextView)findViewById(R.id.tv_version_code);
		application = (HikoDigitalgyApplication) getApplication();
		 customer = LocalDataUtils
				.readCustomer(SpalshActivity.this);
		BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
		if (adapter != null)
			adapter.isEnabled();

		if (NetworkUtil.isNetwork(SpalshActivity.this)) {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {

					versionDao.GetAppVersion();
				

					if (!TextUtils.isEmpty(customer.getId())) {
						reportDao.CreportLaunch();// 客户上报
					} else {
						reportDao.TreportLaunch();// 游客上报
					}

				}
			}, 200);

		} else {
			Utils.showMessage(getApplicationContext(),
					getString(R.string.no_connect_str));
			jump2MainPage();
		}
		tv_version_code.setText(getVersion());

	}

	BaseDao reportDao = new BaseDao(new INetResult() {

		@Override
		public void onResponseReceived(int requestCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(int requestCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestFaild(String errorNo, String errorMessage) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onNoConnect() {
			// TODO Auto-generated method stub

		}
	}, this);
	BaseDao versionDao = new BaseDao(new INetResult() {

		@Override
		public void onResponseReceived(int requestCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(int requestCode) {
			// TODO Auto-generated method stub
			if (requestCode == 4) {// 最新版本信息返回
				AppVersion appVersion = dao.getAppVersion();
				application.appVersion = appVersion;
				dao.GetCustomer();
			}
		}

		@Override
		public void onRequestFaild(String errorNo, String errorMessage) {
			// TODO Auto-generated method stub
			dao.GetCustomer();
		}

		@Override
		public void onNoConnect() {
			// TODO Auto-generated method stub
			jump2MainPage();
		}
	}, this);

	BaseDao dao = new BaseDao(new INetResult() {

		@Override
		public void onResponseReceived(int requestCode) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(ResponseInfo responseInfo) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onRequestSuccess(int requestCode) {
			if (requestCode == 8) {
				Customer customer = dao.getCustomer();
				LocalDataUtils.saveCustomer(SpalshActivity.this, customer);
				if (TextUtils.isEmpty(customer.getGender())
						|| TextUtils.equals("0", customer.getGender())) {// 如果性别为0，说明没有填写个人信息，跳转到个人信息填写页
					jump2MessagePage();
				} else {
					jump2MainPage();
				}
			}
		}

		@Override
		public void onRequestFaild(String errorNo, String errorMessage) {
			// TODO Auto-generated method stub
			jump2MainPage();
		}

		@Override
		public void onNoConnect() {
			// TODO Auto-generated method stub
			jump2MainPage();

		}
	}, this);

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

	}


	private void jump2MainPage() {
		if (this.isFinishing()) {
			return;
		}
		boolean isLoged = LocalDataUtils.getLoginInfo(this);
		if (isLoged) {// 已经登陆过，直接进入到主页
			Intent intent = new Intent(this, MainActivity.class);
			startActivity(intent);
			finish();

		} else {
			Customer customer = LocalDataUtils.readCustomer(this);
			if (TextUtils.isEmpty(customer.getId())) {// 如果用户id为空，说明没有注册，进入引导页
				Intent intent = new Intent(this, GuiderActivity.class);
				startActivity(intent);
				finish();
			} else if (TextUtils.isEmpty(customer.getGender())
					|| TextUtils.equals("0", customer.getGender())) {// 如果性别为0，说明没有填写个人信息，跳转到个人信息填写页
				jump2MessagePage();
			} else {
				Intent intent = new Intent(this, GuiderActivity.class);
				startActivity(intent);
				finish();
			}

		}
	}

	private void jump2MessagePage() {
		Intent intent = new Intent(this, MainFragment.class);
		intent.putExtra("fromWhichPage", MainFragment.fromRegister);
		startActivity(intent);
		finish();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		return super.onKeyDown(keyCode, event);
	}
	/**
	 * 获取版本号
	 *
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
		try {
			PackageManager manager = (this.getPackageManager());
			PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
			String version = info.versionName;
			return this.getString(R.string.version_name) + version;
		} catch (Exception e) {
			e.printStackTrace();
			return this.getString(R.string.can_not_find_version_name);
		}
	}
}