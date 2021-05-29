package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2;

import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class CPRState extends State implements statemachinettree {
    private Test_State_tree tree;
    @Override
    public void enter() {
        Log.d("CPR","CPR.enter");
        tree.tipCPR();
        super.enter();
    }

    @Override
    public void exit() {
        Log.d("CPR","CPR.exit");

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 12:
                Log.d("CPRState","CPR状态");
                if(tree!=null){
//                    默认进入normal状态
                    tree.deferMessage(tree.obtainMessage(120));
                    tree.transitionTo(tree.getN1());

                }
                rev = HANDLED;
                break;
            case 22:rev = HANDLED;
//            进入注射状态
                tree.transitionTo(tree.getI1());
            break;
            case 32:rev = HANDLED;
//            进入电击状态
                tree.transitionTo(tree.getS1());
                break;

            case 112:
//                按压深度正常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushdep_normal());
                break;
            case 1112:
//                按压频率正常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushfreq_normal());
                break;
            case 123:
//                按压深度过浅
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushdep_alert());
                break;
            case 1123:
//                按压深度过深
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushdep_alert());
                break;
            case 1223:
//                按压频率过慢
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getPushfreq_alert());
                break;
            case 2223:
//                按压频率过快
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
            case 2323:
//                胸腔回弹正常
                rev = HANDLED;
                tree.deferMessage(msg);
                tree.transitionTo(tree.getSpringback_normal());
                break;
            default:
                rev = NOT_HANDLED;
                Log.d("CPRState","CPR状态NOT_HANDLED"+msg.what);

                break;
        }
        return rev;
    }

    @Override
    public void setTree(Test_State_tree tree) {
        this.tree = tree;
    }
}