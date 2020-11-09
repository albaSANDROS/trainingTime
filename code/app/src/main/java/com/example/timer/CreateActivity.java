package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timer.Adapters.PartsAdapter;
import com.example.timer.Data.AppDatabase;
import com.example.timer.Dialogs.AddStageDialog;
import com.example.timer.Models.Stage;
import com.example.timer.Models.Training;

import java.util.ArrayList;
import java.util.Locale;

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;

public class CreateActivity extends AppCompatActivity {

    SharedPreferences sp;
    ArrayList<Stage> stages = new ArrayList<>();
    Training training = new Training();
    AppDatabase db;
    ListView lst;
    HSLColorPickerSeekBar bar;
    EditText inputName;
    PartsAdapter adapter;
    TextView textViewTraining;
    TextView textColor;
    Button bntAddStage;
    Button btnSave;

    String activityName = "CreateActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = App.getInstance().getDatabase();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String size = sp.getString("font", "");
        String lang = sp.getString("language", "");
        if (sp.getString("theme", "Тёмная").equals("Тёмная")) {
            setTheme(R.style.AppThemeDark);
        }
        if (sp.getString("theme", "Светлая").equals("Светлая")) {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_create);
        findControls();
        textViewTraining.setTextSize(Float.parseFloat(size));
        inputName.setTextSize(Float.parseFloat(size));
        textColor.setTextSize(Float.parseFloat(size));
        bntAddStage.setTextSize(Float.parseFloat(size));
        btnSave.setTextSize(Float.parseFloat(size));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        adapter = new PartsAdapter(this, R.layout.list_stage_item, stages, size, activityName);
        lst.setAdapter(adapter);

        findViewById(R.id.bntAddStage).setOnClickListener(i -> {
            AddStageDialog dialogFragment = new AddStageDialog();
            FragmentManager manager = getSupportFragmentManager();

            FragmentTransaction transaction = manager.beginTransaction();
            dialogFragment.show(transaction, "dialog");
        });

        findViewById(R.id.btnSave).setOnClickListener(i -> {
            if (!inputName.getText().toString().equals("") && stages.size() != 0) {
                training.name = inputName.getText().toString();
                training.locale = lang;
                IntegerHSLColor hslColor = bar.getPickedColor();
                training.Color = Color.HSVToColor(new float[]{hslColor.getFloatH(), hslColor.getFloatL()
                        , hslColor.getFloatS()});
                db.trainingDao().insert(training, stages);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (stages.size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toastInputStages), Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.toastInputName), Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    ArrayList<String> names;


    private void findControls() {
        lst = findViewById(R.id.listStages);
        inputName = findViewById(R.id.inputName);
        textViewTraining = findViewById(R.id.textViewTraining);
        bar = findViewById(R.id.hueSeekBar);
        textColor = findViewById(R.id.textColor);
        bntAddStage = findViewById(R.id.bntAddStage);
        btnSave = findViewById(R.id.btnSave);
    }


    private void getAllNames() {
        names = null;
        names = new ArrayList<>();
        for (Stage stage : stages) {
            names.add(stage.stageName);
        }
    }

    public void deleteStage(int id){
        stages.remove(id);
        adapter.notifyDataSetChanged();
    }

    public void setDataFromDialog(String partName, String partTime) {
        getAllNames();
        if (names.contains(getResources().getStringArray(R.array.stages)[3])
                && partName.equals(getResources().getStringArray(R.array.stages)[3])) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    getResources().getString(R.string.toastTwiceSet), Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Stage stage = new Stage();
            stage.stageName = partName;
            stage.stageTime = Integer.parseInt(partTime);
            stages.add(stage);
        }
    }
}