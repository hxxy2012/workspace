package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

//睡眠历史记录底部的周统计视图
public class HistorySleepProgressView extends ImageView {
	Context context;
	long animTime=1500;
	private String[] stepsTitle = { "优质", "良好", "一般", "较差" };

	public HistorySleepProgressView(Context context) {
		super(context);
		this.context = context;
	}

	public HistorySleepProgressView(Context context, AttributeSet attrs) {
		super(context, attrs, 0);
		this.context = context;
	}

	int ringWidth = dp2px(2);
	int ringWidthOut= dp2px(1);
	String value = "";
	float progress;
	float animProgress;
	private int actualValue;
	private int[] stepsValue;
	private String actualStep;

	int resouceID=R.color.aver_cal_color;
	public void setColor(int resouceID){
		this.resouceID=resouceID;
	}
	Bitmap bitmap;

	boolean istotalaver;
	/**
	 * 
	 * @param istotalaver   平均睡眠时长标识为true，平均深度睡眠时长为false
	 * @param value    显示的文本信息
	 * @param actualValue    实际的数值（秒）
	 * @param total     进度条总的长度数值（秒）
	 * @param standed   标准数值（秒）
	 */
	public void init(boolean istotalaver,String value, int actualValue, int total,float standed) {
		this.istotalaver=istotalaver;
		BitmapFactory.Options option=new BitmapFactory.Options();
		option.inSampleSize=2;
		this.value = value;
		this.actualValue = actualValue;
		
		if(istotalaver){//平均睡眠时长标识
			int value1v4=(int) (standed-3*3600);
			int value2v4=(int) (standed-1*3600);
			int value3v4=(int) standed;
			int value4v4=10*3600;
			if(actualValue>=value3v4){//优质 360*0.9
				if(value4v4==value3v4)
					this.progress=360;
				else
					this.progress=(actualValue-value3v4)*90/(value4v4-value3v4)+270;
				if(progress>=324)
					this.progress=324;
				actualStep = stepsTitle[0];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_youzhi, option);
			}else if(actualValue>=value2v4){//良好
				this.progress=(actualValue-value2v4)*90/(value3v4-value2v4)+180;
				actualStep = stepsTitle[1];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_lianghao, option);
			}else if(actualValue>=value1v4){//一般
				this.progress=(actualValue-value1v4)*90/(value2v4-value1v4)+90;
				actualStep = stepsTitle[2];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_yiban, option);
			}else{//较差
				this.progress=(value1v4-actualValue)*90/(value1v4-0);
				actualStep = stepsTitle[3];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_jiaocha, option);
			}
		}else{//平均深度睡眠时长标识
			int value1v4=(int) (standed-2*3600);
			int value2v4=(int) (standed-1*3600);
			int value3v4=(int) standed;
			int value4v4=10*3600;
			if(actualValue>=value3v4){//优质 360*0.9
				this.progress=(actualValue-value3v4)*90/(value4v4-value3v4)+270;
				if(progress>=324)
					this.progress=324;
				actualStep = stepsTitle[0];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_youzhi, option);
			}else if(actualValue>=value2v4){//良好
				this.progress=(actualValue-value2v4)*90/(value3v4-value2v4)+180;
				actualStep = stepsTitle[1];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_lianghao, option);
			}else if(actualValue>=value1v4){//一般
				this.progress=(actualValue-value1v4)*90/(value2v4-value1v4)+90;
				actualStep = stepsTitle[2];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_yiban, option);
			}else{//较差
				this.progress=(value1v4-actualValue)*90/(value1v4-0);
				actualStep = stepsTitle[3];
				bitmap=BitmapFactory.decodeResource(context.getResources()
						, R.mipmap.icon_sleep_jiaocha, option);
			}
		}
		
		
//		stepsValue = new int[] { (int) standed, (int) (standed * 3 / 4), (int) (standed * 2 / 4), (int) (standed / 4) };
//		
//		if (actualValue >= stepsValue[1]) {
//			actualStep = stepsTitle[0];
//			bitmap=BitmapFactory.decodeResource(context.getResources()
//					, R.mipmap.icon_sleep_youzhi, option);
//		} else if (actualValue >= stepsValue[2] && actualValue < stepsValue[1]) {
//			actualStep = stepsTitle[1];
//			bitmap=BitmapFactory.decodeResource(context.getResources()
//					, R.mipmap.icon_sleep_lianghao, option);
//		} else if (actualValue >= stepsValue[3] && actualValue < stepsValue[2]) {
//			actualStep = stepsTitle[2];
//			bitmap=BitmapFactory.decodeResource(context.getResources()
//					, R.mipmap.icon_sleep_yiban, option);
//		} else {
//			actualStep = stepsTitle[3];
//			bitmap=BitmapFactory.decodeResource(context.getResources()
//					, R.mipmap.icon_sleep_jiaocha, option);
//		}
//
//		this.progress = (((float) actualValue ) / total)* 360;

		ani = new HistogramAnimation();
		ani.setDuration(animTime);
		this.startAnimation(ani);
		// invalidate();
	}

	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		super.onDraw(canvas);
		int radiusX = getWidth() / 2;
		int radiusY = getHeight() / 2;
		Paint paint = new Paint();
		paint.setAntiAlias(true);
		paint.setColor(Color.WHITE);

		// 设置进度是实心还是空心
		paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
		paint.setAlpha(75);//不透明度30%
		RectF oval = new RectF(ringWidthOut, ringWidthOut, radiusX * 2
				- ringWidthOut, radiusY * 2 - ringWidthOut);

		paint.setStyle(Paint.Style.STROKE);
		canvas.drawArc(oval, -90, (float) 360, false, paint); // 根据进度画圆弧
		if(actualValue>0){
			RectF oval1 = new RectF(ringWidth+ringWidthOut/2, ringWidth+ringWidthOut/2 , radiusX * 2
					- (ringWidth+ringWidthOut/2), radiusY * 2 - (ringWidth+ringWidthOut/2));
			paint.setAlpha(200);//不透明度80%
			canvas.drawArc(oval1, -90, (float) animProgress, false, paint); // 根据进度画圆弧
		}
		paint.setColor(getResources().getColor(R.color.week_color));
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(10));// 字体大小
		paint.setTextAlign(Align.CENTER);
		paint.setAlpha(255);//不透明度100%
		Rect rectTime = new Rect();
		Rect rectStep = new Rect();
		Rect rectBmp = new Rect();
		if(actualValue==0){
			value = "00小时00分";
			paint.getTextBounds(value, 0, value.length(), rectTime);
			canvas.drawText(value, radiusX, radiusY + rectTime.height()/2, paint);
		}else{
			paint.getTextBounds(value, 0, value.length(), rectTime);
			paint.getTextBounds(actualStep, 0, actualStep.length(), rectStep);
			paint.setTextSize(sp2px(15));// 字体大小
			canvas.drawText(actualStep, radiusX, radiusY+rectTime.height(), paint);
			paint.setTextSize(sp2px(10));// 字体大小
			canvas.drawText(value, radiusX, radiusY + rectTime.height()*3, paint);
//			stepsTitle = { "优质", "良好", "一般", "较差" };
			rectBmp.left=(int) (radiusX-getResources().getDimension(R.dimen.x13));
			rectBmp.right=(int) (radiusX+getResources().getDimension(R.dimen.x13));
			rectBmp.bottom=(int) (radiusY-getResources().getDimension(R.dimen.x5));
			rectBmp.top=rectBmp.bottom-(rectBmp.right-rectBmp.left);
					
			canvas.drawBitmap(bitmap, null, rectBmp, paint);
		}
	}

	private int paddingUp = 15;
	private int paddingDown = 5;

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
			if (progress > 0) {
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
