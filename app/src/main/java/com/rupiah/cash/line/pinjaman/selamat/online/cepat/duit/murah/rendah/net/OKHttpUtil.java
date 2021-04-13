package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.net;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.Constants;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.MyApplication;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice.OkResultCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 利用okhttp进行get和post的访问
 *
 * @author cp
 */
public class OKHttpUtil {

    private static final String TAG = "OKHttpUtil";


    /**
     * 上传文件
     *
     * @param data
     * @param params
     * @param callBack
     * @param activity
     * @throws JSONException
     */
    public static void uploadText(String data, String params, OkResultCallback callBack, Activity activity) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            String url = (String) jsonObject.get("url");
            String key = (String) jsonObject.get("key");
            RequestBody requestBody = null;
            requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"), params);
            if (!url.startsWith("https://")) {
                url = URLDecoder.decode(url);
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(key, MyApplication.mSpUtil.getString(Constants.UID))
                    .put(requestBody)
                    .build();
            new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    activity.runOnUiThread(() -> {
                        callBack.onFailed(call, e);
                    });

                }

                @Override
                public void onResponse(Call call, Response response) {
                    activity.runOnUiThread(() -> {
                        callBack.onSuccess(call, response);
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    /**
     * 上传图片
     *
     * @param data
     * @param params
     * @param callBack
     * @param activity
     * @throws JSONException
     */
    public static void uploadByte(String data, byte[] params, OkResultCallback callBack, Activity activity) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            String url = (String) jsonObject.get("url");
            String key = (String) jsonObject.get("key");
            RequestBody requestBody = null;
            requestBody = RequestBody.create(MediaType.parse("image/png; charset=utf-8"), params);
            if (!url.startsWith("https://")) {
                url = URLDecoder.decode(url);
            }
            Request request = new Request.Builder()
                    .url(url)
                    .addHeader(key, MyApplication.mSpUtil.getString(Constants.UID))
                    .put(requestBody)
                    .build();
            new OkHttpClient.Builder().build().newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    activity.runOnUiThread(() -> {
                        callBack.onFailed(call, e);
                    });

                }

                @Override
                public void onResponse(Call call, Response response) {
                    activity.runOnUiThread(() -> {
                        callBack.onSuccess(call, response);
                    });
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}