package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah;

import android.app.Application;
import android.content.Context;

import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.SharedPreferencesUtil;

import go.Seq;

public class MyApplication extends Application {
    public static Context context;
    public static SharedPreferencesUtil mSpUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mSpUtil = SharedPreferencesUtil.getInstance(this);
        Seq.setContext(getApplicationContext());
    }
}
