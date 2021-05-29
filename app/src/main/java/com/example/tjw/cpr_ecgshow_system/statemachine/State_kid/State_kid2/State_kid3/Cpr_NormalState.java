package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.EventType.ChestSpringBack;
import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class Cpr_NormalState extends State implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Cpr_NormalState","Cpr_NormalState.enter");

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Cpr_NormalState","Cpr_NormalState.exit");

        super.exit();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ChestSpringBack string){
        Log.d("Cpr_NormalState","利用Eventbus在state中接收消息成功");

    }
    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        Log.d("Cpr_NormalState","msg.what的值:"+msg.what);
        switch (msg.what){
            case 120:
                Log.d("Cpr_NormalState","一切正常状态:可以添加在正常状态下的表现方式");
//                在正常状态添加判断操作是否正常的逻辑
                rev = HANDLED;
                break;
            case 212:
                rev = HANDLED;
//              进入警告状态
                Log.d("Cpr_NormalState","进入警告状态");
                tree.transitionTo(tree.getAl1());
                break;
            case 2323:
                rev = HANDLED;
//                胸腔回弹正常
                tree.deferMessage(msg);
                tree.transitionTo(tree.getSpringback_normal());
                break;
            case 112:
//                按压深度正常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushdep_normal());
                break;
            case 1112:
                rev = HANDLED;
//                按压频率正常
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushfreq_normal());
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Cpr_NormalState","NOT_HANDLED"+msg.what);

                break;
        }
        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }
}
