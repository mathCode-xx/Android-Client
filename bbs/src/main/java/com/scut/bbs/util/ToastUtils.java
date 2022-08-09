package com.scut.bbs.util;

import android.content.Context;
import android.widget.Toast;

import com.scut.bbs.MyApplication;

/**
 * 土司工具
 * @author 徐鑫
 */
public class ToastUtils {
    public static void show(String msg) {
        Toast.makeText(MyApplication.getInstance(), msg, Toast.LENGTH_SHORT).show();
    }

    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
