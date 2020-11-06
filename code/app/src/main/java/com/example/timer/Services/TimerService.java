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
    //static TimerTask tTask;
    TimerTask tTask;
    Intent intent;
    ArrayList<Stage> stages = new ArrayList<>();

    int counter = 0;
    //static long id;
    public static boolean isFinished = false;

    public void onCreate() {
        super.onCreate();
        intent = new Intent(STR_RECEIVER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        if (id != (long) intent.getExtras().get("serviceId")) {
//            if (tTask != null) {
//                tTask.cancel();
//                tTask = null;
//                intent.putExtra("countdown", new String[]{"Время с отсчётом"
//                        , "Название этапа"});
//                sendBroadcast(intent);
//            }
//        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void schedule(int _count) {
        counter = _count;
        timer = new Timer();
        if (tTask != null) tTask.cancel();
        tTask = new TimerTask() {
            public void run() {
                int size = stages.size();
                if (stages.get(counter).stageTime < 0) {
                    if (counter < size) {
                        counter++;
                    } else {
                        timer.cancel();
                        timer = null;
                    }
                }
                int value = stages.get(counter).stageTime;
                String temp = Integer.toString(value);
                if (value <= 3) {
                    Uri notify = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notify);
                    r.play();
                }

                intent.putExtra("countdown", new String[]{temp, stages.get(counter).stageName
                        , String.valueOf(counter)});
                sendBroadcast(intent);
                if (counter < size) {
                    value--;
                } else {
                    intent.putExtra("countdown",
                            new String[]{"Тренировка завершена", "0", String.valueOf(size)});
                    sendBroadcast(intent);
                    isFinished = true;
                    timer.cancel();
                    timer = null;
                }
                Stage tempStage = new Stage();
                tempStage.stageName = stages.get(counter).stageName;
                tempStage.stageTime = value;
                stages.set(counter, tempStage);

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

    public void Init(ArrayList<Stage> _stages) {
        stages.clear();
        stages = _stages;
        counter = 0;
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
