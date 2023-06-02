package com.example.taskmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbHelper extends SQLiteOpenHelper {
    public dbHelper(@Nullable Context context) {
        super(context, "userDB", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table userTable(" +
                "uNum integer primary key," +
                "uName varchar(20)," +
                "uID varchar(30)," +
                "uPW varchar(30))");
        db.execSQL("create table taskTable(" +
                "taskOrder integer primary key," +
                "userNum integer,"+
                "taskContent varchar(100))");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userTable");
        db.execSQL("drop table if exists taskTable");
        onCreate(db);
    }
}
