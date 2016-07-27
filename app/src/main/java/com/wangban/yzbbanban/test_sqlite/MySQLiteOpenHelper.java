package com.wangban.yzbbanban.test_sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by YZBbanban on 16/7/27.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private Context context;
    public static final String CREATE_BOOK = "create table book(id integer primary key autoincrement,author text,price real,pages integer,name text)";
    public static final String CREATE_CATEGORY = "create table category(id integer primary key autoincrement ,category_name text,category_code integer )";
    public static final String CREATE_FOOD="create table food(id integer primary key autoincrement,name text,price real,type text)";
    public static final String CREATE_STUDY="create table study(id integer primary key autoincrement,lesson text,type text)";
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_BOOK);
        sqLiteDatabase.execSQL(CREATE_CATEGORY);
        sqLiteDatabase.execSQL(CREATE_FOOD);
        sqLiteDatabase.execSQL(CREATE_STUDY);
        Toast.makeText(context, "Create succeeded", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//        sqLiteDatabase.execSQL("drop table if exists book");
//        sqLiteDatabase.execSQL("drop table if exists category");
//        onCreate(sqLiteDatabase);
        switch (i){
            case 1:
//                sqLiteDatabase.execSQL(CREATE_FOOD);
                sqLiteDatabase.execSQL(CREATE_STUDY);
                break;
        }
    }
}
