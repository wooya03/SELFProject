package com.cookandroid.call_me.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "self.db";
    private static final int DATABASE_VERSION = 1;

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // 간이 SQL 문
        String sql =
        "CREATE TABLE user (" +
                "id INTEGER PRIMARY KEY," +
                "name TEXT NOT NULL," +
                "tel TEXT NOT NULL" +
        ");";

        // sql 실행
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 버전이 올라갈 때마다 테이블 삭제 후 재생성
        if (oldVersion < newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
            onCreate(sqLiteDatabase);
        }
    }
}
