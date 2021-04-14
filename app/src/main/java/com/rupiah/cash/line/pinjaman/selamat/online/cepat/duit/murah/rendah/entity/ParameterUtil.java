package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.entity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.Constants;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.R;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.AndroidInfoUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.AppInfoUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.GetMacAddress;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.GetMemoryUtil;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.CreateUUID;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.NetWorkInfo;
import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util.SharedPreferencesUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class ParameterUtil {

    private Runtime runtime;
    private Context mContext;
    private SharedPreferencesUtil mSpUtil;
    private DisplayMetrics mDisplayMetrics;

    public ParameterUtil(Context context, SharedPreferencesUtil mSpUtil) {
        this.mContext = context;
        this.mSpUtil = mSpUtil;
        this.runtime = Runtime.getRuntime();
        this.mDisplayMetrics = mContext.getResources().getDisplayMetrics();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getInitJson() {
        JSONObject deviceInfo2 = new JSONObject();
        JSONObject deviceInfo = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            //内层
            deviceInfo2.put("androidId", CreateUUID.getUuid(mContext));
            deviceInfo2.put("availMemory", GetMemoryUtil.bytes2kb(runtime.freeMemory()));
            deviceInfo2.put("codeName", Build.VERSION.CODENAME);
            deviceInfo2.put("cpu", Build.CPU_ABI);
            deviceInfo2.put("cpuInfo", "0");//暂时传0
            deviceInfo2.put("deviceSoftwareVersion", AndroidInfoUtil.getDeviceSoftwareVersion(mContext));
            deviceInfo2.put("display", mDisplayMetrics.widthPixels + "|" + mDisplayMetrics.heightPixels);
            deviceInfo2.put("gsmCellLocation", NetWorkInfo.getGsmCellLocation(mContext));
            deviceInfo2.put("hardware", Build.HARDWARE);
            deviceInfo2.put("imei", CreateUUID.getUuid(mContext));
            deviceInfo2.put("language", Locale.getDefault().getLanguage());
            deviceInfo2.put("macAddress", GetMacAddress.tryGetWifiMac(mContext));
            deviceInfo2.put("manufacturer", Build.MANUFACTURER);
            deviceInfo2.put("model", Build.MODEL);
            deviceInfo2.put("networkOperator", AndroidInfoUtil.getNetworkOperator(mContext));
            deviceInfo2.put("networkOperatorName", AndroidInfoUtil.getNetworkOperatorName(mContext));
            deviceInfo2.put("networkType", AndroidInfoUtil.getNetworkType(mContext));
            deviceInfo2.put("product", Build.PRODUCT);
            deviceInfo2.put("radioVersion", Build.getRadioVersion());
            deviceInfo2.put("release", Build.VERSION.RELEASE);
            deviceInfo2.put("sdkVersion", Build.VERSION.SDK_INT + "");
            deviceInfo2.put("serialNumber", Build.SERIAL);
            deviceInfo2.put("totalMemory", GetMemoryUtil.bytes2kb(runtime.totalMemory()));
            deviceInfo2.put("uuid", CreateUUID.getUuid(mContext));
            //中间
            deviceInfo.put("appVersion", AppInfoUtil.getVersionCode(mContext) + "");
            deviceInfo.put("bundleid", mContext.getPackageName());
            deviceInfo.put("deviceId", mSpUtil.getString(Constants.DEVICE_ID));
            deviceInfo.put("deviceManufacturer", Build.MANUFACTURER);
            deviceInfo.put("devicePlatform", "0");
            deviceInfo.put("deviceType", Build.MODEL);
            deviceInfo.put("deviceVersion", Build.VERSION.SDK_INT + "");
            if (mSpUtil.getString(Constants.DEVICE_ID).equals(""))
                deviceInfo.put("firstOpen", "1");
            else
                deviceInfo.put("firstOpen", "0");
            deviceInfo.put("latitude", null);
            deviceInfo.put("longitude", null);
            deviceInfo.put("networkAc", AndroidInfoUtil.getNetworkType(mContext));
            deviceInfo.put("screenResolution", mDisplayMetrics.widthPixels + "|" + mDisplayMetrics.heightPixels);
            deviceInfo.put("userAgent", System.getProperty("http.agent"));
            deviceInfo.put("deviceInfo", deviceInfo2);
            //外层
            jsonObject.put("packageName", mContext.getPackageName());            //包名
            jsonObject.put("channelKey", "16");                           //渠道号
            jsonObject.put("version", AppInfoUtil.getVersionCode(mContext) + "");     //版本号
            jsonObject.put("platform", Constants.PLATFORM);                     //平台
            jsonObject.put("versionKey", AppInfoUtil.getVersionName(mContext));  //版本名
            jsonObject.put("country", Constants.COUNTRY);                       //国家
            jsonObject.put("uid", mSpUtil.getString(Constants.UID));           //未登录用户传 ``,h5登录成功后会通过scheme回调给客户端，保存并一直使用，退出后删除本地存储
            jsonObject.put("deviceId", mSpUtil.getString(Constants.DEVICE_ID));//设备ID	第一次传空，服务器返回后一直保存在本地，每次请求都使用
            jsonObject.put("deviceInfo", deviceInfo);
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint({"MissingPermission", "HardwareIds"})
    public String getInitAppInfo() {
        JSONObject deviceInfo2 = new JSONObject();
        JSONObject deviceInfo = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        try {
            //内层
            deviceInfo2.put("androidId", CreateUUID.getUuid(mContext));
            deviceInfo2.put("availMemory", GetMemoryUtil.bytes2kb(runtime.freeMemory()));
            deviceInfo2.put("codeName", Build.VERSION.CODENAME);
            deviceInfo2.put("cpu", Build.CPU_ABI);
            deviceInfo2.put("cpuInfo", "0");//暂时传0
            deviceInfo2.put("deviceSoftwareVersion", AndroidInfoUtil.getDeviceSoftwareVersion(mContext));
            deviceInfo2.put("display", mDisplayMetrics.widthPixels + "|" + mDisplayMetrics.heightPixels);
            deviceInfo2.put("gsmCellLocation", NetWorkInfo.getGsmCellLocation(mContext));
            deviceInfo2.put("hardware", Build.HARDWARE);
            deviceInfo2.put("imei", CreateUUID.getUuid(mContext));
            deviceInfo2.put("language", Locale.getDefault().getLanguage());
            deviceInfo2.put("macAddress", GetMacAddress.tryGetWifiMac(mContext));
            deviceInfo2.put("manufacturer", Build.MANUFACTURER);
            deviceInfo2.put("model", Build.MODEL);
            deviceInfo2.put("networkOperator", AndroidInfoUtil.getNetworkOperator(mContext));
            deviceInfo2.put("networkOperatorName", AndroidInfoUtil.getNetworkOperatorName(mContext));
            deviceInfo2.put("networkType", AndroidInfoUtil.getNetworkType(mContext));
            deviceInfo2.put("product", Build.PRODUCT);
            deviceInfo2.put("radioVersion", Build.getRadioVersion());
            deviceInfo2.put("release", Build.VERSION.RELEASE);
            deviceInfo2.put("sdkVersion", Build.VERSION.SDK_INT + "");
            deviceInfo2.put("serialNumber", Build.SERIAL);
            deviceInfo2.put("totalMemory", GetMemoryUtil.bytes2kb(runtime.totalMemory()));
            deviceInfo2.put("uuid", CreateUUID.getUuid(mContext));
            //中间
            deviceInfo.put("appVersion", AppInfoUtil.getVersionCode(mContext) + "");
            deviceInfo.put("bundleid", mContext.getPackageName());
            deviceInfo.put("deviceId", mSpUtil.getString(Constants.DEVICE_ID));
            deviceInfo.put("deviceManufacturer", Build.MANUFACTURER);
            deviceInfo.put("devicePlatform", "0");
            deviceInfo.put("deviceType", Build.MODEL);
            deviceInfo.put("deviceVersion", Build.VERSION.SDK_INT + "");
            if (mSpUtil.getString(Constants.DEVICE_ID).equals(""))
                deviceInfo.put("firstOpen", "1");
            else
                deviceInfo.put("firstOpen", "0");
            deviceInfo.put("latitude", null);
            deviceInfo.put("longitude", null);
            deviceInfo.put("networkAc", AndroidInfoUtil.getNetworkType(mContext));
            deviceInfo.put("screenResolution", mDisplayMetrics.widthPixels + "|" + mDisplayMetrics.heightPixels);
            deviceInfo.put("userAgent", System.getProperty("http.agent"));
            deviceInfo.put("deviceInfo", deviceInfo2.toString());
            //外层
            jsonObject.put("packageName", mContext.getPackageName());            //包名
            jsonObject.put("bundle_id", mContext.getPackageName());            //包名
            jsonObject.put("channelKey", "16");                           //固定值
            jsonObject.put("channel_code", "16");                           //固定值
            jsonObject.put("channel_name", "google-play");                           //固定值
            jsonObject.put("version", AppInfoUtil.getVersionCode(mContext) + "");     //版本号
            jsonObject.put("platform", Constants.PLATFORM);                     //平台
//            jsonObject.put("versionKey", AppInfoUtil.getVersionName(mContext));  //版本名
            jsonObject.put("country", Constants.COUNTRY);                       //国家
//            jsonObject.put("uid", mSpUtil.getString(Constants.UID));           //未登录用户传 ``,h5登录成功后会通过scheme回调给客户端，保存并一直使用，退出后删除本地存储
            jsonObject.put("deviceId", mSpUtil.getString(Constants.DEVICE_ID));//设备ID	第一次传空，服务器返回后一直保存在本地，每次请求都使用
            jsonObject.put("deviceToken", mSpUtil.getString(Constants.DEVICE_TOKEN));//设备ID	第一次传空，服务器返回后一直保存在本地，每次请求都使用
            jsonObject.put("packageType", "gp");//固定值
            jsonObject.put("versionName", AppInfoUtil.getVersionName(mContext));
            jsonObject.put("local", "id-ID");//固定值
            jsonObject.put("terminal_name", mContext.getString(R.string.app_name));
            jsonObject.put("deviceInfo", deviceInfo.toString());
            return jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getIsSuccessJson(boolean isSuccess) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("isSuccess", isSuccess);
        return new Gson().toJson(map);
    }

}
