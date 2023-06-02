package com.example.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button loginPageBtn, signupPageBtn, initBtn;
    dbHelper helper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginPageBtn = (Button) findViewById(R.id.loginPageBtn);
        signupPageBtn = (Button) findViewById(R.id.signupPageBtn);
        initBtn = (Button) findViewById(R.id.initBtn);
        helper = new dbHelper(this);

        loginPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginPage.class);
                startActivity(intent);
            }
        });

        signupPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), signupPage.class);
                startActivity(intent);
            }
        });
        initBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = helper.getWritableDatabase();
                helper.onUpgrade(sqlDB, 1, 2);;
                sqlDB.close();
            }
        });
    }
}