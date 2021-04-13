package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GpsLocationUtils {

    @SuppressLint("MissingPermission")
    public static double[] getLocation(Context context) {
        double[] doubles = new double[2];
        LocationManager locationManager;
        LocationListener locationListener;
        Location location;
        final String contextService = Context.LOCATION_SERVICE;
        String provider;
        locationManager = (LocationManager) context.getSystemService(contextService);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);// 高精度
        criteria.setAltitudeRequired(false);// 不要求海拔
        criteria.setBearingRequired(false);// 不要求方位
        criteria.setCostAllowed(true);// 允许有花费
        criteria.setPowerRequirement(Criteria.POWER_LOW);// 低功耗
        // 从可用的位置提供器中，匹配以上标准的最佳提供器
        provider = locationManager.getBestProvider(criteria, true);
        // 获得最后一次变化的位置
        location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            doubles[0] = latitude;
            doubles[1] = longitude;
        }
        locationListener = new LocationListener() {
            public void onStatusChanged(String provider, int status,
                                        Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }

            public void onLocationChanged(Location location) {
                doubles[0] = location.getLatitude();
                doubles[1] = location.getLongitude();
            }
        };
        // 监听位置变化，2秒一次，距离10米以上
        locationManager.requestLocationUpdates(provider, 1000, 10,
                locationListener);
        return doubles;
    }
}
