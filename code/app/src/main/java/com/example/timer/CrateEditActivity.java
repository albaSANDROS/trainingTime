package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.divyanshu.colorseekbar.ColorSeekBar;
import com.example.timer.ViewModel.CreateEditViewModel;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;

public class CrateEditActivity extends AppCompatActivity {

    private AppDatabase db;
    private CreateEditViewModel viewModel;

    Button btnPrepPlus;
    Button btnPrepMinus;
    Button btnWorkPlus;
    Button btnWorkMinus;
    Button btnRestPlus;
    Button btnRestMinus;
    Button btnCyclePlus;
    Button btnCycleMinus;
    Button btnSetPlus;
    Button btnSetMinus;
    Button btnCalmPlus;
    Button btnCalmMinus;

    EditText inputName;
    EditText inputPrep;
    EditText inputWork;
    EditText inputRest;
    EditText inputCycle;
    EditText inputSet;
    EditText inputCalm;

    ColorSeekBar bar;

    Training training;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crate);

        viewModel = ViewModelProviders.of(this).get(CreateEditViewModel.class);
        db = App.getInstance().getDatabase();
        FindControls();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int[] id = (int[])bundle.get("trainingId");

        if(id[1] == 1){
            training = db.trainingDao().getById(id[0]);
            initInputs(training);
        }

        viewModel.getName().observe(this, val -> inputName.setText(val));
        viewModel.getPreparationTime().observe(this, val -> inputPrep.setText(val.toString()));
        viewModel.getWorkTime().observe(this, val -> inputWork.setText(val.toString()));
        viewModel.getRestTime().observe(this, val -> inputRest.setText(val.toString()));
        viewModel.getCycles().observe(this, val -> inputCycle.setText(val.toString()));
        viewModel.getSets().observe(this, val -> inputSet.setText(val.toString()));
        viewModel.getCalm().observe(this, val -> inputCalm.setText(val.toString()));
        btnPrepPlus.setOnClickListener(i -> viewModel.setIncrementPreparationTime());
        btnPrepMinus.setOnClickListener(i -> viewModel.setDecrementPreparationTime());

        btnWorkPlus.setOnClickListener(i -> viewModel.setIncrementWorkTime());
        btnWorkMinus.setOnClickListener(i -> viewModel.setDecrementWorkTime());

        btnRestPlus.setOnClickListener(i -> viewModel.setIncrementRestTime());
        btnRestMinus.setOnClickListener(i -> viewModel.setDecrementRestTime());

        btnCyclePlus.setOnClickListener(i -> viewModel.setIncrementCycle());
        btnCycleMinus.setOnClickListener(i -> viewModel.setDecrementCycle());

        btnSetPlus.setOnClickListener(i -> viewModel.setIncrementSets());
        btnSetMinus.setOnClickListener(i -> viewModel.setDecrementSets());

        btnCalmPlus.setOnClickListener(i -> viewModel.setIncrementCalm());
        btnCalmMinus.setOnClickListener(i -> viewModel.setDecrementCalm());

        inputName.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                viewModel.setName(inputName.getText().toString());
                if(keyCode == 4)
                {
                    Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(backIntent);
                    finish();
                    return true;
                }
                return true;
            }
            return false;
        });



        findViewById(R.id.btnCancel).setOnClickListener(i -> {
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });

        findViewById(R.id.submit).setOnClickListener(i -> {
            if (id[1] != 1) {
                Training training = new Training();
                training.Name = inputName.getText().toString();
                training.PreparationTime = Integer.parseInt(inputPrep.getText().toString());
                training.WorkTime = Integer.parseInt(inputWork.getText().toString());
                training.RestTime = Integer.parseInt(inputRest.getText().toString());
                training.Cycles = Integer.parseInt(inputCycle.getText().toString());
                training.Sets = Integer.parseInt(inputSet.getText().toString());
                training.Calm = Integer.parseInt(inputCalm.getText().toString());
                training.Color = bar.getColor();
                db.trainingDao().insert(training);
            }
            else {
                training.Name = inputName.getText().toString();
                training.PreparationTime = Integer.parseInt(inputPrep.getText().toString());
                training.WorkTime = Integer.parseInt(inputWork.getText().toString());
                training.RestTime = Integer.parseInt(inputRest.getText().toString());
                training.Cycles = Integer.parseInt(inputCycle.getText().toString());
                training.Sets = Integer.parseInt(inputSet.getText().toString());
                training.Calm = Integer.parseInt(inputCalm.getText().toString());
                training.Color = bar.getColor();
                db.trainingDao().update(training);
            }
            Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backIntent);
            finish();
        });
    }

    private void initInputs(Training training){
        viewModel.setName(training.Name);
        viewModel.setPrep(training.PreparationTime);
        viewModel.setWork(training.WorkTime);
        viewModel.setRest(training.RestTime);
        viewModel.setCycle(training.Cycles);
        viewModel.setSets(training.Sets);
        viewModel.setCalm(training.Calm);
    }

    private void FindControls(){
        btnPrepPlus = findViewById(R.id.btnPrepPlus);
        btnPrepMinus = findViewById(R.id.btnPrepMinus);
        btnWorkPlus = findViewById(R.id.btnWorkPlus);
        btnWorkMinus = findViewById(R.id.btnWorkMinus);
        btnRestPlus = findViewById(R.id.btnRestPlus);
        btnRestMinus = findViewById(R.id.btnRestMinus);
        btnCyclePlus = findViewById(R.id.btnCyclePlus);
        btnCycleMinus = findViewById(R.id.btnCycleMinus);
        btnSetPlus = findViewById(R.id.btnSetPlus);
        btnSetMinus = findViewById(R.id.btnSetMinus);
        btnCalmPlus = findViewById(R.id.btnCalmPlus);
        btnCalmMinus = findViewById(R.id.btnCalmMinus);

        inputName = findViewById(R.id.inputName);
        inputPrep = findViewById(R.id.inputPrep);
        inputWork = findViewById(R.id.inputWork);
        inputRest = findViewById(R.id.inputRest);
        inputCycle = findViewById(R.id.inputCycle);
        inputSet = findViewById(R.id.inputSet);
        inputCalm = findViewById(R.id.inputCalm);

        bar = findViewById(R.id.color_seek_bar);
    }



}