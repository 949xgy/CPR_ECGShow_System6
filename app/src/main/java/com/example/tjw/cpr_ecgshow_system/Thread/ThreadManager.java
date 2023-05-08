package com.example.tjw.cpr_ecgshow_system.Thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
    简单的线程管理
 */
public class ThreadManager {
    //private List<Thread> threads;
    private Map<String,Thread> threadMap;

    public ThreadManager(){
        this.threadMap =new HashMap<>();
    }

    public void addThread(String flag,Thread th){
        // 启动线程
        th.start();
        // 保存线程对象
        this.threadMap.put(flag,th);
    }

    public void stopThreadByName(String threadName){
        this.threadMap.get(threadName).interrupt();
        this.threadMap.remove(threadName);
    }

    public int getCurrentThreadNum(){
        return this.threadMap.size();
    }

    // 停止所有线程
    public void closeAllThread(){
        for (Map.Entry<String,Thread> entry : threadMap.entrySet()){
            entry.getValue().interrupt();
        }
    }

}
