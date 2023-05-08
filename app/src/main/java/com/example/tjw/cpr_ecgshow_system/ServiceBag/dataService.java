package com.example.tjw.cpr_ecgshow_system.ServiceBag;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


import com.example.tjw.cpr_ecgshow_system.Tools.AppGlobalResource;
import com.google.gson.Gson;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


final
/**
 * 心率传感器
 * 心室颤动进行电击
 * 所有传感器数据提取
 */
public class dataService extends Service
{
//    心电图传感器
//    private HreatData hreatData = new HreatData();
    private int seed = (int) System.currentTimeMillis();
    private boolean isTrue=true;
    private Random random = new Random(seed);
    String str;
    final private int TIMEDATA = 2000;
    Thread thread_hreat;
    //    套接字
    private ServerSocket serverSocket = null;
    //    连接状态
    private boolean socketStatus = false;
    private Socket socket=null;

    httpData http_data =new httpData();


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
                    Log.d("dataclinet","start..");
                    socket = new Socket("127.0.0.1", 5556);
                    Log.d("clinet","link..");
//                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out=new PrintWriter(socket.getOutputStream());



                    while (isTrue) {
//                        float[][] floatString = getData("http://192.168.0.100:8000/get_device_data","C9");
//                        System.out.println("++++++++++++++++++++++>>>>>>>>>>>>>>>>");
//                        System.out.println(floatString[0][0]);
//                        System.out.println("++++++++++++++++++++++>>>>>>>>>>>>>>>>");
//                        接收
                        //读取服务器端数据
                        Gson gson = new Gson();
                        HashMap<String,String> alldata = getMap();

 //                       alldata.put("PH",String.valueOf(floatString[floatString.length-1][floatString[floatString.length-1].length-1]));
                        String json = gson.toJson(alldata);
                        Log.d("客户端","数据："+json);

                        out.println(json);
                        out.flush();
                        try {
                            Thread.sleep(1000);
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
//                    考虑增加一个参数，用以识别数据的类别
            }
        };
        thread_hreat.start();
    }
    public HashMap<String,String> getMap(){
        HashMap map = new HashMap<String,String>();
        map.put("SPO2",getSPO2());
        map.put("BP",getBP());
        map.put("PaO2",getPaO2());
        map.put("PaCO2",getPaCO2());
        map.put("PH",getPH());
        map.put("HR",getHreatData());
        map.put("T",getT());

        return map;
    }



    public String getHreatData(){
//        int data;
//        int ss = (int) (random.nextFloat()*100);
//        if(ss<15){
//            data = (int) (random.nextFloat()*60+10);
//        }else if(ss>88){
//            data = (int) (random.nextFloat()*100+120);
//        }else{
//            data = (int) (random.nextFloat()*40+60);
//        }
        //C7 血氧传感器 第二个参数 表示 心率
        float[][] floatString = http_data.getData("http://"+ AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C7");
        int data = (int)floatString[floatString.length-1][1];

        System.out.println("+++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>+++++++++++++");
         System.out.println(data+"测试HR");
         System.out.println("+++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>+++++++++++++");

        String str = String.valueOf(data);

        return str;
    }

    public String getBP() {
//        int data1 = random.nextInt(80)+70;    //舒张压
//        int data2 = random.nextInt(60)+40;    //收缩压
//        String str = String.valueOf(data1)+"/"+data2;
        //C7 血氧
        float[][] floatString = http_data.getData("http://"+AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C0");


         System.out.println("+++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>+++++++++++++");
         System.out.println(floatString.length+"测试4");
         System.out.println("+++++++>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>+++++++++++++");

       String data1 = String.valueOf((int) floatString[floatString.length - 1][0]);
       String data2 = String.valueOf((int) floatString[floatString.length - 1][1]);
        String str = data1+"/"+data2;

        return str;

    }

    public String getPaO2() {
//        int data = random.nextInt(40)+70;
//        String str = String.valueOf(data);
//        return str;
        int PaO2;
        float[][] floatString = http_data.getData("http://"+AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C7");
        int data = (int)floatString[floatString.length-1][0];
        if (data>=90&&data<=100){
            PaO2 = (int)(5.5*data-444);
        }else {
            PaO2 = (int)(0.8*data-18);
        }
        String str = String.valueOf(PaO2);
        return  str;


    }
    @Test
    public void testFucn() {
        System.out.println(getPaCO2());
    }

    public String getPaCO2() {

         str = "88";
          return  str;

    }
    @Test
    public void testT(){
        getHreatData();
        System.out.println("----------------------->>");
        getT();
    }

    public String getT() {
        // 获取体温数据

         float[][] floatString = http_data.getData("http://"+AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C9");

         str = String.format("%.2f",floatString[floatString.length - 1][floatString[floatString.length - 1].length - 1]);

         System.out.println(str);

          return  str;

    }


    public String getPH() {
        //                        （7.35--7.45)
        float data = random.nextFloat()*1+7;
        String str = String.format("%.2f", data);
        return str;
    }
    public String getSPO2() {
//               95%或更高。 单位为%
        float[][] floatString = http_data.getData("http://"+AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C7");
        int data = (int)floatString[floatString.length-1][0];

        String str = String.valueOf(data);
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
//
//        thread_hreat.interrupt();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
//        return mMessenger.getBinder();
    }

    @Test
    public void Test00(){
        String str = getInputStream("https://www.baidu.com");
        System.out.println(str);
    }

public static String getInputStream(String Url_Path) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        StringBuilder receivedContent = null;
        try {
            URL url = new URL(Url_Path);

            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(2000);
                httpURLConnection.setDoInput(true);
                // 表示设置本次http请求使用GET方式请求
                httpURLConnection.setRequestMethod("GET");
                int responseCode = httpURLConnection.getResponseCode();
                if (responseCode == 200) {
                    // 从服务器获得一个输入流
                    inputStream = httpURLConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(inputStream, "UTF-8"));
                     receivedContent = new StringBuilder(1024);
                    String line = "";
                    while((line = reader.readLine()) != null) {
                        receivedContent.append(line);
                    }
                    reader.close();
                }
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (receivedContent==null){
            return "[[0.0,0.0]]";
        }


        return receivedContent.toString();
    }

    public float[][] getData(String Url_Path,String DeviceName){

        Gson gson = new Gson();
        String floatString = getInputStream(Url_Path+"/"+DeviceName);
        System.out.println("-------------------->>>>>>>>>>>>>>>>>>>>");
        System.out.println(floatString+"测试1");
        System.out.println("-------------------->>>>>>>>>>>>>>>>>>>>");



        float[][] floatFloat2 = gson.fromJson(floatString, float[][].class);



        if (floatFloat2.length==0){
            floatFloat2=new float[][]{{0,0,0}};
        }else {
            if (DeviceName=="C0"){
                if (floatFloat2[floatFloat2.length-1].length!=3){
                    floatFloat2=new float[][]{{0,0,0}};
                }
            }
        }


        return floatFloat2;
    }

    @Test
public void test2() {

        float[][] floatString = getData("http://"+AppGlobalResource.IP+":"+AppGlobalResource.PORT+"/get_device_data","C7");
        System.out.println(floatString[floatString.length-1][1]+"测试2");
    }




}
