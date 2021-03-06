package com.rupiah.cash.line.pinjaman.selamat.online.cepat.duit.murah.rendah.util;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public class CreateUUID {

    /**
     * create by Huanmie on 2019-11-26 11:51
     */

    private static String TEMP_DIR = "system_config";
    private static String TEMP_FILE_NAME = "system_file";
    private static String TEMP_FILE_NAME_MIME_TYPE = "application/octet-stream";
    private static String SP_NAME = "device_info";
    private static String SP_KEY_DEVICE_ID = "device_id";

    public static String createUUID(Context context) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri externalContentUri = MediaStore.Downloads.EXTERNAL_CONTENT_URI;
            ContentResolver contentResolver = context.getContentResolver();
            String[] projection = new String[]{MediaStore.Downloads._ID};
            String selection = MediaStore.Downloads.TITLE + "=?";
            String[] args = new String[]{TEMP_FILE_NAME};
            Cursor query = contentResolver.query(externalContentUri, projection, selection, args, null);
            if (query != null && query.moveToFirst()) {
                Uri uri = ContentUris.withAppendedId(externalContentUri, query.getLong(0));
                query.close();
                InputStream inputStream = null;
                BufferedReader bufferedReader = null;
                try {
                    inputStream = contentResolver.openInputStream(uri);
                    if (inputStream != null) {
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                        uuid = bufferedReader.readLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Downloads.TITLE, TEMP_FILE_NAME);
                contentValues.put(MediaStore.Downloads.MIME_TYPE, TEMP_FILE_NAME_MIME_TYPE);
                contentValues.put(MediaStore.Downloads.DISPLAY_NAME, TEMP_FILE_NAME);
                contentValues.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS + File.separator.toString() + TEMP_DIR);
                Uri insert = contentResolver.insert(externalContentUri, contentValues);
                if (insert != null) {
                    OutputStream outputStream = null;
                    try {
                        outputStream = contentResolver.openOutputStream(insert);
                        if (outputStream == null) {
                            return uuid;
                        }
                        outputStream.write(uuid.getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (outputStream != null) {
                            try {
                                outputStream.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            File externalDownloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File applicationFileDir = new File(externalDownloadsDir, TEMP_DIR);
            if (!applicationFileDir.exists()) {
                if (!applicationFileDir.mkdirs()) {
                    Log.e("DeviceldUtils", "?????????????????????: " + applicationFileDir.getPath());
                }
            }
            File file = new File(applicationFileDir, TEMP_FILE_NAME);
            if (!file.exists()) {
                FileWriter fileWriter = null;
                try {
                    if (file.createNewFile()) {
                        fileWriter = new FileWriter(file, false);
                        fileWriter.write(uuid);
                    } else {
                        Log.e("DeviceldUtils", "?????????????????????" + file.getPath());
                    }
                } catch (IOException e) {
                    Log.e("DeviceldUtils", "?????????????????????" + file.getPath());
                    e.printStackTrace();
                } finally {
                    if (fileWriter != null) {
                        try {
                            fileWriter.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                FileReader fileReader = null;
                BufferedReader bufferedReader = null;
                try {
                    fileReader = new FileReader(file);
                    bufferedReader = new BufferedReader(fileReader);
                    uuid = bufferedReader.readLine();
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fileReader != null) {
                        try {
                            fileReader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return uuid;
    }
    @SuppressLint("MissingPermission")
    public static String getUuid(Context context) {
        String subscriberId = "";
        TelephonyManager mTelephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            //??????Android_ID
            subscriberId = Settings.System.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        } else {
            subscriberId = mTelephonyManager.getSubscriberId();// ?????????????????????,android 10.0????????????
        }
        return subscriberId;
    }
}
