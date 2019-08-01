package com.example.testmodule.livedata;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

//注解配置sql语句
@Dao
public interface UserDao {
    //所有的CURD根据primary key进行匹配
//------------------------query------------------------
//简单sql语句，查询user表所有的column
    @Query("SELECT * FROM UserEntity")
    List<UserEntity> getAll();

    //根据条件查询，方法参数和注解的sql语句参数一一对应
    @Query("SELECT * FROM UserEntity WHERE uid IN (:userIds)")
    List<UserEntity> loadAllByIds(int[] userIds);

    //同上
    @Query("SELECT * FROM UserEntity WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    UserEntity findByName(String first, String last);

    //同上
    @Query("SELECT * FROM UserEntity WHERE uid = :uid")
    UserEntity findByUid(int uid);

//-----------------------insert----------------------

    // OnConflictStrategy.REPLACE表示如果已经有数据，那么就覆盖掉
//数据的判断通过主键进行匹配，也就是uid，非整个user对象
//返回Long数据表示，插入条目的主键值（uid）
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(UserEntity user);
    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(UserEntity... users);
    //返回List<Long>数据表示被插入数据的主键uid列表
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<UserEntity> users);

    //---------------------update------------------------
//更新已有数据，根据主键（uid）匹配，而非整个user对象
//返回类型int代表更新的条目数目，而非主键uid的值。
//表示更新了多少条目
    @Update()
    int update(UserEntity user);
    //同上
    @Update()
    int updateAll(UserEntity... user);
    //同上
    @Update()
    int updateAll(List<UserEntity> user);

    //-------------------delete-------------------
//删除user数据，数据的匹配通过主键uid实现。
//返回int数据表示删除了多少条。非主键uid值。
    @Delete
    int delete(UserEntity user);
    //同上
    @Delete
    int deleteAll(List<UserEntity> users);
    //同上
    @Delete
    int deleteAll(UserEntity... users);
}


