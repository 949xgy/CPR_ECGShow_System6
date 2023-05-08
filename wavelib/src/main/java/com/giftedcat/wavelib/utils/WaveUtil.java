package com.giftedcat.wavelib.utils;

import android.util.Log;

import com.giftedcat.wavelib.view.WaveView;

import java.util.ArrayList;
import java.util.List;

import java.util.Timer;
import java.util.TimerTask;
/**
 * @Description: 曲线绘制工具类
 * @Author: GiftedCat
 * @Date: 2021-01-01
 */
public class WaveUtil {

    private Timer timer;
    private TimerTask timerTask;

    float data = 0f;
    Queue q =new Queue();

    /**
     * 绘制数据到波形图
     */
    public void showWaveData(final WaveView waveShowView) throws InterruptedException {

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if(q.isEmpty()){
                    // 发起请求!
                    //DataProcessingClass
                }
                data = q.out();
                waveShowView.showLine((float) (data/50-15));//取得是-10到10间的浮点数
            }
        };
        //500表示调用schedule方法后等待500ms后调用run方法，50表示以后调用run方法的时间间隔
        timer.schedule(timerTask,500,100);

    }

    /**
     * 停止绘制
     */
    public void stop(){
        if(timer != null){
            timer.cancel();
            timer.purge();
            timer = null;
        }
        if(null != timerTask) {
            timerTask.cancel();
            timerTask = null;
        }
    }


}



//集合实现队列
class Queue{
    List<Integer> list = new ArrayList<Integer>();
    int index = 0;

    public void in(int n) {
        list.add(n);
        index++;
    }
    //出队列操作
    public boolean isEmpty(){
        return this.list.isEmpty();
    }
    //出队
    public int out(){
        if(!list.isEmpty()){
            index--;
            return list.remove(0);
        }
        return 0;
    }
}