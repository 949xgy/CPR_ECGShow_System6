package com.example.tjw.cpr_ecgshow_system.Tools;


//    // 打开各个传感器的代码
//    public static String CODE_RespiratorySensor = "0"; // 呼吸传感器代码
//    public static String CODE_TemperatureSensor = "1"; // 体温传感器
//    public static String CODE_BloodOxygenSensor = "2"; // 血氧传感器
//    public static String CODE_EcgSensor = "3"; // 心电传感器
//    public static String CODE_BloodPressureSensor = "4"; // 血压传感器
//    public static String CODE_MPU6050= "5"; // 为打开或唤醒MPU6050传感器

public enum HandleWhatFlag {
    FLAG_RespiratorySensor_OK,//呼吸传感器_打开成功
    FLAG_RespiratorySensor_ERROR,//呼吸传感器打开失败
    FLAG_TemperatureSensor_OK,//体温传感器
    FLAG_TemperatureSensor_ERROR,//体温传感器
    FLAG_BloodOxygenSensor_OK,//血氧传感器
    FLAG_BloodOxygenSensor_ERROR,//血氧传感器
    FLAG_EcgSensor_OK,//心电传感器
    FLAG_EcgSensor_ERROR,//心电传感器
    FLAG_BloodPressureSensor_OK,//血压传感器
    FLAG_BloodPressureSensor_ERROR,//血压传感器
    FLAG_MPU6050_OK,//MPU6050传感器
    FLAG_MPU6050_ERROR,//MPU6050传感器
}
