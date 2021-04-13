package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice;

import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface OkResultCallback {
    void onSuccess(Call call, Response response) ;

    void onFailed(Call call, IOException e);
}
