package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.base.BaseActivity;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.ui.MainActivity;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.AppInfoUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.disposables.Disposable;

public class WelcomeActivity extends BaseActivity {

    private ImageView mLogo;
    private TextView mApp_name;
    private TextView mVersion_code;
    private String TAG = WelcomeActivity.class.getSimpleName();

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @SuppressLint("CheckResult")
    @Override
    public void initView() {
        mLogo = findViewById(R.id.logo);
        mApp_name = findViewById(R.id.app_name);
        mVersion_code = findViewById(R.id.version_code);
        mApp_name.setText(R.string.app_name);
        mVersion_code.setText(AppInfoUtil.getVersionName(mContext));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            new RxPermissions(this)
                    .requestEach(Manifest.permission.READ_PHONE_STATE
                    ).subscribe(permission -> {
                startMainActivity();
            });
        }
    }

    private void startMainActivity() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }

    @Override
    public void initData() {

    }
}