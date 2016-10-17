package com.hike.digitalgymnastic.view;
		import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
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

import java.util.Arrays;

/**
 * changqi
 * 2015-12-18
 * */
public class DashCircleProgress extends View {

	/**
	 * 整个View的宽高
	 * */
	private int mTotalHeight, mTotalWidth;

	/**
	 * 心跳线的总宽度 -- 圆环的宽度
	 * */
	private int mHeartBeatWidth;

	/**
	 * 圆环半径 根据view的宽度计算
	 * */
	private int mRadius = 200;

	/**
	 * 圆环的中心点 -- 画圆环和旋转画布时需要使用
	 * */
	private int x, y;

	/**
	 * 圆环使用
	 * */
	private Paint mRingPaint;

	/**
	 * 圆环的颜色值
	 * */
	private int mRingPaintColor=Color.RED;

	/**
	 * 圆环动画使用 -- 与mRingPaint唯一不同在于颜色
	 * */
	private Paint mRingAnimPaint;
	/**
	 * 圆环的颜色值
	 * */
	private int mRingColor=Color.WHITE;



	/**
	 * 圆环大小 矩形
	 * */
	private RectF mRectf;

	private Context mContext;

	/**
	 * 圆环 宽度
	 * */
	private  int mHeartPaintWidth = 20;

	/**
	 * 圆环动画开始时 画弧的偏移量
	 * */
	private int mAnimAngle = -1;

	/**
	 * 心跳线 Y轴坐标
	 * */
	private float[] mOriginalYPositon;

	/**
	 * 心跳线 Y轴坐标 -- 默认坐标
	 * */
	private float [] mDefaultYPostion;
	// y = Asin(w*x)+Y
	/**
	 * sin函数 周期因子
	 * */
	private float mPeriodFraction = 0;

	/**
	 * 期初的偏移量
	 * */
	private final int OFFSET_Y = 0;


	/**
	 * 振幅
	 * */
	private float AmplitudeA = 200;// 振幅



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
			attributes.recycle();
		}

	}
	private void initPainter(){
		setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		mRingPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		if (!isInEditMode()) {
			// 造成错误的代码段
			mRingPaint.setColor(mRingColor);
		}
		mRingPaint.setStrokeWidth(mHeartPaintWidth);
		mRingPaint.setAlpha(76);
		mRingPaint.setStyle(Style.STROKE);
		mRingAnimPaint = new Paint(mRingPaint);
		mRingAnimPaint.setColor(mRingPaintColor);


	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		mTotalHeight = h;
		mTotalWidth = w;
		mHeartBeatWidth = w - mHeartPaintWidth*2-40; //内圆宽度
		AmplitudeA = (mTotalHeight-2*mHeartPaintWidth)/4;
		mOriginalYPositon = new float[mHeartBeatWidth];//正弦曲线 Y坐标
		mDefaultYPostion = new float[mHeartBeatWidth];
		Arrays.fill(mOriginalYPositon, 0);
		Arrays.fill(mDefaultYPostion, -1);
		// 周期定位总宽度的1/4
		mPeriodFraction = (float) (Math.PI * 2 / mHeartBeatWidth * 3);
		for (int i =  mHeartBeatWidth/3*2; i < mHeartBeatWidth; i++) {
			mOriginalYPositon[i] = (float) (AmplitudeA * Math.sin(mPeriodFraction * i) + OFFSET_Y);
		}
		x = w / 2;
		y = h / 2;
		mRadius = w / 2 - mHeartPaintWidth / 2; //因为制定了Paint的宽度，因此计算半径需要减去这个
		mRectf = new RectF(x - mRadius, y - mRadius, x + mRadius, y + mRadius);
	}



	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (int i = 0; i < 360; i += 3) {
			canvas.drawArc(mRectf, i, 1, false, mRingPaint);
		}
		if (mAnimAngle != -1) {// 如果开启了动画
			for (int i = -90; i < mAnimAngle-90; i += 3) {
				canvas.drawArc(mRectf, i, 1, false, mRingAnimPaint);
			}
		}


	}


	ValueAnimator valueAnimator=new ValueAnimator();



	/*---------------------------------构造函数-----------------------------------*/
	public DashCircleProgress(Context context) {
		super(context);
		mContext = context;
		init(null);
	}

	public DashCircleProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		init(attrs);
	}

	public DashCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		init(attrs);
	}



	/**
	 * 设置需要显示的数值
	 */
	public void setValue(float value) {
		this.value = value;
		if (value <= max || value >= min) {
			animateValue();
		}
	}
	/**
	 * 启动动画效果
	 */
	private void animateValue() {
		if (valueAnimator != null) {
			valueAnimator.setFloatValues(last, value);
			valueAnimator.setDuration(duration);
			valueAnimator.start();
		}
	}
	/**
	 * 初始化动画
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
			Float value = (Float) valueAnimator.getAnimatedValue();
			if (valueChangeListener != null) {
				valueChangeListener.onValueChange(value);
			}

			last = value;
			mAnimAngle=(int) (last*360/max);
			if(mAnimAngle>360){
				mAnimAngle=360;
			}
			postInvalidate();


		}
	}

	//数值变化回调
	private OnValueChangeListener valueChangeListener;

	public void setValueChangeListener(OnValueChangeListener valueChangeListener) {
		this.valueChangeListener = valueChangeListener;
	}

	public interface OnValueChangeListener {
		void onValueChange(float value);
	}
}
