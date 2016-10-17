package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

public class HomePageListView extends ListView {
	private OnScrollTopListener onScrollTopListener;
	
	public OnScrollTopListener getOnScrollTopListener() {
		return onScrollTopListener;
	}

	public void setOnScrollTopListener(OnScrollTopListener onScrollTopListener) {
		this.onScrollTopListener = onScrollTopListener;
	}

	public HomePageListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public HomePageListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public HomePageListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	float lastY;
	boolean isChanging=false;
	boolean isTop=false;
	public void resetState(){
		postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isChanging=false;
			}
		}, 1000);
		
	}
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		
//		switch (ev.getAction()) {
//		case MotionEvent.ACTION_DOWN:
//			lastY=ev.getY();
////			isChanging=false;
//			if(getFirstVisiblePosition() == 0){
//				isTop=true;
//			}else
//				isTop=false;
//			break;
//		case MotionEvent.ACTION_UP:
//			
//
//			break;
//		case MotionEvent.ACTION_MOVE:
//			float newY=ev.getY();
//			if(getFirstVisiblePosition() == 0&&getChildCount()>0&&getChildAt(0).getY()-getY()<10){
//				if(newY-lastY>150){
//					if(!isChanging&&isTop){
//					 onScrollTopListener.onScrollTop();
//					 isChanging=true;
//					}
//				}
//			}else{
//				if(newY-lastY>150){
//					lastY=newY;
//				}
//			}
//			break;
//		default:
//			break;
//		}
//		return super.onTouchEvent(ev);
		return false;
	}
	public interface OnScrollTopListener{
		public void onScrollTop();
	}
}
