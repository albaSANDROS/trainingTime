package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    ListView lst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();


        lst = findViewById(R.id.ListTraining);
        TrainingAdapter adapter = new TrainingAdapter(this, R.layout.list_item
                , db.trainingDao().getAll(), db);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                Training training = (Training) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(),TimerPage.class);
                intent.putExtra("trainingId", training.Id);
                startActivity(intent);
            }
        });

        findViewById(R.id.buttonAddTraining).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), CrateEditActivity.class);
            intent.putExtra("trainingId", new int[]{0,0});
            startActivity(intent);
        });
    }
}