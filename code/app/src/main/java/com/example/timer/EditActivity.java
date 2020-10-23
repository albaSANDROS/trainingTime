package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.textfield.TextInputEditText;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

public class EditActivity extends AppCompatActivity {

    AppDatabase db;
    TextInputEditText txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        db = App.getInstance().getDatabase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = (int)bundle.get("trainingId");

        Training training = db.trainingDao().getById(id);

        txt = findViewById(R.id.editText);
        txt.setText(training.Name);

        findViewById(R.id.submitEditButton).setOnClickListener(i -> {
            training.Name = txt.getText().toString();
            db.trainingDao().update(training);
            Intent backIntent = new Intent(this, MainActivity.class);
            startActivity(backIntent);
        });
    }
}