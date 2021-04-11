package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.cdma.CdmaCellLocation;
import android.telephony.gsm.GsmCellLocation;
//获取运营商信息  基站信息
public class NetWorkInfo {
    public static String getGsmCellLocation(Context context) {
        try {
            TelephonyManager tel = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            @SuppressLint("MissingPermission")
            CellLocation cel = tel.getCellLocation();
            int cid = 0;
            int lac = 0;
            if (tel.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA) {//如果是电信卡的话
                CdmaCellLocation cdmaCellLocation = (CdmaCellLocation) cel;
                cid = cdmaCellLocation.getBaseStationId();
                lac = cdmaCellLocation.getNetworkId();
            } else {//如果是移动和联通的话  移动联通一致
                GsmCellLocation gsmCellLocation = (GsmCellLocation) cel;
                cid = gsmCellLocation.getCid();
                lac = gsmCellLocation.getLac();
            }
            return cid + "|" + lac;

        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return "";
    }
}
