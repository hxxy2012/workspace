package com.hike.digitalgymnastic;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.hike.digitalgymnastic.entitiy.BodeStateType;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.HomeBodyData;
import com.hike.digitalgymnastic.entitiy.PeriodBmiData;
import com.hike.digitalgymnastic.entitiy.PeriodDanbaizhiData;
import com.hike.digitalgymnastic.entitiy.PeriodFubufeipanglvData;
import com.hike.digitalgymnastic.entitiy.PeriodJiroulvData;
import com.hike.digitalgymnastic.entitiy.PeriodNeizangzhifangData;
import com.hike.digitalgymnastic.entitiy.PeriodShuifenData;
import com.hike.digitalgymnastic.entitiy.PeriodTizhongData;
import com.hike.digitalgymnastic.entitiy.PeriodZhifanglvData;
import com.hike.digitalgymnastic.entitiy.TizhongData;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.HistoryBodyCheckView;
import com.hike.digitalgymnastic.view.HistoryBodyCheckView.OnTouchWeekListener;
import com.hike.digitalgymnastic.view.HistoryBodyCheckView.Type;
import com.hike.digitalgymnastic.view.MutexImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/*
 * @Auth changqi
 * 
 */

@ContentView(R.layout.activity_history_bodycheck)
public class HistoryBodyActivity extends BluetoothActivity implements
		OnTouchWeekListener {
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.btn_right)
	ImageView btn_right;
	@ViewInject(R.id.title)
	TextView tv_title;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	// @ViewInject(R.id.tv_date)
	// TextView tv_date;
	@ViewInject(R.id.viewFlipper)
	ViewFlipper viewFlipper;
	@ViewInject(R.id.tv_weekadd)
	TextView tv_weekadd;
	@ViewInject(R.id.tv_max_record)
	TextView tv_max_record;
	@ViewInject(R.id.tv_min_record)
	TextView tv_min_record;

	@ViewInject(R.id.ll_blank)
	LinearLayout ll_blank;
	@ViewInject(R.id.ll_alert)
	LinearLayout ll_alert;
	@ViewInject(R.id.tv_nodata_alert)
	TextView tv_nodata_alert;
	@ViewInject(R.id.iv_add_weight)
	Button iv_add_weight;

	@ViewInject(R.id.iv_tizhong)
	MutexImageView iv_tizhong;
	@ViewInject(R.id.iv_tizhizhishu)
	MutexImageView iv_tizhizhishu;
	@ViewInject(R.id.iv_shentizhifanglv)
	MutexImageView iv_shentizhifanglv;
	@ViewInject(R.id.iv_neizangzhifangshuiping)
	MutexImageView iv_neizangzhifangshuiping;
	@ViewInject(R.id.iv_fubufeipanglv)
	MutexImageView iv_fubufeipanglv;
	@ViewInject(R.id.iv_jiroulv)
	MutexImageView iv_jiroulv;
	@ViewInject(R.id.iv_shentishuifen)
	MutexImageView iv_shentishuifen;
	@ViewInject(R.id.iv_danbaizhi)
	MutexImageView iv_danbaizhi;

	private BaseDao dao;
	private Type currentType = Type.tizhong;
	private boolean clickable = true;

	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left,  R.id.iv_tizhong, R.id.iv_tizhizhishu,
			R.id.iv_shentizhifanglv, R.id.iv_neizangzhifangshuiping,
			R.id.iv_fubufeipanglv, R.id.iv_jiroulv, R.id.iv_shentishuifen,
			R.id.iv_danbaizhi, R.id.iv_add_weight })
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			finish();
			break;
		case R.id.ll_btn_left:
			finish();
			break;
			
		case R.id.iv_add_weight:
			Intent intent = new Intent(this, PersonalInformationActivity.class);
			startActivityForResult(intent, 0);
			break;
		case R.id.iv_tizhong:
			if (!iv_tizhong.isChecked()&&clickable) {
				tv_title.setText("体重");
				removeAnimation();
				restoreImageViewState(iv_tizhong);
				currentType = Type.tizhong;
				getData();

			}
			break;
		case R.id.iv_tizhizhishu:
			if (!iv_tizhizhishu.isChecked() &&clickable) {
				tv_title.setText("体质指数");
				removeAnimation();
				restoreImageViewState(iv_tizhizhishu);
				currentType = Type.tizhizhishu;
				getData();
			}
			break;
		case R.id.iv_shentizhifanglv:
			if (!iv_shentizhifanglv.isChecked() &&clickable) {
				tv_title.setText("身体脂肪率");
				removeAnimation();
				restoreImageViewState(iv_shentizhifanglv);
				currentType = Type.shentizhifanglv;
				getData();
			}
			break;
		case R.id.iv_neizangzhifangshuiping:
			if (!iv_neizangzhifangshuiping.isChecked()&&clickable ) {
				tv_title.setText("内脏脂肪水平");
				removeAnimation();
				restoreImageViewState(iv_neizangzhifangshuiping);
				currentType = Type.neizangzhifangshuiping;
				getData();
			}
			break;
		case R.id.iv_fubufeipanglv:
			if (!iv_fubufeipanglv.isChecked() &&clickable) {
				tv_title.setText("腹部肥胖率");
				removeAnimation();
				restoreImageViewState(iv_fubufeipanglv);
				currentType = Type.fubufeipanglv;
				getData();
			}
			break;
		case R.id.iv_jiroulv:
			if (!iv_jiroulv.isChecked() &&clickable) {
				tv_title.setText("肌肉率");
				removeAnimation();
				restoreImageViewState(iv_jiroulv);
				currentType = Type.jiroulv;
				getData();
			}
			break;
		case R.id.iv_shentishuifen:
			if (!iv_shentishuifen.isChecked()&&clickable ) {
				tv_title.setText("身体水分");
				removeAnimation();
				restoreImageViewState(iv_shentishuifen);
				currentType = Type.shentishuifen;
				getData();
			}
			break;
		case R.id.iv_danbaizhi:
			if (!iv_danbaizhi.isChecked() &&clickable) {
				tv_title.setText("蛋白质质量比");
				removeAnimation();
				restoreImageViewState(iv_danbaizhi);
				currentType = Type.danbaizhizhiliangbi;
				getData();
			}
			break;
		default:
			break;
		}
	}

	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;

	// List<View> viewList = new ArrayList<View>();
	// List<Object> dataList = new ArrayList<Object>();
	String suffix_kg = "kg";
	String suffix_100 = "%";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
		getData();
	}

	HomeBodyData bhd;

	private void init() {
		initImage();// 初始化底部的按钮
		dao = new BaseDao(this, this);

		bhd = (HomeBodyData) getIntent().getSerializableExtra("bhd");
		iv_tizhong.setChecked(true);
		tv_title.setText("体重");
		// tv_date.setText("2015-06-22");
		// tv_date.setVisibility(View.GONE);
		btn_left.setImageResource(R.mipmap.toolbar_back);
		// 动画效果
		leftInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_enter);
		leftOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_out);
		rightInAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this,
				R.anim.next_view_out);

		addNewItemView();
	}

	// 有数据的显示视图
	private void addNewItemView() {
		HistoryBodyCheckView hbcv0 = new HistoryBodyCheckView(this);
		hbcv0.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv0);
		HistoryBodyCheckView hbcv1 = new HistoryBodyCheckView(this);
		hbcv1.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv1);
		HistoryBodyCheckView hbcv2 = new HistoryBodyCheckView(this);
		hbcv2.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv2);
		HistoryBodyCheckView hbcv3 = new HistoryBodyCheckView(this);
		hbcv3.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv3);
		HistoryBodyCheckView hbcv4 = new HistoryBodyCheckView(this);
		hbcv4.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv4);
		HistoryBodyCheckView hbcv5 = new HistoryBodyCheckView(this);
		hbcv5.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv5);
		HistoryBodyCheckView hbcv6 = new HistoryBodyCheckView(this);
		hbcv6.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv6);
		HistoryBodyCheckView hbcv7 = new HistoryBodyCheckView(this);
		hbcv7.setOnTouchWeekListener(this);
		viewFlipper.addView(hbcv7);
	}

	
	private void getData() {

		buildBlankView();
		
		showProgress(true);
		new Thread(new Runnable() {
			Message msg=new Message();
			@Override
			public void run() {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String startDate = format.format(Utils.dateAdd(-55));
				String endDate = format.format(Utils.dateAdd(-1));
				boolean isLasted = false;
				if (currentType == Type.tizhong) {
					isLasted = dao.GetPeriodTizhongDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodTizhongData periodTizhongData = dao
								.getPeriodTizhongData();
						if (bhd.getTizhongData() == null)
							periodTizhongData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodTizhongData.getDataMap().put(
									format.format(new Date()),
									bhd.getTizhongData().getValue());
//						buildView(periodTizhongData);
						msg.obj=periodTizhongData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.tizhizhishu) {
					isLasted = dao.GetPeriodBmiDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodBmiData periodBmiData = dao.getPeriodBmiData();
						if (bhd.getBmiData() == null)
							periodBmiData.getDataMap().put(format.format(new Date()),
									(double) 0);
						else
							periodBmiData.getDataMap().put(format.format(new Date()),
									bhd.getBmiData().getValue());
//						buildView(periodBmiData);
						msg.obj=periodBmiData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.shentizhifanglv) {
					isLasted = dao.GetPeriodZhifanglvDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodZhifanglvData periodZhifanglvData = dao
								.getPeriodZhifanglvData();
						if (bhd.getZhifanglvData() == null)
							periodZhifanglvData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodZhifanglvData.getDataMap().put(
									format.format(new Date()),
									bhd.getZhifanglvData().getValue());
//						buildView(periodZhifanglvData);
						msg.obj=periodZhifanglvData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.neizangzhifangshuiping) {
					isLasted = dao
							.GetPeriodNeizangzhifangDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodNeizangzhifangData periodNeizangzhifangData = dao
								.getPeriodNeizangzhifangData();
						if (bhd.getNeizangzhifangData() == null)
							periodNeizangzhifangData.getDataMap().put(
									format.format(new Date()), 0);
						else
							periodNeizangzhifangData.getDataMap().put(
									format.format(new Date()),
									bhd.getNeizangzhifangData().getValue());
//						buildView(periodNeizangzhifangData);
						msg.obj=periodNeizangzhifangData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.fubufeipanglv) {
					isLasted = dao.GetPeriodFubufeipanglvDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodFubufeipanglvData periodFubufeipanglvData = dao
								.getPeriodFubufeipanglvData();

						if (bhd.getFubufeipanglvData() == null)
							periodFubufeipanglvData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodFubufeipanglvData.getDataMap().put(
									format.format(new Date()),
									bhd.getFubufeipanglvData().getValue());
//						buildView(periodFubufeipanglvData);
						msg.obj=periodFubufeipanglvData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.jiroulv) {
					isLasted = dao.GetPeriodJiroulvDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodJiroulvData periodJiroulvData = dao
								.getPeriodJiroulvData();
						if (bhd.getJiroulvData() == null)
							periodJiroulvData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodJiroulvData.getDataMap().put(
									format.format(new Date()),
									bhd.getJiroulvData().getValue());
//						buildView(periodJiroulvData);
						msg.obj=periodJiroulvData;
						handler.sendMessage(msg);
					}
				} else if (currentType == Type.shentishuifen) {
					isLasted = dao.GetPeriodShuifenDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodShuifenData periodShuifenData = dao
								.getPeriodShuifenData();
						if (bhd.getShuifenData() == null)
							periodShuifenData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodShuifenData.getDataMap().put(
									format.format(new Date()),
									bhd.getShuifenData().getValue());
//						buildView(periodShuifenData);
						msg.obj=periodShuifenData;
						handler.sendMessage(msg);

					}
				} else if (currentType == Type.danbaizhizhiliangbi) {
					isLasted = dao.GetPeriodDanbaizhiDataFromDB(startDate, endDate);
					if (isLasted) {
						PeriodDanbaizhiData periodDanbaizhiData = dao
								.getPeriodDanbaizhiData();
						if (bhd.getDanbaizhiData() == null)
							periodDanbaizhiData.getDataMap().put(
									format.format(new Date()), (double) 0);
						else
							periodDanbaizhiData.getDataMap().put(
									format.format(new Date()),
									bhd.getDanbaizhiData().getValue());
//						buildView(periodDanbaizhiData);
						msg.obj=periodDanbaizhiData;
						handler.sendMessage(msg);
					}
				}
				
			}
		}).start();
		
	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			;
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			showProgress(false);
			buildView(msg.obj);
		}

	};

	@Override
	public void onRequestFaild(String errorNo, String errorMessage) {
		// TODO Auto-generated method stub
		super.onRequestFaild(errorNo, errorMessage);

	}

	@Override
	public void showProgress(boolean show) {
		// TODO Auto-generated method stub
		super.showProgress(show);
	}

	@Override
	public void showProgressWithText(boolean show, String message) {
		// TODO Auto-generated method stub
		super.showProgressWithText(show, message);
	}

	@Override
	public void onRequestSuccess(int requestCode) {
		// TODO Auto-generated method stub
		super.onRequestSuccess(requestCode);
		showProgress(false);
		if (requestCode == 1000) {
			updateLoacalInfo();
		} else {
			onResponseReceived();
		}
	}

	private void updateLoacalInfo() {
		// 更新本地
		Customer cusomter = LocalDataUtils.readCustomer(this);
		cusomter.weight = String.valueOf(weightValue);
		LocalDataUtils.saveCustomer(this, cusomter);
		SharedPreferences shp = getSharedPreferences("body",
				Context.MODE_WORLD_WRITEABLE);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String json_body = shp.getString(format.format(new Date()), null);
		if (!TextUtils.isEmpty(json_body)) {
			Gson gson = new Gson();
			// HomeBodyData homeBodyData = gson.fromJson(json_body,
			// HomeBodyData.class);
			HomeBodyData hbd = gson.fromJson(json_body, HomeBodyData.class);
			if (hbd.getTizhongData() != null)
				hbd.getTizhongData().setValue(weightValue);
			else {
				TizhongData td = new TizhongData();
				td.setValue(weightValue);
				hbd.setTizhongData(td);
			}
			json_body = gson.toJson(hbd, HomeBodyData.class);
			Editor editor = shp.edit();
			editor.clear();
			editor.putString(format.format(new Date()), json_body);
			editor.commit();
		}

	}

	private void onResponseReceived() {
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		if (currentType == Type.tizhong) {
			PeriodTizhongData periodTizhongData = dao.getPeriodTizhongData();
			if (bhd.getTizhongData() == null) {
				periodTizhongData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodTizhongData.getDataMap().put(format.format(new Date()),
						bhd.getTizhongData().getValue());
			}
			buildView(periodTizhongData);
		} else if (currentType == Type.tizhizhishu) {
			PeriodBmiData periodBmiData = dao.getPeriodBmiData();
			if (bhd.getBmiData() == null) {
				periodBmiData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodBmiData.getDataMap().put(format.format(new Date()),
						bhd.getBmiData().getValue());
			}
			buildView(periodBmiData);
		} else if (currentType == Type.shentizhifanglv) {
			PeriodZhifanglvData periodZhifanglvData = dao
					.getPeriodZhifanglvData();
			if (bhd.getZhifanglvData() == null) {
				periodZhifanglvData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodZhifanglvData.getDataMap().put(format.format(new Date()),
						bhd.getZhifanglvData().getValue());
			}
			buildView(periodZhifanglvData);
		} else if (currentType == Type.neizangzhifangshuiping) {
			PeriodNeizangzhifangData periodNeizangzhifangData = dao
					.getPeriodNeizangzhifangData();
			if (bhd.getNeizangzhifangData() == null) {
				periodNeizangzhifangData.getDataMap().put(
						format.format(new Date()), 0);
			} else {
				periodNeizangzhifangData.getDataMap().put(
						format.format(new Date()),
						bhd.getNeizangzhifangData().getValue());
			}
			buildView(periodNeizangzhifangData);
		} else if (currentType == Type.fubufeipanglv) {
			PeriodFubufeipanglvData periodFubufeipanglvData = dao
					.getPeriodFubufeipanglvData();
			if (bhd.getFubufeipanglvData() == null) {
				periodFubufeipanglvData.getDataMap().put(
						format.format(new Date()), (double) 0);
			} else {
				periodFubufeipanglvData.getDataMap().put(
						format.format(new Date()),
						bhd.getFubufeipanglvData().getValue());
			}
			buildView(periodFubufeipanglvData);
		} else if (currentType == Type.jiroulv) {
			PeriodJiroulvData periodJiroulvData = dao.getPeriodJiroulvData();
			if (bhd.getJiroulvData() == null) {
				periodJiroulvData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodJiroulvData.getDataMap().put(format.format(new Date()),
						bhd.getJiroulvData().getValue());
			}
			buildView(periodJiroulvData);
		} else if (currentType == Type.shentishuifen) {
			PeriodShuifenData periodShuifenData = dao.getPeriodShuifenData();
			if (bhd.getShuifenData() == null) {
				periodShuifenData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodShuifenData.getDataMap().put(format.format(new Date()),
						bhd.getShuifenData().getValue());
			}
			buildView(periodShuifenData);
		} else if (currentType == Type.danbaizhizhiliangbi) {
			PeriodDanbaizhiData periodDanbaizhiData = dao
					.getPeriodDanbaizhiData();
			if (bhd.getDanbaizhiData() == null) {
				periodDanbaizhiData.getDataMap().put(format.format(new Date()),
						(double) 0);
			} else {
				periodDanbaizhiData.getDataMap().put(format.format(new Date()),
						bhd.getDanbaizhiData().getValue());
			}
			buildView(periodDanbaizhiData);
		}

	}

	List<Object> dataList = new ArrayList<Object>();

	private void buildBlankView() {
		HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
				.getChildAt(7);
		viewFlipper.setDisplayedChild(7);
		hbcv.setOnTouchWeekListener(null);
		hbcv.init(currentType, hbcv, -100);
		onBlankPageSelected();
	}

	// 创建视图
	private void buildView(Object object) {
		dataList.clear();
		HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
				.getChildAt(viewFlipper.getChildCount() - 1);
		hbcv.setOnTouchWeekListener(this);
		// 体重
		if (object != null && PeriodTizhongData.class.equals(object.getClass())) {
			PeriodTizhongData periodTizhongData = (PeriodTizhongData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodTizhongData
					.getDataMap();

			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodTizhongData ptd = new PeriodTizhongData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodTizhongData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodTizhongData ptd = (PeriodTizhongData) dataList.get(7);
			hbcv.init(Type.tizhong, ptd);

			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (ptd.getDataMap()), "体重",
					suffix_kg);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);

		}
		// 身体水分
		if (object != null && PeriodShuifenData.class.equals(object.getClass())) {
			PeriodShuifenData periodShuifenData = (PeriodShuifenData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodShuifenData
					.getDataMap();

			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodShuifenData ptd = new PeriodShuifenData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodShuifenData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodShuifenData psd = (PeriodShuifenData) dataList.get(7);
			hbcv.init(Type.shentishuifen, psd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()), "身体水分",
					suffix_100);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}
		// 蛋白质
		if (object != null
				&& PeriodDanbaizhiData.class.equals(object.getClass())) {
			PeriodDanbaizhiData periodDanbaizhiData = (PeriodDanbaizhiData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodDanbaizhiData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodDanbaizhiData ptd = new PeriodDanbaizhiData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodDanbaizhiData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodDanbaizhiData pbd = (PeriodDanbaizhiData) dataList.get(7);
			hbcv.init(Type.danbaizhizhiliangbi, pbd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (pbd.getDataMap()), "蛋白质",
					suffix_100);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}

		// 腹部肥胖率
		if (object != null
				&& PeriodFubufeipanglvData.class.equals(object.getClass())) {
			PeriodFubufeipanglvData periodFubufeipanglvData = (PeriodFubufeipanglvData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodFubufeipanglvData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodFubufeipanglvData ptd = new PeriodFubufeipanglvData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodFubufeipanglvData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodFubufeipanglvData pbd = (PeriodFubufeipanglvData) dataList
					.get(7);
			hbcv.init(Type.fubufeipanglv, pbd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (pbd.getDataMap()),
					"腹部肥胖率", suffix_100);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}

		// 身体指数
		if (object != null && PeriodBmiData.class.equals(object.getClass())) {
			PeriodBmiData periodBmiData = (PeriodBmiData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodBmiData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodBmiData ptd = new PeriodBmiData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodBmiData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodBmiData pbd = (PeriodBmiData) dataList.get(7);
			hbcv.init(Type.tizhizhishu, pbd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (pbd.getDataMap()), "体质指数",
					"");
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}
		// 脂肪率
		if (object != null
				&& PeriodZhifanglvData.class.equals(object.getClass())) {
			PeriodZhifanglvData periodZhifanglvData = (PeriodZhifanglvData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodZhifanglvData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodZhifanglvData ptd = new PeriodZhifanglvData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodZhifanglvData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodZhifanglvData pbd = (PeriodZhifanglvData) dataList.get(7);
			hbcv.init(Type.shentizhifanglv, pbd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (pbd.getDataMap()),
					"身体脂肪率", suffix_100);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}
		// 内脏脂肪率
		if (object != null
				&& PeriodNeizangzhifangData.class.equals(object.getClass())) {
			PeriodNeizangzhifangData periodNeizangzhifangData = (PeriodNeizangzhifangData) object;
			LinkedHashMap<String, Integer> dataMap = (LinkedHashMap<String, Integer>) periodNeizangzhifangData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Integer> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodNeizangzhifangData ptd = new PeriodNeizangzhifangData();
					map = new LinkedHashMap<String, Integer>();
					ptd.setStandard(periodNeizangzhifangData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodNeizangzhifangData pbd = (PeriodNeizangzhifangData) dataList
					.get(7);
			hbcv.init(Type.neizangzhifangshuiping, pbd);
			getMaxAndMinData1(
					(LinkedHashMap<String, Integer>) (pbd.getDataMap()),
					"内脏脂肪率", "");
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}

		// 肌肉率
		if (object != null && PeriodJiroulvData.class.equals(object.getClass())) {
			PeriodJiroulvData periodJiroulvData = (PeriodJiroulvData) object;
			LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodJiroulvData
					.getDataMap();
			Set<String> keySet = dataMap.keySet();
			int i = 0;
			LinkedHashMap<String, Double> map = null;
			for (String key : keySet) {
				if (i % 7 == 0) {
					PeriodJiroulvData ptd = new PeriodJiroulvData();
					map = new LinkedHashMap<String, Double>();
					ptd.setStandard(periodJiroulvData.getStandard());
					ptd.setDataMap(map);
					dataList.add(ptd);
				}
				map.put(key, dataMap.get(key));
				i++;
			}

			hbcv = (HistoryBodyCheckView) viewFlipper.getChildAt(7);
			PeriodJiroulvData pbd = (PeriodJiroulvData) dataList.get(7);
			hbcv.init(Type.jiroulv, pbd);
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (pbd.getDataMap()), "肌肉率",
					suffix_100);
			viewFlipper.setDisplayedChild(7);
			onPageSelected(7);
		}
		
		clickable=true;
	}

	private void setWeekAddTextValue(String type, String value, String suffix) {

		StringBuilder builder = new StringBuilder();
		builder.append("本周" + type + value + suffix + "，");

		String desc_week_added = builder.toString();
		SpannableString sp = new SpannableString(desc_week_added);
		sp.setSpan(new AbsoluteSizeSpan(20, true), 5 + type.length(),
				desc_week_added.length() - suffix.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(12, true), desc_week_added.length()
				- suffix.length(), desc_week_added.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_weekadd.setTextColor(getResources().getColor(
				R.color.time_color_normal));
		tv_weekadd.setText(sp);

	}

	private void setMinRecore(String mm, String dd, String value, String suffix) {
		String desc_max_record = "本周最低纪录为" + mm + "月" + dd + "日，为" + value
				+ suffix;
		SpannableString sp = new SpannableString(desc_max_record);
		sp.setSpan(new AbsoluteSizeSpan(20, true), 15, desc_max_record.length()
				- suffix.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(12, true), desc_max_record.length()
				- suffix.length(), desc_max_record.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_min_record.setTextColor(getResources().getColor(
				R.color.colorCircleID));
		tv_min_record.setText(sp);
	}

	private void setMaxRecore(String mm, String dd, String value, String suffix) {
		String desc_max_record = "本周最高纪录为" + mm + "月" + dd + "日，为" + value
				+ suffix;
		SpannableString sp = new SpannableString(desc_max_record);
		sp.setSpan(new AbsoluteSizeSpan(20, true), 15, desc_max_record.length()
				- suffix.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		sp.setSpan(new AbsoluteSizeSpan(12, true), desc_max_record.length()
				- suffix.length(), desc_max_record.length(),
				Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		tv_max_record.setTextColor(getResources().getColor(R.color.banlkid));
		tv_max_record.setText(sp);
	}

	private void initImage() {
		iv_tizhong.initImageSrcID(R.mipmap.icon_tizhong_normal,
				R.mipmap.icon_tizhong_selected);
		iv_tizhizhishu.initImageSrcID(R.mipmap.icon_tizhizhishu_normal,
				R.mipmap.icon_tizhizhishu_pressed);
		iv_shentizhifanglv.initImageSrcID(R.mipmap.icon_zhifanglv_normal,
				R.mipmap.icon_zhifanglv_pressed);
		iv_neizangzhifangshuiping.initImageSrcID(
				R.mipmap.icon_neizangzhifang_normal,
				R.mipmap.icon_neizangzhifang_selected);
		iv_fubufeipanglv.initImageSrcID(R.mipmap.icon_fubufeipang_normal,
				R.mipmap.icon_fubufeipang_pressed);
		iv_jiroulv.initImageSrcID(R.mipmap.icon_jiroulv_normal,
				R.mipmap.icon_jiroulv_pressed);
		iv_shentishuifen.initImageSrcID(R.mipmap.icon_shuifen_normal,
				R.mipmap.icon_shuifen_selected);
		iv_danbaizhi.initImageSrcID(R.mipmap.icon_danbaizhi_normal,
				R.mipmap.icon_danbaizhi_selected);
	}

	/*
	 * 重置底部按钮状态
	 */
	private void restoreImageViewState(MutexImageView mutexImageView) {
		iv_tizhong.setChecked(false);
		iv_tizhizhishu.setChecked(false);
		iv_shentizhifanglv.setChecked(false);
		iv_neizangzhifangshuiping.setChecked(false);
		iv_fubufeipanglv.setChecked(false);
		iv_jiroulv.setChecked(false);
		iv_shentishuifen.setChecked(false);
		iv_danbaizhi.setChecked(false);

		mutexImageView.setChecked(true);
		clickable=false;
	}

	/*
	 * 设置最大最小值显示页面
	 */
	private void getMaxAndMinData(LinkedHashMap<String, Double> dataMap,
			String type, String suffix) {
		Set<String> keySet = dataMap.keySet();
		double maxValue = 0;
		String maxDate = "";
		double minValue = 1000000;
		String minDate = "";
		int i = 0;
		double changValue = 0;
		DecimalFormat decimalFormat = new DecimalFormat(".#");
		for (String key : keySet) {

			if (i == 0) {
				changValue = Math.abs(dataMap.get(key));
			} else if (i == 6) {
				changValue = Math.abs(dataMap.get(key)) - changValue;
			}
			changValue = Double.parseDouble(decimalFormat.format(changValue));
			if (dataMap.get(key) != 0) {
				if (maxValue <= Math.abs(dataMap.get(key))) {
					maxValue = Math.abs(dataMap.get(key));
					maxDate = key;
				}
				if (minValue >= Math.abs(dataMap.get(key))) {
					minValue = Math.abs(dataMap.get(key));
					minDate = key;
				}
			}
			i++;
		}
//		if (changValue < 0)
//			setWeekAddTextValue(type,
//					"减少了" + String.valueOf(Math.abs(changValue)), suffix);
//		else if (changValue > 0)
//			setWeekAddTextValue(type,
//					"增加了" + String.valueOf(Math.abs(changValue)), suffix);
//		else
//			setWeekAddTextValue(type, "无变化", "");
		HistoryBodyCheckView hbcv=	(HistoryBodyCheckView) viewFlipper.getCurrentView();
		if(hbcv.getObject()!=null){
			tv_weekadd.setText(getTishiMessage(hbcv));
		}
		

		try {
			if (maxDate.length() > 0) {

				String mm = maxDate.split("-", -1)[1];
				String dd = maxDate.split("-", -1)[2];
				setMaxRecore(mm, dd, balnkchar+String.valueOf(maxValue), suffix);
			}
			if (minDate.length() > 0) {
				String mm = minDate.split("-", -1)[1];
				String dd = minDate.split("-", -1)[2];
				setMinRecore(mm, dd, balnkchar+String.valueOf(minValue), suffix);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 设置最大最小值显示页面
	 */
	private void getMaxAndMinData1(LinkedHashMap<String, Integer> dataMap,
			String type, String suffix) {
		Set<String> keySet = dataMap.keySet();
		int maxValue = 0;
		String maxDate = "";
		int minValue = 1000000;
		String minDate = "";
		int i = 0;
		int changValue = 0;
		for (String key : keySet) {

			if (i == 0) {
				changValue = Math.abs(dataMap.get(key));
			} else if (i == 6) {
				changValue = Math.abs(dataMap.get(key)) - changValue;
			}
			if (dataMap.get(key) != 0) {
				if (maxValue <= Math.abs(dataMap.get(key))) {
					maxValue = Math.abs(dataMap.get(key));
					maxDate = key;
				}
				if (minValue >= Math.abs(dataMap.get(key))) {
					minValue = Math.abs(dataMap.get(key));
					minDate = key;
				}
			}
			i++;
		}

//		if (changValue < 0)
//			setWeekAddTextValue(type,
//					"减少了" + String.valueOf(Math.abs(changValue)), suffix);
//		else if (changValue > 0)
//			setWeekAddTextValue(type,
//					"增加了" + String.valueOf(Math.abs(changValue)), suffix);
//		else
//			setWeekAddTextValue(type, "无变化", "");
		HistoryBodyCheckView hbcv=	(HistoryBodyCheckView) viewFlipper.getCurrentView();
		if(hbcv.getObject()!=null){
			tv_weekadd.setText(getTishiMessage(hbcv));
		}
		
		try {
			if (maxDate.length() > 0) {

				String mm = maxDate.split("-", -1)[1];
				String dd = maxDate.split("-", -1)[2];
				setMaxRecore(mm, dd, balnkchar+String.valueOf(maxValue), suffix);
			}
			if (minDate.length() > 0) {
				String mm = minDate.split("-", -1)[1];
				String dd = minDate.split("-", -1)[2];
				setMinRecore(mm, dd, balnkchar+String.valueOf(minValue), suffix);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * 移除动画效果
	 */
	private void removeAnimation() {
		viewFlipper.setInAnimation(null);
		viewFlipper.setOutAnimation(null);

	}

	/*
	 * 显示前一页
	 */
	private void showPreviousView() {
		viewFlipper.setInAnimation(leftInAnimation);
		viewFlipper.setOutAnimation(rightOutAnimation);
		viewFlipper.showPrevious();// 向左滑动
		onPageSelected(viewFlipper.getDisplayedChild());

	}

	/*
	 * 显示下一页
	 */
	private void showNextView() {
		viewFlipper.setInAnimation(rightInAnimation);
		viewFlipper.setOutAnimation(leftOutAnimation);
		viewFlipper.showNext();// 向右滑动
		onPageSelected(viewFlipper.getDisplayedChild());

	}

	private void controlerDouble(Map<String, Double> map) {
		if (map != null && map.size() == 7) {
			boolean isBlank = true;
			Set<String> set = map.keySet();
			for (String key : set) {
				if (Math.abs(map.get(key)) > 0) {
					isBlank = false;
				}
			}
			if (isBlank) {
				ll_blank.setVisibility(View.VISIBLE);
				ll_alert.setVisibility(View.INVISIBLE);
			} else {
				ll_blank.setVisibility(View.INVISIBLE);
				ll_alert.setVisibility(View.VISIBLE);
			}

		} else {
			ll_blank.setVisibility(View.VISIBLE);
			ll_alert.setVisibility(View.INVISIBLE);
		}
	}

	private void controlerInteger(Map<String, Integer> map) {
		if (map != null && map.size() == 7) {
			boolean isBlank = true;
			Set<String> set = map.keySet();
			for (String key : set) {
				if (Math.abs(map.get(key)) > 0) {
					isBlank = false;
				}
			}
			if (isBlank) {
				ll_blank.setVisibility(View.VISIBLE);
				ll_alert.setVisibility(View.INVISIBLE);
			} else {
				ll_blank.setVisibility(View.INVISIBLE);
				ll_alert.setVisibility(View.VISIBLE);
			}

		} else {
			ll_blank.setVisibility(View.VISIBLE);
			ll_alert.setVisibility(View.INVISIBLE);
		}
	}

	private void onBlankPageSelected() {
		ll_blank.setVisibility(View.VISIBLE);
		ll_alert.setVisibility(View.INVISIBLE);
		if (currentType == Type.tizhong) {
			tv_nodata_alert.setText("你本周内没有体重记录，快去测量吧！");
			iv_add_weight.setVisibility(View.VISIBLE);
		} else if (currentType == Type.shentishuifen) {
			tv_nodata_alert.setText("你本周内没有身体水分记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		} else if (currentType == Type.tizhizhishu) {
			tv_nodata_alert.setText("你本周内没有体质指数记录，去测量体重吧！");
			iv_add_weight.setVisibility(View.VISIBLE);
		} else if (currentType == Type.shentizhifanglv) {
			tv_nodata_alert.setText("您本周内没有脂肪率记录，快去用体侧仪测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		} else if (currentType == Type.neizangzhifangshuiping) {
			tv_nodata_alert.setText("你本周内没有内脏脂肪水平记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		} else if (currentType == Type.fubufeipanglv) {
			tv_nodata_alert.setText("你本周内没有腹部肥胖率记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		} else if (currentType == Type.jiroulv) {
			tv_nodata_alert.setText("你本周内没有肌肉率记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		} else if (currentType == Type.danbaizhizhiliangbi) {
			tv_nodata_alert.setText("你本周内没有蛋白质记录，快去用测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);
		}

	}

	private void onPageSelected(int pageID) {
		if (currentType == Type.tizhong) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodTizhongData ptd = (PeriodTizhongData) dataList.get(pageID);
			hbcv.init(Type.tizhong, ptd);

			tv_nodata_alert.setText("你本周内没有体重记录，快去测量吧！");
			iv_add_weight.setVisibility(View.VISIBLE);

			controlerDouble(ptd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (ptd.getDataMap()), "体重",
					suffix_kg);
		} else if (currentType == Type.shentishuifen) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodShuifenData psd = (PeriodShuifenData) dataList.get(pageID);
			hbcv.init(Type.shentishuifen, psd);

			tv_nodata_alert.setText("你本周内没有身体水分记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()), "身体水分",
					suffix_100);
		} else if (currentType == Type.tizhizhishu) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodBmiData psd = (PeriodBmiData) dataList.get(pageID);
			hbcv.init(Type.tizhizhishu, psd);

			tv_nodata_alert.setText("你本周内没有体质指数记录，去测量体重吧！");
			iv_add_weight.setVisibility(View.VISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()), "体质指数",
					"");
		} else if (currentType == Type.shentizhifanglv) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodZhifanglvData psd = (PeriodZhifanglvData) dataList
					.get(pageID);
			hbcv.init(Type.shentizhifanglv, psd);

			tv_nodata_alert.setText("您本周内没有脂肪率记录，快去用体侧仪测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()),
					"身体脂肪率", suffix_100);
		} else if (currentType == Type.neizangzhifangshuiping) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodNeizangzhifangData psd = (PeriodNeizangzhifangData) dataList
					.get(pageID);
			hbcv.init(Type.neizangzhifangshuiping, psd);

			tv_nodata_alert.setText("你本周内没有内脏脂肪水平记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerInteger(psd.getDataMap());
			getMaxAndMinData1(
					(LinkedHashMap<String, Integer>) (psd.getDataMap()),
					"内脏脂肪率", "");
		} else if (currentType == Type.fubufeipanglv) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodFubufeipanglvData psd = (PeriodFubufeipanglvData) dataList
					.get(pageID);
			hbcv.init(Type.fubufeipanglv, psd);

			tv_nodata_alert.setText("你本周内没有腹部肥胖率记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()),
					"腹部肥胖率", suffix_100);
		} else if (currentType == Type.jiroulv) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodJiroulvData psd = (PeriodJiroulvData) dataList.get(pageID);
			hbcv.init(Type.jiroulv, psd);

			tv_nodata_alert.setText("你本周内没有肌肉率记录，快去测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()), "肌肉率",
					suffix_100);
		} else if (currentType == Type.danbaizhizhiliangbi) {
			HistoryBodyCheckView hbcv = (HistoryBodyCheckView) viewFlipper
					.getChildAt(pageID);
			PeriodDanbaizhiData psd = (PeriodDanbaizhiData) dataList
					.get(pageID);
			hbcv.init(Type.danbaizhizhiliangbi, psd);

			tv_nodata_alert.setText("你本周内没有蛋白质记录，快去用测量吧！");
			iv_add_weight.setVisibility(View.INVISIBLE);

			controlerDouble(psd.getDataMap());
			getMaxAndMinData(
					(LinkedHashMap<String, Double>) (psd.getDataMap()), "蛋白质",
					suffix_100);
		}
	}

	@Override
	public void onWeekTouch(Object obj1, Object obj2) {
		// TODO Auto-generated method stub

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
			if (viewFlipper.getDisplayedChild() != viewFlipper.getChildCount() - 1) {
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
			}

			break;

		default:
			break;
		}

	}

	double weightValue;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == 0) {
				weightValue = data.getDoubleExtra("value", 100.0);
				dao.ModifyCustomerWeight(weightValue);
			}
		}
	}

	private SpannableString getTishiMessage(HistoryBodyCheckView hbcv) {
		if (currentType == Type.tizhong) {
			return getTiZhongMessage(hbcv);
		}else if(currentType==Type.tizhizhishu){
			return getTiZhizhishuMessage(hbcv);
		}else if(currentType==Type.shentizhifanglv){
			return	getZhifanglvMessage(hbcv);
		}else if(currentType==Type.neizangzhifangshuiping){
			return	getNeiZangZhifanglvMessage(hbcv);
		}else if(currentType==Type.fubufeipanglv){
			return getFubufeipanglvMessage(hbcv);
		}else if(currentType==Type.jiroulv){
			return getJiroulvMessage(hbcv);
		}else if(currentType==Type.shentishuifen){
			return getShuifenMessage(hbcv);
		}else if(currentType==Type.danbaizhizhiliangbi){
			return getDanbaizhiMessage(hbcv);
		}
		
		return null;
		
	}
	String balnkchar=" ";
	private SpannableString  getTiZhongMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.tizhong) {
			PeriodTizhongData ptzd = (PeriodTizhongData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double upLimit = ptzd.getStandard().getNormalCeil();
			double downLimit = ptzd.getStandard().getNormalFloor();

			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//横坐标最右侧日期的体重小于其一周前的体重
				value=balnkchar+format.format((startValue - endValue));
				if (startValue >= downLimit && endValue >= downLimit
						&& startValue < upLimit && endValue < upLimit) {
					//二者均在标准区间内时显示
					builder.append("本周体重减少 " + value
							+ "kg，仍属标准区间");
				} else if (startValue >=downLimit && startValue < upLimit
						&& endValue < downLimit) {
					//当用户体重由达标转为不达标时显示
					builder.append("本周体重减少" +value
							+ "kg，要增加热量摄入哦");
				} else if ((startValue >=upLimit &&endValue >= downLimit&& endValue < upLimit) ||(endValue >=upLimit)) {
					//用户体重由超标转为标准或仍在超标区间内时显示
					builder.append("本周体重减少" + value
							+ "kg，要继续保持哦");
				}else{
					//用户体重由超标转为不达标区间内时显示
					builder.append("本周体重减少" + value
							+ "kg，要增加热量摄入哦");
					
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 6, 6 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 6 + length,
						8 + length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 6, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				value=balnkchar+format.format((endValue - startValue));
				if (startValue >= downLimit && endValue >= downLimit
						&& startValue < upLimit && endValue < upLimit) {
					//二者均在标准区间内时显示
					builder.append("本周体重增加" + value
							+ "kg，仍属标准区间");
				} else if ((startValue < downLimit && endValue >= downLimit&& endValue < upLimit)||(endValue < downLimit)) {
					//当用户体重由不达标转为达标或仍在不达标区间内
					builder.append("本周体重增加" + value
							+ "kg，要继续努力哦");
				} else if (startValue >= downLimit && startValue < upLimit
						&& endValue >= upLimit) {
					//当用户体重由标准转为超标时显示
					builder.append("本周体重增加" + value
							+ "kg，要加强锻炼哦");
				}else{
					//当用户体重由不达标转为超标时显示
					builder.append("本周体重增加" + value
						+ "kg，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 6, 6 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 6 + length,
						8 + length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 6, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				
				
			} else {
				if (startValue >= downLimit && endValue >= downLimit
						&& startValue < upLimit && endValue < upLimit) {
					builder.append("本周体重没有变化，仍属标准区间");
				} else if (startValue < downLimit && endValue < downLimit) {
					builder.append("本周体重没有变化，要增加热量摄入哦");
				} else if (startValue >= upLimit && endValue >= upLimit) {
					builder.append("本周体重没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		} 
		return sp;
	}
	
	private SpannableString  getTiZhizhishuMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.tizhizhishu) {
			PeriodBmiData ptzd = (PeriodBmiData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double normalFloor = ptzd.getStandard().getNormalFloor();
			double heavierFloor = ptzd.getStandard().getHeavierFloor();
			double fatFloor = ptzd.getStandard().getFatFloor();
			double veryFatFloor = ptzd.getStandard().getVeryFatFloor();
			
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {//如果横坐标最右侧日期的用户体质指数小于其一周前的用户体质指数
				value=balnkchar+format.format((startValue - endValue));
				if (startValue >= normalFloor && startValue <heavierFloor
						&&endValue >= normalFloor && endValue <heavierFloor) {
					//二者均在标准区间内（正常和适中）时
					builder.append("本周体质指数减少" + value
							+ "，仍属标准区间");
				} else if (startValue >= normalFloor && startValue <heavierFloor
						&& endValue < normalFloor) {
					//体质指数由达标转为不达标（过轻）时显示
					builder.append("本周体质指数减少" + value
							+ "，要增加热量摄入哦");
				} else if ((startValue >=heavierFloor && (endValue >= normalFloor && endValue <heavierFloor))||endValue>=heavierFloor) {
					//用户体质指数由超标（过重、肥胖、非常肥胖）转为标准时或仍在超标区间内显示
					builder.append("本周体质指数减少" + value
							+ "，要继续保持哦");
				}else{
					//用户体质指数由超标（过重、肥胖、非常肥胖）转为不达标区间内显示
					builder.append("本周体质指数减少" + value
							+ "，要增加热量摄入哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//				sp.setSpan(new AbsoluteSizeSpan(12, true), 8 + length,
//						8 + length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {//如果横坐标最右侧日期的体质指数大于其一周前的体质指数
				value=balnkchar+format.format((endValue - startValue));
				if (startValue >= normalFloor && startValue <heavierFloor
						&&endValue >= normalFloor && endValue <heavierFloor) {
					//者均在标准区间内时显示为
					builder.append("本周体质指数增加" + value
							+ "，仍属标准区间");
				} else if ((startValue < normalFloor &&(endValue >= normalFloor && endValue <heavierFloor))|| endValue <normalFloor) {
					//当用户体质指数由不达标转为达标或仍在不达标区间内时显示为
					builder.append("本周体质指数增加" + value
							+ "，要继续努力哦");
				} else if (((startValue >= normalFloor && startValue <heavierFloor)&&endValue >= heavierFloor)||startValue>= heavierFloor) {
					//当用户体质指数由标准转为超标或一直在超标区间时显示
					builder.append("本周体质指数增加" + value
							+ "，要加强锻炼哦");
				}else{
					//当用户体质指数由不达标转为超标
					builder.append("本周体质指数增加" + value
							+ "，要加强锻炼哦");
				}
				
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
//				sp.setSpan(new AbsoluteSizeSpan(12, true), 8 + length,
//						8 + length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				
				
			} else {//如果横坐标最右侧日期的体质指数等于其一周前的体质指数
				if (startValue >= normalFloor && startValue <heavierFloor) {
					//二者均在标准区间内时显示
					builder.append("本周体质指数没有变化，仍属标准区间");
				} else if (startValue < normalFloor) {
					//当用户体质指数在不达标区间时显
					builder.append("本周体质指数没有变化，要增加热量摄入哦");
				} else if (startValue >= heavierFloor) {
					//当用户体质指数在超标区间内时显示
					builder.append("本周体质指数没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		} 
		return sp;
	}
	private SpannableString  getZhifanglvMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.shentizhifanglv) {
			PeriodZhifanglvData ptzd = (PeriodZhifanglvData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double standardFloor = ptzd.getStandard().getStandardFloor();
			double mildFatFloor = ptzd.getStandard().getMildFatFloor();
			double fatFloor=ptzd.getStandard().getFatFloor();
			
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的脂肪率小于其一周前的脂肪率
				value=balnkchar+format.format((startValue - endValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue < mildFatFloor && endValue < mildFatFloor) {
					//二者均在标准区间内时显示
					builder.append("本周脂肪率减少" + value
							+ "，仍属标准区间");
				} else if ((startValue >= standardFloor && startValue < mildFatFloor&& endValue < standardFloor)
						) {
					//当用户脂肪率由达标转为不达标时显示
					builder.append("本周脂肪率减少" +value
							+ "，要增加脂肪摄入哦");
				} else if ((startValue >= mildFatFloor && endValue>= standardFloor)||endValue>=mildFatFloor) {
					//由超标转为标准时或仍在超标区间内时显示
					builder.append("本周脂肪率减少" + value
							+ "，要继续保持哦");
				}else{//由超标转为不达标
					builder.append("本周脂肪率减少" +value
							+ "，要增加脂肪摄入哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				value=balnkchar+format.format((endValue - startValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue < mildFatFloor && endValue <mildFatFloor) {
					//二者均在标准区间内时显示
					builder.append("本周脂肪率增加" + value
							+ "，仍属标准区间");
				} else if ((startValue < standardFloor && endValue<mildFatFloor)||endValue<standardFloor) {
					//当用户脂肪率由不达标转为达标或仍在不达标区间内时显示
					builder.append("本周脂肪率增加" + value
							+ "，要继续努力哦");
				} else if (startValue >= standardFloor && startValue < mildFatFloor
						&& endValue >= mildFatFloor) {
					//用户脂肪率由标准转为超标时显示
					builder.append("本周脂肪率增加" + value
							+ "，要加强锻炼哦");
				}else{//由不达标转为超标
						builder.append("本周脂肪率增加" +value
								+ "，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}else{
				//如果横坐标最右侧日期的脂肪率等于其一周前的脂肪率
				if (startValue >= standardFloor && startValue < mildFatFloor ) {
					//当二者均在标准区间内时显示
					builder.append("本周脂肪率没有变化，仍属标准区间");
				} else if (startValue < standardFloor) {
					builder.append("本周脂肪率没有变化，要增加热量摄入哦");
				} else if (startValue >=mildFatFloor) {
					builder.append("本周脂肪率没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		}
		return sp;
	}

	private SpannableString  getNeiZangZhifanglvMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.neizangzhifangshuiping) {
			PeriodNeizangzhifangData ptzd = (PeriodNeizangzhifangData) hbcv.getObject();
			Map<String, Integer> dataMap = (Map<String, Integer>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			int balancedFloor=ptzd.getStandard().getBalancedFloor();
			int warningFloor = ptzd.getStandard().getWarningFloor();
			int fatFloor=ptzd.getStandard().getFatFloor();
			int  veryFatFloor = ptzd.getStandard().getVeryFatFloor();

			int startValue = 0;
			int endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的内脏脂肪水平小于其一周前的内脏脂肪率
				value=balnkchar+format.format((startValue - endValue));
				if (startValue >= balancedFloor && endValue >= balancedFloor
						&& startValue <warningFloor && endValue <warningFloor) {
					//二者均在均衡型区间内时
					builder.append("本周内脏脂肪减少" + value
							+ "，仍属标准区间");
				} else if (startValue >= balancedFloor && startValue < warningFloor
						&& endValue < balancedFloor) {
					//当用户内脏脂肪水平由均衡型转为皮下型时
					builder.append("本周内脏脂肪减少" +value
							+ "，要增加脂肪摄入哦");
				} else if ((startValue >= warningFloor && endValue >= balancedFloor&& endValue <warningFloor)||endValue>=warningFloor) {
					//用户内脏脂肪水平由超标转为均衡型或仍在超标区间（警戒、肥胖和非常肥胖）时显示
					builder.append("本周内脏脂肪减少" + value
							+ "，要继续努力哦");
				}else{
					//用户内脏脂肪水平由超标转为为皮下型时
					builder.append("本周内脏脂肪减少" + value
							+ "，要增加热量摄入哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				//如果横坐标最右侧日期的内脏脂肪水平大于其一周前的内脏脂肪水平
				value=balnkchar+format.format((endValue - startValue));
				if (startValue >= balancedFloor && endValue >= balancedFloor
						&& startValue <warningFloor && endValue <warningFloor) {
					//二者均在标准区间内时显示
					builder.append("本周内脏脂肪增加" + value
							+ "，仍属标准区间");
				} else if ((startValue < balancedFloor&& endValue >= balancedFloor && endValue <warningFloor)||endValue<balancedFloor) {
					//用户内脏脂肪水平由皮下型转为均衡型或仍在皮下型区间内时显示
					builder.append("本周内脏脂肪增加" + value
							+ "，要继续努力哦");
				} else if ((startValue >= balancedFloor && startValue < warningFloor
						&& endValue >=warningFloor)||startValue>=warningFloor) {
					//当用户内脏脂肪水平由均衡型转为超标在或超标区间内提高等级时
					builder.append("本周内脏脂肪增加" + value
							+ "，要加强锻炼哦");
				}else{
					//当用户内脏脂肪水平由皮下型转为超标
					builder.append("本周内脏脂肪增加" + value
						+ "，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				
				
			} else {
				//如果横坐标最右侧日期的内脏脂肪水平等于其一周前的内脏脂肪水平
				if (startValue >= balancedFloor&& startValue <warningFloor) {
					//二者均在均衡型区间内时显示
					builder.append("本周内脏脂肪没有变化，仍属标准区间");
				} else if (startValue < balancedFloor) {
					//当用户内脏脂肪水平在皮下型区间时显示
					builder.append("本周内脏脂肪没有变化，要增加热量摄入哦");
				} else if (startValue >= warningFloor) {
					//当用户内脏脂肪水平在超标区间内时显示
					builder.append("本周内脏脂肪没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		} 
		return sp;
	}
	private SpannableString  getFubufeipanglvMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.fubufeipanglv) {
			PeriodFubufeipanglvData ptzd = (PeriodFubufeipanglvData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double standardFloor = ptzd.getStandard().getStandardFloor();
			double standardCeil = ptzd.getStandard().getStandardCeil();
			
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的腹部肥胖率小于其一周前的腹部肥胖率
				value=balnkchar+format.format((startValue - endValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue <= standardFloor && endValue <= standardCeil) {
					//二者均在标准区间内时显示
					builder.append("本周腹部肥胖率减少" + value
							+ "，仍属标准区间");
				} else if ((startValue >= standardFloor && startValue <= standardCeil&& endValue < standardFloor)
						) {
					//当用户腹部肥胖率由达标转为不达标时显示
					builder.append("本周腹部肥胖率减少" +value
							+ "，要增加热量摄入哦");
				} else if ((startValue >standardCeil && endValue>= standardFloor&&endValue<=standardCeil)||endValue>=standardCeil) {
					//当用户腹部肥胖率由超标转为标准或仍在超标区间内时显示
					builder.append("本周腹部肥胖率减少" + value
							+ "，要继续保持哦");
				}else{//由超标转为不达标
					builder.append("本周腹部肥胖率减少" +value
							+ "，要增加热量摄入哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 9, 9 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 9 + length-1, 9 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 9, 9 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				//如果横坐标最右侧日期的腹部肥胖率大于其一周前的腹部肥胖率
				value=balnkchar+format.format((endValue - startValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue <= standardCeil && endValue <=standardCeil) {
					//二者均在标准区间内时显示
					builder.append("本周腹部肥胖率增加" + value
							+ "，仍属标准区间");
				} else if ((startValue <=standardFloor && endValue>=standardFloor&&endValue<=standardCeil)||endValue<standardFloor) {
					//当用户腹部肥胖率由不达标转为达标或仍在不达标区间内
					builder.append("本周腹部肥胖率增加" + value
							+ "，要继续努力哦");
				} else if (startValue >= standardFloor && startValue <= standardCeil
						&& endValue > standardCeil) {
					//当用户腹部肥胖率由标准转为超标时显示
					builder.append("本周腹部肥胖率增加" + value
							+ "，要加强锻炼哦");
				}else{//由不达标转为超标
						builder.append("本周腹部肥胖率增加" +value
								+ "，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 9, 9 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 9 + length-1, 9 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 9, 9 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}else{
				//如果横坐标最右侧日期的腹部肥胖率等于其一周前的腹部肥胖率
				if (startValue >= standardFloor && startValue <=standardCeil ) {
					//当二者均在标区间内时显示
					builder.append("本周腹部肥胖率没有变化，仍属标准区间");
				} else if (startValue < standardFloor) {
					builder.append("本周腹部肥胖率没有变化，要增加热量摄入哦");
				} else if (startValue >standardCeil) {
					builder.append("本周腹部肥胖率没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		}
		return sp;
	}
	private SpannableString  getJiroulvMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.jiroulv) {
			PeriodJiroulvData ptzd = (PeriodJiroulvData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double standardFloor = ptzd.getStandard().getStandardFloor();
			double higherFloor = ptzd.getStandard().getExcellentFloor();
//			double highestFloor = ptzd.getStandard().getHighestFloor();
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的肌肉率小于其一周前的肌肉率
				value=balnkchar+format.format((startValue - endValue))+"%";
				if (endValue >= standardFloor) {
					//二者均在标准区间内时显示
					builder.append("本周肌肉率减少" + value
							+ "，仍属标准区间");
				} else{//由超标准为不达标
					builder.append("本周肌肉率减少" +value
							+ "，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 7 + length-1, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				//如果横坐标最右侧日期的肌肉率大于其一周前的肌肉率
				value=balnkchar+format.format((endValue - startValue))+"%";
				if (startValue >= standardFloor ) {
					//二者均在标准区间内时显示
					builder.append("本周肌肉率增加" + value
							+ "，仍属标准区间");
				} else{
					builder.append("本周肌肉率增加" + value
							+ "，要继续努力哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 7 + length-1, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}else{
				//如果横坐标最右侧日期的腹部肥胖率等于其一周前的腹部肥胖率
				if (startValue >= standardFloor && startValue <higherFloor ) {
					//当二者均在标区间内时显示
					builder.append("本周肌肉率没有变化，仍属标准区间");
				} else if (startValue < standardFloor) {
					builder.append("本周肌肉率没有变化，要增加锻炼哦");
				} else if (startValue >=higherFloor) {
					builder.append("本周肌肉率没有变化，要降低锻炼强度哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		}
		return sp;
	}
	
	
	
	private SpannableString  getShuifenMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.shentishuifen) {
			PeriodShuifenData ptzd = (PeriodShuifenData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double normalFloor = ptzd.getStandard().getNormalFloor();
			double normalCeil = ptzd.getStandard().getNormalCeil();
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的身体水分小于其一周前的身体水分
				value=balnkchar+format.format((startValue - endValue))+"%";
				if (startValue >= normalFloor && endValue >= normalFloor
						&& startValue <normalCeil && endValue <normalCeil) {
					//二者均在标准区间内时显示
					builder.append("本周身体水分减少" + value
							+ "，仍属标准区间");
				} else if ((startValue >= normalFloor && startValue <normalCeil&& endValue < normalFloor)
						) {
					//当用户身体水分由达标转为不达标时显示
					builder.append("本周身体水分减少" +value
							+ "，要增加水分摄入哦");
				} else if ((startValue >=normalCeil && endValue>= normalFloor&&endValue<normalCeil)||endValue>=normalCeil) {
					//当用户身体水分由超标转为标准或仍在超标区间内时显示
					builder.append("本周身体水分减少" + value
							+ "，要继续保持哦");
				}else{//由超标转为不达标
					builder.append("本周身体水分减少" +value
							+ "，要增加水分摄入哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 8 + length-1, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				//如果横坐标最右侧日期的身体水分大于其一周前的身体水分
				value=balnkchar+format.format((endValue - startValue))+"%";
				if (startValue >= normalFloor && endValue >= normalFloor
						&& startValue <normalCeil && endValue <normalCeil) {
					//二者均在标准区间内时显示
					builder.append("本周身体水分增加" + value
							+ "，仍属标准区间");
				} else if ((startValue <normalFloor && endValue>=normalFloor&&endValue<normalCeil)||endValue<normalFloor) {
					//当用户身体水分由不达标转为达标或仍在不达标区间内时显示
					builder.append("本周身体水分增加" + value
							+ "，要继续努力哦");
				} else if (startValue >= normalFloor && startValue <normalCeil
						&& endValue >= normalCeil) {
					//当用户身体水分由标准转为超标时显示
					builder.append("本周身体水分增加" + value
							+ "，要促进水排出哦");
				}else{//由不达标转为超标
						builder.append("本周身体水分增加" +value
								+ "，要促进水排出哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 8, 8 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 8 + length-1, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 8, 8 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}else{
				//如果横坐标最右侧日期的腹部肥胖率等于其一周前的腹部肥胖率
				if (startValue >= normalFloor && startValue <normalCeil ) {
					//当二者均在标区间内时显示
					builder.append("本周身体水分没有变化，仍属标准区间");
				} else if (startValue < normalFloor) {
					builder.append("本周身体水分没有变化，要增加水摄入哦");
				} else if (startValue >=normalCeil) {
					builder.append("本周身体水分没有变化，要促进水排出哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}

		}
		return sp;
	}

	private SpannableString  getDanbaizhiMessage(HistoryBodyCheckView hbcv){
		StringBuilder builder = new StringBuilder();
		SpannableString sp = null;
		String value="";
		DecimalFormat format=new DecimalFormat("#.#");
		int colorID = getResources().getColor(R.color.time_color_normal);
		if (currentType == Type.danbaizhizhiliangbi) {
			PeriodDanbaizhiData ptzd = (PeriodDanbaizhiData) hbcv.getObject();
			Map<String, Double> dataMap = (Map<String, Double>) ptzd.getDataMap();
			Set<String> keySet = dataMap.keySet();

			double standardFloor = ptzd.getStandard().getStandardFloor();
			double standardCeil = ptzd.getStandard().getStandardCeil();
			double startValue = 0;
			double endValue = 0;
			int i = 0;
			for (String key : keySet) {
				if(dataMap.get(key)==0){
					i++;
					continue;
				}
				if (startValue == 0) {
					startValue = Math.abs(dataMap.get(key));
				}
				if(dataMap.get(key)>0){
					endValue = Math.abs(dataMap.get(key)) ;
				}
				i++;
			}
			if (endValue < startValue) {
				//如果横坐标最右侧日期的蛋白质重量比小于其一周前的蛋白质重量比
				value=balnkchar+format.format((startValue - endValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue <standardCeil && endValue <standardCeil) {
					//二者均在标准区间内时显示
					builder.append("本周蛋白质减少" + value
							+ "，仍属标准区间");
				} else if ((startValue >= standardFloor && startValue <standardCeil&& endValue < standardFloor)
						) {
					//当用户蛋白质重量比由达标转为不达标时显示
					builder.append("本周蛋白质减少" +value
							+ "，要增加摄入量哦");
				} else if ((startValue >=standardCeil && endValue>= standardFloor&&endValue<standardCeil)||endValue>=standardCeil) {
					//当用户蛋白质重量比由超标转为标准或仍在超标区间内时显示
					builder.append("本周蛋白质减少" + value
							+ "，要继续努力哦");
				}else{//由超标转为不达标
					builder.append("本周蛋白质减少" +value
							+ "，要增加摄入量哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 7 + length-1, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

			} else if (endValue > startValue) {
				//如果横坐标最右侧日期的蛋白质重量比大于其一周前的蛋白质重量比 
				value=balnkchar+format.format((endValue - startValue))+"%";
				if (startValue >= standardFloor && endValue >= standardFloor
						&& startValue <standardCeil && endValue <standardCeil) {
					//二者均在标准区间内时显示
					builder.append("本周蛋白质增加" + value
							+ "，仍属标准区间");
				} else if ((startValue <standardFloor && endValue>=standardFloor&&endValue<standardCeil)||endValue<standardFloor) {
					//当用户蛋白质重量比由不达标转为达标或仍在不达标区间内时显示
					builder.append("本周蛋白质增加" + value
							+ "，要继续努力哦");
				} else if (startValue >= standardFloor && startValue < standardCeil
						&& endValue >= standardCeil) {
					//当用户蛋白质重量比由标准转为超标时显示
					builder.append("本周蛋白质增加" + value
							+ "，要加强锻炼哦");
				}else{//由不达标转为超标
						builder.append("本周蛋白质增加" +value
								+ "，要加强锻炼哦");
				}
				int length = value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new AbsoluteSizeSpan(20, true), 7, 7 + length-1,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new AbsoluteSizeSpan(12, true), 7 + length-1, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
				sp.setSpan(new ForegroundColorSpan(Color.WHITE), 7, 7 + length,
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}else{
				//如果横坐标最右侧日期的腹部肥胖率等于其一周前的腹部肥胖率
				if (startValue >= standardFloor && startValue <standardCeil ) {
					//当二者均在标区间内时显示
					builder.append("本周蛋白质没有变化，仍属标准区间");
				} else if (startValue < standardFloor) {
					builder.append("本周蛋白质没有变化，要增加摄入量哦");
				} else if (startValue >=standardCeil) {
					builder.append("本周蛋白质没有变化，要加强锻炼哦");
				}
				int length =value.length();
				sp = new SpannableString(builder.toString());
				sp.setSpan(new ForegroundColorSpan(colorID), 0, builder.toString().length(),
						Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
			}
		}
		return sp;
	}
	
}
