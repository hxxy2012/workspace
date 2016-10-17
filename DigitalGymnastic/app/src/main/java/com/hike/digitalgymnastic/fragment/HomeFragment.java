package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.DownloadManager.Request;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hike.digitalgymnastic.ActivitySportTree;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.adapter.TimeLineAdapter;
import com.hike.digitalgymnastic.entitiy.AppVersion;
import com.hike.digitalgymnastic.entitiy.BuXing;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.entitiy.QQRunData;
import com.hike.digitalgymnastic.entitiy.QQStepsData;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.request.SportDao;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.SportType;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.DashCircleProgress;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hike.digitalgymnastic.view.MyViewFlipper.ViewFlipperOnTouchListener;
import com.hike.digitalgymnastic.view.ScrollViewInner;
import com.hike.digitalgymnastic.view.SportRateImageView;
import com.hiko.enterprisedigital.R;
import com.hiko.enterprisedigital.SocialShareActivity;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HomeFragment extends BaseFragment implements
        ViewFlipperOnTouchListener {

    @ViewInject(R.id.rl_root)
    RelativeLayout rl_root;
    @ViewInject(R.id.iv_completionRate)
    DashCircleProgress iv_completionRate;
    @ViewInject(R.id.tv_today_sport_count)
    TextView tv_today_sport_count;
    @ViewInject(R.id.ll2)
    LinearLayout ll2;
    @ViewInject(R.id.center_layout_target)
    LinearLayout center_layout_target;
    @ViewInject(R.id.center_layout_acture)
    LinearLayout center_layout_acture;
    @ViewInject(R.id.tv_today_info)
    TextView tv_today_info;

    @ViewInject(R.id.ll_sleep)
    LinearLayout ll_sleep;
    @ViewInject(R.id.iv_icon)
    ImageView iv_icon;
    @ViewInject(R.id.tv_sleep_time)
    TextView tv_sleep_time;
    @ViewInject(R.id.tv_sleep)
    TextView tv_sleep;
    @ViewInject(R.id.ll_body)
    LinearLayout ll_body;
    @ViewInject(R.id.iv_icon2)
    ImageView iv_icon2;
    @ViewInject(R.id.tv_weight_day)
    TextView mWeight_day;
    @ViewInject(R.id.tv_weight)
    TextView tv_weight;
    @ViewInject(R.id.tv_weight_value)
    TextView tv_weight_value;


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
    @ViewInject(R.id.tv_date)
    TextView tv_date;

    LayoutInflater inflater;
    View view;

    private TextView tv_target;
    private TextView tv_acture;
    //    private DashCircleProgress iv_completionRate;
    private Customer customer;
    private SportDao dao;
    private BaseDao baseDao;

    private SportRateImageView sriv_sport;
    private Button btn_share;
    private TimeLineAdapter adapter;
    private String sportName;
    private int sporttype;
    private static final String TAG = "HomeFragment";
    private int homepage = 1;
    private String totalCalories;
    private HomeListener hListener = null;
    private String titliname;
    //    private TextView tv_today_desp;
    ScrollViewInner svi;
    Boolean isOpenActivity = true;


    @ViewInject(R.id.rl_root)
    RelativeLayout mLayotRoot;
    private static int mTextColor ;
    private int mWhileColor;
    private String mSleepDate;
    private String mSleepTime;
    private String mWeight;
    private String mWeightDay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View view = inflater.inflate(R.layout.activity_home1, container, false); // 加载fragment布
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
        mWhileColor= getResources().getColor(
                R.color.white);
        mTextColor = getResources().getColor(
                R.color.time_color_normal);

    }


    static HomeFragment home;
    public static HomeFragment newInstance(){
        if(home==null){
            home= new HomeFragment();
        }
        return home;
    }
    MainActivity mMainActivity;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mMainActivity = (MainActivity) activity;

    }

    @Override
    public void onResume() {
        super.onResume();
        UtilLog.e(TAG, " onresume ");
        if (!this.isHidden()) {
            mMainActivity.mMainTabView.setVisibility(View.VISIBLE);
//			if (ma.currentPage == 0 && !versionDialogShowing)// 从本地数据加载睡眠主页
            if (!versionDialogShowing)// 从本地数据加载睡眠主页
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buildViewFromLoacalData();
                    }
                }, 500);


            if (hListener != null) {
                if (homepage == 1) {
                    titliname = getString(R.string.today_sport_title_str);
                    if (hListener != null) {// 回调 change 主activity标题给变量赋值
                        hListener.onChange(titliname, homepage);
                    }
                } else {
                    titliname = "共消耗"
                            + (int) (Double.parseDouble(totalCalories) + 0.5)
                            + "kcal";
                    if (hListener != null) {// 回调 change 主activity标题给变量赋值
                        hListener.onChange(titliname, homepage);
                    }
                }
            }
        }
    }

    private boolean versionDialogShowing = false;

    @Override
    public void onPause() {
        super.onPause();
        UtilLog.e(TAG, "onpause");



        try {
//			iv_completionRate.dismissProgress();
            if (dao != null && dao.isRunning()) {
                dao.cancel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        UtilLog.e(TAG,"onHiddenChanged");

    }
    private void init() {

        dao = new SportDao(this, activity);
        customer = LocalDataUtils.readCustomer(activity);
        title.setTextColor(getResources().getColor(R.color.white));
        title.setText("今日运动");

        iv_completionRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (center_layout_target.isShown()) {
                    center_layout_target.setVisibility(View.INVISIBLE);
                    center_layout_acture.setVisibility(View.VISIBLE);
                } else {
                    if(totalCalories!=null && Double.parseDouble(totalCalories)>0){
                        Intent intent=new Intent(mMainActivity, ActivitySportTree.class);
                        mMainActivity.startActivity(intent);
                    }else{
                        Toast.makeText(mMainActivity,"消耗卡路里为0，没有详细数据",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        center_layout_target.setVisibility(View.INVISIBLE);
        center_layout_acture.setVisibility(View.VISIBLE);
        tv_target = (TextView) center_layout_target
                .findViewById(R.id.tv_target);
        tv_acture = (TextView) center_layout_acture
                .findViewById(R.id.tv_acture);

        //
        // // 先加载本空数据
        buildView(null);

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(sportAction);
        activity.registerReceiver(receiver, intentFilter);

        if (application.appVersion == null)
            startBluetoothStateChecking();
        else {
            showUpdateVersion(application.appVersion);
        }

        btn_left.setVisibility(View.GONE);
        btn_right.setVisibility(View.GONE);
        ll_sleep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳转睡眠页面

                FragmentTransaction _ft = mMainActivity.getManager().beginTransaction();
                _ft.replace(R.id.id_rlyt_fragment, new SleepFragment(), R.id.ll_sleep + "");
                _ft.addToBackStack(null);
                _ft.commit();
                mMainActivity.mMainTabView.setVisibility(View.GONE);
            }
        });
        ll_body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //跳转体测页面

                FragmentTransaction _ft = mMainActivity.getManager().beginTransaction();
                _ft.replace(R.id.id_rlyt_fragment, new BodyCheckFragment(), R.id.ll_body + "");
                _ft.addToBackStack(null);
                _ft.commit();
                mMainActivity.mMainTabView.setVisibility(View.GONE);
            }
        });
        //25号接口出来之后，页面填充

    }

    private void startBluetoothStateChecking() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                checkBluetoothState();
            }
        }, 1000);
    }

    public static final String sportAction = "com.hikodigital.sportdata";

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(sportAction)) {
                // 先加载本地的主页数据
                buildViewFromLoacalData();
            }
        }
    };

    public void onDestroy() {
        super.onDestroy();
        UtilLog.e(TAG,"onDestroy");
        try {

            activity.unregisterReceiver(receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void buildViewFromLoacalData() {
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
        SharedPreferences shp = activity.getSharedPreferences("home",
                Context.MODE_WORLD_WRITEABLE);

        if (!isShowingProgressDialog()) {
            showProgress(false);
        }

//        String homeString = shp.getString(format.format(new Date()), null);
//        if (homeString != null) {
//            Gson gson = new Gson();
//            hsd = gson.fromJson(homeString, HomeSportData.class);
//            buildView(hsd);
//        }

        dao.GetHomeSportData();

    }

    private void setValue(String acture, String value, String advice) {
        if (!TextUtils.isEmpty(value)) {
            tv_target.setText(value);
            // tv_acture.setText(new
            // java.text.DecimalFormat("#").format(Double.parseDouble(value)));
        }

        if (!TextUtils.isEmpty(acture)) {
            // tv_acture.setText(acture);
            tv_acture.setText(new java.text.DecimalFormat("#").format(Double
                    .parseDouble(acture) + 0.5));
        }
        if (!TextUtils.isEmpty(advice)) {
            tv_today_info.setText(advice);
        }
    }
    Date date3;
    String mLock="mLock";
    private synchronized void buildView(HomeSportData hsd) {
        synchronized (mLock){

        }
        if (this.isDetached()) {
            return;
        }

        if (hsd == null) {
            ll2.setVisibility(View.GONE);
            tv_today_info.setText("你今天还未参加任何运动");
            tv_today_info.setTextColor(mTextColor);
            // 构建空数据布局
            if (customer.getGender().equals("1")) {// 男性
                buildMaleBlankView();
            } else if (customer.getGender().equals("2")) {
                buildSexBlankView();
            } else {
                buildMaleBlankView();
            }

        } else {
            customer = LocalDataUtils.readCustomer(activity);
            mSleepDate = hsd.getSleepDate();
            mSleepTime = hsd.getTotalSleep();
            mWeightDay = hsd.getWeightDate();
            mWeight = hsd.getWeight();
            if(!TextUtils.isEmpty(mSleepTime)){
                int mSleepHour = (int)(Integer.parseInt(mSleepTime) / 3600);
                int  mSleepMin = (Integer.parseInt(mSleepTime)-mSleepHour*3600)/60;
                tv_sleep_time.setText(mSleepHour+"小时"+mSleepMin+"分钟");

                try{
                    if(!TextUtils.isEmpty(mSleepDate)) {
                        String _needTime = mSleepDate.split(" ")[0];
                        boolean _isYesterday = Utils.isYesterday(_needTime);
                        if(_isYesterday){
                            tv_sleep.setText("昨晚睡眠");
                        }else{
                            String[] _time = _needTime.split("-");
                            tv_sleep.setText(_time[1]+ "月" + _time[2] + "日");
                        }
                    }else{
                        tv_sleep.setText("暂无睡眠数据！");
                    }
                }catch(Exception e){
                    tv_sleep.setText("暂无数据");
                }

            }else{
                tv_sleep.setText("没有睡眠数据");
                tv_sleep_time.setText("");

            }
            if(!TextUtils.isEmpty(mWeightDay)) {
                try {

                    String _needTime = mWeightDay.split(" ")[0];
                    boolean _isYesterday = Utils.isYesterday(_needTime);
                    if(_isYesterday){
                        mWeight_day.setText("昨日体测");
                    }else{
                        String[] _time = _needTime.split("-");
                        mWeight_day.setText(_time[1]+ "月" + _time[2] + "日");
                    }

                } catch (Exception e) {
                    mWeight_day.setText("暂无时间数据");
                }
            }else {
                mWeight_day.setText("");
            }

            tv_weight_value.setText(mWeight+"公斤");
            totalCalories = hsd.getTotalCalories();

            int calories = (int) Double.parseDouble(hsd.getTotalCalories());
            if (!TextUtils.isEmpty(customer.getGoalCalories())) {
                iv_completionRate.setValue((int) Double.parseDouble(hsd.getTotalCalories()));
                iv_completionRate.setMax((int) Double.parseDouble(customer.getGoalCalories()));


            } else {
                iv_completionRate.setValue((int) Double.parseDouble(hsd.getTotalCalories()));
                iv_completionRate.setMax((int) Double.parseDouble(hsd.getGoalCalories()));

            }
            setValue(String.valueOf(hsd.getTotalCalories()),
                    String.valueOf(hsd.getGoalCalories()), hsd.getAdvice());

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

            if ((hsd != null && Double.parseDouble(hsd.getTotalCalories()) == 0)) {
                ll2.setVisibility(View.GONE);

                tv_today_info.setText("你今天还未参加任何运动");
                tv_today_info.setTextColor(mTextColor);
                // 构建空数据布局
                if (customer.getGender().equals("1")) {// 男性
                    buildMaleBlankView();
                } else if (customer.getGender().equals("2")) {
                    buildSexBlankView();
                } else {
                    buildMaleBlankView();
                }
            } else {
                tv_today_info.setTextColor(mWhileColor);
                ll2.setVisibility(View.GONE);// 显示共参加n项活动布局
                // 构建动态类型布局
                if (hsd.getDailyList() != null)
                    tv_today_sport_count.setText(String.valueOf(hsd
                            .getDailyList().size()));

            }
        }
    }

    void buildMaleBlankView() {
        String[] typeNames = {"跑步", "游泳", "椭圆机", "二头肌双向练习", "三头肌双向练习",
                "坐式肩膀后展练习", "坐式大腿伸展练习", "高拉力背肌练习"};
        int[] typeImage = {R.mipmap.icon_paobu, R.mipmap.icon_youyong,
                R.mipmap.icon_tuoyuanji, R.mipmap.icon_ertouji,
                R.mipmap.icon_santouji, R.mipmap.icon_jianbang,
                R.mipmap.icon_tuibu, R.mipmap.icon_gaolali};



        Random random = new Random();
        int index = (int) (Math.random() * 3 + 1);// 1-3的随机数Math.random()0.0<=n<1.0

    }

    void buildSexBlankView() {
        String[] typeNames = {"跑步", "游泳", "有氧舞蹈", "椭圆机", "动感单车", "步行", "搏击操",
                "蝴蝶式扩胸练习"};
        int[] typeImage = {R.mipmap.icon_paobu, R.mipmap.icon_youyong,
                R.mipmap.icon_youyang, R.mipmap.icon_tuoyuanji,
                R.mipmap.icon_danche, R.mipmap.icon_buxing,
                R.mipmap.icon_bojicao, R.mipmap.icon_hudie};



    }

    @Override
    public void onNoConnect() {
        super.onNoConnect();
        showProgress(false);
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        // TODO Auto-generated method stub
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(false);
    }

    HomeSportData hsd;

    @SuppressLint("NewApi")
    @Override
    public void onRequestSuccess(int requestCode) {
        if (activity == null || activity.isFinishing() || this.isHidden())
            return;
        showProgress(false);
        Intent intent = null;
        switch (requestCode) {
            case SportType.HomeSport: // !<首页
                hsd = dao.getHomesportdata();
                buildView(hsd);
//
                break;

            default:
                break;
        }
    }

    private void uploadQQSteps(BuXing buxing) {
        QQStepsData stepsData = new QQStepsData();
        QQRunData runData = new QQRunData();
        String QQ_oauth_consumer_key = PreferencesUtils.getString(mMainActivity, "QQ_oauth_consumer_key", "");
        String QQ_access_token = PreferencesUtils.getString(mMainActivity, "QQ_access_token", "");
        String QQ_openid = PreferencesUtils.getString(mMainActivity, "QQ_openid", "");
        //步行数据
        stepsData.setAccess_token(QQ_access_token);
        stepsData.setOauth_consumer_key(QQ_oauth_consumer_key);
        stepsData.setOpenid(QQ_openid);
        stepsData.setTime(buxing.getBeginTime());
        stepsData.setDistance(buxing.getJuli() + "");
        stepsData.setSteps(buxing.getBushu() + "");
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");

        Long c = null;
        try {
            c = sf.parse(buxing.getEndTime()).getTime() - sf.parse(buxing.getBeginTime()).getTime();
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        long duration = c / 1000;//秒
        stepsData.setDuration(duration + "");
        stepsData.setCalories(buxing.getKaluli() + "");
        //跑步数据
        runData.setAccess_token(QQ_access_token);
        runData.setOauth_consumer_key(QQ_oauth_consumer_key);
        runData.setOpenid(QQ_openid);
        runData.setTime(buxing.getBeginTime());
        runData.setDistance(buxing.getJuli() + "");
        runData.setSteps(buxing.getBushu() + "");
        runData.setDuration(duration + "");
        runData.setCalories(buxing.getKaluli() + "");

        baseDao.setQQSteps(stepsData);
        baseDao.setQQRun(runData);

    }

    public interface HomeListener {
        public void onChange(String info, int i);
    }


    /**
     * 弹出系统弹框提示用户打开 Bluetooth
     */
    private void turnOnBluetooth() {
        // 请求打开 Bluetooth
        Intent requestBluetoothOn = new Intent(
                BluetoothAdapter.ACTION_REQUEST_ENABLE);

        // 设置 Bluetooth 设备可以被其它 Bluetooth 设备扫描到
        requestBluetoothOn
                .setAction(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);

        // 设置 Bluetooth 设备可见时间
        requestBluetoothOn.putExtra(
                BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION,
                BLUETOOTH_DISCOVERABLE_DURATION);

        // 请求开启 Bluetooth
        startActivityForResult(requestBluetoothOn, REQUEST_CODE_BLUETOOTH_ON);
    }

    private void checkBluetoothState() {
        if (android.os.Build.VERSION.SDK_INT >= 18) {
            if (activity != null) {
                BluetoothManager bluetoothManager = (BluetoothManager) activity
                        .getSystemService(Context.BLUETOOTH_SERVICE);
                BluetoothAdapter bluetoothAdapter = bluetoothManager
                        .getAdapter();

                if (bluetoothAdapter != null && !bluetoothAdapter.isEnabled()) {
                    this.turnOnBluetooth();
                }
            }
        }
    }

    /**
     * 自定义的打开 Bluetooth 的请求码，与 onActivityResult 中返回的 requestCode 匹配。
     */
    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;

    /**
     * Bluetooth 设备可见时间，单位：秒。
     */
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 250;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // requestCode 与请求开启 Bluetooth 传入的 requestCode 相对应
        if (requestCode == REQUEST_CODE_BLUETOOTH_ON) {
            switch (resultCode) {
                // 点击确认按钮
                case Activity.RESULT_OK: {
                    application.scanDevice(Contants.scanbyuuid);

                }


                // 点击确认按钮
                case 250: {
                    // TODO 用户选择开启 Bluetooth，Bluetooth 会被开启

                    application.scanDevice(Contants.scanbyuuid);
                }
                break;

                // 点击取消按钮或点击返回键
                case Activity.RESULT_CANCELED: {
                    // TODO 用户拒绝打开 Bluetooth, Bluetooth 不会被开启
                }
                break;
                default:
                    break;
            }
        }
        if (requestCode == ReqeustCode.FROM_CAPTURE) {
            if (resultCode == Activity.RESULT_OK) {
            }
        }
    }

    public void showUpdateVersion(final AppVersion appVersion) {
        PackageManager manager = mMainActivity.getPackageManager();
        PackageInfo info;
        try {
            info = manager.getPackageInfo(mMainActivity.getPackageName(), 0);
            String version = info.versionName;

            if (!TextUtils.isEmpty(appVersion.getForceVersion())
                    && version
                    .compareToIgnoreCase(appVersion.getForceVersion()) < 0) {
                versionDialogShowing = true;
                new AlertDialog.Builder(mMainActivity).setMessage("发现新版本")
                        .setPositiveButton("升级", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                downLoad(appVersion.getPackageUrl());
                                dialog.dismiss();
                                mMainActivity.finish();
                            }
                        }).setCancelable(false).show();

            } else if (!TextUtils.isEmpty(appVersion.getLatestVersion())
                    && version.compareToIgnoreCase(appVersion
                    .getLatestVersion()) < 0) {
                versionDialogShowing = true;
                new AlertDialog.Builder(mMainActivity).setMessage("发现新版本")
                        .setPositiveButton("升级", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int arg1) {
                                // TODO Auto-generated method stub
                                downLoad(appVersion.getPackageUrl());
                                dialog.dismiss();
                                mMainActivity.finish();
                            }
                        }).setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int arg1) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        versionDialogShowing = false;
                        buildViewFromLoacalData();
                        startBluetoothStateChecking();
                    }
                }).setCancelable(false).show();
            } else {
                versionDialogShowing = false;
                buildViewFromLoacalData();
                startBluetoothStateChecking();
            }
        } catch (NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    public void notifyUpdateTitle() {
        if (!this.isHidden()) {
            if (hListener != null) {
                if (homepage == 1) {
                    titliname = getString(R.string.today_sport_title_str);
                    if (hListener != null) {// 回调 change 主activity标题给变量赋值
                        hListener.onChange(titliname, homepage);
                    }
                } else {
                    int _totalCalories = (int) (Double.parseDouble(totalCalories) + 0.5);
                    titliname = "共消耗"
                            + _totalCalories
                            + "kcal";
                    if (hListener != null) {// 回调 change 主activity标题给变量赋值
                        hListener.onChange(titliname, homepage);
                    }
                }
            }

        }
    }
    public void downLoad(String fileUrl) {
        if (!TextUtils.isEmpty(fileUrl)) {
            String suffix = fileUrl.substring(fileUrl.length() - 4);
            if (suffix.equalsIgnoreCase(".apk")) {
                DownloadManager downloadManager = (DownloadManager) mMainActivity
                        .getSystemService(Context.DOWNLOAD_SERVICE);

                Uri uri = Uri.parse(fileUrl);
                Request request = new Request(uri);

                // 设置允许使用的网络类型，这里是移动网络和wifi都可以
                request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE
                        | DownloadManager.Request.NETWORK_WIFI);

                // 禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
                // request.setShowRunningNotification(false);

                // 不显示下载界面
                request.setVisibleInDownloadsUi(true);
                /*
				 * 设置下载后文件存放的位置,如果sdcard不可用，那么设置这个将报错， 因此最好不设置如果sdcard可用，下载后的文件
				 * 在/mnt/sdcard/Android/ data/packageName/files目录下面
				 * ，如果sdcard不可用,设置了下面这个将报错，不设置，下载后的文件在/cache这个 目录下面
				 */
                // request.setDestinationInExternalFilesDir(this,
                // null, "tar.apk");
                long id = downloadManager.enqueue(request);

                // TODO 把id保存好，在接收者里面要用，最好保存在Preferences里面
                SharedPreferences shp = mMainActivity.getSharedPreferences(
                        Constants.download, Context.MODE_WORLD_WRITEABLE);
                Editor editor = shp.edit();
                editor.putLong(Constants.ID, id);
                editor.commit();

            } else {
                Uri uri = Uri.parse(fileUrl);
                Intent downloadIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(downloadIntent);
            }
        }
    }

    boolean isSaving = false;
    final List<Bitmap> bmpsList = new ArrayList<Bitmap>();
    private synchronized void jump2ShareActivity() {
        bmpsList.clear();
        if (!isSaving) {
            showProgress(true);
            isSaving = true;

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
                                        bmp = Utils.getBitmapByView(svi,  Color.parseColor("#dc4355") , -(int) activity
                                                .getResources()
                                                .getDimension(
                                                        R.dimen.x300));
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

                            new Thread(new Runnable() {

                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    // 添加顶部的头像view
                                    bmpsList.add(Utils.getBitmapByViewCache(
                                            view_head, 0));

                                    // 添加列表view

                                    Bitmap bmp = Utils.getBitmapByView(svi, Color.parseColor("#dc4355"), -(int) activity
                                            .getResources()
                                            .getDimension(
                                                    R.dimen.x300));
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

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

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

        }

        ;
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

    @Override
    public void onTouch(boolean opened) {

    }
    public SpannableString changeText(int i, String str, int j) {

        int size = (int) getResources().getDimensionPixelSize(R.dimen.x12);
        String[] strs = str.split("\n");
        str = strs[1]+"\n"+strs[0];
        SpannableString spanString = new SpannableString(str);
        AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);

        spanString.setSpan(span, i+j, str.length(),
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return spanString;

    }
}
