package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.timer.Adapters.TrainingAdapter;
import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    AppDatabase db;
    ListView lst;
    List<Training> trainings;
    SharedPreferences sp;

    Button buttonAddTraining;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String size = sp.getString("font", "");
        String lang = sp.getString("language", "");
        setLocale(lang);
        if (sp.getString("theme", "Тёмная").equals("Тёмная")) {
            setTheme(R.style.AppThemeDark);
        }
        if (sp.getString("theme", "Светлая").equals("Светлая")) {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_main);
        db = App.getInstance().getDatabase();

        trainings = db.trainingDao().getAllTrainings();
        lst = findViewById(R.id.ListTraining);
        TrainingAdapter adapter = new TrainingAdapter(this, R.layout.list_item
                , trainings, size);
        lst.setAdapter(adapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Training training = (Training) parent.getItemAtPosition(position);
                Intent intent = new Intent(getApplicationContext(), TimerPage.class);
                intent.putExtra("trainingId", training.id);
                startActivity(intent);
            }
        });
        buttonAddTraining = findViewById(R.id.buttonAddTraining);
        buttonAddTraining.setTextSize(Float.parseFloat(size));
        findViewById(R.id.buttonAddTraining).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), CreateActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btnSettings).setOnClickListener(i -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivityForResult(intent, 1);
        });
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }

    public void deleteTraining(Training training) {
        db.trainingDao().delete(training);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        recreate();
    }
}