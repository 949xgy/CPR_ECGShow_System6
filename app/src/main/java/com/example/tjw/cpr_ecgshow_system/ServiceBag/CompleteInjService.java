package com.example.tjw.cpr_ecgshow_system.ServiceBag;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;


import com.example.tjw.cpr_ecgshow_system.EventType.Completeinj;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class CompleteInjService extends Service {
    private int seed = (int) System.currentTimeMillis();

    private Random random = new Random(seed);
    private Completeinj completeinj = new Completeinj();
//    private boolean isTrue=false;
    private Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 随机数
            int data = random.nextInt(100);
            Log.d("CompleteInjService","注射操作随机数为："+String.valueOf(data));
            if(data<=10){
                completeinj.setCompleteinj(true);
                EventBus.getDefault().post(completeinj);
                handler.removeCallbacks(runnable);
            }else{
                handler.postDelayed(runnable,1000);
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        handler.postDelayed(runnable,1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
//        关闭定时器
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
