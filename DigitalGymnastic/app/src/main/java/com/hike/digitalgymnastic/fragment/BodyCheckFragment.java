package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hike.digitalgymnastic.HistoryBodyActivity;
import com.hike.digitalgymnastic.IndicatorsAlert;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.PersonalInformationActivity;
import com.hike.digitalgymnastic.entitiy.BmiData;
import com.hike.digitalgymnastic.entitiy.BodeStateType;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.HomeBodyData;
import com.hike.digitalgymnastic.entitiy.TizhongData;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.tools.AnimUtil;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.BodyStateView;
import com.hike.digitalgymnastic.view.BodyStateView.OnTitleClickListener;
import com.hike.digitalgymnastic.view.DashCircleProgressWeight;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hike.digitalgymnastic.view.MyViewFlipper;
import com.hike.digitalgymnastic.view.MyViewFlipper.ViewFlipperOnTouchListener;
import com.hike.digitalgymnastic.view.ScrollViewInner;
import com.hiko.enterprisedigital.R;
import com.hiko.enterprisedigital.SocialShareActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 * 体侧首页
 */
public class BodyCheckFragment extends BaseFragment implements
		View.OnClickListener, ViewFlipperOnTouchListener {

	private View view;
	private BaseDao dao;
	@ViewInject(R.id.ll_top)
	LinearLayout ll_top;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.viewFlipper)
	MyViewFlipper viewFlipper;
	@ViewInject(R.id.iv_dialScaleView)
	DashCircleProgressWeight iv_dialScaleView;
	@ViewInject(R.id.tv_standWeight)
	TextView tv_standWeight;

	@ViewInject(R.id.handleView)
	View handleView;
	@ViewInject(R.id.frament_body_bottom)
	View frament_body_bottom;
	@ViewInject(R.id.ll_bodystateunqualified)
	LinearLayout ll_bodystateunqualified;
	@ViewInject(R.id.ll_bodystatequalified)
	LinearLayout ll_bodystatequalified;
	@ViewInject(R.id.frament_body_top)
	View frament_body_top;
//	@ViewInject(R.id.tv_xibaozhongliang)
//	TextView tv_xibaozhongliang;
//	@ViewInject(R.id.tv_kuangwuzhi)
//	TextView tv_kuangwuzhi;

	@ViewInject(R.id.tv_advice)
	TextView tv_advice;
	@ViewInject(R.id.btn_share)
	Button btn_share;
	@ViewInject(R.id.ll_bottom)
	LinearLayout ll_bottom;
	@ViewInject(R.id.rl_handler_blank)
	RelativeLayout rl_handler_blank;
	TextView tv_nodata_alert_blank;

	@ViewInject(R.id.rl_handler)
	RelativeLayout rl_handler;

	ImageView iv_top;
	ImageView iv_top_handle;
	TextView tv_unqualified_alet;
	TextView tv_unqualified_alet_value;
	TextView tv_qualified_alet;
	TextView tv_qualified_alet_value;
//	TextView tv_baseinfo_alert;
//	TextView tv_baseinfo_alert_value;
//	TextView tv_baseinfo_alert_kcal;
//	TextView tv_baseinfo_alert_handle;
//	TextView tv_baseinfo_alert_value_handle;
//	TextView tv_baseinfo_alert_kcal_handle;
	TextView tv_couont_qualified;
	TextView tv_couont_unqualified;
	TextView tv_couont_qualified_handle;
	TextView tv_couont_unqualified_handle;
	TextView tv_hege;
	TextView tv_buhege;
	TextView tv_hege_handle;
	TextView tv_buhege_handle;
	Button iv_body_addweight;
	TextView tv_body_advice;

	protected boolean bool = true;
	protected boolean changetitle = false;
	private String titliname;
	private int bodypage = 1;
	public BodyListener bListener;
	private static final String TAG = "BodyCheckFragment";

	private String suffix = "kg";
	Customer cusomter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		this.view = inflater.inflate(R.layout.activity_main_frament_body,
				container, false);
		ViewUtils.inject(this, view);

		return view;
	}

	MainActivity ma;
	private LinearLayout ll_bodystateunqualified1;
	private LinearLayout ll_bodystatequalified1;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		ma = (MainActivity) activity;
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e(TAG, "onresume----body--33333333333>");
		if (!this.isHidden()) {

//			if (ma.currentPage == 2)// 从本地数据加载睡眠主页
			new Handler().postDelayed(new Runnable(){
				@Override
				public void run() {
					buildViewFromLoacalData();
				}
			},500);
//			if(bListener!=null){
				if (bodypage == 1) {
					if (hbd != null && !TextUtils.isEmpty(hbd.getTime())) {
						titliname = getString(R.string.body_data_title_str) + "\n"
								+ hbd.getTime();
//						bListener.change(titliname, bodypage);
					} else {
						titliname = "体测";
//						bListener.change(titliname, bodypage);
					}
				} else {
					titliname = getString(R.string.body_baogao_title_str);
					if (bListener != null) {
//						bListener.change(titliname, 2);
					}
				}
				title.setVisibility(View.VISIBLE);
				if (titliname.length() > 4) {
					title.setText(getSpan(titliname));
				} else {
					title.setText(titliname);
				}
//			}

		}

	}
	@Override
	public void notifyUpdateTitle(){
		if (!this.isHidden()) {
			if(bListener!=null){
				if (bodypage == 1) {
					if (hbd != null && !TextUtils.isEmpty(hbd.getTime())) {
						titliname = getString(R.string.body_data_title_str) + "\n"
								+ hbd.getTime();
						bListener.change(titliname, bodypage);
					} else {
						titliname = "体测";
						bListener.change(titliname, bodypage);
					}
				} else {
					titliname = getString(R.string.body_baogao_title_str);
					if (bListener != null) {
						bListener.change(titliname, 2);
					}
				}
			}

		}
	}
	private SpannableString getSpan(String str) {
		SpannableString spanString = new SpannableString(str);
		AbsoluteSizeSpan spanTitle = new AbsoluteSizeSpan(dp2px(12));
		AbsoluteSizeSpan spanTime = new AbsoluteSizeSpan(dp2px(9));
		spanString.setSpan(spanTitle, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spanString.setSpan(spanTime, 4, str.length(),
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return spanString;
	}
	private int dp2px(int value) {
		float v = getResources().getDisplayMetrics().density;
		return (int) (v * value + 0.5f);
	}
	@Override
	public void onPause() {
		super.onPause();
		if (dao!=null&&dao.isRunning()) {
			dao.cancel();
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
	}

	public void initData() {
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setImageResource(R.mipmap.history_3x);
		// btn_left.setImageResource(R.mipmap.btn_menu);
		btn_left.setImageResource(R.mipmap.btn_back);
		btn_right.setOnClickListener(this);
		ll_btn_right.setOnClickListener(this);
		ll_btn_left.setOnClickListener(this);
		btn_left.setOnClickListener(this);
		title.setVisibility(View.VISIBLE);
		title.setText("体测");
		cusomter = LocalDataUtils.readCustomer(activity);
		initBitmapUtils();
		iv_dialScaleView.init(Integer.parseInt(cusomter.gender),
				Float.parseFloat(cusomter.weight),
				Integer.parseInt(cusomter.height));
		dao = new BaseDao(this, activity);
		iv_top = (ImageView) frament_body_bottom.findViewById(R.id.iv_top11);
//		tv_baseinfo_alert = (TextView) frament_body_bottom
//				.findViewById(R.id.tv_baseinfo_alert);
//		tv_baseinfo_alert_value = (TextView) frament_body_bottom
//				.findViewById(R.id.tv_baseinfo_alert_value);
//		tv_baseinfo_alert_kcal = (TextView) frament_body_bottom
//				.findViewById(R.id.tv_baseinfo_alert_kcal);
		tv_couont_qualified = (TextView) frament_body_bottom
				.findViewById(R.id.tv_couont_qualified);
		tv_couont_unqualified = (TextView) frament_body_bottom
				.findViewById(R.id.tv_couont_unqualified);
		tv_hege = (TextView) frament_body_bottom.findViewById(R.id.tv_hege);
		tv_buhege = (TextView) frament_body_bottom.findViewById(R.id.tv_buhege);
		// tv_hege.setTextColor(ColorConstants.colorValue2);
		// tv_buhege.setTextColor(ColorConstants.colorValue0);
		iv_top_handle = (ImageView) view.findViewById(R.id.iv_top_body);
		iv_body_addweight = (Button) view
				.findViewById(R.id.iv_body_addweight);
		tv_body_advice = (TextView) view.findViewById(R.id.tv_body_advice);

		rl_handler_blank = (RelativeLayout) handleView
				.findViewById(R.id.rl_handler_blank);
		tv_nodata_alert_blank = (TextView) rl_handler_blank
				.findViewById(R.id.tv_nodata_alert_blank);

		rl_handler = (RelativeLayout) handleView.findViewById(R.id.rl_handler);
//		tv_baseinfo_alert_handle = (TextView) handleView
//				.findViewById(R.id.tv_baseinfo_alert);
//		tv_baseinfo_alert_value_handle = (TextView) handleView
//				.findViewById(R.id.tv_baseinfo_alert_value);
//		tv_baseinfo_alert_kcal_handle = (TextView) handleView
//				.findViewById(R.id.tv_baseinfo_alert_kcal);
		tv_couont_qualified_handle = (TextView) handleView
				.findViewById(R.id.tv_couont_qualified);
		tv_couont_unqualified_handle = (TextView) handleView
				.findViewById(R.id.tv_couont_unqualified);
		tv_hege_handle = (TextView) handleView.findViewById(R.id.tv_hege);
		tv_buhege_handle = (TextView) handleView.findViewById(R.id.tv_buhege);

		ll_bodystateunqualified1 = (LinearLayout) frament_body_bottom
				.findViewById(R.id.ll_bodystateunqualified1);
		ll_bodystatequalified1 = (LinearLayout) frament_body_bottom
				.findViewById(R.id.ll_bodystatequalified1);
		tv_qualified_alet = (TextView) frament_body_bottom
				.findViewById(R.id.tv_qualified_alet);
		tv_qualified_alet = (TextView) frament_body_bottom
				.findViewById(R.id.tv_qualified_alet);
		tv_unqualified_alet_value = (TextView) frament_body_bottom
				.findViewById(R.id.tv_unqualified_alet_value);
		tv_qualified_alet_value = (TextView) frament_body_bottom
				.findViewById(R.id.tv_qualified_alet_value);
		// tv_unqualified_alet.setTextColor(ColorConstants.colorValue1);

		iv_top_handle.setOnClickListener(this);
		btn_share.setOnClickListener(this);

		iv_body_addweight.setOnClickListener(this);
		viewFlipper.setListener(this);
		// 从本地数据加载体测主页
		fillData(null);
	}

	public void buildViewFromLoacalData() {
		showProgress(false);
		if (this.isHidden()) {
			if (dao.isRunning()) {
				dao.cancel();
			}
			return;
		}
		if (dao.isRunning()) {
			dao.cancel();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SharedPreferences shp = activity.getSharedPreferences("body",
				Context.MODE_WORLD_WRITEABLE);
		fillData(null);
		String homeString = shp.getString(format.format(new Date()), null);
		if (homeString != null) {
			Gson gson = new Gson();
			HomeBodyData homeBodyData = gson.fromJson(homeString,
					HomeBodyData.class);
			fillData(homeBodyData);
		}
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				dao.GetHomeBodyData();
			}
		}, 100);
	}

	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);

//		if (!hidden) {
//			if (bodypage == 1) {
//				if (hbd != null && !TextUtils.isEmpty(hbd.getTime())) {
//					titliname = getString(R.string.body_data_title_str) + "\n"
//							+ hbd.getTime();
//					bListener.change(titliname, bodypage);
//				} else {
//					titliname = "体测";
//					bListener.change(titliname, bodypage);
//				}
//			} else {
//				titliname = getString(R.string.body_baogao_title_str);
//				if (bListener != null) {
//					bListener.change(titliname, 2);
//				}
//			}
//		} else {
//			if (dao.isRunning()) {
//				dao.cancel();
//			}
//		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		showProgress(false);
		if (activity != null && !activity.isFinishing() && !this.isHidden()) {
			if (requestCode == 50) {
				// 更新本地
				cusomter = LocalDataUtils.readCustomer(activity);

				if (dao.getHomeBodyData() != null
						&& dao.getHomeBodyData().getTizhongData() != null
						&& dao.getHomeBodyData().getTizhongData().getValue() > 0) {
					cusomter.weight = String.valueOf(dao.getHomeBodyData()
							.getTizhongData().getValue());
					LocalDataUtils.saveCustomer(activity, cusomter);
				}
				fillData(dao.getHomeBodyData());
			} else if (requestCode == 1000) {// 添加体重结果返回
				updateLoacalInfo();
				// 更新布局
				iv_dialScaleView.init(Integer.parseInt(cusomter.gender),
						(float) Double.parseDouble(cusomter.weight),
						Integer.parseInt(cusomter.height));

				// dao.GetHomeBodyData();
			}
		}
	}

	private void updateLoacalInfo() {
		// 更新本地
		cusomter = LocalDataUtils.readCustomer(activity);
		cusomter.weight = String.valueOf(weightValue);
		LocalDataUtils.saveCustomer(activity, cusomter);
		SharedPreferences shp = activity.getSharedPreferences("body",
				Context.MODE_WORLD_WRITEABLE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String json_body = shp.getString(format.format(new Date()), null);
		if (!TextUtils.isEmpty(json_body)) {
			Gson gson = new Gson();
			// HomeBodyData homeBodyData = gson.fromJson(json_body,
			// HomeBodyData.class);
			hbd = gson.fromJson(json_body, HomeBodyData.class);
			if (hbd.getTizhongData() != null)
				hbd.getTizhongData().setValue(weightValue);
			else {
				TizhongData td = new TizhongData();
				td.setValue(weightValue);
				hbd.setTizhongData(td);
			}
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy年MM月dd日");
			hbd.setTime(timeFormat.format(new Date()));
			json_body = gson.toJson(hbd, HomeBodyData.class);
			Editor editor = shp.edit();
			editor.clear();
			editor.putString(format.format(new Date()), json_body);
			editor.commit();

			fillData(hbd);
		}

	}

	HomeBodyData hbd;

	public HomeBodyData getHbd() {
		DecimalFormat decimalFormat=new DecimalFormat("#.#");
		decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
		if (hbd == null||hbd.getTizhongData()==null||hbd.getBmiData()==null) {
			HomeBodyData hh = new HomeBodyData();
			SharedPreferences shp = activity.getSharedPreferences("body",
					Context.MODE_WORLD_WRITEABLE);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String json_body = shp.getString(format.format(new Date()), null);
			if (!TextUtils.isEmpty(json_body)) {
				Gson gson = new Gson();
				hh = gson.fromJson(json_body, HomeBodyData.class);

				if (hh.getTizhongData() == null){
					TizhongData td = new TizhongData();
					td.setValue(weightValue);
					hh.setTizhongData(td);
				}
				if(hh.getBmiData()==null){
					BmiData bd=new BmiData();
					double bmi=Double.parseDouble(cusomter.getWeight())/((float)Integer.parseInt(cusomter.getHeight())*Integer.parseInt(cusomter.getHeight())/(100*100));
					Log.d("MyLog","cusomter.getWeight()---------------"+ cusomter.getWeight());
					Log.d("MyLog","cusomter.getHeight()---------------"+ cusomter.getHeight());
					Log.d("MyLog","cusomter.bmi---------------"+ bmi+"");

					bd.setValue(Double.parseDouble(decimalFormat.format(bmi)));
					hh.setBmiData(bd);
				}else{
					BmiData bd=hh.getBmiData();
					double bmi=Double.parseDouble(cusomter.getWeight())/((float)Integer.parseInt(cusomter.getHeight())*Integer.parseInt(cusomter.getHeight())/(100*100));
					bd.setValue(Double.parseDouble(decimalFormat.format(bmi)));
					hh.setBmiData(bd);
				}

				return hh;
			} else {
				return new HomeBodyData();
			}
		} else {

			BmiData bd=hbd.getBmiData();
			double bmi=Double.parseDouble(cusomter.getWeight())/((float)Integer.parseInt(cusomter.getHeight())*Integer.parseInt(cusomter.getHeight())/(100*100));
			bd.setValue(Double.parseDouble(decimalFormat.format(bmi)));
			return hbd;
		}

	}

	private boolean isBlank(HomeBodyData hbd) {
		boolean isBlank = true;
		if (hbd.getBmiData() != null || hbd.getDanbaizhiData() != null
				|| hbd.getFubufeipanglvData() != null
				|| hbd.getGuliangData() != null || hbd.getJiroulvData() != null
				|| hbd.getNeizangzhifangData() != null
				|| hbd.getShuifenData() != null
				|| hbd.getZhifanglvData() != null) {
			isBlank = false;
		}
		return isBlank;
	}

	void fillData(HomeBodyData hbd) {
		if(this.isDetached()){
			return;
		}
		String value="最佳体重"+iv_dialScaleView.getstandardValue()+"kg";
		tv_standWeight.setText(value);
		this.hbd = hbd;
		if (hbd == null || (hbd != null && TextUtils.isEmpty(hbd.getTime()))) {// 空数据页面判断，如果时间为空，显示空数据页面
			// 设置显示布局块
			rl_handler_blank.setVisibility(View.VISIBLE);
			rl_handler.setVisibility(View.INVISIBLE);
			// 更新文本布局
//			tv_body_advice.setText("快去测量体重，了解自己的身体状况吧！");
			DecimalFormat format = new DecimalFormat("#.#");
			double currentValue = Double.parseDouble(cusomter.getWeight());
			if (currentValue <= iv_dialScaleView.getLowValue()) {// 偏瘦提示
				tv_body_advice.setText("你属于偏瘦体型，平时要注意提高热量的摄入，适当增加体重哦！");
			} else if (currentValue >= iv_dialScaleView.getHighValue()) {// 偏胖提示
				tv_body_advice.setText("你属于偏胖体型，平时要注意增强体质锻炼，适当减轻体重哦！");
			} else if (currentValue < iv_dialScaleView.getstandardValue()) {

				tv_body_advice.setText("你属于正常体型，但距离最佳体重还有"
						+ format.format(iv_dialScaleView.getstandardValue()
						- currentValue) + "千克，要继续努力哦！");
			} else if (currentValue > iv_dialScaleView.getstandardValue()) {
				tv_body_advice.setText("你属于正常体型，但超出最佳体重"
						+ format.format(currentValue
						- iv_dialScaleView.getstandardValue())
						+ "千克，要继续加油哦！");
			} else if (currentValue == iv_dialScaleView.getstandardValue()) {
				tv_body_advice.setText("你属于完美体型，要继续保持哦！");
			}

			tv_nodata_alert_blank.setText("暂无数据，你可能还未添加或测量以下项目");
			// 更新体重布局
			iv_dialScaleView.init(Integer.parseInt(cusomter.gender),
					(float) Double.parseDouble(cusomter.weight),
					Integer.parseInt(cusomter.height));
		} else if (isBlank(hbd)) {
			// 设置显示布局块
			rl_handler_blank.setVisibility(View.VISIBLE);
			rl_handler.setVisibility(View.INVISIBLE);

			// 更新文本布局，根据需求的计算方式求出需要显示的内容
			tv_nodata_alert_blank.setText("暂无数据，你可能还未添加或测量以下项目");
			DecimalFormat format = new DecimalFormat("#.#");
			double currentValue = hbd.getTizhongData().getValue();
			if (currentValue <= iv_dialScaleView.getLowValue()) {// 偏瘦提示
				tv_body_advice.setText("你属于偏瘦体型，平时要注意提高热量的摄入，适当增加体重哦！");
			} else if (currentValue >= iv_dialScaleView.getHighValue()) {// 偏胖提示
				tv_body_advice.setText("你属于偏胖体型，平时要注意增强体质锻炼，适当减轻体重哦！");
			} else if (currentValue < iv_dialScaleView.getstandardValue()) {

				tv_body_advice.setText("你属于正常体型，但距离最佳体重还有"
						+ format.format(iv_dialScaleView.getstandardValue()
								- currentValue) + "千克，要继续努力哦！");
			} else if (currentValue > iv_dialScaleView.getstandardValue()) {
				tv_body_advice.setText("你属于正常体型，但超出最佳体重"
						+ format.format(currentValue
								- iv_dialScaleView.getstandardValue())
						+ "千克，要继续加油哦！");
			} else if (currentValue == iv_dialScaleView.getstandardValue()) {
				tv_body_advice.setText("你属于完美体型，要继续保持哦！");
			}
			// 更新体重布局
			iv_dialScaleView.init(Integer.parseInt(cusomter.gender),
					(float) Double.parseDouble(cusomter.weight),
					Integer.parseInt(cusomter.height));
			// 设置滑动监听
		} else {
			// 设置显示布局块
			rl_handler_blank.setVisibility(View.INVISIBLE);
			rl_handler.setVisibility(View.VISIBLE);

			// 更新体重布局
			iv_dialScaleView.init(Integer.parseInt(cusomter.gender),
					(float) Double.parseDouble(cusomter.weight),
					Integer.parseInt(cusomter.height));

			// 更新文本布局，根据需求的计算方式求出需要显示的内容
			DecimalFormat format = new DecimalFormat("#.#");
			double currentValue = hbd.getTizhongData().getValue();
			if ((float) currentValue <= iv_dialScaleView.getLowValue()) {// 偏瘦提示
				tv_body_advice.setText("你属于偏瘦体型，平时要注意提高热量的摄入，适当增加体重哦！");
			} else if ((float) currentValue >= iv_dialScaleView.getHighValue()) {// 偏胖提示
				tv_body_advice.setText("你属于偏胖体型，平时要注意增强体质锻炼，适当减轻体重哦！");
			} else if ((float) currentValue < iv_dialScaleView
					.getstandardValue()) {

				tv_body_advice.setText("你属于正常体型，但距离最佳体重还有"
						+ format.format(iv_dialScaleView.getstandardValue()
								- currentValue) + "千克，要继续努力哦！");
			} else if ((float) currentValue > iv_dialScaleView
					.getstandardValue()) {
				tv_body_advice.setText("你属于正常体型，但超出最佳体重"
						+ format.format(currentValue
								- iv_dialScaleView.getstandardValue())
						+ "千克，要继续加油哦！");
			} else if ((float) currentValue == iv_dialScaleView
					.getstandardValue()) {
				tv_body_advice.setText("你属于完美体型，要继续保持哦！");
			}
			// tv_body_advice.setText("快去测量体重，了解自己的身体状况吧！");

			// 设置滑动监听
			iv_top_handle.setOnClickListener(this);
			viewFlipper.setListener(this);

			double daixielv = hbd.getJichudaixie();
			String daixielvValue = String.valueOf(daixielv);
			if (daixielv >= 9999.9)
				daixielvValue = "大于9999.9";
			int age = hbd.getShentinianling();
			String ageString = String.valueOf(age);
			SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
			int actualAge = Integer.parseInt(yearFormat.format(new Date()))
					- Integer.parseInt(cusomter.getYear());
			// if (age < 0 || age > 100) {
			// ageString = String.valueOf(age);
			// tv_baseinfo_alert.setText("您的身体年龄" + ageString + "岁" + " 基础代谢率"
			// + daixielvValue + "kcal/d");
			// tv_baseinfo_alert_handle.setText("您的身体年龄" + ageString + "岁"
			// + " 基础代谢率" + daixielvValue + "kcal/d");
			// } else if (age > actualAge) {
			// tv_baseinfo_alert.setText("您的身体年龄超过您的实际年龄" + (age - actualAge)
			// + "岁" + " 基础代谢率" + daixielvValue + "kcal/d");
			// tv_baseinfo_alert_handle.setText("您的身体年龄超过您的实际年龄"
			// + (age - actualAge) + "岁" + " 基础代谢率" + daixielvValue
			// + "kcal/d");
			// } else if (age < actualAge) {
			// tv_baseinfo_alert.setText("您的身体年龄比实际年龄年轻" + (actualAge - age)
			// + "岁" + " 基础代谢率" + daixielvValue + "kcal/d");
			// tv_baseinfo_alert_handle.setText("您的身体年龄比实际年龄年轻"
			// + (actualAge - age) + "岁" + " 基础代谢率" + daixielvValue
			// + "kcal/d");
			// } else {
			// tv_baseinfo_alert.setText("您的身体年龄" + ageString + "岁" + " 基础代谢率"
			// + daixielvValue + "kcal/d");
			// tv_baseinfo_alert_handle.setText("您的身体年龄" + ageString + "岁"
			// + " 基础代谢率" + daixielvValue + "kcal/d");
			// }
//			tv_baseinfo_alert_value.setText(daixielvValue);
//			tv_baseinfo_alert_value_handle.setText(daixielvValue);

			tv_couont_qualified.setText(String.valueOf(hbd.getQualified()));
			tv_couont_unqualified.setText(String.valueOf(hbd.getUnqualified()));

			// tv_baseinfo_alert_handle.setText("您的身体年龄" +
			// hbd.getShentinianling()
			// + "岁" + " 基础代谢率" + daixielv + "kcal/d");
			tv_couont_qualified_handle.setText(String.valueOf(hbd
					.getQualified()));
			tv_couont_unqualified_handle.setText(String.valueOf(hbd
					.getUnqualified()));
			tv_unqualified_alet_value.setText(String.valueOf(hbd
					.getUnqualified()));
			tv_qualified_alet_value.setText(String.valueOf(hbd.getQualified()));
			//
			// tv_unqualified_alet.setText("您有" + hbd.getUnqualified()
			// + "项指标不合格，请加强锻炼");
			// tv_qualified_alet
			// .setText("您有" + hbd.getQualified() + "项指标正常，请继续保持");

//			tv_xibaozhongliang.setText(hbd.getXibaozhongliang() + suffix);
//			tv_kuangwuzhi.setText(hbd.getKuangwuzhi() + suffix);
			tv_advice.setText(hbd.getAdvice().replace("\n", ""));

			addBodyStateView(hbd);
		}

	}

	private void addBodyStateView(HomeBodyData hbd) {
		ll_bodystatequalified.removeAllViews();
		ll_bodystateunqualified.removeAllViews();
		WindowManager wm = (WindowManager) ma
				.getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();

		int size = width;
		double value = 0;
		// 体质指数BMI
		BodyStateView bsv=null;
		RelativeLayout rl=null;
		LayoutParams rlParams =null;
		RelativeLayout.LayoutParams params=null;
		if (hbd.getBmiData() != null) {
			bsv = new BodyStateView(activity);
			 rl = new RelativeLayout(activity);
			 rlParams= new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(
					size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.体质指数BMI);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getBmiData().getValue();
			if (value > hbd.getBmiData().getStandard().getNormalFloor()
					&& value <= hbd.getBmiData().getStandard()
							.getHeavierFloor()) {
				
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			} else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		// 身体脂肪率
		
		if (hbd.getZhifanglvData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.身体脂肪率);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getZhifanglvData().getValue();
			if (value > hbd.getZhifanglvData().getStandard().getStandardFloor()
					&& value <= hbd.getZhifanglvData().getStandard()
							.getMildFatFloor()) {
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			} else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		// 内脏脂肪水平
		
		if (hbd.getNeizangzhifangData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.内脏脂肪水平);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getNeizangzhifangData().getValue();
			if (value > hbd.getNeizangzhifangData().getStandard()
					.getBalancedFloor()
					&& value <= hbd.getNeizangzhifangData().getStandard()
							.getWarningFloor()) {
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			} else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		// 腹部肥胖率
		
		if (hbd.getFubufeipanglvData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.腹部肥胖率);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getFubufeipanglvData().getValue();
			if (value > hbd.getFubufeipanglvData().getStandard()
					.getStandardFloor()
					&& value <= hbd.getFubufeipanglvData().getStandard()
							.getStandardCeil()){
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			}
			else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		// 肌肉率
		
		if (hbd.getJiroulvData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.肌肉率);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getJiroulvData().getValue();
			if (value >= hbd.getJiroulvData().getStandard().getStandardFloor()){
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			}
			else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		
		if (hbd.getDanbaizhiData() != null) {
			// 蛋白质
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.蛋白质重量比);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getDanbaizhiData().getValue();
			if (value > hbd.getDanbaizhiData().getStandard().getStandardFloor()
					&& value <= hbd.getDanbaizhiData().getStandard()
							.getStandardCeil()){
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			}
			else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		// 身体水分
		
		if (hbd.getShuifenData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.身体水分);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getShuifenData().getValue();
			if (value > hbd.getShuifenData().getStandard().getNormalFloor()
					&& value <= hbd.getShuifenData().getStandard()
							.getNormalCeil()) {
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			} else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}

		}
		// 骨量
		
		if (hbd.getGuliangData() != null) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.骨量);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getGuliangData().getValue();
			if (value > hbd.getGuliangData().getStandard().getNormalFloor()
					&& value <= hbd.getGuliangData().getStandard()
							.getNormalCeil()) {
				ll_bodystatequalified.addView(rl);
				bsv.setIsBodystatequalified(false);
			} else {
				ll_bodystateunqualified.addView(rl);
				bsv.setIsBodystatequalified(true);
			}
		}
		//======2.1.2隐藏======
		/*
		// 身体年龄

		if (hbd.getShentinianling() > -1) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.身体年龄);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getShentinianling();

			ll_bodystatequalified.addView(rl);
			bsv.setIsBodystatequalified(false);

		}
		// 细胞重量
		
		if (hbd.getXibaozhongliang() > -1) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.细胞重量);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getShentinianling();

			ll_bodystatequalified.addView(rl);
			bsv.setIsBodystatequalified(false);

		}
		// 矿物质含量
		
		if (hbd.getKuangwuzhi() > -1) {
			bsv = new BodyStateView(activity);
			rl = new RelativeLayout(activity);
			rlParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT);
			rl.setLayoutParams(rlParams);
			params = new RelativeLayout.LayoutParams(size, size);
			params.addRule(Gravity.CENTER_HORIZONTAL);
			params.addRule(RelativeLayout.CENTER_HORIZONTAL);
			bsv.setLayoutParams(params);
			bsv.setHomeBodyData(hbd);
			bsv.setBodyStateType(BodeStateType.矿物质含量);
			rl.addView(bsv);
			bsv.setOnTitleClickListener(onTitleClickListener);
			value = hbd.getShentinianling();

			ll_bodystatequalified.addView(rl);
			bsv.setIsBodystatequalified(false);

		}
		*/
		//======2.1.2隐藏======

		// 判断两个textview是否显示

		if (hbd.getUnqualified() == 0)
			ll_bodystateunqualified1.setVisibility(View.GONE);
		else
			ll_bodystateunqualified1.setVisibility(View.VISIBLE);
		if (hbd.getQualified() == 0)
			ll_bodystatequalified1.setVisibility(View.GONE);
		else
			ll_bodystatequalified1.setVisibility(View.VISIBLE);
	}

	/*
	 * p1起始位置 p2终点位置 显示上面布局
	 */
	public void dissmissAnimation() {
		final int currentTop = frament_body_bottom.getTop();
		final int currentLeft = frament_body_bottom.getLeft();
		final int endTop = currentTop + frament_body_bottom.getHeight()
				- getResources().getDimensionPixelSize(R.dimen.y150) - 10;
		TranslateAnimation animation = new TranslateAnimation(0, 0, currentTop,
				endTop);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);
		animation.setStartOffset(0);

		ScrollViewInner scrollView = (ScrollViewInner) frament_body_bottom;
		scrollView.setSpeedMaxRate();
		scrollView.setNeedScroll(false);
		viewFlipper.setScrolling(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

				iv_top.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				final	ScrollViewInner scrollView = (ScrollViewInner) frament_body_bottom;
				scrollView.setSpeedMinRate();

				int left = frament_body_bottom.getLeft();
				int top = endTop;
				int width = frament_body_top.getWidth();
				int height = frament_body_top.getHeight();
				// frament_body_top.clearAnimation();
				TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
				frament_body_bottom.setAnimation(anim);
				frament_body_bottom.layout(left, top, left + width, top
						+ height );
				handleView.setVisibility(View.VISIBLE);


				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						scrollView.setNeedScroll(true);
						viewFlipper.setOpened(false);
						viewFlipper.setScrolling(false);
					}
				},100);

			}
		});
		viewFlipper.setOutAnimation(animation);
	}

	//
	/*
	 * p1起始位置 p2终点位置
	 */
	public void showingAnimation() {
		final int currentTop = frament_body_top.getTop()
				+ frament_body_top.getHeight()
				- getResources().getDimensionPixelSize(R.dimen.y150);
		final int currentLeft = frament_body_top.getLeft();
		final int endTop = frament_body_top.getTop();
		TranslateAnimation animation = new TranslateAnimation(0, 0, currentTop,
				endTop);
		animation.setInterpolator(new OvershootInterpolator());
		animation.setDuration(1000);
		animation.setStartOffset(0);

		ScrollViewInner scrollView = (ScrollViewInner) frament_body_bottom;
		scrollView.setSpeedMaxRate();
		scrollView.setNeedScroll(false);
		viewFlipper.setScrolling(true);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				handleView.setVisibility(View.INVISIBLE);
				iv_top.setVisibility(View.INVISIBLE);
//				frament_body_bottom.findViewById(R.id.ll1).setBackgroundColor(
//						getResources().getColor(R.color.app_bg));


			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				final ScrollViewInner scrollView = (ScrollViewInner) frament_body_bottom;
				scrollView.setSpeedMinRate();

				int left = frament_body_top.getLeft();
				int top = endTop;
				int width = frament_body_bottom.getWidth();
				int height = frament_body_top.getHeight();
				// frament_body_top.clearAnimation();
				TranslateAnimation anim = new TranslateAnimation(0, 0, 0, 0);
				frament_body_bottom.setAnimation(anim);
				frament_body_bottom.layout(left, top , left + width, top
						+ height);


				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						scrollView.setNeedScroll(true);
						viewFlipper.setOpened(true);
						viewFlipper.setScrolling(false);
					}
				}, 100);
			}
		});
		viewFlipper.setInAnimation(animation);
	}

	// 向下滑 显示下面布局
	private void showNextView() {
		viewFlipper.setScrolling(true);
		Log.e("向下滑动", "OK");
		// viewFlipper.setInAnimation(AnimationUtils.loadAnimation(activity,
		// R.anim.push_top_in));
		showingAnimation();
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.push_top_out));
		viewFlipper.showNext();
		if (bListener != null) {// 回调 change 主activity标题给变量赋值
			bodypage = 2;
			bListener.change(getString(R.string.body_baogao_title_str),
					bodypage);

		}
	}

	// 向上滑 显示上面布局
	private void showPreviousView() {
		// TODO Auto-generated method stub

		viewFlipper.setScrolling(true);
		Log.e("向上滑动", "OK");
		viewFlipper.setInAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.push_bot_in));
		//
		viewFlipper.setOutAnimation(AnimationUtils.loadAnimation(activity,
				R.anim.push_bot_out));
		dissmissAnimation();
		viewFlipper.showPrevious();
		if (bListener != null) {// 回调 change 主activity标题给变量赋值
			bodypage = 1;
			if (hbd != null && !TextUtils.isEmpty(hbd.getTime()))
				bListener.change(getString(R.string.body_data_title_str) + "\n"
						+ hbd.getTime(), bodypage);
			else
				bListener.change("体测", bodypage);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.iv_top_body:
			viewFlipper.setOpened(true);
			showNextView();
			break;
		case R.id.btn_share:

			if(!isSaving){
				jump2ShareActivity();
			}
			
			break;
		case R.id.iv_body_addweight:
			Intent intent = new Intent(activity,
					PersonalInformationActivity.class);
			startActivityForResult(intent, requestCode);
			break;

			case R.id.btn_left:
			case R.id.ll_btn_left:

				FragmentTransaction _ft = ma.getManager().beginTransaction();
//				_ft.replace(R.id.id_rlyt_fragment, HomeFragment.newInstance(), R.id.id_add_device_button + "");
				_ft.replace(R.id.id_rlyt_fragment,new HomeFragment(),"HomeFragment");
				_ft.addToBackStack(null);
				_ft.commit();
				break;

			case R.id.btn_right:
			case R.id.ll_btn_right:

				intent = new Intent(activity,
						HistoryBodyActivity.class);

				intent.putExtra("bhd", getHbd());
				startActivity(intent);
				AnimUtil.intentSlidIn(activity);

				break;
		default:
			break;
		}
	}

	public static final int requestCode = 101;
	boolean isSaving = false;

	
	private synchronized void jump2ShareActivity() {
		if (!isSaving) {
			 showProgress(true);
			isSaving = true;

			final List<Bitmap> bmpsList = new ArrayList<Bitmap>();

			final LayoutInflater inflater = activity.getLayoutInflater();
			final View view_head = inflater.inflate(R.layout.share_pic_head,
					null);
			final View view_bottom = inflater.inflate(
					R.layout.share_pic_bottom, null);
			final TextView tv_appname = (TextView) view_bottom
					.findViewById(R.id.tv_appname);
			final TextView tv_desp = (TextView) view_bottom
					.findViewById(R.id.tv_desp);
			final ImageView iv_head = (ImageView) view_head
					.findViewById(R.id.iv_head);
			TextView tv_name = (TextView) view_head.findViewById(R.id.tv_name);
			tv_name.setText(cusomter.getName());
			
			tv_appname.setText(getString(R.string.app_name));
			tv_desp.setText("你从未如此了解自己");

			bitmapUtils.display(iv_head,
					HttpConnectUtils.image_ip + cusomter.getAvatar(),
					new BitmapLoadCallBack<View>() {
						@Override
						public void onLoadCompleted(View container, String uri,
								Bitmap bitmap, BitmapDisplayConfig config,
								BitmapLoadFrom from) {
							// TODO Auto-generated method stub
							// 顶部头像转成圆角
							iv_head.setImageBitmap(ImageHelper
									.toRoundBitmap(bitmap));
							
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									// 添加顶部的头像view
									bmpsList.add(Utils.getBitmapByViewCache(view_head, 0));

									// 添加列表view
									ScrollView svi = (ScrollView) frament_body_bottom;
									Bitmap bmp = Utils.getBitmapByView(svi,Color.parseColor("#00768c"),0);
									bmpsList.add(bmp);

									// 添加底部说明
									bmpsList.add(Utils.getBitmapByViewCache(view_bottom, 0));

									// 生成整个分享图片
									bmp = Utils.getBitmapSportList(Color.parseColor("#00768c"), bmpsList);
									String filePath = Utils.savePic(bmp, "bodyCheck");

									Message msg = new Message();
									msg.what = 0;
									msg.obj = filePath;
									handler.sendMessage(msg);
								}
							}).start();
							
						}

						@Override
						public void onLoadFailed(View container, String uri,
								Drawable drawable) {
							// TODO Auto-generated method stub

							Bitmap bitmap;
							Resources res = getResources();
							if("1".equals(customer.getGender())){
								bitmap = BitmapFactory.decodeResource(res,
										R.mipmap.boy_head);
							}else{
								bitmap = BitmapFactory.decodeResource(res,
										R.mipmap.girl_head);
							}

							iv_head.setImageBitmap(ImageHelper
									.toRoundBitmap(bitmap));
							new Thread(new Runnable() {
								
								@Override
								public void run() {
									// TODO Auto-generated method stub
									// 添加顶部的头像view
									bmpsList.add(Utils.getBitmapByViewCache(view_head, 0));

									// 添加列表view
									ScrollView svi = (ScrollView) frament_body_bottom;
									Bitmap bmp = Utils.getBitmapByView(svi,Color.parseColor("#00768c"),0);
									bmpsList.add(bmp);

									// 添加底部说明
									bmpsList.add(Utils.getBitmapByViewCache(view_bottom, 0));

									// 生成整个分享图片
									bmp = Utils.getBitmapSportList(Color.parseColor("#00768c"), bmpsList);

									String filePath = Utils.savePic(bmp, "bodyCheck");

									Message msg = new Message();
									msg.what = 0;
									msg.obj = filePath;
									handler.sendMessage(msg);
								}
							}).start();
						}
					});
		}
	}
	
//	private void jump2ShareActivity() {
//		if (!isSaving) {
//			showProgress(true);
//
//			// ScrollView sv = (ScrollView) frament_body_bottom;
//			// String filePath = Utils.savePic(
//			// Utils.getBitmapByView(sv, ll_bottom.getHeight()), "bodyCheck");
//			// Intent intent = new Intent(activity,
//			// SocialShareActivity.class);
//			// intent.putExtra("filePath", filePath);
//			// startActivity(intent);
//			new Thread(new Runnable() {
//
//				@Override
//				public void run() {
//					isSaving = true;
//					ScrollView sv = (ScrollView) frament_body_bottom;
//					String filePath = Utils.savePic(
//							Utils.getBitmapByView(sv, 0), "bodyCheck");
//					Message msg = new Message();
//					msg.what = 0;
//					msg.obj = filePath;
//					handler.sendMessage(msg);
//				}
//			}).start();
//		}
//	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			showProgress(false);
			switch (msg.what) {
			case 0:
				if (msg.obj != null) {
					String filePath = (String) msg.obj;
					Intent intent = new Intent(activity,
							SocialShareActivity.class);
					intent.putExtra("filePath", filePath);
					startActivity(intent);
					isSaving = false;
				}
				break;

			default:
				break;
			}

		};
	};

	BitmapUtils bitmapUtils;

	/**
	 * 初始化图片加载器
	 */
	private void initBitmapUtils() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/"+getString(R.string.app_name)+"/cacher";
		bitmapUtils = new BitmapUtils(activity, path);
		bitmapUtils.configDefaultLoadFailedImage(R.mipmap.icon_touxiang);
		bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
														// 尽量不要使用,图片太大时容易OOM。
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(activity));
		bitmapUtils.configDefaultLoadingImage(R.mipmap.icon_touxiang);
		// bitmapUtils.configDefaultCacheExpiry(2*60*1000);
		bitmapUtils.configThreadPoolSize(5);

	}
	
	
	public interface BodyListener {
		public void change(String name, int i);
	}

	public void setbListener(BodyListener bListener) {
		this.bListener = bListener;
	}

	private OnTitleClickListener onTitleClickListener = new OnTitleClickListener() {

		@Override
		public void OnTitleClick(BodeStateType bodeStateType) {
			// 点击title部分
			Intent intent = new Intent(activity, IndicatorsAlert.class);
			intent.putExtra("type", bodeStateType);
			startActivity(intent);
		}
	};

	double weightValue;

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == this.requestCode) {
			if (resultCode == Activity.RESULT_OK) {

				weightValue = data.getDoubleExtra("value", 100.0);
				System.out.println("weightValue=="+weightValue);
				dao.ModifyCustomerWeight(weightValue);
			}
		}
	}

	@Override
	public void onTouch(boolean opened) {
		if(!isBlank(hbd)){
			if (opened) {

				showNextView();

			} else {

				showPreviousView();
			}
		}

	}

}