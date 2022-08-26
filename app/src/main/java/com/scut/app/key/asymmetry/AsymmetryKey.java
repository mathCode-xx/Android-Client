package com.scut.app.key.asymmetry;


import android.util.Log;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import cn.hutool.core.codec.Base64;

/**
 * 非对称加密
 *
 * @author 徐鑫
 */
public class AsymmetryKey {
    private static final String KEY_ALGORITHM = "RSA";
    private static final String TAG = "AsymmetryKey";

    /**
     * 加密方式，android的
     */
//  public static final String TRANSFORMATION = "RSA/None/NoPadding";
    /**
     * 加密方式，标准jdk的
     */
    public static final String TRANSFORMATION = "RSA/None/PKCS1Padding";

    public static byte[] encrypt(byte[] data, byte[] pubKey) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKey);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey publicKey = factory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            Log.d(TAG, "encrypt: ");
            return cipher.doFinal(data);
        } catch (Exception e) {
            Log.d(TAG, "encrypt: 出错");
            throw new RuntimeException("非对称加密出错！");
        }
    }
}
