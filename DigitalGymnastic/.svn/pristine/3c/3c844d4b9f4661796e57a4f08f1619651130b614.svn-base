package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ViewFlipper;

public class MyViewFlipper extends ViewFlipper {

	public interface ViewFlipperOnTouchListener {
		public void onTouch(boolean opened);
	}

	ViewFlipperOnTouchListener listener;

	public ViewFlipperOnTouchListener getListener() {
		return listener;
	}

	public void setListener(ViewFlipperOnTouchListener listener) {
		this.listener = listener;
	}

	public MyViewFlipper(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public MyViewFlipper(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	float lastY = -1;

	public boolean isScrolling() {
		return isScrolling;
	}

	boolean  isScrolling=false;
	public void setScrolling(boolean  isScrolling){
		this.isScrolling=isScrolling;
	}

	boolean isOpened = false;//底部控件显示状态
	boolean isFirstEnter=true;

	public void setOpened(boolean isOpened) {
		this.isOpened = isOpened;
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub

		return super.dispatchTouchEvent(ev);
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isScrolling)
			return true;

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = event.getY();
			break;
		case MotionEvent.ACTION_UP:

			break;
		case MotionEvent.ACTION_MOVE:
			float newY = event.getY();
			if (lastY - newY > 20 && lastY != -1 && !isOpened) {// 向上滑动并且当前显示第一个页面，则shownext
				if (listener != null) {
					
					listener.onTouch(true);
					lastY=-1;
					return true;
				}
			}else
			if (newY - lastY > 20 && lastY != -1 && isOpened) {// 向下滑动并且当前显示第二个页面，则showpre
				if (listener != null) {
				
					listener.onTouch(false);
					lastY=-1;
					return true;
				}
			}else{
				return super.onTouchEvent(event);
			}

			break;
		default:
			break;
		}
		return true;
	}


	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {

		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastY = event.getY();
				break;
			case MotionEvent.ACTION_UP:
				break;
			case MotionEvent.ACTION_MOVE:
				float newY = event.getY();
				if(getChildCount()>1){
					ScrollViewInner scrollViewInner=(ScrollViewInner)getChildAt(1);
					if (lastY - newY > 20  && !isOpened) {// 向上滑动并且当前显示第一个页面，则shownext
						if (listener != null) {
							return true;
						}
					}else
					if (newY - lastY > 20&&  isOpened&&scrollViewInner.getScrollY()==0) {// 向下滑动并且当前显示第二个页面，则showpre
						if (listener != null) {
							return true;
						}
					}else{
						return super.onTouchEvent(event);
					}

				}

				break;
			default:
				break;
		}
		return super.onInterceptTouchEvent(event);
	}
}
