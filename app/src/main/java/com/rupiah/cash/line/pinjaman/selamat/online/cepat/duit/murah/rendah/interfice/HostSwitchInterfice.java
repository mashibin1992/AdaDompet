package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice;

public interface HostSwitchInterfice {
    void appInfo(String data, String callback);

    void updateUid(String data, String callback);

    void getAL(String data, String callback);

    void uploadDeviceInfo(String data, String callback);

    void getGPS(String data, String callback);

    void huoti(String data, String callback);

    void jumpUrlOuter(String data, String callback);

    void jumpUrlInner(String data, String callback);

    void disabledGoBack(String data, String callback);

    void closeNewWebview(String data, String callback);

    void reload(String data, String callback);

    void applyPermission(String data, String callback);

    void applyCamera(String data, String callback);

    void exitApp(String data, String callback);

    void jumpSetting(String data, String callback);

    void goHome(String data, String callback);

    void chooseLinkman(String data, String callback);

    void uploadAB(String data, String callback);
}
