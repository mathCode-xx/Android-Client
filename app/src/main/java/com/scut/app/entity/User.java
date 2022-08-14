package com.scut.app.entity;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * 用户类
 * @author 徐鑫
 */
@Entity(indices = {@Index("id")})
public class User {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String password;
    public int permission;
    public char sex;
    public String college;
    public String major;
    public int score;
    public int status;
    /**
     * 学号长度
     */
    public static final int ID_LENGTH = 12;
    /**
     * 游客
     */
    public static final int TOURIST = 3;
    /**
     * 普通用户
     */
    public static final int NORMAL_USER = 2;
    /**
     * 管理员
     */
    public static final int MANAGER = 1;
    /**
     * 系统管理员
     */
    public static final int SYSTEM_MANAGER = 0;
    /**
     * 正常状态
     */
    public static final int NORMAL_STATUS = 0;
    /**
     * 禁言状态
     */
    public static final int NO_SPEAK_STATUS = 1;
    /**
     * 封禁状态
     */
    public static final int BANNED_STATUS = 2;

    public User() {
        this.id = "";
    }
}
