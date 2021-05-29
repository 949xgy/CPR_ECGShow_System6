package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

//这个是一个状态的类，是最开始的等待状态的类，与active状态相互切换
public class WaitState extends State implements statemachinettree {
    private Test_State_tree tree;
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }

    @Override
    public void enter() {
        Log.d("wait","进入等待状态");
//        tree.stopAllService();
//        停止所有数据的监测
        super.enter();
    }

    @Override
    public void exit() {
        Log.d("wait","离开等待状态");
        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
//        当前状态处理消息
        boolean rev;
        switch (msg.what){
            case 1:
                Log.d("wait","msg.what=1,进行等待状态操作");
                tree.stopAllService();
                rev = HANDLED;
                break;
            case 2:
                rev = HANDLED;
                tree.sendMessage(tree.obtainMessage(2));
//                Log.d("waitState","222");
                tree.transitionTo(tree.getA1());
                break;
                default:
                    rev = NOT_HANDLED;
                    break;
        }
        return rev;
    }
}
