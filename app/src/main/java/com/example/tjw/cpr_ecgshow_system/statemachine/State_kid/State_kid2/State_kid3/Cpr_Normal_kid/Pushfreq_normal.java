package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Pushfreq_normal extends State  implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Pushfreq_normal","enter the Pushfrequest normal State");

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Pushfreq_normal","leave the Pushfrequest normal State");

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 1112:
//                按压频率正常方法
                Log.d("Pushfreq_normal","当前按压频率正常");
                tree.pushfreqNormal();
                rev = HANDLED;
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Pushfreq_normal","NOT_HANDLED"+msg.what);
                break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;

    }
}
