package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import Data.AppDatabase;
import Data.TrainingDao;
import Models.Training;

public class CrateActivity extends AppCompatActivity {

    AppDatabase db;

    TextInputEditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crate);

        db = App.getInstance().getDatabase();

        txt = findViewById(R.id.input);

        findViewById(R.id.submit).setOnClickListener(i -> {
            Training training = new Training();
            training.Name = txt.getText().toString();
            db.trainingDao().insert(training);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }
}