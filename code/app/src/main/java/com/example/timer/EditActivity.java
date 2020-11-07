package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.example.timer.Adapters.PartsAdapter;
import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Stage;
import com.example.timer.Models.Training;
import com.example.timer.Models.TrainingStages;

import java.util.ArrayList;
import java.util.List;

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;

public class EditActivity extends AppCompatActivity {

    SharedPreferences sp;
    ArrayList<Stage> stages = new ArrayList<>();
    Training training = new Training();
    AppDatabase db;
    ListView lst;

    HSLColorPickerSeekBar bar;
    EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = App.getInstance().getDatabase();

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        if (sp.getString("theme", "Тёмная").equals("Тёмная")) {
            setTheme(R.style.AppThemeDark);
        }
        if (sp.getString("theme", "Светлая").equals("Светлая")) {
            setTheme(R.style.AppThemeLight);
        }
        setContentView(R.layout.activity_edit);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        long id = (long) bundle.get("trainingId");
        findControls();
        getDataFromDb(id);


        inputName.setText(training.name);
        bar.setPickedColor(convertToIntegerHSLColor(training.Color));
        PartsAdapter adapter = new PartsAdapter(this, R.layout.list_stage_item, stages);
        lst.setAdapter(adapter);

        findViewById(R.id.btnEdit).setOnClickListener(i -> {
            if (!inputName.getText().toString().equals("")) {
                training.name = inputName.getText().toString();
                IntegerHSLColor hslColor = bar.getPickedColor();
                training.Color = Color.HSVToColor(new float[]{hslColor.getFloatH(), hslColor.getFloatL()
                        , hslColor.getFloatS()});
                db.trainingDao().update(training);
                for (Stage stage : stages) {
                    db.trainingDao().update(stage);
                }
                Intent backIntent = new Intent(this, MainActivity.class);
                startActivity(backIntent);
                finish();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Напишите название тренировки", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    private IntegerHSLColor convertToIntegerHSLColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        IntegerHSLColor integerHSLColor = new IntegerHSLColor();
        integerHSLColor.setFloatH(hsv[0]);
        integerHSLColor.setFloatL(hsv[1]);
        integerHSLColor.setFloatS(hsv[2]);
        return integerHSLColor;
    }


    private void findControls() {
        lst = findViewById(R.id.listStages);
        inputName = findViewById(R.id.inputName);
        bar = findViewById(R.id.hueSeekBar);
    }

    private void getDataFromDb(long id) {
        List<TrainingStages> trainingStages = db.trainingDao().getTrainingStagesByTrainingId(id);
        for (TrainingStages trainingStage : trainingStages) {
            stages = (ArrayList<Stage>) trainingStage.stages;
            training.id = trainingStage.training.id;
            training.name = trainingStage.training.name;
            training.Color = trainingStage.training.Color;
            break;
        }
    }
}