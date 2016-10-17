package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

public class ScrollViewInner extends ScrollView {
	int scrollSpeedMaxRate=2;
	int scrollSpeedMinRate=2;
	int rate=scrollSpeedMaxRate;//滚动速度控制，为了在上下滑动过程中是底部不变化
	
	public void setRate(int rate) {
		this.rate = rate;
	}
	
	
	public void setSpeedMaxRate(){
		rate=scrollSpeedMaxRate;
	}
	public void setSpeedMinRate(){
		rate=scrollSpeedMinRate;
	}
	/**
     * 滑动事件
     */
    @Override
    public void fling(int velocityY) {
        super.fling(velocityY / rate);
    }
    Context context;
	public ScrollViewInner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public ScrollViewInner(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	public ScrollViewInner(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
	}

	float lastY = -1;

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.dispatchTouchEvent(ev);
	}

	boolean isNeedScroll=false;//默认不需要滚动
	public void setNeedScroll(boolean isNeedScroll) {
		this.isNeedScroll = isNeedScroll;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(!isNeedScroll)
			return true;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastY = event.getY();
			break;
		case MotionEvent.ACTION_UP:
			lastY = -1;
			break;
		case MotionEvent.ACTION_MOVE:

			if (event.getY() - lastY > 20 && lastY != -1) {// 向下滑动并且当前显示第二个页面，则showpre
				View view = (View) getParent();
				if (view != null && getScrollY() == 0) {
					MyViewFlipper mvf = (MyViewFlipper) view;
					if(mvf.isScrolling())
						return true;
					if (mvf.isOpened) {
						if(mvf.listener!=null){
//							mvf.listener.onTouch(false);
							return false;
						}
					}
				}
			}

			break;
		default:
			break;
		}

		return super.onTouchEvent(event);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {

		if (ev.getActionMasked() == MotionEvent.ACTION_DOWN) {
			View view = (View) getParent();
			if (view != null && getScrollY() == 0) {

//				 return false;
			}

		}
		return super.onInterceptTouchEvent(ev);
	}

}
