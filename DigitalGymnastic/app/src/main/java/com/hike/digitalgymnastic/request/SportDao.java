package com.hike.digitalgymnastic.request;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hike.digitalgymnastic.entitiy.BoJiCao;
import com.hike.digitalgymnastic.entitiy.BuXing;
import com.hike.digitalgymnastic.entitiy.DongGanDanChe;
import com.hike.digitalgymnastic.entitiy.ErTouJiShuangXiang;
import com.hike.digitalgymnastic.entitiy.GangLingCao;
import com.hike.digitalgymnastic.entitiy.GaoLaLiBeiJi;
import com.hike.digitalgymnastic.entitiy.HomeSportData;
import com.hike.digitalgymnastic.entitiy.HuDieShiKuoXiong;
import com.hike.digitalgymnastic.entitiy.JianBangHouZhan;
import com.hike.digitalgymnastic.entitiy.JianShenCaoOnceData;
import com.hike.digitalgymnastic.entitiy.LiShiDaTuiQuShen;
import com.hike.digitalgymnastic.entitiy.LiShiDaTuiShenZhan;
import com.hike.digitalgymnastic.entitiy.Paobu;
import com.hike.digitalgymnastic.entitiy.SanTouJiShuangXiang;
import com.hike.digitalgymnastic.entitiy.TiaoJieShiDengTui;
import com.hike.digitalgymnastic.entitiy.TuoYuanJi;
import com.hike.digitalgymnastic.entitiy.WoShiTuiQuZhan;
import com.hike.digitalgymnastic.entitiy.YouYangWuDao;
import com.hike.digitalgymnastic.entitiy.YouYong;
import com.hike.digitalgymnastic.entitiy.ZuoShiBeiJiShenZhan;
import com.hike.digitalgymnastic.entitiy.ZuoShiDaTuiNeiCeJi;
import com.hike.digitalgymnastic.entitiy.ZuoShiDaTuiShenZhan;
import com.hike.digitalgymnastic.entitiy.ZuoShiDaTuiWaiCeJi;
import com.hike.digitalgymnastic.entitiy.ZuoShiFeiNiao;
import com.hike.digitalgymnastic.entitiy.ZuoShiHouTuiQuShen;
import com.hike.digitalgymnastic.entitiy.ZuoShiHuaTingLali;
import com.hike.digitalgymnastic.entitiy.ZuoShiJianBangTuiJu;
import com.hike.digitalgymnastic.entitiy.ZuoShiShuangXiangTuiXiong;
import com.hike.digitalgymnastic.entitiy.ZuoShiTiXi;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.http.IBaseRequest;
import com.hike.digitalgymnastic.http.INetResult;
import com.hike.digitalgymnastic.utils.SportType;
import com.hike.digitalgymnastic.utils.Utiles;
import com.lidroid.xutils.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SportDao extends IBaseRequest {
	private Context context;
	private Gson gson;

	public SportDao(INetResult activity, Activity context) {
		super(activity, context);
		this.gson = new Gson();
		this.context = context;
	}

	private HomeSportData homesportdata;// 首页运动数据
	private Paobu paobu; // 获取跑步数据
	private BuXing buxing; // 获取步行数据
	private YouYangWuDao youxiangwudao;// 有氧舞蹈
	private DongGanDanChe donggandanche;// 动感单车
	private GangLingCao ganglingcao;// 杠铃操
	private BoJiCao bojicao;// 搏击操
	private TuoYuanJi tuoyuanji;// 椭圆肌
	private ZuoShiHuaTingLali zuoshihuatinglali;// 坐划艇拉力
	private JianBangHouZhan jianbanghouzhan;// 肩膀后展
	private YouYong youyong;// 游泳
	private GaoLaLiBeiJi gaolalibeiji;// 高拉力背脊
	private ZuoShiBeiJiShenZhan zuoshibeijishenzhan;// 坐式背脊伸展
	private ZuoShiFeiNiao zuoshifeiniao;// 坐式飞鸟
	private ZuoShiTiXi zuoshitixi;// 坐式提膝
	private ZuoShiDaTuiShenZhan zuoshidatuishenzhan;// 坐式大腿伸张
	private ZuoShiHouTuiQuShen zuoshihoutuiqushen;// 坐式后腿屈伸
	private ZuoShiDaTuiWaiCeJi zuoshidatuiwaiceji;// 坐式大腿外侧肌
	private ZuoShiDaTuiNeiCeJi zuoshidatuineiceji;// 坐式大腿内测肌
	private ZuoShiJianBangTuiJu zuoshijianbangtuiju;// 坐式肩膀推举
	private ZuoShiShuangXiangTuiXiong zuoshishuangxiangtuixiong;// 坐式双向推胸
	private HuDieShiKuoXiong hudieshikuoxiong;// 蝴蝶式扩胸
	private WoShiTuiQuZhan woshituiquzhan;// 卧式腿
	private ErTouJiShuangXiang ertoujishuangxiang;// 二头肌双向
	private SanTouJiShuangXiang santoujishuangxiang;// 三头肌双向
	private LiShiDaTuiShenZhan lishidatuishenzhan;// 立式大腿伸展
	private TiaoJieShiDengTui tiaojieshidengtui;// 调节式蹬腿
	private LiShiDaTuiQuShen lishidatuiqushen;// 立式大腿屈伸

	private JianShenCaoOnceData jianshencao;//健身操
	public JianShenCaoOnceData getJianshencao() {
		return jianshencao;
	}

	public LiShiDaTuiQuShen getLishidatuiqushen() {
		return lishidatuiqushen;
	}

	public TiaoJieShiDengTui getTiaojieshidengtui() {
		return tiaojieshidengtui;
	}

	public LiShiDaTuiShenZhan getLishidatuishenzhan() {
		return lishidatuishenzhan;
	}

	public SanTouJiShuangXiang getSantoujishuangxiang() {
		return santoujishuangxiang;
	}

	public ErTouJiShuangXiang getErtoujishuangxiang() {
		return ertoujishuangxiang;
	}

	public HuDieShiKuoXiong getHudieshikuoxiong() {
		return hudieshikuoxiong;
	}

	public ZuoShiShuangXiangTuiXiong getZuoshishuangxiangtuixiong() {
		return zuoshishuangxiangtuixiong;
	}

	public ZuoShiJianBangTuiJu getZuoshijianbangtuiju() {
		return zuoshijianbangtuiju;
	}

	public ZuoShiDaTuiNeiCeJi getZuoshidatuineiceji() {
		return zuoshidatuineiceji;
	}

	public ZuoShiDaTuiWaiCeJi getZuoshidatuiwaiceji() {
		return zuoshidatuiwaiceji;
	}

	public ZuoShiHouTuiQuShen getZuoshihoutuiqushen() {
		return zuoshihoutuiqushen;
	}

	public ZuoShiDaTuiShenZhan getZuoshidatuishenzhan() {
		return zuoshidatuishenzhan;
	}

	public ZuoShiTiXi getZuoshitixi() {
		return zuoshitixi;
	}

	public ZuoShiFeiNiao getZuoshifeiniao() {
		return zuoshifeiniao;
	}

	public ZuoShiBeiJiShenZhan getZuoshibeijishenzhan() {
		return zuoshibeijishenzhan;
	}

	public GaoLaLiBeiJi getGaolalibeiji() {
		return gaolalibeiji;
	}

	public YouYong getYouyong() {
		return youyong;
	}

	public JianBangHouZhan getJianbanghouzhan() {
		return jianbanghouzhan;
	}

	public TuoYuanJi getTuoyuanji() {
		return tuoyuanji;
	}

	public BoJiCao getBojiCao() {
		return bojicao;
	}

	public GangLingCao getGanglingcao() {
		return ganglingcao;
	}

	public DongGanDanChe getDonggandanche() {
		return donggandanche;
	}

	public YouYangWuDao getYouxiangwudao() {
		return youxiangwudao;
	}

	public HomeSportData getHomesportdata() {
		return homesportdata;
	}

	public BuXing getBuxing() {
		return buxing;
	}

	public Paobu getPaobu() {
		return paobu;
	}
	String pattern = "yyyy-MM-dd";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	@Override
	public void onRequestSuccess(JSONObject result, int requestCode)
			throws IOException, JsonSyntaxException, JSONException {
		Log.v("MyLog", result.toString());
		switch (requestCode) {
		case SportType.HomeSport: // !<首页
			
			SharedPreferences shp = context.getSharedPreferences("home",
					Context.MODE_WORLD_WRITEABLE);
			Editor editor = shp.edit();
			editor.clear();
			editor.putString(format.format(new Date()), result.getString("data"));
			editor.commit();
			homesportdata = gson.fromJson(result.getString("data"),
					HomeSportData.class);
			// 获取首页运动数据
			break;
		case SportType.HKSportTypePaoBuJi: // !<跑步机
			// 获取最新跑步机数据
			paobu = gson.fromJson(result.getString("data"), Paobu.class);
			break;
		case SportType.HKSportTypeBuXing:// !<步行
			buxing = gson.fromJson(result.getString("data"), BuXing.class);
			break;
		case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
			youxiangwudao = gson.fromJson(result.getString("data"),
					YouYangWuDao.class);
			break;
		case SportType.HKSportTypeDongGanDanChe:// !<动感单车
			donggandanche = gson.fromJson(result.getString("data"),
					DongGanDanChe.class);
			break;
		case SportType.HKSportTypeGangLingCao:// !<杠铃操
			ganglingcao = gson.fromJson(result.getString("data"),
					GangLingCao.class);
			break;
		case SportType.HKSportTypeBoJiCao:// !<搏击操
			bojicao = gson.fromJson(result.getString("data"), BoJiCao.class);
			break;
		case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
			tuoyuanji = gson
					.fromJson(result.getString("data"), TuoYuanJi.class);
			break;
		case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
			zuoshihuatinglali = gson.fromJson(result.getString("data"),
					ZuoShiHuaTingLali.class);
			break;
		case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
			jianbanghouzhan = gson.fromJson(result.getString("data"),
					JianBangHouZhan.class);
			break;
		case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
			gaolalibeiji = gson.fromJson(result.getString("data"),
					GaoLaLiBeiJi.class);
			break;
		case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
			zuoshibeijishenzhan = gson.fromJson(result.getString("data"),
					ZuoShiBeiJiShenZhan.class);
			break;
		case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
			zuoshifeiniao = gson.fromJson(result.getString("data"),
					ZuoShiFeiNiao.class);
			break;
		case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
			zuoshitixi = gson.fromJson(result.getString("data"),
					ZuoShiTiXi.class);
			break;
		case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
			zuoshidatuishenzhan = gson.fromJson(result.getString("data"),
					ZuoShiDaTuiShenZhan.class);
			break;
		case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
			zuoshihoutuiqushen = gson.fromJson(result.getString("data"),
					ZuoShiHouTuiQuShen.class);
			break;
		case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
			zuoshidatuiwaiceji = gson.fromJson(result.getString("data"),
					ZuoShiDaTuiWaiCeJi.class);
			break;
		case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器
			zuoshidatuineiceji = gson.fromJson(result.getString("data"),
					ZuoShiDaTuiNeiCeJi.class);
			break;
		case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
			zuoshijianbangtuiju = gson.fromJson(result.getString("data"),
					ZuoShiJianBangTuiJu.class);
			break;
		case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器
			zuoshishuangxiangtuixiong = gson.fromJson(result.getString("data"),
					ZuoShiShuangXiangTuiXiong.class);
			break;
		case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器
			hudieshikuoxiong = gson.fromJson(result.getString("data"),
					HuDieShiKuoXiong.class);
			break;
		case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
			woshituiquzhan = gson.fromJson(result.getString("data"),
					WoShiTuiQuZhan.class);
			break;
		case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器
			ertoujishuangxiang = gson.fromJson(result.getString("data"),
					ErTouJiShuangXiang.class);
			break;
		case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
			santoujishuangxiang = gson.fromJson(result.getString("data"),
					SanTouJiShuangXiang.class);
			break;
		case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
			lishidatuishenzhan = gson.fromJson(result.getString("data"),
					LiShiDaTuiShenZhan.class);
			break;
		case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
			tiaojieshidengtui = gson.fromJson(result.getString("data"),
					TiaoJieShiDengTui.class);
			break;
		case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
			lishidatuiqushen = gson.fromJson(result.getString("data"),
					LiShiDaTuiQuShen.class);
			break;
		case SportType.HKSportTypeYouYong: // !<游泳
			youyong = gson.fromJson(result.getString("data"), YouYong.class);
			break;
		case SportType.HKSportTypeJianshencao:
			 jianshencao = gson.fromJson(result.getString("data"), JianShenCaoOnceData.class);
			break;
		default:
			break;
		}
	}

	public ZuoShiHuaTingLali getZuoshihuatinglali() {
		return zuoshihuatinglali;
	}

	public WoShiTuiQuZhan getWoshituiquzhan() {
		return woshituiquzhan;
	}

	/**
	 * 获取首页运动数据
	 */
	public void GetHomeSportData() {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		postRequest(HttpConnectUtils.api_getHomeSportData, params,
				SportType.HomeSport);
	}

	/**
	 * 获取最新跑步机数据
	 */
	public void GetLatestPaoBuJiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestPaoBuJiData, params,
				SportType.HKSportTypePaoBuJi);
	}

	/**
	 * 获取最新步行数据
	 */
	public void GetLatestBuXingData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestBuXingData, params,
				SportType.HKSportTypeBuXing);
	}

	/**
	 * 获取最新有氧舞蹈数据
	 */
	public void GetLatestYouYangWuDaoData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestYouYangWuDaoData, params,
				SportType.HKSportTypeYouYangWuDao);
	}

	/**
	 * 获取最新动感单车数据
	 */
	public void GetLatestDongGanDanCheData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestDongGanDanCheData, params,
				SportType.HKSportTypeDongGanDanChe);
	}

	/**
	 * 获取最新杠铃操数据
	 */
	public void GetLatestGangLingCaoData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestGangLingCaoData, params,
				SportType.HKSportTypeGangLingCao);
	}

	/**
	 * 获取最新搏击操数据
	 */
	public void GetLatestBoJiCaoData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestBoJiCaoData, params,
				SportType.HKSportTypeBoJiCao);
	}

	/**
	 * 获取最新椭圆机数据
	 */
	public void GetLatestTuoYuanJiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestTuoYuanJiData, params,
				SportType.HKSportTypeTuoYuanJi);
	}

	/**
	 * 获取最新划艇拉力数据
	 */
	public void GetLatestHuaTingLaLiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestHuaTingLaLiData, params,
				SportType.HKSportTypeHuaTingLaLi);
	}

	/**
	 * 获取最新肩膀后展数据
	 */
	public void GetLatestJianBangHouZhanData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestJianBangHouZhanData, params,
				SportType.HKSportTypeJianBangHouZhan);
	}

	/**
	 * 获取最新拉力背肌数据
	 */
	public void GetLatestLaLiBeiJiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestLaLiBeiJiData, params,
				SportType.HKSportTypeLaLiBeiJi);
	}

	/**
	 * 获取最新背脊伸展数据
	 */
	public void GetLatestBeiJiShenZhanData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestBeiJiShenZhanData, params,
				SportType.HKSportTypeBeiJiShenZhan);
	}

	/**
	 * 获取最新坐式飞鸟数据
	 */
	public void GetLatestZuoShiFeiNiaoData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestZuoShiFeiNiaoData, params,
				SportType.HKSportTypeZuoShiFeiNiao);
	}

	/**
	 * 获取最新坐式提膝数据
	 */
	public void GetLatestZuoShiTiXiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestZuoShiTiXiData, params,
				SportType.HKSportTypeZuoShiTiXi);
	}

	/**
	 * 获取最新坐式大腿伸展数据
	 */
	public void GetLatestZuoShiDaTuiShenZhanData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestZuoShiDaTuiShenZhanData,
				params, SportType.HKSportTypeZuoShiDaTuiShenZhan);
	}

	/**
	 * 获取最新坐式后腿屈伸数据
	 */
	public void GetLatestZuoShiHouTuiQuShenData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestZuoShiHouTuiQuShenData,
				params, SportType.HKSportTypeZuoShiHouTuiQuShen);
	}

	/**
	 * 获取最新大腿外侧肌数据
	 */
	public void GetLatestDaTuiWaiCeJiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestDaTuiWaiCeJiData, params,
				SportType.HKSportTypeDaTuiWaiCeJi);
	}

	/**
	 * 获取最新大腿内侧肌数据
	 */
	public void GetLatestDaTuiNeiCeJiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestDaTuiNeiCeJiData, params,
				SportType.HKSportTypeDaTuiNeiCeJi);
	}

	/**
	 * 获取最新肩膀推举数据
	 */
	public void GetLatestJianBangTuiJuData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestJianBangTuiJuData, params,
				SportType.HKSportTypeJianBangTuiJu);
	}

	/**
	 * 获取最新双向推胸数据
	 */
	public void GetLatestShuangXiangTuiXiongData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestShuangXiangTuiXiongData,
				params, SportType.HKSportTypeShuangXiangTuiXiong);
	}

	/**
	 * 获取最新蝴蝶式扩胸数据
	 */
	public void GetLatestHuDieShiKuoXiongData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestHuDieShiKuoXiongData, params,
				SportType.HKSportTypeHuDieShiKuoXiong);
	}

	/**
	 * 获取最新卧式腿屈展数据
	 */
	public void GetLatestWoShiTuiQuZhanData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestWoShiTuiQuZhanData, params,
				SportType.HKSportTypeWoShiTuiQuZhan);
	}

	/**
	 * 获取最新二头肌双向数据
	 */
	public void GetLatestErTouJiShuangXiangData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestErTouJiShuangXiangData,
				params, SportType.HKSportTypeErTouJiShuangXiang);
	}

	/**
	 * 获取最新三头肌双向数据
	 */
	public void GetLatestSanTouJiShuangXiangData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestSanTouJiShuangXiangData,
				params, SportType.HKSportTypeSanTouJiShuangXiang);
	}

	/**
	 * 获取最新立式大腿伸展数据
	 */
	public void GetLatestLiShiDaTuiShenZhanData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestLiShiDaTuiShenZhanData,
				params, SportType.HKSportTypeLiShiDaTuiShenZhan);
	}

	/**
	 * 获取最新调节式蹬腿数据
	 */
	public void GetLatestTiaoJieDengTuiData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestTiaoJieDengTuiData, params,
				SportType.HKSportTypeTiaoJieDengTui);
	}

	/**
	 * 获取最新立式大腿屈伸数据
	 */
	public void GetLatestLiShiDaTuiQuShenData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestLiShiDaTuiQuShenData, params,
				SportType.HKSportTypeLiShiDaTuiQuShen);
	}

	/**
	 * 获取最新游泳数据
	 */
	public void GetLatestYouYongData(String dataId) {
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getLatestYouYongData, params,
				SportType.HKSportTypeYouYong);
	}
	
	/**
	 * 
	 */
	public void getJianShenCaoOnceData(String dataId){
		RequestParams params = new RequestParams();
		Utiles.Add(context, params);
		params.addBodyParameter("dataId", dataId);
		postRequest(HttpConnectUtils.api_getJianShenCaoOnceData, params,
				SportType.HKSportTypeJianshencao);
	}



	public void cancel(){
		if(httpHandler!=null&&!httpHandler.isCancelled()){
			httpHandler.cancel(true);
		}
	}
	
	public boolean isRunning(){
		if(httpHandler!=null)
			return httpHandler.isCancelled();
		return false;
		
	}
}
