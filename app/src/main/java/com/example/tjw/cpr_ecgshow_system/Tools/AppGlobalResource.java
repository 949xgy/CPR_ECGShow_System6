package com.example.tjw.cpr_ecgshow_system.Tools;

public class AppGlobalResource {
    // 树莓派地址
    // http://192.168.137.145:8000/
    //18812
    public static String IP = "192.168.137.145";
    // 树莓派端口
    public static String PORT = "8000";

    // 参数 arg : /xxx/xx
    public static String GETURL(String arg){
        return "http://" + IP + ":" + PORT + arg;
    }

//    // 打开各个传感器的代码
//    public static String CODE_RespiratorySensor = "0"; // 呼吸传感器代码
//    public static String CODE_TemperatureSensor = "1"; // 体温传感器
//    public static String CODE_BloodOxygenSensor = "2"; // 血氧传感器
//    public static String CODE_EcgSensor = "3"; // 心电传感器
//    public static String CODE_BloodPressureSensor = "4"; // 血压传感器
//    public static String CODE_MPU6050= "5"; // 为打开或唤醒MPU6050传感器


}
