package com.example.tjw.cpr_ecgshow_system;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;

/**
 * 医生信息的Activity
 *
 */
public class DoctorActivity extends AppCompatActivity {
    private TextView  tv_doctorName;//医生姓名
    private TextView  tv_doctorPhone;//医生电话
    private TextView  tv_doctorSex;//医生性别
    private TextView  tv_doctorID;//医生身份证
    private TextView  tv_doctorAge;//医生年龄
    private TextView  tv_doctorDept;//医生所在科室
    private TextView  tv_doctorDuty;//医生职务
    private TextView  tv_doctorEmail;//医生邮箱

    private Button btn_update;//修改按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        //控件初始化
        initWidget();
        //接收传来的对象数据
        Doctor doctor=new Doctor();
        Intent intent=getIntent();
        doctor=(Doctor) intent.getSerializableExtra("doctorObject");
        //
        System.out.println(""+doctor.toString());

        if(doctor.getDoctorName()!=null){
            tv_doctorName.setText(doctor.getDoctorName());
        }
        if(doctor.getDoctorPhone()!=null){
            tv_doctorPhone.setText(doctor.getDoctorPhone());
        }
        if(doctor.getDoctorAge()!=null){
            tv_doctorAge.setText(""+doctor.getDoctorAge());
        }
        if(doctor.getDoctorDept()!=null){
            tv_doctorDept.setText(doctor.getDoctorDept());
        }
        if(doctor.getDoctorDuty()!=null){
            tv_doctorDuty.setText(doctor.getDoctorDuty());
        }
        if(doctor.getDoctorEmail()!=null){
            tv_doctorEmail.setText(doctor.getDoctorEmail());
        }
        if(doctor.getDoctorID()!=null){
            tv_doctorID.setText(doctor.getDoctorID());
        }
        if(doctor.getDoctorSex()!=null){
            tv_doctorSex.setText(doctor.getDoctorSex().trim());
        }
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDialog();
            }
        });


    }
    //自定义修改医生信息对话框
    private void updateDialog(){
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(this.getApplicationContext());
        final View dialogView = LayoutInflater.from(this.getApplicationContext()).inflate(R.layout.update_doctor_dialog,null);
        updateDialog.setTitle("修改医生信息对话框");
        updateDialog.setView(dialogView);
        updateDialog.setPositiveButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                EditText update_doctorPhone=dialogView.findViewById(R.id.update_doctorPhone);
                EditText update_doctorID=dialogView.findViewById(R.id.update_doctorID);
                EditText update_doctorAge=dialogView.findViewById(R.id.update_doctorAge);
                EditText update_doctorDept=dialogView.findViewById(R.id.update_doctorDept);
                EditText update_doctorDuty=dialogView.findViewById(R.id.update_doctorDuty);
                EditText update_doctorEmail=dialogView.findViewById(R.id.update_doctorEmail);
                String name=tv_doctorName.getText().toString();
                System.out.println(name);


                String phone=update_doctorPhone.getText().toString().trim();
                String id=update_doctorID.getText().toString().trim();
                String age=update_doctorAge.getText().toString().trim();
                String dept=update_doctorDept.getText().toString().trim();
                String duty=update_doctorDuty.getText().toString().trim();
                String email=update_doctorEmail.getText().toString().trim();

                tv_doctorPhone.setText(phone);
                tv_doctorID.setText(id);
                tv_doctorAge.setText(age);
                tv_doctorDept.setText(dept);
                tv_doctorDuty.setText(duty);
                tv_doctorEmail.setText(email);
                try{
                    DoctorDao doctorDao=new DoctorDao(DoctorActivity.this);
                    doctorDao.updateDoctor(phone,id,age,dept,duty,email,name);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        updateDialog.show();
    }
    //控件初始化
    private void initWidget(){
        //找到对应的控件
        tv_doctorName = findViewById(R.id.tv_doctorName);
        tv_doctorPhone = findViewById(R.id.tv_doctorPhone);
        tv_doctorSex= findViewById(R.id.tv_doctorSex);
        tv_doctorID= findViewById(R.id.tv_doctorID);
        tv_doctorAge= findViewById(R.id.tv_doctorAge);
        tv_doctorDept= findViewById(R.id.tv_doctorDept);
        tv_doctorDuty= findViewById(R.id.tv_doctorDuty);
        tv_doctorEmail= findViewById(R.id.tv_doctorEmail);

        btn_update=findViewById(R.id.btn_update);
    }
}
