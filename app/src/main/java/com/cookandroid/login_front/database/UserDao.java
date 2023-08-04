package com.cookandroid.login_front.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UserDao {
    @Query("SELECT * FROM 회원정보 WHERE 회원아이디 = :id")
    User findById(String id);
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM 회원정보 WHERE 회원아이디 = :id")
    User getUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Query("SELECT * FROM 회원정보 WHERE 회원아이디 = :id AND 회원PW = :password")
    User getUser(String id, String password);


}
