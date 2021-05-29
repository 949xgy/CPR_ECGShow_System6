package com.example.tjw.cpr_ecgshow_system.ServiceBag;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.example.tjw.cpr_ecgshow_system.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * 该类模拟心电传感器发送数据
 */
public class SendECGService extends Service {

    private ArrayList<Float> ECGDataList=new ArrayList<>();//保存读取到的V5导联心电数据
    private ArrayList<Float> ECGDataList1=new ArrayList<>();//保存读取到II导联的心电数据
    private ReadThread readThread=new ReadThread();//读取数据的线程
    private boolean isTrue=true;
    private int i=0,j=0;
    Thread thread_ECG;//发送数据的线程

    private Socket socket=null;

    private void linkSocket() {
        thread_ECG = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    if (socket != null) {
                        socket.close();
                        socket = null;
                    }
                    Log.d("数据客户端启动","start....");
                    socket = new Socket("127.0.0.1", 5556);

                    //将socket对   Log.d("客户端连接","link...");应的输出流封装成打印流
                    PrintWriter out=new PrintWriter(socket.getOutputStream());
                    while (isTrue) {
                        //Gson用来对java对象和json字符串进行转化
                        Gson gson = new Gson();
                        if(ECGDataList.size()>1||ECGDataList1.size()>1){
                            Float ecg=ECGDataList.get(i);
                            Float ecg1=ECGDataList1.get(j);
                            String ecgStr=ecg.toString();
                            String ecgStr1=ecg1.toString();
                            i++;
                            j++;
                            if(i==ECGDataList.size()-1){
                                i=0;
                            }
                            if(j==ECGDataList1.size()-1){
                                j=0;
                            }
                            HashMap<String,String> ECGMap=new HashMap<>();
                            ECGMap.put("ECG",ecgStr);
                            ECGMap.put("ECG2",ecgStr1);
                            String json = gson.toJson(ECGMap);
                            Log.d("客户端","数据："+json);
                            //将json数据存到out流里面
                            out.println(json);
                            //清空缓冲区数据
                            out.flush();

                        }

                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//
                    }
                    if(!isTrue){
                        out.close();
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        };
        //启动发数据的线程
        thread_ECG.start();
    }
    //从raw的ECGData文件中读取
    class ReadThread extends Thread{//读取数据的线程
        public ReadThread(){}
        public void run(){
            try {
                InputStream is=getResources().openRawResource(R.raw.data1);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
                String s;
                while ((s=bufferedReader.readLine())!=null) {
                    ArrayList<String> list=new ArrayList<>(Arrays.asList(s.trim().split("\\s+")));
                    String str[]=new String[3];
                    list.toArray(str);
                    ECGDataList.add(Float.parseFloat(str[2]));
                    ECGDataList1.add(Float.parseFloat(str[1]));
                }
                System.out.println("读取数据完毕");

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("读取数据失败");
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        readThread.start();
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
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
