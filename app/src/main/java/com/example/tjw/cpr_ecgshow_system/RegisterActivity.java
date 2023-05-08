package com.example.tjw.cpr_ecgshow_system;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.RegexUtils;
import com.example.tjw.cpr_ecgshow_system.Thread.ThreadHanderRegister;
import com.example.tjw.cpr_ecgshow_system.Types.UserRegisterData;


public class RegisterActivity extends Activity implements View.OnClickListener {
    private EditText doctorName;//医生姓名
    private EditText doctorPass;//医生登录密码
    private EditText doctorAge;//医生年龄
    private RadioGroup doctorSex;//医生性别
    private EditText doctorDuty;//职务
    private EditText doctorPhone;//医生联系电话
    private EditText doctorID;//医生身份证
    private EditText doctorDept;//医生所在科室
    private EditText doctorEmail;//医生邮箱
    private Button btn_register,btn_back;
    private String sex;
    private Toolbar toolbar;    //导航栏
    private ProgressDialog progressDialog;
    static String TAG="RegisterActivity";
    // 主线程的handler
    private Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 111){
                // 登入成功!
                if(progressDialog.isShowing())
                {
                    progressDialog.dismiss();
                }
                Intent intent=getIntent();
                intent.putExtra("username",msg.obj.toString());
                RegisterActivity.this.setResult(1,intent);
                RegisterActivity.this.finish();
            }else if(msg.what == 112){
                //用户名已存在
                Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
            }else if(msg.what == 113){
                //注册失败
                Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //找到对应的控件
        initView();


        //监听导航栏的返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initListeners();

    }

    private void initListeners() {
        //设置注册按钮监听事件
        btn_register.setOnClickListener(this);
        //返回按钮监听事件
        btn_back.setOnClickListener(this);
    }

    private void initView() {
        doctorName=findViewById(R.id.et_doctorName);
        doctorPass=findViewById(R.id.et_doctorPass);
        doctorAge=findViewById(R.id.et_doctorAge);
        doctorSex=findViewById(R.id.doctorSex);
        doctorDuty=findViewById(R.id.et_doctorDuty);
        doctorPhone=findViewById(R.id.et_doctorPhone);
        doctorID=findViewById(R.id.et_doctorID);
        doctorDept=findViewById(R.id.et_doctorDept);
        doctorEmail=findViewById(R.id.et_doctorEmail);
        btn_register=findViewById(R.id.btn_registerRegister);
        btn_back=findViewById(R.id.btn_back);
        toolbar=findViewById(R.id.toolbar);
    }


    //获取屏幕的宽度
    public static int getScreenWidth(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getWidth();
    }
    //获取屏幕的高度
    public static int getScreenHeight(Context context) {
        WindowManager manager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        return display.getHeight();
    }


    @Override
    public void onClick(View view) {
        if(view == this.btn_register){
            UserRegisterData userRegisterData = new UserRegisterData();

            userRegisterData.name=doctorName.getText().toString().trim();
            userRegisterData.password=doctorPass.getText().toString().trim();
            String age = doctorAge.getText().toString().trim();
            if(age.equals("")){
                Toast.makeText(this,"年龄不能为空!", Toast.LENGTH_LONG).show();
                return;
            }
            userRegisterData.age=Integer.parseInt(age);
            userRegisterData.job=doctorDuty.getText().toString().trim();
            userRegisterData.phone=doctorPhone.getText().toString().trim();
            userRegisterData.id=doctorID.getText().toString().trim();
            userRegisterData.department=doctorDept.getText().toString().trim();
            userRegisterData.email=doctorEmail.getText().toString().trim();


            // 参数合法性判断
            if(TextUtils.isEmpty(userRegisterData.name)){
                Toast.makeText(this,"用户名为空!",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(userRegisterData.password)){
                Toast.makeText(this,"密码为空!",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(userRegisterData.job)){
                Toast.makeText(this,"职位为空!",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(userRegisterData.id) == true || RegexUtils.isIDCard18(userRegisterData.id) == false){
                Toast.makeText(this,"身份证号码格式不符合要求!",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(userRegisterData.phone) == true || RegexUtils.isMobileSimple(userRegisterData.phone) == false){
                Toast.makeText(this,"手机号格式不符合要求!",Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(userRegisterData.email) == true || RegexUtils.isEmail(userRegisterData.email) == false){
                Toast.makeText(this,"电子邮件格式不正确!",Toast.LENGTH_LONG).show();
                return;
            }

            // 创建进度对话框
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("提示");
            progressDialog.setMessage("正在提交注册数据...");
            progressDialog.show();

            int sex_selector=doctorSex.getCheckedRadioButtonId();
            if(sex_selector== R.id.man){
                sex="男";
            }else{
                sex="女";
            }
            userRegisterData.sex = sex;
            // 创建线程对象
            ThreadHanderRegister threadHanderRegister = new ThreadHanderRegister(this.handler,userRegisterData,this);
            // 启动线程
            threadHanderRegister.start();
        }
        else if(this.btn_back == view){
            this.finish();
        }
    }
}

