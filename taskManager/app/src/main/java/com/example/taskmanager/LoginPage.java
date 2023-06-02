package com.example.taskmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginPage extends AppCompatActivity {
    EditText usrIdInput, usrPwInput;
    Button loginBtn, logToMainBtn;
    dbHelper helper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        setTitle("Login Page");
        usrIdInput = (EditText) findViewById(R.id.usrIdInput);
        usrPwInput = (EditText) findViewById(R.id.usrPwInput);
        loginBtn = (Button) findViewById(R.id.loginBtn);
        logToMainBtn = (Button) findViewById(R.id.logToMainBtn);
        helper = new dbHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = helper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from userTable where uID='"
                        + usrIdInput.getText() + "' and uPW='"
                        + usrPwInput.getText() + "';", null);
                cursor.moveToFirst();

                if (cursor.getCount() == 1) {
                    Intent intent = new Intent(getApplicationContext(), taskPage.class);
                    int userNumber = cursor.getInt(0);
                    String username = cursor.getString(1);
                    intent.putExtra("userNumber", userNumber);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed",
                            Toast.LENGTH_SHORT).show();
                }
                cursor.close();
                sqlDB.close();


                /*
                while (cursor.moveToNext()) {
                    if (cursor.getCount())
                    Toast.makeText(getApplicationContext(), cursor.getString(0),
                            Toast.LENGTH_SHORT).show();
                }*/
                /*
                cursor = sqlDB.rawQuery("select * from userTable;", null);
                while (cursor.moveToNext()) {
                    Toast.makeText(getApplicationContext(), cursor.getString(2),
                            Toast.LENGTH_SHORT).show();
                }*/
                /*
                cursor.close();
                sqlDB.close();
                */

                /*
                while ( cursor.moveToNext() ) {
                    strNames += cursor.getString(0) + "\r\n";
                    strNumbers += cursor.getString(1)+"\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);

                cursor.close();
                sqlDB.close();
                */
            }
        });

        logToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}