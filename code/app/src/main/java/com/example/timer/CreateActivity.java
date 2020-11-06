package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
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

import java.util.ArrayList;

import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar;
import codes.side.andcolorpicker.model.IntegerHSLColor;

public class CreateActivity extends AppCompatActivity {

    ArrayList<Stage> stages = new ArrayList<>();
    Training training = new Training();
    AppDatabase db;
    ListView lst;
    HSLColorPickerSeekBar bar;
    EditText inputName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        db = App.getInstance().getDatabase();

        findControls();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        PartsAdapter adapter = new PartsAdapter(this, R.layout.list_stage_item, stages);
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
                IntegerHSLColor hslColor = bar.getPickedColor();
                training.Color = Color.HSVToColor(new float[]{hslColor.getFloatH(), hslColor.getFloatL()
                        , hslColor.getFloatS()});
                db.trainingDao().insert(training, stages);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (stages.size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Добавьте этапы тренировки", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Напишите название тренировки", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    ArrayList<String> names;


    private void findControls() {
        lst = findViewById(R.id.listStages);
        inputName = findViewById(R.id.inputName);
        bar = findViewById(R.id.hueSeekBar);
    }

    private void getAllNames() {
        names = null;
        names = new ArrayList<>();
        for (Stage stage : stages) {
            names.add(stage.stageName);
        }
    }


    public void setDataFromDialog(String partName, String partTime) {
        getAllNames();
        if (names.contains("Сеты") && partName.equals("Сеты")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Нельзя добавлять два этапа \"Сеты\"", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Stage stage = new Stage();
            stage.stageName = partName;
            stage.stageTime = Integer.parseInt(partTime);
            stages.add(stage);
        }
    }
}