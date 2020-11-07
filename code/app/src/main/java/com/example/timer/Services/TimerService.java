package com.example.timer.Services;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

import com.example.timer.Models.Stage;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    MyBinder binder = new MyBinder();
    public static String STR_RECEIVER = "com.example.timer.Services";
    Timer timer;

//    TimerTask tTask;
    Intent intent;
    ArrayList<Stage> stages = new ArrayList<>();

    int counter = 0;
    static long id;
    static TimerTask tTask;

    public void onCreate() {
        super.onCreate();
        intent = new Intent(STR_RECEIVER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (id != (long) intent.getExtras().get("serviceId")) {
            if (tTask != null) {
                tTask.cancel();
                tTask = null;
                intent.putExtra("countdown", new String[]{"Время с отсчётом"
                        , "Название этапа", String.valueOf(-1)});
                sendBroadcast(intent);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void schedule(int _count) {
        counter = _count;
        timer = new Timer();
        int size = stages.size();
        if (tTask != null) tTask.cancel();
        tTask = new TimerTask() {
            public void run() {
                int time = stages.get(counter).stageTime;
                String name = stages.get(counter).stageName;
                intent.putExtra("countdown", new String[]{Integer.toString(time)
                        , name
                        , String.valueOf(counter)});
                sendBroadcast(intent);
                if (time <= 3) {
                    Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
                    r.play();
                }
                if (counter < size - 1 && time == 0) {
                    counter++;
                } else if (counter == size - 1) {
                    intent.putExtra("countdown", new String[]{Integer.toString(0)
                            , "Тренировка закончена"
                            , String.valueOf(counter)});
                    sendBroadcast(intent);
                    timer.cancel();
                    timer = null;
                }
                if (time != 0) {
                    time--;
                    Stage tempStage = new Stage();
                    tempStage.stageName = name;
                    tempStage.stageTime = time;
                    stages.set(counter, tempStage);
                }
            }
        };
        timer.schedule(tTask, 0, 1000);
    }

    public void schedule() {
        schedule(counter);
    }

    public void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public void Init(ArrayList<Stage> _stages, long _id) {
        stages.clear();
        stages = _stages;
        Stage addStage = new Stage();
        addStage.stageName = "";
        addStage.stageTime = 0;
        stages.add(addStage);
        counter = 0;
        id = _id;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public TimerService getService() {
            return TimerService.this;
        }
    }
}
