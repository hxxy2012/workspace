package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.hiko.enterprisedigital.R;
//历史记录底部的周统计视图
public class HistoryProgressView extends ImageView {
	Context context;
	long animTime=1500;
	String value;
	float progress;
	float animProgress;
	public HistoryProgressView(Context context) {
		super(context);
		this.context = context;
	}

	public HistoryProgressView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.context = context;
	}

	int ringWidth = dp2px(2);
	int ringWidthOut= dp2px(1);
	int resouceID=R.color.aver_cal_color;
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		int radiusX = getWidth() / 2;
		int radiusY = getHeight() / 2;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);
		paint.setAlpha(75);//不透明度30%
		// 设置进度是实心还是空心
		paint.setStyle(Paint.Style.STROKE);

		RectF oval = new RectF(ringWidthOut, ringWidthOut, radiusX * 2
				- ringWidthOut, radiusY * 2 - ringWidthOut);

		canvas.drawArc(oval, -90, (float) 360, false, paint); // 根据进度画圆弧


		paint.setAlpha(200);//不透明度80%
		RectF oval1 = new RectF(ringWidth+ringWidthOut/2, ringWidth+ringWidthOut/2 , radiusX * 2
				- (ringWidth+ringWidthOut/2), radiusY * 2 - (ringWidth+ringWidthOut/2));
		paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
		canvas.drawArc(oval1, -90, (float) animProgress, false, paint); // 根据进度画圆弧
		
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(12));// 字体大小
		paint.setTextAlign(Align.CENTER);
		paint.setAlpha(255);//不透明度80%
		Rect rect=new Rect();
		paint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, radiusX, radiusY+rect.height()/2, paint);
		
	}
	public void setColor(int resouceID){
		this.resouceID=resouceID;
	}
	public void setText(String value,double aver,double total) {
		this.value = value;
		this.progress=(float) ((aver*360)/total);
//		invalidate();

		ani = new HistogramAnimation();
		ani.setDuration(animTime);
		this.startAnimation(ani);
	}


	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	/**
	 * @see android.view.View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * Determines the width of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The width of the view, honoring constraints from measureSpec
	 */
	private int measureWidth(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			// Measure the text
			result = (int) getWidth() + getPaddingLeft() + getPaddingRight();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	/**
	 * Determines the height of this view
	 * 
	 * @param measureSpec
	 *            A measureSpec packed into an int
	 * @return The height of the view, honoring constraints from measureSpec
	 */
	private int measureHeight(int measureSpec) {
		int result = 0;
		int specMode = MeasureSpec.getMode(measureSpec);
		int specSize = MeasureSpec.getSize(measureSpec);

		if (specMode == MeasureSpec.EXACTLY) {
			// We were told how big to be
			result = specSize;
		} else {
			result = (int) getHeight() + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

	HistogramAnimation ani;

	private class HistogramAnimation extends Animation {
		protected void applyTransformation(float interpolatedTime,
				Transformation t) {
			super.applyTransformation(interpolatedTime, t);
			if (progress >= 0) {
				if (interpolatedTime < 1.0f) {
					animProgress = progress * interpolatedTime;

				} else {
					animProgress = progress;
				}
			}
			invalidate();
		}
	}
}
