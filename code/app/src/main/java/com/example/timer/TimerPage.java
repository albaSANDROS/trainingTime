package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timer.Adapters.TimerPageAdapter;
import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Stage;
import com.example.timer.Models.TrainingStages;

import java.util.ArrayList;
import java.util.List;

public class TimerPage extends AppCompatActivity {

    SharedPreferences sp;
    AppDatabase db;
    ListView lst;
    TextView nameTraining;
    ArrayList<String> trainingLst = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getString("theme", "Тёмная").equals("Тёмная")) {
            setTheme(R.style.AppThemeDark);
        }
        if (sp.getString("theme", "Светлая").equals("Светлая")) {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_timer_page);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        long id = (long) bundle.get("trainingId");

        nameTraining = findViewById(R.id.nameTraining);
        lst = findViewById(R.id.listTraining);
        getDataFromDb(id);
        TimerPageAdapter adapter = new
                TimerPageAdapter(this, R.layout.work_list_item, trainingLst);
        lst.setAdapter(adapter);
    }

    private void getDataFromDb(long id) {
        List<TrainingStages> trainingStages = db.trainingDao().getTrainingStagesByTrainingId(id);
        for (TrainingStages trainingStage : trainingStages) {
            nameTraining.setText(trainingStage.training.name);
            for (Stage stage : trainingStage.stages) {
                trainingLst.add(stage.stageName + ":" + Integer.toString(stage.stageTime));
            }
            break;
        }
    }
}