package com.hike.digitalgymnastic;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;

import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
/*
 * changqi
 */

@ContentView(R.layout.activity_scanhelp)
public class DeviceScanHelpActivity extends BaseActivity {
	@ViewInject(R.id.root)
	View root;
	@OnClick(value={R.id.root})
	public void onClick(View view){
		switch (view.getId()) {
		case R.id.root:
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ViewUtils.inject(this);
		init();

	}

	private void init() {
	}
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			application.unBinder();
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
