package com.scut.bbs.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.scut.bbs.MyApplication;

/**
 * 有关SharedPreference的工具栏
 * @author 徐鑫
 */
public class SharedPreferenceUtils {

    public static final String REMEMBER_KEY = "rememberPassword";

    public static SharedPreferences getSharedPreference() {
        return MyApplication.getInstance().getSharedPreferences("config", Context.MODE_PRIVATE);
    }
}
