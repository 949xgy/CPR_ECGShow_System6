package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Springback_normal extends State  implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Springback_normal","enter the springback normal State");
//        tree.deferMessage(tree.obtainMessage(2323));

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Springback_normal","leave the springback normal State");
        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 2323:
//                胸腔回弹正常
                Log.d("Springback_normal","胸腔回弹正常");
                tree.springBackNormal();
                rev = HANDLED;
                break;
            case 1323:
//                胸腔回弹异常
                Log.d("Springback_normal","胸腔回弹异常");
//                tree.springBackNormal();
                tree.deferMessage(msg);
                tree.transitionTo(tree.getSpringback_alert());
                rev = HANDLED;
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Springback_normal","NOT_HANDLED"+msg.what);

                break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }
}
