package com.hike.digitalgymnastic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.BodyImage;
import com.hike.digitalgymnastic.entitiy.BuXing;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.DongGanDanChe;
import com.hike.digitalgymnastic.entitiy.ErTouJiShuangXiang;
import com.hike.digitalgymnastic.entitiy.GaoLaLiBeiJi;
import com.hike.digitalgymnastic.entitiy.HuDieShiKuoXiong;
import com.hike.digitalgymnastic.entitiy.JianBangHouZhan;
import com.hike.digitalgymnastic.entitiy.JianShenCaoOnceData;
import com.hike.digitalgymnastic.entitiy.LiShiDaTuiQuShen;
import com.hike.digitalgymnastic.entitiy.LiShiDaTuiShenZhan;
import com.hike.digitalgymnastic.entitiy.Paobu;
import com.hike.digitalgymnastic.entitiy.SanTouJiShuangXiang;
import com.hike.digitalgymnastic.entitiy.SportGifImage;
import com.hike.digitalgymnastic.entitiy.TiaoJieShiDengTui;
import com.hike.digitalgymnastic.entitiy.TuoYuanJi;
import com.hike.digitalgymnastic.entitiy.WoShiTuiQuZhan;
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
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.tools.FrameAnimUtil;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.HttpUtil;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.SportType;
import com.hike.digitalgymnastic.view.MyImageView;
import com.hike.digitalgymnastic.view.MyRadioButton;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


@ContentView(R.layout.activity_sportdetail)
public class SportSingleDetailActivity extends BaseActivity{
	@ViewInject(R.id.ll_btn_left)
	LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	LinearLayout ll_btn_right;
	@ViewInject(R.id.title)
	TextView title;

	@ViewInject(R.id.tv_one)
	TextView tv_one;
	@ViewInject(R.id.tv_two)
	TextView tv_two;
	@ViewInject(R.id.tv_three)
	TextView tv_three;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.ll_example)
	LinearLayout ll_example;
	@ViewInject(R.id.ll_part)
	LinearLayout ll_part;
	@ViewInject(R.id.ll_desp)
	LinearLayout ll_desp;
	@ViewInject(R.id.iv_example)
	ImageView iv_example;
	@ViewInject(R.id.iv_part)
//	ImageView iv_part;
	MyImageView iv_part;
	@ViewInject(R.id.tv_desp_action)
	TextView tv_desp_action;
	@ViewInject(R.id.tv_desp_protect)
	TextView tv_desp_protect;

	@ViewInject(R.id.view_line)
	View view_line;

	@ViewInject(R.id.rb_example)
	MyRadioButton rb_example;
	@ViewInject(R.id.rb_part)
	MyRadioButton rb_part;
	@ViewInject(R.id.rb_desp)
	MyRadioButton rb_desp;
	private BaseDao dao;
	private ArrayList<Bitmap> list;
	private int sporttype;
	private ArrayList<SportGifImage> sportGifImagesList;
	private String typeDir;
	private File fileType;
	private String typepath;
	private File typeFile;
	private int[] sings;
	private String head = "gym-api/";
	private int imageNumber;
	private int courseTypeId=0;
	private BodyImage bodyImage;
	private String bodyfilepath;
	private int gender;

	@OnClick(value = { R.id.ll_btn_left, R.id.ll_btn_right })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ll_btn_left:
			finish();
			break;
		default:
			break;
		}
	}

	@OnCompoundButtonCheckedChange(value = { R.id.rb_example, R.id.rb_part,
			R.id.rb_desp })
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			switch (buttonView.getId()) {
			case R.id.rb_example:
				ll_example.setVisibility(View.VISIBLE);
				ll_part.setVisibility(View.GONE);
				ll_desp.setVisibility(View.GONE);
				break;
			case R.id.rb_part:
				ll_example.setVisibility(View.GONE);
				ll_part.setVisibility(View.VISIBLE);
				ll_desp.setVisibility(View.GONE);
				break;
			case R.id.rb_desp:
				ll_example.setVisibility(View.GONE);
				ll_part.setVisibility(View.GONE);
				ll_desp.setVisibility(View.VISIBLE);
				break;
			default:
				break;
			}
		}
	}
	Customer customer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		dao = new BaseDao(this,this);
		initData();
		// tv_one.setText("最专业的安卓开发者社区：njuapk.com");
	}

	public SpannableString changeText(int i, String str, int j) {

		int size = (int) getResources().getDimensionPixelSize(R.dimen.x16);
		String[] strs = str.split("\n");
		str = strs[1]+"\n"+strs[0];
		SpannableString spanString = new SpannableString(str);
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);

		spanString.setSpan(span, 0, str.length() - j - i - 1,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return spanString;

	}

	AnimationDrawable animDrawable;

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		if (iv_example.isShown()) {
			animDrawable = (AnimationDrawable) iv_example.getBackground();
			if(animDrawable!=null)
				animDrawable.start();
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		if(animDrawable!=null)
			animDrawable.stop();
		System.gc();
	}
	private void setText(TextView container_time,TextView container_kcal,int shijian,double kaluli){
		
		String value="时间\n"+shijian/60+"min";
		SpannableString spanString = new SpannableString(value);
		int size = (int) getResources().getDimensionPixelSize(R.dimen.x16);
		AbsoluteSizeSpan span = new AbsoluteSizeSpan(size);
		spanString.setSpan(span, 2, value.length() - 3,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		container_time.setText(spanString);
		
		value="消耗\n"+kaluli+"kcal";
		spanString = new SpannableString(value);
		span = new AbsoluteSizeSpan(size);
		spanString.setSpan(span, 2, value.length() - 4,
				Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		container_kcal.setText(spanString);
	}
	private void initData() {
		title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
		btn_left.setImageResource(R.mipmap.back_login_3x);
		btn_left.setVisibility(View.VISIBLE);
		list = new ArrayList<Bitmap>();
		System.out.print("initData==29==");
		customer=LocalDataUtils.readCustomer(this);
		gender=Integer.parseInt(customer.getGender());
		Intent intent = getIntent();
		title.setText(intent.getStringExtra(Constants.oncesportname));
		sporttype = intent.getIntExtra(Constants.oncesporttype, 0);
		String sdpath = FileUtil.getStoragePathIfMounted(this);
		if(TextUtils.isEmpty(sdpath)){
			
		}else{
			typepath = sdpath + "/sporttype/" + sporttype;
			typeFile = new File(typepath);
		}
		rb_example.setChecked(true);

		DisplayMetrics dm=getResources().getDisplayMetrics();
		if(dm.densityDpi>240)
		 dao.getSportGifImageData(sporttype, 3);
		else
			dao.getSportGifImageData(sporttype, 2);

		switch(sporttype){

		case SportType.HKSportTypeJianshencao:// 健身操
		{
			tv_three.setVisibility(View.GONE);
			rb_example.setVisibility(View.GONE);
			rb_part.setChecked(true);
			JianShenCaoOnceData jianShencao = (JianShenCaoOnceData) intent
					.getSerializableExtra(Constants.oncesportdata);
			title.setText(jianShencao.getCourseName());
			courseTypeId = jianShencao.getCourseType();
			switch (jianShencao.getCourseType()) {
			case 1:// ZUMBA
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body29_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body29_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.zumba));
				tv_desp_protect.setText(getString(R.string.zumba_fh));
				break;
			case 2:// 健身瑜伽
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body30_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body30_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.yujia));
				tv_desp_protect.setText(getString(R.string.yujia_fh));
				break;
			case 3:// 有氧舞蹈
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body31_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body31_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.youyangwudao));
				tv_desp_protect.setText(getString(R.string.youyangwudao_fh));
				
				break;
			case 4:// 拉丁舞
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body32_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body32_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.ladingwu));
				tv_desp_protect.setText(getString(R.string.ladingwu_fh));
				break;
			case 5:// 现代舞
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body33_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body33_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.xiandaiwu));
				tv_desp_protect.setText(getString(R.string.xiandaiwu_fh));
				break;
			case 6:// 古典舞
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body34_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body34_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.gudianwu));
				tv_desp_protect.setText(getString(R.string.gudianwu_fh));
				break;
			case 7:// 爵士舞
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body35_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body35_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.jueshiwu));
				tv_desp_protect.setText(getString(R.string.jueshiwu_fh));
				break;
			case 8:// 肚皮舞
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body36_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body36_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.dupiwu));
				tv_desp_protect.setText(getString(R.string.dupiwu_fh));
				break;
			case 9:// BODY JAM
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body37_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body37_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.bodyjam));
				tv_desp_protect.setText(getString(R.string.bodyjam_fh));
				break;

			case 10:// BODY PUMP
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body38_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body38_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.bodypump));
				tv_desp_protect.setText(getString(R.string.bodypump_fh));
				
				break;
			case 11:// 动感单车
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body39_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body39_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.donggandanche));
				tv_desp_protect.setText(getString(R.string.donggandanche_fh));

				break;

			case 12:// BODY BLANCE
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body40_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body40_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.bodyblance));
				tv_desp_protect.setText(getString(R.string.bodyblance_fh));
				break;
			case 13:// BODY COMBAT搏击操
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body41_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body41_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.bodybcombat));
				tv_desp_protect.setText(getString(R.string.bodybcombat_fh));
				break;
			case 14:// BODY ATTACK
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body42_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body42_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.bodyattack));
				tv_desp_protect.setText(getString(R.string.bodyattack_fh));
				break;
			case 15:// 民族舞
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body43_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body43_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.minzuwu));
				tv_desp_protect.setText(getString(R.string.minzuwu_fh));
				break;
			case 16:// POP DANCE
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body44_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body44_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.popdance));
				tv_desp_protect.setText(getString(R.string.popdance_fh));
				break;

			case 17:// FOC
				
//				if(gender==1){//男性
//					iv_part.setImageResource(R.mipmap.image_body45_male);
//				}else{//女性
//					iv_part.setImageResource(R.mipmap.image_body45_sex);
//				}
				setText(tv_one,tv_two,jianShencao.getShijian(),jianShencao.getKaluli());
				tv_desp_action.setText(getString(R.string.foc));
				tv_desp_protect.setText(getString(R.string.foc_fh));
				break;

			default:
				break;
			}

		}

			break;
		case SportType.HKSportTypePaoBuJi: // !<跑步   -over

//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body03_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body03_sex);
//			}
			tv_desp_action.setText(getString(R.string.paobu));
			tv_desp_protect.setText(getString(R.string.paobu_fh));
			Paobu paobu = (Paobu) intent
					.getSerializableExtra(Constants.oncesportdata);
			title.setText(intent.getStringExtra(Constants.oncesportname));
			tv_one.setText(changeText(2,"距离\n"+ paobu.getJuli() + "米", 1));
			tv_two.setText(changeText(2,  "速度\n"+ paobu.getSudu() + "公里/小时", 5));
//			iv_example.setBackgroundResource(R.drawable.anim_example_paobu);
//			if(sportGifImagesList!=null && sportGifImagesList.size()!=0){
//				AnimationDrawable anim = FrameAnimUtil.GetFrameAnim(this, list, Bitmap.class, false, 300);
//				if(anim!=null){
//					iv_example.setBackgroundDrawable(anim);
//					anim.start();
//				}
//
//			}else{
//				File[] files = typeFile.listFiles();
//				if(files!=null && files.length!=0){
//					ArrayList<String> strlist = new ArrayList<String>();
//					for(int i=0;i<files.length;i++){
//						strlist.add(files[i].getName());
//					}
//					AnimationDrawable anim = FrameAnimUtil.GetFrameAnim(this, strlist, String.class, false, 300);
//					if(anim!=null){
//						iv_example.setBackgroundDrawable(anim);
//						anim.start();
//					}
//				}
//			}
			break;
		case SportType.HKSportTypeBuXing:// !<步行   没有示例图，没有防伤害提示-over
			rb_part.setChecked(true);
			ll_example.setVisibility(View.GONE);
			rb_example.setVisibility(View.GONE);
			tv_three.setVisibility(View.VISIBLE);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body01_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body01_sex);
//			}
			tv_desp_action.setText(getString(R.string.buxing));
			tv_desp_protect.setVisibility(View.GONE);
			BuXing buxing = (BuXing) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,  "距离\n"+buxing.getJuli() + "米", 1));
			tv_two.setText(changeText(2, "步数\n"+buxing.getBushu() + "步\n", 1));
			tv_three.setText(changeText(2, "速度\n"+buxing.getSudu() + "公里/小时\n",
					5));
			break;
	
		case SportType.HKSportTypeDongGanDanChe:// !<动感单车-器械
			ll_example.setVisibility(View.GONE);
			rb_example.setVisibility(View.GONE);
			tv_three.setVisibility(View.GONE);
			rb_part.setChecked(true);
			
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body39_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body39_sex);
//			}
			tv_desp_action.setText(getString(R.string.donggandanche));
			tv_desp_protect.setText(getString(R.string.donggandanche_fh));
			DongGanDanChe youyang = (DongGanDanChe) intent
					.getSerializableExtra(Constants.oncesportdata);

			tv_one.setText(changeText(2, "距离" + youyang.getJuli() + "米", 1));
			tv_two.setText(changeText(2, "速度\n" + youyang.getSudu() + "公里/小时",
					5));

			break;
		// case SportType.HKSportTypeGangLingCao:// !<杠铃操
		// iv_part.setImageResource(R.mipmap.pic_buxing_paobu_youyang_gangling_tuoyuanji_donggandanche_datuiwaiceji);
		// tv_desp_action.setText(getString(R.string.ganglingcao));
		// tv_desp_protect.setVisibility(View.GONE);
		//
		// GangLingCao ganglingcao = (GangLingCao) intent
		// .getSerializableExtra(Constants.oncesportdata);
		//
		// break;
		// case SportType.HKSportTypeBoJiCao:// !<搏击操
		// iv_part.setImageResource(R.mipmap.pic_boji);
		// tv_desp_action.setText(getString(R.string.bojicao));
		// tv_desp_protect.setVisibility(View.GONE);
		// BoJiCao bojicao = (BoJiCao) intent
		// .getSerializableExtra(Constants.oncesportdata);
		//
		// break;
		case SportType.HKSportTypeTuoYuanJi: // !<椭圆机   -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_tuoyuanji);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body04_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body04_sex);
//			}
			tv_desp_action.setText(getString(R.string.tuoyuanji));
			tv_desp_protect.setText(getString(R.string.tuoyuanji_fh));
			TuoYuanJi tuoyuanji = (TuoYuanJi) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "距离\n" + tuoyuanji.getJuli() + "米", 1));
			tv_two.setText(changeText(2,
					"速度\n" + tuoyuanji.getSudu() + "公里/小时", 5));

			break;
		case SportType.HKSportTypeHuaTingLaLi:// !<坐式划艇拉力练习器  -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshihuating);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body05_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body05_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshihuatinglali));
			tv_desp_protect.setText(getString(R.string.zuoshihuatinglali_fh));
			ZuoShiHuaTingLali zuoshihuatinglali = (ZuoShiHuaTingLali) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshihuatinglali.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshihuatinglali.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeJianBangHouZhan: // !<坐式肩膀后展练习器    -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshijianbanghouzhan);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body06_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body06_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshijianbanghouzhan));
			tv_desp_protect
					.setText(getString(R.string.zuoshijianbanghouzhan_fh));
			JianBangHouZhan jianbanghouzhan = (JianBangHouZhan) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + jianbanghouzhan.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + jianbanghouzhan.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeLaLiBeiJi:// !<高拉力背肌练习器   -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_gaolabeiji);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body07_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body07_sex);
//			}
			
			tv_desp_action.setText(getString(R.string.gaolalibeiji));
			tv_desp_protect.setText(getString(R.string.gaolalibeiji_fh));
			GaoLaLiBeiJi gaolalibeiji = (GaoLaLiBeiJi) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,
					"频次\n" + gaolalibeiji.getCishu() + "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + gaolalibeiji.getZhongliang() + "磅", 1));

			break;
		case SportType.HKSportTypeBeiJiShenZhan:// !<坐式背肌伸展练习器 -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshibeiji);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body08_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body08_sex);
//			}
			
			tv_desp_action.setText(getString(R.string.zuoshibeijishenzhan));
			tv_desp_protect.setText(getString(R.string.zuoshibeijishenzhan_fh));

			ZuoShiBeiJiShenZhan zuoshibeijishenzhan = (ZuoShiBeiJiShenZhan) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,
					"频次\n" + zuoshibeijishenzhan.getCishu() + "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshibeijishenzhan.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeZuoShiFeiNiao:// !<坐式飞鸟练习器 -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshifeiniao);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body09_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body09_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshifeiniao));
			tv_desp_protect.setText(getString(R.string.zuoshifeiniao_fh));

			ZuoShiFeiNiao zuoshifeiniao = (ZuoShiFeiNiao) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshifeiniao.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshifeiniao.getZhongliang() + "磅", 1));

//			animDrawable = (AnimationDrawable) iv_example.getBackground();
//			animDrawable.start();
			break;
		case SportType.HKSportTypeZuoShiTiXi:// !<坐式提膝练习器   -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshitixi);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body10_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body10_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshitixi));
			tv_desp_protect.setText(getString(R.string.zuoshitixi_fh));

			ZuoShiTiXi zuoshitixi = (ZuoShiTiXi) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshitixi.getCishu() + "次",
					1));
			tv_two.setText(changeText(4, "最大重量\n" + zuoshitixi.getZhongliang()
					+ "磅", 1));
			break;
		case SportType.HKSportTypeZuoShiDaTuiShenZhan:// !<坐式大腿伸展练习器 - over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshidatuishenzhan);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body11_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body11_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshidatuishenzhan));
			tv_desp_protect.setText(getString(R.string.zuoshidatuishenzhan_fh));
			ZuoShiDaTuiShenZhan zuoshidatuishenzhan = (ZuoShiDaTuiShenZhan) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,
					"频次\n" + zuoshidatuishenzhan.getCishu() + "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshidatuishenzhan.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeZuoShiHouTuiQuShen: // !<坐式后腿屈伸练习器  -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshihoutuiqushen);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body12_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body12_sex);
//			}
			
			tv_desp_action.setText(getString(R.string.zuoshihoutuiqushen));
			tv_desp_protect.setText(getString(R.string.zuoshihoutuiqushen_fh));
			ZuoShiHouTuiQuShen zuoshihoutuiqushen = (ZuoShiHouTuiQuShen) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshihoutuiqushen.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshihoutuiqushen.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeDaTuiWaiCeJi: // !<大腿外侧肌练习器     -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_datuiwaizhan);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body13_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body13_sex);
//			}
			tv_desp_action.setText(getString(R.string.datuiwaiceji));
			tv_desp_protect.setText(getString(R.string.datuiwaiceji_fh));
			ZuoShiDaTuiWaiCeJi zuoshidatuiwaiceji = (ZuoShiDaTuiWaiCeJi) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshidatuiwaiceji.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshidatuiwaiceji.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeDaTuiNeiCeJi: // !<大腿内侧肌练习器   -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_datuineishou);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body14_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body14_sex);
//			}
			tv_desp_action.setText(getString(R.string.datuineiceji));
			tv_desp_protect.setText(getString(R.string.datuineiceji_fh));
			ZuoShiDaTuiNeiCeJi zuoshidatuineiceji = (ZuoShiDaTuiNeiCeJi) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshidatuineiceji.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshidatuineiceji.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeJianBangTuiJu: // !<坐式肩膀推举练习器  -over
			
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_jianbangtuiju);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body15_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body15_sex);
//			}
			tv_desp_action.setText(getString(R.string.zuoshijianbangtuiju));
			tv_desp_protect.setText(getString(R.string.zuoshijianbangtuiju_fh));
			ZuoShiJianBangTuiJu zuoshijianbangtuiju = (ZuoShiJianBangTuiJu) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,
					"频次\n" + zuoshijianbangtuiju.getCishu() + "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshijianbangtuiju.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeShuangXiangTuiXiong: // !<坐式双向推胸练习器  -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_zuoshituixiong);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body16_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body16_sex);
//			}
			tv_desp_action
					.setText(getString(R.string.zuoshishuangxiangtuixong));
			tv_desp_protect
					.setText(getString(R.string.zuoshishuangxiangtuixong_fh));
			ZuoShiShuangXiangTuiXiong zuoshishuangxiang = (ZuoShiShuangXiangTuiXiong) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + zuoshishuangxiang.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + zuoshishuangxiang.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeHuDieShiKuoXiong: // !<蝴蝶式扩胸练习器   -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_hudiekuangxiong);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body17_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body17_sex);
//			}
			
			tv_desp_action.setText(getString(R.string.hudieshikuoxiong));
			tv_desp_protect.setText(getString(R.string.hudieshikuoxiong_fh));
			HuDieShiKuoXiong hudieshikuoxiong = (HuDieShiKuoXiong) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + hudieshikuoxiong.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + hudieshikuoxiong.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeWoShiTuiQuZhan: // !<卧式腿屈展器    -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_woshiqutui);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body18_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body18_sex);
//			}
			
			tv_desp_action.setText(getString(R.string.woshituiquzhan));
			tv_desp_protect.setText(getString(R.string.woshituiquzhan_fh));
			WoShiTuiQuZhan woshituiquzhan = (WoShiTuiQuZhan) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + woshituiquzhan.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + woshituiquzhan.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeErTouJiShuangXiang: // !<二头肌双向练习器  -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_ertoushuangxiang);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body19_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body19_sex);
//			}
			tv_desp_action.setText(getString(R.string.ertoujishuangxiang));
			tv_desp_protect.setText(getString(R.string.ertoujishuangxiang_fh));
			ErTouJiShuangXiang ertoujishaungxiang = (ErTouJiShuangXiang) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + ertoujishaungxiang.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + ertoujishaungxiang.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeSanTouJiShuangXiang: // !<三头肌双向练习器  -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_santoushuangxiang);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body21_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body21_sex);
//			}
			tv_desp_action.setText(getString(R.string.santoujishuangxiang));
			tv_desp_protect.setText(getString(R.string.santoujishuangxiang_fh));
			SanTouJiShuangXiang santoujishaungxiang = (SanTouJiShuangXiang) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2,
					"频次\n" + santoujishaungxiang.getCishu() + "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + santoujishaungxiang.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeLiShiDaTuiShenZhan: // !<立式大腿伸展练习器   -over
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_lishidatuishenzhan);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body23_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body23_sex);
//			}
			tv_desp_action.setText(getString(R.string.lishidatuiqushen));
			tv_desp_protect.setText(getString(R.string.lishidatuiqushen_fh));
			LiShiDaTuiShenZhan lishidatuishenzhan = (LiShiDaTuiShenZhan) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + lishidatuishenzhan.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + lishidatuishenzhan.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeTiaoJieDengTui: // !<调节式蹬腿练习器   -over
//			iv_example.setBackgroundResource(R.drawable.anim_example_tiaojieshidengtui);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body24_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body24_sex);
//			}
			tv_desp_action.setText(getString(R.string.tiaojieshidengtui));
			tv_desp_protect.setText(getString(R.string.tiaojieshidengtui_fh));
			TiaoJieShiDengTui tiaojieshidengtui = (TiaoJieShiDengTui) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + tiaojieshidengtui.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + tiaojieshidengtui.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeLiShiDaTuiQuShen: // !<立式大腿屈伸练习器  -over
			
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_lishidatuiqushen);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body25_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body25_sex);
//			}
			tv_desp_action.setText(getString(R.string.lishidatuiqushen));
			tv_desp_protect.setText(getString(R.string.lishidatuiqushen_fh));
			LiShiDaTuiQuShen lishidatuiqushen = (LiShiDaTuiQuShen) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setText(changeText(2, "频次\n" + lishidatuiqushen.getCishu()
					+ "次", 1));
			tv_two.setText(changeText(4,
					"最大重量\n" + lishidatuiqushen.getZhongliang() + "磅", 1));
			break;
		case SportType.HKSportTypeYouYong: // // !<游泳
			
//			iv_example.setBackgroundResource(R.drawable.anim_example_youyong);
//			if(gender==1){//男性
//				iv_part.setImageResource(R.mipmap.image_body02_male);
//			}else{//女性
//				iv_part.setImageResource(R.mipmap.image_body02_sex);
//			}
			tv_desp_action.setText(getString(R.string.youyong));
			tv_desp_protect.setVisibility(View.GONE);
			YouYong youyong = (YouYong) intent
					.getSerializableExtra(Constants.oncesportdata);
			tv_one.setVisibility(View.VISIBLE);
			tv_two.setVisibility(View.VISIBLE);
			tv_one.setText(changeText(2, "距离\n" + youyong.getJuli() + "米", 1));
			tv_two.setText(changeText(2, "速度\n" + youyong.getPingjunsudu()
					+ "公里/小时", 5));
			break;
		default:
			break;
		}

		if(dm.densityDpi>240)
			dao.getBodyImageData(sporttype, courseTypeId, gender,3);
		else
			dao.getBodyImageData(sporttype, courseTypeId, gender, 2);

	}


	@Override
	public void onRequestSuccess(int requestCode) {
		super.onRequestSuccess(requestCode);
		switch (requestCode){
			case 77:
				imageNumber = dao.getImageNumber();
				sportGifImagesList = dao.getSportGifImagesList();
				downLoadGifList(imageNumber, sporttype, sportGifImagesList);

				break;
			case 79:
				bodyImage = dao.getBodyImage();
				if(bodyImage!=null){
					downLoadBodyImage(bodyImage);
				}


				break;
		}
	}

	private void downLoadBodyImage(final BodyImage bodyImage) {

				String sdDir = FileUtil.getStoragePathIfMounted(this);
				if(TextUtils.isEmpty(sdDir)){

				}else{
					String appNameDir = sdDir + "/sportpart";
					File file = new File(appNameDir);
					if(file.exists()){

					}else{
						file.mkdirs();

					}
					typeDir = appNameDir+"/"+sporttype;
					fileType = new File(typeDir);
					if(fileType.exists()){

					}else{
						fileType.mkdirs();
					}

					if(sporttype==28){
						typeDir = typeDir+"/"+courseTypeId;
						fileType = new File(typeDir);
						if(fileType.exists()){

						}else{
							fileType.mkdirs();
						}
					}else{

					}
					typeDir = typeDir+"/"+gender;
					fileType = new File(typeDir);
					if(fileType.exists()){

					}else{
						fileType.mkdirs();
					}

					File[] listfils = fileType.listFiles();
					bodyfilepath = 	fileType.getAbsolutePath()+"/part.png";
					if(listfils!=null && fileType.listFiles().length!=0){
						//判断服务器文件是否被更新过
						long time = fileType.lastModified();
						Date date = new Date(time);

						String modifytime = bodyImage.getModifiedTime();
						SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

						Date date2 = null;
							try {
								date2 = sdf.parse(modifytime);
							} catch (ParseException e) {
								e.printStackTrace();
							}
//					Date date2=new Date(modifytime);
//					   bodyfilepath = 	fileType.getAbsolutePath()+"part.png";
							if(date2!=null){
								if(date2.before(date)){
									Drawable drawable = Drawable.createFromPath(bodyfilepath);
									iv_part.setBackground(drawable);
								}else{
									HttpUtil.initBitmapUtils(this,bodyfilepath);
									HttpUtil.displayIcon(iv_part,bodyImage.getImageUrl());
								}
							}
						}else{
						HttpUtil.initBitmapUtils(this,bodyfilepath);
						HttpUtil.displayIcon(iv_part,bodyImage.getImageUrl());
					}

		}
	}

	private void downLoadGifList(int imageNumber,int sprotType, ArrayList<SportGifImage> sportGifImagesList) {
		if(sportGifImagesList!=null && sportGifImagesList.size()!=0){

			imageNumber= sportGifImagesList.size();
		sings=new int[sportGifImagesList.size()];

		BitmapUtils bitmapUtils=new BitmapUtils(this);
		//加载网络图片
//		bitmapUtils.display(image01, "http://pic.youngt.com/static/team/2015/0204/14230151388959.jpg");
		String sdDir = FileUtil.getStoragePathIfMounted(this);
		if(TextUtils.isEmpty(sdDir)){

		}else{
			String appNameDir = sdDir + "/sporttype";
			File file = new File(appNameDir);
			if(file.exists()){

			}else{
				file.mkdirs();

			}
			typeDir = appNameDir+"/"+sprotType;
			fileType = new File(typeDir);
			if(fileType.exists()){

			}else{
				fileType.mkdirs();
			}
			File[] listfils = fileType.listFiles();
			if(listfils!=null && fileType.listFiles().length==imageNumber){
				//判断服务器文件是否被更新过
				long time = fileType.lastModified();
				Date date = new Date(time);

				boolean isReLoad = false;
				for (int i=0;i<imageNumber;i++){
					String modifytime = sportGifImagesList.get(i).getModifiedTime();
					SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

					Date date2 = null;
					try {
						date2 = sdf.parse(modifytime);
					} catch (ParseException e) {
						e.printStackTrace();
					}
//					Date date2=new Date(modifytime);
					if(date2!=null){
						if(date2.before(date)){
							sings[i]=1;
						}else{
							isReLoad =true;
							sings[i]=0;
						}
					}
				}
				if(isReLoad){
					loadAndSaveImage(imageNumber,sprotType,sportGifImagesList,typeDir);
				}else{

					File[] files = typeFile.listFiles();
					if(files!=null && files.length!=0){
					ArrayList<String> strlist = new ArrayList<String>();
					for(int i=0;i<files.length;i++){
						String path = typeDir+"/"+files[i].getName();
						strlist.add(path);
						System.out.println("path=="+path);
					}
					if(anim!=null){
						anim.stop();
						System.gc();
						anim=null;
					}
					System.out.println("strlist=="+strlist.size());
					anim = FrameAnimUtil.GetFrameAnim(this, strlist, String.class, false, 300);
					System.out.println("anim=="+anim);
					if(anim!=null){
						iv_example.setBackgroundDrawable(anim);
						anim.start();
					}
				}
				}
			}else{

				loadAndSaveImage(imageNumber,sprotType,sportGifImagesList,typeDir);
			}


		}



		}

	}
	public AnimationDrawable anim;
	Handler handler=new Handler(){


		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what){
				case 0:
					if(anim!=null){
						anim.stop();
						System.gc();
						anim=null;
					}

						anim = FrameAnimUtil.GetFrameAnim(SportSingleDetailActivity.this, list, Bitmap.class, false, 300);
						if(anim!=null){
							iv_example.setBackgroundDrawable(anim);
							anim.start();
						}


					break;
				default:
					break;
			}
		}
	};
	private Bitmap bitmap;
	private void loadAndSaveImage(final int imageNumber,int sprotType,final ArrayList<SportGifImage> sportGifImagesList,final String typeDir) {


		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i=0;i<imageNumber;i++){
					sings[i]=0;
				}

				for (int i=0;i==-1?false:i<imageNumber;i++){

					Bitmap bitmap = HttpUtil.getSportGifImage(SportSingleDetailActivity.this, HttpConnectUtils.image_ip + sportGifImagesList.get(i).getImageUrl(), i, typeDir +"/"+ i+".png", sings);
					if(bitmap==null){
						i=-1;
					}else{
						list.add(bitmap);
						handler.sendEmptyMessage(0);
					}


				}
			}
		}).start();

	}
}
