package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.Constants;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.HostSwitchHandle;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.R;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.base.BaseActivity;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity.ParameterUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.firebase.AnalyticsWebInterface;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice.HostSwitchInterfice;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import goutil.Goutil;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends BaseActivity implements HostSwitchInterfice {
    private String TAG = MainActivity.class.getSimpleName();

    private WebView mWeb;
    private ParameterUtil mParameterUtil;
    private HostSwitchHandle mHostSwitchHandle;
    private Boolean disabled;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initView() {
        mWeb = findViewById(R.id.web);
        mHostSwitchHandle = new HostSwitchHandle(mWeb, this, this);
        mHostSwitchHandle.switchHandle();
        initH5();
        initWebView();
    }

    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initH5() {
        mParameterUtil = new ParameterUtil(mContext, mSpUtil);
        //初始化数据
        String requestData = Goutil.sendRequest(
                Constants.ENV_URL,
                getString(R.string.app_name),
                Constants.API_CODE,
                mParameterUtil.getInitJson(),
                "{}");
//        try {
//            mSp.putString(Constants.DEVICE_ID,data);
//            mSp.putString(Constants.DEVICE_TOKEN, jsonObject.data.deviceToken);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    private void initWebView() {
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);                                      //允许js交互
        settings.setUseWideViewPort(true);                                           //设置可以加载更多格式页面
        settings.setDomStorageEnabled(true);                                         //使用广泛视窗
        settings.setJavaScriptCanOpenWindowsAutomatically(true);                     //启用Dom storage api
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);     //自动打开窗口
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);                             //排版适应屏幕
        settings.setDefaultTextEncodingName("utf-8");                                //设置缓存类型
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWeb.addJavascriptInterface(new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);
        }
    }

    @Override
    public void initData() {

    }

    /**
     * host回调
     *
     * @param data
     * @param callback
     */
    @Override
    public void appInfo(String data, String callback) {

    }

    @Override
    public void updateUid(String data, String callback) {

    }

    @Override
    public void getAL(String data, String callback) {

    }

    @Override
    public void uploadDeviceInfo(String data, String callback) {

    }

    @Override
    public void getGPS(String data, String callback) {

    }

    @Override
    public void huoti(String data, String callback) {
        //活体
//        Map<String, Object> isSuccess = new HashMap<>();
//        isSuccess.put("isSuccess", isAllGranted);
//        if (isAllGranted) {
//            startLivenessActivity();
//        } else {
//            mHostSwitchHandle.uploadData(mCallback, new Gson().toJson(isSuccess));
//        }
    }

    @Override
    public void jumpUrlOuter(String data, String callback) {
        try {
            String string = new JSONObject(data).getString("url");
            //打开系统浏览器跳转
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(string));
            startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void jumpUrlInner(String data, String callback) {
        mHostSwitchHandle.loadUrl(data);
    }

    @Override
    public void disabledGoBack(String data, String callback) {
        try {
            disabled = (Boolean) new JSONObject(data).get("disabled");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeNewWebview(String data, String callback) {
        mWeb.reload();
        finish();
    }

    @Override
    public void reload(String data, String callback) {
        mWeb.reload();
    }

    @SuppressLint("CheckResult")
    @Override
    public void applyPermission(String data, String callback) {
        String permissions = null;
        try {
            permissions = (String) new JSONObject(data).get("permissions");
            String[] mSplit = permissions.split(",");
            String[] strings = new String[mSplit.length];
            for (int i = 0; i < mSplit.length; i++) {
                switch (mSplit[i]) {
                    case "gps":
                        strings[i] = ACCESS_FINE_LOCATION;
                        break;
                    case "readPhoneState":
                        strings[i] = READ_PHONE_STATE;
                        break;
                    case "camera":
                        strings[i] = CAMERA;
                        break;
                    case "contacts":
                        strings[i] = READ_CONTACTS;
                        break;
                    case "write":
                        strings[i] = WRITE_EXTERNAL_STORAGE;
                        break;
                }
            }
            final int[] i = {0};
            new RxPermissions(this)
                    .requestEach(strings).subscribe(permission -> {
                Map<String, String> gpsMap = new HashMap<>();
                if (permission.granted) {
                    gpsMap.put(mSplit[i[0]], "refused");
                } else if (permission.shouldShowRequestPermissionRationale) {
                    gpsMap.put(mSplit[i[0]], "forbid");
                } else {
                    gpsMap.put(mSplit[i[0]], "granted");
                }
                i[0]++;
                if (i[0] == mSplit.length) {
                    mHostSwitchHandle.uploadData(callback, new Gson().toJson(gpsMap));
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void applyCamera(String data, String callback) {

    }

    @Override
    public void exitApp(String data, String callback) {

    }

    @Override
    public void jumpSetting(String data, String callback) {

    }

    @Override
    public void goHome(String data, String callback) {

    }

    @Override
    public void chooseLinkman(String data, String callback) {

    }

    @Override
    public void uploadAB(String data, String callback) {

    }
}