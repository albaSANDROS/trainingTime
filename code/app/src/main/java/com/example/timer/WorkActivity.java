package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class WorkActivity extends AppCompatActivity {

    private AppDatabase db;

    TextView namePart;
    TextView timePart;
    ListView allParts;
    Button btnStart;
    Button btnStop;

    Timer timer;
    Runnable Timer_Tick;

    ArrayList<String> items = new ArrayList<>();

    int t = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = (int)bundle.get("trainingId");

        Training training = db.trainingDao().getById(id);
        CreateItemSequence(training);
        namePart = findViewById(R.id.partName);
        timePart = findViewById(R.id.partTime);
        allParts = findViewById(R.id.allParts);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        allParts.setAdapter(adapter);

        Timer_Tick = new Runnable() {

            public void run() {
                String temp = Integer.toString(t);
                timePart.setText(temp);
                t--;
            }
        };

        btnStart.setOnClickListener(i -> {
            if (timer != null) {
                timer.cancel();
            }
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }
            }, 0, 1000);
        });

        btnStop.setOnClickListener(i -> {
            if (timer != null) {
                timer.cancel();
                timer = null;
            }
        });
    }

    private void TimerMethod() {
        this.runOnUiThread(Timer_Tick);
    }

    private void CreateItemSequence(Training training){
        String[] names = {"Работа","Отдых","Подготовиться","Отдых между подходами","Финиш"};
        items.add(names[2] + " : " + training.PreparationTime);

        for(int i = 0; i < training.Sets; i++) {
            for (int j = 0; j < training.Cycles; j++) {
                items.add(names[0] + " : " + training.WorkTime);
                items.add(names[1] + " : " + training.RestTime);
            }
            items.add(names[3] + " : " + training.Calm);
        }
        items.add(names[4]);
    }
}