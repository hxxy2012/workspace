package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hiko.enterprisedigital.R;

import java.text.DecimalFormat;

/**
 * 
 * @author changqi
 * @category 目标运动各种动作比例圆环
 */
public class SportRateImageView extends View {

	public static final float radiusRate = (float) 3.14159265;
	private int[] colors = { R.color.circle_color_one,
			R.color.circle_color_two, R.color.circle_color_three,
			R.color.circle_color_four, R.color.circle_color_five,
			R.color.circle_color_six };
//	private int color_type = getResources().getColor(R.color.text_color_normal);
private int color_type = getResources().getColor(R.color.white);
	Context context;
	/**
	 * 圆环百分比画笔颜色
	 */
	int colorId = R.color.white;
	Paint paint;
	/**
	 * 中心x轴坐标
	 */
	int radiusX;
	/**
	 * 中心Y轴坐标
	 */
	int radiusY;

	int ringWidth;// 环的宽度
	int distance;//预留出的宽度，用于绘制点击后的效果
	int outRadius;// 圆环外圆半径
	int innerRadius;// 内圆半径
	int width;// 视图宽度
	int height;// 视图高度
	int clickIndex = 0;

	/**
	 * 
	 * @param clickIndex
	 * @category 设置默认显示的元素
	 */
	public void setClickIndex(int clickIndex) {
		this.clickIndex = clickIndex;
		postInvalidate();
	}

	/**
	 * 
	 * @param type
	 * @category 设置默认显示的元素
	 */
	public void setClickIndex(String type) {
		if (TextUtils.isEmpty(type) || types == null)
			return;
		for (int i = 0; i < types.length; i++) {
			if (TextUtils.equals(type, types[i])) {
				this.clickIndex = i;
			}
		}
	}

	public SportRateImageView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		this.context = context;

		init();
	}

	public SportRateImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public SportRateImageView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	public void init() {
		paint = new Paint();
		paint.setAntiAlias(true);
		paint.setTextAlign(Align.CENTER);

	}

	// private String[] types = { "步行", "有氧呼吸", "舞蹈" };
	// private float[] rates = { (float) 44.1, (float) 32.9, (float) 23.0 };
	// String[] values = { "1000", "2000", "2032" };
	// float[] degrees=new float[3];
	private String[] types;
	private float[] rates;
	private boolean[] selecteds;
	String[] values;
	float[] degrees;
	/**
	 * @category 最大的元素个数
	 */
	int maxItem = 6;

	public void setMaxItem(int maxItem) {
		if (maxItem > 6)
			this.maxItem = 6;
		else
			this.maxItem = maxItem;
	}

	/**
	 * @category 设置数据
	 */
	public void setData(String[] types, int[] values) {
		int total = 0;
		int i = 0;
		this.types = types;
		this.values = new String[types.length];
		this.rates = new float[types.length];
		this.selecteds=new boolean[types.length];
		this.degrees = new float[types.length];
		xy_names = new int[types.length][2];
		for (int value : values) {
			total += value;
		}
		DecimalFormat df = new DecimalFormat("##.#");

		for (int value : values) {
			float rate = (float) (value * 100) / total;
			this.rates[i] = Float.parseFloat(df.format(rate));
			this.selecteds[i]=false;
			this.values[i] = df.format(value);
			i++;
		}
		// 以下处理超过6个元素的显示样式
		if (types != null && types.length > maxItem) {
			String[] cache_types = new String[maxItem];
			String[] cache_values = new String[maxItem];
			float[] cache_rates = new float[maxItem];
			float[] cache_degrees = new float[maxItem];
			xy_names = new int[maxItem][2];
			float rate;
			// 重置typs数组,values 数组
			for (int j = 0; j < maxItem - 1; j++) {
				cache_types[j] = types[j];
				cache_values[j] = df.format(values[j]);
				rate = (float) (values[j] * 100) / total;
				cache_rates[j] = Float.parseFloat(df.format(rate));
			}
			cache_types[maxItem - 1] = "其它";
			this.types = cache_types;
			int extra_value_total = 0;
			float extra_rate_total = 0;
			for (int j = maxItem - 1; j < types.length; j++) {
				extra_value_total += values[j];
				extra_rate_total += (float) (values[j] * 100) / total;
			}
			cache_values[maxItem - 1] = df.format(extra_value_total);
			cache_rates[maxItem - 1] = extra_rate_total;
			this.values = cache_values;
			this.rates = cache_rates;
			this.degrees = cache_degrees;
		}
		  invalidate();
	}

	/**
	 * @category 设置数据
	 */
	public void setData(String[] types, double[] values) {
		double total = 0;
		int i = 0;
		this.types = types;
		this.values = new String[types.length];
		this.rates = new float[types.length];
		this.selecteds=new boolean[types.length];
		this.degrees = new float[types.length];
		xy_names = new int[types.length][2];
		for (double value : values) {
			total += value;
		}
		DecimalFormat df = new DecimalFormat("##.#");

		for (double value : values) {
			float rate = (float) ((value * 100) / total);
			rates[i] = Float.parseFloat(df.format(rate));
			this.selecteds[i]=false;
			this.values[i] = df.format(value);
			i++;
		}
		// 以下处理超过6个元素的显示样式
		if (types != null && types.length > maxItem) {
			String[] cache_types = new String[maxItem];
			String[] cache_values = new String[maxItem];
			float[] cache_rates = new float[maxItem];
			float[] cache_degrees = new float[maxItem];
			xy_names = new int[maxItem][2];
			float rate;
			// 重置typs数组,values 数组
			for (int j = 0; j < maxItem - 1; j++) {
				cache_types[j] = types[j];
				cache_values[j] = df.format(values[j]);
				rate = (float) ((values[j] * 100) / total);
				cache_rates[j] = Float.parseFloat(df.format(rate));
			}
			cache_types[maxItem - 1] = "其它";
			this.types = cache_types;
			int extra_value_total = 0;
			float extra_rate_total = 0;
			for (int j = maxItem - 1; j < types.length; j++) {
				extra_value_total += values[j];
				extra_rate_total += (float) (values[j] * 100) / total;
			}
			cache_values[maxItem - 1] = df.format(extra_value_total);
			cache_rates[maxItem - 1] = extra_rate_total;
			this.values = cache_values;
			this.rates = cache_rates;
			this.degrees = cache_degrees;
		}
		invalidate();
	}

	/**
	 * @category 设置数据
	 */
	public void setData(String[] types, long[] values) {
		long total = 0;
		int i = 0;
		this.types = types;
		this.values = new String[types.length];
		this.rates = new float[types.length];
		this.selecteds=new boolean[types.length];
		this.degrees = new float[types.length];
		xy_names = new int[types.length][2];
		for (long value : values) {
			total += value;
			this.selecteds[i]=false;
		}
		DecimalFormat df = new DecimalFormat("##.#");

		for (double value : values) {
			float rate = (float) ((value * 100) / total);
			rates[i] = Float.parseFloat(df.format(rate));
			this.values[i] = df.format(value);
			i++;
		}
		// 以下处理超过6个元素的显示样式
		if (types != null && types.length > maxItem) {
			String[] cache_types = new String[maxItem];
			String[] cache_values = new String[maxItem];
			float[] cache_rates = new float[maxItem];
			float[] cache_degrees = new float[maxItem];
			xy_names = new int[maxItem][2];
			float rate;
			// 重置typs数组,values 数组
			for (int j = 0; j < maxItem - 1; j++) {
				cache_types[j] = types[j];
				cache_values[j] = df.format(values[j]);
				rate = (float) (values[j] * 100) / total;
				cache_rates[j] = Float.parseFloat(df.format(rate));
			}
			cache_types[maxItem - 1] = "其它";
			this.types = cache_types;
			int extra_value_total = 0;
			float extra_rate_total = 0;
			for (int j = maxItem - 1; j < types.length; j++) {
				extra_value_total += values[j];
				extra_rate_total += (float) (values[j] * 100) / total;
			}
			cache_values[maxItem - 1] = df.format(extra_value_total);
			cache_rates[maxItem - 1] = extra_rate_total;
			this.values = cache_values;
			this.rates = cache_rates;
			this.degrees = cache_degrees;
		}
		invalidate();
	}

	/**
	 * @category 设置数据
	 */
	public void setData(String[] types, short[] values) {
		int total = 0;
		int i = 0;
		this.types = types;
		this.values = new String[types.length];
		this.rates = new float[types.length];
		this.selecteds=new boolean[types.length];
		this.degrees = new float[types.length];
		xy_names = new int[types.length][2];
		for (int value : values) {
			total += value;
			this.selecteds[i]=false;
		}
		DecimalFormat df = new DecimalFormat("##.#");

		for (int value : values) {
			float rate = (float) (value * 100) / total;
			rates[i] = Float.parseFloat(df.format(rate));
			this.values[i] = df.format(value);
		}

		// 以下处理超过6个元素的显示样式
		if (types != null && types.length > maxItem) {
			String[] cache_types = new String[maxItem];
			String[] cache_values = new String[maxItem];
			float[] cache_rates = new float[maxItem];
			float[] cache_degrees = new float[maxItem];
			xy_names = new int[maxItem][2];
			float rate;
			// 重置typs数组,values 数组
			for (int j = 0; j < maxItem - 1; j++) {
				cache_types[j] = types[j];
				cache_values[j] = df.format(values[j]);
				rate = (float) (values[j] * 100) / total;
				cache_rates[j] = Float.parseFloat(df.format(rate));
			}
			cache_types[maxItem - 1] = "其它";
			this.types = cache_types;
			int extra_value_total = 0;
			float extra_rate_total = 0;
			for (int j = maxItem - 1; j < types.length; j++) {
				extra_value_total += values[j];
				extra_rate_total += (float) (values[j] * 100) / total;
			}
			cache_values[maxItem - 1] = df.format(extra_value_total);
			cache_rates[maxItem - 1] = extra_rate_total;
			this.values = cache_values;
			this.rates = cache_rates;
			this.degrees = cache_degrees;
		}
		postInvalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (rates == null || types == null || values == null) {
			return;
		}
		drawCircle(canvas);
		drawMiddleText(canvas);
//		drawNames(canvas);
	}


	float transRate=0.9f;
	/**
	 * 
	 * @param canvas
	 * @category 绘制中间的圆环
	 */
	private void drawCircle(Canvas canvas) {
		// 设置进度是实心还是空心
		paint.setStrokeWidth(ringWidth); // 设置圆环的宽度
//		RectF oval1 = new RectF(radiusX - outRadius + ringWidth / 2, radiusY
//				- outRadius + ringWidth / 2, radiusY + outRadius - ringWidth
//				/ 2, radiusY + outRadius - ringWidth / 2);
//		int distance=ringWidth/4;//预留出一个ringWidth的宽度，来处理点击后的显示效果

		RectF rectF = new RectF(distance+ringWidth , distance+ringWidth, width-(distance+ringWidth ), height-(distance+ringWidth ));
		RectF rectF1 =new RectF();


		paint.setStyle(Paint.Style.STROKE);
		float startdegree = 0;

		int colorid;
		float deg;
		for(int i=0;i<types.length;i++){
			colorid = getResources().getColor(colors[i]);
			paint.setColor(colorid);

			deg= startdegree + rates[i] * 360 / 100 / 2;
			if(selecteds[i]){
				rectF1.left= (float) (ringWidth+distance*Math.cos(Math.toRadians(deg))/2);
				rectF1.right= (float) (rectF1.left+width-ringWidth*2);
				rectF1.top= (float) (ringWidth+distance*Math.sin(Math.toRadians(deg))/2);
				rectF1.bottom= (float) (rectF1.top+width-ringWidth*2);
				canvas.drawArc(rectF1, startdegree+2, rates[i] * 360 / 100+0.5f-4, false,
						paint); // 根据进度画圆弧
			}else{
				canvas.drawArc(rectF, startdegree, rates[i] * 360 / 100+0.5f , false,
						paint); // 根据进度画圆弧
			}


			startdegree += rates[i] * 360 / 100;
			degrees[i] = startdegree;
		}

	}

	/**
	 * 
	 * @param canvas
	 * @category 绘制中间的显示的文本
	 */
	private void drawMiddleText(Canvas canvas) {
		// paint.setStrokeWidth(0); // 设置圆环的宽度
		String value=values[selectIndex];
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);

		Rect rect = new Rect();
		Rect rect1 = new Rect();
		paint.setTextSize(width / 8);
		paint.getTextBounds(value, 0, value.length(), rect);

		value = " kcal";
		paint.setTextSize(width / 22);
		paint.getTextBounds(value, 0, value.length(), rect1);
		paint.setTextSize(width / 8);

		value=values[selectIndex];
		int x = radiusX-(rect.width()+rect1.width())/2+rect.width()/2;
		int y = radiusY;
		canvas.drawText(value, x, y, paint);

		value = " kcal";
		paint.setTextSize(width / 22);
		x = radiusX-(rect.width()+rect1.width())/2+rect.width()+rect1.width()/2;
		y = radiusY;
		canvas.drawText(value, x, y, paint);

		value = types[selectIndex];
		paint.setTextSize(width / 15);
		x=radiusX;
		y = (int) (radiusY + rect.height() * 1.2);
		canvas.drawText(value, x, y, paint);
	}

	int[][] xy_names;
	/**
	 *
	 * @param canvas
	 * @category 绘制周边的名称
	 */
	private void getPositions(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);

		paint.setTextSize(width / 25);
		int left2Cir = outRadius + outRadius / 3;
		int leftX;
		int leftY;
		Rect rect;
		int i = 0;
		String value;
		for (float degree : degrees) {
			if (i == clickIndex)
				paint.setColor(Color.WHITE);
			else
				paint.setColor(color_type);
			if (degree > 135 && degree < 225) {
				left2Cir = outRadius + outRadius / 2;
			} else {
				left2Cir = outRadius + outRadius / 3;
			}
			leftX = (int) (radiusX + left2Cir
					* Math.cos(Math.toRadians(degree)));
			leftY = (int) (radiusY + left2Cir
					* Math.sin(Math.toRadians(degree)));
			value = types[i];
			rect = new Rect();
			paint.getTextBounds(value, 0, value.length(), rect);
			int x = leftX;
			int y = leftY;
			canvas.drawText(value, x, y, paint);
			xy_names[i][0] = x;
			xy_names[i][1] = y;
			i++;
		}

	}
	/**
	 * 
	 * @param canvas
	 * @category 绘制周边的名称
	 */
	private void drawNames(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);

		paint.setTextSize(width / 25);
		int left2Cir = outRadius + outRadius / 3;
		int leftX;
		int leftY;
		Rect rect;
		int i = 0;
		String value;
		for (float degree : degrees) {
			if (i == clickIndex)
				paint.setColor(Color.WHITE);
			else
				paint.setColor(color_type);
			if (degree > 135 && degree < 225) {
				left2Cir = outRadius + outRadius / 2;
			} else {
				left2Cir = outRadius + outRadius / 3;
			}
			leftX = (int) (radiusX + left2Cir
					* Math.cos(Math.toRadians(degree)));
			leftY = (int) (radiusY + left2Cir
					* Math.sin(Math.toRadians(degree)));
			value = types[i];
			rect = new Rect();
			paint.getTextBounds(value, 0, value.length(), rect);
			int x = leftX;
			int y = leftY;
			canvas.drawText(value, x, y, paint);
			xy_names[i][0] = x;
			xy_names[i][1] = y;
			i++;
		}

	}
	//判断点击位置是否在圆环上
	private boolean isInsideProgress(float x, float y) {
		boolean isInside = false;
		int sqrt = (int) Math.sqrt((x - radiusX) * (x - radiusX)
				+ (y - radiusY) * (y - radiusY));
		if (sqrt >= radiusX -  (distance+ringWidth+ringWidth/2)
				&& sqrt <= radiusX-(distance+ringWidth/2))
			return true;
		else
			return false;
	}

	//判断点击位置在圆环上的索引
	private int getClickIndex(float x, float y) {
		int  index = -1;
		int distance = (int) Math.sqrt((x - radiusX) * (x - radiusX)
				+ (y - radiusY) * (y - radiusY));
		if(y!=radiusY&&x != radiusX){
			double sinValue=((double)(y - radiusY))/distance;//-PI/2到PI/2
			double cosValue=((double)(x - radiusX))/distance;//-PI/2到PI/2
			double degree=Math.toDegrees(Math.asin(sinValue));
			if(sinValue>0&&cosValue>0){//第一象限

			}else if(sinValue>0&&cosValue<0){//第二象限
				degree=90+Math.abs(degree);
			}else if(sinValue<0&&cosValue<0){//第三象限
				degree=180+Math.abs(degree);
			}else if(sinValue<0&&cosValue>0){//第四象限
				degree=360+degree;
			}
			for(int i=0;i<degrees.length;i++){
				if(i==0){
					if(degree<=degrees[i]){
						index=i;
						break;
					}
				}else if(i==degrees.length-1){
					if(degree>degrees[i-1]&&degree<=degrees[i]){
						index=i;
						break;
					}
				}else if(degree>degrees[i-1]&&degree<=degrees[i]){
					index=i;;
					break;
				}
			}

		}
		return index;
	}
	int lastX;
	int lastY;
	int selectIndex;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) event.getX();
			lastY = (int) event.getY();
			break;
		case MotionEvent.ACTION_UP:
			int newX = (int) event.getX();
			int newY = (int) event.getY();
			if(isInsideProgress(newX,newY)){
				selectIndex=	getClickIndex(newX,newY);
				int lastIndex=-1;
					if(selectIndex!=-1){
						for(int i=0;i<selecteds.length;i++){
							if(selecteds[i]){
								lastIndex=i;
							}
							selecteds[i]=false;

						}
						if(lastIndex==selectIndex){
							selecteds[selectIndex]=false;
						}else{
							selecteds[selectIndex]=true;
						}
						postInvalidate();
					}
			}

			break;
		case MotionEvent.ACTION_MOVE:

		default:
			break;
		}
		return true;
	}

	/**
	 * @see View#measure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		widthMeasureSpec = measureWidth(widthMeasureSpec);
		heightMeasureSpec = measureHeight(heightMeasureSpec);
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

		width = widthMeasureSpec;
		height = heightMeasureSpec;
		ringWidth = width / 10;
		distance=ringWidth/4;
		outRadius = width / 4;
		innerRadius = outRadius - ringWidth;
		radiusX = width / 2;
		radiusY = width / 2;
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
