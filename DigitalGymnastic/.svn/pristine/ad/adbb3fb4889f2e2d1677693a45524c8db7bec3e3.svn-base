package com.hike.digitalgymnastic.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.NinePatch;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Looper;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.entitiy.BodeStateType;
import com.hike.digitalgymnastic.entitiy.HomeBodyData;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hiko.enterprisedigital.R;

import java.util.ArrayList;

//import com.tencent.open.a.d.e;
/*
 *体测主页下方的身体状态圆图
 *
 *
 */

@SuppressLint({ "ClickableViewAccessibility", "DrawAllocation" })
public class BodyStateView extends View {

	public BodyStateView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
		init();
	}

	public BodyStateView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public BodyStateView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init();
	}

	private int zhizheLength = dp2px(45);
	private float paddingDegree = 40;
	public String[] bodyStateBMI;
	public String[] bodyStateZhiFangLv;
	public String[] bodystateneizangzhifang;
	public String[] bodystateFubufeipanglv;
	public String[] bodystateJiroulvlv;
	public String[] bodystateDanbaizhi;
	public String[] bodystateShuifenData;
	public String[] bodystateGuliang;
	public String[] bodystateJichudaixielv;
	public String[] bodystateKuangwuzhi;

	private Bitmap bitmap;
	private Bitmap bitmap_state;
	private TextPaint titieTextPaint;// 顶部标题的画笔
	private TextPaint middleTextPaint;// 中间白字的画笔
	private TextPaint othertTextPaint;// 其它文本的画笔
	private Paint circlePaint;// 圆圈的画笔
	int middleTextSize = 16;
	int titieTextSize = 14;
	int othertTextSize = 12;
	int ringWidth = 2;

	int progress;// 圆环进度
	String danwei = "%";
	String kg = "kg";
	BodeStateType bodeStateType;// 身体状态类型 0：体质指数BMI，1：身体脂肪率 ，2：内脏脂肪水平
	String acturevalue; // 实际值
	// double standedValue;//标准值
	private HomeBodyData homeBodyData;

	public void setHomeBodyData(HomeBodyData homeBodyData) {
		this.homeBodyData = homeBodyData;
	}

	int width;// 视图宽度
	int height;// 视图高度
	int titleHeight;// 标题高度
	int circleHeight;// 标题下部的圆高度
	int radius_outer; // 外圆半径
	float radiusX_outer;// 外圆的x坐标
	float radiusY_outer;// 外圆的y坐标
	int radius_inner;// 内圆半径
	float radiusX_inner;// 内圆的x坐标
	float radiusY_inner;// 内圆的y坐标

	float littleCircleRadius; // 指针划过小球半径
	float rate;
	String suffix = "%";
	String suffixKg = "kg";
	private Bitmap jidutiao_3x;
	private Bitmap hongqi_3x;
	private TextPaint progressTextPaint;
	private Bitmap tuanyuan;
	private int progresspadding;
	private int hFlength;
	private int MARGIN_KEDU=dp2px(2);
	private void init() {

		titieTextPaint = new TextPaint();
		titieTextPaint.setAntiAlias(true);
		titieTextPaint.setStyle(Paint.Style.FILL);
		titieTextPaint.setTextAlign(Align.CENTER);
		titieTextPaint.setColor(Color.rgb(170, 172, 205));
		titieTextPaint.setTextSize(sp2px(middleTextSize));// 字体大小
		titieTextPaint.setStrokeWidth(3);

		middleTextPaint = new TextPaint();
		middleTextPaint.setAntiAlias(true);
		middleTextPaint.setStyle(Paint.Style.FILL);
		middleTextPaint.setTextAlign(Align.CENTER);
		middleTextPaint.setColor(Color.WHITE);
		middleTextPaint.setTextSize(sp2px(middleTextSize));// 字体大小
		titieTextPaint.setStrokeWidth(3);

		othertTextPaint = new TextPaint();
		othertTextPaint.setAntiAlias(true);
		othertTextPaint.setStyle(Paint.Style.FILL);
		othertTextPaint.setTextAlign(Align.CENTER);
		othertTextPaint.setColor(Color.rgb(170, 172, 205));
		othertTextPaint.setTextSize(sp2px(othertTextSize));// 字体大小

		circlePaint = new Paint();
		circlePaint.setAntiAlias(true);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setTextAlign(Align.CENTER);
		circlePaint.setColor(getResources().getColor(R.color.aver_color));
		// circlePaint.setColor(getResources().getColor(R.color.aver_cal_color));
		circlePaint.setStrokeWidth(dp2px(ringWidth)); // 设置圆环的宽度

		progressTextPaint = new TextPaint();
		progressTextPaint.setAntiAlias(true);
		progressTextPaint.setStyle(Paint.Style.FILL);
		progressTextPaint.setTextAlign(Align.CENTER);
		progressTextPaint.setColor(Color.rgb(170, 172, 205));
		progressTextPaint.setTextSize(sp2px(7));// 字体大小
		progressTextPaint.setStrokeWidth(3);

		bodyStateBMI = getResources().getStringArray(R.array.bodystatebmi);
		bodyStateZhiFangLv = getResources().getStringArray(
				R.array.bodystatezhifanglv);
		bodystateneizangzhifang = getResources().getStringArray(
				R.array.bodystateneizangzhifang);
		bodystateFubufeipanglv = getResources().getStringArray(
				R.array.bodystateFubufeipanglv);
		bodystateJiroulvlv = getResources().getStringArray(
				R.array.bodystateJiroulvlv);
		bodystateDanbaizhi = getResources().getStringArray(
				R.array.bodystateDanbaizhi);
		bodystateShuifenData = getResources().getStringArray(
				R.array.bodystateShuifenData);
		bodystateGuliang = getResources().getStringArray(
				R.array.bodystateGuliang);
		bodystateJichudaixielv = getResources().getStringArray(
				R.array.bodystateJichudaixielv);
		bodystateKuangwuzhi = getResources().getStringArray(
				R.array.bodystateKuangwuzhi);
	}

	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// 小圆球指针参数
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_body_needle);
		bitmap_state = BitmapFactory.decodeResource(getResources(),
				R.mipmap.icon_bodystate);
		jidutiao_3x = BitmapFactory.decodeResource(getResources(),
				R.mipmap.jindutiao_3x);
		if (isbodystatequalified) {

			hongqi_3x = BitmapFactory.decodeResource(getResources(),
					R.mipmap.lvqi_3x);
			tuanyuan = BitmapFactory.decodeResource(getResources(),
					R.mipmap.green_yuan);
		} else {

			hongqi_3x = BitmapFactory.decodeResource(getResources(),
					R.mipmap.hongqi_3x);
			tuanyuan = BitmapFactory.decodeResource(getResources(),
					R.mipmap.white_yuan);
		}
		hFlength = (int) getResources().getDimension(R.dimen.x9);
		progresspadding = dp2px(20);

		// 计算绘制的半径和坐标开始
		width = getWidth();
		height = getHeight();
		titleHeight = height / 8;
		circleHeight = height * 7 / 8;
		radius_outer = circleHeight / 2;
		radiusX_outer = width / 2;
		radiusY_outer = titleHeight / 2 + circleHeight / 2;
		radius_inner = circleHeight / 2 / 2;// 内圆是外圆半径的2/3
		radiusX_inner = width / 2;
		radiusY_inner = titleHeight / 2 + circleHeight / 2;

		if (circleHeight >= width) {
			radius_outer = width / 2;
			radiusX_outer = width / 2;
			radiusY_outer = titleHeight / 2 + width / 2;
			radius_inner = width / 2 * 2 / 3;
			radiusX_inner = width / 2;
			radiusY_inner = titleHeight / 2 + width / 2;
		}

		Rect rect = new Rect();
		othertTextPaint.getTextBounds("0000", 0, 4, rect);
		double textduijiaolength = Math.sqrt(rect.width() * rect.width()
				+ rect.height() * rect.height());
		float rate = 1 - (float) textduijiaolength / radius_outer
				- (float) 0.15;
		radius_inner = (int) (radius_outer * rate);

		// 计算绘制的半径和坐标结束
		// canvas添加抗锯齿效果
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG
				| Paint.FILTER_BITMAP_FLAG));
		if (homeBodyData != null) {
			// 绘制体质指数BMI
			if (bodeStateType == BodeStateType.体质指数BMI) {
				drawBMIView(canvas);
			} else if (bodeStateType == BodeStateType.身体脂肪率) {
				drawZhifanglvView(canvas);
			} else if (bodeStateType == BodeStateType.内脏脂肪水平) {
				drawNeizangzhifangView(canvas);
			} else if (bodeStateType == BodeStateType.腹部肥胖率) {
				drawFubufeipanglvView(canvas);
			} else if (bodeStateType == BodeStateType.肌肉率) {
				drawJiroulvView(canvas);
			} else if (bodeStateType == BodeStateType.蛋白质重量比) {
				drawDanbaizhiView(canvas);
			} else if (bodeStateType == BodeStateType.身体水分) {
				drawShuifenView(canvas);
			} else if (bodeStateType == BodeStateType.骨量) {
				drawGuliangView(canvas);
			}else if(bodeStateType == BodeStateType.身体年龄){
				drawShenTiNianLingView(canvas);
			}
			else if(bodeStateType == BodeStateType.细胞重量){
				drawXiBaoZhongLiangView(canvas);
			}
			else if(bodeStateType == BodeStateType.矿物质含量){
				drawKuangwuzhiView(canvas);
			}
		}

	}


	float[] startXY = new float[2];
	float[] endXY = new float[2];

	private void drawTopView(Canvas canvas, String name, String value) {
	}

	int bitmapstatepadding = dp2px(3);
	private Paint linePaint;

	// 绘制体质BMI视图
	private void drawBMIView(Canvas canvas) {
		Bitmap bitmap_BMI;
		if (isbodystatequalified) {
			bitmap_BMI = BitmapFactory.decodeResource(getResources(),
					R.mipmap.bmi_green_3x);
		} else {
			bitmap_BMI = BitmapFactory.decodeResource(getResources(),
					R.mipmap.bmi_white_3x);
		}
		double bmiValue = homeBodyData.getBmiData().getValue();
		double normalFloor = homeBodyData.getBmiData().getStandard()
				.getNormalFloor();

		double heavierFloor = homeBodyData.getBmiData().getStandard()
				.getHeavierFloor();
		double fatFloor = homeBodyData.getBmiData().getStandard().getFatFloor();
		double veryFatFloor = homeBodyData.getBmiData().getStandard()
				.getVeryFatFloor();
		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(normalFloor+"");
		gradelist.add(heavierFloor+"");
		gradelist.add(fatFloor+"");
		gradelist.add(veryFatFloor+"");
		String value = bodyStateBMI[homeBodyData.getBmiData().getGrade() + 1];
		baseDraw(canvas, bitmap_BMI, bmiValue, gradelist, value,"","");

	}

	// 绘制脂肪率视图
	private void drawZhifanglvView(Canvas canvas) {
		Bitmap bitmap_Zhifanglv;
		if (isbodystatequalified) {
			bitmap_Zhifanglv = BitmapFactory.decodeResource(getResources(),
					R.mipmap.zhifanglv_green_3x);
		} else {
			bitmap_Zhifanglv = BitmapFactory.decodeResource(getResources(),
					R.mipmap.zhifanglv_white_3x);
		}

		double zhiFangLvValue = homeBodyData.getZhifanglvData().getValue();
		double standardFloor = homeBodyData.getZhifanglvData().getStandard().getStandardFloor();

		double mildFatFloor = homeBodyData.getZhifanglvData().getStandard().getMildFatFloor();
		double fatFloor = homeBodyData.getZhifanglvData().getStandard().getFatFloor();		
		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(standardFloor+"");
		gradelist.add(mildFatFloor+"");
		gradelist.add(fatFloor+"");
		String value = bodyStateZhiFangLv[homeBodyData.getZhifanglvData().getGrade() + 1];
		baseDraw(canvas, bitmap_Zhifanglv, zhiFangLvValue, gradelist, value,danwei,"");
	}

	

	// 绘制内脏脂肪视图
	private void drawNeizangzhifangView(Canvas canvas) {


		Bitmap bitmap_neizang = BitmapFactory.decodeResource(getResources(),
				R.mipmap.neizangzhifang_green_3x);
		if (!isbodystatequalified) {
			bitmap_neizang = BitmapFactory.decodeResource(getResources(),
					R.mipmap.neizangzhifang_white_3x);
		}
		double balancedFloor = homeBodyData.getNeizangzhifangData()
				.getStandard().getBalancedFloor();
		double warningFloor = homeBodyData.getNeizangzhifangData()
				.getStandard().getWarningFloor();
		double fatFloor = homeBodyData.getNeizangzhifangData().getStandard()
				.getFatFloor();
		double veryFatFloor = homeBodyData.getNeizangzhifangData()
				.getStandard().getVeryFatFloor();
		double neiZangZhiFangValue = homeBodyData.getNeizangzhifangData()
				.getValue();
		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(balancedFloor+"");
		gradelist.add(warningFloor+"");
		gradelist.add(fatFloor+"");
		gradelist.add(veryFatFloor+"");
		String value = bodystateneizangzhifang[homeBodyData.getNeizangzhifangData().getGrade() + 1];
		baseDraw(canvas, bitmap_neizang, neiZangZhiFangValue, gradelist, value,danwei,"");

	}

	// 绘制腹部肥胖率视图
	private void drawFubufeipanglvView(Canvas canvas) {
		Bitmap bitmap_fubu = BitmapFactory.decodeResource(getResources(),
				R.mipmap.fubufeipang_green_3x);
		if (!isbodystatequalified) {
			bitmap_fubu = BitmapFactory.decodeResource(getResources(),
					R.mipmap.fubufeipang_white_3x);
		}

		double fuBuZhiFangValue = homeBodyData.getFubufeipanglvData()
				.getValue();
		double standardFloor = homeBodyData.getFubufeipanglvData()
				.getStandard().getStandardFloor();
		double StandardCeil = homeBodyData.getFubufeipanglvData().getStandard()
				.getStandardCeil();

		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(standardFloor+"");
		gradelist.add(StandardCeil+"");
		String value = bodystateFubufeipanglv[homeBodyData.getFubufeipanglvData()
		               				.getGrade() + 1];
		baseDraw(canvas, bitmap_fubu, fuBuZhiFangValue, gradelist, value,danwei,"");
	}

	// 绘制肌肉率视图
	private void drawJiroulvView(Canvas canvas) {

		Bitmap bitmap_JiRouLv = BitmapFactory.decodeResource(getResources(),
				R.mipmap.jiroulv_green_3x);
		if (!isbodystatequalified) {
			bitmap_JiRouLv = BitmapFactory.decodeResource(getResources(),
					R.mipmap.jiroulv_white_3x);
		}

		double jiRouLvValue = homeBodyData.getJiroulvData().getValue();
		double standardFloor = homeBodyData.getJiroulvData()
				 .getStandard().getStandardFloor();
		double excellentFloor = homeBodyData.getJiroulvData()
				 .getStandard().getExcellentFloor();

		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(standardFloor+"");
		gradelist.add(excellentFloor+"");
		String value = bodystateJiroulvlv[homeBodyData.getJiroulvData().getGrade() + 1];
		baseDraw(canvas, bitmap_JiRouLv, jiRouLvValue, gradelist, value,danwei,"");
	}

	// 绘制蛋白质视图
	private void drawDanbaizhiView(Canvas canvas) {
		Bitmap bitmap_DanBaiZhi = BitmapFactory.decodeResource(getResources(),
				R.mipmap.danbaizhi_green_3x);
		if (!isbodystatequalified) {
			bitmap_DanBaiZhi = BitmapFactory.decodeResource(getResources(),
					R.mipmap.danbaizhi_white_3x);
		}

		double danBaiZhiValue = homeBodyData.getDanbaizhiData().getValue();
		double standardFloor = homeBodyData.getDanbaizhiData().getStandard()
				.getStandardFloor();
		double standardCeil = homeBodyData.getDanbaizhiData().getStandard()
				.getStandardCeil();

		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(standardFloor+"");
		gradelist.add(standardCeil+"");
		String gradeLevel = bodystateDanbaizhi[homeBodyData.getDanbaizhiData().getGrade() + 1];
		baseDraw(canvas, bitmap_DanBaiZhi, danBaiZhiValue, gradelist, gradeLevel,danwei,"");
		
	}

	// 绘制身体水分视图
	private void drawShuifenView(Canvas canvas) {
		// 1111
		Bitmap bitmap_ShenTiShuiFen = BitmapFactory.decodeResource(getResources(),
				R.mipmap.shentishuifen_green_3x);
		if (!isbodystatequalified) {
			bitmap_ShenTiShuiFen = BitmapFactory.decodeResource(getResources(),
					R.mipmap.shentishuifen_white_3x);
		}

		double shuiFenValue = homeBodyData.getShuifenData().getValue();
		double normalFloor = homeBodyData.getShuifenData()
				 .getStandard().getNormalFloor();
		double normaldCeil = homeBodyData.getShuifenData().getStandard().getNormalCeil();

		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(normalFloor+"");
		gradelist.add(normaldCeil+"");
		String gradeLevel = bodystateDanbaizhi[homeBodyData.getShuifenData().getGrade() + 1];
		baseDraw(canvas, bitmap_ShenTiShuiFen, shuiFenValue, gradelist, gradeLevel,danwei,"");
	}

	
	// 绘制骨量视图
	private void drawGuliangView(Canvas canvas) {
		Bitmap bitmap_Guliang = BitmapFactory.decodeResource(getResources(),
				R.mipmap.guliang_green_3x);
		if (!isbodystatequalified) {
			bitmap_Guliang = BitmapFactory.decodeResource(getResources(),
					R.mipmap.guliang_white_3x);
		}

		double guLiangValue = homeBodyData.getGuliangData().getValue();
		double normalFloor = homeBodyData.getGuliangData().getStandard().getNormalFloor();
		double normaldCeil = homeBodyData.getGuliangData().getStandard().getNormalCeil();

		ArrayList<String> gradelist = new ArrayList<String>();
		gradelist.add(normalFloor+"");
		gradelist.add(normaldCeil+"");
		String gradeLevel = bodystateDanbaizhi[homeBodyData.getGuliangData().getGrade() + 1];
		baseDraw(canvas, bitmap_Guliang, guLiangValue, gradelist, gradeLevel,kg,"");
	}

	public void setBodyStateType(BodeStateType bodeStateType) {
		this.bodeStateType = bodeStateType;
	}

	public void setValue(String acturevalue) {
		this.acturevalue = acturevalue;
		reDraw();
	}

	private void reDraw() {
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	float lastX;
	float lastY;

	public boolean onTouchEvent(MotionEvent event) {
		System.out.println("event.getAction()=="+event.getAction());
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			lastY = event.getY();
			System.out.println("event.getAction()=lastY=zoule"+lastY);
			break;
		case MotionEvent.ACTION_UP:
			float currentX = event.getX();
			float currentY = event.getY();
			if (lastX >= startXY[0] && lastX <= endXY[0]
					&& currentX >= startXY[0] && currentX <= endXY[0]
					&& lastY >= startXY[1] && lastY <= endXY[1]
					&& currentY >= startXY[1] && currentY <= endXY[1]){
				if (Math.abs(lastX - event.getX()) < 10) {
					if (onTitleClickListener != null && bodeStateType != null) {
						onTitleClickListener.OnTitleClick(bodeStateType);
						System.out.println("event.getAction()==zoule");
						return true;
					}else{
						return super.onTouchEvent(event);
					}
				}else{
					return super.onTouchEvent(event);
				}
			}else {
				return super.onTouchEvent(event);
			}
//			break;
		case MotionEvent.ACTION_MOVE:

			break;
		case MotionEvent.ACTION_CANCEL:

			break;
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
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec/2);
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

	private void drawKuangwuzhiView(Canvas canvas) {
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.kuangwuzhi_3x);
//		String str = "kg";
//		String value = homeBodyData.getKuangwuzhi()+str;
//		// 矿物质含量
//		baseDraw2(canvas, bmp,value);

		Bitmap bitmap_kuangwuzhi = BitmapFactory.decodeResource(getResources(),
				R.mipmap.kuangwuzhi_white_3x);

		double value = homeBodyData.getKuangwuzhi();


		ArrayList gradelist = new ArrayList();
		gradelist.add("2.8");
		gradelist.add("4.0");
		int customer_age = PreferencesUtils.getInt(getContext(), "customer_age");

		String gradeLevel = bodystateKuangwuzhi[1];

		baseDraw(canvas, bitmap_kuangwuzhi, value, gradelist, gradeLevel,kg,"矿物质含量");
	}
	private void drawJiChuDaiXieLv(Canvas canvas) {
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.kuangwuzhi_3x);
//		String str = "kg";
//		String value = homeBodyData.getKuangwuzhi()+str;
//		// 矿物质含量
//		baseDraw2(canvas, bmp,value);

		Bitmap bitmap_JiChuDaiXieLv = BitmapFactory.decodeResource(getResources(),
				R.mipmap.jichudaixie_white_3x);

		double value = homeBodyData.getJichudaixie();


		ArrayList gradelist = new ArrayList();
		gradelist.add("100");
		gradelist.add("9999");

		String gradeLevel = bodystateJichudaixielv[1];

		baseDraw(canvas, bitmap_JiChuDaiXieLv, value, gradelist, gradeLevel,"kcal","矿物质含量");
	}

	private void drawXiBaoZhongLiangView(Canvas canvas) {
		// 细胞重量
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.xibaohanliang_3x);
		String str = "kg";
		String value = homeBodyData.getXibaozhongliang()+str;

		baseDraw2(canvas, bmp,value);
	}

	private void drawShenTiNianLingView(Canvas canvas) {
//		// 身体年龄
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.bodyage_3x);
//		String str = "岁";
//		String value = homeBodyData.getShentinianling()+str;
//
//		//
//		baseDraw2(canvas, bmp,value);
		Bitmap bitmap_ShenTiNianLing = BitmapFactory.decodeResource(getResources(),
				R.mipmap.shentinianling_green_3x);

		String value = homeBodyData.getShentinianling()+"";


		ArrayList gradelist = new ArrayList();
		gradelist.add("10");
		gradelist.add("100");
		int customer_age = PreferencesUtils.getInt(getContext(),"customer_age");

//		String gradeLevel = bodystateDanbaizhi[homeBodyData.getShuifenData().getGrade() + 1];
		String gradeLevel ="";
		if(homeBodyData.getShentinianling()>customer_age || homeBodyData.getShentinianling()==customer_age){
			gradeLevel = "超过您的实际年龄"+(homeBodyData.getShentinianling()-customer_age)+"岁";
		}else{
			gradeLevel = "您比实际年龄年轻"+(customer_age-homeBodyData.getShentinianling())+"岁";
		}
		baseDraw(canvas, bitmap_ShenTiNianLing, homeBodyData.getShentinianling(), gradelist, gradeLevel,"岁","身体年龄");
	}
	private void baseDraw(Canvas canvas, Bitmap bmp, double stateValue,
			ArrayList<String> grade, String str,String danw,String modeName) {
//		Bitmap bmp2 = BitmapFactory.decodeResource(getResources(), R.mipmap.line_bg);
//		RectF rect2 = new RectF();
//		rect2.left = 0;
//		rect2.right = bmp2.getWidth();
//		rect2.top = 0;
//		rect2.bottom = bmp2.getHeight();
//		canvas.drawBitmap(bmp2, null, rect2, titieTextPaint);
		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setStyle(Paint.Style.FILL);
		linePaint.setTextAlign(Align.CENTER);
		linePaint.setColor(Color.rgb(170, 172, 205));
		linePaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 5, linePaint);
		int int_niangling=0;
		if(modeName.equals("身体年龄")){
			int_niangling = (int)stateValue;
		}
		double fuBuZhiFangValue = stateValue;

		// 图片

		RectF rectf = new RectF();
		rectf.left = bmp.getHeight() / 3;
		rectf.right = bmp.getHeight();
		rectf.top = bmp.getHeight()/3;
		rectf.bottom = bmp.getHeight();
		canvas.drawBitmap(bmp, null, rectf, titieTextPaint);

		 startXY[0]=rectf.left;
		 startXY[1]=rectf.top;
		 endXY[0]=rectf.right;
		 endXY[1]=rectf.bottom;
		
		Rect rect = new Rect();
		String value="";
		if(modeName.equals("身体年龄")){
			value = int_niangling + "";
		}else{

			value = fuBuZhiFangValue + "";

		}
//		String value = fuBuZhiFangValue + "";
		//设置值的颜色
		titieTextPaint.setColor(getResources().getColor(R.color.white_cce7ec));
		float startWidth;
		if(modeName.equals("身体年龄") || modeName.equals("基础代谢率")){
			startWidth = width * 3 / 7;
		}else{
			startWidth = width * 4 / 7;
		}
		//具体数值

		titieTextPaint.getTextBounds(value, 0, value.length(), rect);
		titieTextPaint.setTextSize(sp2px(18));
		int height2 = rect.height() + progresspadding + hFlength*2;

		canvas.drawText(value, startWidth, height2, titieTextPaint);

		float valueTextWidth = rect.right;

		if(modeName.equals("基础代谢率")){
			danw = danw+"/d";
		}
		//绘制单位
		titieTextPaint.setTextSize(sp2px(10));
		progressTextPaint.getTextBounds(danw, 0, danw.length(), rect);
		canvas.drawText(danw, startWidth+valueTextWidth/2+rect.width(), height2, titieTextPaint);

		// rect = new Rect();
//		value = bodystateFubufeipanglv[homeBodyData.getFubufeipanglvData()
//				.getGrade() + 1];
		progressTextPaint.getTextBounds(str, 0, str.length(), rect);
		progressTextPaint.setColor(Color.rgb(246, 154, 157));
		progressTextPaint.setTextSize(sp2px(10));
		float strWidth = rect.width();
		// 进度条
		Rect rectp = new Rect();
		rectp.left = (int) (rectf.right + progresspadding);
		rectp.right = (int) (width - rectf.left);
		rectp.top = (int) rectf.bottom -hFlength;
		rectp.bottom = (int) (rectf.bottom -hFlength+ hFlength);
		canvas.drawBitmap(jidutiao_3x, null, rectp, titieTextPaint);
		circlePaint.setColor(getResources().getColor(R.color.aver_cal_color));

		// 圆点
		Paint paint = new Paint();
		/* 去锯齿 */
		paint.setAntiAlias(true);
		/* 设置paint的颜色 */
//		paint.setColor(Color.rgb(246, 154, 157));
		if(isbodystatequalified){
			paint.setColor(getResources().getColor(R.color.green_73dcff));
		}else{
			paint.setColor(getResources().getColor(R.color.white_cce7ec));
		}
		/* 设置paint 的style为 FILL：实心 */
		paint.setStyle(Paint.Style.FILL);

		int length = rectp.right - rectp.left;
		int midlength = length * 5 / 7;
		int othlength = length * 1 / 7;
		int gradelength = grade.size();
		double standardFloor = Double.parseDouble(grade.get(0));
		double StandardCeil = Double.parseDouble(grade.get(gradelength - 1));
		int standardFloor_int=0;
		int StandardCeil_int=0;
		if(modeName.equals("身体年龄")){
			standardFloor_int = Integer.parseInt(grade.get(0));
			StandardCeil_int = Integer.parseInt(grade.get(gradelength - 1));
		}

		int fuBuZhiFanglength;

		if (fuBuZhiFangValue < standardFloor) {
			fuBuZhiFanglength = (int) (othlength * fuBuZhiFangValue / standardFloor);
		} else if (fuBuZhiFangValue < StandardCeil) {
			fuBuZhiFanglength = (int) (othlength + (fuBuZhiFangValue - standardFloor)
					/ (StandardCeil - standardFloor) * midlength);
		} else {
			fuBuZhiFanglength = othlength * 2 + midlength;
		}
		int extra=(int) getResources().getDimension(R.dimen.x5);
		progressTextPaint.setTextSize(sp2px(7));// 字体大小
		progressTextPaint.setColor(getResources().getColor(R.color.white_cce7ec));
		RectF kedu_rect = new RectF();

		Bitmap kedu_3x = BitmapFactory.decodeResource(getResources(), R.mipmap.kedu_3x);
		Double level;
		for (int i = 0; i < gradelength; i++) {

			level = Double.parseDouble(grade.get(i));

			if (i == 0) {
				// 进度条刻度值
				rect = new Rect();
				if(modeName.equals("身体年龄")){
					if(danw.equals("kg") || danw.equals("kcal")){
						value = String.valueOf(standardFloor_int)+danw;
					}else{
						value = String.valueOf(standardFloor_int);
					}
				}else{
					if(danw.equals("kg") || danw.equals("kcal")){
						value = String.valueOf(standardFloor)+danw;
					}else{
						value = String.valueOf(standardFloor);
					}
				}

				progressTextPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rectp.left + othlength, rectp.bottom
						+ hFlength + extra, progressTextPaint);
				//

				kedu_rect.left = rectp.left + othlength;
				kedu_rect.right =rectp.left + othlength+kedu_3x.getWidth();
				kedu_rect.top = rectp.top + hFlength+MARGIN_KEDU;
				kedu_rect.bottom = rectp.top + hFlength+kedu_3x.getHeight()+MARGIN_KEDU;
				canvas.drawBitmap(kedu_3x,null,kedu_rect,progressTextPaint);
				/* 画一个实心圆 */
				canvas.drawCircle(rectp.left + othlength, rectp.top - hFlength, 2, paint);
			} else if (i == gradelength - 1) {
				rect = new Rect();
//				value = String.valueOf(StandardCeil);
				if(modeName.equals("身体年龄")){
					if(danw.equals("kg") || danw.equals("kcal")){
						value = String.valueOf(StandardCeil_int)+danw;
					}else{
						value = String.valueOf(StandardCeil_int);
					}
				}else{
					if(danw.equals("kg") || danw.equals("kcal")){
						value = String.valueOf(StandardCeil)+danw;
					}else{
						value = String.valueOf(StandardCeil);
					}
				}

				progressTextPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rectp.left + othlength + midlength,
						rectp.bottom + hFlength+extra, progressTextPaint);
				//画刻度图片
				kedu_rect.left = rectp.left + othlength + midlength;
				kedu_rect.right =rectp.left + othlength+kedu_3x.getWidth() + midlength;
				kedu_rect.top = rectp.top + hFlength+MARGIN_KEDU;
				kedu_rect.bottom = rectp.top + hFlength+kedu_3x.getHeight()+MARGIN_KEDU;
				canvas.drawBitmap(kedu_3x, null, kedu_rect, progressTextPaint);

				canvas.drawCircle(rectp.left + othlength + midlength, rectp.top
						- hFlength, 2, paint);
			} else {
				int level_niangling=0;
				if(modeName.equals("身体年龄")){
					level_niangling = Integer.parseInt(grade.get(i));
					rect = new Rect();

					if(danw.equals("kg") || danw.equals("kcal")){
						value = String.valueOf(level_niangling)+danw;
					}else{
						value = String.valueOf(level_niangling);
					}
					progressTextPaint.getTextBounds(value, 0, value.length(), rect);
					canvas.drawText(value,
							(float) (rectp.left + othlength + midlength
									* (level_niangling - standardFloor)
									/ (StandardCeil - standardFloor)), rectp.bottom
									+ hFlength+extra, progressTextPaint);

					//画刻度图片
					kedu_rect.left = (float)(rectp.left + othlength +  midlength * (level_niangling - standardFloor) / (StandardCeil - standardFloor));
					kedu_rect.right =(float)(rectp.left + othlength+kedu_3x.getWidth() + midlength * (level_niangling - standardFloor) / (StandardCeil - standardFloor));
					kedu_rect.top = rectp.top + hFlength+MARGIN_KEDU;
					kedu_rect.bottom = rectp.top + hFlength + kedu_3x.getHeight()+MARGIN_KEDU;
					canvas.drawBitmap(kedu_3x, null, kedu_rect, progressTextPaint);

					canvas.drawCircle((float) (rectp.left + othlength + midlength
							* (level_niangling - standardFloor)
							/ (StandardCeil - standardFloor)), rectp.top - hFlength, 2, paint);
				}else {
					rect = new Rect();
//				value = String.valueOf(level);

					if (danw.equals("kg") || danw.equals("kcal")) {
						value = String.valueOf(level) + danw;
					} else {
						value = String.valueOf(level);
					}
					progressTextPaint.getTextBounds(value, 0, value.length(), rect);
					canvas.drawText(value,
							(float) (rectp.left + othlength + midlength
									* (level - standardFloor)
									/ (StandardCeil - standardFloor)), rectp.bottom
									+ hFlength + extra, progressTextPaint);

					//画刻度图片
					kedu_rect.left = (float) (rectp.left + othlength + midlength * (level - standardFloor) / (StandardCeil - standardFloor));
					kedu_rect.right = (float) (rectp.left + othlength + kedu_3x.getWidth() + midlength * (level - standardFloor) / (StandardCeil - standardFloor));
					kedu_rect.top = rectp.top + hFlength + MARGIN_KEDU;
					kedu_rect.bottom = rectp.top + hFlength + kedu_3x.getHeight() + MARGIN_KEDU;
					canvas.drawBitmap(kedu_3x, null, kedu_rect, progressTextPaint);

					canvas.drawCircle((float) (rectp.left + othlength + midlength
							* (level - standardFloor)
							/ (StandardCeil - standardFloor)), rectp.top - hFlength, 2, paint);
				}

			}
		}
		//级别描述文字字体颜色（正常，优秀）
//		if(isbodystatequalified){
			progressTextPaint.setColor(Color.rgb(255, 255, 255));
//		}else{
//			progressTextPaint.setColor(getResources().getColor(R.color.green_73dcff));
//		}
		progressTextPaint.setTextSize(sp2px(10));
		canvas.drawText(str, rectp.left + othlength*2 + midlength-strWidth/2, height2,
				progressTextPaint);
		// 进度
		RectF rectpro = new RectF();
		rectpro.left = rectp.left;
		rectpro.right = rectp.left + fuBuZhiFanglength + bitmapstatepadding;
		rectpro.top = rectp.top;
		rectpro.bottom = rectp.top + hFlength;
		// //把.9图片添加到画布上
		// canvas.drawBitmap(tuanyuan, null, rectpro, titieTextPaint);
		NinePatch np = new NinePatch(tuanyuan, tuanyuan.getNinePatchChunk(),
				null);
		np.draw(canvas, rectpro);

		// 红旗
		RectF recthongqi = new RectF();
		recthongqi.left = rectp.left + fuBuZhiFanglength;
		recthongqi.right = rectp.left + fuBuZhiFanglength
				+ hongqi_3x.getWidth();
		recthongqi.top = rectp.top - hongqi_3x.getHeight() - hFlength
				- bitmapstatepadding * 2;
		recthongqi.bottom = rectp.top - hFlength - bitmapstatepadding * 2;
		canvas.drawBitmap(hongqi_3x, null, recthongqi, titieTextPaint);

	}

	private void baseDraw2(Canvas canvas, Bitmap bmp,String value) {

		linePaint = new Paint();
		linePaint.setAntiAlias(true);
		linePaint.setStyle(Paint.Style.FILL);
		linePaint.setTextAlign(Align.CENTER);
		linePaint.setColor(Color.rgb(170, 172, 205));
		linePaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 5, linePaint);


		// 图片

		RectF rectf = new RectF();
		rectf.left = bmp.getHeight() / 3;
		rectf.right = bmp.getHeight();
		rectf.top = height/2-bmp.getHeight()/3;
		rectf.bottom = height/2+bmp.getHeight()/3;
		canvas.drawBitmap(bmp, null, rectf, titieTextPaint);

//		 startXY[0]=rectf.left;
//		 startXY[1]=rectf.top;
//		 endXY[0]=rectf.right;
//		 endXY[1]=rectf.bottom;
		//值
		linePaint.setColor(Color.rgb(0, 188, 163));
		int textsize = (int) getResources().getDimension(R.dimen.x30);
		linePaint.setTextSize(sp2px(30));
		Rect rect = new Rect();
		linePaint.getTextBounds(value, 0, value.length(), rect);
		canvas.drawText(value, width * 2 / 3, height/2+rect.height()/2, linePaint);
//		//单位
//		linePaint.setColor(Color.rgb(0, 188, 163));
//		textsize = (int) getResources().getDimension(R.dimen.x30);
//		linePaint.setTextSize(dp2px(15));
//		Rect rect2 = new Rect();
//		linePaint.getTextBounds(str, 0, str.length(), rect2);
//		canvas.drawText(str, width * 2 / 3+rect2.width()/2+rect.width()/2, height/2+rect.height()/2, linePaint);
		
	}
	private OnTitleClickListener onTitleClickListener;
	private Boolean isbodystatequalified = true;

	public void setOnTitleClickListener(
			OnTitleClickListener onTitleClickListener) {
		this.onTitleClickListener = onTitleClickListener;
	}

	public interface OnTitleClickListener {
		public void OnTitleClick(BodeStateType bodeStateType);
	}

	public void setIsBodystatequalified(Boolean b) {
		isbodystatequalified = b;
	}
//	public static int getTextWidth(Paint paint, String str) {
//		       int iRet = 0;
//		        if (str != null && str.length() > 0) {
//			            int len = str.length();
//			            float[] widths = new float[len];
//			            paint.getTextWidths(str, widths);
//		            for (int j = 0; j < len; j++) {
//			               iRet += (int) Math.ceil(widths[j]);
//			           }
//			       }
//	       return iRet;
//		    }

}
