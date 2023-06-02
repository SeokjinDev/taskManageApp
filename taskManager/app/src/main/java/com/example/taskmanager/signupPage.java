package com.example.taskmanager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class signupPage extends AppCompatActivity {
    EditText newNameInput, newIdInput, newPwInput;
    Button signupBtn, signToMainBtn;
    dbHelper helper;
    SQLiteDatabase sqlDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        setTitle("Sign Up Page");

        newNameInput = (EditText) findViewById(R.id.newNameInput);
        newIdInput = (EditText) findViewById(R.id.newIdInput);
        newPwInput = (EditText) findViewById(R.id.newPwInput);
        signupBtn = (Button) findViewById(R.id.signupBtn);
        signToMainBtn = findViewById(R.id.signToMainBtn);
        helper = new dbHelper(this);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqlDB = helper.getWritableDatabase();
                try {
                    sqlDB.execSQL("insert into userTable (uName, uID, uPW) values ('"
                            + newNameInput.getText() + "', '"
                            + newIdInput.getText() + "', '"
                            + newPwInput.getText() + "')");
                    Toast.makeText(getApplicationContext(), "Sign Up Success",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Sign Up Failed",
                            Toast.LENGTH_SHORT).show();
                }
                sqlDB.close();
            }
        });

        signToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}