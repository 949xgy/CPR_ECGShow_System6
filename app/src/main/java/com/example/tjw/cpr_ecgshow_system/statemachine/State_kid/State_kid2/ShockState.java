package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

//import State;

public class ShockState extends State implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("ShockState","ShockState.enter");
//        执行tipShock方法
        tree.tipShock();
//        已经进入电击状态，关闭可电击监测
        tree.closeCheckhread();
//        开启电击操作是否完成的服务
        tree.openCompleteshock();
        super.enter();
    }

    @Override
    public void exit() {
        Log.d("ShockState","ShockState.exit");

//        离开电击状态，开启两分钟的计时器
        tree.openHreadshockable();
//        关闭电击操作是否完成的服务
        tree.closeCompleteshock();

        super.exit();
    }

/**
 * 在完成电击后自动进入CPR状态
 *进入电击状态需要判断是否为可电击心率，所以需要对心率进行判断
 *
 */

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 32:
                Log.d("ShockState","进行电击状态");
//                调用电击心率判断逻辑，对是否进行点击进行判断，该判断逻辑在MainActivity中执行
                rev = HANDLED;
                break;
            case 12:
                rev = HANDLED;
//              进入CPR状态
                Log.d("ShockState","进入CPR状态");

                tree.deferMessage(tree.obtainMessage(12));
//                电击操作完成后跳转到cpr状态
                tree.transitionTo(tree.getC1());
                break;
            case 22:
                rev = HANDLED;
//                此时不执行任何操作，需要等到电击操作完成后才能切换到注射状态
                Log.d("ShockState","此时在电击状态下接受到跳转到注射状态的指令");
                break;
            default:
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
