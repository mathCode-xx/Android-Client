package com.scut.app.key.symmetry;

import com.scut.app.MyApplication;
import com.scut.app.key.entity.Secret;
import com.scut.app.key.util.KeyConstant;
import com.scut.app.util.SharedPreferenceUtils;

import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RadixUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;


/**
 * 对称密钥生成器
 *
 * @author 徐鑫
 */
public class SymmetryGenerator {

    public static void create() {
        try {
            Secret secret;
            if (SharedPreferenceUtils.isContains(SharedPreferenceUtils.CLIENT_KEY)
                    && SharedPreferenceUtils.isContains(SharedPreferenceUtils.CLIENT_SECRET)) {
                String clientKey = SharedPreferenceUtils.getClientKey();
                secret = new Secret(clientKey, SharedPreferenceUtils.getClientSecret());
            } else {
                //密钥生成器
                KeyGenerator keyGen = KeyGenerator.getInstance(KeyConstant.SYMMETRY_MODEL);
                //默认128，获得无政策权限后可为192或256
                keyGen.init(128);
                //生成密钥
                SecretKey secretKey = keyGen.generateKey();
                byte[] keyEncoded = secretKey.getEncoded();
                //base64编码
                String encode = Base64.encode(keyEncoded);
                secret = new Secret(RandomUtil.randomString(6), encode);

                SharedPreferenceUtils.putClientKey(secret.getClientKey());
                SharedPreferenceUtils.putClientSecret(secret.getClientSecret());
            }
            MyApplication.getInstance().setSecret(secret);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("对称密钥生成失败！");
        }
    }

}
