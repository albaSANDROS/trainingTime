package com.example.timer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

import java.util.List;
import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {

    private AppDatabase db;
    SharedPreferences sp;
    Button btnDeleteAll;
    Toolbar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = App.getInstance().getDatabase();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String size = sp.getString("font", "");
        String lang = sp.getString("language", "");
        String theme = sp.getString("theme",  "");
        if(size.equals("") || size.equals("false")) size = "17";
        if(lang.equals("") || lang.equals("false")) lang = "ru";
        if (theme.equals("") || theme.equals("false")) {
            setTheme(R.style.AppThemeDark);
        } else if(theme.equals("Тёмная") || theme.equals("Dark")){
            setTheme(R.style.AppThemeDark);
        }
        else {
            setTheme(R.style.AppThemeLight);
        }

        setLocale(lang);
        setContentView(R.layout.settings_activity);
        Configuration configuration = new Configuration();
        btnDeleteAll = findViewById(R.id.btnDeleteAll);
        bar = findViewById(R.id.toolbar);


        btnDeleteAll.setTextSize(Float.parseFloat(size));
        if (size.equals("13")) {
            configuration.fontScale = (float) 0.85;
        } else if (size.equals("17")) {
            configuration.fontScale = (float) 1;
        } else {
            configuration.fontScale = (float) 1.15;
        }

        getBaseContext().getResources().updateConfiguration(configuration, null);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();

        btnDeleteAll.setOnClickListener(i -> {
            List<Training> trainings =  db.trainingDao().getAllTrainings();
            for(int j = 0; j < trainings.size(); j++){
                db.trainingDao().delete(trainings.get(j));
            }
            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    private void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.locale = locale;
        getBaseContext().getResources().updateConfiguration(configuration, null);
    }


    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            ListPreference listPreferenceTheme = findPreference("theme");
            ListPreference listPreferenceSize = findPreference("font");
            ListPreference listPreferenceLang = findPreference("language");

            listPreferenceTheme.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setDefaultValue(newValue);
                    getActivity().recreate();
                    return true;
                }
            });
            listPreferenceSize.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setDefaultValue(newValue);
                    Configuration configuration = getResources().getConfiguration();
                    if (newValue.toString().equals("13")) {
                        configuration.fontScale = (float) 0.85;
                    } else if (newValue.toString().equals("17")) {
                        configuration.fontScale = (float) 1;
                    } else {
                        configuration.fontScale = (float) 1.15;
                    }
                    DisplayMetrics metrics = new DisplayMetrics();
                    getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
                    metrics.scaledDensity = configuration.fontScale * metrics.density;
                    getActivity().getBaseContext().getResources().updateConfiguration(configuration, metrics);
                    getActivity().recreate();
                    return true;
                }
            });

            listPreferenceLang.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    preference.setDefaultValue(newValue);
                    getActivity().recreate();
                    return true;
                }
            });
        }
    }
}