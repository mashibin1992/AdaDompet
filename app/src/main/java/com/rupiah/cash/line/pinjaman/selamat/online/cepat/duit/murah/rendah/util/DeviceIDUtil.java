package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;

import java.util.UUID;

public class DeviceIDUtil {

    public static String getDeviceId(Context context) {
        String deviceId = "";
        if (deviceId != null && !"".equals(deviceId)) {
            return deviceId;
        }
        if (deviceId == null || "".equals(deviceId)) {
            try {
                deviceId = getLocalMac(context).replace(":", "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (deviceId == null || "".equals(deviceId)) {
            try {
                deviceId = getAndroidId(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (deviceId == null || "".equals(deviceId)) {

            if (deviceId == null || "".equals(deviceId)) {
                UUID uuid = UUID.randomUUID();
                deviceId = uuid.toString().replace("-", "");
            }
        }
        return deviceId;
    }

    // Mac地址
    private static String getLocalMac(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

    // Android Id
    private static String getAndroidId(Context context) {
        String androidId = Settings.Secure.getString(
                context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return androidId;
    }
}
