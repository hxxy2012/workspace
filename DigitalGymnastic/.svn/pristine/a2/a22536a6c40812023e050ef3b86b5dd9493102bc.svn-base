package com.hike.digitalgymnastic;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;

import com.hike.digitalgymnastic.tools.AnimUtil;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.view.CustomerVideoView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * changqi
 */
@ContentView(R.layout.activity_guider_qixie)
public class GuiderQixieActivity extends Activity implements View.OnTouchListener{

	@ViewInject(R.id.viewPager)
	private ViewPager mViewPager;
	
	ArrayList<View> list=new ArrayList<View>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
		
		
		
	}

	private void init(){
		LocalDataUtils.setIsFirstEntered(this, false);
		
		LinearLayout ll=new LinearLayout(this);
		ll.setBackgroundResource(R.mipmap.bg_guider_one);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		list.add(ll);
		
		ll=new LinearLayout(this);
		ll.setBackgroundResource(R.mipmap.bg_guider_two);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		list.add(ll);
		
		ll=new LinearLayout(this);
		ll.setBackgroundResource(R.mipmap.bg_guider_three);
		ll.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		ll.setOnTouchListener(this);
		list.add(ll);
		
	
		
		ViewPagerAdapter adapter=new ViewPagerAdapter(list);
		
		mViewPager.setAdapter(adapter);
		mViewPager.setOnPageChangeListener(listener);
	}
	
	
	public void onStart() {
		// Play Video
		super.onStart();
	}


	class ViewPagerAdapter extends PagerAdapter {
		//界面列表
	    private ArrayList<View> views;
	    
	    public ViewPagerAdapter (ArrayList<View> views){
	        this.views = views;
	    }
	       
		/**
		 * 获得当前界面数
		 */
		@Override
		public int getCount() {
			 if (views != null) {
	             return views.size();
	         }      
	         return 0;
		}

		/**
		 * 初始化position位置的界面
		 */
	    @Override
	    public Object instantiateItem(View view, int position) {
	       
	        ((ViewPager) view).addView(views.get(position), 0);
	       
	        return views.get(position);
	    }
	    
	    /**
		 * 判断是否由对象生成界面
		 */
		@Override
		public boolean isViewFromObject(View view, Object arg1) {
			return (view == arg1);
		}

		/**
		 * 销毁position位置的界面
		 */
	    @Override
	    public void destroyItem(View view, int position, Object arg2) {
	        ((ViewPager) view).removeView(views.get(position));       
	    }
	}

	

	ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
		@Override
		public void onPageScrolled(int i, float v, int i2) {
			switch (i) {
			case 0:
				
				break;
			case 1:
				break;
			case 2:
				
				break;
			default:
				break;
			}
		}

		@Override
		public void onPageSelected(int i) {
		}

		@Override
		public void onPageScrollStateChanged(int i) {
		}
	};

	
	

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	float lastX=-1;
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			break;
		case MotionEvent.ACTION_UP:
		
			break;
		case MotionEvent.ACTION_MOVE:
			if(lastX>event.getX()&&lastX-event.getX()>=10&& lastX != -1){
				lastX = -1;
				if(mViewPager.getCurrentItem()==2){
					Intent intent = new Intent(GuiderQixieActivity.this,
							MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
			break;

		default:
			break;
		}

		return true;
	}
}
