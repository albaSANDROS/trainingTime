package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import Data.AppDatabase;

public class TimerPage extends AppCompatActivity {

    AppDatabase db;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);
        db = App.getInstance().getDatabase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = (int)bundle.get("trainingId");

        txt = findViewById(R.id.textView1);
        txt.setText(db.trainingDao().getById(id).Name);
    }
}