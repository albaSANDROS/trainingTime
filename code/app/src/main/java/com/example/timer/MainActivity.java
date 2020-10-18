package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Data.AppDatabase;
import Data.TrainingDao;
import Models.Training;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();

        List<String> names = Names();

        lst = findViewById(R.id.ListTraining);
        TrainingAdapter adapter = new TrainingAdapter(this, R.layout.list_item
                , db.trainingDao().getAll(), db);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(),TimerPage.class);
                intent.putExtra("name", names.get(position));
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonAddTraining).setOnClickListener(i -> {
            Intent intent = new Intent(this, CrateActivity.class);
            startActivity(intent);
        });
    }

    private List<String> Names(){
        List<Training> trainings =  db.trainingDao().getAll();
        ArrayList<String> names = new ArrayList<>();
        for (Training training: trainings) {
            names.add(training.Name);
        }
        return names;
    }

}