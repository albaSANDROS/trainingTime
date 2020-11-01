package com.example.timer.Services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class TimerService extends Service {

    MyBinder binder = new MyBinder();
    public static String STR_RECEIVER = "com.example.timer.Services";
    Timer timer;
    static TimerTask tTask;
    Intent intent;
    ArrayList<Integer> times;
    ArrayList<String> parts;
    int counter = 0;
    static int id;
    public static boolean flag = false;

    public void onCreate() {
        super.onCreate();
        intent = new Intent(STR_RECEIVER);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (id != (int) intent.getExtras().get("serviceId")) {
            if (tTask != null) {
                tTask.cancel();
                tTask = null;
                intent.putExtra("countdown", new String[]{"Время с отсчётом"
                        , "Название этапа"});
                sendBroadcast(intent);
            }
        }

        return super.onStartCommand(intent, flags, startId);
    }

    public void shedule(int _count){
        counter = _count;
        timer = new Timer();
        if (tTask != null) tTask.cancel();
        tTask = new TimerTask() {
            public void run() {
                int size = times.size() - 1;
                if (times.get(counter) < 0) {
                    if (counter < size) {
                        counter++;
                    } else {
                        timer.cancel();
                        timer = null;
                    }
                }
                int value = times.get(counter);
                String temp = Integer.toString(value);
                intent.putExtra("countdown", new String[]{temp, parts.get(counter)});
                sendBroadcast(intent);
                if (counter < size) {
                    value--;
                } else {
                    intent.putExtra("countdown",
                            new String[]{"Тренировка завершена", parts.get(size)});
                    sendBroadcast(intent);
                    flag = true;
                    timer.cancel();
                    timer = null;
                }
                times.set(counter, value);
            }
        };
        timer.schedule(tTask, 0, 1000);
    }

    public void schedule() {
         shedule(counter);
    }

    public void stopTimer()
    {
        if(timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public IBinder onBind(Intent arg0) {
        return binder;
    }

    public void Init(ArrayList<Integer> _times, ArrayList<String> _parts, int _id) {
        id = _id;
        times = _times;
        parts = _parts;
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
