package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.base;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.Constants;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.R;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity.CurrencyBean;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.AppInfoUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.SharedPreferencesUtil;


/**
 * Activity基类
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    protected SharedPreferencesUtil mSpUtil = null;
    private Dialog mDialog;
    protected String[] cameraPermission = {
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    protected String[] gps = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    protected final String[] requestPermissions = new String[]{
            Manifest.permission.READ_PHONE_STATE,
    };
    protected final String[] lundu = new String[]{
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    //读取通讯录
    protected final String[] readContacts = new String[]{
            Manifest.permission.READ_CONTACTS,
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 全局设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        //初始化sp对象
        mContext = this;
        mSpUtil = SharedPreferencesUtil.getInstance(mContext);
        // 沉浸式状态栏
        if (isImmersionBarEnabled()) {
            initImmersionBar();
        } else {
            // 状态栏深色字体
            //ImmersionBar.with(this).statusBarColor(R.color.white).statusBarDarkFont(true).init();
        }

        initView();
        initData();
    }

    protected String getCurrentPrams() {
        //初始化通用参数
        CurrencyBean currencyBean = new CurrencyBean();
        currencyBean.channelKey = Constants.CHANNEL_KEY;
        currencyBean.packageName = getPackageName();
        currencyBean.version = AppInfoUtil.getVersionCode(mContext) + "";
        currencyBean.versionKey = AppInfoUtil.getVersionName(mContext);
        return new Gson().toJson(currencyBean);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (isImmersionBarEnabled()) {
        }
    }

    protected void initImmersionBar() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.white)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    protected boolean checkPermissions(String[] permissions) {
        int count = 0;

        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(this, permissions[i])
                    == PackageManager.PERMISSION_GRANTED) {
                count++;
            }
        }

        return count == permissions.length;
    }

    /**
     * 跳转app设置页面
     */
    protected void gouAppSettings(Context context) {
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(mIntent);
    }


    /**
     * 是否可以使用沉浸式
     * Is immersion bar enabled boolean.
     *
     * @return the boolean
     */
    protected boolean isImmersionBarEnabled() {
        return true;
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();
}
