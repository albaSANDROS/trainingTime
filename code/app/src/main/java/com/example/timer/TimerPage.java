package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class TimerPage extends AppCompatActivity {

    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer_page);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = (String)bundle.get("name");

        txt = findViewById(R.id.textView1);
        txt.setText(name);
    }
}