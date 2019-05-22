package com.example.testmodule;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    void insert(UserEntity entity);

    @Query("SELECT * FROM UserEntity WHERE id=1")
    UserEntity getUserOne();

    @Update
    void update(UserEntity entity);

    @Query("SELECT * FROM UserEntity ")
    List<UserEntity> getUser();


    @Query("SELECT * FROM UserEntity WHERE id=1")
    LiveData<UserEntity> getUserByLiveData();
}


