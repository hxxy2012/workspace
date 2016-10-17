package com.hike.digitalgymnastic.request;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hike.digitalgymnastic.db.BodyHistory;
import com.hike.digitalgymnastic.db.BodyType;
import com.hike.digitalgymnastic.db.DBManager;
import com.hike.digitalgymnastic.entitiy.AllRemind;
import com.hike.digitalgymnastic.entitiy.AppVersion;
import com.hike.digitalgymnastic.entitiy.BandVersion;
import com.hike.digitalgymnastic.entitiy.BmiStandard;
import com.hike.digitalgymnastic.entitiy.BodyImage;
import com.hike.digitalgymnastic.entitiy.BuXing;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.CustomerVenue;
import com.hike.digitalgymnastic.entitiy.DailySleepData;
import com.hike.digitalgymnastic.entitiy.DailyTotalSportData;
import com.hike.digitalgymnastic.entitiy.DanbaizhiStandard;
import com.hike.digitalgymnastic.entitiy.DiaryDetail;
import com.hike.digitalgymnastic.entitiy.FeedBack;
import com.hike.digitalgymnastic.entitiy.FubufeipanglvStandard;
import com.hike.digitalgymnastic.entitiy.HomeBodyData;
import com.hike.digitalgymnastic.entitiy.HomeSleepData;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.entitiy.HomeTimeLineBtnData;
import com.hike.digitalgymnastic.entitiy.ImageUrl;
import com.hike.digitalgymnastic.entitiy.JiroulvStandard;
import com.hike.digitalgymnastic.entitiy.MessageContext;
import com.hike.digitalgymnastic.entitiy.MessageCount;
import com.hike.digitalgymnastic.entitiy.NeizangzhifangStandard;
import com.hike.digitalgymnastic.entitiy.OnceSportData;
import com.hike.digitalgymnastic.entitiy.Paobu;
import com.hike.digitalgymnastic.entitiy.PeriodBmiData;
import com.hike.digitalgymnastic.entitiy.PeriodDanbaizhiData;
import com.hike.digitalgymnastic.entitiy.PeriodFubufeipanglvData;
import com.hike.digitalgymnastic.entitiy.PeriodGuliangData;
import com.hike.digitalgymnastic.entitiy.PeriodJiroulvData;
import com.hike.digitalgymnastic.entitiy.PeriodNeizangzhifangData;
import com.hike.digitalgymnastic.entitiy.PeriodShuifenData;
import com.hike.digitalgymnastic.entitiy.PeriodSleepData;
import com.hike.digitalgymnastic.entitiy.PeriodSportData;
import com.hike.digitalgymnastic.entitiy.PeriodTizhongData;
import com.hike.digitalgymnastic.entitiy.PeriodZhifanglvData;
import com.hike.digitalgymnastic.entitiy.QQRunData;
import com.hike.digitalgymnastic.entitiy.QQSleepData;
import com.hike.digitalgymnastic.entitiy.QQStepsData;
import com.hike.digitalgymnastic.entitiy.QQWeightData;
import com.hike.digitalgymnastic.entitiy.QueryVenueDiary;
import com.hike.digitalgymnastic.entitiy.ShareImageData;
import com.hike.digitalgymnastic.entitiy.ShuifenStandard;
import com.hike.digitalgymnastic.entitiy.SportGifImage;
import com.hike.digitalgymnastic.entitiy.SportGifImageData;
import com.hike.digitalgymnastic.entitiy.TizhongStandard;
import com.hike.digitalgymnastic.entitiy.VenueList;
import com.hike.digitalgymnastic.entitiy.VerifyCode;
import com.hike.digitalgymnastic.entitiy.ZhifanglvStandard;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.http.IBaseRequest;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.tools.UtilLog;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utiles;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.db.sqlite.WhereBuilder;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BaseDao extends IBaseRequest {
    private SportGifImageData sportGifImageData;
    private ShareImageData shareImageData;
    private String TAG = "BaseDao";

    public ShareImageData getShareImageData() {
        return shareImageData;
    }

    //消息页面

    MessageContext mscontext;

    public MessageContext getMscontext() {
        return mscontext;
    }

    public void setMscontext(MessageContext mscontext) {
        this.mscontext = mscontext;
    }

    private int imageNumber = 0;
    private ArrayList<SportGifImage> sportGifImagesList;
    private BodyImage bodyImage;
    private QueryVenueDiary queryVenueDiary;
    private VenueList venueList;
    private CustomerVenue customerVenue;
    //日记页Beans

    public DiaryDetail diaryDetail;

    public DiaryDetail getDiaryDetail() {
        return diaryDetail;
    }

    public void setDiaryDetail(DiaryDetail diaryDetail) {
        this.diaryDetail = diaryDetail;
    }

    public CustomerVenue getCustomerVenue() {
        return customerVenue;
    }

    public void setCustomerVenue(CustomerVenue customerVenue) {
        this.customerVenue = customerVenue;
    }

    public VenueList getVenueList() {
        return venueList;
    }

    public void setVenueList(VenueList venueList) {
        this.venueList = venueList;
    }

    public QueryVenueDiary getQueryVenueDiary() {
        return queryVenueDiary;
    }

    public void setQueryVenueDiary(QueryVenueDiary queryVenueDiary) {
        this.queryVenueDiary = queryVenueDiary;
    }

    public int getImageNumber() {
        return imageNumber;
    }

    public ArrayList<SportGifImage> getSportGifImagesList() {
        return sportGifImagesList;
    }

    public BodyImage getBodyImage() {
        return bodyImage;
    }

    public enum DataType {
        one, two, threee, four, five, six, seven, eigth
    }

    private BandVersion bandVersion;
    private AppVersion appVersion;

    public AppVersion getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(AppVersion appVersion) {
        this.appVersion = appVersion;
    }

    public BandVersion getBandVersion() {
        return bandVersion;
    }

    public void setBandVersion(BandVersion bandVersion) {
        this.bandVersion = bandVersion;
    }

    private Context context;
    private JSONObject json;
    private Gson gson;
    private Customer customer; // 客户信息
    private VerifyCode verifyCode; // 验证码
    private ImageUrl imageurl; // 返回的验证码
    private FeedBack feedback; // 提交反馈返回的反馈标识

    private HomeTimeLineBtnData hometimelinebtndata;

    private Paobu paobu; // 获取跑步数据
    private BuXing buxing; // 获取步行数据

    public BuXing getBuxing() {
        return buxing;
    }

    public Paobu getPaobu() {
        return paobu;
    }

    public HomeTimeLineBtnData getHometimelinebtndata() {
        return hometimelinebtndata;
    }

    public BaseDao(INetResult activity, Context context) {
        super(activity, context);
        this.gson = new Gson();
        this.context = context;
    }

    public FeedBack getFeedback() {
        return feedback;
    }

    public ImageUrl getImageurl() {
        return imageurl;
    }

    public VerifyCode getVerifyCode() {
        return verifyCode;
    }

    /**
     * 首页运动数据
     *
     * @return
     */
    private HomeSportData homesportdata;

    public HomeSportData getHomesportdata() {
        return homesportdata;
    }

    /**
     * 首页睡眠数据
     *
     * @return
     */
    private HomeSleepData homeSleepData;

    public HomeSleepData getHomeSleepData() {
        return homeSleepData;
    }

    /**
     * 首页体测数据
     *
     * @return
     */
    private HomeBodyData homeBodyData;

    public HomeBodyData getHomeBodyData() {
        return homeBodyData;
    }

    /**
     * 体测历史数据-体质
     *
     * @return
     */
    PeriodTizhongData periodTizhongData;

    public PeriodTizhongData getPeriodTizhongData() {
        return periodTizhongData;
    }

    /**
     * 体测历史数据-身体水分
     *
     * @return
     */
    PeriodShuifenData PeriodShuifenData;

    public PeriodShuifenData getPeriodShuifenData() {
        return PeriodShuifenData;
    }

    /**
     * 体测历史数据-蛋白质
     *
     * @return
     */
    PeriodDanbaizhiData periodDanbaizhiData;

    public PeriodDanbaizhiData getPeriodDanbaizhiData() {
        return periodDanbaizhiData;
    }

    /**
     * 体测历史数据-腹部配胖率
     *
     * @return
     */
    PeriodFubufeipanglvData periodFubufeipanglvData;

    public PeriodFubufeipanglvData getPeriodFubufeipanglvData() {
        return periodFubufeipanglvData;
    }

    /**
     * 体测历史数据-肌肉率
     *
     * @return
     */
    PeriodJiroulvData periodJiroulvData;

    public PeriodJiroulvData getPeriodJiroulvData() {
        return periodJiroulvData;
    }

    /**
     * 体测历史数据-身体指数
     *
     * @return
     */
    PeriodBmiData PeriodBmiData;

    public PeriodBmiData getPeriodBmiData() {
        return PeriodBmiData;
    }

    /**
     * 体测历史数据-脂肪率
     *
     * @return
     */
    PeriodZhifanglvData periodZhifanglvData;

    public PeriodZhifanglvData getPeriodZhifanglvData() {
        return periodZhifanglvData;
    }

    /**
     * 体测历史数据-内脏脂肪率
     *
     * @return
     */
    PeriodNeizangzhifangData periodNeizangzhifangData;

    public PeriodNeizangzhifangData getPeriodNeizangzhifangData() {
        return periodNeizangzhifangData;
    }

    /**
     * 体测历史数据-骨量
     *
     * @return
     */
    PeriodGuliangData periodGuliangData;

    public PeriodGuliangData getPeriodGuliangData() {
        return periodGuliangData;
    }

    /**
     * 睡眠历史数据
     *
     * @return
     */

    private PeriodSleepData periodSleepData;

    public PeriodSleepData getPeriodSleepData() {
        return periodSleepData;
    }

    private PeriodSportData periodSportData;

    /**
     * 获取历史数据
     *
     * @return
     */
    public PeriodSportData getPeriodSportData() {
        return periodSportData;
    }

    /**
     * 登陆成功
     *
     * @return
     */
    public Customer getCustomer() {
        return customer;
    }

    public String getStr() {
        return json.toString();
    }

    String pattern = "yyyy-MM-dd";
    SimpleDateFormat format = new SimpleDateFormat(pattern);
    private AllRemind allRemind;

    @Override
    public void onRequestSuccess(JSONObject json, int requestCode)
            throws IOException, JsonSyntaxException, JSONException {
        this.json = json;
        Log.v("MyLog", json.toString());
        switch (requestCode) {
            case 0:// 登陆
                customer = gson.fromJson(json.getJSONObject("data").toString(),
                        Customer.class);

                String old_id = LocalDataUtils.readCustomer(context).getId();
                if (!TextUtils.isEmpty(old_id)
                        && !TextUtils.equals(old_id, customer.getId())
                        || TextUtils.isEmpty(old_id)) {
                    Log.d("MyLog", "SharedPreferences  clear");
                    SharedPreferences shp_home = context.getSharedPreferences(
                            "home", Context.MODE_WORLD_WRITEABLE);
                    SharedPreferences shp_sleep = context.getSharedPreferences(
                            "sleep", Context.MODE_WORLD_WRITEABLE);
                    SharedPreferences shp_body = context.getSharedPreferences(
                            "body", Context.MODE_WORLD_WRITEABLE);
                    shp_home.edit().clear().commit();
                    shp_sleep.edit().clear().commit();
                    shp_body.edit().clear().commit();
                }

                // 保存登陆token
                LocalDataUtils.saveCustomer(context, customer);// 登录令牌
                break;
            case 1:
                // 登出
                break;
            case 2:

                // 绑定手环
                break;
            case 3:

                // 解绑手环
                break;
            case 4:
                // 获取应用版本
                appVersion = gson.fromJson(json.getJSONObject("data").toString(),
                        AppVersion.class);

                break;
            case 5:
                // 获取手环版本

                bandVersion = gson.fromJson(json.getJSONObject("data").toString(),
                        BandVersion.class);
                break;
            case 6:
                // 上报推送令牌

                break;
            case 7:
                // 上传图片
                imageurl = gson.fromJson(json.getString("data"), ImageUrl.class);
                break;
            case 8:
                // 获取客户
                customer = gson.fromJson(json.getJSONObject("data").toString(),
                        Customer.class);
                break;
            case 9:
                // 修改客户
                break;
            case 1000:
                // 修改体重

                break;
            case 10:
                // 重置密码
                break;
            case 11:
                // 修改密码
                break;
            case 12:
                // 提交反馈
                feedback = gson.fromJson(json.getString("data"), FeedBack.class);
                break;
            case 13:
                // 上传步行数据
                break;
            case 14:
                SharedPreferences shp = context.getSharedPreferences("home",
                        Context.MODE_WORLD_WRITEABLE);
                Editor editor = shp.edit();
                editor.clear();
                editor.putString(format.format(new Date()), json.getString("data"));
                editor.commit();
                // homesportdata = gson.fromJson(json.getString("data"),
                // HomeSportData.class);
                // PreferencesUtils.putString(context, "HomeSportData",
                // json.getString("data"));
                // 获取首页运动数据
                break;
            case 15:
                // 获取日期运动数据
                break;
            case 16:
                // 获取时期运动数据
                periodSportData = gson.fromJson(json.getString("data"),
                        PeriodSportData.class);
                DBManager manager = DBManager.getIntance(context, LocalDataUtils
                        .readCustomer(context).getId());
                List<OnceSportData> onceSportDataList = new ArrayList<OnceSportData>();
                for (DailyTotalSportData dailyTotalSportData : periodSportData
                        .getDailyList()) {
                    for (OnceSportData onceSportData : dailyTotalSportData
                            .getOnceList()) {
                        onceSportData
                                .setStatDate(dailyTotalSportData.getStatDate());
                    }
                    onceSportDataList.addAll(dailyTotalSportData.getOnceList());
                }
                manager.saveOrupdateAll(periodSportData.getDailyList());
                manager.saveOrupdateAll(onceSportDataList);
                GetPeriodSportDataFromDB();
                break;
            case 17:
                // 获取最新背脊伸展数据
                break;
            case 18:
                // 获取最新搏击操数据
                break;
            case 19:
                // 获取最新步行数据
                break;
            case 20:
                // 获取最新大腿内侧肌数据
                break;
            case 21:
                // 获取最新大腿外侧肌数据
                break;
            case 22:
                // 获取最新动感单车数据
                break;
            case 23:
                // 获取最新二头肌双向数据
                break;
            case 24:
                // 获取最新杠铃操数据
                break;
            case 25:
                // 获取最新划艇拉里数据
                break;
            case 26:
                // 获取最新蝴蝶式扩胸数据
                break;
            case 27:
                // 获取最新肩膀后展数据
                break;
            case 28:
                // 获取最新肩膀推举数据
                break;
            case 29:
                // 获取最新拉力背肌数据
                break;
            case 30:
                // 获取最新立式大腿屈伸数据
                break;
            case 31:
                // 获取最新立式大腿伸展数据
                break;
            case 32:
                // 获取最新跑步机数据
                break;

            case 33:
                // 获取最新三头肌双向数据
                break;
            case 34:
                // 获取最新双向推胸数据
                break;
            case 35:
                // 获取最新调节式蹬腿数据
                break;

            case 36:
                // 获取最新椭圆机数据
                break;
            case 37:
                // 获取最新卧式腿屈展数据
                break;
            case 38:
                // 获取最新有氧舞蹈数据
                break;
            case 39:
                // 获取最新游泳数据
                break;
            case 40:
                // 获取最新坐式大腿伸展数据
                break;
            case 41:
                // 获取最新坐式飞鸟数据
                break;
            case 42:
                // 获取最新坐式后腿屈伸数据
                break;
            case 43:
                // 获取最新坐式提膝数据
                break;
            case 44:
                break;
            case 45:
                // 上传睡眠数据
                break;
            case 46:
                // 获取首页睡眠数据
                shp = context.getSharedPreferences("sleep",
                        Context.MODE_WORLD_WRITEABLE);
                editor = shp.edit();
                editor.clear();
                editor.putString(format.format(new Date()), json.getString("data"));
                editor.commit();
                PreferencesUtils.putString(context, "HomeSleepJson",
                        json.getString("data"));
                homeSleepData = gson.fromJson(json.getString("data"),
                        HomeSleepData.class);
                break;

            case 47:
                // 获取日期睡眠数据
                break;
            case 48:
                // 获取时期睡眠数据
                periodSleepData = gson.fromJson(json.getString("data"),
                        PeriodSleepData.class);

                manager = DBManager.getIntance(context, LocalDataUtils
                        .readCustomer(context).getId());
                manager.saveAll(periodSleepData.getDataList());
                GetPeriodSleepDataFromDB();
                break;
            case 49:
                break;
            case 50:
                // 获取首页体侧数据
                shp = context.getSharedPreferences("body",
                        Context.MODE_WORLD_WRITEABLE);
                editor = shp.edit();
                editor.clear();
                editor.putString(format.format(new Date()), json.getString("data"));
                editor.commit();
                homeBodyData = gson.fromJson(json.getString("data"),
                        HomeBodyData.class);
                break;
            case 51:
                // 获取日期段体重数据
                periodTizhongData = gson.fromJson(json.getString("data"),
                        PeriodTizhongData.class);
                BodyType bodyType = new BodyType();
                bodyType.type = 1;
                bodyType.name = "体重";
                bodyType.standard1 = String.valueOf(periodTizhongData.getStandard()
                        .getNormalCeil());
                bodyType.standard2 = String.valueOf(periodTizhongData.getStandard()
                        .getNormalFloor());
                bodyType.standard3 = String.valueOf(periodTizhongData.getStandard()
                        .getNormalValue());

                LinkedHashMap<String, Double> dataMap = (LinkedHashMap<String, Double>) periodTizhongData
                        .getDataMap();
                List<BodyHistory> list = new ArrayList<BodyHistory>();

                list = getData(dataMap, bodyType);

                TizhongStandard tizhongStandard = periodTizhongData.getStandard();
                periodTizhongData = new PeriodTizhongData();
                periodTizhongData.setStandard(tizhongStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                periodTizhongData.setDataMap(dataMap);
                break;
            case 52:
                // 获取日期段体质指数数据
                PeriodBmiData = gson.fromJson(json.getString("data"),
                        PeriodBmiData.class);

                bodyType = new BodyType();
                bodyType.type = 2;
                bodyType.name = "体质指数";
                bodyType.standard1 = String.valueOf(PeriodBmiData.getStandard()
                        .getNormalFloor());
                bodyType.standard2 = String.valueOf(PeriodBmiData.getStandard()
                        .getHeavierFloor());
                bodyType.standard3 = String.valueOf(PeriodBmiData.getStandard()
                        .getFatFloor());
                bodyType.standard4 = String.valueOf(PeriodBmiData.getStandard()
                        .getVeryFatFloor());

                dataMap = (LinkedHashMap<String, Double>) PeriodBmiData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                BmiStandard bmiStandard = PeriodBmiData.getStandard();
                PeriodBmiData = new PeriodBmiData();
                PeriodBmiData.setStandard(bmiStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                PeriodBmiData.setDataMap(dataMap);
                break;
            case 53:
                // 获取日期段身体脂肪率数据
                periodZhifanglvData = gson.fromJson(json.getString("data"),
                        PeriodZhifanglvData.class);

                bodyType = new BodyType();
                bodyType.type = 3;
                bodyType.name = "脂肪率";
                bodyType.standard1 = String.valueOf(periodZhifanglvData
                        .getStandard().getStandardFloor());
                bodyType.standard2 = String.valueOf(periodZhifanglvData
                        .getStandard().getMildFatFloor());
                bodyType.standard3 = String.valueOf(periodZhifanglvData
                        .getStandard().getFatFloor());

                dataMap = (LinkedHashMap<String, Double>) periodZhifanglvData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                ZhifanglvStandard zhifanglvStandard = periodZhifanglvData
                        .getStandard();
                periodZhifanglvData = new PeriodZhifanglvData();
                periodZhifanglvData.setStandard(zhifanglvStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                periodZhifanglvData.setDataMap(dataMap);
                break;
            case 54:
                // 获取日期段内脏脂肪指数数据
                periodNeizangzhifangData = gson.fromJson(json.getString("data"),
                        PeriodNeizangzhifangData.class);
                bodyType = new BodyType();
                bodyType.type = 4;
                bodyType.name = "内脏脂肪率";
                bodyType.standard1 = String.valueOf(periodNeizangzhifangData
                        .getStandard().getBalancedFloor());
                bodyType.standard2 = String.valueOf(periodNeizangzhifangData
                        .getStandard().getWarningFloor());
                bodyType.standard3 = String.valueOf(periodNeizangzhifangData
                        .getStandard().getFatFloor());
                bodyType.standard4 = String.valueOf(periodNeizangzhifangData
                        .getStandard().getVeryFatFloor());

                LinkedHashMap<String, Integer> dMap = (LinkedHashMap<String, Integer>) periodNeizangzhifangData
                        .getDataMap();
                list = getIntegerData(dMap, bodyType);
                NeizangzhifangStandard neizangzhifangStandard = periodNeizangzhifangData
                        .getStandard();
                periodNeizangzhifangData = new PeriodNeizangzhifangData();
                periodNeizangzhifangData.setStandard(neizangzhifangStandard);
                dMap = new LinkedHashMap<String, Integer>();

                for (BodyHistory bh : list) {
                    dMap.put(bh.date, Integer.parseInt(bh.value));
                }
                periodNeizangzhifangData.setDataMap(dMap);

                break;
            case 55:
                // 获取日期段腹部肥胖率数据
                periodFubufeipanglvData = gson.fromJson(json.getString("data"),
                        PeriodFubufeipanglvData.class);

                bodyType = new BodyType();
                bodyType.type = 5;
                bodyType.name = "腹部肥胖率";
                bodyType.standard1 = String.valueOf(periodFubufeipanglvData
                        .getStandard().getStandardFloor());
                bodyType.standard2 = String.valueOf(periodFubufeipanglvData
                        .getStandard().getStandardCeil());

                dataMap = (LinkedHashMap<String, Double>) periodFubufeipanglvData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                FubufeipanglvStandard fubufeipanglvStandard = periodFubufeipanglvData
                        .getStandard();
                periodFubufeipanglvData = new PeriodFubufeipanglvData();
                periodFubufeipanglvData.setStandard(fubufeipanglvStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                periodFubufeipanglvData.setDataMap(dataMap);
                break;
            case 56:
                // 获取日期段肌肉率数据

                periodJiroulvData = gson.fromJson(json.getString("data"),
                        PeriodJiroulvData.class);
                bodyType = new BodyType();
                bodyType.type = 6;
                bodyType.name = "肌肉率";
                bodyType.standard1 = String.valueOf(periodJiroulvData.getStandard()
                        .getStandardFloor());
                bodyType.standard2 = String.valueOf(periodJiroulvData.getStandard()
                        .getExcellentFloor());
                dataMap = (LinkedHashMap<String, Double>) periodJiroulvData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                JiroulvStandard jiroulvStandard = periodJiroulvData.getStandard();
                periodJiroulvData = new PeriodJiroulvData();
                periodJiroulvData.setStandard(jiroulvStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                periodJiroulvData.setDataMap(dataMap);
                break;

            case 57:
                // 获取日期段蛋白质数据
                periodDanbaizhiData = gson.fromJson(json.getString("data"),
                        PeriodDanbaizhiData.class);
                bodyType = new BodyType();
                bodyType.type = 8;
                bodyType.name = "蛋白质";
                bodyType.standard1 = String.valueOf(periodDanbaizhiData
                        .getStandard().getStandardFloor());
                bodyType.standard2 = String.valueOf(periodDanbaizhiData
                        .getStandard().getStandardCeil());
                dataMap = (LinkedHashMap<String, Double>) periodDanbaizhiData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                DanbaizhiStandard danbaizhiStandard = periodDanbaizhiData
                        .getStandard();
                periodDanbaizhiData = new PeriodDanbaizhiData();
                periodDanbaizhiData.setStandard(danbaizhiStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                periodDanbaizhiData.setDataMap(dataMap);
                break;
            case 58:
                // 获取日期段身体水分数据
                PeriodShuifenData = gson.fromJson(json.getString("data"),
                        PeriodShuifenData.class);
                bodyType = new BodyType();
                bodyType.type = 7;
                bodyType.name = "水分";
                bodyType.standard1 = String.valueOf(PeriodShuifenData.getStandard()
                        .getNormalCeil());
                bodyType.standard2 = String.valueOf(PeriodShuifenData.getStandard()
                        .getNormalFloor());

                dataMap = (LinkedHashMap<String, Double>) PeriodShuifenData
                        .getDataMap();
                list = getData(dataMap, bodyType);
                ShuifenStandard shuifenStandard = PeriodShuifenData.getStandard();
                PeriodShuifenData = new PeriodShuifenData();
                PeriodShuifenData.setStandard(shuifenStandard);
                dataMap = new LinkedHashMap<String, Double>();

                for (BodyHistory bh : list) {
                    dataMap.put(bh.date, Double.parseDouble(bh.value));
                }
                PeriodShuifenData.setDataMap(dataMap);

                break;
            case 59:
                // 获取日期段骨量数据
                periodGuliangData = gson.fromJson(json.getString("data"),
                        PeriodGuliangData.class);
                break;
            case 60:
                break;
            case 61:
                // 查询公告
                break;
            case 62:
                // 获取公告
                break;
            case 63:
                // 读取公告
                break;
            case 64:
                // 确认推送消息
                break;
            case 65:
                // 注册
                customer = gson.fromJson(json.getJSONObject("data").toString(),
                        Customer.class);
                // 保存登陆token
                LocalDataUtils.saveCustomer(context, customer);
                break;
            case 66:
                // 获取验证码
                verifyCode = gson
                        .fromJson(json.getString("data"), VerifyCode.class);
                System.out.println("verifyCode===" + json);
                break;
            case 67:
                // 检查验证码
                break;
            case 68:
                // 获取验证图片
                break;
            case 69:
                // 设置久坐提醒
                break;
            case 70:
                // 设置体测提醒
                System.out.println("体测提醒：" + json);
                break;
            case 71:
                // 设置运动提醒
                break;
            case 72:
                // 获取所有提醒
                // allRemind = gson.fromJson(json.getJSONObject("data").toString(),
                // AllRemind.class);
                PreferencesUtils.putString(context, Contants.ALLREMIND, json
                        .getJSONObject("data").toString());
                System.out.println("所有提醒：" + json);
                break;
            case 77:
                // 获取运动动画图片
                sportGifImageData = gson.fromJson(json.getString("data"),
                        SportGifImageData.class);
                imageNumber = sportGifImageData.getImageNumber();
                sportGifImagesList = sportGifImageData.getImageList();
                System.out.println("所有运动图片信息：" + json + "==" + sportGifImagesList + "==" + imageNumber);
                break;
            case 78:
                // 获取运动分享图片
                shareImageData = gson.fromJson(json.getString("data"),
                        ShareImageData.class);
                break;

            case 79:
                // 获取运动部位图片
                bodyImage = gson.fromJson(json.getString("data"),
                        BodyImage.class);
                System.out.println("所有bodyImage信息：" + json + "==" + bodyImage);
                break;
            case 800:

                String isBind = json.getString("data");
                PreferencesUtils.putString(context, Contants.ISBIND, isBind);
                break;
            case 80:
                //查询场馆圈日记
                queryVenueDiary = gson.fromJson(json.getString("data"),
                        QueryVenueDiary.class);
//                DBManager managers = DBManager.getIntance(context, customer.getId());
//                List<VenueDiary> diaryList = queryVenueDiary.getDiaryList();
//                managers.saveOrupdateAll(diaryList);
                break;
            case 81:
                //获取用户场馆列表
                venueList = gson.fromJson(json.getString("data"), VenueList.class);
                break;
            case 82:
                //保存运动日记，没有返回参数
                Log.d("BaseDao", "hfdh");
                break;
            case 83:
                //上传文件,返回为文件的地址
                imageurl = gson.fromJson(json.getString("data"), ImageUrl.class);
                break;
            case 84:
                //获取日记详情
                diaryDetail = gson.fromJson(json.getString("data"), DiaryDetail.class);
                break;
            case 85:
                //点赞

                break;
            case 86:
                //取消赞

                break;
            case 87:
                //评论

                break;
            case 88:
                //取消评论
            case 89:
                //删除日记
                break;
            case 90:
                //查询个人主页
                queryVenueDiary = gson.fromJson(json.getString("data"), QueryVenueDiary.class);
                break;
//
//            case 91:
//                //获取总排行
//                allRank = gson.fromJson(json.getString("data"), AllRank.class);
//                break;
//            case 92:
//                //获取个人排行
//                allRank = gson.fromJson(json.getString("data"), AllRank.class);
//                break;
//            case 93:
//                //获取成就
//                achievement = gson.fromJson(json.getString("data"), Achievement.class);
//                break;


            case 94:
                //获取未读消息
                msCount = gson.fromJson(json.getString("data"), MessageCount.class);
                break;
            case 95:
                mscontext = gson.fromJson(json.getString("data"), MessageContext.class);
                break;
            case 96:
                break;
            case 97:
                break;
            default:
                break;
        }
    }

    MessageCount msCount;

    public MessageCount getMsCount() {
        return msCount;
    }

    public void setMsCount(MessageCount msCount) {
        this.msCount = msCount;
    }

    public AllRemind getAllRemind() {
        return allRemind;
    }

    /**
     * 登陆
     */
    public void Login(String phone, String psd) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("phone", phone);
        params.addBodyParameter("password", psd);
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_login, params, 0);
    }

    /**
     * 登出
     */
    public void Logout() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_logout, params, 1);
    }

    /**
     * 绑定手环
     */
    public void BindWach(String bandId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("bandId", bandId);
        postRequest(HttpConnectUtils.api_bindBand, params, 2);
    }

    /**
     * 4
     * 绑定手环
     */
    public void CheckBindWach(String macAddress) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("bandId", macAddress);
        postRequest(HttpConnectUtils.api_checkbindBand, params, 800);
    }


    /**
     * 解绑手环
     */
    public void UnBindWach(String mac) {
        RequestParams params = new RequestParams();
        params.addBodyParameter("bandId", mac);
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_unbindBand, params, 3);
    }

    /**
     * 获取应用版本
     */
    public void GetAppVersion() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getAppVersion, params, 4);
    }

    /**
     * 客户上报启动
     */
    public void CreportLaunch() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_creportLaunch, params, 2001);
    }

    /**
     * 游客上报启动
     */
    public void TreportLaunch() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_treportLaunch, params, 2002);
    }

    /**
     * 获取手环版本
     */
    public void GetWatchVersion() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getBandVersion, params, 5);
    }

    /**
     * 上报推送令牌
     */
    public void ReportPushToken(String pushToken) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("pushToken", pushToken);// 推送令牌
        // params.addBodyParameter("pushChannel", "1");// 推送通道(1:APNS;2:GEXIN)
        // // (Short)
        postRequest(HttpConnectUtils.api_reportPushToken, params, 6);
    }

    /**
     * 上传图片
     *
     * @param imgpath 图片路径
     */
    public void UploadImage(String imgpath) {

        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("imageFile", new File(imgpath), "image/jpeg");
        postRequest(HttpConnectUtils.api_uploadImage, params, 7);
    }

    /**
     * 获取客户
     */
    public void GetCustomer() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getCustomer, params, 8);
    }

    /**
     * 修改客户
     */
    public void ModifyCustomer(Customer customer) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("name", customer.getName());
        params.addBodyParameter("avatar", customer.getAvatar());
        params.addBodyParameter("year", customer.getYear());
        params.addBodyParameter("gender", customer.getGender());
        params.addBodyParameter("height", customer.getHeight());
        params.addBodyParameter("weight", customer.getWeight());
        params.addBodyParameter("goalCalories", customer.getGoalCalories());// 目标卡里路
        params.addBodyParameter("description", customer.getDescription());
        postRequest(HttpConnectUtils.api_modifyCustomer, params, 9);
    }

    /**
     * 新增体重
     */
    public void ModifyCustomerWeight(double value) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("weight", String.valueOf(value));
        postRequest(HttpConnectUtils.api_modifyCustomer, params, 1000);
    }

    /**
     * 重置密码
     *
     * @param old    旧密码
     * @param newpsw 新密码
     */
    public void ModifyPassword(String old, String newpsw) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("oldPassword", old);
        params.addBodyParameter("newPassword", newpsw);
        postRequest(HttpConnectUtils.api_modifyPassword, params, 10);
    }

    /**
     * 重置密码
     *
     * @param phone 电话
     * @param code  验证码
     * @param psd   密码
     */

    public void ResetPassword(String phone, String code, String psd) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", phone); // 客户电话
        params.addBodyParameter("verifyCode", code);// 验证码
        params.addBodyParameter("newPassword", psd); // 新客户密码
        postRequest(HttpConnectUtils.api_resetPassword, params, 11);
    }

    /**
     * 提交反馈
     *
     * @param phoneemail
     * @param backcontext
     */
    public void CommitFeedback(String backcontext, String phoneemail) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("content", backcontext);
        postRequest(HttpConnectUtils.api_commitFeedback, params, 12);
    }

    /**
     * 上传步行数据
     */
    public void UploadWalkData(String json) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("walkData", json);// 步行数据(JSON)
        UtilLog.e(TAG, "uploadWalkData-->" + json);
        postRequest(HttpConnectUtils.api_uploadWalkData, params, 13);
    }

    /**
     * 获取首页运动数据
     */
    public void GetHomeSportData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        Log.v("MyLog", "_______-----" + params.toString());
        postRequest(HttpConnectUtils.api_getHomeSportData, params, 14);
    }

    /**
     * 获取日期运动数据
     */
    public void GetDateSportData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("date", "2015/6/6");// 指定日期
        postRequest(HttpConnectUtils.api_getDateSportData, params, 15);
    }

    /**
     * 获取时期运动数据
     */
    public void GetPeriodSportData(String beginDate, String endDate) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期
        params.addBodyParameter("endDate", endDate);// 结束日期
        postRequest(HttpConnectUtils.api_getPeriodSportData, params, 16);
    }

    /**
     * 获取时期运动数据-本地数据库
     */
    public boolean GetPeriodSportDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(DailyTotalSportData.class)
                .where(WhereBuilder.b("1", "==", 1)).orderBy("id", true)
                .limit(56);
        List<DailyTotalSportData> dsdList = (ArrayList<DailyTotalSportData>) dbManager
                .getAllObject(DailyTotalSportData.class, selector);

        for (DailyTotalSportData dailyTotalSportData : dsdList) {
            selector = Selector
                    .from(OnceSportData.class)
                    .where(WhereBuilder.b("statDate", "==",
                            dailyTotalSportData.getStatDate()))
                    .orderBy("id", false);
            List<OnceSportData> osdList = (ArrayList<OnceSportData>) dbManager
                    .getAllObject(OnceSportData.class, selector);
            dailyTotalSportData.setOnceList(osdList);
        }

        boolean isLasted = false;
        DailyTotalSportData dsd = null;

        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.getStatDate().equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else if (dsd.getStatDate().compareTo(endDate) > 0) {// 最新日期大于请求的最后日期，清除本地数据库内容
                isLasted = false;
                dbManager.clearTable(DailyTotalSportData.class);
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.getStatDate());
            }

        }
        if (isLasted) {
            Collections.reverse(dsdList);
            periodSportData = new PeriodSportData();
            periodSportData.setDailyList(dsdList);
        } else {
            // if(dsd!=null){
            // dbManager.deleteObject(DailyTotalSportData.class);
            // dbManager.deleteObject(OnceSportData.class);
            // }
            GetPeriodSportData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取时期运动数据-本地数据库
     */
    public void GetPeriodSportDataFromDB() {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(DailyTotalSportData.class)
                .where(WhereBuilder.b("1", "==", 1)).orderBy("id", true)
                .limit(56);
        List<DailyTotalSportData> dsdList = (ArrayList<DailyTotalSportData>) dbManager
                .getAllObject(DailyTotalSportData.class, selector);

        for (DailyTotalSportData dailyTotalSportData : dsdList) {
            selector = Selector
                    .from(OnceSportData.class)
                    .where(WhereBuilder.b("statDate", "==",
                            dailyTotalSportData.getStatDate()))
                    .orderBy("id", false);
            List<OnceSportData> osdList = (ArrayList<OnceSportData>) dbManager
                    .getAllObject(OnceSportData.class, selector);
            dailyTotalSportData.setOnceList(osdList);
        }
        Collections.reverse(dsdList);
        periodSportData = new PeriodSportData();
        periodSportData.setDailyList(dsdList);

    }

    /**
     * 获取最新背脊伸展数据
     */
    public void GetLatestBeiJiShenZhanData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestBeiJiShenZhanData, params, 17);
    }

    /**
     * 获取最新搏击操数据
     */
    public void GetLatestBoJiCaoData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestBoJiCaoData, params, 18);
    }

    /**
     * 获取最新步行数据
     */
    public void GetLatestBuXingData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestBuXingData, params, 19);
    }

    /**
     * 获取最新大腿内侧肌数据
     */
    public void GetLatestDaTuiNeiCeJiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestDaTuiNeiCeJiData, params, 20);
    }

    /**
     * 获取最新大腿外侧肌数据
     */
    public void GetLatestDaTuiWaiCeJiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestDaTuiWaiCeJiData, params, 21);
    }

    /**
     * 获取最新动感单车数据
     */
    public void GetLatestDongGanDanCheData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestDongGanDanCheData, params, 22);
    }

    /**
     * 获取最新二头肌双向数据
     */
    public void GetLatestErTouJiShuangXiangData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestErTouJiShuangXiangData,
                params, 23);
    }

    /**
     * 获取最新杠铃操数据
     */
    public void GetLatestGangLingCaoData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestGangLingCaoData, params, 24);
    }

    /**
     * 获取最新划艇拉里数据
     */
    public void GetLatestHuaTingLaLiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestHuaTingLaLiData, params, 25);
    }

    /**
     * 获取最新蝴蝶式扩胸数据
     */
    public void GetLatestHuDieShiKuoXiongData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestHuDieShiKuoXiongData, params,
                26);
    }

    /**
     * 获取最新肩膀后展数据
     */
    public void GetLatestJianBangHouZhanData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestJianBangHouZhanData, params,
                27);
    }

    /**
     * 获取最新肩膀推举数据
     */
    public void GetLatestJianBangTuiJuData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestJianBangTuiJuData, params, 28);
    }

    /**
     * 获取最新拉力背肌数据
     */
    public void GetLatestLaLiBeiJiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestLaLiBeiJiData, params, 29);
    }

    /**
     * 获取最新立式大腿屈伸数据
     */
    public void GetLatestLiShiDaTuiQuShenData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestLiShiDaTuiQuShenData, params,
                30);
    }

    /**
     * 获取最新立式大腿伸展数据
     */
    public void GetLatestLiShiDaTuiShenZhanData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestLiShiDaTuiShenZhanData,
                params, 31);
    }

    /**
     * 获取最新跑步机数据
     */
    public void GetLatestPaoBuJiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestPaoBuJiData, params, 32);
    }

    /**
     * 获取最新三头肌双向数据
     */
    public void GetLatestSanTouJiShuangXiangData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestSanTouJiShuangXiangData,
                params, 33);
    }

    /**
     * 获取最新双向推胸数据
     */
    public void GetLatestShuangXiangTuiXiongData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestShuangXiangTuiXiongData,
                params, 34);
    }

    /**
     * 获取最新调节式蹬腿数据
     */
    public void GetLatestTiaoJieDengTuiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestTiaoJieDengTuiData, params,
                35);
    }

    /**
     * 获取最新椭圆机数据
     */
    public void GetLatestTuoYuanJiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestTuoYuanJiData, params, 36);
    }

    /**
     * 获取最新卧式腿屈展数据
     */
    public void GetLatestWoShiTuiQuZhanData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestWoShiTuiQuZhanData, params,
                37);
    }

    /**
     * 获取最新有氧舞蹈数据
     */
    public void GetLatestYouYangWuDaoData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestYouYangWuDaoData, params, 38);
    }

    /**
     * 获取最新游泳数据
     */
    public void getLatestYouYongData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestYouYongData, params, 39);
    }

    /**
     * 获取最新坐式大腿伸展数据
     */
    public void GetLatestZuoShiDaTuiShenZhanData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestZuoShiDaTuiShenZhanData,
                params, 40);
    }

    /**
     * 获取最新坐式飞鸟数据
     */
    public void GetLatestZuoShiFeiNiaoData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestZuoShiFeiNiaoData, params, 41);
    }

    /**
     * 获取最新坐式后腿屈伸数据
     */
    public void GetLatestZuoShiHouTuiQuShenData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestZuoShiHouTuiQuShenData,
                params, 42);
    }

    /**
     * 获取最新坐式提膝数据
     */
    public void GetLatestZuoShiTiXiData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getLatestZuoShiTiXiData, params, 43);
    }

    /**
     * 上传睡眠数据
     */
    public void UploadSleepData(String json) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("sleepData", json);// 睡眠数据(JSON)
        postRequest(HttpConnectUtils.api_uploadSleepData, params, 45);
    }

    /**
     * 获取首页睡眠数据
     */
    public void GetHomeSleepDat() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getHomeSleepData, params, 46);
    }

    /**
     * 获取日期睡眠数据
     */
    public void GetDateSleepData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("date", "");// 指定日期
        postRequest(HttpConnectUtils.api_getDateSleepData, params, 47);
    }

    /**
     * 获取时期睡眠数据-服务器
     */
    public void GetPeriodSleepData(String beginDate, String endDate) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate);// 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodSleepData, params, 48);
    }

    /**
     * 获取最新睡眠数据日期--数据库
     */
    public boolean GetPeriodSleepDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(DailySleepData.class)
                .where(WhereBuilder.b("1", "==", 1)).orderBy("id", true)
                .limit(56);
        List<DailySleepData> dsdList = (ArrayList<DailySleepData>) dbManager
                .getAllObject(DailySleepData.class, selector);
        boolean isLasted = false;
        DailySleepData dsd = null;
        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.getStatDate().equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else if (dsd.getStatDate().compareTo(endDate) > 0) {// 最新日期大于请求的最后日期，清除本地数据库内容
                isLasted = false;
                dbManager.clearTable(DailySleepData.class);
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.getStatDate());
            }

        }
        if (isLasted) {
            Collections.reverse(dsdList);
            periodSleepData = new PeriodSleepData();
            periodSleepData.setDataList(dsdList);
        } else {
            // if(dsd!=null){
            // dbManager.deleteObject(DailySleepData.class);
            // }
            GetPeriodSleepData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取最新睡眠数据日期--数据库
     */
    public void GetPeriodSleepDataFromDB() {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(DailySleepData.class)
                .where(WhereBuilder.b("1", "==", 1)).orderBy("id", true)
                .limit(56);
        List<DailySleepData> dsdList = (ArrayList<DailySleepData>) dbManager
                .getAllObject(DailySleepData.class, selector);
        Collections.reverse(dsdList);
        periodSleepData = new PeriodSleepData();
        periodSleepData.setDataList(dsdList);
    }

    /**
     * 获取首页体侧数据
     */
    public void GetHomeBodyData() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getHomeBodyData, params, 50);
    }


    /**
     * 获取日期段体重数据
     */
    public void GetPeriodTizhongData(String beginDate, String endDate) {
        // beginDate=getBeginData(DataType.one,beginDate,endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodTizhongData, params, 51);

    }

    /**
     * 获取日期段体重数据-数据库
     */
    public boolean GetPeriodTizhongDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 1)).orderBy("id", true)
                .limit(55);

        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 1));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);

        boolean isLasted = false;
        BodyHistory dsd = null;

        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else if (dsd.date.compareTo(endDate) > 0) {// 最新日期大于请求的最后日期，清除本地数据库内容
                isLasted = false;
                dbManager.clearTable(BodyHistory.class);
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }
        if (isLasted) {
            periodTizhongData = new PeriodTizhongData();
            if (type != null) {
                TizhongStandard ts = new TizhongStandard();
                ts.setNormalCeil(Double.parseDouble(type.standard1));//
                ts.setNormalFloor(Double.parseDouble(type.standard2));
                ts.setNormalValue(Double.parseDouble(type.standard3));
                periodTizhongData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                periodTizhongData.setDataMap(map);
            }

        } else {
            GetPeriodTizhongData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段体质指数数据
     */
    public void GetPeriodBmiData(String beginDate, String endDate) {
        // beginDate=getBeginData(DataType.two,beginDate,endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodBmiData, params, 52);
    }

    /**
     * 获取日期段体质指数数据-数据库
     */
    public boolean GetPeriodBmiDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 2)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 2));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);

        boolean isLasted = false;
        BodyHistory dsd = null;

        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }

        if (isLasted) {
            PeriodBmiData = new PeriodBmiData();
            if (type != null) {
                BmiStandard ts = new BmiStandard();
                ts.setNormalFloor(Double.parseDouble(type.standard1));//
                ts.setHeavierFloor(Double.parseDouble(type.standard2));
                ts.setFatFloor(Double.parseDouble(type.standard3));
                ts.setVeryFatFloor(Double.parseDouble(type.standard4));
                PeriodBmiData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                PeriodBmiData.setDataMap(map);
            }

        } else {
            GetPeriodBmiData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段身体脂肪率数据
     */
    public void GetPeriodZhifanglvData(String beginDate, String endDate) {
        // beginDate=getBeginData(DataType.threee,beginDate,endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodZhifanglvData, params, 53);
    }

    /**
     * 获取日期段身体脂肪率数据-数据库
     */
    public boolean GetPeriodZhifanglvDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 3)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 3));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);

        boolean isLasted = false;
        BodyHistory dsd = null;

        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }

        if (isLasted) {
            periodZhifanglvData = new PeriodZhifanglvData();
            if (type != null) {
                ZhifanglvStandard ts = new ZhifanglvStandard();
                ts.setStandardFloor(Double.parseDouble(type.standard1));//
                ts.setMildFatFloor(Double.parseDouble(type.standard2));
                ts.setFatFloor(Double.parseDouble(type.standard3));
                periodZhifanglvData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                periodZhifanglvData.setDataMap(map);
            }

        } else {
            GetPeriodZhifanglvData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段内脏脂肪指数数据
     */
    public void GetPeriodNeizangzhifangData(String beginDate, String endDate) {
        // beginDate = getBeginData(DataType.four, beginDate, endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodNeizangzhifangData, params,
                54);
    }

    /**
     * 获取日期段内脏脂肪指数数据-数据库
     */
    public boolean GetPeriodNeizangzhifangDataFromDB(String beginDate,
                                                     String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 4)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 4));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);

        boolean isLasted = false;
        BodyHistory dsd = null;

        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }

        if (isLasted) {
            periodNeizangzhifangData = new PeriodNeizangzhifangData();
            if (type != null) {
                NeizangzhifangStandard ts = new NeizangzhifangStandard();
                ts.setBalancedFloor(Integer.parseInt(type.standard1));//
                ts.setWarningFloor(Integer.parseInt(type.standard2));
                ts.setFatFloor(Integer.parseInt(type.standard3));
                ts.setVeryFatFloor(Integer.parseInt(type.standard4));
                periodNeizangzhifangData.setStandard(ts);
                Map<String, Integer> map = new LinkedHashMap<String, Integer>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Integer.parseInt(bh.value));
                }
                periodNeizangzhifangData.setDataMap(map);
            }

        } else {
            GetPeriodNeizangzhifangData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段腹部肥胖率数据
     */
    public void GetPeriodFubufeipanglvData(String beginDate, String endDate) {
        // beginDate = getBeginData(DataType.five, beginDate, endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodFubufeipanglvData, params, 55);
    }

    /**
     * 获取日期段腹部肥胖率数据-数据库
     */
    public boolean GetPeriodFubufeipanglvDataFromDB(String beginDate,
                                                    String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 5)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 5));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);

        boolean isLasted = false;
        BodyHistory dsd = null;
        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }

        if (isLasted) {
            periodFubufeipanglvData = new PeriodFubufeipanglvData();
            if (type != null) {
                FubufeipanglvStandard ts = new FubufeipanglvStandard();
                ts.setStandardFloor(Double.parseDouble(type.standard1));//
                ts.setStandardCeil(Double.parseDouble(type.standard2));
                periodFubufeipanglvData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                periodFubufeipanglvData.setDataMap(map);
            }

        } else {
            GetPeriodFubufeipanglvData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段肌肉率数据
     */
    public void GetPeriodJiroulvData(String beginDate, String endDate) {
        // beginDate = getBeginData(DataType.six, beginDate, endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodJiroulvData, params, 56);

    }

    /**
     * 获取日期段肌肉率数据-数据库
     */
    public boolean GetPeriodJiroulvDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 6)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 6));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);
        boolean isLasted = false;
        BodyHistory dsd = null;
        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }

        if (isLasted) {
            periodJiroulvData = new PeriodJiroulvData();
            if (type != null) {
                JiroulvStandard ts = new JiroulvStandard();
                ts.setStandardFloor(Double.parseDouble(type.standard1));//
                ts.setExcellentFloor(Double.parseDouble(type.standard2));
                periodJiroulvData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                periodJiroulvData.setDataMap(map);
            }

        } else {
            GetPeriodJiroulvData(beginDate, endDate);
        }
        return isLasted;

    }

    /**
     * 获取日期段蛋白质数据
     */
    public void GetPeriodDanbaizhiData(String beginDate, String endDate) {
        beginDate = getBeginData(DataType.eigth, beginDate, endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodDanbaizhiData, params, 57);
    }

    /**
     * 获取日期段蛋白质数据-数据库
     */
    public boolean GetPeriodDanbaizhiDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 8)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 8));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);
        boolean isLasted = false;
        BodyHistory dsd = null;
        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }
        if (isLasted) {
            periodDanbaizhiData = new PeriodDanbaizhiData();
            if (type != null) {
                DanbaizhiStandard ts = new DanbaizhiStandard();
                ts.setStandardFloor(Double.parseDouble(type.standard1));//
                ts.setStandardCeil(Double.parseDouble(type.standard2));
                periodDanbaizhiData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                periodDanbaizhiData.setDataMap(map);
            }

        } else {
            GetPeriodDanbaizhiData(beginDate, endDate);
        }
        return isLasted;
    }

    /**
     * 获取日期段身体水分数据
     */
    public void GetPeriodShuifenData(String beginDate, String endDate) {
        // beginDate = getBeginData(DataType.seven, beginDate, endDate);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodShuifenData, params, 58);
    }

    /**
     * 获取日期段身体水分数据-数据库
     */
    public boolean GetPeriodShuifenDataFromDB(String beginDate, String endDate) {
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", 7)).orderBy("id", true)
                .limit(55);
        List<BodyHistory> dsdList = (ArrayList<BodyHistory>) dbManager
                .getAllObject(BodyHistory.class, selector);
        selector = Selector.from(BodyType.class).where(
                WhereBuilder.b("type", "==", 7));
        BodyType type = (BodyType) dbManager
                .getObject(BodyType.class, selector);
        boolean isLasted = false;
        BodyHistory dsd = null;
        if (dsdList.size() > 0) {
            dsd = dsdList.get(0);
            if (dsd.date.equalsIgnoreCase(endDate)) {
                isLasted = true;
            } else {
                isLasted = false;
                beginDate = getSpecifiedDayAfter(dsd.date);
            }

        }
        if (isLasted) {
            PeriodShuifenData = new PeriodShuifenData();
            if (type != null) {
                ShuifenStandard ts = new ShuifenStandard();
                ts.setNormalCeil(Double.parseDouble(type.standard1));//
                ts.setNormalFloor(Double.parseDouble(type.standard2));
                PeriodShuifenData.setStandard(ts);
                Map<String, Double> map = new LinkedHashMap<String, Double>();
                for (BodyHistory bh : dsdList) {
                    map.put(bh.date, Double.parseDouble(bh.value));
                }
                PeriodShuifenData.setDataMap(map);
            }

        } else {
            GetPeriodShuifenData(beginDate, endDate);
        }
        return isLasted;

    }

    /**
     * 获取日期段骨量数据
     */
    public void GetPeriodGuliangData(String beginDate, String endDate) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("beginDate", beginDate);// 开始日期(yyyy-MM-dd)
        params.addBodyParameter("endDate", endDate); // 结束日期(yyyy-MM-dd)
        postRequest(HttpConnectUtils.api_getPeriodGuliangData, params, 59);
    }

    /**
     * 查询公告
     */
    public void QueryNotice() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("readStatus", "");// 读取状态(1:未读;2:已读) (Short)
        params.addBodyParameter("startIndex", "");// 开始序号 (Integer)
        params.addBodyParameter("pageSize", "");// 页面大小 (Integer)
        postRequest(HttpConnectUtils.api_queryNotice, params, 61);
    }

    /**
     * 获取公告
     */
    public void GetNotice() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("noticeId", "");// 公告标识
        postRequest(HttpConnectUtils.api_getNotice, params, 62);
    }

    /**
     * 读取公告
     */
    public void ReadNotice() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("noticeId", "");// 公告标识
        postRequest(HttpConnectUtils.api_readNotice, params, 63);
    }

    /**
     * 确认推送消息
     */
    public void AckPushMessage() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("messageId", "");// 消息标识(Long)
        params.addBodyParameter("pushChannel", "");// 推送通道(1:APNS;2:GEXIN)(Short)
        postRequest(HttpConnectUtils.api_ackPushMessage, params, 64);
    }

    /**
     * 注册
     *
     * @param phone 电话号码
     * @param code  验证码
     * @param psd   密码
     */

    public void Register(String phone, String code, String psd) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", phone); // 客户电话
        params.addBodyParameter("verifyCode", code);// 验证码
        params.addBodyParameter("password", psd); // 新客户密码
        postRequest(HttpConnectUtils.api_register, params, 65);
    }

    /**
     * 获取验证码
     *
     * @param mobile 客户电话
     * @param type   短信类型(1:注册用户;2:找回密码) (Short)
     */
    public void GetVerifyCode(String mobile, String type) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", mobile); // 客户电话
        params.addBodyParameter("type", type); // 短信类型(1:注册用户;2:找回密码) (Short)
        postRequest(HttpConnectUtils.api_getVerifyCode, params, 66);
    }

    /**
     * 获取验证码
     *
     * @param mobile 客户电话
     * @param type   短信类型(1:注册用户;2:找回密码) (Short)
     */
    public void GetVerifyCode(String mobile, String type, String imageCode) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", mobile); // 客户电话
        params.addBodyParameter("type", type); // 短信类型(1:注册用户;2:找回密码) (Short)
        params.addBodyParameter("imageCode", imageCode);
        postRequest(HttpConnectUtils.api_getVerifyCode, params, 66);
    }

    /**
     * 检查验证码
     *
     * @param phone      客户电话
     * @param verifyCode 短信类型(1: 注册用户,2:找回密码)
     * @param type
     */
    public void CheckVerifyCode(String phone, String type, String verifyCode) {
        Log.v("MyLog", phone + "-------" + type + "----------" + verifyCode);
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", phone);// 客户电话
        params.addBodyParameter("type", type);// 验证码
        params.addBodyParameter("verifyCode", verifyCode);// 新客户密码
        postRequest(HttpConnectUtils.api_checkVerifyCode, params, 67);
    }

    /**
     * 获取验证图片
     */
    public void GetVerifyImage(String phone) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("phone", phone);// 客户电话
        // params.addBodyParameter("type", type); // 短信类型(1:注册用户;2:找回密码) (Short)
        postRequest(HttpConnectUtils.api_getVerifyImage, params, 68);
    }

    // 根据返回的值，保存返回到数据库，然后读取本地最新的数据
    public ArrayList<BodyHistory> getData(
            LinkedHashMap<String, Double> dataMap, BodyType bodyType) {

        ArrayList<BodyHistory> list = new ArrayList<BodyHistory>();
        Set<String> set = dataMap.keySet();

        for (String key : set) {
            try {
                BodyHistory bh = new BodyHistory();
                bh.date = key;
                bh.type = bodyType.type;
                bh.value = String.valueOf(dataMap.get(key));
                list.add(bh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        dbManager.save(bodyType);
        dbManager.saveAll(list);

        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", bodyType.type))
                .orderBy("id", true).limit(55);
        list = (ArrayList<BodyHistory>) dbManager.getAllObject(
                BodyHistory.class, selector);
        return list;
    }

    // 根据返回的值，保存返回到数据库，然后读取本地最新的数据
    public ArrayList<BodyHistory> getIntegerData(
            LinkedHashMap<String, Integer> dataMap, BodyType bodyType) {

        ArrayList<BodyHistory> list = new ArrayList<BodyHistory>();
        Set<String> set = dataMap.keySet();

        for (String key : set) {
            try {
                BodyHistory bh = new BodyHistory();
                bh.date = key;
                bh.type = bodyType.type;
                bh.value = String.valueOf(dataMap.get(key));
                list.add(bh);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        dbManager.save(bodyType);
        dbManager.saveAll(list);

        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", bodyType.type))
                .orderBy("id", true).limit(55);
        list = (ArrayList<BodyHistory>) dbManager.getAllObject(
                BodyHistory.class, selector);
        return list;
    }

    // 获取应该取值的起始天
    private String getBeginData(DataType type, String beginDate, String endDate) {
        // TODO Auto-generated method stub
        DBManager dbManager = DBManager.getIntance(context, LocalDataUtils
                .readCustomer(context).getId());
        Selector selector = Selector.from(BodyHistory.class)
                .where(WhereBuilder.b("type", "==", type)).orderBy("id", true);

        BodyHistory bh = (BodyHistory) dbManager.getObject(BodyHistory.class,
                selector);
        // 如果本地存在，则读取本地的记录
        if (bh != null && bh.date.compareToIgnoreCase(endDate) <= 0) {
            dbManager.deleteObject(bh);
            beginDate = bh.date;
        }
        return beginDate;
    }

    public static Date dateAdd(int days) {
        // 日期处理模块 (将日期加上某些天或减去天数)返回字符串
        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, days); // 日期减 如果不够减会将月变动
        return canlendar.getTime();
    }

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay) {
        // SimpleDateFormat simpleDateFormat = new
        // SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 1);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后一天
     *
     * @param specifiedDay
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + 1);

        String dayAfter = new SimpleDateFormat("yyyy-MM-dd")
                .format(c.getTime());
        return dayAfter;
    }

    public void cancel() {
        if (httpHandler != null && !httpHandler.isCancelled()) {
            httpHandler.cancel(true);
        }
    }

    public boolean isRunning() {

        if (httpHandler != null)
            return httpHandler.isCancelled();
        return false;
    }

    /**
     * 设置久坐提醒
     *
     * @param isEnabled
     * @param sedentaryTime
     * @param bremindTime
     * @param eremindTime
     */
    public void setSedentaryRemind(String isEnabled, String sedentaryTime,
                                   String bremindTime, String eremindTime) {
        RequestParams params = new RequestParams();

        Utiles.Add(context, params);
        params.addBodyParameter("isEnabled", isEnabled);
        params.addBodyParameter("sedentaryTime", sedentaryTime);
        params.addBodyParameter("bremindTime", bremindTime);
        params.addBodyParameter("eremindTime", eremindTime);
        postRequest(HttpConnectUtils.api_setSedentaryRemind, params, 69);
    }

    /**
     * 设置体测提醒
     *
     * @param isEnabled
     * @param remindWeekday
     * @param remindTime
     */
    public void setBodyRemind(String isEnabled, String remindWeekday,
                              String remindTime) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("isEnabled", isEnabled);
        params.addBodyParameter("remindWeekday", remindWeekday);
        params.addBodyParameter("remindTime", remindTime);
        postRequest(HttpConnectUtils.api_setBodyRemind, params, 70);

    }

    /**
     * 设置运动提醒
     *
     * @param isEnabled
     * @param remindTime
     */
    public void setSportRemind(String isEnabled, String remindTime) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("isEnabled", isEnabled);
        params.addBodyParameter("remindTime", remindTime);
        postRequest(HttpConnectUtils.api_setSportRemind, params, 71);
    }

    /**
     * 获取所有提醒
     *
     * @param sportRemind
     * @param sedentaryRemind
     * @param bodyRemind
     */
    public void getAllRemind(String sportRemind, String sedentaryRemind,
                             String bodyRemind) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("sportRemind", sportRemind);
        params.addBodyParameter("sedentaryRemind", sedentaryRemind);
        params.addBodyParameter("bodyRemind", bodyRemind);
        postRequest(HttpConnectUtils.api_getAllRemind, params, 72);
    }

    /**
     * 获取所有提醒
     *
     * @param thirdType
     * @param thirdId
     * @param thirdToken
     * @param name
     * @param avatar
     */
    public void doThirdLogin(String thirdType, String thirdId,
                             String thirdToken, String name, String avatar) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("thirdType", thirdType);
        params.addBodyParameter("thirdId", thirdId);
        params.addBodyParameter("thirdToken", thirdToken);
        params.addBodyParameter("name", name);
        params.addBodyParameter("avatar", avatar);
        postRequest(HttpConnectUtils.api_thirdLogin, params, 0);
    }

    /**
     * QQ健康，计步类接口
     *
     * @param stepsData
     */
    public void setQQSteps(QQStepsData stepsData) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("access_token", stepsData.getAccess_token());
        params.addBodyParameter("oauth_consumer_key",
                stepsData.getOauth_consumer_key());
        params.addBodyParameter("openid", stepsData.getOpenid());
        params.addBodyParameter("pf", "qzone");

        params.addBodyParameter("time", stepsData.getTime());
        params.addBodyParameter("distance", stepsData.getDistance());
        params.addBodyParameter("steps", stepsData.getSteps());
        params.addBodyParameter("duration", stepsData.getDuration());
        params.addBodyParameter("calories", stepsData.getCalories());
        params.addBodyParameter("achieve", stepsData.getAchieve());
        params.addBodyParameter("target", stepsData.getTarget());
        params.addBodyParameter("pm25", stepsData.getPm25());

        postRequest(HttpConnectUtils.QQ_HEALTH_STEPS, params, 73);
    }

    /**
     * QQ健康，跑步类接口
     *
     * @param runData
     */
    public void setQQRun(QQRunData runData) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("access_token", runData.getAccess_token());
        params.addBodyParameter("oauth_consumer_key",
                runData.getOauth_consumer_key());
        params.addBodyParameter("openid", runData.getOpenid());
        params.addBodyParameter("pf", "qzone");

        params.addBodyParameter("time", runData.getTime());
        params.addBodyParameter("distance", runData.getDistance());
        params.addBodyParameter("steps", runData.getSteps());
        params.addBodyParameter("duration", runData.getDuration());
        params.addBodyParameter("calories", runData.getCalories());

        params.addBodyParameter("speed", runData.getSpeed());
        params.addBodyParameter("route", runData.getRoute());

        params.addBodyParameter("achieve", runData.getAchieve());
        params.addBodyParameter("target", runData.getTarget());
        params.addBodyParameter("pm25", runData.getPm25());

        postRequest(HttpConnectUtils.QQ_HEALTH_RUN, params, 74);
    }

    /**
     * QQ健康，睡眠类接口
     *
     * @param sleepData
     */
    public void setQQSleep(QQSleepData sleepData) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("access_token", sleepData.getAccess_token());
        params.addBodyParameter("oauth_consumer_key",
                sleepData.getOauth_consumer_key());
        params.addBodyParameter("openid", sleepData.getOpenid());
        params.addBodyParameter("pf", "qzone");

        params.addBodyParameter("end_time", sleepData.getEnd_time());
        params.addBodyParameter("start_time", sleepData.getStart_time());
        params.addBodyParameter("total_time", sleepData.getTotal_time());
        params.addBodyParameter("light_sleep", sleepData.getLight_sleep());
        params.addBodyParameter("deep_sleep", sleepData.getDeep_sleep());

        params.addBodyParameter("awake_time", sleepData.getAwake_time());
        params.addBodyParameter("goal", sleepData.getGoal());

        params.addBodyParameter("detail", sleepData.getDetail());
        params.addBodyParameter("sleep_quality", sleepData.getSleep_quality());
        params.addBodyParameter("heart_rate", sleepData.getHeart_rate());
        params.addBodyParameter("snore", sleepData.getSnore());
        postRequest(HttpConnectUtils.QQ_HEALTH_SLEEP, params, 75);
    }

    /**
     * QQ健康，体重类接口
     *
     * @param weightData
     */
    public void setQQWeight(QQWeightData weightData) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("access_token", weightData.getAccess_token());
        params.addBodyParameter("oauth_consumer_key",
                weightData.getOauth_consumer_key());
        params.addBodyParameter("openid", weightData.getOpenid());
        params.addBodyParameter("pf", "qzone");

        params.addBodyParameter("time", weightData.getTime());
        params.addBodyParameter("weight", weightData.getWeight());
        params.addBodyParameter("weight_result", weightData.getWeight_result());
        params.addBodyParameter("fat_per", weightData.getFat_per());
        params.addBodyParameter("fat_result", weightData.getFat_result());

        params.addBodyParameter("bmi", weightData.getBmi());
        params.addBodyParameter("bmi_result", weightData.getBmi_result());

        params.addBodyParameter("muscle_per", weightData.getMuscle_per());
        params.addBodyParameter("muscle_result", weightData.getMuscle_result());
        params.addBodyParameter("bolism_per", weightData.getBolism_per());
        params.addBodyParameter("bolism_result", weightData.getBolism_result());

        params.addBodyParameter("bone_weight", weightData.getBone_weight());
        params.addBodyParameter("bone_result", weightData.getBone_result());
        params.addBodyParameter("fat_index", weightData.getFat_index());
        params.addBodyParameter("index_result", weightData.getIndex_result());
        params.addBodyParameter("warter_per", weightData.getWarter_per());
        params.addBodyParameter("water_result", weightData.getWater_result());
        params.addBodyParameter("protei_per", weightData.getProtei_per());
        params.addBodyParameter("protei_result", weightData.getProtei_result());
        params.addBodyParameter("weight_target", weightData.getWeight_target());

        postRequest(HttpConnectUtils.QQ_HEALTH_WEIGHT, params, 76);
    }

    /**
     * 获取所有运动的GIF
     *
     * @param sportTypeId
     * @param size
     */
    public void getSportGifImageData(int sportTypeId, int size) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("sportTypeId", String.valueOf(sportTypeId));
        params.addBodyParameter("size", String.valueOf(size));
        postRequest(HttpConnectUtils.api_getSportGifImageData, params, 77);
    }
    public void getSportSumData(int sportTypeId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("sportTypeId", String.valueOf(sportTypeId));
        postRequest(HttpConnectUtils.api_getSumSportData, params, 770);
    }

    /**
     * 获取运动分享图片
     */
    public void getShareImageData(int typeId, int size) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("typeId", String.valueOf(typeId));
        params.addBodyParameter("size", String.valueOf(size));
        postRequest(HttpConnectUtils.api_getShareImageData, params,
                78);
    }

    /**
     * 获取训练部位的图片
     *
     * @param sportTypeId
     * @param size
     */
    public void getBodyImageData(int sportTypeId, int courseTypeId, int gender, int size) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("sportTypeId", String.valueOf(sportTypeId));
//		if(sportTypeId==28){
        params.addBodyParameter("courseTypeId", String.valueOf(courseTypeId));
//		}else{
//
//		}
        params.addBodyParameter("gender", String.valueOf(gender));
        params.addBodyParameter("size", String.valueOf(size));
        System.out.print("sportTypeId==" + String.valueOf(sportTypeId) + "==courseTypeId==" + String.valueOf(courseTypeId) +
                "==gender==" + String.valueOf(gender) + "==size==" + String.valueOf(size));
        postRequest(HttpConnectUtils.api_getBodyImageData, params, 79);
    }

    /**
     * 查询场馆圈列表
     */
    public void queryVenueDiary(int startIndex, int pageSize, int venueId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("startIndex", String.valueOf(startIndex));
        params.addBodyParameter("pageSize", String.valueOf(pageSize));
        params.addBodyParameter("venueId", String.valueOf(venueId));
        postRequest(HttpConnectUtils.api_queryVenueDiary, params, 80);
    }

    /**
     * 获取用户场馆列表
     */
    public void GetVenueList() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getVenueList, params, 81);
    }

    /**
     * @param date       是 日记日期(YYYY-MM-DD)
     * @param permission 是 日记权限(1:公开;2:私密;3:朋友)
     * @param idea       否 日记想法
     * @param photo      否 日记照片
     */
    public void saveDiary(int venueId, String date, int permission, String idea,
                          String photo) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("venueId", String.valueOf(venueId));
        params.addBodyParameter("date", String.valueOf(date));
        params.addBodyParameter("permission", String.valueOf(permission));
        params.addBodyParameter("idea", idea);
        params.addBodyParameter("photo", photo);
        postRequest(HttpConnectUtils.api_saveDiary, params, 82);
    }

    /**
     * @param type    Short 是 文件类型(0:其他文件;1:用户头像;2:日记照片)
     * @param imgpath
     */
    public void uploadFile(int type, String imgpath) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("type", String.valueOf(type));
        params.addBodyParameter("file", new File(imgpath), "image/jpeg");
        postRequest(HttpConnectUtils.api_uploadFile, params, 83);
    }

    public void uploadFile(int type, File file) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("type", String.valueOf(type));
        params.addBodyParameter("file", file, "image/jpeg");
        postRequest(HttpConnectUtils.api_uploadFile, params, 83);
    }

    /**
     * @param actionTag
     * @param diaryId
     * @category 获取日记详情
     */
    public void getDiaryDetail(String actionTag, long diaryId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("diaryId", String.valueOf(diaryId));
        postRequest(HttpConnectUtils.api_getDiaryDetail, params, 84);
    }

    /**
     * @param actionTag
     * @param diaryId
     * @category 点赞
     */
    public void praiseDiary(String actionTag, long diaryId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("diaryId", String.valueOf(diaryId));
        postRequest(HttpConnectUtils.api_praiseDiary, params, 85);
    }

    /**
     * @param actionTag
     * @param diaryId
     * @category 取消点赞
     */
    public void unpraiseDiary(String actionTag, long diaryId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("diaryId", String.valueOf(diaryId));
        postRequest(HttpConnectUtils.api_unpraiseDiary, params, 86);
    }

    /**
     * @param actionTag
     * @param diaryId   日记标识
     * @param replyId   回复标识
     * @param content   评论内容
     * @category 评论日记
     */
    public void commentDiary(String actionTag, long diaryId, Long replyId,
                             String content) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("diaryId", String.valueOf(diaryId));
        if (replyId != null)
            params.addBodyParameter("replyId", String.valueOf(replyId));
        params.addBodyParameter("content", content);
        postRequest(HttpConnectUtils.api_commentDiary, params, 87);
    }

    /**
     * @param actionTag
     * @param commentId
     * @category 取消评论日记
     */
    public void uncommentDiary(String actionTag, long commentId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("commentId", String.valueOf(commentId));
        postRequest(HttpConnectUtils.api_uncommentDiary, params, 88);
    }

    /**
     * @param actionTag
     * @param diaryId
     * @category 删除评论
     */

    public void deleteDiary(String actionTag, long diaryId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("diaryId", String.valueOf(diaryId));
        postRequest(HttpConnectUtils.api_deleteDiary, params, 89);
    }

    /**
     * c查询个人主页
     *
     * @param venueId
     * @param otherCustomerId
     * @param startIndex
     * @param pageSize
     */
    public void queryDiaryHome(int venueId, long otherCustomerId, int startIndex, int pageSize) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("venueId", String.valueOf(venueId));
        params.addBodyParameter("otherCustomerId", String.valueOf(otherCustomerId));
        params.addBodyParameter("startIndex", String.valueOf(startIndex));
        params.addBodyParameter("pageSize", String.valueOf(pageSize));
        postRequest(HttpConnectUtils.api_queryDiaryHome, params, 90);
    }
//    gym/rank/getVenueAllRank：查询场馆排行榜

    /**
     * 查询场馆排行
     *
     * @param venueId
     */
    public void GetVenueAllRank(int venueId) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("venueId", String.valueOf(venueId));
        postRequest(HttpConnectUtils.api_getVenueAllRank, params, 91);
    }

    /**
     * 查询个人排行
     */
    public void GetAllRank() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getAllRank, params, 92);
    }

    /**
     * 查询成就
     */
    public void queryAchievement() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_queryAchievement, params, 93);
    }


    /**
     * 获取未读消息数量
     */
    public void GetUnreadMessageCount() {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        postRequest(HttpConnectUtils.api_getUnreadMessageCount, params,
                94);
    }


    /**
     * 查询消息列表
     */
    public void queryMessage(int startIndex, int pageSize) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("startIndex", String.valueOf(startIndex));
        params.addBodyParameter("pageSize", String.valueOf(pageSize));
        postRequest(HttpConnectUtils.api_queryMessage, params,
                95);
    }

    /**
     * 确认推送消息 messageId Long 是 消息标识
     */
    public void AckPushMessage(String id) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("messageId", id);
        postRequest(HttpConnectUtils.api_ackPushMessage, params,
                96);
    }

    /**
     * 阅读消息 messageId Long 否 消息标识
     */
    public void ReadMessage(String id) {
        RequestParams params = new RequestParams();
        Utiles.Add(context, params);
        params.addBodyParameter("messageId", id);
        postRequest(HttpConnectUtils.api_readMessage, params,
                97);
    }


}
