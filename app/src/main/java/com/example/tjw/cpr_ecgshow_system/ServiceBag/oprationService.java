package com.example.tjw.cpr_ecgshow_system.ServiceBag;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

/**
 * 心率传感器
 * 心室颤动进行电击
 */
public class  oprationService extends Service
{
//    心电图传感器
//    private HreatData hreatData = new HreatData();
    private int seed = (int) System.currentTimeMillis();
    private boolean isTrue=true;
    private Random random = new Random(seed);
    private Thread thread_hreat;
    //    套接字
    private Socket socket=null;

    private void linkSocket() {
        thread_hreat = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }
                    Log.d("clinetopration","start..");
                    socket = new Socket("127.0.0.1", 5556);
                    Log.d("clinet","link..");

//                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream());
                    while (isTrue) {

//                        接收
                        //读取服务器端数据
                        Gson gson = new Gson();
                        HashMap<String,String> alldata = getMap();
                        String json = gson.toJson(alldata);
                        Log.d("客户端","数据："+json);
                        out.println(json);
                        out.flush();
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    if(!isTrue){
                        out.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                    考虑增加一个参数，用以识别数据的类别
            }
        };
        thread_hreat.start();
    }
    public HashMap<String,String> getMap(){
        HashMap map = new HashMap<String,String>();
        map.put("BACK",getChestSpring());
        map.put("DEP",getPushdep());
        map.put("FREQ",getPushfreq());
        return map;
    }

    public String getChestSpring(){
        String springback;
        float data = random.nextFloat()*10;
        if (data>5){
            springback = "true";
        }else {
            springback = "false";
        }

        return springback;
    }

    public String getPushdep() {
        float data = random.nextFloat()*40+30;
        String str =  String.format("%.2f", data);
        return str;
    }
    public String getPushfreq() {
        float data = random.nextFloat()*30+90;
        String str =  String.format("%.2f", data);
        return str;
    }

        @Override
    public void onCreate() {
        super.onCreate();
        linkSocket();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isTrue = true;
        return super.onStartCommand(intent, flags, startId);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        this.isTrue=false;
        thread_hreat.interrupt();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
//        return mMessenger.getBinder();
    }
}
