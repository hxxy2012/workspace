package com.hike.digitalgymnastic;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import com.hiko.enterprisedigital.R;
/*
 * @auth changqi
 * @descirption 指示灯提示页面
 */

@ContentView(R.layout.dialog_lightguider)
public class LightGuiderActivity extends BaseActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		ViewUtils.inject(this);
	}
	 // 实现onTouchEvent触屏函数但点击屏幕时销毁本Activity  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        finish();  
        return true;  
    }  
}
