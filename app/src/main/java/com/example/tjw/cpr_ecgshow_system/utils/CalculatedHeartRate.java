package com.example.tjw.cpr_ecgshow_system.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
//通过RR期间计算心率的类
public class CalculatedHeartRate {
    //计算平均心率
    public static int xinlv(List<Float> ECGCacheData) {

        ArrayList<Integer> ECGDataIndexs =new ArrayList<>();//记录心电图R波的下标值数组列表
        ArrayList<Integer> ECGDataNums=new ArrayList<>();//记录RR期间的数据点数

        Integer ECGDataIndex = null;//记录心电图R波的下标值
        Float ECGDataMax = null;//心电图极大值
        boolean flag = false;
        int RRavg;//RR期间数据点数的平均值
        int sum=0;
        int value=0;

        for (int i=1;i<ECGCacheData.size()-1;i++) {
            if (ECGCacheData.get(i)>=0.4) {//设定阈值为0.4
                flag = true;
                //找极大值点
                if (ECGCacheData.get(i)>=ECGCacheData.get(i-1) && ECGCacheData.get(i)>=ECGCacheData.get(i+1)) {
                    if (ECGDataMax==null || ECGCacheData.get(i)>ECGDataMax) {
                        ECGDataMax = ECGCacheData.get(i);
                        ECGDataIndex = i;
                    }
                }
            } else {
                if (flag) {
                    //将极大值点保存在ECGDataIndexs中
                    ECGDataIndexs.add(ECGDataIndex);
                    flag = false;
                    ECGDataIndex = null;
                    ECGDataMax = null;
                }
            }
        }
        //判断是否存在两个R波以上
        if(ECGDataIndexs.size()>=2){
            System.out.println("第一个R值的下标"+ECGDataIndexs.get(0)+"第二个R值的下标"+ECGDataIndexs.get(1)+"最后一个R值下标"+ECGDataIndexs.get(ECGDataIndexs.size()-1));
            //       System.out.println(ECGDataIndexs.size());
            for(int i=1;i<ECGDataIndexs.size();i++){
                //计算任意两R波之间的数据点
                ECGDataNums.add(ECGDataIndexs.get(i)-ECGDataIndexs.get(i-1));
            }

            for(int i=0;i<ECGDataNums.size();i++){
                //计算所有R波间距的总和
                sum+=ECGDataNums.get(i);
            }
            try{
                //计算R波的平均间隔数据点
                RRavg=sum/ECGDataNums.size();
                //  System.out.println("平均RR期间的数据点个数"+RRavg);
                //根据公式计算这段时间的平均心率
                value=(int)(60*1000/(RRavg*2.77)+0.5);
            }catch (Exception e){
                e.printStackTrace();
            }
            Log.d("DrawToView","心率值为"+value);
        }
        return value;
    }

}
