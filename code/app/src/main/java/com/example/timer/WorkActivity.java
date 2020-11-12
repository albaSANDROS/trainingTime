package com.example.timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.PreferenceManager;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.timer.Data.AppDatabase;
import com.example.timer.Dialogs.AddStageDialog;
import com.example.timer.Dialogs.RepeatDialog;
import com.example.timer.Models.Stage;
import com.example.timer.Models.TrainingStages;
import com.example.timer.Services.TimerService;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity {

    private AppDatabase db;
    SharedPreferences sp;
    ArrayList<Stage> trainingLst = new ArrayList<>();

    TextView namePart;
    TextView timePart;
    ListView allParts;
    Button btnStart;
    Button btnStop;

    ArrayList<String> items = new ArrayList<>();

    boolean bound = false;
    boolean isPaused = false;
    ServiceConnection sConn;
    Intent intentService;
    TimerService timerService;
    long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        String size = sp.getString("font", "");
        String theme = sp.getString("theme",  "");
        if(size.equals("") || size.equals("false")) size = "17";
        if (theme.equals("") || theme.equals("false")) {
            setTheme(R.style.AppThemeDark);
        } else if(theme.equals("Тёмная") || theme.equals("Dark")){
            setTheme(R.style.AppThemeDark);
        }
        else {
            setTheme(R.style.AppThemeLight);
        }

        setContentView(R.layout.activity_work);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = App.getInstance().getDatabase();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = (long) bundle.get("trainingId");


        Init(size);
        getDataFromDb(id);
        getLstData();
        WorkAdapter adapter = new
                WorkAdapter(this, R.layout.work_list_item, items, size);
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

        btnStart.setOnClickListener(i -> {
            startTimer();
        });

        btnStop.setOnClickListener(i -> {
            isPaused = true;
            timerService.stopTimer();
        });

        allParts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long _id) {
                for (int i = 0; i < items.size(); i++) {
                    allParts.setItemChecked(i, false);
                    allParts.setSelected(true);
                }
                setChecked(position);

                getDataFromDb(id);
                timerService.Init(trainingLst);
                timerService.schedule(position);
            }
        });
    }

    public void startTimer(){
        if (!isPaused) {
            startService(intentService.putExtra("serviceId", id));
            getDataFromDb(id);
            timerService.Init(trainingLst);
            timerService.schedule();
        } else {
            timerService.schedule();
            isPaused = false;
        }
    }

    public void Init(String size) {
        namePart = findViewById(R.id.partName);
        timePart = findViewById(R.id.partTime);
        allParts = findViewById(R.id.allParts);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);

        namePart.setTextSize(Float.parseFloat(size));
        timePart.setTextSize(Float.parseFloat(size));
        btnStop.setTextSize(Float.parseFloat(size));
        btnStart.setTextSize(Float.parseFloat(size));
    }

    private void getLstData() {
        int sets = 1;
        ArrayList<String> tempItems = new ArrayList<>();
        List<TrainingStages> trainingStages = db.trainingDao().getTrainingStagesByTrainingId(id);
        for (TrainingStages trainingStage : trainingStages) {
            trainingLst = (ArrayList<Stage>) trainingStage.stages;
            for (Stage stage : trainingStage.stages) {
                if(trainingStages.get(0).training.locale.equals("en")){
                    if (!stage.stageName.equals("Sets")) {
                        tempItems.add(stage.stageName + ":" + Integer.toString(stage.stageTime));
                    } else {
                        sets = stage.stageTime;
                    }
                } else {
                    if (!stage.stageName.equals("Сеты")) {
                        tempItems.add(stage.stageName + ":" + Integer.toString(stage.stageTime));
                    } else {
                        sets = stage.stageTime;
                    }
                }
            }
            break;
        }
        for (int i = 0; i < sets; i++) {
            items.addAll(tempItems);
        }
    }

    private void getDataFromDb(long id) {
        int sets = 1;
        trainingLst.clear();
        List<TrainingStages> trainingStages = db.trainingDao().getTrainingStagesByTrainingId(id);
        ArrayList<Stage> tempStages = new ArrayList<>();
        for (TrainingStages trainingStage : trainingStages) {
            trainingLst = (ArrayList<Stage>) trainingStage.stages;
            for (Stage stage : trainingStage.stages) {
                if (!stage.stageName.equals("Сеты")) {
                    tempStages.add(stage);
                } else {
                    sets = stage.stageTime;
                }
            }
            break;
        }
        trainingLst.clear();
        for (int i = 0; i < sets; i++) {
            trainingLst.addAll(tempStages);
        }
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

    @Override
    public void onBackPressed() {
         timerService.stopTimerTask();
         super.onBackPressed();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            UpdateUI(intent);
        }
    };

    private void UpdateUI(Intent intent) {
        if (intent.getExtras() != null) {
            String[] data = (String[]) intent.getExtras().get("countdown");
            if(data[2].equals("FINISHED")){
                timePart.setText(data[0]);
                namePart.setText(getResources().getString(R.string.finishedTraining));
                RepeatDialog dialogFragment = new RepeatDialog();
                FragmentManager manager = getSupportFragmentManager();

                FragmentTransaction transaction = manager.beginTransaction();
                dialogFragment.show(transaction, "dialog");
            }
            else {
                timePart.setText(data[0]);
                namePart.setText(data[1]);
                setChecked(Integer.parseInt(data[2]));
            }
        }
    }

    private void setChecked(int position) {
        for (int i = 0; i < items.size(); i++) {
            if (i == position) {
                allParts.setItemChecked(position, true);
            } else {
                allParts.setItemChecked(i, false);
            }
            allParts.setSelected(true);
        }
    }

    class WorkAdapter extends ArrayAdapter<String> {
        private LayoutInflater inflater;
        private int layout;
        private ArrayList<String> stages;
        String size;

        public WorkAdapter(Context context, int resource, ArrayList<String> stages, String size) {
            super(context, resource, stages);
            this.stages = stages;
            this.layout = resource;
            this.inflater = LayoutInflater.from(context);
            this.size = size;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            WorkAdapter.ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(this.layout, parent, false);
                viewHolder = new WorkAdapter.ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (WorkAdapter.ViewHolder) convertView.getTag();
            }
            viewHolder.textNamePart.setText(stages.get(position));
            viewHolder.textNamePart.setTextSize(Float.parseFloat(size));
            if (allParts.isItemChecked(position)) {
                viewHolder.layout.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                notifyDataSetChanged();
            } else {
                if (sp.getString("theme", "Тёмная").equals("Тёмная")) {
                    viewHolder.layout.setBackgroundColor(getResources().getColor(R.color.darkBackground));
                }
                if (sp.getString("theme", "Светлая").equals("Светлая")) {
                    viewHolder.layout.setBackgroundColor(getResources().getColor(R.color.lightBackground));
                }

            }

            return convertView;
        }

        class ViewHolder {
            public TextView textNamePart;
            public LinearLayout layout;

            ViewHolder(View view) {
                textNamePart = view.findViewById(R.id.textNamePart);
                layout = view.findViewById(R.id.item_container);
            }
        }
    }
}