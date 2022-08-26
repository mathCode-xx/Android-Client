package com.scut.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.scut.app.MyApplication;

import cn.hutool.core.codec.Base64;

/**
 * 有关SharedPreference的工具栏
 * @author 徐鑫
 */
public class SharedPreferenceUtils {

    public static final String CLIENT_KEY = "client_key";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String REMEMBER_KEY = "rememberPassword";
    private static final SharedPreferences SHARED_PREFERENCES
            = MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);

    public static boolean isContains(String key) {
        return SHARED_PREFERENCES.getAll().containsKey(key);
    }

    public static void putBoolean(String key, boolean value) {
        SHARED_PREFERENCES.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return SHARED_PREFERENCES.getBoolean(key, defValue);
    }

    public static void putClientKey(String value) {
        SHARED_PREFERENCES.edit().putString(CLIENT_KEY, value).apply();
    }

    public static void putClientSecret(String value) {
        SHARED_PREFERENCES.edit().putString(CLIENT_SECRET, value).apply();
    }

    public static String getClientKey() {
        return SHARED_PREFERENCES.getString(CLIENT_KEY, null);
    }
    public static String getClientSecret() {
        return SHARED_PREFERENCES.getString(CLIENT_SECRET, null);
    }
}
