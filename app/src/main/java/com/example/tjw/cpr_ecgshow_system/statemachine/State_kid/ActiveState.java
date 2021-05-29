package com.example.tjw.cpr_ecgshow_system.statemachine.State_kid;


import android.os.Message;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.statemachine.State;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.statemachine.statemachinettree;

public class ActiveState extends State implements statemachinettree {


    private Test_State_tree tree;
//    Intent intenthreat = new Intent(tree.getContext(),HeatService.class);
    @Override
    public void enter() {
        Log.d("active","active.enter");
        tree.startAllService();
//        开始对所有数据进行监测
//        tree.getMethod();

        super.enter();
    }

    @Override
    public void exit() {
        Log.d("active","active.exit");
        tree.stopAllService();

        super.exit();
    }

    @Override
    public boolean processMessage(Message msg) {
        boolean rev;
        switch (msg.what){
            case 2:
                rev = HANDLED;
                Log.d("activesatte","msg为12");
                tree.deferMessage(tree.obtainMessage(12));
                tree.transitionTo(tree.getC1());
                break;
            case 1:
                rev = HANDLED;
                if(this.tree!=null){
                    Log.d("ActiveState","跳转到wait状态");
                    tree.deferMessage(msg);
                    tree.transitionTo(tree.getW1());
                }
                break;
            case 12:
                rev = HANDLED;
//                Log.d("activesatte","开始实时监测数据");
//                tree.startAllService();
                tree.deferMessage(tree.obtainMessage(120));
                tree.transitionTo(tree.getC1());
                break;
            case 22:
                rev = HANDLED;
                Log.d("activesatte","进入inj注射提示状态");
//                tree.startAllService();
                tree.deferMessage(tree.obtainMessage(22));
                tree.transitionTo(tree.getI1());
                break;
            case 32:
                rev = HANDLED;
                Log.d("activesatte","进入电击提示状态");
//                tree.startAllService();
                tree.deferMessage(tree.obtainMessage(32));
                tree.transitionTo(tree.getS1());
                break;
            default:
                Log.d("ActiveState","default"+msg.what);
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
