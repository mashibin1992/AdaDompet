package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

public class AndroidInfoUtil {

    @SuppressLint("MissingPermission")
    public static String getDeviceSoftwareVersion(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getDeviceSoftwareVersion();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getDeviceId(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getDeviceId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint({"MissingPermission", "HardwareIds"})
    public static String getSubscriberId(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getSubscriberId();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint({"MissingPermission"})
    public static String getNetworkOperator(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getNetworkOperator();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint({"MissingPermission"})
    public static String getNetworkOperatorName(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tel.getNetworkOperatorName();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

    @SuppressLint({"MissingPermission"})
    public static String getNetworkType(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return String.valueOf(tel.getNetworkType());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }

}
