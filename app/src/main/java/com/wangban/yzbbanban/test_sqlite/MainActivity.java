package com.wangban.yzbbanban.test_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private MySQLiteOpenHelper dbHelper;
    private Button btnCreate;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnRetrieve;
    private Button btnReplace;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setData();
    }

    private void initView() {
        btnCreate = (Button) findViewById(R.id.btn_create);
        btnInsert = (Button) findViewById(R.id.btn_insert);
        btnUpdate = (Button) findViewById(R.id.btn_update);
        btnDelete = (Button) findViewById(R.id.btn_delete);
        btnRetrieve = (Button) findViewById(R.id.btn_retrieve);
        btnReplace = (Button) findViewById(R.id.btn_replace);
        textView = (TextView) findViewById(R.id.textView);

    }

    private void setData() {
        dbHelper = new MySQLiteOpenHelper(this, "BookStore.db", null, 3);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbHelper.getWritableDatabase();
            }
        });
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name", "create world");
                values.put("author", "ban");
                values.put("pages", 33);
                values.put("price", 100.2);
                db.insert("book", null, values);
                values.clear();
                values.put("name", "create sun");
                values.put("author", "banban");
                values.put("pages", 23);
                values.put("price", 230.2);
                db.insert("book", null, values);
                values.clear();
                values.put("category_name", "knight");
                values.put("category_code", 111);
                db.insert("category", null, values);
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("price", 98.5);
                db.update("book", values, "name=?", new String[]{"create sun"});
                values.clear();
                values.put("category_code", 2333);
                db.update("category", values, "category_name=?", new String[]{"knight"});
                values.clear();
                values.put("price", 400.5);
                db.update("book", values, "name=?", new String[]{"create sun"});

            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("book", "name=?", new String[]{"create sun"});
                db.delete("book", "pages>?", new String[]{"30"});
            }
        });
        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor c = db.query("book", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        String name = c.getString(c.getColumnIndex("name"));
                        int pages = c.getInt(c.getColumnIndex("pages"));
                        double price = c.getDouble(c.getColumnIndex("price"));
                        String author = c.getString(c.getColumnIndex("author"));
                        textView.append("name: " + name +
                                "\nauthor: " + author +
                                "\npages" + pages +
                                "\nprice" + price);
                    } while (c.moveToNext());
                }
                c.close();
            }
        });
        btnReplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.beginTransaction();
                try {
                    db.delete("category",null,null);
                    ContentValues valus=new ContentValues();
                    valus.put("category_name","java编程");
                    valus.put("category_code",777777);
                    db.insert("category",null,valus);
                    db.setTransactionSuccessful();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    db.endTransaction();
                }
            }
        });
    }
}
