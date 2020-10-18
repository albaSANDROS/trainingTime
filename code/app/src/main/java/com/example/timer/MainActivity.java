package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Data.AppDatabase;
import Data.TrainingDao;
import Models.Training;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    TrainingDao trainingDao;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();
        trainingDao = db.trainingDao();

        findViewById(R.id.buttonAddTraining).setOnClickListener(i -> {
            Intent intent = new Intent(this, CrateActivity.class);
            startActivity(intent);
        });

        lst = findViewById(R.id.ListTraining);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_item,
                Names());
        lst.setAdapter(adapter);
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