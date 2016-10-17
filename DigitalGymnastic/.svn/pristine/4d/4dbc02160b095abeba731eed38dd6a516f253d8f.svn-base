package com.hike.digitalgymnastic.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hike.digitalgymnastic.entitiy.BmiStandard;
import com.hike.digitalgymnastic.entitiy.DanbaizhiStandard;
import com.hike.digitalgymnastic.entitiy.FubufeipanglvStandard;
import com.hike.digitalgymnastic.entitiy.JiroulvStandard;
import com.hike.digitalgymnastic.entitiy.NeizangzhifangStandard;
import com.hike.digitalgymnastic.entitiy.PeriodBmiData;
import com.hike.digitalgymnastic.entitiy.PeriodDanbaizhiData;
import com.hike.digitalgymnastic.entitiy.PeriodFubufeipanglvData;
import com.hike.digitalgymnastic.entitiy.PeriodJiroulvData;
import com.hike.digitalgymnastic.entitiy.PeriodNeizangzhifangData;
import com.hike.digitalgymnastic.entitiy.PeriodShuifenData;
import com.hike.digitalgymnastic.entitiy.PeriodTizhongData;
import com.hike.digitalgymnastic.entitiy.PeriodZhifanglvData;
import com.hike.digitalgymnastic.entitiy.ShuifenStandard;
import com.hike.digitalgymnastic.entitiy.TizhongStandard;
import com.hike.digitalgymnastic.entitiy.ZhifanglvStandard;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class HistoryBodyCheckView extends View {
	private int blankMode = -100;

	public enum Type {
		tizhong, tizhizhishu, shentizhifanglv, neizangzhifangshuiping, fubufeipanglv, jiroulv, shentishuifen, danbaizhizhiliangbi
	}

	int width;
	int height;
	OnTouchWeekListener onTouchWeekListener;

	public void setOnTouchWeekListener(OnTouchWeekListener onTouchWeekListener) {
		this.onTouchWeekListener = onTouchWeekListener;
	}

	int bg_color = getResources().getColor(R.color.app_bg);

	public HistoryBodyCheckView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	public HistoryBodyCheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init();
	}

	public HistoryBodyCheckView(Context context) {
		super(context);
		init();
	}

	// 横向的虚线
	private Bitmap bitmap_horiLine;
	// 纵向的虚线
	private Bitmap bitmap_vertLine;
	public String[] bodyStateBMI;
	public String[] bodyStateZhiFangLv;
	public String[] bodystateneizangzhifang;
	public String[] bodystateFubufeipanglv;
	public String[] bodystateJiroulvlv;
	public String[] bodystateDanbaizhi;
	public String[] bodystateShuifenData;
	public String[] bodystateGuliang;
	private Paint textPaint;// 文本的画笔
	private Paint datePaint;// 日期的画笔
	private Paint bimapPaint;// 位图的画笔
	private Paint linePaint;// 连线的画笔
	private Paint dotPaint;// 其它文本的画笔
	int textSize = 12;
	int dateSize = 12;
	int alertTextSize = 10;
	int colorCircleBlankID = getResources().getColor(R.color.banlkid);
	int colorCircleID = getResources().getColor(R.color.colorCircleID);
	int colorCircleLine = Color.WHITE;;
	int colorAlert = getResources().getColor(R.color.banlkid);
	int colorDate = Color.rgb(170, 172, 205);

	private void init() {

		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setStyle(Paint.Style.FILL);
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setColor(Color.rgb(170, 172, 205));
		textPaint.setTextSize(sp2px(textSize));// 字体大小

		datePaint = new Paint();
		datePaint.setAntiAlias(true);
		datePaint.setStyle(Paint.Style.FILL);
		datePaint.setTextAlign(Align.CENTER);
		datePaint.setColor(colorDate);
		datePaint.setTextSize(sp2px(dateSize));// 字体大小

		bimapPaint = new Paint();
		bimapPaint.setAntiAlias(true);
		bimapPaint.setStyle(Paint.Style.FILL);
		bimapPaint.setTextAlign(Align.CENTER);
		bimapPaint.setColor(Color.WHITE);

		dotPaint = new Paint();
		dotPaint.setAntiAlias(true);
		dotPaint.setStyle(Paint.Style.FILL);
		dotPaint.setTextAlign(Align.CENTER);
		dotPaint.setColor(Color.rgb(170, 172, 205));

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

		bitmap_horiLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_horizontalline);
		bitmap_vertLine = BitmapFactory.decodeResource(getResources(),
				R.mipmap.history_chart_iv_verticalline);
	}

	Type type;

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	int itemDataWidth;
	int dotRadius;
	private final int TRUE = 1;// 在柱状图上显示数字
	float[][] xy = new float[7][2];// 设置点击事件，显示哪一条柱状的信息
	float[][] xyCircle = new float[7][2];// 记录小圆点（连线需要的坐标）的坐标
	private int[] text = new int[7];// 设置点击事件，显示哪一条柱状的信息

	// 根据传入的类型和数据重画页面
	public void init(Type type, Object object) {
		this.type = type;
		this.object = object;
		xy = new float[7][2];
		xyCircle = new float[7][2];
		text = new int[7];
		mode = 1;
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	// 根据传入的类型和数据重画页面
	public void init(Type type, Object object, int blankMode) {
		this.type = type;
		this.object = object;
		xy = new float[7][2];
		text = new int[7];
		this.mode = blankMode;
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}

	// 根据传入的类型和数据重画页面
	public void reDrawByType(Type type, Object object) {
		this.type = type;
		this.object = object;
		if (Looper.getMainLooper() == Looper.myLooper()) {
			invalidate();
		} else {
			postInvalidate();
		}
	}
	void drawBackground(Canvas canvas){
		int width = getWidth();
		int height = getHeight();
		Paint paintBg=new Paint();
		paintBg.setAntiAlias(true);
		paintBg.setColor(getResources().getColor(R.color.bg_chart));
		paintBg.setStyle(Paint.Style.FILL);
		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();
		rect=new Rect();
		rect.left=0;
		rect.top=0;
		rect.right=getWidth();
		rect.bottom=height- dataTextHeight * 2;
		canvas.drawRect(rect, paintBg);
		
	
		
	}
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
//		drawBackground(canvas);
		if (mode == blankMode) {
			drawBlankView(canvas);
		} else if (type == Type.tizhong) {
			drawTiZhong(canvas);
		} else if (type == Type.tizhizhishu) {
			drawTiZhiBmi(canvas);
		} else if (type == Type.shentizhifanglv) {
			drawZhiFanglv(canvas);
		} else if (type == Type.neizangzhifangshuiping) {
			drawNeiZangZhiFanglv(canvas);
		} else if (type == Type.fubufeipanglv) {
			drawPeriodFubufeipanglvData(canvas);
		} else if (type == Type.jiroulv) {
			drawJiroulv(canvas);
		} else if (type == Type.shentishuifen) {
			drawShuiFen(canvas);
		} else if (type == Type.danbaizhizhiliangbi) {
			drawDanbaizhiData(canvas);
		}

	}

	float rate = (float) 1.4;
	int upOrDownPadding = 7;
	/*
	 * 绘制的模式，体质中：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低 蛋白质：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低
	 * 腹部肥胖率：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低 水分：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低
	 * 体质指数:(-1:较轻;0:正常;1:适中;2:过重;3:肥胖;4:非常胖)
	 */
	int mode = 0;
	String suffix_kg = "kg";
	String suffix_100 = "%";
	String[] dataStringArray = new String[7];

	private void drawBlankView(Canvas canvas) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);

		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		SimpleDateFormat format = new SimpleDateFormat("MM.dd");
		String[] days = new String[] { format.format(Utils.dateAdd(-6)),
				format.format(Utils.dateAdd(-5)),
				format.format(Utils.dateAdd(-4)),
				format.format(Utils.dateAdd(-3)),
				format.format(Utils.dateAdd(-2)),
				format.format(Utils.dateAdd(-1)),
				"今天" };
		
		for (int k = 0; k < days.length; k++) {
			canvas.drawText(days[k], (k + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
		}

		if (type == Type.tizhong) {
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 2;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 2 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// String value = String.valueOf(Math.abs(tizhongStandard
			// .getNormalValue())) + suffix_kg;
//			String value = "56.0" + suffix_kg;
			String value = "最佳";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.tizhizhishu) {
			// bmi适中上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中上限数值
			String value = String.valueOf("25.0");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// bmi适中下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中下限数值
			value = String.valueOf("20.0");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// bmi适中描述

			value = bodyStateBMI[2];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.shentizhifanglv) {
			// 脂肪率标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 脂肪率标准上限数值
			String value = String.valueOf("20.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 脂肪率标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 脂肪率标准下限数值
			value = String.valueOf("10.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 脂肪率标准描述

			value = bodyStateZhiFangLv[1];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);

		} else if (type == Type.neizangzhifangshuiping) {

			// 均衡型上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 均衡型上限数值
			String value = String.valueOf("8");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 均衡型下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 均衡型下限数值
			value = String.valueOf("5");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 均衡型描述

			value = "均衡型";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.fubufeipanglv) {
			// bmi适中上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中上限数值
			String value = String.valueOf("90.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// bmi适中下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中下限数值
			value = String.valueOf("75.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// bmi适中描述

			value = "标准";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.jiroulv) {
			// 肌肉率标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 肌肉率标准上限数值
			String value = String.valueOf("34.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 肌肉率标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 肌肉率标准下限数值
			value = String.valueOf("31.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 肌肉率标准描述

			value = bodyStateZhiFangLv[1];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.shentishuifen) {
			// 水分正常上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 水分正常上限数值
			String value = String.valueOf("67.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 水分正常下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 水分正常下限数值
			value = String.valueOf("53.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 水分正常描述

			value = "正常";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else if (type == Type.danbaizhizhiliangbi) {

			// 蛋白质标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 蛋白质标准上限数值
			String value = String.valueOf("18.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 蛋白质标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 蛋白质标准下限数值
			value = String.valueOf("16.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 蛋白质标准描述

			value = "标准";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		}
	}

	// 绘制脂肪率视图
	private void drawJiroulv(Canvas canvas) {
		PeriodJiroulvData periodJiroulvData = (PeriodJiroulvData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodJiroulvData
				.getDataMap();
		JiroulvStandard jiroulvStandard = periodJiroulvData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double[] floors = new double[2];
		floors[0] = jiroulvStandard.getStandardFloor();
		floors[1] = jiroulvStandard.getExcellentFloor();

		double[] values = new double[7];

		// double maxValue = dataMap.get(periodJiroulvData.getMaxDate());
		// double minValue = dataMap.get(periodJiroulvData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int k = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
					values[k++] = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
					dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xyCircle = new float[length][2];
		// 绘制的模式，(-1:偏低;0:标准;1:优秀
		int[] modes = new int[] { 0, 1, 2, 3 };
		Map<Integer, String> modeMap = new HashMap<Integer, String>();

		for (i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				for (int j = 0; j < 2; j++) {
					if (j == 0) {
						if (values[i] < floors[j])
							modeMap.put(modes[j], "");
					} else if (j == 1) {

						if (values[i] >= floors[j]){
							modeMap.put(modes[j + 1], "");
						}else if(values[i] >= floors[j-1] && values[i] < floors[j]){
							modeMap.put(modes[j], "");
						}
					} else if (j > 0 && j < 1) {
						if (values[i] >= floors[j - 1] && values[i] < floors[j])
							modeMap.put(modes[j], "");
					}
				}
			}
		}
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		}
		drawJiroulvByMode(canvas, maxValue, minValue, jiroulvStandard, dataMap,
				modeMap);
	}

	// 绘制内脏脂肪率视图
	private void drawNeiZangZhiFanglv(Canvas canvas) {
		PeriodNeizangzhifangData periodNeizangzhifangData = (PeriodNeizangzhifangData) object;
		LinkedHashMap<String, Integer> dataMap = (LinkedHashMap<String, Integer>) periodNeizangzhifangData
				.getDataMap();
		NeizangzhifangStandard neizangzhifangStandard = periodNeizangzhifangData
				.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		int[] floors = new int[4];
		floors[0] = neizangzhifangStandard.getBalancedFloor();
		floors[1] = neizangzhifangStandard.getWarningFloor();
		floors[2] = neizangzhifangStandard.getFatFloor();
		floors[3] = neizangzhifangStandard.getVeryFatFloor();

		int[] values = new int[7];

		// int maxValue = dataMap.get(periodNeizangzhifangData.getMaxDate());
		// int minValue = dataMap.get(periodNeizangzhifangData.getMinDate());
		int maxValue = 0;
		int minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int k = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
					values[k++] = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xyCircle = new float[length][2];
		// 绘制的模式，(-1:皮下型;0:均衡型;1:警戒;2:肥胖;3:非常肥胖)
		int[] modes = new int[] { 0, 1, 2, 3, 4 };
		Map<Integer, String> modeMap = new HashMap<Integer, String>();

		for (i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				for (int j = 0; j < 4; j++) {
					if (j == 0) {
						if (values[i] < floors[j])
							modeMap.put(modes[j], "");
					}
//					else if (j == 3) {
//						if (values[i] >= floors[j])
//							modeMap.put(modes[j + 1], "");
//					} 
					else if (j > 0 && j <= 3) {
						if (values[i] >= floors[j-1] && values[i] < floors[j])
							modeMap.put(modes[j], "");
						else if (j == 3&&values[i] >= floors[j])
							modeMap.put(modes[j + 1], "");
					}

				}
			}
		}
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		}
		drawNeiZangZhiFanglvByMode(canvas, maxValue, minValue,
				neizangzhifangStandard, dataMap, modeMap);
	}

	// 绘制脂肪率视图
	private void drawZhiFanglv(Canvas canvas) {
		PeriodZhifanglvData periodZhifanglvData = (PeriodZhifanglvData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodZhifanglvData
				.getDataMap();
		ZhifanglvStandard zhifanglvStandard = periodZhifanglvData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double[] floors = new double[3];
		floors[0] = zhifanglvStandard.getStandardFloor();
		floors[1] = zhifanglvStandard.getMildFatFloor();
		floors[2] = zhifanglvStandard.getFatFloor();

		double[] values = new double[7];

		// double maxValue = dataMap.get(periodZhifanglvData.getMaxDate());
		// double minValue = dataMap.get(periodZhifanglvData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int k = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
					values[k++] = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
					dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		xyCircle = new float[length][2];

		// 绘制的模式，(-1:瘦;0:标准;1:轻度肥胖;2:肥胖
		int[] modes = new int[] { 0, 1, 2, 3 };
		Map<Integer, String> modeMap = new HashMap<Integer, String>();

		for (i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				for (int j = 0; j < 3; j++) {
					if (j == 0) {
						if (values[i] < floors[j])
							modeMap.put(modes[j], "");
					}
//					else if (j == 2) {
//						if (values[i] >= floors[j])
//							modeMap.put(modes[j + 1], "");
//					} 
					else if (j > 0 && j <= 2) {
						if (values[i] >= floors[j - 1] && values[i] < floors[j])
							modeMap.put(modes[j], "");
						else if (j == 2&&values[i] >= floors[j])
							modeMap.put(modes[j + 1], "");
					}
				}
			}
		}
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		}
		drawZhiFanglvByMode(canvas, maxValue, minValue, zhifanglvStandard,
				dataMap, modeMap);
	}

	// 绘制体重指数视图
	private void drawTiZhiBmi(Canvas canvas) {
		PeriodBmiData periodBmiData = (PeriodBmiData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodBmiData
				.getDataMap();
		BmiStandard bmiStandard = periodBmiData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double[] floors = new double[4];
		floors[0] = bmiStandard.getNormalFloor();
		floors[1] = bmiStandard.getHeavierFloor();
		floors[2] = bmiStandard.getFatFloor();
		floors[3] = bmiStandard.getVeryFatFloor();

		double[] values = new double[7];

		// double maxValue = dataMap.get(periodBmiData.getMaxDate());
		// double minValue = dataMap.get(periodBmiData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int k = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		int length = 0;
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
					values[k++] = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xyCircle = new float[length][2];

		// 绘制的模式，体质中：-1:较轻;0:正常;1:过重;2:肥胖;3:非常胖
		int[] modes = new int[] { 0, 1, 2, 3, 4 };
		Map<Integer, String> modeMap = new HashMap<Integer, String>();

		for (i = 0; i < values.length; i++) {
			if (values[i] > 0) {
				for (int j = 0; j < 4; j++) {
					if (j == 0) {
						if (values[i] < floors[j])
							modeMap.put(modes[j], "");
					}
//					else if (j == 3) {
//						if (values[i] >= floors[j])
//							modeMap.put(modes[j + 1], "");
//					}
					else if (j > 0 && j <= 3) {
						if (values[i] >= floors[j - 1] && values[i] < floors[j])
							modeMap.put(modes[j], "");
						else if (j == 3&&values[i] >= floors[j]) 
							modeMap.put(modes[j + 1], "");
					}
				}
			}
		}
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		}
		drawTiZhiBmiByMode(canvas, maxValue, minValue, bmiStandard, dataMap,
				modeMap);
	}

	// 绘制体重视图
	private void drawTiZhong(Canvas canvas) {
		PeriodTizhongData periodTizhongData = (PeriodTizhongData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodTizhongData
				.getDataMap();

		TizhongStandard tizhongStandard = periodTizhongData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double normalFloor = tizhongStandard.getNormalFloor();
		double normalValue = tizhongStandard.getNormalValue();
		double normalCeil = tizhongStandard.getNormalCeil();

		// double maxValue = dataMap.get(periodTizhongData.getMaxDate());
		// double minValue = dataMap.get(periodTizhongData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");

		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		xyCircle = new float[length][2];

		// 绘制的模式，体质中：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低,-100为空
		// 某几天超出上限，另外几天正常
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		} else if (maxValue <= normalCeil && minValue >= normalFloor) {
			mode = 0;
		} else if (maxValue > normalCeil && minValue < normalFloor) {
			mode = -11;
		} else if (maxValue > normalCeil) {
			mode = 1;
		} else if (minValue < normalFloor) {
			mode = -1;
		}

		drawTiZhongByMode(canvas, maxValue, minValue, tizhongStandard, dataMap);
	}

	// 绘制腹部配胖率视图
	private void drawPeriodFubufeipanglvData(Canvas canvas) {
		PeriodFubufeipanglvData periodFubufeipanglvData = (PeriodFubufeipanglvData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodFubufeipanglvData
				.getDataMap();
		FubufeipanglvStandard fubufeipanglvStandard = periodFubufeipanglvData
				.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double normalFloor = fubufeipanglvStandard.getStandardFloor();
		double normalCeil = fubufeipanglvStandard.getStandardCeil();

		// double maxValue = dataMap.get(periodFubufeipanglvData.getMaxDate());
		// double minValue = dataMap.get(periodFubufeipanglvData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		xyCircle = new float[length][2];
		// 绘制的模式，体质中：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低
		// 某几天超出上限，另外几天正常
		if (maxValue <= normalCeil && minValue >= normalFloor) {
			mode = 0;
		} else if (maxValue > normalCeil && minValue < normalFloor) {
			mode = -11;
		} else if (maxValue > normalCeil) {
			mode = 1;
		} else if (minValue < normalFloor) {
			mode = -1;
		}

		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		}
		drawPeriodFubufeipanglvDataByMode(canvas, maxValue, minValue,
				fubufeipanglvStandard, dataMap);
	}

	// 绘制蛋白质视图
	private void drawDanbaizhiData(Canvas canvas) {
		PeriodDanbaizhiData periodDanbaizhiData = (PeriodDanbaizhiData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodDanbaizhiData
				.getDataMap();
		DanbaizhiStandard danbaizhiStandard = periodDanbaizhiData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double normalFloor = danbaizhiStandard.getStandardFloor();
		double normalCeil = danbaizhiStandard.getStandardCeil();

		// double maxValue = dataMap.get(periodDanbaizhiData.getMaxDate());
		// double minValue = dataMap.get(periodDanbaizhiData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xyCircle = new float[length][2];
		// 绘制的模式，体质中：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低
		// 某几天超出上限，另外几天正常
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		} else if (maxValue <= normalCeil && minValue >= normalFloor) {
			mode = 0;
		} else if (maxValue > normalCeil && minValue < normalFloor) {
			mode = -11;
		} else if (maxValue > normalCeil) {
			mode = 1;
		} else if (minValue < normalFloor) {
			mode = -1;
		}
		drawDanbaizhiDataByMode(canvas, maxValue, minValue, danbaizhiStandard,
				dataMap);
	}

	// 绘制身体水分视图
	private void drawShuiFen(Canvas canvas) {
		PeriodShuifenData periodShuifenData = (PeriodShuifenData) object;
		LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodShuifenData
				.getDataMap();
		ShuifenStandard shuifenStandard = periodShuifenData.getStandard();
		int width = getWidth();
		int height = getHeight();
		// 计算最大值和最小值
		double normalFloor = shuifenStandard.getNormalFloor();
		double normalCeil = shuifenStandard.getNormalCeil();

		// double maxValue = dataMap.get(periodShuifenData.getMaxDate());
		// double minValue = dataMap.get(periodShuifenData.getMinDate());
		double maxValue = 0;
		double minValue = 1000000;

		Set<String> keySet = dataMap.keySet();
		int i = 0;
		int length = 0;
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM.dd");
		for (String key : keySet) {
			try {
				if (dataMap.get(key) != 0) {
					length++;
					if (maxValue <= Math.abs(dataMap.get(key)))
						maxValue = Math.abs(dataMap.get(key));
					if (minValue >= Math.abs(dataMap.get(key)))
						minValue = Math.abs(dataMap.get(key));
				}
				if(format1.format(new Date()).equals(key))
					dataStringArray[i++] = "今天";
				else
				dataStringArray[i++] = format2.format(format1.parse(key));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		xyCircle = new float[length][2];
		// 绘制的模式，体质中：-1为偏低，0为标准，1位偏高，-11为既偏高又偏低
		// 某几天超出上限，另外几天正常
		if (maxValue == 0 && minValue == 1000000) {
			mode = blankMode;
		} else if (maxValue <= normalCeil && minValue >= normalFloor) {
			mode = 0;
		} else if (maxValue > normalCeil && minValue < normalFloor) {
			mode = -11;
		} else if (maxValue > normalCeil) {
			mode = 1;
		} else if (minValue < normalFloor) {
			mode = -1;
		}
		drawShuifenByMode(canvas, maxValue, minValue, shuifenStandard, dataMap);
	}

	// 内脏脂肪率
	private void drawNeiZangZhiFanglvByMode(Canvas canvas, int maxValue,
			int minValue, NeizangzhifangStandard neizangzhifangStandard,
			LinkedHashMap<String, Integer> dataMap, Map<Integer, String> modeMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));

		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		for (String key : set) {
			int value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value));
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
//				value =Math.abs(value);
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;
			}
			
			
			i++;
		}
		if (mode == blankMode) {
			// 均衡型上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 均衡型上限数值
			String value = String.valueOf("8");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 均衡型下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 均衡型下限数值
			value = String.valueOf("5");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 均衡型描述

			value = "均衡型";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			Set<Integer> modeSet = modeMap.keySet();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer key : modeSet) {
				list.add(key);
			}
			Collections.sort(list, new Comparator() {
				public int compare(Object o1, Object o2) {
					boolean bigTosmall = true;
					int i1 = ((Integer) o1).intValue();
					int i2 = ((Integer) o2).intValue();
					if (i1 > i2) {
						if (bigTosmall)
							return -1;
						else
							return 1;
					} else {
						if (bigTosmall)
							return 1;
						else
							return -1;
					}
				}
			});
			// int[] modes=new int[modeSet.size()];
			// i=0;
			// i++;

			int modeCount = modeSet.size();
			for (i = 0; i < list.size(); i++) {
				value = bodystateneizangzhifang[list.get(i)];

				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3), startY
						+ (endY - startY) * (i * 2 + 1) / (2 * list.size())
						+ rect.height() / 2 - dp2px(2), textPaint);
				if (i != list.size() - 1) {// 画线
					Rect bitmapRect = new Rect();
					bitmapRect.left = 0;
					bitmapRect.top = startY + (endY - startY) * (i + 1)
							/ list.size();
					bitmapRect.right = width;
					bitmapRect.bottom = startY + (endY - startY) * (i + 1)
							/ list.size() + 1;
					canvas.drawBitmap(bitmap_horiLine, null, bitmapRect,
							bimapPaint);
				}
			}
		}
	}

	// 肌肉率
	private void drawJiroulvByMode(Canvas canvas, double maxValue,
			double minValue, JiroulvStandard jiroulvStandard,
			LinkedHashMap<String, Double> dataMap, Map<Integer, String> modeMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));

		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_100;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;

			}
			i++;
		}
		if (mode == blankMode) {
			// 肌肉率标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 肌肉率标准上限数值
			String value = String.valueOf("34.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 肌肉率标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 肌肉率标准下限数值
			value = String.valueOf("31.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 肌肉率标准描述

			value = bodyStateZhiFangLv[1];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			Set<Integer> modeSet = modeMap.keySet();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer key : modeSet) {
				list.add(key);
			}
			Collections.sort(list, new Comparator() {
				public int compare(Object o1, Object o2) {
					boolean bigTosmall = true;
					int i1 = ((Integer) o1).intValue();
					int i2 = ((Integer) o2).intValue();
					if (i1 > i2) {
						if (bigTosmall)
							return -1;
						else
							return 1;
					} else {
						if (bigTosmall)
							return 1;
						else
							return -1;
					}
				}
			});
			// int[] modes=new int[modeSet.size()];
			// i=0;
			// i++;

			int modeCount = modeSet.size();
			for (i = 0; i < list.size(); i++) {
				value = bodystateJiroulvlv[list.get(i)];

				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3), startY
						+ (endY - startY) * (i * 2 + 1) / (2 * list.size())
						+ rect.height() / 2 - dp2px(2), textPaint);
				if (i != list.size() - 1) {// 画线
					Rect bitmapRect = new Rect();
					bitmapRect.left = 0;
					bitmapRect.top = startY + (endY - startY) * (i + 1)
							/ list.size();
					bitmapRect.right = width;
					bitmapRect.bottom = startY + (endY - startY) * (i + 1)
							/ list.size() + 1;
					canvas.drawBitmap(bitmap_horiLine, null, bitmapRect,
							bimapPaint);
				}
			}
		}
	}

	// 脂肪率
	private void drawZhiFanglvByMode(Canvas canvas, double maxValue,
			double minValue, ZhifanglvStandard zhifanglvStandard,
			LinkedHashMap<String, Double> dataMap, Map<Integer, String> modeMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_100;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;

			}
			i++;
		}
		if (mode == blankMode) {
			// 脂肪率标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 脂肪率标准上限数值
			String value = String.valueOf("20.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 脂肪率标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 脂肪率标准下限数值
			value = String.valueOf("10.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 脂肪率标准描述

			value = bodyStateZhiFangLv[1];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			Set<Integer> modeSet = modeMap.keySet();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer key : modeSet) {
				list.add(key);
			}
			Collections.sort(list, new Comparator() {
				public int compare(Object o1, Object o2) {
					boolean bigTosmall = true;
					int i1 = ((Integer) o1).intValue();
					int i2 = ((Integer) o2).intValue();
					if (i1 > i2) {
						if (bigTosmall)
							return -1;
						else
							return 1;
					} else {
						if (bigTosmall)
							return 1;
						else
							return -1;
					}
				}
			});
			// int[] modes=new int[modeSet.size()];
			// i=0;
			// i++;

			int modeCount = modeSet.size();
			for (i = 0; i < list.size(); i++) {
				value = bodyStateZhiFangLv[list.get(i)];

				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3), startY
						+ (endY - startY) * (i * 2 + 1) / (2 * list.size())
						+ rect.height() / 2 - dp2px(2), textPaint);
				if (i != list.size() - 1) {// 画线
					Rect bitmapRect = new Rect();
					bitmapRect.left = 0;
					bitmapRect.top = startY + (endY - startY) * (i + 1)
							/ list.size();
					bitmapRect.right = width;
					bitmapRect.bottom = startY + (endY - startY) * (i + 1)
							/ list.size() + 1;
					canvas.drawBitmap(bitmap_horiLine, null, bitmapRect,
							bimapPaint);
				}
			}

		}

	}

	// 体质指数
	private void drawTiZhiBmiByMode(Canvas canvas, double maxValue,
			double minValue, BmiStandard bmiStandard,
			LinkedHashMap<String, Double> dataMap, Map<Integer, String> modeMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		int j = 0;
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value));
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}

				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;

			}

			i++;
		}
		if (mode == blankMode) {
			// bmi适中上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中上限数值
			String value = String.valueOf("25.0");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// bmi适中下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中下限数值
			value = String.valueOf("20.0");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// bmi适中描述

			value = bodyStateBMI[2];
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			Set<Integer> modeSet = modeMap.keySet();
			ArrayList<Integer> list = new ArrayList<Integer>();
			for (Integer key : modeSet) {
				list.add(key);
			}
			Collections.sort(list, new Comparator() {
				public int compare(Object o1, Object o2) {
					boolean bigTosmall = true;
					int i1 = ((Integer) o1).intValue();
					int i2 = ((Integer) o2).intValue();
					if (i1 > i2) {
						if (bigTosmall)
							return -1;
						else
							return 1;
					} else {
						if (bigTosmall)
							return 1;
						else
							return -1;
					}
				}
			});
			// int[] modes=new int[modeSet.size()];
			// i=0;
			// i++;

			int modeCount = modeSet.size();
			for (i = 0; i < list.size(); i++) {
				value = bodyStateBMI[list.get(i)];

				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3), startY
						+ (endY - startY) * (i * 2 + 1) / (2 * list.size())
						+ rect.height() / 2 - dp2px(2), textPaint);
				if (i != list.size() - 1) {// 画线
					Rect bitmapRect = new Rect();
					bitmapRect.left = 0;
					bitmapRect.top = startY + (endY - startY) * (i + 1)
							/ list.size();
					bitmapRect.right = width;
					bitmapRect.bottom = startY + (endY - startY) * (i + 1)
							/ list.size() + 1;
					canvas.drawBitmap(bitmap_horiLine, null, bitmapRect,
							bimapPaint);
				}
			}
		}
	}

	// 腹部肥胖率
	private void drawPeriodFubufeipanglvDataByMode(Canvas canvas,
			double maxValue, double minValue,
			FubufeipanglvStandard fubufeipanglvStandard,
			LinkedHashMap<String, Double> dataMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_100;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;

			}
			i++;
		}
		if (mode == blankMode) {
			// bmi适中上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中上限数值
			String value = String.valueOf("90.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// bmi适中下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// bmi适中下限数值
			value = String.valueOf("75.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// bmi适中描述

			value = "标准";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			if (mode == 1 || mode == -1) {
				// if (mode == 1)
				// value = "偏高";
				// else if (mode == -1)
				// value = "偏低";
				value = bodystateFubufeipanglv[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);
			} else if (mode == 0) {

				// value =
				// String.valueOf(Math.abs(tizhongStandard.getNormalValue()))
				// + suffix_kg;
				// value = "正常";
				value = bodystateFubufeipanglv[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2, textPaint);

				Rect bitmapRect = new Rect();
				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			} else {
				// value = "偏高";
				value = bodystateFubufeipanglv[2];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) / 4 + startY + rect.height() / 2,
						textPaint);
				// value = "偏低";
				value = bodystateFubufeipanglv[0];
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) * 3 / 4 + startY + rect.height() / 2,
						textPaint);
				Rect bitmapRect = new Rect();

				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);

			}
		}
	}

	// 蛋白质质量
	private void drawDanbaizhiDataByMode(Canvas canvas, double maxValue,
			double minValue, DanbaizhiStandard danbaizhiStandard,
			LinkedHashMap<String, Double> dataMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);
		String showValue = "";
		boolean[] booleans = new boolean[7];
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_100;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);

			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}
			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;
			}

			i++;
		}
		if (mode == blankMode) {
			// 蛋白质标准上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 蛋白质标准上限数值
			String value = String.valueOf("18.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 蛋白质标准下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 蛋白质标准下限数值
			value = String.valueOf("16.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 蛋白质标准描述

			value = "标准";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			if (mode == 1 || mode == -1) {
				// if (mode == 1)
				// value = "偏高";
				// else if (mode == -1)
				// value = "偏低";
				value = bodystateDanbaizhi[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);
			} else if (mode == 0) {

				// value =
				// String.valueOf(Math.abs(tizhongStandard.getNormalValue()))
				// + suffix_kg;
				// value = "正常";
				value = bodystateDanbaizhi[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);

				Rect bitmapRect = new Rect();
				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			} else {
				// value = "偏高";
				value = bodystateDanbaizhi[2];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) / 4 + startY + rect.height() / 2,
						textPaint);
				// value = "偏低";
				value = bodystateDanbaizhi[0];
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) * 3 / 4 + startY + rect.height() / 2,
						textPaint);
				Rect bitmapRect = new Rect();

				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);

			}
		}
	}

	private void drawShuifenByMode(Canvas canvas, double maxValue,
			double minValue, ShuifenStandard shuifenStandard,
			LinkedHashMap<String, Double> dataMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));
		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		int j = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);

		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_100;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}

			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = (startY + endY) / 2;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
//				float y = endY - dp2px(10) - ((float) (value - minValue))
//						/ eachPixKg;
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;
			}

			i++;
		}
		if (mode == blankMode) {
			// 水分正常上限
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 水分正常上限数值
			String value = String.valueOf("67.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 4 + rect.height() / 2, textPaint);
			// 水分正常下限
			bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) * 3 / 4;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) * 3 / 4 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// 水分正常下限数值
			value = String.valueOf("53.0%");
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					* 3 / 4 + rect.height() / 2, textPaint);
			// 水分正常描述

			value = "正常";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {
			// 绘制两点之间的连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}
			bimapPaint.setColor(colorCircleLine);
			// 绘制左边的描述
			String value = "";

			if (mode == 1 || mode == -1) {
				// if (mode == 1)
				// value = "偏高";
				// else if (mode == -1)
				// value = "偏低";
				value = bodystateShuifenData[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);
			} else if (mode == 0) {

				// value =
				// String.valueOf(Math.abs(tizhongStandard.getNormalValue()))
				// + suffix_kg;
				// value = "正常";
				value = bodystateShuifenData[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);

				Rect bitmapRect = new Rect();
				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			} else {
				// value = "偏高";
				value = bodystateShuifenData[2];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) / 4 + startY + rect.height() / 2,
						textPaint);
				// value = "偏低";
				value = bodystateShuifenData[0];
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) * 3 / 4 + startY + rect.height() / 2,
						textPaint);
				Rect bitmapRect = new Rect();

				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);

			}
		}
	}

	private void drawTiZhongByMode(Canvas canvas, double maxValue,
			double minValue, TizhongStandard tizhongStandard,
			LinkedHashMap<String, Double> dataMap) {
		int width = getWidth();
		int height = getHeight();

		Rect rect = new Rect();
		datePaint.getTextBounds("00.00", 0, 5, rect);
		int dataTextHeight = rect.height();

		int startY = dp2px(upOrDownPadding);
		int endY = height - dataTextHeight * 2 - dp2px(upOrDownPadding);

//		float eachPixKg = (float) (rate * ((float) (maxValue - minValue)) / (endY - startY));
		float eachPixKg = (float) ( ((float) (maxValue - minValue)) / (endY - startY-(rate-1)*(endY-startY)));

		// 绘制最上面和最下面的两条白直线
		bimapPaint.setStrokeWidth(1);
		canvas.drawLine(0, 0, width, 0, bimapPaint);
		canvas.drawLine(0, height - dataTextHeight * 2, width, height
				- dataTextHeight * 2, bimapPaint);
		// 绘制上下两条虚线
		rect = new Rect();
		rect.left = 0;
		rect.top = dp2px(upOrDownPadding);
		rect.right = width;
		rect.bottom = dp2px(upOrDownPadding) + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		rect = new Rect();
		rect.left = 0;
		rect.top = endY;
		rect.right = width;
		rect.bottom = endY + 1;
		canvas.drawBitmap(bitmap_horiLine, null, rect, bimapPaint);
		// 绘制最下面的日期
		itemDataWidth = width / 8;// 多出的一个用于两边等分间距
		int i = 0;
		Set<String> set = dataMap.keySet();
		dotRadius = dp2px(3);

		String showValue = "";
		boolean[] booleans = new boolean[xyCircle.length];
		int j = 0;
		for (String key : set) {
			double value = dataMap.get(key);
			if (text[i] == TRUE) {
				showValue = String.valueOf(Math.abs(value)) + suffix_kg;
				datePaint.setColor(colorCircleLine);
			} else {
				datePaint.setColor(colorDate);
			}
			canvas.drawText(dataStringArray[i], (i + 1) * itemDataWidth,
					(float) (height - dataTextHeight / 2), datePaint);
			datePaint.setColor(colorDate);
			boolean isBlank = false;
			if (value < 0) {
				value =Math.abs(value);
				isBlank = false;
			}

			if (maxValue == minValue) {
				xy[i][0] = (i + 1) * itemDataWidth;
				if(Math.abs(tizhongStandard.getNormalValue())>maxValue)
					xy[i][1] = (startY + endY) / 2+dp2px(20);
				else{
					xy[i][1] = (startY + endY) / 2-dp2px(20);
				}
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = xy[i][1];
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
			} else {
				float y = endY-(rate-1)*(endY-startY)/2 - ((float) (value - minValue))
						/ eachPixKg;
				if (value != 0) {
					xyCircle[j][0] = (i + 1) * itemDataWidth;
					xyCircle[j][1] = y;
					booleans[j] = isBlank;
					if (text[i] == TRUE) {
						booleans[j] = true;
					}
					j++;
				}
				xy[i][0] = (i + 1) * itemDataWidth;
				xy[i][1] = y;
			}

			i++;
		}
		if (mode == blankMode) {
			Rect bitmapRect = new Rect();
			bitmapRect.left = 0;
			bitmapRect.top = (startY + endY) / 2;
			bitmapRect.right = width;
			bitmapRect.bottom = (startY + endY) / 2 + 1;
			canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);
			// String value = String.valueOf(Math.abs(tizhongStandard
			// .getNormalValue())) + suffix_kg;
//			String value = "56.0" + suffix_kg;
			String value = "最佳";
			rect = new Rect();
			textPaint.getTextBounds(value, 0, value.length(), rect);
			canvas.drawText(value, rect.width() / 2 + dp2px(3), (startY + endY)
					/ 2 + rect.height() / 2 - dp2px(2), textPaint);
		} else {

			// 绘制连线
			bimapPaint.setStrokeWidth(dp2px(1));
			for (i = 0; i < xyCircle.length - 1; i++) {
				bimapPaint.setColor(colorCircleLine);
				canvas.drawLine(xyCircle[i][0], xyCircle[i][1],
						xyCircle[i + 1][0], xyCircle[i + 1][1], bimapPaint);
			}
			// 绘制点
			for (i = 0; i < xyCircle.length; i++) {
				if (booleans[i])
					bimapPaint.setColor(colorCircleBlankID);
				else
					bimapPaint.setColor(colorCircleID);
				canvas.drawCircle(xyCircle[i][0], xyCircle[i][1], dotRadius,
						bimapPaint);
				bimapPaint.setColor(colorCircleID);

			}
			// 绘制点击顶部的文字
			for (i = 0; i < text.length; i++) {
				if (text[i] == TRUE) {
					bimapPaint.setColor(colorAlert);
					rect = new Rect();
					bimapPaint.getTextBounds(showValue, 0, showValue.length(),
							rect);
					bimapPaint.setTextSize(dp2px(alertTextSize));
					canvas.drawText(showValue, xy[i][0],
							xy[i][1] - rect.height(), bimapPaint);
				}
			}

			bimapPaint.setColor(colorCircleID);
			// 绘制左边的描述
			String value = "";

			if (mode == 1 || mode == -1) {
				// if (mode == 1)
				// value = "偏高";
				// else if (mode == -1)
				// value = "偏低";
				value = bodystateShuifenData[mode + 1];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);
			} else if (mode == 0) {

				Rect bitmapRect = new Rect();
				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);

				value = String.valueOf(Math.abs(tizhongStandard
						.getNormalValue())) + suffix_kg;
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(startY + endY) / 2 + rect.height() / 2 - dp2px(2),
						textPaint);
			} else {
				// value = "偏高";
				value = bodystateShuifenData[2];
				rect = new Rect();
				textPaint.getTextBounds(value, 0, value.length(), rect);
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) / 4 + startY + rect.height() / 2,
						textPaint);
				// value = "偏低";
				value = bodystateShuifenData[0];
				canvas.drawText(value, rect.width() / 2 + dp2px(3),
						(endY - startY) * 3 / 4 + startY + rect.height() / 2,
						textPaint);
				Rect bitmapRect = new Rect();

				bitmapRect.left = 0;
				bitmapRect.top = (startY + endY) / 2;
				bitmapRect.right = width;
				bitmapRect.bottom = (startY + endY) / 2 + 1;
				canvas.drawBitmap(bitmap_horiLine, null, bitmapRect, bimapPaint);

			}
		}
	}

	/**
	 * @see View#measure(int, int)
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

	private int dp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}

	private int sp2px(int value) {
		float v = getContext().getResources().getDisplayMetrics().scaledDensity;
		return (int) (v * value + 0.5f);
	}

	float lastX;

	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = event.getX();
			break;
		case MotionEvent.ACTION_UP:

			if (Math.abs(lastX - event.getX()) < 10) {
				Paint paint = new Paint();
				paint.setStyle(Paint.Style.FILL);
				paint.setTextSize(sp2px(textSize));// 字体大小
				paint.setColor(Color.parseColor("#6DCAEC"));// 字体颜色
				Rect textBounds = new Rect();
				paint.getTextBounds("00", 0, 2, textBounds);
				int x = (int) event.getX();
				int y = (int) event.getY();
				for (int i = 0; i < 7; i++) {
					if (x > xy[i][0] - 3 * dotRadius
							&& x < xy[i][0] + 3 * dotRadius
							&& y > xy[i][1] - 3 * dotRadius
							&& y < xy[i][1] + 3 * dotRadius) {

						text[i] = 1;
						for (int j = 0; j < 7; j++) {
							if (i != j) {
								text[j] = 0;
							}
						}
						if (Looper.getMainLooper() == Looper.myLooper()) {
							invalidate();
						} else {
							postInvalidate();
						}
					}
				}
			} else {

			}
			break;
		case MotionEvent.ACTION_MOVE:
			if (lastX - event.getX() > 20 && lastX != -1) {
				if (onTouchWeekListener != null)
					onTouchWeekListener.onWeekFiller(1);
				lastX = event.getX();
				lastX = -1;
			} else if (event.getX() - lastX > 20 && lastX != -1) {
				if (onTouchWeekListener != null)
					onTouchWeekListener.onWeekFiller(-1);
				lastX = event.getX();
				lastX = -1;
			}
			break;

		default:
			break;
		}

		return true;
	}

	public interface OnTouchWeekListener {
		public void onWeekTouch(Object obj1, Object obj2);

		// -1为showpre，1为shownext
		public void onWeekFiller(int direction);
	}
}
