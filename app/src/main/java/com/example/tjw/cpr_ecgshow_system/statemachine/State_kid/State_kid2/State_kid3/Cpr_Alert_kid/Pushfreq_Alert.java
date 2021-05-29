package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Pushfreq_Alert extends State implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Pushfreq_Alert","enter the Pushfrequest Alert State");

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Pushfreq_Alert","leave the Pushfrequest Alert State");

        super.exit();
}

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 1223:
                rev = HANDLED;
//                按压频率过慢
                Log.d("Pushfreq_Alert","当前按压频率过慢");
                tree.pushfreqToslow();
                break;
            case 2223:
//                按压频率过快
                Log.d("Pushfreq_Alert","当前按压频率过快");

                tree.pushfreqTofast();
                rev = HANDLED;
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Pushfreq_Alert","NOT_HANDLED"+msg.what);

                break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;

    }
}
