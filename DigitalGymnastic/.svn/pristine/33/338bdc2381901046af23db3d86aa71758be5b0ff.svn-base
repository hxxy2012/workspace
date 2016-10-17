package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class DiverRuleAgeScrollView extends ScrollView {
	public int currentAge;

	NotifationDp notifationDp;

	public NotifationDp getNotifationDp() {
		return notifationDp;
	}

	public void setNotifationDp(NotifationDp notifationDp) {
		this.notifationDp = notifationDp;
	}

	View yearView;
	View yearView_right;
	TextView selectedView;
	int x10;
	int startyear;
	float scrollIndex;
	int scrollIndexInt;

	public int getStartyear() {
		return startyear;
	}

	public void setStartyear(int startyear) {
		this.startyear = startyear;
	}

	public DiverRuleAgeScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleAgeScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleAgeScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}
	int initialY;
	private void init() {
		// initLayoutParam();

		// 滚动停止监听
		scrollerTask = new Runnable() {

			public void run() {
				int newY = getScrollY();
				if (initialY - newY == 0) { // stopped
					final int remainder = initialY % x10;
					
					if (remainder == 0) {

					} else {
						if (remainder > x10 / 2) {
							DiverRuleAgeScrollView.this.post(new Runnable() {
								@Override
								public void run() {
									DiverRuleAgeScrollView.this.smoothScrollTo(
											0,initialY - remainder + x10+x10/4);
								}
							});
						} else {
							DiverRuleAgeScrollView.this.post(new Runnable() {
								@Override
								public void run() {
									DiverRuleAgeScrollView.this.smoothScrollTo(0,initialY - remainder+x10/4);
								}
							});
						}

					}

				} else {
					initialY = getScrollY();
					DiverRuleAgeScrollView.this.postDelayed(scrollerTask,
							newCheck);
				}
			}
		};

	}

	Runnable scrollerTask;
	int initialX;
	int newCheck = 50;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			startScrollerTask();
		}
		return super.onTouchEvent(ev);
	}

	public void startScrollerTask() {

		initialX = getScrollX();
		this.postDelayed(scrollerTask, newCheck);
	}

	// //初始化布局参数
	// private void initLayoutParam() {
	// // TODO Auto-generated method stub
	// LinearLayout.LayoutParams
	// params=(android.widget.LinearLayout.LayoutParams) this.getLayoutParams();
	// if(params!=null)
	// {
	// params.leftMargin=this.getDisplay().getWidth()/2;
	// params.rightMargin=this.getDisplay().getWidth()/2;
	// }
	// else{
	// params=new
	// LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
	// this.measure(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
	// params.leftMargin=this.getMeasuredWidth()/2;
	// params.rightMargin=this.getMeasuredWidth()/2;
	// setLayoutParams(params);
	// }
	// }

	boolean isScrolling = false;

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (yearView != null&&yearView_right!=null) {
			yearView.scrollTo(l, t);
			yearView_right.scrollTo(l, t);
		}
		if (x10 != 0) {
			scrollIndex =(float)t / x10;
			
			if(scrollIndexInt!=t / x10){
				isScrolling=false;
				scrollIndexInt=t / x10;
			}
			
			if (t >= oldt) {
				if(!isScrolling){
					isScrolling=false;
					if(scrollIndex-t / x10>0.01&&scrollIndex-t / x10<=0.50){
						isScrolling=true;
//						smoothScrollBy((int)((0.5-(scrollIndex-t / x10))*x10), 0);
//						if (startyear-t / x10>= Constants.defaultStartHeight
//								&& startyear-t / x10 <= Constants.defaultEndHeight)
//						selectedView.setText(String.valueOf(startyear-t / x10
//								));
					}else{
//						isScrolling=false;
						isScrolling=true;
//						smoothScrollBy((int)((0.5-(scrollIndex-t / x10))*x10-x10), 0);
//						if (startyear-t / x10>= Constants.defaultStartHeight
//								&& startyear-t / x10 <= Constants.defaultEndHeight)
//						selectedView.setText(String.valueOf(startyear-t / x10
//								));
					}
				}
//				else{
//					isScrolling=false;
//				}
			} else {
				if(!isScrolling){
					if(scrollIndex-t / x10<0.99&&scrollIndex-t / x10>=0.50){
						isScrolling=true;
//						smoothScrollBy(-(int)((0.5-(1-(scrollIndex-t / x10)))*x10), 0);
//						if (startyear-t / x10>= Constants.defaultStartHeight
//								&& startyear-t / x10 <= Constants.defaultEndHeight)
//						selectedView.setText(String.valueOf(startyear-t / x10
//								));
					}else{
//						isScrolling=false;
						isScrolling=true;
						smoothScrollBy(-(int)((0.5-(1-(scrollIndex-t / x10)))*x10)+x10, 0);
//						if (startyear-t / x10>= Constants.defaultStartHeight
//								&& startyear-t / x10 <= Constants.defaultEndHeight)
//						selectedView.setText(String.valueOf(startyear-t / x10
//								));
					}
				}
//				else{
//					isScrolling=false;
//				}
			}
			currentAge=startyear-t / x10;
			notifationDp.changeValue(currentAge);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public void setYearScrollView(View view,View view1) {
		yearView = view;
		yearView_right=view1;
	}

	public void setSelectedView(TextView selectedView) {
		this.selectedView = selectedView;
	}

	public void setKeduWidth(int x10) {
		this.x10 = x10;
	}

}
