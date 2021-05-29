package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class Pushdep_normal extends State  implements statemachinettree {
    private Test_State_tree tree;

    /**
     * 正常状态，只要显示正常数据就行？
     * 同时可以显示，当前操作的正确规范
     *
     */
    @Override
    public void enter() {
        Log.d("Pushdep_normal","enter the Pushdepth normal State");

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("Pushdep_normal","leave the Pushdepth normal State");

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 112:
//                调用按压深度正常方法
                Log.d("Pushdep_normal","当前按压正常深度");

                tree.pushdepNormal();
                rev = HANDLED;
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("Pushdep_normal","NOT_HANDLED"+msg.what);

                break;
        }

        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;

    }
}
