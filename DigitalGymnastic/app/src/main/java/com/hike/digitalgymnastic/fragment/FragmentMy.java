package com.hike.digitalgymnastic.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.AboutUsActivity;
import com.hike.digitalgymnastic.DiaryPageActivity;
import com.hike.digitalgymnastic.FeedBackActivity;
import com.hike.digitalgymnastic.GuiderActivity;
import com.hike.digitalgymnastic.HikoDigitalgyApplication;
import com.hike.digitalgymnastic.MainActivity;
import com.hike.digitalgymnastic.MyGoalActivity;
import com.hike.digitalgymnastic.MyMessageActivity;
import com.hike.digitalgymnastic.db.DBManager;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.Set;

/**
 * Created by huwei on 16/3/23.
 * <p/>
 * 我的页面
 */
public class FragmentMy extends BaseFragment implements View.OnClickListener {
    //
    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    @ViewInject(R.id.btn_right)
    ImageView btn_right;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;


    @ViewInject(R.id.iv_touxiang)
    ImageView iv_touxiang;

    @ViewInject(R.id.tv_head)
    TextView tv_head;
    @ViewInject(R.id.title)
    TextView title;
    //点击事件注册button
    @ViewInject(R.id.rl_head)
    RelativeLayout rl_head;
    @ViewInject(R.id.rl_aboutus)
    RelativeLayout rl_aboutus;
    @ViewInject(R.id.rl_homepage)
    RelativeLayout rl_homepage;
    @ViewInject(R.id.rl_goal)
    RelativeLayout rl_goal;
    @ViewInject(R.id.rl_feedback)
    RelativeLayout rl_feedback;
    @ViewInject(R.id.tv_loginout)
    TextView tv_loginout;
    static FragmentMy my;

    public static FragmentMy newInstance() {
        if (my == null) {
            my = new FragmentMy();
        }
        return my;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.inflater = inflater;
        View _view = inflater.inflate(R.layout.fragment_my, container, false);
        ViewUtils.inject(this, _view);
        return _view;
    }

    BitmapUtils bitmapUtils;

    private void initBitmapUtils() {
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/image";
        bitmapUtils = new BitmapUtils(activity, imageCachePath);
        bitmapUtils.configDefaultLoadFailedImage(R.mipmap.boy_head);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(activity));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initBitmapUtils();
//        initCustomer();
        init();
        initCustomer2();
    }


    BaseDao dao;

    private void init() {
        ll_btn_left.setVisibility(View.GONE);
        title.setText("我的");
        dao = new BaseDao(this, activity);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (application.isCustomerModify) {
            application.isCustomerModify = false;
//            initCustomer();
            initCustomer2();

        }
    }

    private void initCustomer2() {

        if (activity instanceof MainActivity) {
            ((MainActivity) activity).getLocalCusInfo();
            customer = ((MainActivity) activity).getCustomer();
            if (TextUtils.isEmpty(this.customer.getName()))
                dao.GetCustomer();
            else
                tv_head.setText(customer.getName());
            if (!TextUtils.isEmpty(this.customer.getAvatar())) {
                bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + customer.getAvatar());
            } else
                dao.GetCustomer();
        }
    }

    private void initCustomer() {
        Customer customer = LocalDataUtils.readCustomer(activity);
        if (TextUtils.isEmpty(customer.getName()))
            tv_head.setText("客户");
        else
            tv_head.setText(customer.getName());
        if (!TextUtils.isEmpty(customer.getAvatar())) {
            bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + customer.getAvatar());
        } else
            iv_touxiang.setImageResource(R.mipmap.boy_head);
    }

    @OnClick(value = {R.id.rl_head, R.id.tv_loginout, R.id.rl_aboutus, R.id.rl_homepage, R.id.rl_goal, R.id.rl_feedback})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_head:
                Intent it = new Intent(activity, MyMessageActivity.class);
                startActivity(it);
                break;
            case R.id.rl_homepage:
                Intent intent = new Intent(activity, DiaryPageActivity.class);
                intent.putExtra(Constants.customerName, customer.getName());
                intent.putExtra(Constants.customerAvator, customer.getAvatar());
                intent.putExtra(Constants.customerId, Long.parseLong(customer.getId()));
                startActivity(intent);
                break;
            case R.id.rl_goal:
                Intent it_message = new Intent(activity, MyGoalActivity.class);
                startActivity(it_message);
                break;
            case R.id.rl_feedback:
                startActivity(new Intent(activity, FeedBackActivity.class));
                break;
            case R.id.rl_aboutus:
                startActivity(new Intent(activity, AboutUsActivity.class));
                break;
            case R.id.tv_loginout:
                DBManager.getIntance(activity, null);//清除DBManager实例化对象
                showProgress(true);
                dao.Logout();

                break;
            default:
                break;
        }
    }


    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (getActivity() != null && !getActivity().isFinishing() && getActivity() != null) {
            showProgress(false);
        }
        if (requestCode == 1) {
            logout();
        }
        if (requestCode == 8) {
            Customer cus = dao.getCustomer();
            if (TextUtils.isEmpty(cus.getAvatar()))
                iv_touxiang.setImageResource(R.mipmap.boy_head);
            else
                bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + cus.getAvatar());
            if (TextUtils.isEmpty(cus.getName()))
                tv_head.setText("客户");
            else
                tv_head.setText(cus.getName());

        }
    }


    void logout() {
        try {
            LocalDataUtils.saveLoginInfo(activity, false);
            LocalDataUtils.clearClock();
            Intent intent = new Intent(activity, GuiderActivity.class);
            startActivity(intent);
            Set<String> set = HikoDigitalgyApplication.map.keySet();
            String[] nameArray = new String[100];
            set.toArray(nameArray);

            for (String activityName : nameArray) {
                Activity ay = HikoDigitalgyApplication.map.get(activityName);
                if (ay != null) {
                    ay.finish();
                }
            }
            application.logout();
            System.gc();
            if (activity instanceof Activity) {
                ((Activity) activity).finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
