package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class GetSystemAppInfoList {

    public static String getALInfo(Context context) {
        PackageManager pckMan = context.getPackageManager();
        List<PackageInfo> packageInf = pckMan.getInstalledPackages(0);
        JSONArray jsonArrayAL = new JSONArray();
        for (PackageInfo packageInfo : packageInf) {
            JSONObject jsonObjectAL = new JSONObject();
            try {
                jsonObjectAL.put("app_name", packageInfo.applicationInfo.loadLabel(pckMan).toString().contains("\"") ? "" : packageInfo.applicationInfo.loadLabel(pckMan).toString());
                jsonObjectAL.put("bundle_id", packageInfo.packageName);
                jsonObjectAL.put("installTime", packageInfo.firstInstallTime);
                jsonObjectAL.put("updateTime", packageInfo.lastUpdateTime);
                jsonObjectAL.put("versionName", packageInfo.versionName);
                jsonObjectAL.put("versionCode", packageInfo.versionCode);
                // 获取系统程序
                jsonObjectAL.put("systemAppFlag", (packageInfo.applicationInfo.flags & packageInfo.applicationInfo.FLAG_SYSTEM) != 0 ? true : false);
                jsonObjectAL.put("appTag", packageInfo.applicationInfo.flags);
                jsonArrayAL.put(jsonObjectAL);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonArrayAL.toString();
    }
}
