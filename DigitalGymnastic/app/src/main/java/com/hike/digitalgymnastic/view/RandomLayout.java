package com.hike.digitalgymnastic.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hike.digitalgymnastic.entitiy.DailySportData;
import com.hike.digitalgymnastic.view.SportTypeCheckBox.OnCheckedChangeListener;
import com.hiko.enterprisedigital.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLayout extends RelativeLayout implements
		OnCheckedChangeListener {
	List<SportTypeCheckBox> cbList;
	List<DailySportData> dailySportDataList;
	public List<DailySportData> getDailySportDataList() {
		return dailySportDataList;
	}

	ONSportTypeChangedListener onTypeChangedlistener;
	int screenWidth;
	int screenHeight;
	float density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
	int densityDPI; // 屏幕密度（每寸像素：120/160/240/320）
	float xdpi;
	float ydpi;

	int maxRadio = (int) getResources().getDimensionPixelSize(R.dimen.x89);// 最大圆的直径,
	int normalRadio =(int) getResources().getDimensionPixelSize(R.dimen.x67) ;// 中圆的直径
	int minRaido =(int) getResources().getDimensionPixelSize(R.dimen.x45) ;// 最小圆直径
	int textSize =(int) getResources().getDimensionPixelSize(R.dimen.x14) ;
	int textLittleSize = (int) getResources().getDimensionPixelSize(R.dimen.x9) ;

	public RandomLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RandomLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		init(context);
	}

	public RandomLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		init(context);
	}

	private void init(Context context) {
		Activity ay = (Activity) context;
		// 获取屏幕密度（方法1）
		screenWidth = ay.getWindowManager().getDefaultDisplay().getWidth(); // 屏幕宽（像素，如：480px）
		screenHeight = ay.getWindowManager().getDefaultDisplay().getHeight(); // 屏幕高（像素，如：800p）
		// 获取屏幕密度（方法2）
		DisplayMetrics dm = new DisplayMetrics();
		dm = getResources().getDisplayMetrics();
		density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
		densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
		xdpi = dm.xdpi;
		ydpi = dm.ydpi;

	}

	public void ininData(List<DailySportData> dailySportDataList) {
		this.dailySportDataList = dailySportDataList;
		this.cbList = new ArrayList<SportTypeCheckBox>();
	}
	public void clear(){
		this.dailySportDataList=null;
		this.cbList=null;
		this.removeAllViews();
	}

	public void setOnSportTypeChangedListener(
			ONSportTypeChangedListener onTypeChangedlistener) {
		this.onTypeChangedlistener = onTypeChangedlistener;
	}

	public void buildLayout(Context context) {
		if (dailySportDataList == null)
			return;
		int sportDataTypes = dailySportDataList.size();
		switch (sportDataTypes) {
		case 0:
			break;
		case 1:
			buildOneSportView(context);
			break;
		case 2:
			buildTwoSportView(context);
			break;
		case 3:
			buildThreeSportView(context);
			break;
		case 4:
			buildFourSportView(context);
			break;
		case 5:
			buildFiveSportView(context);
			break;
		case 6:
			buildSixSportView(context);

			break;
		case 7:
			buildSevenSportView(context);
			break;
		default:
			buildSevenSportView(context);
			break;
		}

	}

	private void buildOneSportView(Context context) {
//		CheckBox ib = new CheckBox(context);ib.setFocusable(false);
//		ib.setButtonDrawable(R.mipmap.bg_check_null);
//		ib.setText(dailySportDataList.get(0).getSportName());
//		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
//		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
//		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
//		ib.setGravity(Gravity.CENTER);
//		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//				(int) (maxRadio), (int) (maxRadio));
//		params.addRule(Gravity.CENTER);
//		params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		ib.setLayoutParams(params);
//		ib.setTag(dailySportDataList.get(0));
//		addView(ib);
//		cbList.add(ib);
//		ib.setOnCheckedChangeListener(this);
		
		
		
		LinearLayout content_layout = new LinearLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_IN_PARENT);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setFocusable(false);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (maxRadio), (int) (maxRadio));
		params.gravity = Gravity.CENTER;
		params.rightMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		content_layout.addView(ib);
		cbList.add(ib);
		
		// 将content_layout放入父布局中
				addView(content_layout);
		ib.setOnCheckedChangeListener(this);
	}

	// 生成两种类型的显示布局
	private void buildTwoSportView(Context context) {
		Random random = new Random();
		int mode = random.nextInt(2);// 生成0-1的随机码，显示不同的布局
		if (mode == 0) {
			build20View(context);
		} else if (mode == 1) {
			build21View(context);
		}
	}

	// 生成3个圆的视图
	private void buildThreeSportView(Context context) {
		Random random = new Random();
		int mode = random.nextInt(3);// 生成0-2的随机码，显示不同的布局
		if (mode == 0) {
			build30View(context);
		} else if (mode == 1) {
			build31View(context);
		} else if (mode == 2) {
			build32View(context);
		}
	}

	// 生成4个圆的视图
	private void buildFourSportView(Context context) {
		Random random = new Random();
		int mode = random.nextInt(2);// 生成0-1的随机码，显示不同的布局
		if (mode == 0) {
			build41View(context);
		} else if (mode == 1) {
			build41View(context);
		}
	}

	// 生成5个圆的视图
	private void buildFiveSportView(Context context) {
		build50View(context);
	}

	private void buildSixSportView(Context context) {
		build60View(context);
	}

	private void buildSevenSportView(Context context) {
		build70View(context);
	}

	// 以下生成各种混乱的视图
	private void build20View(Context context) {
		// 两个圆放到一个LinearLayout中
		LinearLayout content_layout = new LinearLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		content_layout_params.setLayoutDirection(LinearLayout.HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (maxRadio), (int) (maxRadio));
		params.gravity = Gravity.CENTER;
		params.rightMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		content_layout.addView(ib);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.gravity = Gravity.CENTER;
		params.leftMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		content_layout.addView(ib);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build21View(Context context) {
		// 两个圆放到一个LinearLayout中
		LinearLayout content_layout = new LinearLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_IN_PARENT);
//		content_layout_params.setLayoutDirection(LinearLayout.HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (normalRadio), (int) (normalRadio));
		params.gravity = Gravity.CENTER;
		params.rightMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		content_layout.addView(ib);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.gravity = Gravity.CENTER;
		params.leftMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		content_layout.addView(ib);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build30View(Context context) {
		// 三个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (normalRadio), (int) (normalRadio));
		params.topMargin = (int) (normalRadio);
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		setTextSize(ib,dailySportDataList.get(2).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.topMargin = (int) (maxRadio + normalRadio + minRaido) / 2;
		params.leftMargin = (int) (maxRadio);
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build31View(Context context) {
		// 三个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (normalRadio), (int) (normalRadio));
		params.topMargin = (int) (normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (maxRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = (int) (maxRadio);
		params.topMargin = (int) (normalRadio - minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build32View(Context context) {
		// 三个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (normalRadio), (int) (normalRadio));
		// params.rightMargin = 15;
		params.topMargin = (int) (maxRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;

		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = (int) (maxRadio);
		params.topMargin = (int) (normalRadio);
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build40View(Context context) {
		// 四个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (maxRadio), (int) (maxRadio));
		params.topMargin = (int) (maxRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		setTextSize(ib,dailySportDataList.get(1).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.leftMargin = (int) (normalRadio);
		params.topMargin = 15;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第四个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fourth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(3).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (maxRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(3));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build41View(Context context) {
		// 四个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (normalRadio), (int) (normalRadio));
		params.topMargin = (int) (maxRadio + minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		setTextSize(ib,dailySportDataList.get(1).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.leftMargin = (int) (minRaido);
		params.topMargin = (int) (30 * density);
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = (int) (30 * density);
		params.topMargin = (int) (normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第四个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fourth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(3).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		params.leftMargin = 15;
		params.topMargin = (int) (maxRadio + minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(3));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build50View(Context context) {
		// 五个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		setTextSize(ib,dailySportDataList.get(0).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (minRaido), (int) (minRaido));
		params.topMargin = (int) (maxRadio + minRaido + normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		setTextSize(ib,dailySportDataList.get(1).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.leftMargin = (int) (15 * density);
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		// params.leftMargin = (int) (15* density);
		params.topMargin = (int) (maxRadio + 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第四个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fourth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(3).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(3));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第五个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fivth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(4).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = (int) (minRaido + 15 * density);
		params.topMargin = (int) (maxRadio + minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(4));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void build60View(Context context) {
		// 六个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		setTextSize(ib,dailySportDataList.get(0).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (minRaido), (int) (minRaido));
		params.topMargin = (int) (maxRadio + minRaido + normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());
		if (dailySportDataList.get(1).getSportName().length() > 3)
			ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textLittleSize);
		else
			ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.topMargin = (int) (maxRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		// params.leftMargin = (int) (15* density);
		params.topMargin = (int) (maxRadio + 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第四个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fourth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(3).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(3));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第五个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fivth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(4).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = (int) (minRaido + 15 * density);
		params.topMargin = (int) (maxRadio + normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(4));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第六个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.sixth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(5).getSportName());
		setTextSize(ib,dailySportDataList.get(5).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.topMargin = (int) (maxRadio + minRaido + normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(5));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 将content_layout放入父布局中
		addView(content_layout);
	}

	private void setTextSize(SportTypeCheckBox ib,String text){
		if (text.length() >= 3)
			ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textLittleSize);
		else
			ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		
	}
	private void build70View(Context context) {
		// 七个圆放到一个RelativeLayout中
		RelativeLayout content_layout = new RelativeLayout(context);
		RelativeLayout.LayoutParams content_layout_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		content_layout_params.addRule(RelativeLayout.CENTER_HORIZONTAL);
		content_layout.setLayoutParams(content_layout_params);
		// 第一个圆
		LinearLayout ll = new LinearLayout(context);
		RelativeLayout.LayoutParams ll_params = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.first_circle);
		ll.setLayoutParams(ll_params);

		SportTypeCheckBox ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(0).getSportName());
		setTextSize(ib,dailySportDataList.get(0).getSportName());
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
//		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);
		ib.setGravity(Gravity.CENTER);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				(int) (minRaido), (int) (minRaido));
		params.leftMargin = (int) (minRaido) / 2;
		params.topMargin = (int) ((minRaido) / 2+normalRadio+2*density);
//				+ (int) (maxRadio + normalRadio) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(0));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);
		// 第二个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll.setId(R.id.secend_circle);
		ll.setLayoutParams(ll_params);

		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(1).getSportName());

		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(1));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第三个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.first_circle);
		ll.setId(R.id.third_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(2).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (maxRadio),
				(int) (maxRadio));
		// params.leftMargin = (int) (15* density);
		params.topMargin = (int) (maxRadio + 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(2));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第四个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.fourth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(3).getSportName());
		
		setTextSize(ib,dailySportDataList.get(3).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
//		params.topMargin = (int) (normalRadio) / 2;
		params.topMargin = (int) (minRaido) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(3));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第五个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.fourth_circle);
		ll.setId(R.id.fivth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(4).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = (int) (15 * density) / 2;
		params.topMargin = (int) (minRaido - 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(4));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第六个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.third_circle);
		ll.setId(R.id.sixth_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(5).getSportName());
		ib.setTextSize(TypedValue.COMPLEX_UNIT_PX,textSize);
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (normalRadio),
				(int) (normalRadio));
		params.leftMargin = (int) (2 * density);
//		params.topMargin = (int) (maxRadio + normalRadio + 15 * density) / 2;
		params.topMargin = (int) (maxRadio + minRaido + 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(5));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 第七个圆
		ll = new LinearLayout(context);
		ll_params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		ll_params.addRule(RelativeLayout.RIGHT_OF, R.id.sixth_circle);
		ll.setId(R.id.seventh_circle);
		ll.setLayoutParams(ll_params);
		ib = new SportTypeCheckBox(context);
		ib.setText(dailySportDataList.get(6).getSportName());
		setTextSize(ib,dailySportDataList.get(6).getSportName());
		ib.setTextColorID(R.color.time_color_normal,R.color.time_color_normal);;
		ib.setBackGround(R.mipmap.bg_type_nor, R.mipmap.bg_type_pressed);
		ib.setGravity(Gravity.CENTER);
		params = new LinearLayout.LayoutParams((int) (minRaido),
				(int) (minRaido));
		params.leftMargin = (int) (15 * density) / 2;
		params.topMargin = (int) (maxRadio + normalRadio + 15 * density) / 2;
		ib.setLayoutParams(params);
		ib.setTag(dailySportDataList.get(6));
		ll.addView(ib);
		content_layout.addView(ll);
		cbList.add(ib);
		ib.setOnCheckedChangeListener(this);

		// 将content_layout放入父布局中
		addView(content_layout);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthMeasureSpec,
				MeasureSpec.EXACTLY);
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightMeasureSpec,
				MeasureSpec.EXACTLY);

	}

//	@Override
//	public void onCheckedChanged(CompoundButton button, boolean checked) {
//		// TODO Auto-generated method stub
//		if (checked) {
//
//			CheckBox cb = (CheckBox) button;
//			for (CheckBox checkbox : cbList) {
//				if (!checkbox.equals(cb)) {
//					checkbox.setChecked(false);
//					checkbox.setTextColor(getResources().getColor(R.color.time_color_normal));
//				}else{
//					checkbox.setTextColor(getResources().getColor(R.color.white));
//				}
//				
//			}
//			if (onTypeChangedlistener != null) {
//				onTypeChangedlistener.onTypeChanged(cb);
//			}
//		}
//	}

	public interface ONSportTypeChangedListener {
		public void onTypeChanged(SportTypeCheckBox cb);
	}

	@Override
	public void onCheckedChanged(View view, boolean checked) {
		// TODO Auto-generated method stub
		if (checked) {

			SportTypeCheckBox cb = (SportTypeCheckBox) view;
			for (SportTypeCheckBox checkbox : cbList) {
				if (!checkbox.equals(cb)) {
					checkbox.setChecked(false);
					checkbox.setTextColor(getResources().getColor(R.color.time_color_normal));
				}else{
					checkbox.setTextColor(getResources().getColor(R.color.white));
				}
				
			}
			if (onTypeChangedlistener != null) {
				onTypeChangedlistener.onTypeChanged(cb);
			}
		}
	}
}
