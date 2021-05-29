package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class InjectionState extends State implements statemachinettree {
    private Test_State_tree tree;
    private int time=0;
    private boolean istrue=true;
    private Handler handlert = new Handler();
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            time++;
            Log.d("MainActivity_Handle","inj内部状态时间:"+time+"s");
            handlert.postDelayed(this, 1000);
        }
    };

    @Override
    public void enter() {
        Log.d("InjectionState","InjectionState.enter");
//         停止注射计时服务
        tree.stopenterinj();
//        开启接收注射状态方法，开启对注射状态的监测
        tree.openCompleteinj();
//        每当进入到inj注射提示状态时，就运行提示方法
        tree.tipInjection();
        super.enter();
    }
    @Override
    public void exit() {
        Log.d("InjectionState","InjectionState.exit");
//        开启注射计时服务
        tree.startenterinj();
//        关闭接收注射状态的方法，关闭监测注射完成状态的方法
        tree.closeCompleteinj();
        super.exit();
    }

/*
*注射完成可以接受来自mainactivity的特定消息，自己本身可以进行一个30s的延时操作，后自动跳转到cpr状态
* 在注射完成后自动进入CPR状态
*同时该过程需要运用周期线程
*
* */

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){

            case 22:
                rev = HANDLED;
                break;

//

            default:
                Log.d("injectionState","NOT_HANDLED"+msg.what);
                rev = NOT_HANDLED;
                break;
        }
        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }
}
