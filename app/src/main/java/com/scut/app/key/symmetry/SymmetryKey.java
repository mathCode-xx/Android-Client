package com.scut.app.key.symmetry;

import android.util.Log;

import com.scut.app.MyApplication;
import com.scut.app.key.util.KeyConstant;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import cn.hutool.core.codec.Base64;


public class SymmetryKey {

    private static final byte[] KEY = Base64.decode(MyApplication.getInstance().getSecret().getClientSecret());
    private static final String TAG = "SymmetryKey";

    public static byte[] encrypt(byte[] data) {
        Log.d(TAG, "encrypt: " + Base64.encode(KEY));
        try {
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(KEY, KeyConstant.SYMMETRY_MODEL);
            //Cipher完成加密或解密工作类
            Cipher cipher = Cipher.getInstance(KeyConstant.SYMMETRY_MODEL);
            //对Cipher初始化，解密模式
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //加密data
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("对称加密异常！");
        }
    }

    public static byte[] decrypt(byte[] data) {
        Log.d(TAG, "encrypt: " + Base64.encode(KEY));
        try {
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(KEY, KeyConstant.SYMMETRY_MODEL);
            //Cipher完成加密或解密工作类
            Cipher cipher = Cipher.getInstance(KeyConstant.SYMMETRY_MODEL);
            //对Cipher初始化，解密模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //解密data
            return cipher.doFinal(data);
        } catch (Exception e) {
            throw new RuntimeException("对称解密异常！");
        }
    }
}
