package com.example.tjw.cpr_ecgshow_system;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.Dao.PatientDao;
import com.example.tjw.cpr_ecgshow_system.domain.Patient;

import java.util.Objects;

public class PatientActivity extends AppCompatActivity {
    private TextView tv_patientName;//病人姓名
    private TextView tv_patientPhone;//家属联系电话
    private TextView tv_patientSex;//病人性别
    private TextView tv_patientID;//病人身份证
    private TextView tv_patientAge;//病人年龄
    private TextView tv_patientDoc;//病人主治医生
    private TextView tv_patientRoom;//病人房间号
    private TextView tv_patientBed;//病人病床号
    private TextView tv_patientPmh;//病人既往病史
    private TextView tv_patientState;//病人病况

    private Button btn_find_patient;
    private Button btn_update_patient;
    private Button btn_back;
    private Button btn_add_patient;

    private Toolbar toolbar;    //导航栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        initWidget();
        //添加按钮点击事件
        btn_add_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPatientDialog();
            }
        });
        //查找按钮点击事件
        btn_find_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findPatientDialog();
            }
        });
        //修改按钮点击事件
        btn_update_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePatientDialog();
            }
        });
        //返回按钮监听事件
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //监听导航栏的返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }




    //自定义添加病人对话框
    private void addPatientDialog() {
        AlertDialog.Builder addDialog = new AlertDialog.Builder(PatientActivity.this);
        final View addDialogView = LayoutInflater.from(PatientActivity.this).inflate(R.layout.add_patient_dialog, null);
        addDialog.setTitle("添加病人信息");
        addDialog.setView(addDialogView);





        addDialog.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText add_patientName=addDialogView.findViewById(R.id.add_patientName);
                EditText add_patientPhone=addDialogView.findViewById(R.id.add_patientPhone);
                EditText add_patientSex=addDialogView.findViewById(R.id.add_patientSex);
                EditText add_patientID=addDialogView.findViewById(R.id.add_patientID);
                EditText add_patientAge=addDialogView.findViewById(R.id.add_patientAge);
                EditText add_patientDoc=addDialogView.findViewById(R.id.add_patientDoc);
                EditText add_patientRoom=addDialogView.findViewById(R.id.add_patientRoom);
                EditText add_patientBed=addDialogView.findViewById(R.id.add_patientBed);
                EditText add_patientPmh=addDialogView.findViewById(R.id.add_patientPmh);
                EditText add_patientState=addDialogView.findViewById(R.id.add_patientState);

                String name=add_patientName.getText().toString().trim();
                String phone=add_patientPhone.getText().toString().trim();
                String sex=add_patientSex.getText().toString().trim();
                String ID=add_patientID.getText().toString().trim();

                Integer age=null,room=null,bed=null;

                String ageStr=add_patientAge.getText().toString().trim();
                String roomStr=add_patientRoom.getText().toString().trim();
                String bedStr=add_patientBed.getText().toString().trim();
                if(!ageStr.equals("")){
                    age=Integer.valueOf(ageStr);
                }
                if(!roomStr.equals("")){
                    room=Integer.valueOf(roomStr);
                }
                if(!bedStr.equals("")){
                    bed=Integer.valueOf(bedStr);
                }
                String doc=add_patientDoc.getText().toString().trim();
                String pmh=add_patientPmh.getText().toString().trim();
                String state=add_patientState.getText().toString().trim();

                if(Objects.equals(name, ""))
                {
                    Toast.makeText(addDialogView.getContext(),"姓名不能为空",Toast.LENGTH_SHORT).show();
                }else if (Objects.equals(sex, "")){
                    Toast.makeText(PatientActivity.this,"性别不能为空",Toast.LENGTH_SHORT).show();
                }else if (Objects.equals(ID, "")){
                    Toast.makeText(PatientActivity.this,"身份证不能为空",Toast.LENGTH_SHORT).show();
                }else if (Objects.equals(ageStr, "")){
                    Toast.makeText(PatientActivity.this,"年龄不能为空",Toast.LENGTH_SHORT).show();
                }else {

                    //添加进入数据库
                    PatientDao patientDao=new PatientDao(PatientActivity.this);
                    try{
                       Boolean b = patientDao.addPatient(name,phone,sex,ID,age,doc,room,bed,pmh,state);
                       if (b){
                           Toast.makeText(PatientActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                       }else {
                           Toast.makeText(PatientActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                       }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }




            }
        });

        addDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PatientActivity.this,"已取消添加",Toast.LENGTH_SHORT).show();
            }
        });

        addDialog.show();
    }

    //自定义查找病人对话框
    private void findPatientDialog(){

                View ff = findViewById(R.id.layout_out);//动态显示隐藏查询内容

                EditText et_find_patientName=findViewById(R.id.et_find_patientName);    //获取输入框
                String search_name=et_find_patientName.getText().toString().trim();     //获取输入框名字

                PatientDao patientDao=new PatientDao(PatientActivity.this);
                Patient patient=new Patient();


                if (Objects.equals(search_name, "")){

                    ff.setVisibility(View.GONE);                //内容不可见
                    Toast.makeText(PatientActivity.this,"请输入要查询的信息",Toast.LENGTH_SHORT).show();
                }else if(!patientDao.CheckIsPatientExists(search_name)){


                    ff.setVisibility(View.GONE);            //内容不可见

                    Toast.makeText(PatientActivity.this,"您查询的患者不存在",Toast.LENGTH_SHORT).show();
                }else
                {
                    try{

                        ff.setVisibility(View.VISIBLE);     //内容可见

                        patient=patientDao.findPatient(search_name);

                        tv_patientName.setHint(patient.getPatientName());
                        tv_patientAge.setHint(patient.getPatientAge()+"");
                        tv_patientBed.setHint(patient.getPatientBed()+"");
                        tv_patientDoc.setHint(patient.getPatientDoc());
                        tv_patientID.setHint(patient.getPatientID());
                        tv_patientRoom.setHint(patient.getPatientRoom()+"");
                        tv_patientSex.setHint(patient.getPatientSex());
                        tv_patientPmh.setHint(patient.getPatientPmh());
                        tv_patientPhone.setHint(patient.getPatientPhone());
                        tv_patientState.setHint(patient.getPatientState());
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }





    //自定义修改病人信息对话框
    private void updatePatientDialog(){
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(PatientActivity.this);
        final View updateDialogView = LayoutInflater.from(PatientActivity.this).inflate(R.layout.update_patient_dialog,null);
        updateDialog.setTitle("修改病人信息");
        updateDialog.setView(updateDialogView);

        EditText update_patientRoom=updateDialogView.findViewById(R.id.update_patientRoom);
        EditText update_patientBed=updateDialogView.findViewById(R.id.update_patientBed);
        EditText update_patientState=updateDialogView.findViewById(R.id.update_patientState);
        EditText update_patientDoc=updateDialogView.findViewById(R.id.update_patientDoc);
        EditText update_patientPhone=updateDialogView.findViewById(R.id.update_patientPhone);

        //先获取要修改的内容 并显示
        update_patientRoom.setText(tv_patientRoom.getHint().toString().trim());
        update_patientBed.setText(tv_patientBed.getHint().toString().trim());
        update_patientState.setText(tv_patientState.getHint().toString().trim());
        update_patientDoc.setText(tv_patientDoc.getHint().toString().trim());
        update_patientPhone.setText(tv_patientPhone.getHint().toString().trim());


        updateDialog.setNegativeButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {


                String name=tv_patientName.getHint().toString();
                Log.i("name",name);

                String roomStr=update_patientRoom.getText().toString().trim();
                System.out.println("Room"+roomStr);
                Integer room=null;
                if(!roomStr.equals("")){
                    room=Integer.parseInt(roomStr);
                }
                Integer bed=null;
                String bedStr=update_patientBed.getText().toString().trim();
                if(!bedStr.equals("")){
                    bed=Integer.parseInt(bedStr);
                }

                String doc=update_patientDoc.getText().toString().trim();
                String phone=update_patientPhone.getText().toString().trim();
                String state=update_patientState.getText().toString().trim();

                //Log.i("信息",name+"\\"+roomStr+"\\"+room+'\\'+bed+'\\'+doc+'\\'+phone+'\\'+state);


                try{
                    if(!Objects.equals(name, "")){
                        PatientDao patientDao=new PatientDao(PatientActivity.this);
                        Boolean b=patientDao.updatePatient(room,bed,doc,phone,state,name);
                        if (b){
                            Toast.makeText(PatientActivity.this,"修改成功!",Toast.LENGTH_SHORT).show();
                            tv_patientRoom.setHint(roomStr);
                            tv_patientBed.setHint(bedStr);
                            tv_patientDoc.setHint(doc);
                            tv_patientPhone.setHint(phone);
                            tv_patientState.setHint(state);
                            Log.i("病人信息修改成功", String.valueOf(b));
                        }else {
                            Toast.makeText(PatientActivity.this,"修改失败!",Toast.LENGTH_SHORT).show();
                            Log.i("病人信息修改失败", String.valueOf(b));
                        }



                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        updateDialog.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        updateDialog.show();
    }





    //控件初始化
    private void initWidget(){
        //找到对应的控件
        tv_patientName = findViewById(R.id.tv_patientName);
        tv_patientPhone = findViewById(R.id.tv_patientPhone);
        tv_patientSex= findViewById(R.id.tv_patientSex);
        tv_patientID= findViewById(R.id.tv_patientID);
        tv_patientAge= findViewById(R.id.tv_patientAge);
        tv_patientDoc= findViewById(R.id.tv_patientDoc);
        tv_patientRoom= findViewById(R.id.tv_patientRoom);
        tv_patientBed= findViewById(R.id.tv_patientBed);
        tv_patientPmh= findViewById(R.id.tv_patientPmh);
        tv_patientState= findViewById(R.id.tv_patientState);
        btn_add_patient=findViewById(R.id.btn_add_patient);
        btn_update_patient=findViewById(R.id.btn_update_patient);
        btn_find_patient=findViewById(R.id.btn_find_patient);
        btn_back=findViewById(R.id.btn_back);
        toolbar=findViewById(R.id.toolbar);

    }
}
