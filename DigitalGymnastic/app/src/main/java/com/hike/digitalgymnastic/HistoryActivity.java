package com.hike.digitalgymnastic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.hike.digitalgymnastic.adapter.TimeLineAdapter;
import com.hike.digitalgymnastic.adapter.TimeLineAdapter.btnTimeLineListener;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.DailyTotalSportData;
import com.hike.digitalgymnastic.entitiy.OnceSportData;
import com.hike.digitalgymnastic.entitiy.PeriodSportData;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.SportType;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.HistogramView;
import com.hike.digitalgymnastic.view.HistogramView.OnTouchWeekListener;
import com.hike.digitalgymnastic.view.HistoryProgressView;
import com.hiko.enterprisedigital.R;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.request.SportDao;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
/*
 * @Auth changqi
 * 
 */
@ContentView(R.layout.activity_history)
public class HistoryActivity extends BaseActivity implements
		OnTouchWeekListener {
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	@ViewInject(R.id.title)
	TextView tv_title;
	// @ViewInject(R.id.tv_date)
	// TextView tv_date;
	@ViewInject(R.id.viewFlipper)
	ViewFlipper viewFlipper;

	@ViewInject(R.id.history_lv)
	ListView history_lv;
	@ViewInject(R.id.history_bottom_ly)
	View history_bottom_ly;
	@ViewInject(R.id.view_avercal)
	HistoryProgressView view_avercal;
	@ViewInject(R.id.view_avertime)
	HistoryProgressView view_avertime;
	@ViewInject(R.id.tv_hasnosport_alert)
	TextView tv_hasnosport_alert;
	private BaseDao dao;
	private SportDao sportDao;
	private LayoutInflater test_inflater;
	Customer customer;

	@OnClick(value={R.id.btn_left,R.id.ll_btn_left})
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			finish();
			break;
		case R.id.ll_btn_left:
			finish();
			break;
		default:
			break;
		}
	}

	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		//如果有获取焦点的对象，移除焦点，关闭软键盘
		 InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		 if(this.getCurrentFocus()!=null&&this.getCurrentFocus().getWindowToken()!=null){
			 inputmanger.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
					 InputMethodManager.HIDE_NOT_ALWAYS);
		 }
		init();
		setData();
	}

	private void setData() {
		// 动画效果
		leftInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_enter);
		leftOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_out);
		rightInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_out);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = format.format(new Date());

		buildView(null);

		showProgress(this,true);
		boolean isLasted = dao.GetPeriodSportDataFromDB(
				format.format(dateAdd(-56)), format.format(dateAdd(-1)));
		if (isLasted) {
			
			PeriodSportData periodSportData = dao.getPeriodSportData();
			buildView(periodSportData);
		}

	}

	public static Date dateAdd(int days) {
		// 日期处理模块 (将日期加上某些天或减去天数)返回字符串
		Calendar canlendar = Calendar.getInstance(); // java.util包
		canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
		return canlendar.getTime();
	}

	private void init() {
		customer = LocalDataUtils.readCustomer(this);
		dao = new BaseDao(this, this);
		sportDao=new SportDao(this, this);
		tv_title.setText(getString(R.string.history_title_str));
		// tv_date.setText("2015-06-22");
		// tv_date.setVisibility(View.GONE);
		// btn_left.setBackgroundResource(R.drawable.toolbar_back);

	}

	private OnClickListener MyListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			switch (v.getId()) {
			case R.id.btn_left:
				Back();
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Back();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	// 返回事件
	private void Back() {
		finish();

	}

	private void showPreviousView() {
		leftOutAnimation.setAnimationListener(null);
		rightInAnimation.setAnimationListener(null);
		leftInAnimation.setAnimationListener(anim);

		viewFlipper.setInAnimation(leftInAnimation);
		viewFlipper.setOutAnimation(rightOutAnimation);
		viewFlipper.showPrevious();// 向左滑动
		isShowNext = false;
	}

	private void showNextView() {
		leftInAnimation.setAnimationListener(null);
		rightOutAnimation.setAnimationListener(null);
		rightInAnimation.setAnimationListener(anim);
		viewFlipper.setInAnimation(rightInAnimation);
		viewFlipper.setOutAnimation(leftOutAnimation);
		viewFlipper.showNext();// 向右滑动
		isShowNext = true;
		// HistogramView hs = (HistogramView) viewFlipper.getCurrentView();
		// onDayClick(hs.getDataList().get(hs.getDataList().size()-1));
		// buildBottomView(hs.getDataList());
		// handler.postDelayed(new Runnable() {
		// @Override
		// public void run() {
		//
		// HistogramView hs = (HistogramView) viewFlipper.getCurrentView();
		// hs.start(2);
		// hs = (HistogramView) viewFlipper.getChildAt(viewFlipper
		// .getDisplayedChild() - 1);
		// hs.clear();
		// }
		// }, 500);

	}

	@Override
	public void onWeekTouch(Object obj) {
		// TODO Auto-generated method stub
		onDayClick(obj);

	}

	private void onDayClick(Object obj) {
		if (obj != null) {
			DailyTotalSportData otsd = (DailyTotalSportData) obj;
			TimeLineAdapter adpter = new TimeLineAdapter(this,
					otsd.getOnceList());
			history_lv.setAdapter(adpter);
			Utils.setListViewHeightBasedOnChildren(history_lv);
			
			adpter.setbtnTimeLineListener(new btnTimeLineListener() {
				@Override
				public void onSomeChange(OnceSportData info, int i) {
					btnGetDatas(info, i);
				}
			});
		}
	}

	boolean isToucheAble = true;

	@Override
	public void onWeekFiller(int direction) {
		// TODO Auto-generated method stub
		switch (direction) {
		case -1:
			if (viewFlipper.getDisplayedChild() != 0) {
				if (isToucheAble) {
					showPreviousView();
					isToucheAble = false;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isToucheAble = true;
						}
					}, 800);
				}

			}

			break;
		case 1:
			if (viewFlipper.getDisplayedChild() != viewFlipper.getChildCount() - 1)

				if (isToucheAble) {
					showNextView();
					isToucheAble = false;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isToucheAble = true;
						}
					}, 800);
				}

			break;

		default:
			break;
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
		}

	};



	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);
	}

	@Override
	public void showProgress(Context  pContext,boolean show) {
		// TODO Auto-generated method stub
		super.showProgress(pContext,show);
	}

	@Override
	public void showProgressWithText(Context  pContext,boolean show, String message) {
		// TODO Auto-generated method stub
		super.showProgressWithText(pContext,show, message);
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		// TODO Auto-generated method stub
		super.onRequestSuccess(requestCode);
		showProgress(this,false);
		if (this.isFinishing())
			return;
		
		
		showProgress(this,false);
		Intent intent = null;
		switch (requestCode) {
		case 16:
			PeriodSportData periodSportData = dao.getPeriodSportData();
			buildView(periodSportData);
			break;
		case SportType.HKSportTypePaoBuJi: // !<跑步机
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getPaobu());
			startActivity(intent);
			break;
		case SportType.HKSportTypeBuXing:// !<步行
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getBuxing());
			startActivity(intent);
			break;
		case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getYouxiangwudao());
			startActivity(intent);
			break;
		case SportType.HKSportTypeDongGanDanChe:// !<动感单车
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getDonggandanche());
			startActivity(intent);
			break;
		case SportType.HKSportTypeGangLingCao:// !<杠铃操
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getGanglingcao());
			startActivity(intent);
			break;
		case SportType.HKSportTypeBoJiCao:// !<搏击操
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getBojiCao());
			startActivity(intent);
			break;
		case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getTuoyuanji());
			startActivity(intent);
			break;
		case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getZuoshihuatinglali());
			startActivity(intent);
			break;
		case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getJianbanghouzhan());
			startActivity(intent);
			break;
		case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getGaolalibeiji());
			startActivity(intent);
			break;
		case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshibeijishenzhan());
			startActivity(intent);
			break;
		case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getZuoshifeiniao());
			startActivity(intent);
			break;
		case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getZuoshitixi());
			startActivity(intent);
			break;
		case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshidatuishenzhan());
			startActivity(intent);
			break;
		case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshihoutuiqushen());
			startActivity(intent);
			break;
		case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshidatuiwaiceji());
			startActivity(intent);
			break;
		case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshidatuineiceji());
			startActivity(intent);
			break;
		case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshijianbangtuiju());
			startActivity(intent);
			break;
		case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getZuoshishuangxiangtuixiong());
			startActivity(intent);
			break;
		case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getHudieshikuoxiong());
			startActivity(intent);
			break;
		case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getWoshituiquzhan());
			startActivity(intent);
			break;
		case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getErtoujishuangxiang());
			startActivity(intent);
			break;
		case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getSantoujishuangxiang());
			startActivity(intent);
			break;
		case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata,
					sportDao.getLishidatuishenzhan());
			startActivity(intent);
			break;
		case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getTiaojieshidengtui());
			startActivity(intent);
			break;
		case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getLishidatuiqushen());
			startActivity(intent);
			break;
		case SportType.HKSportTypeYouYong: // !<游泳
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getYouyong());
			startActivity(intent);
			break;

		case SportType.HKSportTypeJianshencao:
			intent = new Intent(this, SportSingleDetailActivity.class);
			intent.putExtra(Constants.oncesporttype, sporttype);
			intent.putExtra(Constants.oncesportname, sportName);
			intent.putExtra(Constants.oncesportdata, sportDao.getJianshencao());
			startActivity(intent);
			break;
		default:
			break;
		}
		

	}

	private void buildView(PeriodSportData periodSportData) {
		// TODO Auto-generated method stub
		showProgress(this,false);
		List<List<DailyTotalSportData>> dailyListCollection = new ArrayList<List<DailyTotalSportData>>();

		if (periodSportData != null) {
			viewFlipper.removeAllViews();
			List<DailyTotalSportData> dailyTotalSportDataList = periodSportData
					.getDailyList();
			if(dailyTotalSportDataList.size()==0){//取回来的空数据
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				Date startDate=dateAdd(-56);
				Date endDate=dateAdd(-1);
				DailyTotalSportData dtsd=new DailyTotalSportData();
				for(int i=56;i>0;i--){
					dtsd=new DailyTotalSportData();
					dtsd.setStatDate(format.format(dateAdd(-i)));
					dtsd.setTotalTime(0);
					dtsd.setTotalCalories(0);
					dtsd.setOnceList(new ArrayList<OnceSportData>());
					dailyTotalSportDataList.add(dtsd);
				}


			}else if(dailyTotalSportDataList.size()<56){//此处说明取回来的数据不是正好56天
				SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
				List<DailyTotalSportData> listNew =new ArrayList<DailyTotalSportData>();
				List<DailyTotalSportData> totalList =new ArrayList<DailyTotalSportData>();
				DailyTotalSportData dtsd=dailyTotalSportDataList.get(0);
				try {
					Date date=format.parse(dtsd.getStatDate());
					for(int i=56;i>dailyTotalSportDataList.size();i--){
						dtsd=new DailyTotalSportData();
						dtsd.setStatDate(format.format(dateAdd(-i)));
						dtsd.setTotalTime(0);
						dtsd.setTotalCalories(0);
						dtsd.setOnceList(new ArrayList<OnceSportData>());
						listNew.add(dtsd);
					}
					totalList.addAll(listNew);
					totalList.addAll(dailyTotalSportDataList);
					periodSportData.setDailyList(totalList);
					dailyTotalSportDataList=periodSportData.getDailyList();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}




			int viewSize = 0;
			int mode = 0;
			if (dailyTotalSportDataList.size() % 7 == 0) {
				viewSize = dailyTotalSportDataList.size() / 7;
				mode = dailyTotalSportDataList.size() % 7;
			} else {
				viewSize = dailyTotalSportDataList.size() / 7 + 1;

			}
			int size=dailyTotalSportDataList.size();
			for (int i = 0; i < viewSize; i++) {
				if (i == viewSize - 1) {
					if (mode == 0)
						dailyListCollection
								.add(dailyTotalSportDataList.subList(
										size- (i + 1) * 7,size - i * 7));
					else
						dailyListCollection.add(dailyTotalSportDataList
								.subList(0, mode));
				} else
					dailyListCollection.add(dailyTotalSportDataList.subList(
							size - (i + 1) * 7,
							size - i * 7));
			}
			double maxTotalCalories = 0;
			for (DailyTotalSportData dtsd : dailyTotalSportDataList) {
				if (dtsd.getTotalCalories() > maxTotalCalories)
					maxTotalCalories = dtsd.getTotalCalories();
			}
			new BigDecimal(maxTotalCalories).setScale(0,
					BigDecimal.ROUND_HALF_UP);
			int maxValue = (int) maxTotalCalories;
			int[] ySteps = getYSteps(maxValue,
					Double.parseDouble(customer.getGoalCalories()));

			if (dailyListCollection.size() > 0) {
				for (int i = dailyListCollection.size() - 1; i >= 0; i--) {
					List<DailyTotalSportData> list = dailyListCollection.get(i);
					HistogramView hs = new HistogramView(this,
							Double.parseDouble(customer.getGoalCalories()));
					hs.setOnTouchWeekListener(this);
					hs.initData(list, ySteps);
					hs.setOnTouchWeekListener(this);
					viewFlipper.addView(hs);
				}
				viewFlipper.setDisplayedChild(dailyListCollection.size() - 1);
				HistogramView hs = (HistogramView) viewFlipper
						.getChildAt(dailyListCollection.size() - 1);
				hs.start(2);

				history_bottom_ly.setVisibility(View.VISIBLE);

				List<DailyTotalSportData> dtsdList = dailyListCollection.get(0);
				buildBottomView(dtsdList);

			}
		} else {// 构建空数据页面
			viewFlipper.removeAllViews();
			HistogramView hs = new HistogramView(this);
			hs.init(Double.parseDouble(customer.getGoalCalories()));
			viewFlipper.addView(hs);
			history_bottom_ly.setVisibility(View.VISIBLE);
			view_avercal.setText("0kcal", 0, 2000);
			view_avertime.setText("0小时0分钟", 0, 8 * 3600);
		}

	}

	DecimalFormat df = new DecimalFormat("####.#");

	@SuppressLint("ResourceAsColor")
	void buildBottomView(List<DailyTotalSportData> dtsdList) {
		double totalCal = 0;
		long totalTime = 0;
		int calLength = 0;
		int timeLength = 0;
		for (DailyTotalSportData dtsd : dtsdList) {
			if (dtsd.getTotalCalories() > 0) {
				totalCal += dtsd.getTotalCalories();
				calLength++;
			}
			if (dtsd.getTotalTime() > 0) {
				totalTime += dtsd.getTotalTime();
				timeLength++;
			}

		}
		if (calLength > 0) {
			view_avercal
					.setText(String.valueOf((int) (totalCal / calLength + 0.5))
							+ "kcal", totalCal / calLength, 2000);
		} else {
			view_avercal.setText(String.valueOf(0) + "kcal", 0, 2000);
		}

		String value = "";
		view_avertime.setColor(R.color.time_color_normal);
		if (timeLength > 0) {
			if (totalTime / timeLength / 3600 > 0)
				value = totalTime / timeLength / 3600 + "h" + totalTime
						/ timeLength % 3600 / 60 + "min";
			else
				value = totalTime / timeLength / 60 + "min";
			view_avertime.setText(value, totalTime / timeLength, 8 * 3600);
		} else {
			value = "0min";
			view_avertime.setText(value, 0, 8 * 3600);
		}

		if (totalCal == 0 && totalTime == 0) {
			tv_hasnosport_alert.setVisibility(View.VISIBLE);
		} else {
			tv_hasnosport_alert.setVisibility(View.INVISIBLE);
		}
	}
	private int[] getYSteps(int maxValue, double targetCal) {
		int[] ySteps = new int[6];
		int height = maxValue / (ySteps.length-1);
		if (targetCal >= maxValue) {
			maxValue = (int) (targetCal * 2);
		}
		height= maxValue / (ySteps.length-1);
		int extra=0;
		if (height >= 0 && height < 100) {
			extra = 10;
			height = height / 10 * 10 + extra;
		} else if (height >= 100 && height < 1000) {
			extra = 50;
			height = height / 100 * 100 + extra;
		} else if (height >= 1000 && height < 10000) {
			extra = 500;
			height = height / 1000 * 1000 + extra;
		} else if (height >= 10000 && height < 100000) {
			extra = 5000;
			height = height / 10000 * 10000 + extra;
		} else {
			extra = 50000;
			height = height / 100000 * 100000 + extra;
		}

		for (int i = 0; i < ySteps.length; i++) {
			ySteps[i] = height * (ySteps.length - 1 - i);
		}
		return ySteps;
	}

//	private int[] getYSteps(int maxValue, double targetCal) {
//		int[] ySteps = new int[6];
//
//		int height = maxValue / (ySteps.length);
//		maxValue += height;
//
//		if (targetCal >= maxValue) {
//			maxValue = (int) (targetCal * 2);
//		}
//		int extra = 0;
//		height = maxValue / (ySteps.length);
//		if (height >= 0 && height < 100) {
//			extra = 10;
//			height = height / 10 * 10 + extra;
//		} else if (height >= 100 && height < 1000) {
//			extra = 100;
//			height = height / 100 * 100 + extra;
//		} else if (height >= 1000 && height < 10000) {
//			extra = 1000;
//			height = height / 1000 * 1000 + extra;
//		} else if (height >= 10000 && height < 100000) {
//			extra = 10000;
//			height = height / 10000 * 10000 + extra;
//		} else {
//			extra = 100000;
//			height = height / 100000 * 100000 + extra;
//		}
//
//		for (int i = 0; i < ySteps.length; i++) {
//			ySteps[i] = height * (ySteps.length - 1 - i);
//		}
//		return ySteps;
//	}

	boolean isShowNext = false;
	AnimationListener anim = new AnimationListener() {

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animation animation) {
			HistogramView hs = (HistogramView) viewFlipper.getCurrentView();
			hs.start(2);
			buildBottomView(hs.getDataList());
			if (!isShowNext) {
				hs = (HistogramView) viewFlipper.getChildAt(viewFlipper
						.getDisplayedChild() + 1);
				hs.clear();

			} else {
				hs = (HistogramView) viewFlipper.getChildAt(viewFlipper
						.getDisplayedChild() - 1);
				hs.clear();
			}
			// onDayClick(hs.getDataList().get(hs.getDataList().size()-1));

		}
	};
	
	
	
	
	
	private String sportName;
	private int sporttype;
	protected void btnGetDatas(OnceSportData str, int i) {
		if(isShowingProgressDialog())
			return ;
		showProgress(this,true);
		Log.v("MyLog", i + "------------" + str.getSportType());
		sporttype = str.getSportType();
		sportName = str.getSportName();
		switch (str.getSportType()) {
		case SportType.HKSportTypePaoBuJi: // !<跑步机
			sportDao.GetLatestPaoBuJiData(str.getOnceId());
			break;
		case SportType.HKSportTypeBuXing:// !<步行
			sportDao.GetLatestBuXingData(str.getOnceId());
			break;
		case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
			sportDao.GetLatestYouYangWuDaoData(str.getOnceId());
			break;
		case SportType.HKSportTypeDongGanDanChe:// !<动感单车
			sportDao.GetLatestDongGanDanCheData(str.getOnceId());
			break;
		case SportType.HKSportTypeGangLingCao:// !<杠铃操
			sportDao.GetLatestGangLingCaoData(str.getOnceId());
			break;
		case SportType.HKSportTypeBoJiCao:// !<搏击操
			sportDao.GetLatestBoJiCaoData(str.getOnceId());
			break;
		case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
			sportDao.GetLatestTuoYuanJiData(str.getOnceId());
			break;
		case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
			sportDao.GetLatestHuaTingLaLiData(str.getOnceId());
			break;
		case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
			sportDao.GetLatestJianBangHouZhanData(str.getOnceId());
			break;
		case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
			sportDao.GetLatestLaLiBeiJiData(str.getOnceId());
			break;
		case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
			sportDao.GetLatestBeiJiShenZhanData(str.getOnceId());
			break;
		case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
			sportDao.GetLatestZuoShiFeiNiaoData(str.getOnceId());
			break;
		case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
			sportDao.GetLatestZuoShiTiXiData(str.getOnceId());
			break;
		case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
			sportDao.GetLatestZuoShiDaTuiShenZhanData(str.getOnceId());
			break;
		case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
			sportDao.GetLatestZuoShiHouTuiQuShenData(str.getOnceId());
			break;
		case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
			sportDao.GetLatestDaTuiWaiCeJiData(str.getOnceId());
			break;
		case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器
			sportDao.GetLatestDaTuiNeiCeJiData(str.getOnceId());
			break;
		case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
			sportDao.GetLatestJianBangTuiJuData(str.getOnceId());
			break;
		case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器
			sportDao.GetLatestShuangXiangTuiXiongData(str.getOnceId());
			break;
		case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器
			sportDao.GetLatestHuDieShiKuoXiongData(str.getOnceId());
			break;
		case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
			sportDao.GetLatestWoShiTuiQuZhanData(str.getOnceId());
			break;
		case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器
			sportDao.GetLatestErTouJiShuangXiangData(str.getOnceId());
			break;
		case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
			sportDao.GetLatestSanTouJiShuangXiangData(str.getOnceId());
			break;
		case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
			sportDao.GetLatestLiShiDaTuiShenZhanData(str.getOnceId());
			break;
		case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
			sportDao.GetLatestTiaoJieDengTuiData(str.getOnceId());
			break;
		case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
			sportDao.GetLatestLiShiDaTuiQuShenData(str.getOnceId());
			break;
		case SportType.HKSportTypeYouYong: // !<游泳
			sportDao.GetLatestYouYongData(str.getOnceId());
			break;
		case SportType.HKSportTypeJianshencao:// !<健身操
			sportDao.getJianShenCaoOnceData(str.getOnceId());
			break;
		default:
			break;
		}

	}

}
