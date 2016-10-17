package com.hike.digitalgymnastic.view;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

public class DiverRuleHeightScrollView extends ScrollView {
	public int currentHeight;
	

	NotifationDp notifationDp;

	public NotifationDp getNotifationDp() {
		return notifationDp;
	}

	public void setNotifationDp(NotifationDp notifationDp) {
		this.notifationDp = notifationDp;
	}

	View yearView;
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

	public DiverRuleHeightScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleHeightScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public DiverRuleHeightScrollView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private void init() {
		// x10=(int) getResources().getDimension(R.dimen.x10);
		
		//滚动停止监听
				scrollerTask = new Runnable() {

					public void run() {

						int newY = getScrollY();
						if (initialY - newY == 0) { // stopped
							final int remainder = initialY % x10;
							
							if (remainder == 0) {

							} else {
								if (remainder > x10 / 2) {
									DiverRuleHeightScrollView.this.post(new Runnable() {
										@Override
										public void run() {
											DiverRuleHeightScrollView.this.smoothScrollTo(
													0,initialY - remainder + x10);
										}
									});
								} else {
									DiverRuleHeightScrollView.this.post(new Runnable() {
										@Override
										public void run() {
											DiverRuleHeightScrollView.this.smoothScrollTo(0,initialY - remainder);
										}
									});
								}

							}

						} else {
							initialY = getScrollY();
							DiverRuleHeightScrollView.this.postDelayed(scrollerTask,
									newCheck);
						}
					}
				};
	}
	Runnable scrollerTask;
	int initialY;
	int newCheck = 50;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_UP) {

			startScrollerTask();
		}
		return super.onTouchEvent(ev);
	}

	public void startScrollerTask() {

		initialY = getScrollY();
		this.postDelayed(scrollerTask, newCheck);
	}
	boolean isScrolling=false;
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (yearView != null) {
			yearView.scrollTo(l, t);
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
			currentHeight=startyear-t / x10;
			notifationDp.changeValue(currentHeight);
		}

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
	}

	public void setYearScrollView(View view) {
		yearView = view;
	}

	public void setSelectedView(TextView selectedView) {
		this.selectedView = selectedView;
	}

	public void setKeduWidth(int x10) {
		this.x10 = x10;
	}


}
