package com.example.tjw.cpr_ecgshow_system.ServiceBag;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.*;


// 提供Http请求
public class httpData {

//    private static String URL_PATH = "http://192.168.12.1:8000/open_device/3&0.005";

    public httpData() {
        // TODO Auto-generated constructor stub
    }


    /**
     * 获得服务器端的数据,以InputStream形式返回
     * @return
     */
    public static String getInputStream(String Url_Path) {
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        StringBuilder receivedContent = null;
        try {
            URL url = new URL(Url_Path);

            if (url != null) {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                // 设置连接网络的超时时间
                httpURLConnection.setConnectTimeout(3000);
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
            return "{}";
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

    public int getData(String Url_Path){

        String String = getInputStream(Url_Path);
        System.out.println("-------------------->>>>>>>>>>>>>>>>>>>>");
        System.out.println(String+"测试1");
        System.out.println("-------------------->>>>>>>>>>>>>>>>>>>>");
        int value = Integer.parseInt(String);
        return value;
    }

}

