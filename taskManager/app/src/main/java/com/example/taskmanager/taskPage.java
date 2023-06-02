package com.example.taskmanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class taskPage extends AppCompatActivity {
    Button taskInputBtn, taskInitBtn, taskDeleteBtn, taskToMainBtn;
    TextView usernameView;
    EditText taskText;
    ListView listView;
    dbHelper helper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_page);

        taskInputBtn = findViewById(R.id.taskInputBtn);
        taskDeleteBtn = findViewById(R.id.taskDeleteBtn);
        taskInitBtn = findViewById(R.id.taskInitBtn);
        taskToMainBtn = findViewById(R.id.taskToMainBtn);
        usernameView = findViewById(R.id.usernameView);
        taskText = findViewById(R.id.taskText);
        listView = findViewById(R.id.listView);
        helper = new dbHelper(this);

        Intent intent = getIntent();
        int userNumber = intent.getIntExtra("userNumber", 0);
        String username = intent.getStringExtra("username");
        usernameView.setText("User : "+username);

        sqlDB = helper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select * from taskTable where userNum='"
                + Integer.toString(userNumber) + "';", null);
        cursor.moveToFirst();

        ArrayList<String> tasks = new ArrayList<String>();
        ArrayList<Integer> delPoint = new ArrayList<Integer>();

        while (cursor.moveToNext()) {
            tasks.add(cursor.getString(2));
        }
        cursor.close();
        sqlDB.close();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, tasks);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), "Task Complete",
                        Toast.LENGTH_SHORT).show();
                if (delPoint.contains(i)) {
                    delPoint.remove(i);
                } else {
                    delPoint.add(i);
                }
            }
        });

        taskInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newTask = taskText.getText().toString();
                tasks.add(newTask);
                adapter.notifyDataSetChanged();

                sqlDB = helper.getWritableDatabase();
                try {
                    sqlDB.execSQL("insert into taskTable (userNum, taskContent) values ('"
                            + userNumber + "', '"
                            + newTask + "')");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Query Failed",
                            Toast.LENGTH_SHORT).show();
                }
                sqlDB.close();
            }
        });

        taskDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String delTask;
                for(int i=0; i<delPoint.size(); i++) {
                    delTask = tasks.get(delPoint.get(i));
                    tasks.remove(delTask);
                    if (!delTask.isEmpty()) {
                        try {
                            sqlDB.execSQL("delete from taskTable WHERE taskContent='"
                                    + delTask + "'';");
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Query Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                delPoint.clear();

                sqlDB = helper.getWritableDatabase();
                sqlDB.close();
            }
        });

        taskInitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tasks.clear();
                delPoint.clear();
                adapter.notifyDataSetChanged();

                sqlDB = helper.getWritableDatabase();
                try {
                    sqlDB.execSQL("delete from taskTable WHERE userNum=" +
                            + userNumber + ";");
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Query Failed",
                            Toast.LENGTH_SHORT).show();
                }
                sqlDB.close();
            }
        });

        taskToMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}