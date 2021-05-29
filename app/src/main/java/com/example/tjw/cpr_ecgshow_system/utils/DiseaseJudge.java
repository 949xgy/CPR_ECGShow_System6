package com.example.tjw.cpr_ecgshow_system.utils;


import java.util.List;

//根据心电数据进行分析病症
public class DiseaseJudge {
    //停博判断  0为不是停博 1是停博
    public static int StopBo(List<Float> ECGList){
        Float value=0.4f;
        int flag=1;
        for (int i=0;i<ECGList.size();i++){
           if(ECGList.get(i)>value){
               flag=0;
           }
        }
        return flag;
    }
    //心动过速 0为正常 1为心动过缓 2 心动过速  3可能为心室颤动
    public static int HeartJudge(int heart){
        int flag=0;
        if(heart>=60&&heart<=100){
            flag=0;
        }
        if(heart<60){
            flag=1;
        }
        if(heart>100&&heart<150){
            flag=2;
        }
        if(heart>=150){
            flag=3;
        }
        return flag;
    }
}
