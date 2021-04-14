package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah;

public class Constants {
    public static final String CHANNEL_KEY = "16";
    public static final String COUNTRY = "+62";
    public static final String PLATFORM = "android";
    public static final String API_CODE = "a_a_0";

    //SP key
    public static final String DEVICE_ID = "deviceId";
    public static final String DEVICE_TOKEN = "deviceToken";
    public static final String UID = "uid"; //用户id
    //选择通讯录信息回调
    public static final int CONTACTS_INFO = 0x10;
    //活体的key
    public static final String LD_API_SECRET_KEY = "ld_api_secret_key"; //活体识别所需的secretKey
    public static final String LD_API_ACCESS_KEY = "ld_api_access_key"; //活体识别所需的accessKey

    //服务器地址
    public static final String ENV_URL = "dev"; //测试
    //活体回调code
    public static final int REQUEST_CODE_LIVENESS = 0x11;
    //状态码
    public static final String TIME_OUT = "50010";              //网络请求超时

    public static final String RESPONSE_ERROR = "50011";        //http 响应错误

    public static final String NETWORK_OPERATION_ERROR = "50012";//网路操作错误

    public static final String PARAMETER_ERROR = "50020";       //参数处理错误

    public static final String PARAMETER_MISSING = "50021";     //参数丢失

    public static final String ANALYSIS_ERROR = "50022";        //json 解析错误

    public static final String DECRYPT_ERROR = "50030";         //数据解密错误

}
