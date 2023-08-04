package com.cookandroid.login_front.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.w3c.dom.Text;

import java.lang.reflect.TypeVariable;

@Entity(tableName = "회원정보")
    public class User {
        @PrimaryKey
        @NonNull
        public String 회원아이디;

        public int 회원고유번호;


        public String 회원PW;
        public String 회원이름;
        public int 회원의_전화번호;

    public User(String 회원아이디, String 회원PW) {
        this.회원아이디 = 회원아이디;
        this.회원PW = 회원PW;
    }
    }

