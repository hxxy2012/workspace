package com.hike.digitalgymnastic;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.service.BLEDataType;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

@ContentView(R.layout.activity_deviceconnect)
public class DeviceConnectActivity extends BluetoothActivity implements
		UIHandler {
	HikoDigitalgyApplication application;
	@ViewInject(R.id.iv_connecting)
	ImageView iv_connecting;
	private BaseDao dao;

	@OnClick(value = {R.id.btn_conntect_nopause})
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.btn_conntect_nopause:
				jump2HomePage();
				break;

			default:
				break;
		}
	}

	public static String TAG = "DeviceConnectActivity";

	private AnimationDrawable animationDrawable;
	String mDeviceMac;
	BluetoothDevice mDevice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
	}

	ConnecttingTask task;
	boolean isFromRegister;

	private void init() {
		dao = new BaseDao(this, this);
		isFromRegister = getIntent().getBooleanExtra(Constants.isRegister, false);
		application = (HikoDigitalgyApplication) getApplication();
		mDevice = getIntent().getParcelableExtra(Constants.deviceName);
		mDeviceMac = Utils.disposeAdress(mDevice.getAddress());
		animationDrawable = (AnimationDrawable) iv_connecting.getDrawable();
		application.registerUIHandler(this);
		dao.CheckBindWach(mDeviceMac);

		task = new ConnecttingTask();
//		task.execute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		application.registerUIHandler(this);
	}


	// 匹配手环线程
	class ConnecttingTask extends AsyncTask<Void, Void, Boolean> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			animationDrawable.start();
		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			boolean result = false;
			try {
//				 result=application.getBinder().syncBindDevice(device_name);
				result = application.connecting(mDevice.getAddress());
				for (int i = 0; i < 10; i++) {
					Thread.currentThread().sleep(3000);
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					Thread.currentThread().sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			return result;
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			super.onCancelled();
			animationDrawable.stop();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
//			 animationDrawable.stop();
//			 if(!result){
//			 jump2DeviceFailedPage();
//			 }
		}
	}

	private void jump2HomePage() {
		Intent intent = new Intent(DeviceConnectActivity.this,
				MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void jump2DeviceConfirmPage() {
		application.unRegisterUIHandler();
		Intent intent = new Intent(this, DeviceConfirmPage.class);
		intent.putExtra(Constants.deviceName, mDevice);
		intent.putExtra(Constants.isRegister, isFromRegister);
		startActivity(intent);
		finish();
	}

	private void jump2DeviceFailedPage() {
		application.unRegisterUIHandler();
		Intent intent = new Intent(this, DeviceFailedPage.class);
		intent.putExtra(Constants.isRegister, isFromRegister);
		startActivity(intent);
		finish();
	}

	@Override
	public void handlerUI(Message msg) {

		if (!DeviceConnectActivity.this.isFinishing()) {
			switch (msg.what) {
				case BLEDataType.BLE_ISREADY_CODE:

					if (task != null && task.isCancelled()) {
						task.cancel(true);
					}
					jump2DeviceConfirmPage();
					break;
				case BLEDataType.BLE_CONNECTTIMEOUT_CODE:
					if (task != null && task.isCancelled()) {
						task.cancel(true);
					}
					jump2DeviceFailedPage();
					break;
				case BLEDataType.BLE_DISCONNECT_CODE:
					if (task != null && task.isCancelled()) {
						task.cancel(true);
					}
					jump2DeviceFailedPage();
					break;
				default:
					if (task != null && task.isCancelled()) {
						task.cancel(true);
					}
					jump2DeviceFailedPage();
					break;
			}
		} else {
			if (msg.what == BLEDataType.BLE_ISREADY_CODE) {
				application.unBinder();
			}
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			application.unBinder();
			if (isFromRegister)
				return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		if(requestCode == 800){
			int isbind = Integer.parseInt(PreferencesUtils.getString(this, Contants.ISBIND, "0"));
			if(isbind==0){
				task.execute();
			}else{
//				task.execute();
				//已经绑定手环的处理，跳转到失败页面
				if (task != null && task.isCancelled()) {
					task.cancel(true);
				}
				if (isbind==1){
					Toast.makeText(this,"您的手环已被绑定",Toast.LENGTH_LONG).show();
				}
				jump2DeviceFailedPage();
			}

		}

	}
}
