package com.hike.digitalgymnastic.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hike.digitalgymnastic.DiaryPublishActivity;
import com.hike.digitalgymnastic.ImageDetailPage;
import com.hike.digitalgymnastic.MyMessageActivity;
import com.hike.digitalgymnastic.VenueDiaryDetailActivity;
import com.hike.digitalgymnastic.adapter.PersonalDiaryAdapter;
import com.hike.digitalgymnastic.entitiy.Customer;
import com.hike.digitalgymnastic.entitiy.VenueDiary;
import com.hike.digitalgymnastic.http.HttpConnectUtils;
import com.hike.digitalgymnastic.request.BaseDao;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.FileUtil;
import com.hike.digitalgymnastic.utils.ImageUtil;
import com.hike.digitalgymnastic.utils.LocalDataUtils;
import com.hike.digitalgymnastic.utils.PhotoPicker;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hike.digitalgymnastic.utils.Utils;
import com.hike.digitalgymnastic.view.CircleImageView;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.bitmap.BitmapCommonUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

//import com.hike.digitalgymnastic.MyMessageActivity;

/**
 * @author huwei
 *         个人主页
 */
@SuppressLint("ValidFragment")
public class PersonalPageFragment extends BaseFragment implements ReqeustCode {

    @ViewInject(R.id.rl_hide)
    RelativeLayout rl_hide;
    @ViewInject(R.id.btn_left)
    ImageView btn_left;
    @ViewInject(R.id.ll_btn_left)
    LinearLayout ll_btn_left;
    @ViewInject(R.id.ll_btn_right)
    LinearLayout ll_btn_right;
    @ViewInject(R.id.btn_editor)
    Button btn_editor;
    @ViewInject(R.id.lv_personalDaily)
    ListView lv_personalDaily;
    @ViewInject(R.id.tv_name)
    TextView tv_name;
    @ViewInject(R.id.iv_touxiang)
    CircleImageView iv_touxiang;
    @ViewInject(R.id.rl_camera)
    RelativeLayout rl_camera;


    @ViewInject(R.id.ib_backtotop)
    ImageButton ib_backtotop;
    BaseDao dao;
    String customerName;
    long customerId;
    String customerAvator;

    public PersonalPageFragment(long customerId, String customerName, String customerAvator) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAvator = customerAvator;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_page, null);
        ViewUtils.inject(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        init();
    }

    private void init() {
        initBitmapUtils();
        dao = new BaseDao(this, activity);
        if (customer != null && customerId != Long.parseLong(customer.getId())) {
            // 如果不是自己,隐藏资料图标
            btn_editor.setVisibility(View.GONE);
            rl_camera.setVisibility(View.GONE);
            bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + customerAvator);
            tv_name.setText(customerName);
            pictureHeadUrl = HttpConnectUtils.image_ip + customerAvator;
            pictureHeadBigUrl = HttpConnectUtils.image_ip + customerAvator;
        } else {
            rl_camera.setVisibility(View.VISIBLE);
            btn_editor.setVisibility(View.VISIBLE);
            initCustomer();
        }
        lv_personalDaily.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PersonalDiaryAdapter adapter = (PersonalDiaryAdapter) parent.getAdapter();
                venueDiary = (VenueDiary) adapter
                        .getItem(position);
                long diaryId = venueDiary.getDiaryId();
                Intent intent = new Intent(getActivity(), VenueDiaryDetailActivity.class);
                intent.putExtra(Constants.diaryId, diaryId);
                intent.putExtra(Constants.customerId, Long.parseLong(customer.getId()));
                startActivityForResult(intent, Constants.diaryDetail);
            }
        });
        getData(startIndex, pageSize);
    }


    private void initCustomer() {
        Customer customer = LocalDataUtils.readCustomer(activity);
        if (TextUtils.isEmpty(customer.getName()))
            dao.GetCustomer();
        else
            tv_name.setText(customer.getName());
        if (!TextUtils.isEmpty(customer.getAvatar())) {
            bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + customer.getAvatar());
            pictureHeadUrl = HttpConnectUtils.image_ip + customer.getAvatar();
            pictureHeadBigUrl = HttpConnectUtils.image_ip + customer.getAvatar();
        } else
            dao.GetCustomer();
    }

    PersonalDiaryAdapter adapter;
    int startIndex = 0;
    int pageSize = 10;

    private void getData(int startIndex, int pageSize) {
        dao.queryDiaryHome(3, customerId, startIndex, pageSize);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (application.isCustomerModify) {
            application.isCustomerModify = false;
            initCustomer();
        }
        if (application.isNeedUpdated) {
            application.isNeedUpdated = false;
            reload = true;
            getData(startIndex, pageSize);
        }
    }

    List<VenueDiary> totallist = new ArrayList<VenueDiary>();

    @Override
    public void onRequestSuccess(int requestCode) {
        super.onRequestSuccess(requestCode);
        if (requestCode == 8) {
            Customer cus = dao.getCustomer();
            if (TextUtils.isEmpty(cus.getAvatar()))
                iv_touxiang.setImageResource(R.mipmap.boy_head);
            else
                bitmapUtils.display(iv_touxiang, HttpConnectUtils.image_ip + cus.getAvatar());
            if (TextUtils.isEmpty(cus.getName()))
                tv_name.setText("客户");
            else
                tv_name.setText(cus.getName());
        } else if (requestCode == 90) {
            //请求个人主页的日记成功
            List<VenueDiary> diaryList = dao.getQueryVenueDiary().getDiaryList();
            if (diaryList != null && diaryList.size() > 0)
                buildView(diaryList);
        }
    }

    boolean reload = false;

    private void buildView(List<VenueDiary> diaryList) {
        if (reload) {
            reload = false;
            totallist.clear();
            startIndex = 0;
        }
        if (diaryList != null) {
            totallist.addAll(diaryList);
            if (adapter == null) {
                adapter = new PersonalDiaryAdapter(totallist,
                        activity, bitmapUtils, dao);
                lv_personalDaily.setAdapter(adapter);
                Utils.setListViewHeightBasedOnChildren(lv_personalDaily);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }


    /**
     * @category 进入图片大图页面
     */
    private void jump2ImagePage(String url) {
        Intent intent = new Intent(activity, ImageDetailPage.class);
        intent.putExtra(Constants.image_url, url);
        intent.putExtra(Constants.defaultID, R.mipmap.boy_head);
        startActivity(intent);
        activity.overridePendingTransition(R.anim.abc_fade_in,
                R.anim.abc_fade_out);
    }

    String pictureHeadUrl, pictureHeadBigUrl;

    private void showBigIcon() {
        // 查看大图
        if (!TextUtils.isEmpty(pictureHeadUrl)) {
            jump2ImagePage(pictureHeadBigUrl);
        } else if (bitmapUtils != null && !TextUtils.isEmpty(pictureHeadUrl)) { //
            File file = bitmapUtils.getBitmapFileFromDiskCache(pictureHeadUrl);

            if (file != null) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), "image/*");
                startActivity(intent);
            } else {
                jump2ImagePage(pictureHeadUrl);
            }
        }

    }

    @OnClick(value = {R.id.iv_touxiang, R.id.btn_editor, R.id.ib_backtotop, R.id.btn_left, R.id.ll_btn_right, R.id.ll_btn_left, R.id.btn_right, R.id.rl_camera})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_touxiang:
                showBigIcon();
                break;
            // 跳转个人资料页面
            case R.id.btn_editor:
            case R.id.ll_btn_right:
                Intent intent = new Intent(activity, MyMessageActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_btn_left:
            case R.id.btn_left:
                activity.finish();
                break;
            case R.id.rl_camera:
                showImagePicker();
//                Intent it = new Intent(activity, PhotoAlbumActivity.class);
//                startActivityForResult(it, Constants.requestCode_pickpicture);
//                activity.overridePendingTransition(R.anim.push_up, R.anim.push_down);
                break;
            case R.id.ib_backtotop:
                lv_personalDaily.setSelection(0);
                break;
            default:
                break;
        }
    }

    /**
     * @category 头像选择方式菜单
     */

    File captureFile;

    private void showImagePicker() {
        // 拍照/从相册选择
        final Dialog dialog = new Dialog(activity, R.style.MyDialog);
        dialog.setCanceledOnTouchOutside(false);
        View view = activity.getLayoutInflater().inflate(R.layout.pop_image_picker,
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
                    captureFile = FileUtil.getCaptureFile(activity);
                    PhotoPicker.launchCamera(activity, FROM_CAPTURE, captureFile);
                } catch (Exception e) {
                    e.printStackTrace();
                    Utils.showMessage(activity, "无法使用拍照功能");
                }
            }
        });
        // 相ce添加点击事件
        rl_galary.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
                try {
                    PhotoPicker.launchGallery(activity, FROM_GALLERY);
                } catch (ActivityNotFoundException e) {
                    Utils.showMessage(activity, "无法查看图片浏览器");
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
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ColorDrawable drawable = new ColorDrawable(getResources().getColor(
                R.color.black));
        window.setBackgroundDrawable(drawable);
        window.setGravity(Gravity.BOTTOM);
        dialog.show();
    }

    public VenueDiary venueDiary;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.diaryDetail) {
                int count_Customer = data.getIntExtra("count_Customer", 0);
                int count_Comment = data.getIntExtra("count_Comment", 0);
                venueDiary.setCommentCount(count_Comment);
                venueDiary.setPraiseCount(count_Customer);
                venueDiary.setPraised(data.getBooleanExtra("isprised",
                        false));
                adapter.notifyDataSetChanged();
            }

            if (requestCode == FROM_CAPTURE) {
                onCaptureComplete(captureFile);
            } else if (requestCode == FROM_GALLERY) {
                if (data != null) {
                    String path = PhotoPicker.getPhotoPathByLocalUri(activity, data);
                    onGalleryComplete(path);
                }
            }
        }
    }

    protected void onGalleryComplete(String filepath) {
        String path = ImageUtil.getTargetImage(activity, filepath, null, 1024, false, 0);
        startIntent(path, DiaryPublishActivity.class);
    }

    protected void onCaptureComplete(File captureFile) {
        String path = ImageUtil.getTargetImage(activity, captureFile.getPath(), null, 1024, false, 0);
        startIntent(path, DiaryPublishActivity.class);
    }

    public void startIntent(String path, Class class1) {
        Intent intent = new Intent(activity, class1);
        intent.putExtra("imagePath", path);
        startActivity(intent);
    }

    BitmapUtils bitmapUtils;
    String imageCachePath;

    private void initBitmapUtils() {
        imageCachePath = Environment.getExternalStorageDirectory() + "/" + getString(R.string.app_name) + "/image";
        bitmapUtils = new BitmapUtils(activity, imageCachePath);
//		bitmapUtils.configDefaultLoadFailedImage(R.drawable.icon_head_deffault);
        bitmapUtils.configDefaultShowOriginal(false);// 显示原始图片,不压缩,
        // 尽量不要使用,图片太大时容易OOM。
        bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
        bitmapUtils.configDefaultBitmapMaxSize(BitmapCommonUtils
                .getScreenSize(activity));
        bitmapUtils.configDefaultLoadingImage(R.mipmap.boy_head);
        // bitmapUtils.configDefaultCacheExpiry(2*60*1000);
        bitmapUtils.configThreadPoolSize(5);
    }
}
