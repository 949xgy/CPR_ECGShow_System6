package com.example.tjw.cpr_ecgshow_system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.EventType.Completeinj;
import com.example.tjw.cpr_ecgshow_system.EventType.Completeshock;
import com.example.tjw.cpr_ecgshow_system.EventType.Data_Map;
import com.example.tjw.cpr_ecgshow_system.ServiceBag.CompleteInjService;
import com.example.tjw.cpr_ecgshow_system.ServiceBag.CompleteShockService;
import com.example.tjw.cpr_ecgshow_system.ServiceBag.SendECGService;
import com.example.tjw.cpr_ecgshow_system.ServiceBag.dataService;
import com.example.tjw.cpr_ecgshow_system.ServiceBag.oprationService;
import com.example.tjw.cpr_ecgshow_system.dataExcption.DataException;
import com.example.tjw.cpr_ecgshow_system.dataExcption.excptionlist.DataExceptionList;
import com.example.tjw.cpr_ecgshow_system.fragment.Fragment1;
import com.example.tjw.cpr_ecgshow_system.fragment.Fragment2;
import com.example.tjw.cpr_ecgshow_system.fragment.Fragment3;
import com.example.tjw.cpr_ecgshow_system.fragment.Fragment4;
import com.example.tjw.cpr_ecgshow_system.statemachine.Test_State_tree;
import com.example.tjw.cpr_ecgshow_system.utils.CalculatedHeartRate;
import com.example.tjw.cpr_ecgshow_system.utils.DiseaseJudge;
import com.example.tjw.cpr_ecgshow_system.utils.DrawECGView;
import com.example.tjw.cpr_ecgshow_system.utils.MRUtils;
import com.example.tjw.cpr_ecgshow_system.utils.MyPaint;
import com.example.tjw.cpr_ecgshow_system.utils.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class ECGShowActivity extends FragmentActivity implements View.OnClickListener {
    private Button btn_start,btn_set;
    private Button btn_realDraw,btn_pause;//实时绘图按钮
    private Button btn_windowview,btn_choose,btn_patient,btn_ECG;
    private Context mContext = null;
    private boolean INJ_TATE = false;
    private boolean isWindowView =false;
    private SurfaceView sfv;
    private DrawECGView drawECGView;
    private Handler msgHandler;


    private Timer mDSTimer;
    private TimerTask mDSTask;

    private boolean start=false;//启动画图的标志位
    private ArrayList<String> LeadList=new ArrayList<>();//导联选择的数组列表

    private ArrayList<Float> SocketECGList=new ArrayList<Float>();//一个导联的数据列表

    private ArrayList<Float> SocketECGList1=new ArrayList<>();//一个导联的数据列表

    //huangcheng
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;

    private ImageView iv_canvas;
    private Bitmap baseBitmap;
    private Canvas canvas;
    private Paint paint;
    private MyPaint myPaint;
    private int time;


    private TextView tv_CPR;
    private TextView tv_EPI;

    private RelativeLayout rl_include;
    private RelativeLayout top_layout;
    private TextView workflow_step1;
    private LinearLayout workflow_step2;
    private TextView workflow_step3;
    private LinearLayout workflow_step4;
    private TextView workflow_step5;

    private Fragment fragment1;
    private Fragment fragment2;
    private Fragment fragment3;
    private Fragment fragment4;
    /**
     * MAinActivity的参数
     *
     *
     */
//    这是主要入口，需要接收数据，进行判断该进入那个模块
//    暂时先不管外部数据的接入
//    首先完成程序刚进入时的模块选择

    private Intent intenthreat,completeinjection,completeshock,intent_data,intent_opration;
    private Test_State_tree tree;
    private boolean completeInj=false;
    private boolean BcompleteShock=false;
    private Data_Map all_data = new Data_Map();
    //    按压深度标准变量（）
    private int minPushDep=50,maxPushDep=60;
    //    按压频率标准变量
    private int minPushfreq=80,maxPushfreq=100;
    //    计时数据
    private int recLen = 0;
    //    肾上腺素注射间隔时间(标准时间为4分钟)
    final private int TIMEINJ = 1000*40*1;
    //    完成注射和点击操作默认周转的时间长度（标注时间为30s）
    final private int TIMECOMPLETE=1000*10;
    //    电击状态的周期时间 (2分钟)
    final private int TIMESHOCK= 1000*20*1;

    private WindowManager windowManager;
    private WindowManager.LayoutParams layoutParams;
    private View windowview;
    private LayoutInflater factory ;
    boolean is_show = true;
    private TextView State,pushdep_del,pushfreq_del,springback_del,tip_info;
    private TextView ph_state,ph_opration,spo2_state,spo2_opration,pao2_state,pao2_opration,paco2_state,paco2_opration,bp_state,bp_opration;
    private DataExceptionList ecp_list= new DataExceptionList();


    //    0为正常1为心跳过慢2为心跳过快（心室颤动？）
    private int HREAT_TATE = 0;
    private int seed = (int) System.currentTimeMillis();
    private Socket socket_hreat = null;
    private ServerSocket serverSocket = null;
    private Socket client=null;
    private HashMap viewmap = new HashMap();
    private boolean isCathData = true;
    private Thread thread_hreat = null;

    //    随机数
    private Random random = new Random(seed);

    Handler handler = new Handler();


    //    心率可电击监测
    Runnable checkhread = new Runnable() {
        @Override
        public void run() {
            Log.d("HREAT_TATE","+++++++++++++++++++++++++++++++++++"+HREAT_TATE);
            if(HREAT_TATE==2){
//            跳转到shock状态
                tree.sendMessage(tree.obtainMessage(32));
            }
            handler.postDelayed(checkhread,1000);
        }
    };



    //开启心率可电击监测（周期为两分钟的循环）
    Runnable hreadshockable = new Runnable() {
        @Override
        public void run() {
            Log.d("MainActivity","2分钟为一轮的可电击心率判断循环");
//            执行实时监测是否为可电击心率的方法
            handler.postDelayed(checkhread,10);
        }
    };

    //持续性发送跳转状态到注射状态的方法
    Runnable sendTrsationToInj = new Runnable() {
        @Override
        public void run() {
            if(INJ_TATE){
                tree.sendMessage(tree.obtainMessage(22));
            }else {
                handler.postDelayed(sendTrsationToInj,1000);
            }
        }
    };

    //    调用发方法将状态切换到注射状态
    Runnable enterinjstate = new Runnable(){
        @Override
        public void run() {
            INJ_TATE = true;
            Log.d("MainActivity","实行状态的切换:进入肾上腺素注射状态");
            handler.postDelayed(sendTrsationToInj,200);
        }
    };


    Runnable trasctionToCprState = new Runnable(){

        @Override
        public void run() {
            Log.d("MainActivity","实行状态的切换：离开副状态，进入cpr状态");
            handler.removeCallbacks(sendTrsationToInj);
            tree.sendMessage(tree.obtainMessage(12));
        }
    };

    //    计时
    Runnable runnable = new Runnable(){
        @Override
        public void run() {
            recLen++;
//            showtimer.setText("" + recLen);
            Log.d("MainActivity_Handle","时间:"+recLen+"s");

            handler.postDelayed(this, 1000);
        }
    };
    /**
     * 需要增加的参数：
     * ph:7.40（7.35--7.45)05
     * spo2:血氧饱和度:95%或更高。06
     * PaO2:动脉血氧分压:(80--100mmHg)07
     * PaCO2: 血二氧化碳分压:35～45mmHg08
     * HR:心率 60-100次/分
     * RR:呼吸频率  HR:RR = 4:1
     * BP:血压：收缩压90-140， 舒张压60-90 09
     */


    /**
     * 成人 按压频率：80-100次  深度：5到6cm
     * 孕妇   按压频率：80-100次 深度：5到6cm  ：建议增加一个关于人体姿态的指导
     * 儿童   按压频率：100-120次 深度：4-5cm
     * 婴儿   按压频率：100-120次 深度：3-4cm
     */
    private void chose_Adult(){
//        参数不变化
        minPushDep = 50;
        maxPushDep = 60;
        minPushfreq = 80;
        maxPushfreq = 100;
        all_data.putData("USER","0");
    }
    private void chose_Pregnant(){
//        参数不变化
        minPushDep = 50;
        maxPushDep = 60;
        minPushfreq = 80;
        maxPushfreq = 100;
        all_data.putData("USER","1");
    }
    private void chose_Child(){
//        参数变化
        minPushDep = 40;
        maxPushDep = 50;
        minPushfreq = 100;
        maxPushfreq = 120;
        all_data.putData("USER","2");

    }
    private void chose_Baby(){
//        参数变化
        minPushDep = 30;
        maxPushDep = 40;
        minPushfreq = 100;
        maxPushfreq = 120;
        all_data.putData("USER","3");

    }



    public void dealData(HashMap map){
        if(!map.isEmpty()) {
            HashMap<String, String> Hmap = map;
            if(Hmap.get("BACK")!=null){
                String back = Hmap.get("BACK");
                pickSpringBack(back);
            }
            if(Hmap.get("FREQ")!=null) {
                String freq=Hmap.get("FREQ");
                pickPushFreq(freq);
            }
            if(Hmap.get("DEP")!=null) {
                String dep=Hmap.get("DEP");
                pickPushDepth(dep);
            }
            if(Hmap.get("HR")!=null){
                pickHreat(Integer.valueOf(Hmap.get("HR")));
            }
            dealSPO2(Hmap.get("SPO2"));
            dealBP(Hmap.get("BP"));
            dealPaO2(Hmap.get("PaO2"));
            dealPaCO2(Hmap.get("PaCO2"));
            dealPH(Hmap.get("PH"));
        }

    }
    /**
     *
     *接收socket传递过来的参数
     *
     */
    public void receiveData(){
        Thread thread_service = new Thread() {
            @Override
            public void run() {
                super.run();

                try {
                    if(serverSocket == null){
                        serverSocket = new ServerSocket(5556);
                    }
                    while (isCathData){
                        client = serverSocket.accept();
                        new Thread(new recivedThread(client)).start();//创建线程
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        thread_service.start();
    }

    /**
     *
     *接收socket传递数据的线程
     *
     */
    private class recivedThread implements Runnable {
        private Socket clinet;
        public recivedThread(Socket clinet){
            this.clinet = clinet;
        }
        @Override
        public void run() {
            try{
                System.out.println("新连接:"+clinet.getInetAddress()+":"+clinet.getPort());
//                Thread.sleep(1000);
                BufferedReader in=new BufferedReader(new InputStreamReader(clinet.getInputStream()));
                while(isCathData) {
                    String str = in.readLine();
                    Gson gson = new Gson();
                    Type type11 = new TypeToken<HashMap<String, String>>(){}.getType();
                    HashMap gsonmap = gson.fromJson(str, type11);
                    all_data.putAll(gsonmap);
                    if(all_data.isTrue_Key("ECG")){
                        String ECGstr=all_data.getData("ECG");
                        float a=Float.parseFloat(ECGstr);
                        Log.i("接收到的ECG值",""+a);
                        SocketECGList.add(a);

                    }
                    if(all_data.isTrue_Key("ECG2")){
                        String ECGstr1=all_data.getData("ECG2");
                        float b=Float.parseFloat(ECGstr1);
                        Log.i("接收到的ECG2值",""+b);
                        SocketECGList1.add(b);
                    }

//                          数据处理
                    dealData(all_data.getMap());
                    System.out.println("连接:"+clinet.getInetAddress()+":"+clinet.getPort()+"shuju:"+str);
                    Log.d("ACService","shuju:"+str);
                    Thread.sleep(8);
                }
            }catch(Exception e){e.printStackTrace();}


        }
    }

    /**
     *
     * 胸腔回弹数据逻辑判断方法
     * @param string
     */
    public void pickSpringBack(String string){
//        Log.d("MainActivity","接收到的胸腔回弹数据，可以在此处进行消息的传递");
        if(!Boolean.getBoolean(string)){
//            切换状态树的状态并执行相应的方法
//            好像无法在外界进行状态数的切换
            tree.sendMessage(tree.obtainMessage(1323));
//            springback_del.setText("alert");
        }else{
            tree.sendMessage(tree.obtainMessage(2323));
//            springback_del.setText("normal");
        }
    }
    /**
     *
     * 按压深度逻辑判断方法
     * @param string
     */

    public void pickPushDepth(String string){
        Log.d("MainActivity","接收到的按压深度数据，可以在此处进行消息的传递");
        float dep = Float.valueOf(string);
        if(dep>=this.minPushDep&&dep<=this.maxPushDep){
            tree.sendMessage(tree.obtainMessage(112));

        }else if(dep<this.minPushDep){

            tree.sendMessage(tree.obtainMessage(123));

        }else {

            tree.sendMessage(tree.obtainMessage(1123));

        }
    }

    /**
     * 按压频率逻辑判断方法
     * @param string
     */
    public void pickPushFreq(String string){


        float freq = Float.valueOf(string);
        if(freq>=this.minPushfreq&freq<=this.maxPushfreq){
            tree.sendMessage(tree.obtainMessage(1112));
        }else if(freq<this.minPushfreq){
            tree.sendMessage(tree.obtainMessage(1223));

        }else {
            tree.sendMessage(tree.obtainMessage(2223));
        }

    }

    /**
     * 对心率传感器数据分析
     * @param data
     */
    public void pickHreat(int data){
        ArrayList<Integer> hreat = new ArrayList();
        Log.d("pickhreat+++++++++++++","hreat.length"+hreat.size());
        if (data>160){
//            可能为心室颤动
            HREAT_TATE=2;
        }else if(data<55){
//          心跳过慢
            HREAT_TATE=1;

        }else{
//          正常心跳
            HREAT_TATE=0;
        }

    }
    public void addEcpToList(String type,int state,String opr){
        DataException ecp = new DataException();
        ecp.setEcp_state(state);
        ecp.setEcp_type(type);
        ecp.setOpration(opr);
        ecp_list.addExcpList(ecp);
    }
    //huangcheng
    /**
     * 需要增加的参数：
     * ph:7.40（7.35--7.45)05
     * spo2:血氧饱和度:95%或更高。06
     * PaO2:动脉血氧分压:(80--100mmHg)07
     * PaCO2: 血二氧化碳分压:35～45mmHg08
     * HR:心率 60-100次/分
     * RR:呼吸频率  HR:RR = 4:1
     * BP:血压：bp1收缩压90-140， bp2舒张压60-90 09
     *
     * 部分状态量统一：0：正常，1偏低，2：偏高
     */
    public void dealSPO2(String s_data){
        int spo2 = Integer.parseInt(s_data);
        if(spo2<95) {
            putViewData("SPO2_ST", 1);
            addEcpToList("SPO2",1,null);
        }
        else{
            putViewData("SPO2_ST",0);
        }
    }
    public void  dealBP(String data){
//       血压首先需要将其进行数据处理
        int index = data.indexOf("/");
        int bp1= Integer.parseInt(data.substring(0,index));
        Log.d("substring",data.substring(0,index));
        Log.d("substring",data.substring(index+1));
        int bp2= Integer.parseInt(data.substring(index+1));
//        具体操作待查阅资料
        if(bp1<90){
            putViewData("BP_ST", 1);
            addEcpToList("BP",1,null);

        }else if(bp1>90&bp1<140){
            putViewData("BP_ST", 0);

        }else{
            putViewData("BP_ST", 2);
            addEcpToList("BP",2,null);


        }

    }
    public void  dealPH(String data) {
        float ph = Float.parseFloat(data);
        if(ph<7.35){
//            过低
            putViewData("PH_ST", 1);
            addEcpToList("PH",1,null);

        }else if(ph<7.45&ph>7.35){
//            正常
            putViewData("PH_ST", 0);

        }else{
//            过高
            putViewData("PH_ST", 2);
            addEcpToList("PH",2,null);

        }
    }
    public void  dealPaO2(String data) {
        int pao2 = Integer.parseInt(data);
        if(pao2<80){
//            过低
            putViewData("PaO2_ST", 1);
            addEcpToList("PaO2",1,null);


        }else if(pao2>=80&pao2<=100){
//            正常
            putViewData("PaO2_ST", 0);
        }else{
//            过高
            putViewData("PaO2_ST", 2);
            addEcpToList("PaO2",2,null);

        }

    }
    public void  dealPaCO2(String data) {
        int paco2 = Integer.parseInt(data);
        if(paco2<35){
//            过低
            putViewData("PaCO2_ST", 1);
            addEcpToList("PaCO2",1,null);


        }else if(paco2>=35&paco2<=45){
//            正常
            putViewData("PaCO2_ST", 0);

        }else{
//            过高
            putViewData("PaCO2_ST", 2);
            addEcpToList("PaCO2",2,null);
        }
    }

    private TextClock textClock;
    private TextView tv_start_time;


    @Override
    protected void onStart() {
        super.onStart();
        btn_choose.performClick();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//设置屏幕为横屏, 设置后会锁定方向
        Log.d("btn_choose","执行语句perform");

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecgshow2);     //2用来测试
        isCathData = true;
        mContext = this;
        /**
         * MainActivity内部
         */
        factory = LayoutInflater.from(ECGShowActivity.this);
        windowview = factory.inflate(R.layout.windowview, null);
        State = windowview.findViewById(R.id.State);

        pushdep_del=windowview.findViewById(R.id.pushdep_del);
        pushfreq_del=windowview.findViewById(R.id.pushfreq_del);
        springback_del=windowview.findViewById(R.id.springback_del);
        tip_info=windowview.findViewById(R.id.tip_text);

        ph_state = windowview.findViewById(R.id.state_ph);
        ph_opration = windowview.findViewById(R.id.opration_ph);
        spo2_state = windowview.findViewById(R.id.state_spo2);
        spo2_opration = windowview.findViewById(R.id.opration_spo2);
        pao2_state = windowview.findViewById(R.id.state_pao2);
        pao2_opration = windowview.findViewById(R.id.opration_pao2);
        paco2_state = windowview.findViewById(R.id.state_pcao2);
        paco2_opration = windowview.findViewById(R.id.opration_paco2);
        bp_state = windowview.findViewById(R.id.state_BP);
        bp_opration = windowview.findViewById(R.id.opration_BP);
        btn_windowview = findViewById(R.id.btn_windowview);
        btn_choose = findViewById(R.id.btn_choose);

        intent_data = new Intent(ECGShowActivity.this,dataService.class);
        intent_opration = new Intent(ECGShowActivity.this,oprationService.class);
        completeinjection = new Intent(ECGShowActivity.this,CompleteInjService.class);
        completeshock = new Intent(ECGShowActivity.this,CompleteShockService.class);
        EventBus.getDefault().register(this);
        btn_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("btn_choose","电击事件触发");
                showSingleChoiceDialog();
            }
        });

        receiveData();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        tree = Test_State_tree.makeTest_State_tree(this,ECGShowActivity.this);
        testTree();




//huangcheng
        initView();
        initEvent();
        setFragment(1);
        initMR();
        initTL();

        MRUtils.init();

        startFloatingService();
//huangcheng

        //控制读取数据按钮
        btn_start=findViewById(R.id.btn_start);
        btn_set=findViewById(R.id.btn_set);
        btn_start=findViewById(R.id.btn_start);
//        btn_doctor=findViewById(R.id.btn_doctor);
//        btn_patient=findViewById(R.id.btn_patient);
//        btn_ECG=findViewById(R.id.btn_ECG);

        //控制实时绘图按钮
        btn_realDraw=findViewById(R.id.btn_realDraw);
        btn_pause=findViewById(R.id.btn_pause);

        sfv= findViewById(R.id.sfv);
        drawECGView=new DrawECGView(sfv);
        btn_windowview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isWindowView){
                    windowManager.removeView(windowview);
                }else{
                    windowManager.addView(windowview, layoutParams);
                }
                isWindowView=!isWindowView;

            }
        });

//      启动按钮点击事件
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=true;
            }
        });

//      实时绘图部分
        btn_realDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(ECGShowActivity.this,SendECGService.class));

                mDSTimer = new Timer();//设置一个定时器，让定时器周期的执行任务
                mDSTask = new TimerTask(){
                    public void run(){
                        Message msg = Message.obtain();
                        msg.what =0X11;
                        msgHandler.sendMessage(msg);
                    }
                };
                mDSTimer.schedule(mDSTask,1000,50);
            }
        });
        btn_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start=false;
                stopService(new Intent(ECGShowActivity.this,SendECGService.class));
                if(mDSTask!=null){
                    mDSTask.cancel();
                }
                if(mDSTimer!=null){
                    mDSTimer.cancel();
                }

            }
        });


        Looper lp = Looper.myLooper();//每个线程只能有一个looper实例
        msgHandler = new MsgHandler(lp);//实例化一个Handler对象



        btn_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeadList.clear();
                mulCheckDialog();
            }
        });

        is_show = true;
        myHandler.sendEmptyMessage(0);
//        myHandler.sendEmptyMessage(4);
        myHandler.sendEmptyMessageDelayed(1, 2000);
        myHandler.sendEmptyMessageDelayed(2, 3000);

    }

    //    启动悬浮窗方法
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startFloatingService() {
//                启动悬浮窗
        showFloatingWindow();
    }
    @Override
    protected void onResume() {
        super.onResume();
        myHandler.sendEmptyMessageDelayed(10, 3000);
    }
    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);
        myPaint = new MyPaint();
        Log.i("AA", iv_canvas.getWidth() + ":" + iv_canvas.getHeight());
        if (baseBitmap == null) {
            //有bug 异常  先+1 让其正常运行
            baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth()+1,iv_canvas.getHeight()+1, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.BLACK);
            iv_canvas.setImageBitmap(baseBitmap);
        }
        myPaint.drawBaseLine(canvas,paint);
        myPaint.drawCPR(canvas,paint,0);
    }
    private void VF() {
        myHandler.sendEmptyMessage(3);
    }
    @SuppressLint("SetTextI18n")
    private int startVF(){
        time = (int)(Math.random()*120+1);
        int min = time/60;
        int sec = time%60;
        Log.i("yimt",time+":"+min+":"+sec);
        workflow_step1.setText("0"+min+":"+ (sec<10?"0"+sec:sec)+" Started:VF");
        workflow_step1.setBackgroundResource(R.drawable.layout_shock);
        return time;
    }
    private void startShockDone(){
        workflow_step1.setBackgroundResource(R.drawable.tv_workflow_step);
        workflow_step2.setBackgroundResource(R.drawable.layout_shock);

    }
    private void startCPR() {
        workflow_step2.setBackgroundResource(R.drawable.tv_workflow_step);
        workflow_step3.setBackgroundResource(R.drawable.layout_epi);
    }

    private void startEPI() {

        workflow_step3.setBackgroundResource(R.drawable.tv_workflow_step3);
        workflow_step4  .setBackgroundResource(R.drawable.layout_epi);
    }
    private void startCheckRhythm() {
        workflow_step4.setBackgroundResource(R.drawable.tv_workflow_step3);
        workflow_step5  .setBackgroundResource(R.drawable.layout_epi);
    }
    /**
     * MainActivity内部
     *
     */
    @SuppressLint("ResourceAsColor")
    public void showWindowView(HashMap map){
        Map<String, Integer> Hmap = map;
        Log.d("悬浮窗数据",""+map);
        for (Map.Entry<String, Integer> entry : Hmap.entrySet()) {
            String key=entry.getKey();
            int val= entry.getValue();
            switch (key){
                case "STATE":
                    switch (val){
                        case 0:
                            State.setText(R.string.CPR);
                            tip_info.setText(R.string.CPR_info);
                            break;
                        case 1:
                            State.setText(R.string.EPI);
                            tip_info.setText(R.string.EPI_info);
                            break;
                        case 2:
                            State.setText(R.string.SHOCK);
                            tip_info.setText(R.string.SHOCK_info);
                            break;
                        default:break;
                    }
                    break;
                case "DEP_ST":
                    switch (val){
                        case 0:
                            pushdep_del.setTextColor(getResources().getColor(R.color.colorBlack));
                            pushdep_del.setText(R.string.Push_dep_m);
                            break;
                        case 1:
                            pushdep_del.setTextColor(getResources().getColor(R.color.colorRed));
                            pushdep_del.setText(R.string.Push_dep_s);
                            break;
                        case 2:
                            pushdep_del.setTextColor(getResources().getColor(R.color.colorRed));
                            pushdep_del.setText(R.string.Push_dep_b);
                            break;
                        case 4:
                            pushdep_del.setTextColor(getResources().getColor(R.color.colorBlack));
                            pushdep_del.setText(R.string.NULL_Data);
                            break;
                        default:break;

                    }
                    break;
                case "FREQ_ST":
                    switch (val){
                        case 0:
                            pushfreq_del.setTextColor(getResources().getColor(R.color.colorBlack));

                            pushfreq_del.setText(R.string.Push_freq_m);
                            break;
                        case 1:
                            pushfreq_del.setTextColor(getResources().getColor(R.color.colorRed));

                            pushfreq_del.setText(R.string.Push_freq_s);
                            break;
                        case 2:
                            pushfreq_del.setTextColor(getResources().getColor(R.color.colorRed));

                            pushfreq_del.setText(R.string.Push_freq_b);
                            break;
                        case 4:
                            pushfreq_del.setTextColor(getResources().getColor(R.color.colorBlack));

                            pushfreq_del.setText(R.string.NULL_Data);
                            break;
                        default:break;

                    }
                    break;
                case "BACK_ST":
                    switch (val){
                        case 0:
                            springback_del.setTextColor(getResources().getColor(R.color.colorRed));
                            springback_del.setText(R.string.Back_n);
                            break;
                        case 1:
                            springback_del.setTextColor(getResources().getColor(R.color.colorRed));
                            springback_del.setText(R.string.Back_a);
                            break;
                        case 4:
                            springback_del.setTextColor(getResources().getColor(R.color.colorRed));

                            springback_del.setText(R.string.NULL_Data);
                            break;
                        default:break;
                    }
                    break;
                case "PH_ST":
                    switch (val){
                        case 0:
                            ph_state.setTextColor(getResources().getColor(R.color.colorBlack));
                            ph_opration.setTextColor(getResources().getColor(R.color.colorBlack));
                            ph_state.setText(R.string.NORMAL);
                            ph_opration.setText(R.string.NULL_Data);
                            break;
                        case 1:
                            ph_state.setTextColor(getResources().getColor(R.color.colorRed));
                            ph_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            ph_state.setText(R.string.LESS);
                            ph_opration.setText(R.string.PH_LESS_OPR);
                            break;
                        case 2:
                            ph_state.setTextColor(getResources().getColor(R.color.colorRed));
                            ph_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            ph_state.setText(R.string.OVER);
                            ph_opration.setText(R.string.PH_OVER_OPR);
                            break;
                        default:
                            break;

                    }
                    break;
                case "SPO2_ST":
                    switch (val){
                        case 0:
                            spo2_state.setTextColor(getResources().getColor(R.color.colorBlack));
                            spo2_opration.setTextColor(getResources().getColor(R.color.colorBlack));
                            spo2_state.setText(R.string.NORMAL);
                            spo2_opration.setText(R.string.NULL_Data);
                            break;
                        case 1:
                            spo2_state.setTextColor(getResources().getColor(R.color.colorRed));
                            spo2_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            spo2_state.setText(R.string.LESS);
                            spo2_opration.setText(R.string.SPO2_LESS_OPR);
                            break;
                        default:
                            break;

                    }
                    break;
                case "PaCO2_ST":
                    switch (val){
                        case 0:
                            paco2_state.setTextColor(getResources().getColor(R.color.colorBlack));
                            paco2_opration.setTextColor(getResources().getColor(R.color.colorBlack));
                            paco2_state.setText(R.string.NORMAL);
                            paco2_opration.setText(R.string.NULL_Data);

                            break;
                        case 1:
                            paco2_state.setTextColor(getResources().getColor(R.color.colorRed));
                            paco2_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            paco2_state.setText(R.string.LESS);
                            paco2_opration.setText(R.string.PACO2_LESS_OPR);
                            break;
                        case 2:
                            paco2_state.setTextColor(getResources().getColor(R.color.colorRed));
                            paco2_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            paco2_state.setText(R.string.OVER);
                            paco2_opration.setText(R.string.PACO2_OVER_OPR);
                            break;
                        default:
                            break;

                    }
                    break;
                case "PaO2_ST":
                    switch (val){

                        case 0:
                            pao2_state.setTextColor(getResources().getColor(R.color.colorBlack));
                            pao2_opration.setTextColor(getResources().getColor(R.color.colorBlack));
                            pao2_state.setText(R.string.NORMAL);
                            pao2_opration.setText(R.string.NULL_Data);
                            break;
                        case 1:
                            pao2_state.setTextColor(getResources().getColor(R.color.colorRed));
                            pao2_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            pao2_state.setText(R.string.LESS);
                            pao2_opration.setText(R.string.PAO2_LESS_OPR);

                            break;
                        case 2:
                            pao2_state.setTextColor(getResources().getColor(R.color.colorRed));
                            pao2_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            pao2_state.setText(R.string.OVER);
                            pao2_opration.setText(R.string.PAO2_OVER_OPR);

                            break;
                        default:
                            break;
                    }
                    break;
                case "BP_ST":
                    switch (val){
                        case 0:
                            bp_state.setTextColor(getResources().getColor(R.color.colorBlack));
                            bp_opration.setTextColor(getResources().getColor(R.color.colorBlack));
                            bp_state.setText(R.string.NORMAL);
                            bp_opration.setText(R.string.NULL_Data);
                            break;
                        case 1:
                            bp_state.setTextColor(getResources().getColor(R.color.colorRed));
                            bp_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            bp_state.setText(R.string.LESS);
                            bp_opration.setText(R.string.BP_LESS_OPR);
                            break;
                        case 2:
                            bp_state.setTextColor(getResources().getColor(R.color.colorRed));
                            bp_opration.setTextColor(getResources().getColor(R.color.colorRed));
                            bp_state.setText(R.string.OVER);
                            bp_opration.setText(R.string.BP_LESS_OPR);
                            break;
                        default:
                            break;

                    }
                    break;
                default:break;
            }
        }
//        State.setText("showWindowView");
    }
    @SuppressLint("HandlerLeak")
    Handler myHandler = new Handler() {
        int count = 0;
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0: {
                    if (is_show) {
                        HashMap<String, String> hashMap = all_data.getMap();
                        Log.i("map", hashMap + "");
                        setMRvalue(hashMap);
                        showWindowView(viewmap);
                        myHandler.sendEmptyMessageDelayed(0, 1000);
                    }
                } break;
                case 1:{
                    setStartTime();
                } break;
                case 2: {
//                    receiveData();
                } break;
                case 3:{
                    int time = startVF();
                    System.out.println("A : "+canvas);
                    System.out.println("B : "+paint);
                    myPaint.drawVF(canvas, paint, time);
                    myHandler.sendEmptyMessageDelayed(4,5000);
                } break;
                case 4:{
//                    showWindowView(viewmap);
//                    myHandler.sendEmptyMessageDelayed(4, 10);
                    time += (int)(Math.random()*60 + 30);
                    Log.i("Time",":" + time);
                    startShockDone();
                    myPaint.drawShock(canvas, paint, time);
                    myHandler.sendEmptyMessageDelayed(5,10000);
                }break;
                case 5:{
                    if (count == 0) {
                        startCPR();
                    }
                    if (++count > 2400) {
                        int time = (count * 50) / 1000;
                        int min = time / 60;
                        int sec = time % 60;
                        String text = "CPR(1) " + String.format("%02d:%02d", min, sec);
                        myPaint.drawCPRText(canvas,paint,text);
                        count = 0;
                        myHandler.sendEmptyMessage(6);
                    } else {
                        myHandler.sendEmptyMessageDelayed(5, 50);
                        myPaint.drawCPR(canvas,paint,(float)(count*0.05*3.5));
                        int time = (count * 50) / 1000;
                        int min = time / 60;
                        int sec = time % 60;
                        tv_CPR.setText(String.format("%02d:%02d Elasped", min, sec));
                    }
                }break;
                case 6:{
                    if (count == 0) {
                        startEPI();
                    }
                    if (++count > 3600) {
                        int time = (count * 50) / 1000;
                        int min = time / 60;
                        int sec = time % 60;
                        String text = "EPI(1) " + String.format("%02d:%02d", min, sec);
                        myPaint.drawEPIText(canvas,paint,text);
                        count = 0;
                        myHandler.sendEmptyMessage(7);
                    } else {
                        myHandler.sendEmptyMessageDelayed(6,50);
                        myPaint.drawEPI(canvas,paint,(float)(count*0.05*3.5));

                        int time = (count * 50) / 1000;
                        int min = time / 60;
                        int sec = time % 60;
                        tv_EPI.setText(String.format("%02d:%02d Elasped", min, sec));
                    }

                }break;
                case 7:{
                    startCheckRhythm();

                }break;
                case 10: {
                    initPaint();
                    VF();
                } break;
                default:break;
            }

        }
    };

    private void setStartTime() {
        textClock.getText();
        tv_start_time.setText(textClock.getText().subSequence(11,19));
    }
    private void setMRvalue(HashMap hashMap){

        Set<Map.Entry<String, String>> entrySet = hashMap.entrySet();

        for (Map.Entry<String, String> entry : entrySet) {
            String key = entry.getKey();
            String value = entry.getValue();
            Integer re_id = MRUtils.getId_hashMap().get(key);
            if (re_id != null) {
                Log.i("key", "key : " +  key + " - value : " + value);
                LinearLayout ll =  findViewById(re_id);
                TextView tv = ll.findViewById(R.id.tv_value);
                tv.setText(value);
                if (key.equals("BP")) {
                    String[] s = value.split("/");
                    int a = Integer.valueOf(s[0]);
                    int b = Integer.valueOf(s[1]);
                    if ((a > 100 && a < 120) && (b > 60 && b < 80)) {
                        ll.setBackgroundColor(Color.WHITE);
                    } else if (((a > 70 && a < 100) || (a > 120 && a < 150)) && ((b > 40 && b < 60) || (b > 80 && b < 100))) {
                        ll.setBackgroundColor(Color.YELLOW);
                    } else {
                        ll.setBackgroundColor(Color.RED);
                    }
                } else {
                    ll.setBackgroundColor(MRUtils.get_color(key, Double.valueOf(value)));
                }
            }
        }
    }

    private void initMR() {
//        LinearLayout layout_HR = findViewById(R.id.include_HR);
        LinearLayout layout_RR = findViewById(R.id.include_RR);
        TextView tv_RR_title = layout_RR.findViewById(R.id.tv_title);
        tv_RR_title.setText("RR");

        LinearLayout layout_BP = findViewById(R.id.include_BP);
        TextView tv_BP_title = layout_BP.findViewById(R.id.tv_title);
        tv_BP_title.setText("BP");

        LinearLayout layout_SPO2 = findViewById(R.id.include_SPO2);
        TextView tv_SPO2_title = layout_SPO2.findViewById(R.id.tv_title);
        tv_SPO2_title.setText("SPO2");

        LinearLayout layout_PH = findViewById(R.id.include_PH);
        TextView tv_PH_title = layout_PH.findViewById(R.id.tv_title);
        tv_PH_title.setText("PH");

        LinearLayout layout_PaO2 = findViewById(R.id.include_PaO2);
        TextView tv_PaO2_title = layout_PaO2.findViewById(R.id.tv_title);
        tv_PaO2_title.setText("PaO2");

        LinearLayout layout_PaCO2 = findViewById(R.id.include_PaCO2);
        TextView tv_PaCO2_title = layout_PaCO2.findViewById(R.id.tv_title);
        tv_PaCO2_title.setText("PaCO2");

        LinearLayout layout_Mg = findViewById(R.id.include_Mg);
        TextView tv_Mg_title = layout_Mg.findViewById(R.id.tv_title);
        tv_Mg_title.setText("Mg");

        LinearLayout layout_K = findViewById(R.id.include_K);
        TextView tv_K_title = layout_K.findViewById(R.id.tv_title);
        tv_K_title.setText("K+");

        LinearLayout layout_Creat = findViewById(R.id.include_Creat);
        TextView tv_Creat_title = layout_Creat.findViewById(R.id.tv_title);
        tv_Creat_title.setText("Creat");
    }

    void testTree(){
        tree.sendMessage(tree.obtainMessage(2));
    }

    public void putViewData(String key,int val){
        viewmap.put(key,val);
    }
    public void tipCPR(){
        Log.d("由状态树调用的方法","CPR状态");
        putViewData("STATE",0);
    }
    /**
     * key      val
     * state    0:CPR;1:inj;2:shock
     *dep       0:normal;1+；2：-
     *freq:     0:normal;1:+；2：-
     *springback:0：normal；1：alert
     */
    public void tipInjection(){

        Log.d("由状态树调用的方法","马上进行肾上腺素的注射");
        //        进行肾上腺素计时30s和注入肾上腺素是否完成判断操作
        //        true表示肾上腺素注射未完成，进行定时30s操作,若已完成则直接进行状态的跳转
        putViewData("STATE",1);
        putViewData("DEP_ST",4);
        putViewData("FREQ_ST",4);
        putViewData("BACK_ST",4);
        /**
         * 存在功能上的不现实，应该存在一个实时监测肾上腺素是否注入的实时数据接收，
         * 若成功接收则直接跳转到cpr状态
         */
        if(!completeInj){
            handler.postDelayed(trasctionToCprState,TIMECOMPLETE);
        }else{
            //            切换回cpr状态
            tree.sendMessage(tree.obtainMessage(12));
        }

        //发送cpr消息
        //跳转回cpr状态操作
        //        tree.deferMessage(tree.obtainMessage(012));
        //        tree.transitionTo(tree.getC1());
    }
    //    关闭实时心率可电击状态监测
    public void closeCheckhread(){
        handler.removeCallbacks(checkhread);
    }



    public void tipShock(){
//        由状态数调用的shoc方法，可以在此执行界面上的变化，以提示其进行相应的操作
        Log.d("MainActivity","由状态树调用，执行提示进行AED电击操作");
//        此处执行具体的电击操作：
//        判断电击能量
        putViewData("STATE",2);
        putViewData("DEP_ST",4);
        putViewData("FREQ_ST",4);
        putViewData("BACK_ST",4);
        if(!BcompleteShock){
//      30s离开电击状态，电击操作未执行状况下
            /**
             * 引出来的问题：如何执行电击方法？
             * 如何检测电击是否完成？
             *
             */
            handler.postDelayed(trasctionToCprState,TIMECOMPLETE);
        }else{
//            切换回cpr状态
            tree.sendMessage(tree.obtainMessage(12));
        }
    }

    public void pushdepNormal(){
//        按压深度正常
        Log.d("由状态树调用的方法","按压深度正常，继续保持当前深度按压");
//        pushdep_del.setText("normal,保持深度");
        putViewData("DEP_ST",0);

    }
    public void pushdepTobot(){
//        按压深度过低
        Log.d("由状态树调用的方法","按压深度太浅，加深当前深度按压");
//        pushdep_del.setText("alert,加深深度");
        putViewData("DEP_ST",1);


    }
    public void pushdepTotop(){
//        按压深度过高
        Log.d("由状态树调用的方法","按压深度太深，减少当前深度按压");
//        pushdep_del.setText("alert,减少深度");
        putViewData("DEP_ST",2);


    }
    public void pushfreqNormal(){
//        按压频率正常
        Log.d("由状态树调用的方法","按压频率正常，继续保持当前频率按压");
//        pushfreq_del.setText("normal,保持频率");
        putViewData("FREQ_ST",0);


    }
    public void pushfreqToslow(){
//         按压频率过慢
        Log.d("由状态树调用的方法","按压频率过慢，加快当前频率按压");
//        pushfreq_del.setText("alert,加快频率");
        putViewData("FREQ_ST",1);

    }
    public void pushfreqTofast(){
//          按压频率过快
        Log.d("由状态树调用的方法","按压频率过快，减慢当前频率按压");
//        pushfreq_del.setText("alert,减弱频率");
        putViewData("FREQ_ST",2);

    }
    public void springBackNormal(){
//        胸腔回弹正常
        Log.d("由状态树调用的方法","由状态树调用的方法胸腔回弹正常，切换到胸腔回弹正常状态");
//        springback_del.setText("normal，胸腔回弹正常");
        putViewData("BACK_ST",0);
    }
    public void springBackAlert() {
//         胸腔回弹异常
        Log.d("由状态树调用的方法","由状态树调用的方法胸腔回弹错误，切换到胸腔回弹警告状态");
//        springback_del.setText("alert，胸腔回弹异常");
        putViewData("BACK_ST",1);

    }
    /**
     * 计时方法：建议移除
     */
    public void openTimercal(){
        handler.postDelayed(runnable, 1000);
    }

    public void startAllService(){
//        从State调用该方法启动服务
        Log.d("MainActivity","从state里调用开始所有数据随机服务");
        startService(intent_data);
        startService(intent_opration);

//

//        启动间隔为1s的定时器
        openTimercal();
        openHreadshockable();
        startenterinj();
    }


    //    停止所有外部数据服务
    public void stopAllService(){
        Log.d("MainActivity","从state里调用停止所有数据随机服务");
        stopService(intent_data);
        stopService(intent_opration);
//        停止该定时器
        stopenterinj();
        handler.removeCallbacks(trasctionToCprState);
    }
    //进入电击状态后需要开启的电击状态检测服务
    public void openCompleteshock(){
        startService(completeshock);
    }
    //离开电击状态后需要关闭的电击状态检测服务
    public void closeCompleteshock(){
        stopService(completeshock);
    }

    //    进入注射状态时需要开启的服务
    public void openCompeleteinj(){
        INJ_TATE = false;
        startService(completeinjection);
    }


    //    离开注射状态后需要关闭的服务
    public void closeCompleteinj(){
        stopService(completeinjection);
    }




    //    开启肾上腺素定时器
    public void startenterinj(){
        handler.postDelayed(enterinjstate, TIMEINJ);
    }



    //    关闭肾上腺素定时
    public void stopenterinj(){
//        首先关闭循环发生跳转到注射状态的定时器
        handler.removeCallbacks(sendTrsationToInj);
//        下一条语句好像不是很有存在的意义
        handler.removeCallbacks(enterinjstate);
    }


    //    关闭没两分钟一轮的心率可电击定时器
    public void colseHreadshockable(){
        handler.removeCallbacks(checkhread);
        handler.removeCallbacks(hreadshockable);
    }


    //开始进行每两分钟一轮的可电击心率检查
    public void openHreadshockable(){
        handler.postDelayed(hreadshockable,TIMESHOCK);
    }




    //cpr行为已经持续了30分钟，停止进行cpr
    public void overTime(){
        onFinsh();
    }


    /**
     * 每过一段时间EventBus发送过来的指令开始进行injection提示状态
     * @param completeshock
     */
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 6)
    public void onEventMainThread(Completeshock completeshock){
        if(completeshock.isCompleteinj()){
//              注射已经完成，跳转回cpr状态
            tree.sendMessage(tree.obtainMessage(12));
//              清楚30s离开电击状态定时器
            handler.removeCallbacks(trasctionToCprState);
        }
    }

    /**
     * 每过一段时间EventBus发送过来的指令开始进行injection提示状态
     * @param completeinj
     */
    @Subscribe(threadMode = ThreadMode.MAIN,priority = 6)
    public void onEventMainThread(Completeinj completeinj){
        if(completeinj.isCompleteinj()){
//              注射已经完成，跳转回cpr状态
            tree.sendMessage(tree.obtainMessage(12));
//              清楚30s离开注射状态定时器
            handler.removeCallbacks(trasctionToCprState);
        }

    }


    public void onFinsh(){
//        停止运行
//        stopService(new Intent(ECGShowActivity.this,SendService.class));
//      将状态树切换至wait状态
        tree.sendMessage(1);
        this.stopAllService();
        this.finish();
    }



    private void mulCheckDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("请选择导联");
        final String[] items=new String[]{"I导联","II导联","III导联","V1导联","V2导联","V3导联","V4导联","V5导联","V6导联","avR导联","avL导联","avR导联"};
        final boolean[] checkedItems=new boolean[]{false,false,false,false,false,false,false,false,false,false,false,false};

        builder.setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedItems[which]=isChecked;
            }
        });

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String text="";
                for(int i=0;i<items.length;i++){
                    if(checkedItems[i]){
                        LeadList.add(items[i]);
                        text+=items[i];
                    }
                }
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    // 更新UI画图的线程
    class MsgHandler extends Handler {
        public MsgHandler(Looper looper) {
            super(looper);
        }
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 0X11:
                    if(start==true){
//                        DiseaseJudge.HeartJudge(value)心率状态State
                        //如果是单导联
                        if(LeadList.size()==1&&SocketECGList.size()>30){
                            if(SocketECGList.size()>500) {
                                int value = CalculatedHeartRate.xinlv(SocketECGList);
                                if(value==0){
                                    drawECGView.DrawOnetoView(SocketECGList,LeadList);
                                }else{
                                    drawECGView.DrawOnetoView(SocketECGList, value, LeadList);
                                    Log.d("心率值", "handleMessage: "+DiseaseJudge.HeartJudge(value));
                                    if(DiseaseJudge.HeartJudge(value)==1){
                                        ToastUtils.showToast(ECGShowActivity.this,"心动过缓");
                                    }
                                    if(DiseaseJudge.HeartJudge(value)==2){
                                        ToastUtils.showToast(ECGShowActivity.this,"心动过速");
                                    }
                                    if(DiseaseJudge.HeartJudge(value)==3){
                                        ToastUtils.showToast(ECGShowActivity.this,"心室颤动");
                                    }
                                }

                            }else{
                                drawECGView.DrawOnetoView(SocketECGList,LeadList);
                            }
                            if(SocketECGList.size()>1500){
                                if(DiseaseJudge.StopBo(SocketECGList)==1){
                                    ToastUtils.showToast(ECGShowActivity.this,"停博");
                                }
                                SocketECGList.clear();
                            }
                        }
                        //如果是双导联
                        if(LeadList.size()==2&&SocketECGList.size()>30&&SocketECGList1.size()>30){
                            if(SocketECGList.size()>500) {
                                int value = CalculatedHeartRate.xinlv(SocketECGList);
                                if(value==0){
                                    drawECGView.DrawTwotoView(SocketECGList,SocketECGList1,LeadList);
                                }else{
                                    drawECGView.DrawTwotoView(SocketECGList,SocketECGList1, LeadList, value);
                                    Log.d("心率值", "handleMessage: "+DiseaseJudge.HeartJudge(value));
                                    if(DiseaseJudge.HeartJudge(value)==1){
                                        ToastUtils.showToast(ECGShowActivity.this,"心动过缓");
                                    }
                                    if(DiseaseJudge.HeartJudge(value)==2){
                                        ToastUtils.showToast(ECGShowActivity.this,"心动过速");
                                    }
                                    if(DiseaseJudge.HeartJudge(value)==3){
                                        ToastUtils.showToast(ECGShowActivity.this,"心室颤动");
                                    }
                                }

                            }else{
                                drawECGView.DrawTwotoView(SocketECGList,SocketECGList1,LeadList);
                            }
                            if(SocketECGList.size()>1500){
                                if(DiseaseJudge.StopBo(SocketECGList)==1){
                                    ToastUtils.showToast(ECGShowActivity.this,"停博");
                                }
                                SocketECGList.clear();
                                SocketECGList1.clear();
                            }
                        }


                    }

                    break;
                default:break;
            }
        }
    }
    //huangcheng
    private void initTL() {
        TextView TL_tv1 = findViewById(R.id.id_TL_tv1);
        TextView TL_tv2 = findViewById(R.id.id_TL_tv2);
        TextView TL_tv3 = findViewById(R.id.id_TL_tv3);
        TextView TL_tv4 = findViewById(R.id.id_TL_tv4);
        TextView TL_tv5 = findViewById(R.id.id_TL_tv5);
        TextView TL_tv6 = findViewById(R.id.id_TL_tv6);
        TextView TL_tv7 = findViewById(R.id.id_TL_tv7);
        TextView TL_tv8 = findViewById(R.id.id_TL_tv8);

        TL_tv1.setOnClickListener(this);
        TL_tv2.setOnClickListener(this);
        TL_tv3.setOnClickListener(this);
        TL_tv4.setOnClickListener(this);
        TL_tv5.setOnClickListener(this);
        TL_tv6.setOnClickListener(this);
        TL_tv7.setOnClickListener(this);
        TL_tv8.setOnClickListener(this);
    }



    private void initEvent() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);

    }

    private void initView() {
        tv1 = findViewById(R.id.id_tv_fragment1);
        tv2 = findViewById(R.id.id_tv_fragment2);
        tv3 = findViewById(R.id.id_tv_fragment3);
        tv4 = findViewById(R.id.id_tv_fragment4);

        tv_CPR = findViewById(R.id.id_CPR_tv);
        tv_EPI = findViewById(R.id.id_EPI_tv);

        textClock = findViewById(R.id.id_tv_TimeClock);
        tv_start_time = findViewById(R.id.id_tv_starttime);

        rl_include = findViewById(R.id.include_layout_workflow);
        workflow_step1 = rl_include.findViewById(R.id.id_workflow_step1);
        workflow_step2 = rl_include.findViewById(R.id.id_shockdone_layout);
        workflow_step3 = rl_include.findViewById(R.id.id_workflow_step3);
        workflow_step4 = rl_include.findViewById(R.id.id_EPI_layout);
        workflow_step5 = rl_include.findViewById(R.id.id_workflow_step5);

        iv_canvas = findViewById(R.id.iv_canvas);

        top_layout = findViewById(R.id.id_top_layout);
    }

    private void setFragment(int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i)
        {
            case 1:
                if (fragment1 == null)
                {
                    fragment1 = new Fragment1();
                    transaction.add(R.id.id_fragment,fragment1);
                }
                else
                {
                    transaction.show(fragment1);
                }
                break;
            case 2:
                if (fragment2 == null)
                {
                    fragment2 = new Fragment2();
                    transaction.add(R.id.id_fragment, fragment2);
                }
                else
                {
                    transaction.show(fragment2);
                }
                break;
            case 3:
                if (fragment3 == null)
                {
                    fragment3 = new Fragment3();
                    transaction.add(R.id.id_fragment, fragment3);
                }
                else
                {
                    transaction.show(fragment3);
                }
                break;
            case 4:
                if (fragment4 == null)
                {
                    fragment4 = new Fragment4();
                    transaction.add(R.id.id_fragment, fragment4);
                }
                else
                {
                    transaction.show(fragment4);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (fragment1 != null)
        {
            transaction.hide(fragment1);
        }
        if (fragment2 != null)
        {
            transaction.hide(fragment2);
        }
        if (fragment3 != null)
        {
            transaction.hide(fragment3);
        }
        if (fragment4 != null)
        {
            transaction.hide(fragment4);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
//        Log.i("A","" + v.getId());
        switch (v.getId()){
            case R.id.id_tv_fragment1:
                setFragment(1);
                break;
            case R.id.id_tv_fragment2:
                setFragment(2);
                break;
            case R.id.id_tv_fragment3:
                setFragment(3);
                break;
            case R.id.id_tv_fragment4:
                setFragment(4);
                break;

            case R.id.id_TL_tv1:
                showInfo();
                break;
            case R.id.id_TL_tv2:
                showInfo();
                break;
            case R.id.id_TL_tv3:
                showInfo();
                break;
            case R.id.id_TL_tv4:
                showInfo();
                break;
            case R.id.id_TL_tv5:
                showInfo();
                break;
            case R.id.id_TL_tv6:
                showInfo();
                break;
            case R.id.id_TL_tv7:
                showInfo();
                break;
            case R.id.id_TL_tv8:
//                showInfo();
                showWarning();
                break;
            default:
                break;
        }


    }

    private void showInfo() {
        String content;
        content = "......\n.....\n....";
        android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(ECGShowActivity.this);
        dialog.setTitle("当前禁忌");
        dialog.setMessage(content);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private  void showWarning(){
        android.app.AlertDialog.Builder alterDiaglog = new android.app.AlertDialog.Builder(ECGShowActivity.this,R.style.transBg);
        alterDiaglog.setView(R.layout.warning_layout);

        final android.app.AlertDialog dialog = alterDiaglog.create();

        dialog.show();
        //放在show()之后，不然有些属性是没有效果的，比如height和width
        Window dialogWindow = dialog.getWindow();
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
        assert dialogWindow != null;
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        // 设置高度和宽度
        //noinspection deprecation
        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.3
        //noinspection deprecation
        p.width = (int) (d.getWidth() * 0.5); // 宽度设置为屏幕的0.5

        p.gravity = Gravity.CENTER;//设置位置

//        p.alpha = 0.2f;//设置透明度
        dialogWindow.setAttributes(p);
        Button btn_ok = dialog.findViewById(R.id.id_warning_ok);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void setChronometer(Chronometer chronometer) {
        //设置起始时间和时间格式，然后开始计时
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setFormat("%s Elapsed");
        chronometer.start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        isTrue=false;
//        stopService(new Intent(ECGShowActivity.this,SendECGService.class));
        handler.removeCallbacks(runnable);
        stopenterinj();
        colseHreadshockable();
        EventBus.getDefault().unregister(this);
        stopAllService();
        stopService(completeinjection);
        stopService(completeshock);
        if(isWindowView){
            windowManager.removeView(windowview);
        }
        try {
            if(client!=null){
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(mDSTask!=null){
            mDSTask.cancel();
        }
        if(mDSTimer!=null){
            mDSTimer.cancel();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        try {

            if (myHandler != null) {
                is_show = false;
                Log.i("server", "stop update ui");
            }
            if (thread_hreat != null) {
                thread_hreat.interrupt();
                Log.i("server", "close client thread");
            }
            if (socket_hreat != null) {
                socket_hreat.close();
                Log.i("server", "client socket close");
            }
            if (intenthreat != null) {
                stopService(intenthreat);
                Log.i("server", "stop server");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showFloatingWindow() {
        if (Settings.canDrawOverlays(this)) {
            // 获取WindowManager服务
            windowManager = (WindowManager) this.getApplicationContext().getSystemService(WINDOW_SERVICE);

            // 新建悬浮窗控件
            // 设置LayoutParam
            layoutParams = new WindowManager.LayoutParams();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                layoutParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
            } else {
                layoutParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            }
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
            layoutParams.format = PixelFormat.RGBA_8888;
            layoutParams.width = 1800;
            layoutParams.height = 800;
            layoutParams.x = 300;
            layoutParams.y = 300;
            // 将悬浮窗控件添加到WindowManager
//            windowManager.addView(windowview, layoutParams);
            windowview.setOnTouchListener(new FloatingOnTouchListener());
        }
    }

    private class FloatingOnTouchListener implements View.OnTouchListener {
        private int x;
        private int y;
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x = (int) event.getRawX();
                    y = (int) event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    int nowX = (int) event.getRawX();
                    int nowY = (int) event.getRawY();
                    int movedX = nowX - x;
                    int movedY = nowY - y;
                    x = nowX;
                    y = nowY;
                    layoutParams.x = layoutParams.x + movedX;
                    layoutParams.y = layoutParams.y + movedY;
                    windowManager.updateViewLayout(view, layoutParams);
                    break;
                default:
                    break;
            }
            return false;
        }
    }

    int yourChoice;
    private void showSingleChoiceDialog(){
        final String[] items = { "成人","孕妇","儿童","婴儿" };
        yourChoice = -1;
        AlertDialog.Builder singleChoiceDialog =
                new AlertDialog.Builder(ECGShowActivity.this);
        singleChoiceDialog.setTitle("选择病患的类型");
        // 第二个参数是默认选项，此处设置为0
        singleChoiceDialog.setSingleChoiceItems(items, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        yourChoice = which;
                    }
                });
        singleChoiceDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (yourChoice != -1) {
                            Log.d("选择",""+items[yourChoice]+yourChoice);
                            switch (yourChoice){
                                case 0:
                                    chose_Adult();
                                    break;
                                case 1:
                                    chose_Pregnant();
                                    break;
                                case 2:
                                    chose_Child();
                                    break;
                                case 3:
                                    chose_Baby();
                                    break;
                            }

                        }
                    }
                });
        singleChoiceDialog.show();
    }

}
