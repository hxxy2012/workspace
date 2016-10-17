package com.hike.digitalgymnastic;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by huwei on 16/3/29.
 */
@ContentView(R.layout.activity_targetset)
public class MyGoalActivity extends BaseActivity implements View.OnClickListener {

    private int stepRate = 26;
    private float runRate = (float) 0.018;
    private float SwipRate = (float) 0.11;
    //	@ViewInject(R.id.tv_target)
//	private TextView tv_target;
//	@ViewInject(R.id.iv_targetSetImageView)
//	private TargetSetImageView iv_targetSetImageView;
//	@ViewInject(R.id.tv_target_walk)
//	private TextView tv_target_walk;
//	@ViewInject(R.id.tv_target_run)
//	private TextView tv_target_run;
//	@ViewInject(R.id.tv_target_swip)
//	private TextView tv_target_swip;
    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.title)
    private TextView title;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;
    @ViewInject(R.id.top)
    private View top;


    @ViewInject(R.id.btn_finish)
    private Button btn_finish;

    private BaseDao dao;
    private Customer changecustomer;

    @ViewInject(R.id.kcal_value)
    private TextView kcal_value;
    @ViewInject(R.id.tv_step)
    private TextView tv_step;
    @ViewInject(R.id.tv_run)
    private TextView tv_run;
    @ViewInject(R.id.tv_swim)
    private TextView tv_swim;
    @ViewInject(R.id.seek_bar)
    private SeekBar seek_bar;


    @OnClick(value = {R.id.btn_left, R.id.ll_btn_left, R.id.btn_finish})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
            case R.id.ll_btn_left:
                finish();
                break;
            case R.id.btn_finish:
                if (goalCalories > 0) {
                    showProgress(this, true);
                    changecustomer = LocalDataUtils.readCustomer(this);
                    SimpleDateFormat format = new SimpleDateFormat("yyyy");
                    String currentYear = format.format(new Date());
                    PreferencesUtils.putInt(this, "customer_age", Integer.parseInt(currentYear) - Integer.parseInt(customer.getYear()));
                    changecustomer.setGoalCalories(goalCalories + "");
                    dao.ModifyCustomer(changecustomer);

                } else {
                    Utils.showMessage(this, "请选择目标");
                }
                break;
            default:
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initView();
        initData();
    }

    public void initView() {
        kcal_value = (TextView) findViewById(R.id.kcal_value);
        tv_step = (TextView) findViewById(R.id.tv_step);
        tv_run = (TextView) findViewById(R.id.tv_run);
        tv_swim = (TextView) findViewById(R.id.tv_swim);
        btn_left = (ImageView) findViewById(R.id.btn_left);
        title = (TextView) findViewById(R.id.title);
        ll_btn_left = (LinearLayout) findViewById(R.id.ll_btn_left);
        ll_btn_right = (LinearLayout) findViewById(R.id.ll_btn_right);
        top = findViewById(R.id.top);
        btn_finish = (Button) findViewById(R.id.btn_finish);

        ll_btn_left.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
    }

    private void initData() {
        getLocalCustomer();
        btn_left.setImageResource(R.mipmap.back_login_3x);
        title.setText("每日运动目标");
        title.setTextColor(getResources().getColor(R.color.umeng_socialize_text_share_content));
        dao = new BaseDao(this, this);
        String goalCaloriesValue = null;
        goalCaloriesValue = goalCalories + "";

        if (TextUtils.isEmpty(goalCaloriesValue)) {
            goalCaloriesValue = "500";
        }

//		goalCalories =Integer.parseInt(goalCaloriesValue);
        BigDecimal dd = new BigDecimal(goalCaloriesValue).setScale(0, BigDecimal.ROUND_HALF_UP);
        goalCalories = dd.intValue();
        int kcalValue = goalCalories;
        kcal_value.setText(kcalValue + "");

        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        tv_step.setText(String.valueOf(kcalValue * stepRate) + "步");
        tv_run.setText(String.valueOf(decimalFormat.format(kcalValue * runRate)) + "km");
        tv_swim.setText(String.valueOf(decimalFormat.format(kcalValue * SwipRate)) + "分钟");

        seek_bar.setMax(1500);
        seek_bar.setProgress(500);
        seek_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                goalCalories = progress;
                DecimalFormat decimalFormat = new DecimalFormat("#.#");
                kcal_value.setText(String.valueOf(progress));
                tv_step.setText(String.valueOf(progress * stepRate) + "步");
                tv_run.setText(String.valueOf(decimalFormat.format(progress * runRate)) + "km");
                tv_swim.setText(String.valueOf(decimalFormat.format(progress * SwipRate)) + "分钟");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    private String gender;
    private String year;
    private String height;
    private String weight;
    public int goalCalories;
    public String avatar;
    public String description;
    public String name;

    private void getLocalCustomer() {
        Customer customer = LocalDataUtils.readCustomer(this);
        name = customer.getName();
        avatar = customer.getAvatar();
        gender = customer.getGender();
        year = customer.getYear();
        height = customer.getHeight();
        weight = customer.getWeight();
        goalCalories = Integer.parseInt(customer.getGoalCalories());
        description = customer.getDescription();
    }

    @Override
    public void onNoConnect() {
        // TODO Auto-generated method stub
        super.onNoConnect();
        showProgress(this, false);
    }

    @Override
    public void onRequestFaild(String errorNo, String errorMessage) {
        // TODO Auto-generated method stub
        super.onRequestFaild(errorNo, errorMessage);
        showProgress(this, false);
    }

    boolean isFromRegister = false;

    public void onRequestSuccess(int requestCode) {
        showProgress(this, false);
        if (requestCode == 9) {
            Utils.showMessage(this, "修改成功");
//            PreferencesUtils.putString(this, Constants.step, tv_step.getText().toString());
//            PreferencesUtils.putString(this, Constants.run, tv_run.getText().toString());
//            PreferencesUtils.putString(this, Constants.swim, tv_swim.getText().toString());
            LocalDataUtils.saveCustomer(this, changecustomer);
            finish();
            if (isFromRegister) {//注册之后进入方式
                Utils.showMessage(this, "提交成功！");
                LocalDataUtils.saveCustomer(this, changecustomer);
                Intent intent = new Intent(this, DeviceScanActivity.class);
                if (android.os.Build.VERSION.SDK_INT < 18) {
                    intent = new Intent(this, MainActivity.class);
                } else {
                    intent.putExtra(Constants.customer, changecustomer);
                    intent.putExtra(Constants.isRegister, true);
                }
                startActivity(intent);
                finish();
            }

        }
    }


}
