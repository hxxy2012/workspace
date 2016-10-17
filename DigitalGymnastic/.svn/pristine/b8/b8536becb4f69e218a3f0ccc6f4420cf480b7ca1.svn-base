package com.hike.digitalgymnastic.view;

import java.text.DecimalFormat;

import com.hiko.enterprisedigital.R;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.view.View;

/*
 * 首页体测刻度盘视图
 */
public class DialScaleView extends View {

	public DialScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public DialScaleView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public DialScaleView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
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

	float startDegree = -156;
	float endDegree = -24;

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
	int textSize = 15;
	int textLargeSize = 20;
	int radius;

	// 当身高大于120cm时，用现有公式
	// 当身高100~120cm时，男的最佳体重18kg 女的16kg
	// 当身高80~100cm时，男的最佳体重16kg 女的15kg
	// 当身高小于80cm时，男女的最佳体重均为12kg
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

		postInvalidate();

	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);

		int width = getWidth();
		int height = getHeight();
		int padding = 20;
		radius = width / 2;

		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.text_normal_color));
		paint.setAntiAlias(true);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(textSize));// 字体大小
		paint.setTextAlign(Align.CENTER);

		// 绘制顶部的标题
		Rect titleStandardRect = new Rect();
		paint.getTextBounds(titleStandard, 0, titleStandard.length(),
				titleStandardRect);

		canvas.drawText(titleStandard, width / 2, titleStandardRect.height(),
				paint);

		// 绘制顶部的标题下面的显示数据
		Rect standardValueRect = new Rect();
		String value = String.valueOf(standardValue)+"kg" ;
		paint.getTextBounds(value, 0, value.length(), standardValueRect);
		canvas.drawText(value, width / 2,
				titleStandardRect.height() + standardValueRect.height()
						+ dp2px(3), paint);
		
		
		
		// 绘制中间的圆盘
		int longLength = radius / 10;
		int shortLength = radius / 15;
		int ringWidth = shortLength;
		float swipeDegree = (float) 1.5;
		int topPadding = dp2px(padding) + titleStandardRect.height()
				+ standardValueRect.height() + dp2px(3);
		RectF oval = new RectF();
		for (int i = 0; i < 67; i++) {

			if (i % 2 == 0) {
				if (i % 6 == 0) {// 绘制长的线
					ringWidth = longLength;
					swipeDegree = (float) 1.5;
				} else {// 绘制短的线
					ringWidth = shortLength;
					swipeDegree = 1;
				}
				startDegree = (float) (-90 + (i - 33) * 1.5);
				paint.setColor(Color.rgb(95, 97, 141));
				// paint.setColor(Color.WHITE);
				// paint.setAlpha(100);
				// 设置进度是实心还是空心
				paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
				float startX = ringWidth / 2;
				float startY = topPadding + ringWidth / 2;
				float endX = width - ringWidth / 2;
				float endY = topPadding + radius * 2 - ringWidth / 2;
				oval = new RectF(startX, startY, endX, endY);
				paint.setStyle(Paint.Style.STROKE);
				canvas.drawArc(oval, startDegree, (float) swipeDegree, false,
						paint); // 根据进度画圆弧
			}
		}

		// 根据实际的结果值绘制白线
		float totalDegree = (float) (67 * 1.5);
		if (currentValue >= 150) {
			startDegree = (float) (-90 + ((float) 67 / 2) * 1.5);
		} else if (currentValue <= 0) {
			startDegree = (float) (-90 - ((float) 67 / 2) * 1.5 + 1.5);
		} else if (currentValue <= standardValue) {
				startDegree = (float) (-90)
						+ (float) ((currentValue - standardValue) * totalDegree/2 / (standardValue));
		} else if (currentValue == standardValue) {
			startDegree = (float) (-90);
		} else {
				startDegree = (float) (-90)
						+ (float) ((currentValue - standardValue) * totalDegree/2 / (150-standardValue));
		}
		if(startDegree<(float) (-90 - ((float) 67 / 2) * 1.5 + 1.5)){
			startDegree=(float) (-90 - ((float) 67 / 2) * 1.5 + 1.5);
		}else if(startDegree > (float) (-90 + ((float) 67 / 2) * 1.5)){
			startDegree = (float) (-90 + ((float) 67 / 2) * 1.5);
		}
//		if(startDegree<(float) (-90)-totalDegree/2){
//			startDegree=(float) (-90)-totalDegree/2;
//		}else if(startDegree>(float) (-90)+totalDegree/2){
//			startDegree=(float) (-90)+totalDegree/2;
//		}

		float startX = (float) (radius + (radius+10)
				* Math.cos(Math.toRadians(startDegree)));
		float startY = (float) (topPadding + radius + (radius+10)
				* Math.sin(Math.toRadians(startDegree)));
		float endX = (float) (radius + radius / 2
				* Math.cos(Math.toRadians(startDegree)));
		float endY = (float) (topPadding + radius + radius / 2
				* Math.sin(Math.toRadians(startDegree)));
		paint.setStyle(Paint.Style.FILL);
		paint.setStrokeWidth(dp2px(2)); // 设置宽度
		paint.setColor(Color.WHITE);
		canvas.drawLine(startX, startY, endX, endY, paint);

		// 画底部的数字
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(sp2px(textLargeSize));// 字体大小
		paint.setTextAlign(Align.CENTER);
		Rect currentValueRect = new Rect();
		value = String.valueOf(currentValue);
		paint.getTextBounds(value, 0, value.length(), currentValueRect);
		canvas.drawText(value, width / 2, topPadding + radius / 2 + dp2px(40),
				paint);
		
		paint.setTextSize(sp2px(textSize-5));// 字体大小
		Rect suffixRect = new Rect();
		value="kg";
		paint.getTextBounds(value, 0, value.length(), suffixRect);
		
		canvas.drawText(value, width / 2+currentValueRect.width()/2+suffixRect.width()/2+dp2px(2), topPadding + radius / 2 + dp2px(40),
				paint);
		

		// 绘制左侧的标题
		Rect titleLowRect = new Rect();
		paint.setColor(getResources().getColor(R.color.text_normal_color));
		paint.setTextSize(sp2px(textSize));// 字体大小
		paint.getTextBounds(titleLow, 0, titleLow.length(), titleLowRect);
		float angle1 = (float) (-90 - ((float) 67 / 2) * 1.5 + 1.5);
		float x = radius + (float) (radius * Math.cos(Math.toRadians(angle1)))
				- titleLowRect.width() / 2;
		float y = topPadding + radius
				+ (float) (radius * Math.sin(Math.toRadians(angle1)))
				- titleLowRect.height();
		canvas.drawText(titleLow, x, y, paint);

		// 绘制右侧的标题
		Rect titleHighLowRect = new Rect();
		paint.getTextBounds(titleHigh, 0, titleHigh.length(), titleHighLowRect);
		float angle = (float) (-90 + ((float) 67 / 2) * 1.5 + 1.5);
		x = radius + (float) (radius * Math.cos(Math.toRadians(angle)))
				+ titleHighLowRect.width() / 3;
		y = topPadding + radius
				+ (float) (radius * Math.sin(Math.toRadians(angle1)))
				- titleHighLowRect.height();
		canvas.drawText(titleHigh, x, y, paint);

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
			// Measure the text (beware: ascent is a negative number)
			result = (int) getHeight() + getPaddingTop() + getPaddingBottom();
			if (specMode == MeasureSpec.AT_MOST) {
				// Respect AT_MOST value if that was what is called for by
				// measureSpec
				result = Math.min(result, specSize);
			}
		}
		return result;
	}

}
