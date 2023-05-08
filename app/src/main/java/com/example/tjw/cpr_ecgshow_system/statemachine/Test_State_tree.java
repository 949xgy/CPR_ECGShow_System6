package com.example.tjw.cpr_ecgshow_system.statemachine;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.ECGShowActivity;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.ActiveState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.CPRState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.InjectionState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.ShockState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_AlertState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid.Pushdep_Alert;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid.Pushfreq_Alert;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Alert_kid.Springback_Alert;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_NormalState;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid.Pushdep_normal;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid.Pushfreq_normal;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.State_kid2.State_kid3.Cpr_Normal_kid.Springback_normal;
import com.example.tjw.cpr_ecgshow_system.statemachine.State_kid.WaitState;

public class Test_State_tree extends StateMachine {
    private Context contxt;
    private ECGShowActivity activity;

    WaitState w1 = new WaitState();         //等待状态
    ActiveState a1 = new ActiveState();     //活动状态
    CPRState c1 = new CPRState();            //CPR状态
    InjectionState i1 = new InjectionState();   //注射状态
    ShockState s1 = new ShockState();           //电击状态
    Cpr_AlertState al1 = new Cpr_AlertState();  //警告状态
    Cpr_NormalState n1 = new Cpr_NormalState(); //正常状态
//    Inj_AlertState Inj_A1 = new Inj_AlertState();
//    Inj_NormalState InJ_N1 = new Inj_NormalState();
//    Shock_AlertState Sh_A1 = new Shock_AlertState();
//    Shock_NormalState Sh_N1 = new Shock_NormalState();
    Pushfreq_Alert pushfreq_alert = new Pushfreq_Alert();
    Pushfreq_normal pushfreq_normal = new Pushfreq_normal();
    Pushdep_Alert pushdep_alert = new Pushdep_Alert();
    Pushdep_normal pushdep_normal = new Pushdep_normal();
    Springback_Alert springback_alert = new Springback_Alert();
    Springback_normal springback_normal = new Springback_normal();


    public Context getContxt() {
        return contxt;
    }

    public void setContxt(Context contxt) {
        this.contxt = contxt;
    }

    public ECGShowActivity getActivity() {
        return activity;
    }

    public void setActivity(ECGShowActivity activity) {
        this.activity = activity;
    }

    public WaitState getW1() {
        return w1;
    }

    public ActiveState getA1() {
        return a1;
    }

    public CPRState getC1() {
        return c1;
    }

    public InjectionState getI1() {
        return i1;
    }

    public ShockState getS1() {
        return s1;
    }

    public Cpr_AlertState getAl1() {
        return al1;
    }

    public Cpr_NormalState getN1() {
        return n1;
    }

    public Pushfreq_Alert getPushfreq_alert() {
        return pushfreq_alert;
    }

    public Pushfreq_normal getPushfreq_normal() {
        return pushfreq_normal;
    }

    public Pushdep_Alert getPushdep_alert() {
        return pushdep_alert;
    }

    public Pushdep_normal getPushdep_normal() {
        return pushdep_normal;
    }

    public Springback_Alert getSpringback_alert() {
        return springback_alert;
    }

    public Springback_normal getSpringback_normal() {
        return springback_normal;
    }

//构建状态树
    Test_State_tree(String name, Context context, ECGShowActivity activity) {
        super(name);
            addState(w1,null);
            addState(a1,null);
                addState(c1,a1);
                    addState(al1,c1);
                        addState(pushdep_alert,al1);
                        addState(pushfreq_alert,al1);
                        addState(springback_alert,al1);
                    addState(n1,c1);
                        addState(pushdep_normal,n1);
                        addState(pushfreq_normal,n1);
                        addState(springback_normal,n1);

                addState(i1,a1);
                addState(s1,a1);
        w1.setTree(this);
        a1.setTree(this);
            c1.setTree(this);
                al1.setTree(this);
                    pushdep_alert.setTree(this);
                    pushfreq_alert.setTree(this);
                    springback_alert.setTree(this);
                n1.setTree(this);
                    pushdep_normal.setTree(this);
                    pushfreq_normal.setTree(this);
                    springback_normal.setTree(this);
            i1.setTree(this);
            s1.setTree(this);
            setInitialState(w1);
            this.setActivity(activity);
            this.setContxt(contxt);

    }

//        启动状态机
    public static Test_State_tree makeTest_State_tree(Context context, Activity activity){
        Test_State_tree T1 = new Test_State_tree("流程tree",context, (ECGShowActivity) activity);

        T1.start();
        return T1;
    }

//    调用MainActivity的方法
    public void startAllService(){
        activity.startAllService();  //开启全部服务
        }
    public void stopAllService(){
        activity.stopAllService();  //关闭全部服务

    }
    public void tipInjection(){
//        肾上腺素提示注入信息
        activity.tipInjection();
    }
    public void tipCPR(){
        activity.tipCPR();
    }
    public void tipShock(){
//        执行ManActivity里的电击提示方法
        activity.tipShock();
    }
    public void pushdepNormal(){
//        按压深度正常
        activity.pushdepNormal();
    }
    public void pushdepTobot(){
//        按压深度过低
        activity.pushdepTobot();

    }
    public void pushdepTotop(){
//        按压深度过高
        activity.pushdepTotop();

    }
    public void pushfreqNormal(){
//        按压频率正常
        activity.pushfreqNormal();

    }
    public void pushfreqToslow(){
//         按压频率过慢
        activity.pushfreqToslow();

    }
    public void pushfreqTofast(){
//          按压频率过慢
        activity.pushfreqTofast();

    }
    public void springBackNormal(){
//        胸腔回弹正常
        activity.springBackNormal();

    }
    public void springBackAlert() {
//         胸腔回弹异常
        activity.springBackAlert();

    }
//    进入电击状态时需要开启的电击操作监测服务
    public void openCompleteshock(){
        activity.openCompleteshock();
    }
    //    离开电击状态时需要关闭的电击操作监测服务
    public void closeCompleteshock(){
        activity.closeCompleteshock();
    }


    public void openCompleteinj(){
        activity.openCompeleteinj();
    }
    public void closeCompleteinj(){
        activity.closeCompleteinj();
    }
    //    关闭实时心率可电击状态监测
    public void closeCheckhread(){

        activity.closeCheckhread();
    }
    //开始进行每两分钟一轮的可电击心率检查
    public void openHreadshockable(){
        activity.openHreadshockable();
    }
    public void startenterinj(){
        activity.startenterinj();
    }
    public void stopenterinj(){
        activity.stopenterinj();
    }
}
