package com.hike.digitalgymnastic;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.BodeStateType;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
@ContentView(R.layout.activity_indicatorsalert)
public class IndicatorsAlert extends Activity {

	BodeStateType bodeStateType;
	@ViewInject(R.id.btn_left)
	ImageView btn_left;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.right)
	ImageView btn_right;
	@ViewInject(R.id.ll_btn_left)
	private LinearLayout ll_btn_left;
	@ViewInject(R.id.ll_btn_right)
	private LinearLayout ll_btn_right;
	
	
	@ViewInject(R.id.tv_content)
	TextView tv_content;
	
	@OnClick(value={R.id.btn_left,R.id.ll_btn_left})
	public void onClick(View view){
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ViewUtils.inject(this);
		init();
	}

	private void init() {
		btn_left.setImageResource(R.mipmap.back_login_3x);
		title.setTextColor(getResources().getColor(R.color.umeng_socialize_text_share_content));
		// TODO Auto-generated method stub
		if (getIntent() != null) {
			bodeStateType = (BodeStateType) getIntent().getSerializableExtra(
					"type");
		}
		if (bodeStateType == null)
			bodeStateType = BodeStateType.体质指数BMI;
		if (bodeStateType == BodeStateType.体质指数BMI) {
			title.setText("体质指数");
			tv_content.setText("体质指数，又称BMI指数，BMI=体重/(身高*身高）。体质指数是目前国际上最常用的衡量人体胖瘦程度以及是否健康的一个标准，因此我们的个人体质指数将会更直观的反映出我们的身体体质水平。");
		} else if (bodeStateType == BodeStateType.身体脂肪率) {
			title.setText("身体脂肪率");
			tv_content.setText("脂肪率是指我们的身体成分中，脂肪组织所占的比率，即脂肪率=脂肪重量/体重。脂肪率比单纯的只测量体重更能反映出我们身体的肥胖程度，因此维持正常的身体脂肪率对于我们保持健康的身体至关重要。");
		} else if (bodeStateType == BodeStateType.内脏脂肪水平) {
			title.setText("内脏脂肪水平");
			tv_content.setText("内脏脂肪围绕着我们的脏器，对我们的内脏起着支撑、稳定和保护的作用。如果我们体内内脏脂肪过少将无法对内脏起到保护和支撑，而内脏脂肪过多则容易引发癌症、呼吸急促、不孕不育等疾病，因此保持内脏脂肪水平的正常对我们的健康起着举足轻重的作用。");
		} else if (bodeStateType == BodeStateType.腹部肥胖率) {
			title.setText("腹部肥胖率");
			tv_content.setText("腹部肥胖又称向心性肥胖。向心性肥胖容易使我们身体并发动脉硬化、脑卒中、高血压、冠心病、糖尿病、高脂血症等疾病。腹部肥胖的人患以上症状的危险性约是全身匀称性肥胖者的2-3倍，因此我们一定要控制好饮食和运动。");
		} else if (bodeStateType == BodeStateType.肌肉率) {
			title.setText("肌肉率");
			tv_content.setText(" 肌肉率是我们身体的肌肉总量与体重的比例值。肌肉率的范围能够决定我们的身体的健康状况以及力量的多少，因此维持正常的肌肉率是我们健康和力量的基本保证。");
		} else if (bodeStateType == BodeStateType.蛋白质重量比) {
			title.setText("蛋白质重量比");
			tv_content.setText("蛋白质是组成我们人体一切细胞、组织的重要成分。我们身体的所有生命活动都需要有蛋白质的参与。如果我们机体缺乏蛋白质不仅会造成代谢率下降而且还使我们抵抗疾病能力降低；而蛋白质过量则会造成脂肪堆积，加重人体代谢负担以及造成骨质疏松等症状。");
		} else if (bodeStateType == BodeStateType.身体水分) {
			title.setText("身体水分");
			tv_content.setText("成年人体内的含水量约占人体重的65%左右。 人体内的水，是组织细胞的主要成分之一，约占人体重的50%左右。其余的水分处于血液和细胞间隙之中，还有少量转胞水存在于各空的器官。人体每天平均消耗的水分约为2500毫升，除了体内物质代谢可氧化生水300毫升外，每天至少应从饮食中补充2200毫升，才能达到平衡。 ");
		} else if (bodeStateType == BodeStateType.骨量) {
			title.setText("骨量");
			tv_content.setText("骨量是指我们人体单位体积内，骨组织（如骨矿物质（钙、磷等）和骨基质（骨胶原、蛋白质、无机盐等等）的含量。骨量偏低会造成我们机体骨密度下降，进而造成骨质疏松、抽筋等症状；而骨量偏高则会造成骨质硬化，影响我们正常的活动。");
		}

	}
}
