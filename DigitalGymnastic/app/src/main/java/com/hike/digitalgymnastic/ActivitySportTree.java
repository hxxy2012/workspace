package com.hike.digitalgymnastic;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hike.digitalgymnastic.adapter.TimeLineAdapter;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.DailySportData;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.entitiy.OnceSportData;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.SportDao;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.SportType;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.HomePageListView;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hike.digitalgymnastic.view.SportRateImageView;
import com.hiko.enterprisedigital.R;
import com.hiko.enterprisedigital.SocialShareActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ContentView(R.layout.activity_home2)
public class ActivitySportTree extends BaseActivity {

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
	@ViewInject(R.id.tv_today_desp)
	private TextView tv_today_desp;
	@ViewInject(R.id.sportList)
	private HomePageListView sportList;
	@ViewInject(R.id.sriv_sport)
	private SportRateImageView sriv_sport;
	@ViewInject(R.id.btn_share)
	private Button btn_share;
	private HomeSportData hsd;
	private TimeLineAdapter adapter;
	private Customer customer;
	private String totalCalories;
	private SportDao dao;
	private int sporttype;
	private String sportName;

	private ScrollView svi;
	private String TAG = "ActivitySportTree";

	@OnClick(value = {R.id.btn_left, R.id.btn_right, R.id.ll_btn_left,
			R.id.ll_btn_right})
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
			case R.id.btn_left:
			case R.id.ll_btn_left:
				finish();
				break;

			case R.id.btn_right:
			case R.id.ll_btn_right:

					intent = new Intent(this, HistoryActivity.class);
					startActivity(intent);
//					AnimUtil.intentSlidIn(this);

				break;

			default:
				break;
		}
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		Utils.toolBarManager(this, R.color.app_bg_login);
		ViewUtils.inject(this);
		init();
	}
	public static final String sportAction = "com.hikodigital.sportdata";
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private void init() {
		dao = new SportDao(this, this);
		initBitmapUtils();
		//buildView(null);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setImageResource(R.mipmap.yundong_3x);
		btn_left.setImageResource(R.mipmap.btn_back);
		title.setText(getString(R.string.today_sport_title_str));
		buildViewFromLoacalData();
		btn_share.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!isSaving) {
					jump2ShareActivity();
				}
			}
		});
		View viewBottom = findViewById(R.id.sv_sport);
		svi = (ScrollView)viewBottom;
	}
	BitmapUtils bitmapUtils;

	/**
	 * 初始化图片加载器
	 */
	private void initBitmapUtils() {
		String path = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/"+getString(R.string.app_name)+"/cacher";
		bitmapUtils = new BitmapUtils(this, path);
		bitmapUtils.configDefaultLoadFailedImage(R.mipmap.icon_touxiang);
		bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
		// 尽量不要使用,图片太大时容易OOM。
		bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
		bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
				.getScreenSize(this));
		bitmapUtils.configDefaultLoadingImage(R.mipmap.icon_touxiang);
		// bitmapUtils.configDefaultCacheExpiry(2*60*1000);
		bitmapUtils.configThreadPoolSize(5);

	}
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {

			showProgress(ActivitySportTree.this,false);
			switch (msg.what) {
				case 0:
					if (msg.obj != null) {
						String filePath = (String) msg.obj;
						Intent intent = new Intent(ActivitySportTree.this,
								SocialShareActivity.class);
						intent.putExtra("filePath", filePath);
						startActivity(intent);
						isSaving = false;
					}
					break;

				default:
					break;
			}

		}

		;
	};
	boolean isSaving = false;
	final List<Bitmap> bmpsList = new ArrayList<Bitmap>();
	private synchronized void jump2ShareActivity() {
		bmpsList.clear();
		if (!isSaving) {
			showProgress(ActivitySportTree.this,true);
			isSaving = true;

			final LayoutInflater inflater = getLayoutInflater();
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
			tv_name.setText(customer.getName());

			tv_appname.setText(getString(R.string.app_name));
			tv_desp.setText("你从未如此了解自己");

			bitmapUtils.display(iv_head,
					HttpConnectUtils.image_ip + customer.getAvatar(),
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
									bmpsList.add(Utils.getBitmapByViewCache(
											view_head, 0));

									// 添加列表view
									Bitmap bmp;
									synchronized (mLock){
										bmp = Utils.getBitmapByView(svi,  Color.parseColor("#dc4355") , -(int)getResources()
												.getDimension(R.dimen.x300));
										bmpsList.add(bmp);

									}

									// 添加底部说明
									bmpsList.add(Utils.getBitmapByViewCache(
											view_bottom, 0));

									// 生成整个分享图片
									bmp = Utils.getBitmapSportList(Color.parseColor("#dc4355"), bmpsList);

									bmpsList.clear();
									String filePath = Utils.savePic(bmp,
											"sport");

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
							//资源图片 转 Drawable
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
									bmpsList.add(Utils.getBitmapByViewCache(
											view_head, 0));

									// 添加列表view

									Bitmap bmp = Utils.getBitmapByView(svi, Color.parseColor("#dc4355"), -(int)getResources()
											.getDimension(
													R.dimen.x280));
									bmpsList.add(bmp);

									// 添加底部说明
									bmpsList.add(Utils.getBitmapByViewCache(
											view_bottom, 0));

									// 生成整个分享图片
									bmp = Utils.getBitmapSportList(Color.parseColor("#dc4355"), bmpsList);
									String filePath = Utils.savePic(bmp,
											"sport");

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
	BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(sportAction)) {
				// 先加载本地的主页数据
				buildViewFromLoacalData();
			}
		}
	};
	public OnClickListener myListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
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
	};




	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	String mLock="mLock";
	private synchronized void buildView(HomeSportData hsd) {
		synchronized (mLock){

		}

		customer = LocalDataUtils.readCustomer(this);
//        randomLayout.clear();
		if (hsd == null) {

		} else {

			totalCalories = hsd.getTotalCalories();
			title.setText("共消耗"+totalCalories+"kcal");
//            randomLayout.clear();
			int calories = (int) Double.parseDouble(hsd.getTotalCalories());

//			setValue(String.valueOf(hsd.getTotalCalories()),
//					String.valueOf(hsd.getGoalCalories()), hsd.getAdvice());

			int total = (int) (Double.parseDouble(hsd.getTotalCalories()));
			int aver = (int) Double.parseDouble(hsd.getGoalCalories());
			int dis = total - aver;
			String value = null;
			if (dis > 0) {
				value = "超过目标" + dis + "kcal，" + "继续挑战自己吧";
			} else if (dis == 0) {
				value = "已达到目标,继续挑战自己吧";
			} else {
				value = "还差目标" + (-dis) + "kcal，" + "继续挑战自己吧";
			}
			tv_today_desp.setText(value);

			if ((hsd != null && Double.parseDouble(hsd.getTotalCalories()) == 0)) {
			} else {
				if (hsd.getOnceList() != null) {
					adapter = new TimeLineAdapter(this, hsd.getOnceList());
					sportList.setAdapter(adapter);
					Utils.setListViewHeightBasedOnChildren(sportList);
					String[] types = new String[hsd.getDailyList().size()];
					double[] values = new double[hsd.getDailyList().size()];
					int i = 0;

					for (DailySportData dsd : hsd.getDailyList()) {
						types[i] = dsd.getSportName();
						values[i] = dsd.getTotalCalories();
						i++;
					}

					sriv_sport.setData(types, values);
					setListener();
//					viewFlipper.setListener(this);
				}

			}
		}
	}
	public synchronized void buildViewFromLoacalData() {
		showProgress(ActivitySportTree.this,false);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SharedPreferences shp = this.getSharedPreferences("home",
				Context.MODE_WORLD_WRITEABLE);

		if (!isShowingProgressDialog()) {

			showProgress(ActivitySportTree.this,false);
		}
//		iv_completionRate.showProgress();
		// showProgress(true);
		String homeString = shp.getString(format.format(new Date()), null);
		if (homeString != null) {
			Gson gson = new Gson();
			hsd = gson.fromJson(homeString, HomeSportData.class);
			buildView(hsd);
		}
//		dao.GetHomeSportData();
	}
	private void setListener() {
		adapter.setbtnTimeLineListener(new TimeLineAdapter.btnTimeLineListener() {
			@Override
			public void onSomeChange(OnceSportData info, int i) {
				btnGetDatas(info, i);
			}
		});
	}
	protected void btnGetDatas(OnceSportData str, int i) {
		if (isShowingProgressDialog())
			return;
		showProgress(ActivitySportTree.this,true);
		UtilLog.e(TAG, i + "------------" + str.getSportType());
		sporttype = str.getSportType();
		
		sportName = str.getSportName();
		switch (str.getSportType()) {
			case SportType.HKSportTypePaoBuJi: // !<跑步机
				dao.GetLatestPaoBuJiData(str.getOnceId());
				break;
			case SportType.HKSportTypeBuXing:// !<步行
				dao.GetLatestBuXingData(str.getOnceId());
				break;
			case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
				dao.GetLatestYouYangWuDaoData(str.getOnceId());
				break;
			case SportType.HKSportTypeDongGanDanChe:// !<动感单车
				dao.GetLatestDongGanDanCheData(str.getOnceId());
				break;
			case SportType.HKSportTypeGangLingCao:// !<杠铃操
				dao.GetLatestGangLingCaoData(str.getOnceId());
				break;
			case SportType.HKSportTypeBoJiCao:// !<搏击操
				dao.GetLatestBoJiCaoData(str.getOnceId());
				break;
			case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
				dao.GetLatestTuoYuanJiData(str.getOnceId());
				break;
			case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
				dao.GetLatestHuaTingLaLiData(str.getOnceId());
				break;
			case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
				dao.GetLatestJianBangHouZhanData(str.getOnceId());
				break;
			case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
				dao.GetLatestLaLiBeiJiData(str.getOnceId());
				break;
			case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
				dao.GetLatestBeiJiShenZhanData(str.getOnceId());
				break;
			case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
				dao.GetLatestZuoShiFeiNiaoData(str.getOnceId());
				break;
			case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
				dao.GetLatestZuoShiTiXiData(str.getOnceId());
				break;
			case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
				dao.GetLatestZuoShiDaTuiShenZhanData(str.getOnceId());
				break;
			case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
				dao.GetLatestZuoShiHouTuiQuShenData(str.getOnceId());
				break;
			case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
				dao.GetLatestDaTuiWaiCeJiData(str.getOnceId());
				break;
			case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器
				dao.GetLatestDaTuiNeiCeJiData(str.getOnceId());
				break;
			case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
				dao.GetLatestJianBangTuiJuData(str.getOnceId());
				break;
			case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器
				dao.GetLatestShuangXiangTuiXiongData(str.getOnceId());
				break;
			case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器
				dao.GetLatestHuDieShiKuoXiongData(str.getOnceId());
				break;
			case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
				dao.GetLatestWoShiTuiQuZhanData(str.getOnceId());
				break;
			case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器
				dao.GetLatestErTouJiShuangXiangData(str.getOnceId());
				break;
			case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
				dao.GetLatestSanTouJiShuangXiangData(str.getOnceId());
				break;
			case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
				dao.GetLatestLiShiDaTuiShenZhanData(str.getOnceId());
				break;
			case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
				dao.GetLatestTiaoJieDengTuiData(str.getOnceId());
				break;
			case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
				dao.GetLatestLiShiDaTuiQuShenData(str.getOnceId());
				break;
			case SportType.HKSportTypeYouYong: // !<游泳
				dao.GetLatestYouYongData(str.getOnceId());
				break;
			case SportType.HKSportTypeJianshencao:// !<健身操
				dao.getJianShenCaoOnceData(str.getOnceId());
				break;
			default:
				break;
		}

	}
	Boolean isOpenActivity = true;
	@Override
	public void onRequestSuccess(int requestCode) {

		showProgress(ActivitySportTree.this,false);
		Intent intent = null;
		switch (requestCode) {
//			case SportType.HomeSport: // !<首页
//				hsd = dao.getHomesportdata();
//				buildView(hsd);
////
//				break;
			case SportType.HKSportTypePaoBuJi: // !<跑步机
				if (isOpenActivity) {
					intent = new Intent(this, SportSingleDetailActivity.class);
					intent.putExtra(Constants.oncesporttype, sporttype);
					intent.putExtra(Constants.oncesportname, sportName);
					intent.putExtra(Constants.oncesportdata, dao.getPaobu());
					startActivity(intent);
				} else {
//				BuXing buxing = dao.getBuxing();
//				uploadQQSteps(buxing);
//				isOpenActivity = true;
				}

				break;
			case SportType.HKSportTypeBuXing:// !<步行
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getBuxing());
				startActivity(intent);
				break;
			case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getYouxiangwudao());
				startActivity(intent);
				break;
			case SportType.HKSportTypeDongGanDanChe:// !<动感单车
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getDonggandanche());
				startActivity(intent);
				break;
			case SportType.HKSportTypeGangLingCao:// !<杠铃操
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getGanglingcao());
				startActivity(intent);
				break;
			case SportType.HKSportTypeBoJiCao:// !<搏击操
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getBojiCao());
				startActivity(intent);
				break;
			case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getTuoyuanji());
				startActivity(intent);
				break;
			case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getZuoshihuatinglali());
				startActivity(intent);
				break;
			case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getJianbanghouzhan());
				startActivity(intent);
				break;
			case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getGaolalibeiji());
				startActivity(intent);
				break;
			case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshibeijishenzhan());
				startActivity(intent);
				break;
			case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getZuoshifeiniao());
				startActivity(intent);
				break;
			case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getZuoshitixi());
				startActivity(intent);
				break;
			case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshidatuishenzhan());
				startActivity(intent);
				break;
			case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshihoutuiqushen());
				startActivity(intent);
				break;
			case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshidatuiwaiceji());
				startActivity(intent);
				break;
			case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshidatuineiceji());
				startActivity(intent);
				break;
			case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshijianbangtuiju());
				startActivity(intent);
				break;
			case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getZuoshishuangxiangtuixiong());
				startActivity(intent);
				break;
			case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getHudieshikuoxiong());
				startActivity(intent);
				break;
			case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getWoshituiquzhan());
				startActivity(intent);
				break;
			case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getErtoujishuangxiang());
				startActivity(intent);
				break;
			case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getSantoujishuangxiang());
				startActivity(intent);
				break;
			case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata,
						dao.getLishidatuishenzhan());
				startActivity(intent);
				break;
			case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getTiaojieshidengtui());
				startActivity(intent);
				break;
			case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getLishidatuiqushen());
				startActivity(intent);
				break;
			case SportType.HKSportTypeYouYong: // !<游泳
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getYouyong());
				startActivity(intent);
				break;

			case SportType.HKSportTypeJianshencao:
				intent = new Intent(this, SportSingleDetailActivity.class);
				intent.putExtra(Constants.oncesporttype, sporttype);
				intent.putExtra(Constants.oncesportname, sportName);
				intent.putExtra(Constants.oncesportdata, dao.getJianshencao());
				startActivity(intent);
				break;
			default:
				break;
		}
	}
}