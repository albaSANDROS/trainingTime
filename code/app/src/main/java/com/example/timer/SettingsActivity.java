package com.example.timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

import java.util.List;

public class SettingsActivity extends AppCompatActivity {

    private AppDatabase db;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = App.getInstance().getDatabase();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getString("theme", "Тёмная").equals("Тёмная"))
        {
            setTheme(R.style.AppThemeDark);
        }
        if (sp.getString("theme", "Светлая").equals("Светлая"))
        {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        findViewById(R.id.btnDeleteAll).setOnClickListener(i -> {
            List<Training> trainings =  db.trainingDao().getAllTrainings();
            for(int j = 0; j < trainings.size(); j++){
                db.trainingDao().delete(trainings.get(j));
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}