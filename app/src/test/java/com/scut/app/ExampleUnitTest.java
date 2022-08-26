package com.scut.app;

import android.util.Log;

import com.scut.app.key.asymmetry.AsymmetryKey;

import org.junit.Test;

import cn.hutool.core.codec.Base64;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        String before = "PMYNDA18erhFGXlefEhh0w==";
        String pubStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfaJFicuy+M6urBvXVo3Fnjy/fXM+xd6Hxvrk1lwa9KwwEpNCNMYZ8U3wB6xeqPRaHo5Dm/vAg6qe6NMGhIxPusaH4eF4zr6CXWXj24NRTt83so5eCQI5jdrG7mhI7awxXGMXafzJdsEUaajp1eUnkEyzXhTmMszJCko4y8pMMTQIDAQAB";
        byte[] encrypt = AsymmetryKey.encrypt(Base64.decode(before), Base64.decode(pubStr));
        System.out.println(Base64.encode(encrypt));
    }
}