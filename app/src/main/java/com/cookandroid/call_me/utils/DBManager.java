package com.cookandroid.call_me.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {
    // DB name 임시 변수 (이름1 .. 이름2 .... 이름6)
    static private int nameCnt;
    private static final String DATABASE_NAME = "self.db";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DBManager(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
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

    public long insertUserData(String tel) {
        ContentValues values = new ContentValues();
        values.put("name", "name" + (++nameCnt));
        values.put("tel", tel);

        long newRowId = db.insert("user", null, values);

        return newRowId;
    }

    public List<List<String>> selectAll(){
        Cursor cursor = db.query("user", new String[] {"name", "tel"}, null, null, null, null, null);

        List<List<String>> data = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                List<String> tmp = new ArrayList<>();

                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String tel = cursor.getString(cursor.getColumnIndexOrThrow("tel"));

                tmp.add(name);
                tmp.add(tel);
                data.add(tmp);
            }
            cursor.close();
        }
        return data;
    }

    public void deleteAllUserData() {
        db.delete("user", null, null);
    }

    public SQLiteDatabase getDB(){
        return db;
    }

    public void close(){
        db.close();
    }
}
