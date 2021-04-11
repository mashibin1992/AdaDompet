package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah;

import android.app.Application;

import go.Seq;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Seq.setContext(getApplicationContext());
    }
}
