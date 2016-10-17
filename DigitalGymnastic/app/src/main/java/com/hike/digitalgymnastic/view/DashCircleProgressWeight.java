package com.hike.digitalgymnastic.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

import com.hiko.enterprisedigital.R;

import java.text.DecimalFormat;

/**
 * changqi
 * 2015-12-18
 * */
public class DashCircleProgressWeight extends View {



	private int mRadius = 200;

	private int mRadiusExn = 200;

	private int x, y;

	private Paint mRingPaint;
	private Paint mRingExnPaint;

	private Paint mTextPaint;

	private int mRingPaintColor=Color.RED;

	private Paint mRingAnimPaint;
	private int mRingColor=Color.WHITE;



	private RectF mRectf;
	private RectF mRectfExn;

	private RectF mRectfBmp;

	private Context mContext;

	private  int mHeartPaintWidth = dp2px(10);

	private int mAnimAngle = -1;



	float upRate = (float) 1.1;// 偏高比例系数
	float lowRate = (float) 0.9;// 偏低比例系数
	String titleStandard = "最佳体重";// 表盘上面显示的种类名称，可以根据需求修改
	String titleHigh = "偏高";// 表盘上面显示的种类名称，可以根据需求修改
	String titleLow = "偏低";// 表盘上面显示的种类名称，可以根据需求修改
	float standardValue = (float) 65.4;

	float highValue = (float) (standardValue * upRate);
	public float getHighValue() {
		return highValue;
	}

	float lowValue = (float) (standardValue * lowRate);
	public float getLowValue() {
		return lowValue;
	}

	float currentValue = (float) 65.4;

	Path path = new Path();

	private void init(AttributeSet attrs) {
		TypedArray attributes = mContext.obtainStyledAttributes(attrs,
				R.styleable.DashCircleProgress);
		initAttr( attributes);
		initPainter();
		initValueAnimator();
	}

	private void initAttr(TypedArray attributes){
		if(attributes!=null){
			mHeartPaintWidth=attributes.getDimensionPixelSize(R.styleable.DashCircleProgress_mHeartPaintWidth, 20);
			mRingColor=attributes.getColor(R.styleable.DashCircleProgress_mRingColor, 25);
			mRingPaintColor=attributes.getColor(R.styleable.DashCircleProgress_mRingPaintColor, 25);
			max=attributes.getInt(R.styleable.DashCircleProgress_max, 1000);
			min=attributes.getInt(R.styleable.DashCircleProgress_min, 0);
			duration=attributes.getInt(R.styleable.DashCircleProgress_duration, 500);
			bitmap=	BitmapFactory.decodeResource(getResources(),
					attributes.getResourceId(R.styleable.DashCircleProgress_zhizhen, R.mipmap.icon_zhizhen));
			attributes.recycle();
			}

	}
	private void initPainter(){
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		if (!isInEditMode()) {
			mRingPaint.setColor(mRingColor);
		}
		mRingPaint.setAntiAlias(true);
		mRingPaint.setStrokeWidth(mHeartPaintWidth);
		mRingPaint.setAlpha(76);

		mRingPaint.setStyle(Style.STROKE);
		mRingAnimPaint = new Paint(mRingPaint);
		mRingAnimPaint.setColor(mRingPaintColor);

		mRingExnPaint = new Paint();
		mRingExnPaint.setAntiAlias(true);
		mRingExnPaint.setColor(mRingPaintColor);
		mRingExnPaint.setStyle(Style.STROKE);
		mRingExnPaint.setStrokeWidth(2);


		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mRingPaintColor);
		mTextPaint.setTextAlign(Paint.Align.CENTER);
		mTextPaint.setTextSize(dp2px(18));
	}


	public String getTitleStandard() {
		return titleStandard;
	}

	public void setTitleStandard(String titleStandard) {
		this.titleStandard = titleStandard;
	}

	public String getTitleHigh() {
		return titleHigh;
	}

	public void setTitleHigh(String titleHigh) {
		this.titleHigh = titleHigh;
	}

	public String getTitleLow() {
		return titleLow;
	}

	public void setTitleLow(String titleLow) {
		this.titleLow = titleLow;
	}

	public float getstandardValue() {
		return standardValue;
	}

	public void setstandardValue(float standardValue) {
		this.standardValue = standardValue;
	}
	public void init(int sex, float weight, int height) {
		currentValue = weight;
		if (height >= 100 && height <= 120) {
			if (sex == 1) {
				standardValue = 18;
			} else if (sex == 2) {
				standardValue = 16;
			}
		} else if (height >= 80 && height <= 100) {
			if (sex == 1) {
				standardValue = 16;
			} else if (sex == 2) {
				standardValue = 15;
			}
		} else if (height < 80) {
			standardValue = 12;
		} else if (height > 120) {
			standardValue = (float) ((height - 100) * lowRate);
		}
		DecimalFormat format = new DecimalFormat(".#");
		standardValue = Float.parseFloat(format.format(standardValue));
		highValue = (float) (standardValue * upRate);
		lowValue = (float) (standardValue * lowRate);






		// 根据实际的结果值绘制白线
		if (currentValue >= 150) {
			actualAngle =sweepAngle;
		} else if (currentValue <= 0) {
			actualAngle = 0;
		} else if (currentValue <= standardValue) {
			actualAngle = (int)((270-startAngle)- sweepAngle*(standardValue-currentValue)   / (standardValue-0)/2);
		} else if (currentValue == standardValue) {
			actualAngle =  270;
		} else {
			actualAngle = (int)((270-startAngle)
					+ (float) ((currentValue - standardValue) * sweepAngle/2 / (150-standardValue)));
		}
		setValue(max);
	}
	int paddingTop=50;//用于绘制最佳体重
	int zhizhenSize=dp2px(8);

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		x = w / 2;
		y =paddingTop+(h-paddingTop) / 2;

//		mRadiusExn=w / 2 - mHeartPaintWidth / 2;
//		mRadius = (int) (w / 2 -zhizhenSize*1.6- mHeartPaintWidth / 2);
//
//		mRectf = new RectF(x - mRadius, y - mRadius, x + mRadius, y + mRadius);
//		mRectfExn= new RectF(x - mRadiusExn, y - mRadiusExn, x + mRadiusExn, y + mRadiusExn);
//		mRectfBmp=new RectF(x - zhizhenSize/2,  mHeartPaintWidth / 2 , x + zhizhenSize/2, mHeartPaintWidth / 2+zhizhenSize);
		mRadiusExn=w / 2 - mHeartPaintWidth / 2;
		mRadius = (int) (w / 2 -zhizhenSize*1.6- mHeartPaintWidth / 2);

		mRectf = new RectF(x - mRadius, y - mRadius, x + mRadius, y + mRadius);
		mRectfExn= new RectF(x - mRadiusExn, y - mRadiusExn, x + mRadiusExn, y + mRadiusExn);
		mRectfBmp=new RectF(x - zhizhenSize/2,  y - mRadiusExn , x + zhizhenSize/2,y - mRadiusExn+zhizhenSize);

	}

	private int startAngle=135;
	private int sweepAngle=270;
	private int actualAngle;//实际的角度
	private int lastAngle;//上一次滑动的角度
	Bitmap bitmap;
	DecimalFormat format=new DecimalFormat("##.#");
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.drawArc(mRectfExn,startAngle,sweepAngle,false,mRingExnPaint);
		canvas.drawBitmap(bitmap, null, mRectfBmp, mRingExnPaint);

		for (int i = startAngle; i <= startAngle+sweepAngle; i += 3) {
			canvas.drawArc(mRectf, i, 1, false, mRingPaint);
		}
		String value="";
		if(actualAngle>0)
		 	value=format.format(currentValue*mAnimAngle/actualAngle)+" kg";
		else
			value=currentValue+"kg";
		canvas.drawText(value,getWidth()/2,(getHeight()+ paddingTop) / 2, mTextPaint);
		if (mAnimAngle != -1) {
			for (int i = startAngle; i < mAnimAngle+startAngle; i += 3) {
				canvas.drawArc(mRectf, i, 1, false, mRingAnimPaint);

			}
		}


	}




	ValueAnimator valueAnimator=new ValueAnimator();

	public DashCircleProgressWeight(Context context) {
		super(context);
		mContext = context;
		init(null);
	}

	public DashCircleProgressWeight(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(attrs);
	}

	public DashCircleProgressWeight(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init(attrs);
	}



	public void setValue(float value) {
		this.value = value;
		if (value <= max || value >= min) {
			animateValue();
		}
	}

	private void animateValue() {
		if (valueAnimator != null) {
			valueAnimator.setFloatValues(last, value);
			valueAnimator.setDuration(duration);
			valueAnimator.start();
		}
	}
	/**
	 *
	 */

	private void initValueAnimator() {
		valueAnimator = new ValueAnimator();
		valueAnimator.setInterpolator(interpolator);
		valueAnimator.addUpdateListener(new ValueAnimatorListenerImp());
	}

	public void setInterpolator(Interpolator interpolator) {
		this.interpolator = interpolator;
		if (valueAnimator != null) {
			valueAnimator.setInterpolator(interpolator);
		}
	}

	float min,max=1000,last, value;
	long duration=500;
	Interpolator interpolator;


	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	private class ValueAnimatorListenerImp implements ValueAnimator.AnimatorUpdateListener {
		@Override
		public void onAnimationUpdate(ValueAnimator valueAnimator) {
			Float val = (Float) valueAnimator.getAnimatedValue();
			if (valueChangeListener != null) {
				valueChangeListener.onValueChange(val);
			}
				last = val;

			mAnimAngle=(int) (val*actualAngle/max);
			if(mAnimAngle>actualAngle){
				mAnimAngle=actualAngle;
			}
			postInvalidate();


		}
	}

	private OnValueChangeListener valueChangeListener;

	public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
		this.valueChangeListener = valueChangeListener;
	}

	public interface OnValueChangeListener {
		void onValueChange(float value);
	}


	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}



	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}
}
