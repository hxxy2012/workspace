package com.hike.digitalgymnastic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.hike.digitalgymnastic.entitiy.BoJiCao;
import com.hike.digitalgymnastic.entitiy.BuXing;
import com.hike.digitalgymnastic.entitiy.DongGanDanChe;
import com.hike.digitalgymnastic.entitiy.ErTouJiShuangXiang;
import com.hike.digitalgymnastic.entitiy.GangLingCao;
import com.hike.digitalgymnastic.entitiy.GaoLaLiBeiJi;
import com.hike.digitalgymnastic.entitiy.HuDieShiKuoXiong;
import com.hike.digitalgymnastic.entitiy.JianBangHouZhan;
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
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.SportType;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.hiko.enterprisedigital.R;
/**
 * 
 * @author changqi
 * @category 该文件废弃
 *
 */
@ContentView(R.layout.activity_onesportshow)
public class SportMessageShowActivity extends Activity {
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.iv_sport)
	ImageView iv_sport;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.tv_sport)
	TextView tv_sport;
	@ViewInject(R.id.tv_one)
	TextView tv_one;
	@ViewInject(R.id.tv_two)
	TextView tv_two;
	@ViewInject(R.id.tv_three)
	TextView tv_three;
	@ViewInject(R.id.tv_jieshi)
	TextView tv_jieshi;
	@ViewInject(R.id.tv_fh)
	TextView tv_fh;
	
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	@OnClick(value = { R.id.btn_left,R.id.ll_btn_left})
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		initData();
//		tv_one.setText("最专业的安卓开发者社区：njuapk.com");
	}
	public SpannableString changeText(int i,String str,int j){
		SpannableString spanString = new SpannableString(str);    
		int size=(int) getResources().getDimensionPixelSize(R.dimen.x25);
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);    
		spanString.setSpan(span, i, str.length() - j, Spannable.SPAN_INCLUSIVE_INCLUSIVE);   
		return spanString;
	}

	private void initData() {
		Intent intent = getIntent();
		title.setText(intent.getStringExtra(Constants.oncesportname));
		switch (intent.getIntExtra(Constants.oncesporttype, 0)) {
		case SportType.HKSportTypePaoBuJi: // !<跑步

			
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.paobu));
			tv_fh.setText(getString(R.string.paobu_fh));
			Paobu paobu = (Paobu) intent.getSerializableExtra(Constants.oncesportdata);
			title.setText(intent.getStringExtra(Constants.oncesportname));
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"距离\n"+paobu.getJuli()+"米",1)); 
			tv_two.setText(changeText(2,"速度\n"+paobu.getSudu()+"公里/小时",5));
//			tv_sport.setText(
//					"坡度-->" +paobu.getPodu()+
//			        "距离-->"+paobu.getJuli()+
//					"卡里路-->"+paobu.getKaluli()+
//					"时间--->"+paobu.getShijian()+
//					"实时速度--->"+paobu.getShishisudu()+
//					"速度--->"+paobu.getSudu());
			break;
		case SportType.HKSportTypeBuXing:// !<步行
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.buxing));
			tv_fh.setVisibility(View.GONE);
			BuXing buxing = (BuXing) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_three.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"距离\n"+buxing.getJuli()+"米",1));
			tv_two.setText(changeText(2,"步数\n"+buxing.getBushu()+"步",1));
			tv_three.setText(changeText(2,"速度\n"+buxing.getSudu()+"公里/小时",5));
//			tv_sport.setText(
//					"步数-->" +buxing.getBushu()+
//			        "距离-->"+buxing.getJuli()+
//					"卡里路-->"+buxing.getKaluli()+
//					"速度-->"+buxing.getSudu());
			break;
		case SportType.HKSportTypeYouYangWuDao:// !<有氧舞蹈
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.youyangwudao));
			tv_fh.setVisibility(View.GONE);

			YouYangWuDao donggandanche = (YouYangWuDao) intent.getSerializableExtra(Constants.oncesportdata);
//			tv_sport.setText(
//					"次数-->" +donggandanche.getCishu()+
//			        "时间-->"+donggandanche.getShijian()+
//					"卡里路-->"+donggandanche.getKaluli());
			break;
		case SportType.HKSportTypeDongGanDanChe:// !<动感单车
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.donggandanche));
			tv_fh.setText(getString(R.string.donggandanche_fh));
			DongGanDanChe youyang = (DongGanDanChe) intent.getSerializableExtra(Constants.oncesportdata);
			
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"距离\n"+youyang.getJuli()+"米",1));
			tv_two.setText(changeText(2,"速度\n"+youyang.getSudu()+"公里/小时",5));
//			tv_sport.setText(
//					"距离-->" +youyang.getJuli()+
//					"速度-->" +youyang.getSudu()+
//					"阻力-->" +youyang.getZuli()+
//			        "时间-->"+youyang.getShijian()+
//					"卡里路-->"+youyang.getKaluli());
			break;
		case SportType.HKSportTypeGangLingCao:// !<杠铃操
//			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
//			tv_jieshi.setText(getString(R.string.ganglingcao));
//			tv_fh.setVisibility(View.GONE);
//
//			GangLingCao ganglingcao = (GangLingCao) intent.getSerializableExtra(Constants.oncesportdata);
//			tv_sport.setText(
//					"次数-->" +ganglingcao.getCishu()+
//			        "时间-->"+ganglingcao.getShijian()+
//					"卡里路-->"+ganglingcao.getKaluli());
			break;
		case SportType.HKSportTypeBoJiCao:// !<搏击操
//			iv_sport.setImageResource(R.mipmap.pic_boji);
//			tv_jieshi.setText(getString(R.string.bojicao));
//			tv_fh.setVisibility(View.GONE);
//			BoJiCao bojicao = (BoJiCao) intent.getSerializableExtra(Constants.oncesportdata);
//			tv_sport.setText(
//					"次数-->" +bojicao.getCishu()+
//			        "时间-->"+bojicao.getShijian()+
//					"卡里路-->"+bojicao.getKaluli());
			break;
		case SportType.HKSportTypeTuoYuanJi: // !<椭圆机
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.tuoyuanji));
			tv_fh.setText(getString(R.string.tuoyuanji_fh));
			TuoYuanJi tuoyuanji = (TuoYuanJi) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"距离\n"+tuoyuanji.getJuli()+"米",1));
			tv_two.setText(changeText(2,"速度\n"+tuoyuanji.getSudu()+"公里/小时",5));
//			tv_sport.setText(
//					"速度-->" +tuoyuanji.getSudu()+
//			        "时间-->"+tuoyuanji.getShijian()+
//			        "距离-->"+tuoyuanji.getJuli()+
//					"卡里路-->"+tuoyuanji.getKaluli());
			break;
		case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshihuatinglali);
			tv_jieshi.setText(getString(R.string.zuoshihuatinglali));
			tv_fh.setText(getString(R.string.zuoshihuatinglali_fh));
			ZuoShiHuaTingLali zuoshihuatinglali = (ZuoShiHuaTingLali) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshihuatinglali.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshihuatinglali.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshihuatinglali.getCishu()+
//			        "时间-->"+zuoshihuatinglali.getShijian()+
//			        "距离-->"+zuoshihuatinglali.getJuli()+
//			        "重量-->"+zuoshihuatinglali.getZhongliang()+
//					"卡里路-->"+zuoshihuatinglali.getKaluli());
			break;
		case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshijianbangtuiju_zuoshijianbanghouzhan_zuoshifeiniao);
			tv_jieshi.setText(getString(R.string.zuoshijianbanghouzhan));
			tv_fh.setText(getString(R.string.zuoshijianbanghouzhan_fh));
			JianBangHouZhan jianbanghouzhan = (JianBangHouZhan) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+jianbanghouzhan.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+jianbanghouzhan.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +jianbanghouzhan.getCishu()+
//			        "时间-->"+jianbanghouzhan.getShijian()+
//			        "距离-->"+jianbanghouzhan.getJuli()+
//			        "重量-->"+jianbanghouzhan.getZhongliang()+
//					"卡里路-->"+jianbanghouzhan.getKaluli());
			break;
		case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器
			iv_sport.setImageResource(R.mipmap.pic_hudieshikuoxiong_gaolalibeiji_zuoshibeijishenzhan);
			tv_jieshi.setText(getString(R.string.gaolalibeiji));
			tv_fh.setText(getString(R.string.gaolalibeiji_fh));
			GaoLaLiBeiJi gaolalibeiji = (GaoLaLiBeiJi) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+gaolalibeiji.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+gaolalibeiji.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +gaolalibeiji.getCishu()+
//			        "时间-->"+gaolalibeiji.getShijian()+
//			        "距离-->"+gaolalibeiji.getJuli()+
//			        "重量-->"+gaolalibeiji.getZhongliang()+
//					"卡里路-->"+gaolalibeiji.getKaluli());
			break;
		case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器
			iv_sport.setImageResource(R.mipmap.pic_hudieshikuoxiong_gaolalibeiji_zuoshibeijishenzhan);
			tv_jieshi.setText(getString(R.string.zuoshibeijishenzhan));
			tv_fh.setText(getString(R.string.zuoshibeijishenzhan_fh));

			ZuoShiBeiJiShenZhan zuoshibeijishenzhan = (ZuoShiBeiJiShenZhan) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshibeijishenzhan.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshibeijishenzhan.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshibeijishenzhan.getCishu()+
//			        "时间-->"+zuoshibeijishenzhan.getShijian()+
//			        "距离-->"+zuoshibeijishenzhan.getJuli()+
//			        "重量-->"+zuoshibeijishenzhan.getZhongliang()+
//					"卡里路-->"+zuoshibeijishenzhan.getKaluli());
			break;
		case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshijianbangtuiju_zuoshijianbanghouzhan_zuoshifeiniao);
			tv_jieshi.setText(getString(R.string.zuoshifeiniao));
			tv_fh.setText(getString(R.string.zuoshifeiniao_fh));

			ZuoShiFeiNiao zuoshifeiniao = (ZuoShiFeiNiao) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshifeiniao.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshifeiniao.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshifeiniao.getCishu()+
//			        "时间-->"+zuoshifeiniao.getShijian()+
//			        "距离-->"+zuoshifeiniao.getJuli()+
//			        "重量-->"+zuoshifeiniao.getZhongliang()+
//					"卡里路-->"+zuoshifeiniao.getKaluli());
			break;
		case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshitixi);
			tv_jieshi.setText(getString(R.string.zuoshitixi));
			tv_fh.setText(getString(R.string.zuoshitixi_fh));

			ZuoShiTiXi zuoshitixi = (ZuoShiTiXi) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshitixi.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshitixi.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshitixi.getCishu()+
//			        "时间-->"+zuoshitixi.getShijian()+
//			        "距离-->"+zuoshitixi.getJuli()+
//			        "重量-->"+zuoshitixi.getZhongliang()+
//					"卡里路-->"+zuoshitixi.getKaluli());
			break;
		case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器
			iv_sport.setImageResource(R.mipmap.pic_woshituiquzhanqi_zuoshidatuiqushen_zuoshidatuishenzhan);
			tv_jieshi.setText(getString(R.string.zuoshidatuishenzhan));
			tv_fh.setText(getString(R.string.zuoshidatuishenzhan_fh));
			ZuoShiDaTuiShenZhan zuoshidatuishenzhan = (ZuoShiDaTuiShenZhan) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshidatuishenzhan.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshidatuishenzhan.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshidatuishenzhan.getCishu()+
//			        "时间-->"+zuoshidatuishenzhan.getShijian()+
//			        "距离-->"+zuoshidatuishenzhan.getJuli()+
//			        "重量-->"+zuoshidatuishenzhan.getZhongliang()+
//					"卡里路-->"+zuoshidatuishenzhan.getKaluli());
			break;
		case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器
			iv_sport.setImageResource(R.mipmap.pic_woshituiquzhanqi_zuoshidatuiqushen_zuoshidatuishenzhan);
			tv_jieshi.setText(getString(R.string.zuoshihoutuiqushen));
			tv_fh.setText(getString(R.string.zuoshihoutuiqushen_fh));
			ZuoShiHouTuiQuShen zuoshihoutuiqushen = (ZuoShiHouTuiQuShen) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshihoutuiqushen.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshihoutuiqushen.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshihoutuiqushen.getCishu()+
//			        "时间-->"+zuoshihoutuiqushen.getShijian()+
//			        "距离-->"+zuoshihoutuiqushen.getJuli()+
//			        "重量-->"+zuoshihoutuiqushen.getZhongliang()+
//					"卡里路-->"+zuoshihoutuiqushen.getKaluli());
			break;
		case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.datuiwaiceji));
			tv_fh.setText(getString(R.string.datuiwaiceji_fh));
			ZuoShiDaTuiWaiCeJi zuoshidatuiwaiceji = (ZuoShiDaTuiWaiCeJi) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshidatuiwaiceji.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshidatuiwaiceji.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshidatuiwaiceji.getCishu()+
//			        "时间-->"+zuoshidatuiwaiceji.getShijian()+
//			        "距离-->"+zuoshidatuiwaiceji.getJuli()+
//			        "重量-->"+zuoshidatuiwaiceji.getZhongliang()+
//					"卡里路-->"+zuoshidatuiwaiceji.getKaluli());
			break;
		case SportType.HKSportTypeDaTuiNeiCeJi:  // !<大腿内侧肌练习器
			iv_sport.setImageResource(R.mipmap.pic_datuineiceji);
			tv_jieshi.setText(getString(R.string.datuineiceji));
			tv_fh.setText(getString(R.string.datuineiceji_fh));
			ZuoShiDaTuiNeiCeJi zuoshidatuineiceji = (ZuoShiDaTuiNeiCeJi) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshidatuineiceji.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshidatuineiceji.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshidatuineiceji.getCishu()+
//			        "时间-->"+zuoshidatuineiceji.getShijian()+
//			        "距离-->"+zuoshidatuineiceji.getJuli()+
//			        "重量-->"+zuoshidatuineiceji.getZhongliang()+
//					"卡里路-->"+zuoshidatuineiceji.getKaluli());
			break;
		case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshijianbangtuiju_zuoshijianbanghouzhan_zuoshifeiniao);
			tv_jieshi.setText(getString(R.string.zuoshijianbangtuiju));
			tv_fh.setText(getString(R.string.zuoshijianbangtuiju_fh));
			ZuoShiJianBangTuiJu zuoshijianbangtuiju = (ZuoShiJianBangTuiJu) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshijianbangtuiju.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshijianbangtuiju.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshijianbangtuiju.getCishu()+
//			        "时间-->"+zuoshijianbangtuiju.getShijian()+
//			        "距离-->"+zuoshijianbangtuiju.getJuli()+
//			        "重量-->"+zuoshijianbangtuiju.getZhongliang()+
//					"卡里路-->"+zuoshijianbangtuiju.getKaluli());
			break;
		case SportType.HKSportTypeShuangXiangTuiXiong:  // !<坐式双向推胸练习器
			iv_sport.setImageResource(R.mipmap.pic_zuoshishuangxiangtuixiong);
			tv_jieshi.setText(getString(R.string.zuoshishuangxiangtuixong));
			tv_fh.setText(getString(R.string.zuoshishuangxiangtuixong_fh));
			ZuoShiShuangXiangTuiXiong zuoshishuangxiang = (ZuoShiShuangXiangTuiXiong) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+zuoshishuangxiang.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+zuoshishuangxiang.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +zuoshishuangxiang.getCishu()+
//			        "时间-->"+zuoshishuangxiang.getShijian()+
//			        "距离-->"+zuoshishuangxiang.getJuli()+
//			        "重量-->"+zuoshishuangxiang.getZhongliang()+
//					"卡里路-->"+zuoshishuangxiang.getKaluli());
			break;
		case SportType.HKSportTypeHuDieShiKuoXiong:  // !<蝴蝶式扩胸练习器
//			iv_sport.setImageResource(R.mipmap.pic_hudieshikuoxiong_gaolalibeiji_zuoshibeijishenzhan);
//			tv_jieshi.setText(getString(R.string.dieshikuoxiong));
//			tv_fh.setText(getString(R.string.dieshikuoxiong_fh));
//			HuDieShiKuoXiong hudieshikuoxiong = (HuDieShiKuoXiong) intent.getSerializableExtra(Constants.oncesportdata);
//			tv_one.setVisibility(View.VISIBLE);
//			tv_two.setVisibility(View.VISIBLE);
//			tv_one.setText(changeText(2,"频次\n"+hudieshikuoxiong.getCishu()+"次",1));
//			tv_two.setText(changeText(4,"最大重量\n"+hudieshikuoxiong.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +hudieshikuoxiong.getCishu()+
//			        "时间-->"+hudieshikuoxiong.getShijian()+
//			        "距离-->"+hudieshikuoxiong.getJuli()+
//			        "重量-->"+hudieshikuoxiong.getZhongliang()+
//					"卡里路-->"+hudieshikuoxiong.getKaluli());
			break;
		case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器
			iv_sport.setImageResource(R.mipmap.pic_woshituiquzhanqi_zuoshidatuiqushen_zuoshidatuishenzhan);
			tv_jieshi.setText(getString(R.string.woshituiquzhan));
			tv_fh.setText(getString(R.string.woshituiquzhan_fh));
			WoShiTuiQuZhan woshituiquzhan = (WoShiTuiQuZhan) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+woshituiquzhan.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+woshituiquzhan.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +woshituiquzhan.getCishu()+
//			        "时间-->"+woshituiquzhan.getShijian()+
//			        "距离-->"+woshituiquzhan.getJuli()+
//			        "重量-->"+woshituiquzhan.getZhongliang()+
//					"卡里路-->"+woshituiquzhan.getKaluli());
			break;
		case SportType.HKSportTypeErTouJiShuangXiang:  // !<二头肌双向练习器
			iv_sport.setImageResource(R.mipmap.pic_ertoujishuangxiang);
			tv_jieshi.setText(getString(R.string.ertoujishuangxiang));
			tv_fh.setText(getString(R.string.ertoujishuangxiang_fh));
			ErTouJiShuangXiang ertoujishaungxiang = (ErTouJiShuangXiang) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+ertoujishaungxiang.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+ertoujishaungxiang.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +ertoujishaungxiang.getCishu()+
//			        "时间-->"+ertoujishaungxiang.getShijian()+
//			        "距离-->"+ertoujishaungxiang.getJuli()+
//			        "重量-->"+ertoujishaungxiang.getZhongliang()+
//					"卡里路-->"+ertoujishaungxiang.getKaluli());
			break;
		case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器
			iv_sport.setImageResource(R.mipmap.pic_ertoujishuangxiang);
			tv_jieshi.setText(getString(R.string.santoujishuangxiang));
			tv_fh.setText(getString(R.string.santoujishuangxiang_fh));
			SanTouJiShuangXiang santoujishaungxiang = (SanTouJiShuangXiang) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+santoujishaungxiang.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+santoujishaungxiang.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +santoujishaungxiang.getCishu()+
//			        "时间-->"+santoujishaungxiang.getShijian()+
//			        "距离-->"+santoujishaungxiang.getJuli()+
//			        "重量-->"+santoujishaungxiang.getZhongliang()+
//					"卡里路-->"+santoujishaungxiang.getKaluli());
			break;
		case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器
//			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
//			tv_jieshi.setText(getString(R.string.lishidatuishenzhan));
//			tv_fh.setText(getString(R.string.lishidatuishenzhan_fh));
//			LiShiDaTuiShenZhan lishidatuishenzhan = (LiShiDaTuiShenZhan) intent.getSerializableExtra(Constants.oncesportdata);
//			tv_one.setVisibility(View.VISIBLE);
//			tv_two.setVisibility(View.VISIBLE);
//			tv_one.setText(changeText(2,"频次\n"+lishidatuishenzhan.getCishu()+"次",1));
//			tv_two.setText(changeText(4,"最大重量\n"+lishidatuishenzhan.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +lishidatuishenzhan.getCishu()+
//			        "时间-->"+lishidatuishenzhan.getShijian()+
//			        "距离-->"+lishidatuishenzhan.getJuli()+
//			        "重量-->"+lishidatuishenzhan.getZhongliang()+
//					"卡里路-->"+lishidatuishenzhan.getKaluli());
			break;
		case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.tiaojieshidengtui));
			tv_fh.setText(getString(R.string.tiaojieshidengtui_fh));
			TiaoJieShiDengTui tiaojieshidengtui = (TiaoJieShiDengTui) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+tiaojieshidengtui.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+tiaojieshidengtui.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +tiaojieshidengtui.getCishu()+
//			        "时间-->"+tiaojieshidengtui.getShijian()+
//			        "距离-->"+tiaojieshidengtui.getJuli()+
//			        "重量-->"+tiaojieshidengtui.getZhongliang()+
//					"卡里路-->"+tiaojieshidengtui.getKaluli());
			break;
		case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器
			iv_sport.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
			tv_jieshi.setText(getString(R.string.lishidatuiqushen));
			tv_fh.setText(getString(R.string.lishidatuiqushen_fh));
			LiShiDaTuiQuShen lishidatuiqushen = (LiShiDaTuiQuShen) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"频次\n"+lishidatuiqushen.getCishu()+"次",1));
			tv_two.setText(changeText(4,"最大重量\n"+lishidatuiqushen.getZhongliang()+"磅",1));
//			tv_sport.setText(
//					"次数-->" +lishidatuiqushen.getCishu()+
//			        "时间-->"+lishidatuiqushen.getShijian()+
//			        "距离-->"+lishidatuiqushen.getJuli()+
//			        "重量-->"+lishidatuiqushen.getZhongliang()+
//					"卡里路-->"+lishidatuiqushen.getKaluli());
			break;
		case SportType.HKSportTypeYouYong: //  // !<游泳
			iv_sport.setImageResource(R.mipmap.pic_youyong);
			tv_jieshi.setText(getString(R.string.youyong));
			tv_fh.setVisibility(View.GONE);
			YouYong youyong = (YouYong) intent.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2,"距离\n"+youyong.getJuli()+"米",1));
			tv_two.setText(changeText(2,"速度\n"+youyong.getPingjunsudu()+"公里/小时",5));
//			tv_sport.setText(
//					"峰值速度-->" +youyong.getFengzhisudu()+
//					"距离-->" +youyong.getJuli()+
//					"时间-->" +youyong.getShijian()+
//			        "平均速度-->"+youyong.getPingjunsudu()+
//					"卡里路-->"+youyong.getKaluli());
			break;
		default:
			break;
		}
	}
}
