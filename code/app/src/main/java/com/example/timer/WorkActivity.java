package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Models.Training;
import com.example.timer.Services.TimerService;

import java.util.ArrayList;

public class WorkActivity extends AppCompatActivity {

    private AppDatabase db;

    TextView namePart;
    TextView timePart;
    ListView allParts;
    Button btnStart;
    Button btnStop;

    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> parts;
    ArrayList<Integer> times;

    boolean bound = false;
    boolean isPaused = false;
    ServiceConnection sConn;
    Intent intentService;
    TimerService timerService;
    int id;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);


        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = (int)bundle.get("trainingId");
        Training training = db.trainingDao().getById(id);
        CreateItemSequence(training);
        Init();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        allParts.setAdapter(adapter);

        intentService = new Intent(this, TimerService.class);
        sConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                timerService = ((TimerService.MyBinder) binder).getService();
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };

        TimerSequence(training);
        btnStart.setOnClickListener(i -> {
            if (!isPaused) {
                if (TimerService.flag) {
                    TimerSequence(training);
                    startService(intentService.putExtra("serviceId", id));
                }
                timerService.Init(times, parts, id);
            }
            timerService.schedule();
            isPaused = false;
        });

        btnStop.setOnClickListener(i -> {
            isPaused = true;
            timerService.stopTimer();
            stopService(new Intent(this, TimerService.class));
        });

        allParts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long _id) {
                TimerSequence(training);
                timerService.Init(times, parts, id);
                timerService.shedule(position);
            }
        });
    }

    public void Init(){
        namePart = findViewById(R.id.partName);
        timePart = findViewById(R.id.partTime);
        allParts = findViewById(R.id.allParts);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
    }


    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(TimerService.STR_RECEIVER));
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(intentService, sConn, 0);
        startService(new Intent(this, TimerService.class).putExtra("serviceId", id));
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    @Override
    protected void onStop() {
        if (!bound) return;
        unbindService(sConn);
        bound = false;
        super.onStop();
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, TimerService.class));
        super.onDestroy();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UpdateUI(intent);
        }
    };

    private void UpdateUI(Intent intent){
        if (intent.getExtras() != null) {
            String[] data = (String[]) intent.getExtras().get("countdown");
            timePart.setText(data[0]);
            namePart.setText(data[1]);
        }
    }

    private void TimerSequence(Training training){
        String[] names = {"Работа","Отдых","Подготовиться","Отдых между подходами","Финиш"};
        parts = new ArrayList<>();
        times = new ArrayList<>();
        parts.add(names[2]);
        times.add(training.PreparationTime);

        for(int i = 0; i < training.Sets; i++) {
            for (int j = 0; j < training.Cycles; j++) {
                parts.add(names[0]);
                parts.add(names[1]);
                times.add(training.WorkTime);
                times.add(training.RestTime);
            }
            parts.add(names[3]);
            times.add(training.Calm);
        }
        parts.add(names[4]);
        times.add(0);
    }

    private void CreateItemSequence(Training training){
        String[] names = {"Работа","Отдых","Подготовиться","Отдых между подходами","Финиш"};
        parts = new ArrayList<>();
        times = new ArrayList<>();
        items.add(names[2] + " : " + training.PreparationTime);
        parts.add(names[2]);
        times.add(training.PreparationTime);

        for(int i = 0; i < training.Sets; i++) {
            for (int j = 0; j < training.Cycles; j++) {
                items.add(names[0] + " : " + training.WorkTime);
                items.add(names[1] + " : " + training.RestTime);
                parts.add(names[0]);
                parts.add(names[1]);
                times.add(training.WorkTime);
                times.add(training.RestTime);
            }
            items.add(names[3] + " : " + training.Calm);
            parts.add(names[3]);
            times.add(training.Calm);
        }
        items.add(names[4]);
        parts.add(names[4]);
        times.add(0);
    }
}