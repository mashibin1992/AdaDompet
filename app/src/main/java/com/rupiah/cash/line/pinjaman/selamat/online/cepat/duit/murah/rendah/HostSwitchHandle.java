package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.RequiresApi;

import com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.interfice.HostSwitchInterfice;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;

public class HostSwitchHandle {
    private static String mData;
    private static String mCallback;
    private static WebView mWeb;
    private static Activity activity;
    private static HostSwitchInterfice mHostSwitchInterfice;

    public HostSwitchHandle(WebView mWeb, Activity activity, HostSwitchInterfice hostSwitchInterfice) {
        HostSwitchHandle.mWeb = mWeb;
        HostSwitchHandle.activity = activity;
        mHostSwitchInterfice = hostSwitchInterfice;
    }

    public void switchHandle() {
        mWeb.setWebViewClient(new WebViewClient() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                try {
                    Uri uri = Uri.parse(url);
                    String scheme = uri.getScheme();
                    String mMHost = uri.getHost();
                    if (scheme.equals("wode-schema")) {
                        mData = uri.getQueryParameter("data");
                        mCallback = uri.getQueryParameter("callback");
                        switch (mMHost) {
                            case "appInfo":
                                //处理AppInfo的业务逻辑
                                mHostSwitchInterfice.appInfo(mData, mCallback);
                                break;
                            case "updateUid":
                                //处理更新uid的业务逻辑
                                mHostSwitchInterfice.updateUid(mData, mCallback);
                                break;
                            case "getAL":
                                //获取app列表
                                mHostSwitchInterfice.getAL(mData, mCallback);
                                break;
                            case "uploadDeviceInfo":
                                //轮渡
                                mHostSwitchInterfice.uploadDeviceInfo(mData, mCallback);
                                break;
                            case "getGPS":
                                //获取位置信息
                                mHostSwitchInterfice.getGPS(mData, mCallback);
                                break;
                            case "huoti":
                                //活体
                                mHostSwitchInterfice.huoti(mData, mCallback);
                                break;
                            case "jumpUrlOuter":
                                mHostSwitchInterfice.jumpUrlOuter(mData, mCallback);
                                break;
                            case "jumpUrlInner":
                                //打开新的webview跳转
                                mHostSwitchInterfice.jumpUrlInner(mData, mCallback);
                                break;
                            case "disabledGoBack":
                                //禁用物理返回键
                                mHostSwitchInterfice.disabledGoBack(mData, mCallback);
                                break;
                            case "closeNewWebview":
                                //关闭 webview
                                mHostSwitchInterfice.closeNewWebview(mData, mCallback);
                                mWeb.reload();
                                activity.finish();
                                break;
                            case "reload":
                                //调用webview的reload方法刷新webview
                                mWeb.reload();
                                mHostSwitchInterfice.reload(mData, mCallback);
                                break;
                            case "applyPermission":
                                //调用系统的申请权限
                                mHostSwitchInterfice.applyPermission(mData, mCallback);
                                break;
                            case "applyCamera":
                                //请求相机和存储的权限
                                mHostSwitchInterfice.applyCamera(mData, mCallback);
                                break;
                            case "exitApp":
                                //退出app
                                mHostSwitchInterfice.exitApp(mData, mCallback);
                                break;
                            case "jumpSetting":
                                //根据app的包名跳转至此APP对用的应用设置界面
                                mHostSwitchInterfice.jumpSetting(mData, mCallback);
                                break;
                            case "goHome":
                                //将打开的多个包含WebView的Activity除了第一个全部关闭
                                mHostSwitchInterfice.goHome(mData, mCallback);
                                activity.finish();
                                break;
                            case "chooseLinkman":
                                //选择联系人
                                //首先 我们需要跳入手机通讯录
                                mHostSwitchInterfice.chooseLinkman(mData, mCallback);
                                break;
                            case "uploadAB":
                                //上传通讯录
                                mHostSwitchInterfice.uploadAB(mData, mCallback);
                                break;
                        }
                        return true;
                    } else {
                        if (!TextUtils.isEmpty(mMHost) && mMHost.equals("play.google.com")) {//host为play.google.com的,因Firebase统计归因的问题用外部浏览器打开
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            activity.startActivity(intent);
                            return true;
                        } else {
                            loadUrl(url);
                            return true;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return true;
                }
            }
        });
    }

    public void loadUrl(String data) {
        try {
            String url = new JSONObject(data).getString("url");
            if (!url.startsWith("https://")) {
                url = URLDecoder.decode(url);
            }
            mWeb.loadUrl(url);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void uploadData(String callback, String data) {
        mWeb.evaluateJavascript("javascript: " + callback + "(" + data + ")", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                //此处为 js 返回的结果
                Log.d("loadUrlData", "onReceiveValue: " + value);
            }
        });
    }
}
