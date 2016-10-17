package com.hike.digitalgymnastic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.hike.digitalgymnastic.fragment.PersonalPageFragment;
import com.hike.digitalgymnastic.utils.Constants;
import com.hike.digitalgymnastic.utils.ReqeustCode;
import com.hiko.enterprisedigital.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;


@ContentView(R.layout.activity_diary_page)
public class DiaryPageActivity extends BaseFragmentActivity implements ReqeustCode {
    private FragmentManager fragmentManager;
    private FragmentTransaction mFragmentTransaction;

    long customerId;
    String customerName;
    String customerAvator;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ViewUtils.inject(this);
        initData(bundle);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(Constants.customerId, customerId);
        outState.putString(Constants.customerName, customerName);
        outState.putString(Constants.customerAvator, customerAvator);
    }

    private void initData(Bundle bundle) {
        if (bundle != null) {
            customerId = bundle.getLong(Constants.customerId);
            customerName = bundle.getString(Constants.customerName);
            customerAvator = bundle.getString(Constants.customerAvator);
        } else {
            customerId = getIntent().getLongExtra(Constants.customerId, 0);
            customerName = getIntent().getStringExtra(Constants.customerName);
            customerAvator = getIntent().getStringExtra(Constants.customerAvator);
        }
        fragmentManager = getSupportFragmentManager();
        initPage();
    }

    PersonalPageFragment ppf;

    private void initPage() {
        mFragmentTransaction = fragmentManager.beginTransaction();
        ppf = new PersonalPageFragment(customerId, customerName, customerAvator);
        mFragmentTransaction.replace(R.id.content, ppf);
        mFragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == FROM_CAPTURE||requestCode==FROM_GALLERY) {
                if (ppf != null) {
                    ppf.onActivityResult(requestCode, resultCode, data);
                }
            }
        }

    }
}
