package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Pushdep_Alert extends State  implements statemachinettree {
    private Test_State_tree tree;

    /**
     * 按压深度警告状态。该状态需要调用告知操作者加深或减缓按压深度的操作
     *
     * 需要根据what的值判断该执行什么操作
     */
    @Override
    public void enter() {
        Log.d("Pushdep_Alert","enter the Pushdepth Alert State");

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Pushdep_Alert","leave the Pushdepth Alert State");

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 123:
                rev = HANDLED;
//                按压深度过浅
                Log.d("Pushdep_Alert","按压深度过浅");

                tree.pushdepTobot();
                break;
            case 1123:
                rev = HANDLED;
//                按压深度过深
                Log.d("Pushdep_Alert","按压深度过深");

                tree.pushdepTotop();
            break;
                default:
                    rev = NOT_HANDLED;
                    Log.d("Pushdep_Alert","NOT_HANDLED"+msg.what);

                    break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;

    }
}
