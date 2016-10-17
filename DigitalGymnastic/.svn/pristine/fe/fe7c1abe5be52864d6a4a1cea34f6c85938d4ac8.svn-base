package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.OverScroller;
import android.widget.Scroller;

import java.lang.reflect.Field;

public class HorizontalScrollViewEx extends HorizontalScrollView  implements OnGestureListener{
    static final int ANIMATED_SCROLL_GAP = 250;
    private long mLastScroll;

    private Field mScrollerField;
    ScrollerEx scrollerEx = null;

    public HorizontalScrollViewEx(Context context) {
        this(context, null);

    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setSmoothScrollingEnabled(false);
        initScroller();
    }

    public HorizontalScrollViewEx(Context context, AttributeSet attrs, int defStyle) {

        super(context, attrs, defStyle);
        this.setSmoothScrollingEnabled(false);
        initScroller();
    }

    private void initScroller() {
        try {
            mScrollerField = HorizontalScrollView.class.getDeclaredField("mScroller");
            mScrollerField.setAccessible(true);
            String type = mScrollerField.getType().getSimpleName();

            if ("OverScroller".equals(type)) {
                scrollerEx = new ScrollerEx() {
                    private OverScroller mScroller = null;

                    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                        mScroller.startScroll(startX, startY, dx, dy, duration);
                    }

                    public boolean isFinished() {
                        return mScroller.isFinished();
                    }

                    public Object getScroller() {
                        return mScroller;
                    }

                    public void create(Context context, Interpolator interpolator) {
                        mScroller = new OverScroller(context, interpolator);
                    }

                    public void abortAnimation() {
                        if (mScroller != null) {
                            mScroller.abortAnimation();
                        }
                    }
                };
            } else {
                scrollerEx = new ScrollerEx() {
                    private Scroller mScroller = null;

                    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
                        mScroller.startScroll(startX, startY, dx, dy, duration);
                    }

                    public boolean isFinished() {
                        return mScroller.isFinished();
                    }

                    public Object getScroller() {
                        return mScroller;
                    }

                    public void create(Context context, Interpolator interpolator) {
                        mScroller = new Scroller(context, interpolator);
                    }

                    public void abortAnimation() {
                        if (mScroller != null) {
                            mScroller.abortAnimation();
                        }
                    }
                };
            }

        } catch (Exception ex) {
        }
    }

    public final void smoothScrollBy(int dx, int dy, int addDuration) {

        float tension = 0f;

        scrollerEx.abortAnimation();

        Interpolator ip = new OvershootInterpolator(tension);
        scrollerEx.create(getContext(), ip);

        try {
            mScrollerField.set(this, scrollerEx.getScroller());
        } catch (Exception e) {
        }

        long duration = AnimationUtils.currentAnimationTimeMillis() - mLastScroll;
        if (duration > ANIMATED_SCROLL_GAP) {
            scrollerEx.startScroll(getScrollX(), getScrollY(), dx, dy, addDuration);

            awakenScrollBars();

            // awakenScrollBars(mScroller.getDuration());

            invalidate();
        } else {
            if (!scrollerEx.isFinished()) {
                scrollerEx.abortAnimation();
            }
            scrollBy(dx, dy);
        }
        mLastScroll = AnimationUtils.currentAnimationTimeMillis();

    }

    public final void smoothScrollTo(int x, int y, int duration) {
        smoothScrollBy(x - getScrollX(), y - getScrollY(), duration);
    }

    private interface ScrollerEx {

        void create(Context context, Interpolator interpolator);

        Object getScroller();

        void abortAnimation();

        void startScroll(int startX, int startY, int dx, int dy, int duration);

        boolean isFinished();

    }
    
    private int LEFT_DISTANCE = -150;
    private int RIGHT_DISTANCE = 150;
    private int  xPosition=0;
	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		 return true;
	}
	 // 用户按下触摸屏、快速移动后松开,由1个MotionEvent ACTION_DOWN,
    // 多个ACTION_MOVE, 1个ACTION_UP触发
    // e1：第1个ACTION_DOWN MotionEvent
    // e2：最后一个ACTION_MOVE MotionEvent
    // velocity X：X 轴上的移动速度，像素/秒
    // velocity Y：Y 轴上的移动速度，像素/秒
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		 float distanceX = e2.getX() - e1.getX();
		 xPosition+=distanceX;
//         if (distanceX < LEFT_DISTANCE) {
//             // left
//             setXPosition(true);
//
//         } else if (distanceX > RIGHT_DISTANCE) {
//             // right
//             setXPosition(false);
//         }
         smoothScrollTo(xPosition, 0,(int) ((distanceX*1000)/velocityX));
         return true;
	}
	 // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	// 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		scrollBy((int) distanceX, 0);
		return true;
	}
	 // 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
    // 注意和onDown()的区别，强调的是没有松开或者拖动的状态
	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	 // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
//		 if (e.getY() < offsetOfBottom) {
//             return true;
//         }
//         if (e.getX() > halfOfWidth) {
//             setXPosition(true);
//         } else {
//             setXPosition(false);
//         }
//         scrollTo(xPosition, 0);
         return true;
	}

}
