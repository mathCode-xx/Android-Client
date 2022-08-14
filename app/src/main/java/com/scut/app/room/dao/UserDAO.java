package com.scut.app.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.scut.app.entity.User;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

/**
 * 数据库查询
 *
 * @author 徐鑫
 */
@Dao
public interface UserDAO {

    /**
     * 插入操作
     *
     * @param user 数据
     * @return 异步调用
     */
    @Insert
    public Completable insert(User user);

    /**
     * 插入集合
     *
     * @param users 数据
     * @return 受影响的行数
     */
    @Insert
    public Completable insertList(List<User> users);

    /**
     * 依据主键更新数据
     *
     * @param user 需要更新的数据
     * @return 受影响的行数
     */
    @Update
    public Single<Integer> update(User user);

    /**
     * 根据主键删除数据
     *
     * @param user 需要删除的数据
     * @return 受影响的行数
     */
    @Delete
    public Single<Integer> delete(User user);

    /**
     * 根据id查找用户信息
     *
     * @param id 依据
     * @return 查找到的用户
     */
    @Query("select * from user where id = :id")
    public Single<User> selectById(String id);

    /**
     * 读取一个数据
     *
     * @return 读取到的数据
     */
    @Query("select * from user limit 1")
    public Single<User> selectOne();

    /**
     * 查询所有用户信息
     * @return 查询到的数据
     */
    @Query("select * from user")
    public Single<List<User>> selectAll();
}
