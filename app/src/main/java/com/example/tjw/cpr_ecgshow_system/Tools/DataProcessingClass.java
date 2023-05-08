package com.example.tjw.cpr_ecgshow_system.Tools;

import com.example.tjw.cpr_ecgshow_system.ServiceBag.httpData;
import com.example.tjw.cpr_ecgshow_system.Types.ReturnData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
/*
    树莓派端数据处理类
    本类所有方法均未做多线程处理。
 */
public class DataProcessingClass {
    // 打开设备操作
    // 参数: 设备ID
    // 返回值 ReturnData
    public static ReturnData OpenDevice(Device deviceId)  {

        String res;
        try {
            res = httpData.getInputStream(AppGlobalResource.GETURL("/open_device/") + deviceId.ordinal());
            System.out.println("url :" +AppGlobalResource.GETURL("/open_device/")+deviceId.ordinal());
            System.out.println("result : " + res);
        }catch (Exception e){
            System.out.println(e.toString());
            return new ReturnData(null,e.toString(),0);
        }
        try{
            JSONObject jsonObject = new JSONObject(res);
            ReturnData data = new ReturnData(jsonObject.getJSONArray("data"),jsonObject.getString("msg"),jsonObject.getInt("state"));
            return data;
        }catch (Exception e){
            return new ReturnData(null,e.toString(),0);
        }
    }
    // 关闭设备操作
    // 参数: 设备ID
    // 返回值 ReturnData
    public static ReturnData CloseDevice(Device deviceId){
        String res =  httpData.getInputStream(AppGlobalResource.GETURL("/close_device/") + deviceId.ordinal());
        //{"data": [0],"msg": "open device successful","state": 1}
        // 解析Json数据
        try{
            JSONObject jsonObject = new JSONObject(res);
            ReturnData data = new ReturnData(jsonObject.getJSONArray("data"),jsonObject.getString("msg"),jsonObject.getInt("state"));
            return data;
        }catch (Exception e){
            return null;
        }
    }

    // 获取设备数据
    public static ReturnData GetDeviceData(Device deviceId){
        String res =  httpData.getInputStream(AppGlobalResource.GETURL("/get_device_data/") + deviceId.ordinal());
        System.out.println("result : " + res);
        // 解析Json数据
        try{
            JSONObject jsonObject = new JSONObject(res);
            JSONArray data1 = jsonObject.getJSONArray("data");
            JSONArray data2 = new JSONArray();

            for (int i = 0; i < data1.length(); i++) {
                data2.put(data1.getJSONArray(i).get(0));
            }

            //System.out.println(data2.toString());
            ReturnData data = new ReturnData(data2,jsonObject.has("msg") == true ? jsonObject.getString("msg") : "data",jsonObject.getInt("state"));
            return data;
        }catch (Exception e){
            return new ReturnData(null,"json解析失败," + e.toString() +"!",0);
        }

    }
    // 这个请求是针对于姿态传感器开发的http请求
    public static ReturnData GetDeviceData_Ex(Device deviceId){
        String res =  httpData.getInputStream(AppGlobalResource.GETURL("/get_device_data/") + deviceId.ordinal());
        System.out.println("result : " + res);
        // 解析Json数据
        try{
            JSONObject jsonObject = new JSONObject(res);
            JSONArray data1 = jsonObject.getJSONArray("data");
            ReturnData data = new ReturnData(data1,jsonObject.has("msg") == true ? jsonObject.getString("msg") : "data",jsonObject.getInt("state"));
            return data;
        }catch (Exception e){
            return new ReturnData(null,"json解析失败," + e.toString() +"!",0);
        }

    }
}
