package com.hike.digitalgymnastic;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.FastBlur;
import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.CircleImageView;
import com.hike.digitalgymnastic.view.ImageHelper;
import com.hike.digitalgymnastic.view.PickerViewTwo;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.callback.BitmapLoadCallBack;
import com.lidroid.xutils.bitmap.callback.BitmapLoadFrom;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/*
 * 个人信息修改
 */

@ContentView(R.layout.activity_menu_my_message)
public class MyMessageActivity extends BaseActivity implements ReqeustCode {

    @ViewInject(R.id.btn_left)
    private ImageView btn_left;
    @ViewInject(R.id.ll_btn_left)
    private LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    private LinearLayout ll_btn_right;

    @ViewInject(R.id.btn_right_text)
    private Button btn_right_text;
    @ViewInject(R.id.title)
    private TextView title;
    //	@ViewInject(R.id.top_rl)
//	private RelativeLayout top_rl;
    @ViewInject(R.id.rl_gender)
    private RelativeLayout rl_gender;
    @ViewInject(R.id.rl_year)
    private RelativeLayout rl_year;
    @ViewInject(R.id.rl_height)
    private RelativeLayout rl_height;
    @ViewInject(R.id.rl_weight)
    private RelativeLayout rl_weight;
    //	@ViewInject(R.id.top_pic)
//	private RelativeLayout top_pic;
    @ViewInject(R.id.iv_camera)
    private CircleImageView iv_camera;
//	@ViewInject(R.id.et_person_name)
//	private EditText et_person_name;

    @ViewInject(R.id.tv_value_gender)
    private TextView tv_value_gender;
    @ViewInject(R.id.tv_value_year)
    private TextView tv_value_year;
    @ViewInject(R.id.tv_value_height)
    private TextView tv_value_height;
    @ViewInject(R.id.tv_value_weight)
    private TextView tv_value_weight;

    @ViewInject(R.id.rl_head)
    private RelativeLayout rl_head;

    @ViewInject(R.id.rl_nickname)
    private RelativeLayout rl_nickname;

    @ViewInject(R.id.tv_nickname2)
    private TextView tv_nickname2;


    private View v;
    private BaseDao dao;

    // 记录当前选中的各个参数
    private String gender;
    private String year;
    private String height;
    private String weight;
    public String goalCalories;
    public String description;
    public String name;
    public String avatar;

    private Bitmap myBitmap;
    private ByteArrayOutputStream baos;
    String headPath;

    String[] array = new String[]{"管理员", "admin", "官方", "工作人员"};

    @OnClick(value = {R.id.btn_left, R.id.ll_btn_left, R.id.rl_gender,
            R.id.btn_right_text, R.id.rl_year, R.id.rl_height, R.id.rl_weight,
            R.id.iv_camera, R.id.rl_head, R.id.rl_nickname})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
            case R.id.ll_btn_left:
                View view = getWindow().peekDecorView();
                if (view != null) {
                    InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                finish();
                break;
            case R.id.btn_right_text:

                break;
            case R.id.rl_gender:

                showGenderPicker();
                break;
            case R.id.rl_year:
                showAgePicker(age_value);
                break;
            case R.id.rl_height:
                showHeightPicker(String.valueOf(height_value));
                break;
            case R.id.rl_weight:
                showWeightPicker(String.valueOf(weight_value));
                break;
            case R.id.iv_camera:
            case R.id.rl_head:
                // 头像
//                showPicturePickerDialog();
                showImagePicker();
                break;
            case R.id.rl_nickname:
                showNickDialog();
                break;
            default:
                break;
        }
    }


    private void showNickDialog() {
        // 拍照/从相册选择
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View mView = LayoutInflater.from(this).inflate(R.layout.item_nickdialog, null);
        final EditText et_name = (EditText) mView.findViewById(R.id.et_name);
        TextView tv_ensure = (TextView) mView.findViewById(R.id.positiveButton);
        TextView tv_cancel = (TextView) mView.findViewById(R.id.negativeButton);
        checkEtName(et_name);
        tv_ensure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_name.getText().toString().length() == 0) {
                    Utils.showMessage(MyMessageActivity.this, "请输入昵称");
                } else {
                    tv_nickname2.setText(et_name.getText().toString());
                    customer.setName(tv_nickname2.getText().toString());
                    dao.ModifyCustomer(customer);
                    dialog.dismiss();
                }
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(mView);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.width = (int) getResources().getDimension(R.dimen.x280); // 宽度
        lp.height = (int) getResources().getDimension(R.dimen.y114); // 高度
        // dialog.onWindowAttributesChanged(lp);
        //(当Window的Attributes改变时系统会调用此函数)
        window.setAttributes(lp);
        dialog.show();
    }

    private void checkEtName(EditText et_name) {
        String name = tv_nickname2.getText().toString();
        if (!TextUtils.isEmpty(name)) {
            et_name.setText(name);
            et_name.setSelection(name.length());
        }
        initSoftKeyBoard(et_name);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initData();
    }

    File cacheFile;
    File captureFile;

    private void initData() {
        headPath = Environment.getExternalStorageDirectory()
                .getAbsolutePath() + "/" + getString(R.string.app_name) + "/分享/picHead.jpg";
        title.setText("个人资料");
        cacheFile = new File(FileUtil.getStoragePathIfMounted(this),
                "cache.jpg");
        captureFile = new File(FileUtil.getStoragePathIfMounted(this),
                "capture.jpg");

        dao = new BaseDao(this, this);
        initBitmapUtils();
        tv_nickname2.setText(LocalDataUtils.readCustomer(this).getName());
        changeData();
    }

    TextWatcher watcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                  int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable editable) {
            // TODO Auto-generated method stub
            String name = editable.toString();
            if (name.contains(" ")) {
                name = name.trim();
//				et_person_name.setText(name);
            }
            // else if(is2Long(name)){
            // et_person_name.setText(name.substring(0, name.length()-1));
            // }

        }
    };

//    @Override
//    public void onAttach(Activity activity) {
//        // TODO Auto-generated method stub
//        super.onAttach(activity);
//        MainMenuActivity mma = (MainMenuActivity) activity;
//        mma.setDataCommuiation(new DataCommuiation() {
//
//            @Override
//            public void onKyDown() {
//                // TODO Auto-generated method stub
//                submit();
//            }
//        });
//    }

    private void showPicturePickerDialog() {
        Intent intent = null;
        intent = new Intent(this, PicturePickerActivity.class);
        startActivityForResult(intent, Constants.requestCode_pickpicture);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FROM_GALLERY) {
                if (data != null) {
                    String path = PhotoPicker.getPhotoPathByLocalUri(this,
                            data);
                    if (!TextUtils.isEmpty(path)) {
                        Bitmap bitmap = Utils.imageFile2Bitmap(new File(path));
                        Utils.bitmap2JPG(bitmap, cacheFile);
                        uploadFile(cacheFile);
                    }
                }
            } else if (requestCode == FROM_CAPTURE) {
                if (cacheFile != null && captureFile != null
                        && captureFile.exists()) {
                    Bitmap bitmap = Utils.imageFile2Bitmap(captureFile);
                    Utils.bitmap2JPG(bitmap, cacheFile);
                    uploadFile(cacheFile);
                }
            }
        }
    }


    /**
     * @category 上传图片
     */
    private void uploadFile(File file) {
        showProgress(this, true);
        dao.uploadFile(2, file);
    }


    @Override
    public void onResume() {
        super.onResume();
        changeData();
    }

    private void changeData() {
        Customer customer = LocalDataUtils.readCustomer(this);
        avatar = customer.getAvatar();
        description = customer.getDescription();
        gender = customer.getGender();
        year = customer.getYear();
        height = customer.getHeight();
        weight = customer.getWeight();
        goalCalories = customer.getGoalCalories();
        if (TextUtils.isEmpty(goalCalories))
            goalCalories = "0";
        if ("1".equals(gender)) {
            tv_value_gender.setText("男");
            if (TextUtils.isEmpty(year))
                year = String.valueOf(Constants.defaultAge);
            if (TextUtils.isEmpty(height))
                height = String.valueOf(Constants.defaultMaleHeight);
            if (TextUtils.isEmpty(weight))
                weight = String.valueOf(Constants.defaultMaleWeight);
        } else {
            tv_value_gender.setText("女");
            if (TextUtils.isEmpty(year))
                year = String.valueOf(Constants.defaultAge);
            if (TextUtils.isEmpty(height))
                height = String.valueOf(Constants.defaultSexHeight);
            if (TextUtils.isEmpty(weight))
                weight = String.valueOf(Constants.defaultSexWeight);
        }
        if (!TextUtils.isEmpty(name))
            tv_nickname2.setText(name);
        int yearint = Integer.parseInt(year);
        if (yearint < 1930) {
            yearint = Constants.defaultStartYear;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        String currentYear = format.format(new Date());
        age = Integer.parseInt(currentYear) - yearint + 1;
        tv_value_year.setText(age + "");
        tv_value_height.setText(height + "cm");
        tv_value_weight.setText(Float.parseFloat(weight) + "kg");
        initHeadImage();
    }

    int age;

    private void initHeadImage() {
        display(iv_camera, avatar);
    }

    Map<String, SoftReference<Bitmap>> bmpMap = new HashMap<String, SoftReference<Bitmap>>();

    private void setPic(final String str) {
        if (!TextUtils.isEmpty(str)) {// 此处启动线程获取头像
            showProgress(this, true);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    File file = new File(headPath);
                    if (file.exists()) {
                        Message msg = new Message();
                        msg.obj = BitmapFactory.decodeFile(headPath);
                        handler.sendMessage(msg);
                        return;
                    }
                    String url = HttpConnectUtils.image_ip + str;
                    boolean isNeedRequestNet = true;
                    if (bmpMap.get(url) != null) {
                        SoftReference<Bitmap> srf = bmpMap.get(url);
                        if (srf.get() != null) {
                            isNeedRequestNet = false;
                            Message msg = new Message();
                            msg.obj = srf.get();
                            handler.sendMessage(msg);
                        } else {
                            isNeedRequestNet = true;
                        }
                    }
                    if (isNeedRequestNet) {
                        Bitmap bitmap = Utils.getHttpBitmap(url);
                        if (bitmap != null) {
                            String path = Utils.saveHeadPic(bitmap);
                            SoftReference<Bitmap> srf = new SoftReference<Bitmap>(
                                    bitmap);
                            bmpMap.put(url, srf);
                        }
                        Message msg = new Message();
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    }
                }
            }).start();
        }
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {

        @SuppressLint("NewApi")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showProgress(MyMessageActivity.this, false);
            if (msg.obj != null) {
                try {
//					System.gc();
                    final Bitmap bmp = (Bitmap) msg.obj;
                    iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(bmp));
//					top_pic.setBackgroundDrawable(new BitmapDrawable(bmp));
//					bitmapMohu();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public void onRequestSuccess(int requestCode) {
        showProgress(this, false);
        if (requestCode == 9) {// 修改个人信息
            isSubmitting = false;
            isUpdataedPicHead = false;
            Utils.showMessage(this, "修改成功");
            application.isCustomerModify = true;
            LocalDataUtils.saveCustomer(this, customer);
        }
        if (requestCode == 83) {// 上传文件
            this.avatar = dao.getImageurl().getImageUrl();
            customer.setAvatar(avatar);
            dao.ModifyCustomer(customer);
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inSampleSize = 3;
            Bitmap bm = BitmapFactory.decodeFile(cacheFile.getPath(), option);
            Bitmap bitmap = ImageHelper.toRoundBitmap(bm);
            iv_camera.setImageBitmap(bitmap);

        }

    }



    boolean isSubmitting = false;
    boolean isUpdataedPicHead = false;

    public void submit() {
        View view = getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
//        ((MainMenuActivity) ).fragmentBack();
        if (!isSubmitting) {
//
//			// name = et_person_name.getText().toString().trim();
//			name = et_person_name.getText().toString();
//			if (TextUtils.isEmpty(name)) {
//				Utils.showMessage(activity, "请填写你的昵称！");
//			} else if (name.contains(" ")) {
//				Utils.showMessage(activity, "昵称中包含非法字符！");
//			} else if (!check(name)) {
//				Utils.showMessage(activity, "昵称中仅支持中英文及数字！");
//			} else if (is2Long(name)) {
//				Utils.showMessage(activity, "你输入的昵称过长！");
//			} else if (name.contains(array[0]) || name.contains(array[1])
//					|| name.contains(array[2]) || name.contains(array[3])) {
//				Utils.showMessage(activity, "你输入的昵称中包含敏感词！");
//			} else if (!checkIsNeedUpdate()) {
//				((MainMenuActivity) activity).fragmentBack();
//			} else {
//				showProgress(true);
//				isSubmitting = true;
//				changCustomerData();
//				dao.ModifyCustomer(changecustomer);
//
//			}
        }
    }

    private boolean checkIsNeedUpdate() {
        Customer customer = LocalDataUtils.readCustomer(this);
        if (isUpdataedPicHead) {// 如果更新了头像，必须提交一次
            return true;
        } else if (!TextUtils.isEmpty(gender)
                && !TextUtils.equals(gender, customer.getGender())) {
            return true;
        } else if (!TextUtils.isEmpty(year)
                && !TextUtils.equals(year, customer.getYear())) {
            return true;
        } else if (!TextUtils.isEmpty(height)
                && !TextUtils.equals(height, customer.getHeight())) {
            return true;
        } else if (!TextUtils.isEmpty(weight)
                && !TextUtils.equals(weight, customer.getWeight())) {
            return true;
        } else if (!TextUtils.isEmpty(goalCalories)
                && !TextUtils.equals(goalCalories, customer.getGoalCalories())) {
            return true;
        } else if (!TextUtils.isEmpty(description)
                && !TextUtils.equals(description, customer.getDescription())) {
            return true;
        } else if (!TextUtils.isEmpty(name)
                && !TextUtils.equals(name, customer.getName())) {
            return true;
        } else if (!TextUtils.isEmpty(avatar)
                && !TextUtils.equals(avatar, customer.getAvatar())) {
            return true;
        }
        return false;
    }

    public void bitmapMohu() {
//		top_pic.getViewTreeObserver().addOnPreDrawListener(
//				new ViewTreeObserver.OnPreDrawListener() {
//					@Override
//					public boolean onPreDraw() {
//
//						top_pic.getViewTreeObserver().removeOnPreDrawListener(
//								this);
//						top_pic.buildDrawingCache();
//
//						Bitmap bmp = top_pic.getDrawingCache();
//						// if(android.os.Build.VERSION.SDK_INT>=17){
//						blur(bmp, top_pic);
//						// }
//						return true;
//					}
//				});
    }

	/*
     * @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1) private void blur(Bitmap
	 * bkg, View view) { long startMs = System.currentTimeMillis();
	 * 
	 * float radius = 25;
	 * 
	 * Bitmap overlay = Bitmap.createBitmap((int) (view.getMeasuredWidth()),
	 * (int) (view.getMeasuredHeight()), Bitmap.Config.ARGB_8888);
	 * 
	 * Canvas canvas = new Canvas(overlay);
	 * 
	 * canvas.translate(-view.getLeft(), -view.getTop()); canvas.drawBitmap(bkg,
	 * 0, 0, null);
	 * 
	 * RenderScript rs = RenderScript.create(activity);
	 * 
	 * Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
	 * 
	 * ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs,
	 * overlayAlloc.getElement());
	 * 
	 * blur.setInput(overlayAlloc);
	 * 
	 * blur.setRadius(radius);
	 * 
	 * blur.forEach(overlayAlloc);
	 * 
	 * overlayAlloc.copyTo(overlay);
	 * 
	 * view.setBackground(new BitmapDrawable(getResources(), overlay));
	 * 
	 * rs.destroy(); System.gc(); //
	 * statusText.setText(System.currentTimeMillis() - startMs + "ms"); }
	 */

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 1;
        float radius = 25;
        /*
         * if (downScale.isChecked()) { scaleFactor = 8; radius = 2; }
		 */

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
    }

    private boolean is2Long(String str) {
        String hanziEx = "[\\u4e00-\\u9fa5]";
        String zifuEx = "[a-zA-Z0-9]";

        int hanziCount = 0;
        int zifuCount = 0;
        String c;
        for (int i = 0; i < str.length(); i++) {
            c = String.valueOf(str.charAt(i));
            if (c.matches(hanziEx)) {
                hanziCount++;
            } else if (c.matches(zifuEx)) {
                zifuCount++;
            }
        }
        int length = hanziCount * 2 + zifuCount;
        if (hanziCount > 6)
            return true;
        else if (zifuCount > 12)
            return true;
        else if (length > 12)
            return true;
        return false;
    }

    // 特殊字符过滤
    public static boolean check(String str) {

        String regEx = "[\\u4e00-\\u9fa5a-zA-Z0-9]{1,10000}";
        return str.matches(regEx);
    }

    DataCommuiation dataCommuiation;

    public DataCommuiation getDataCommuiation() {
        return dataCommuiation;
    }

    public void setDataCommuiation(DataCommuiation dataCommuiation) {
        this.dataCommuiation = dataCommuiation;
    }

    public interface DataCommuiation {
        public void onKyDown();
    }


    BitmapUtils bitmapUtils;
    String imageCachePath;


    /**
     * 初始化图片加载器
     */
    private void initBitmapUtils() {
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/image";
        bitmapUtils = new BitmapUtils(this, imageCachePath);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon_head_deffault);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(this));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);

    }

    // 出生年份选中值
    int age_value = 30;
    SimpleDateFormat format = new SimpleDateFormat("yyyy");

    private void showAgePicker(int defaultAge) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater().inflate(R.layout.pop_agepicker, null);

        final PickerViewTwo pv_gae = (PickerViewTwo) view.findViewById(R.id.pv_age);

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                age_value = Integer.parseInt(format.format(new Date()))
                        - Integer.parseInt(pv_gae.getSelectedValue()) + 1;
                tv_value_year.setText(String.valueOf(age_value));
                customer.setYear(pv_gae.getSelectedValue());
                dao.ModifyCustomer(customer);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        int currentyYear = Integer.parseInt(format.format(new Date()));
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 1960; i <= currentyYear; i++) {
            list.add(String.valueOf(i));
        }
        pv_gae.setData(list, String.valueOf(currentyYear - defaultAge + 1));
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    int height_value = 170;

    //身高选择
    private void showHeightPicker(String defaultHeight) {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = getLayoutInflater()
                .inflate(R.layout.pop_heightpicker, null);
        final PickerViewTwo pv_height = (PickerViewTwo) view
                .findViewById(R.id.pv_height);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                height_value = Integer.parseInt(pv_height.getSelectedValue());
                tv_value_height.setText(String.valueOf(height_value) + "cm");
                dialog.dismiss();
                // 身高为整数
                customer.setHeight(String.valueOf(height_value));
                dao.ModifyCustomer(customer);
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 150; i <= 200; i++) {
            list.add(String.valueOf(i));
        }
        defaultHeight = LocalDataUtils.readCustomer(this).getHeight();
        pv_height.setData(list, defaultHeight);

        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    // 体重选中值
    double weight_value = 65.0;

    //体重选择
    private void showWeightPicker(String defaultWeight) {
        String intValue = "0";
        String floatValue = ".0";
        defaultWeight = LocalDataUtils.readCustomer(this).getWeight();
        if (defaultWeight != null) {
            if (!TextUtils.isEmpty(defaultWeight)) {
                if (defaultWeight.contains(".")) {
                    String[] strs = defaultWeight.split("\\.");
                    intValue = strs[0];
                    floatValue = "." + strs[1];
                } else {
                    intValue = defaultWeight;
                    floatValue = ".0";
                }
            }
        }
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = this.getLayoutInflater()
                .inflate(R.layout.pop_weightpicker, null);

        final PickerViewTwo pv_weight_float = (PickerViewTwo) view
                .findViewById(R.id.pv_weight_float);
        final PickerViewTwo pv_weight_int = (PickerViewTwo) view
                .findViewById(R.id.pv_weight_int);

        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        Button btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                weight_value = Double.parseDouble(pv_weight_int
                        .getSelectedValue()
                        + pv_weight_float.getSelectedValue());
                tv_value_weight.setText(String.valueOf(weight_value) + "kg");
                customer.setWeight(String.valueOf(weight_value));
                dao.ModifyCustomer(customer);
                dialog.dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        ArrayList<String> listInt = new ArrayList<String>();
        ArrayList<String> listFloat = new ArrayList<String>();

        for (int i = 20; i < 150; i++) {
            listInt.add(String.valueOf(i));
        }

        for (int i = 0; i < 10; i++) {
            listFloat.add("." + i);
        }

        pv_weight_int.setData(listInt, intValue);
        pv_weight_float.setData(listFloat, floatValue);
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    //性别是否选择的标记
    boolean isMan = true;

    // 性别选择
    private void showGenderPicker() {
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = this.getLayoutInflater()
                .inflate(R.layout.pop_genderpicker, null);
        final RadioButton tv_man = (RadioButton) view.findViewById(R.id.tv_man);
        final RadioButton tv_girl = (RadioButton) view.findViewById(R.id.tv_girl);
        final ImageView iv_man = (ImageView) view.findViewById(R.id.iv_man);
        final ImageView iv_girl = (ImageView) view.findViewById(R.id.iv_girl);
        final RelativeLayout rl_gender_man = (RelativeLayout) view.findViewById(R.id.rl_gender_man);
        final RelativeLayout rl_gender_girl = (RelativeLayout) view.findViewById(R.id.rl_gender_girl);


        String gender = LocalDataUtils.readCustomer(this).getGender();
        int i = Integer.parseInt(gender);
        if (i == 1) {
            tv_man.setChecked(true);
            iv_girl.setVisibility(View.GONE);
            iv_man.setVisibility(View.VISIBLE);
        } else if (i == 2) {
            tv_girl.setChecked(true);
            iv_man.setVisibility(View.GONE);
            iv_girl.setVisibility(View.VISIBLE);
        }
        //点击选择男性
        rl_gender_man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iv_girl.setVisibility(View.GONE);
                iv_man.setVisibility(View.VISIBLE);
                tv_man.setChecked(true);
                tv_value_gender.setText("男");
                customer.setGender(String.valueOf(1));
                dao.ModifyCustomer(customer);
                dialog.dismiss();
            }
        });
        rl_gender_girl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                iv_man.setVisibility(View.GONE);
                iv_girl.setVisibility(View.VISIBLE);
                tv_girl.setChecked(true);
                tv_value_gender.setText("女");
                customer.setGender(String.valueOf(2));
                dao.ModifyCustomer(customer);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    private void initSoftKeyBoard(final EditText et_name) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                InputMethodManager inputManager = (InputMethodManager) et_name
                        .getContext().getSystemService(
                                Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(et_name, 0);
            }
        }, 100);

    }

    /**
     * @category 头像选择方式菜单
     */
    private void showImagePicker() {
        // 拍照/从相册选择
        final Dialog dialog = new Dialog(this, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = this.getLayoutInflater().inflate(R.layout.pop_image_picker,
                null);
        final RelativeLayout rl_galary = (RelativeLayout) view
                .findViewById(R.id.rl_galary);
        final RelativeLayout rl_camera = (RelativeLayout) view
                .findViewById(R.id.rl_camera);
        final RelativeLayout rl_cancel = (RelativeLayout) view
                .findViewById(R.id.rl_cancel);
        rl_cancel.setVisibility(View.VISIBLE);

        // 相ji添加点击事件
        rl_camera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    PhotoPicker.launchCamera(MyMessageActivity.this, FROM_CAPTURE,
                            captureFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showMessage(MyMessageActivity.this, "无法使用拍照功能");
                }
            }
        });
        // 相ce添加点击事件
        rl_galary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    PhotoPicker.launchGallery(MyMessageActivity.this, FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    Utils.showMessage(MyMessageActivity.this, "无法查看图片浏览器");
                }
            }
        });

        rl_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        Window window = dialog.getWindow();
        window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }


    private void display(ImageView container, String path) {
        if (TextUtils.isEmpty(path))
            return;
        String url = HttpConnectUtils.image_ip + path;
        bitmapUtils.display(container, url, new BitmapLoadCallBack<View>() {

            @Override
            public void onLoadCompleted(View container, String uri,
                                        Bitmap bitmap, BitmapDisplayConfig config,
                                        BitmapLoadFrom from) {
                // TODO Auto-generated method stub
                File headfile = bitmapUtils.getBitmapFileFromDiskCache(uri);
                System.out.println("headfile==" + headfile);
                if (headfile == null) {

                } else {
                    int degree = Utils.readPictureDegree(bitmapUtils.getBitmapFileFromDiskCache(uri).getPath());
                    bitmap = Utils.rotaingImageView(degree, bitmap);
                    iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(bitmap));
                    System.out.println("bitmap==mymessage" + bitmap);
//					top_pic.setBackgroundDrawable(new BitmapDrawable(bitmap));
//					bitmapMohu();
                }

            }

            @Override
            public void onLoadFailed(View container, String uri,
                                     Drawable drawable) {
                // TODO Auto-generated method stub
//				Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
//						R.drawable.icon_head_deffault);
//				iv_camera.setImageBitmap(ImageHelper.toRoundBitmap(bitmap));
            }
        });

    }
}
