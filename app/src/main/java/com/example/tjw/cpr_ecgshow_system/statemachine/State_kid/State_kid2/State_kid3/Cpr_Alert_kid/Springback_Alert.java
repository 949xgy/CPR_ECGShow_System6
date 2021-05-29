package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Springback_Alert extends State  implements statemachinettree {
    private Test_State_tree tree;

    @Override
    public void enter() {
        Log.d("Springback_Alert","enter the Springback Alert State");
//            tree.deferMessage(tree.obtainMessage(1323));
        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Springback_Alert","leave the Springback Alert State");

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 1323:
//                胸腔回弹失常方法：
                Log.d("Springback_Alert","胸腔回弹异常");
                tree.springBackAlert();
                rev = HANDLED;
                break;
            case 2323:
//                胸腔回弹正常方法：
                Log.d("Springback_Alert","胸腔回弹正常");
//                tree.springBackAlert();
                tree.deferMessage(msg);
                tree.transitionTo(tree.getSpringback_normal());
                rev = HANDLED;
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Springback_Alert","NOT_HANDLED"+msg.what);

                break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;

    }
}
