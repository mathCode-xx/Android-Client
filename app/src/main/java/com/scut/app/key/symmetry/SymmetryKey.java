package com.scut.app.key.symmetry;

import com.scut.app.key.util.KeyConstant;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class SymmetryKey {
    protected static byte[] key;

    public static byte[] encrypt(byte[] data) {
        try {
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(key, KeyConstant.SYMMETRY_MODEL);
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
        try {
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(key, KeyConstant.SYMMETRY_MODEL);
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
