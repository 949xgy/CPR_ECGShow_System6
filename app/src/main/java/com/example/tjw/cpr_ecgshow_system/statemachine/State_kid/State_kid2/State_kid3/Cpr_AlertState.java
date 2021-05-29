package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Cpr_AlertState extends State implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Cpr_AlertState","Cpr_AlertState.enter");
        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Cpr_AlertState","Cpr_AlertState.exit");
        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        Log.d("Cpr_AlertState","msg.what的值:"+msg.what);
        boolean rev;
        switch (msg.what){
            case 212:
                rev = HANDLED;
                Log.d("Cpr_AlertState","警告状态");
                break;
            case 120:
//                可以添加在正常状态下的表现方式
                Log.d("Cpr_AlertState","进入一切正常状态");
                rev = HANDLED;
                break;
            case 123|1123:
//                按压深度异常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushdep_alert());
                break;
            case 1223|2223:
//                按压频率异常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushfreq_alert());
                break;
            case 1323:
//                胸腔回弹异常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getSpringback_alert());
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Cpr_AlertState","NOT_HANDLED"+msg.what);

                break;
        }
        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }
}
