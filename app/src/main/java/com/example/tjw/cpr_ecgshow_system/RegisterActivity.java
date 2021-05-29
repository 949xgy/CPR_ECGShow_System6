package com.example.tjw.cpr_ecgshow_system;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);




        //设置字体
        TextView tv_re1=(TextView) findViewById(R.id.text_bar);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/KT.ttf");
        tv_re1.setTypeface(typeface);


        //找到对应的控件
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








        WindowManager wm = getWindowManager();
        Display d = wm.getDefaultDisplay();

        //拿到布局参数
        ViewGroup.LayoutParams d1 = doctorName.getLayoutParams();
        ViewGroup.LayoutParams d2 = doctorPass.getLayoutParams();
        ViewGroup.LayoutParams d3 = doctorPhone.getLayoutParams();
        ViewGroup.LayoutParams d4 = doctorID.getLayoutParams();
        ViewGroup.LayoutParams d5 = doctorAge.getLayoutParams();
        ViewGroup.LayoutParams d6 = doctorDept.getLayoutParams();
        ViewGroup.LayoutParams d7 = doctorDuty.getLayoutParams();
        ViewGroup.LayoutParams d8 = doctorEmail.getLayoutParams();

        ViewGroup.LayoutParams d9 = btn_register.getLayoutParams();
        ViewGroup.LayoutParams d10 = btn_back.getLayoutParams();
        d1.width=d.getWidth()/2-1;
        d2.width=d.getWidth()/2-1;
        d3.width=d.getWidth()/2-1;
        d4.width=d.getWidth()/2-1;
        d5.width=d.getWidth()/2-1;
        d6.width=d.getWidth()/2-1;
        d7.width=d.getWidth()/2-1;
        d8.width=d.getWidth()/2-1;

        // 判断Android当前的屏幕是横屏还是竖屏。横竖屏判断  
        if(this.getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT)
        {
            //竖屏 
            Log.i("TAG","竖屏");
            Log.i("TAG",d.getWidth()+"//"+d.getHeight());
            d1.height=d.getHeight()/9;
            d2.height=d.getHeight()/9;
            d3.height=d.getHeight()/9;
            d4.height=d.getHeight()/9;
            d5.height=d.getHeight()/9;
            d6.height=d.getHeight()/9;
            d7.height=d.getHeight()/9;
            d8.height=d.getHeight()/9;

        }else {
            //横屏  
            Log.i("TAG","横屏");
            Log.i("TAG",d.getWidth()+"//"+d.getHeight());
            d1.height=d.getHeight()/9;
            d2.height=d.getHeight()/9;
            d3.height=d.getHeight()/9;
            d4.height=d.getHeight()/9;
            d5.height=d.getHeight()/9;
            d6.height=d.getHeight()/9;
            d7.height=d.getHeight()/9;
            d8.height=d.getHeight()/9;


        }








        //监听导航栏的返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置注册按钮监听事件
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=doctorName.getText().toString().trim();
                String pass=doctorPass.getText().toString().trim();
                String age=doctorAge.getText().toString().trim();
                String duty=doctorDuty.getText().toString().trim();
                String phone=doctorPhone.getText().toString().trim();
                String ID=doctorID.getText().toString().trim();
                String dept=doctorDept.getText().toString().trim();
                String email=doctorEmail.getText().toString().trim();

                int sex_selector=doctorSex.getCheckedRadioButtonId();
                if(sex_selector== R.id.man){
                    sex="男";
                }
                if(sex_selector== R.id.woman){
                    sex="女";
                }
                DoctorDao doctorDao=new DoctorDao(RegisterActivity.this);
                Doctor doctor=new Doctor();

                doctor.setDoctorName(name);
                doctor.setDoctorPass(pass);
                if(!Objects.equals(age, "")){
                    doctor.setDoctorAge(Integer.parseInt(age));
                }else{
                    Toast.makeText(RegisterActivity.this,"年龄不能为空",Toast.LENGTH_SHORT).show();
                }

                doctor.setDoctorDuty(duty);
                doctor.setDoctorID(ID);
                doctor.setDoctorDept(dept);
                doctor.setDoctorEmail(email);
                doctor.setDoctorSex(sex);
                doctor.setDoctorPhone(phone);

                try{
                    //判断医生是否存在数据库
                    if(doctorDao.CheckIsDataAlreadyInDBorNot(name)==false){
                        doctorDao.register(doctor);
                        Intent intent=getIntent();
                        intent.putExtra("username",doctor.getDoctorName());
                        RegisterActivity.this.setResult(1,intent);
                        RegisterActivity.this.finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"用户名已存在",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    Toast.makeText(RegisterActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //返回按钮监听事件
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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


}

