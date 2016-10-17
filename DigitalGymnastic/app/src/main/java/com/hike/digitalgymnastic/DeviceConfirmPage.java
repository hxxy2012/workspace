package com.hike.digitalgymnastic;

import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.BleData;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.service.BLEDataType;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;

@ContentView(R.layout.activity_deviceconfirm)
public class DeviceConfirmPage extends BluetoothActivity implements UIHandler {
	HikoDigitalgyApplication application;
	@ViewInject(R.id.btn_changedevice)
	Button btn_changedevice;
	@ViewInject(R.id.btn_alermnotlight)
	Button btn_alermnotlight;
	@ViewInject(R.id.tv_desc)
	TextView tv_desc;

	@OnClick(value = { R.id.btn_changedevice, R.id.btn_alermnotlight })
	public void onclick(View view) {
		switch (view.getId()) {
		case R.id.btn_changedevice:
			jump2DeviceList();
			break;

		case R.id.btn_alermnotlight:
			jump2LightGuider();
			break;

		default:
			break;
		}
	}

	Customer customer;
	ArrayList<String> deviceList;
	String device_name;
	BluetoothDevice device;

	boolean isFirstEntered = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		ViewUtils.inject(this);

		init();
	}

	public boolean isBinding = true;
	boolean isFromRegister;
	BaseDao  dao;
	private void init() {
		dao = new BaseDao(this, this);
		isFromRegister=getIntent().getBooleanExtra(Constants.isRegister, false);
		
		isFirstEntered = LocalDataUtils.getIsFirstEntered(this);
		customer = LocalDataUtils.readCustomer(this);
		application = (HikoDigitalgyApplication) getApplication();
		device = getIntent().getParcelableExtra(Constants.deviceName);

		device_name = Utils.disposeAdress(device.getAddress());
		String s = "找到" + device.getName() + "的手环";
		tv_desc.setText(s);

		dao.BindWach(device_name);
	}

	@Override
	protected void onResume() {
		super.onResume();
		application.registerUIHandler(this);
	}

	private void bindUser() {
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				application.registerUIHandler(DeviceConfirmPage.this);
				application.bindMacCacher = device.getAddress();
				application.bindUser();
			}
		}, 1000);

	}



	private void jump2DeviceList() {
		backAction();
		Intent intent = new Intent(DeviceConfirmPage.this,
				DeviceListActivity.class);
		startActivity(intent);
		finish();
	}

	private void jump2LightGuider() {
		Intent intent = new Intent(DeviceConfirmPage.this,
				LightGuiderActivity.class);
		startActivity(intent);
	}

	@Override
	public void handlerUI(Message msg) {
		application.unRegisterUIHandler();
		if (BLEDataType.BLE_BIND_CODE == msg.what) {// 绑定返回值
			BleData<String> bind = (BleData<String>) msg.obj;
			if(bind.isTimeOut()){
				Utils.showMessage(getApplicationContext(), getString(R.string.string_connect_time_out));
				return ;
			}
			int code = Integer.parseInt(bind.getData());
			if (code == 0) {
				// 手环与手机绑定成功，此时向服务器绑定
				dao.BindWach(device.getAddress().toUpperCase().trim());
			} else {
				Utils.showMessage(this, bind.getDesc());
			}
		}
	}
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		Utils.showMessage(this, getString(R.string.string_bind_success));
		String mac = device.getAddress();
		application.bindMAC = mac;
		application.name = mac;
		Intent intent = null;
		if (!isFirstEntered){
			intent = new Intent(DeviceConfirmPage.this, MainActivity.class);
			intent.putExtra(Contants.WHERE_FROM,Contants.FROM_DEVICE_CONFIRM_PAGE);
		}
		else {
			intent = new Intent(DeviceConfirmPage.this,
					GuiderQixieActivity.class);
		}
		LocalDataUtils.saveBindMAC(DeviceConfirmPage.this, mac);
		LocalDataUtils.saveBindName(DeviceConfirmPage.this, mac);
		LocalDataUtils.saveBindTag(DeviceConfirmPage.this, device.getAddress().toUpperCase().trim());
		LocalDataUtils.saveConnectedInfo(DeviceConfirmPage.this, true);
		startActivity(intent);

		finish();

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backAction();
			if(isFromRegister)
				return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	private void  backAction(){
		//断开连接
		application.unBinder();
	}
}
