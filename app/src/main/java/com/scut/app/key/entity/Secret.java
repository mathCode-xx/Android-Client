package com.scut.app.key.entity;

/**
 * 用于唯一标识客户端密钥
 * @author 徐鑫
 */
public class Secret {
    private final String clientKey;
    private final String clientSecret;

    public String getClientKey() {
        return clientKey;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public Secret(String clientKey, String clientSecret) {
        this.clientKey = clientKey;
        this.clientSecret = clientSecret;
    }

    @Override
    public String toString() {
        return "Secret{" +
                "clientKey='" + clientKey + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                '}';
    }
}
