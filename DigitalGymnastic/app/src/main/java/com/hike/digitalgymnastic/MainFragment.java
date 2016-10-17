package com.hike.digitalgymnastic;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.fragment.BaseFragment;
import com.hike.digitalgymnastic.fragment.HeightFragment;
import com.hike.digitalgymnastic.fragment.MyGooalFrament;
import com.hike.digitalgymnastic.fragment.PersonalInfoFragment;
import com.hike.digitalgymnastic.fragment.PicHeadFragment;
import com.hike.digitalgymnastic.fragment.WeightFragment;
import com.hike.digitalgymnastic.fragment.YearFragment;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.Contants;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PreferencesUtils;
import com.hike.digitalgymnastic.utils.Utils;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_fragment)
public class MainFragment extends BaseFragmentActivity {
    public static final int fromRegister = 0;
    public static final int fromMenu = 1;


    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    @ViewInject(R.id.btn_right)
    ImageView btn_right;
    @ViewInject(R.id.title)
    TextView title;

    @ViewInject(R.id.btn_right_text)
    Button btn_right_text;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;
    @ViewInject(R.id.ll_btn_right2)
    private LinearLayout ll_btn_right2;

    @ViewInject(R.id.top_ly)
    private LinearLayout top_ly;

    @ViewInject(R.id.viewGroup)
    LinearLayout viewGroup;

    private int gender;
    private int year;
    private int height;
    private double weight;
    private String imgpath;
    private String name;
    public Customer customer;
    private int screenWidth;
    private int screenHeight;
    private int eachItemWidth;
    private int eachItemHeight;
    private int x4;
    private List<View> guiderList;
    private ByteArrayOutputStream baos;
    public static final int mode_enter = 1;
    public static final int mode_out = 2;
    private int totalPage = 2;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager fragmentManager;
    private FragmentTransaction mFragmentTransaction;
    public static String curFragmentTag;
    private PersonalInfoFragment personalInfoFragment;
    private YearFragment yearFragment;
    private HeightFragment heightFragment;
    private WeightFragment weightFragment;
    private PicHeadFragment picHeadFragment;
    private MyGooalFrament myGooalFrament;
    String[] array = new String[]{"管理员", "admin", "官方", "工作人员"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		Utils.toolBarManager(this, R.color.app_bg_login);
        ViewUtils.inject(this);
        init();
        fragmentManager = getSupportFragmentManager();
        setCurrentFragment();
    }

    private void setCurrentFragment() {
        mFragmentTransaction = fragmentManager.beginTransaction();
        loadGuiderView();
        showPreviousView(0);
        if (personalInfoFragment == null) {
            // 如果MessageFragment为空，则创建一个并添加到界面上
            personalInfoFragment = new PersonalInfoFragment();
            mFragmentTransaction.add(R.id.content_frame, personalInfoFragment,
                    getString(R.string.per_info));
            commitTransactions();
        }
        curFragmentTag = getString(R.string.per_info);
    }

    private void loadGuiderView() {
        guiderList = new ArrayList<View>();
        //原来5页变为2页
        for (int i = 0; i < totalPage; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    eachItemWidth, eachItemHeight);
            params.setMargins(0, 0, 0, 0);
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            guiderList.add(imageView);
            if (i == 0) {
                imageView.setBackgroundColor(getResources().getColor(
                        R.color.page_select));
            } else {
                imageView.setBackgroundColor(getResources().getColor(
                        R.color.grey_ae));
            }
            viewGroup.addView(imageView, i);
        }
    }

    private void showPreviousView(int j) {
        //原来是5，改为2
        for (int i = 0; i < totalPage; i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i == j) {
                imageView.setBackgroundColor(getResources().getColor(
                        R.color.page_select));
            } else {
                imageView.setBackgroundColor(getResources().getColor(
                        R.color.grey_ae));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(Constants.customer, customer);
    }

    public int fromWhich;
    BaseDao dao;

    private void init() {
        ll_btn_right.setVisibility(View.GONE);
        ll_btn_right2.setVisibility(View.VISIBLE);
        btn_right_text.setVisibility(View.VISIBLE);
        btn_right_text.setTextColor(getResources().getColor(R.color.light_green));
        btn_right_text.setText(getString(R.string.next));
        btn_right_text.setOnClickListener(myRightListener);
        ll_btn_right2.setOnClickListener(myRightListener);
//		ll_btn_right.setVisibility(View.GONE);
//		ll_btn_right.setOnClickListener(myRightListener);
        ll_btn_left.setVisibility(View.INVISIBLE);
        fromWhich = getIntent().getIntExtra("fromWhichPage",
                MainFragment.fromMenu);
        TargetSetActivity.activityList.add(this);


        dao = new BaseDao(this, this);
        customer = LocalDataUtils.readCustomer(this);
        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getWindowManager().getDefaultDisplay().getHeight();
        eachItemWidth = screenWidth / totalPage;
        eachItemHeight = (int) getResources().getDimensionPixelSize(R.dimen.y1);
        x4 = (int) getResources().getDimensionPixelSize(R.dimen.x4);
        title.setTextColor(this.getResources().getColor(R.color.umeng_socialize_list_item_textcolor));
        title.setText(getString(R.string.persioninfo));
//		ll_btn_left.setBackgroundResource(R.mipmap.back_login_3x);
        btn_left.setImageResource(R.mipmap.back_login_3x);
        btn_left.setOnClickListener(myListener);
        ll_btn_left.setOnClickListener(myListener);
        initparams();
    }

    public void initparams() {

        if (TextUtils.isEmpty(customer.getGender())) {
            gender = 1;
        } else
            gender = Integer.parseInt(customer.getGender());
        if (gender == 1) {
            year = TextUtils.isEmpty(customer.getYear()) ? Constants.defaultAge
                    : Integer.parseInt(customer.getYear());
            height = TextUtils.isEmpty(customer.getHeight()) ? Constants.defaultMaleHeight
                    : Integer.parseInt(customer.getHeight());
            weight = TextUtils.isEmpty(customer.getWeight()) ? Constants.defaultMaleWeight
                    : Double.parseDouble(customer.getWeight());
            imgpath = TextUtils.isEmpty(customer.getAvatar()) ? "" : customer
                    .getAvatar();
            name = TextUtils.isEmpty(customer.getName()) ? "" : customer
                    .getName();
        } else if (gender == 2) {
            year = TextUtils.isEmpty(customer.getYear()) ? Constants.defaultAge
                    : Integer.parseInt(customer.getYear());
            height = TextUtils.isEmpty(customer.getHeight()) ? Constants.defaultSexHeight
                    : Integer.parseInt(customer.getHeight());
            weight = TextUtils.isEmpty(customer.getWeight()) ? Constants.defaultSexWeight
                    : Double.parseDouble(customer.getWeight());
            imgpath = TextUtils.isEmpty(customer.getAvatar()) ? "" : customer
                    .getAvatar();
            name = TextUtils.isEmpty(customer.getName()) ? "" : customer
                    .getName();
        } else {
            year = TextUtils.isEmpty(customer.getYear()) ? Constants.defaultAge
                    : Integer.parseInt(customer.getYear());
            height = TextUtils.isEmpty(customer.getHeight()) ? Constants.defaultMaleHeight
                    : Integer.parseInt(customer.getHeight());
            weight = TextUtils.isEmpty(customer.getWeight()) ? Constants.defaultMaleWeight
                    : Integer.parseInt(customer.getWeight());
            imgpath = TextUtils.isEmpty(customer.getAvatar()) ? "" : customer
                    .getAvatar();
            name = TextUtils.isEmpty(customer.getName()) ? "" : customer
                    .getName();
        }

    }

    public OnClickListener myListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_left:
                    fragmentBack();
                    break;
                case R.id.ll_btn_left:
                    fragmentBack();
                    break;
                default:
                    break;
            }
        }
    };

    public OnClickListener myRightListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
//				case R.id.ll_btn_right:
//					next();
//					break;
                case R.id.btn_right_text:
                    next();
                    break;
                case R.id.ll_btn_right2:
                    next();
                    break;
//				myRightListener
                default:
                    break;
            }
        }
    };

    public void next() {

        if (TextUtils.equals(curFragmentTag, getString(R.string.per_info))) {
            setTabSelection(getString(R.string.pichead),
                    MainFragment.mode_out);
            showPreviousView(1);
            ll_btn_left.setVisibility(View.VISIBLE);
        } else {

            name = PreferencesUtils.getString(this, Contants.NICKNAME);
            if (TextUtils.isEmpty(name)) {
                Utils.showMessage(this, "请填写你的昵称！");
            } else if (name.contains(" ")) {
                Utils.showMessage(this, "昵称中包含非法字符！");
            } else if (!Utils.check(name)) {
                Utils.showMessage(this, "昵称中仅支持中英文及数字！");
            } else if (Utils.isToLong(name)) {
                Utils.showMessage(this, "你输入的昵称过长！");
            } else if (name.contains(array[0]) || name.contains(array[1])
                    || name.contains(array[2]) || name.contains(array[3])) {
                Utils.showMessage(this, "你输入的昵称中包含敏感词！");
            } else {
                if (imgpath == null || imgpath.length() == 0) {
//					Utils.showMessage(activity, "请上传头像");
                    this.enterTargetActivity();
                } else {
                    imgpath = this.getCustomer()
                            .getAvatar();
                    this.setPichead(imgpath, name);// 数据返回给主Activity

                    this.enterTargetActivity();
                }
            }
            dao.ModifyCustomer(customer);
        }

    }

    public void fragmentBack() {

        if (!TextUtils.equals(curFragmentTag, getString(R.string.gender))) {
            if (TextUtils.equals(curFragmentTag, getString(R.string.pichead))) {
                setTabSelection(getString(R.string.per_info),
                        MainFragment.mode_out);
                showPreviousView(0);
                ll_btn_left.setVisibility(View.INVISIBLE);
            }
//			else if (TextUtils.equals(curFragmentTag,
//					getString(R.string.year))) {
//				setTabSelection(getString(R.string.gender),
//						MainFragment.mode_out);
//				showPreviousView(0);
//				ll_btn_left.setVisibility(View.INVISIBLE);
//			} else if (TextUtils.equals(curFragmentTag,
//					getString(R.string.height))) {
//				setTabSelection(getString(R.string.year), MainFragment.mode_out);
//				showPreviousView(1);
//				ll_btn_left.setVisibility(View.VISIBLE);
//			} else if (TextUtils.equals(curFragmentTag,
//					getString(R.string.weight))) {
//				setTabSelection(getString(R.string.height),
//						MainFragment.mode_out);
//				showPreviousView(2);
//				ll_btn_left.setVisibility(View.VISIBLE);
//			} else if (TextUtils.equals(curFragmentTag,
//					getString(R.string.menu_my_goal))) {
//				setTabSelection(getString(R.string.menu_my_goal),
//						MainFragment.mode_out);
//				showPreviousView(4);
//
//				exitTargetAtivity();
//				return;
//			}
            switchFragment(curFragmentTag);
        } else {
            // finish();
        }
    }

    /**
     * 设置性别
     *
     * @param gender
     */
    public void setGender(int gender) {
        this.gender = gender;
        customer.setGender(String.valueOf(this.gender));
        setTabSelection(getString(R.string.year), MainFragment.mode_enter);
        showPreviousView(1);
        ll_btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置年龄
     *
     * @param currentAge
     */
    public void setYear(int currentAge) {
        setTabSelection(getString(R.string.height), MainFragment.mode_enter);
        this.year = currentAge;
        customer.setYear(String.valueOf(this.year));
        showPreviousView(2);
        ll_btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置身高
     *
     * @param height
     */
    public void setHeight(int height) {
        this.height = height;
        customer.setHeight(String.valueOf(this.height));
        setTabSelection(getString(R.string.weight), MainFragment.mode_enter);
        showPreviousView(3);
        ll_btn_left.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        LocalDataUtils.saveCustomer(this, customer);
    }

    /**
     * 设置体重
     *
     * @param currentWeight
     */
    public void setWeight(double currentWeight) {
        this.weight = currentWeight;
        customer.setWeight(String.valueOf(this.weight));
        setTabSelection(getString(R.string.pichead), MainFragment.mode_enter);
        showPreviousView(4);
        ll_btn_left.setVisibility(View.VISIBLE);
    }

    /**
     * 设置性别，年龄，体重，身高等信息
     *
     * @param gender
     * @param year
     * @param height
     * @param weight 1 开始拍照 2 提交图片路径
     */
    public void setPerInfo(int gender, int year, int height, int weight) {

        this.gender = gender;
        this.year = year;
        this.weight = weight;
        this.height = height;
        System.out.println("gender" + this.gender);
        System.out.println("year" + this.year);
        System.out.println("weight" + this.weight);
        System.out.println("height" + this.height);
//		setTabSelection(getString(R.string.pichead), MainFragment.mode_enter);
    }

    /**
     * 设置头像
     *
     * @param imgpath
     * @param name    1 开始拍照 2 提交图片路径
     */
    public void setPichead(String imgpath, String name) {
        this.imgpath = imgpath;
        this.name = name;
        customer.setAvatar(this.imgpath);
        customer.setName(this.name);
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getGender() {
        return this.gender;
    }

    public int getYear() {
        return this.year;
    }

    public int getHeight() {
        return this.height;
    }

    public double getWeight() {
        return this.weight;
    }

    public String getImgpath() {
        return this.imgpath;
    }

    public String getName() {
        return this.name;
    }


    // 进入目标设定页面
    public void enterTargetActivity() {
//		 Intent intent = new Intent(this, TargetSetActivity.class);
//		 intent.putExtra(Constants.customer, customer);
//		 intent.putExtra("name",getName() );
//		 intent.putExtra("gender",getGender() );
//		 intent.putExtra("weight",getWeight());
//		 intent.putExtra("height",getHeight());
//		 intent.putExtra("year",getYear() );
//		 intent.putExtra("imgpath",getImgpath() );
//
//		 startActivity(intent);

        setTabSelection(getString(R.string.menu_my_goal), 0);
    }

    public void exitTargetAtivity() {
        setTabSelection(getString(R.string.pichead), 0);
    }

    /**
     * 根据传入的tag参数来设置选中的tab页。
     *
     * @param tag
     */
    public void setTabSelection(String tag, int num) {
        top_ly.setVisibility(View.VISIBLE);
        viewGroup.setVisibility(View.VISIBLE);
        title.setText(getString(R.string.persioninfo));
        // 开启一个Fragment事务
        mFragmentTransaction = fragmentManager.beginTransaction();
        if (num == 1) {
            mFragmentTransaction.setCustomAnimations(R.anim.next_right_in,
                    R.anim.next_right_out);
        } else if (num == 2) {
            mFragmentTransaction.setCustomAnimations(R.anim.next_view_enter,
                    R.anim.next_view_out);
        } else {
//			topbar_ll.setVisibility(View.GONE);
            mFragmentTransaction
                    .setCustomAnimations(R.anim.urual, R.anim.urual);
        }
        if (TextUtils.equals(tag, getString(R.string.gender))) {
            // 当点击了消息tab时，改变控件的图片和文字颜色
            if (personalInfoFragment == null) {
                personalInfoFragment = new PersonalInfoFragment();
            }

        } else if (TextUtils.equals(tag, getString(R.string.year))) {
            if (yearFragment == null) {
                yearFragment = new YearFragment();
            }
        } else if (TextUtils.equals(tag, getString(R.string.height))) {
            if (heightFragment == null) {
                heightFragment = new HeightFragment();
            }
        } else if (TextUtils.equals(tag, getString(R.string.weight))) {
            if (weightFragment == null) {
                weightFragment = new WeightFragment();
            }
        } else if (TextUtils.equals(tag, getString(R.string.per_info))) {
            if (personalInfoFragment == null) {

                personalInfoFragment = new PersonalInfoFragment();
            }
        } else if (TextUtils.equals(tag, getString(R.string.pichead))) {
//			if (picHeadFragment == null) {
            picHeadFragment = new PicHeadFragment();
//			}
        } else if (TextUtils.equals(tag, getString(R.string.menu_my_goal))) {
            top_ly.setVisibility(View.GONE);
            if (myGooalFrament == null) {

                myGooalFrament = new MyGooalFrament();
            }
            title.setText(getString(R.string.menu_my_goal));

            viewGroup.setVisibility(View.INVISIBLE);
        }
        switchFragment(tag);
    }


    public void switchFragment(String tag) {
        if (TextUtils.equals(tag, curFragmentTag)) {
            Log.e("switchFragment", "curTag == tag");
            return;
        }
        if (curFragmentTag != null) {
            hideFragment(getFragment(curFragmentTag));
        }
        attachFragment(R.id.content_frame, getFragment(tag), tag);
        curFragmentTag = tag;
        commitTransactions();
    }

    private void hideFragment(Fragment f) {
        mFragmentTransaction.hide(f);
    }

    private FragmentTransaction ensureTransaction() {
        if (mFragmentTransaction == null) {
            mFragmentTransaction = fragmentManager.beginTransaction();
            mFragmentTransaction
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            mFragmentTransaction.setCustomAnimations(R.anim.push_top_in,
                    R.anim.push_top_out);
        }
        return mFragmentTransaction;
    }

    private void attachFragment(int layout, Fragment f, String tag) {
        if (f != null) {
            if (f.isAdded()) {
                mFragmentTransaction.show(f);
            } else {
                ensureTransaction();
                mFragmentTransaction.add(layout, f, tag);
            }
        }
    }

    private void commitTransactions() {
        if (mFragmentTransaction != null && !mFragmentTransaction.isEmpty()) {
            mFragmentTransaction.commit();
            mFragmentTransaction = null;
        }
    }

    private Fragment getFragment(String tag) {
        Fragment f = fragmentManager.findFragmentByTag(tag);
        if (f == null) {
            f = BaseFragment.newInstance(getApplicationContext(), tag);
        }
        return f;
    }

    /**
     * 监听后退事件
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 按下的如果是BACK，同时没有重复
            fragmentBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
//		Utils.toolBarManager(this, R.color.app_bg_login);
    }
}
