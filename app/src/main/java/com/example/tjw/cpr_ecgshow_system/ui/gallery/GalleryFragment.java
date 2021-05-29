package com.example.tjw.cpr_ecgshow_system.ui.gallery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.DoctorActivity;
import com.example.tjw.cpr_ecgshow_system.LoginActivity;
import com.example.tjw.cpr_ecgshow_system.R;
import com.example.tjw.cpr_ecgshow_system.RegisterActivity;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.ui.home.HomeFragment;

public class GalleryFragment extends Fragment implements View.OnClickListener {


    private TextView  tv_doctorName;//医生姓名
    private TextView  tv_doctorPhone;//医生电话
    private TextView  tv_doctorSex;//医生性别
    private TextView  tv_doctorID;//医生身份证
    private TextView  tv_doctorAge;//医生年龄
    private TextView  tv_doctorDept;//医生所在科室
    private TextView  tv_doctorDuty;//医生职务
    private TextView  tv_doctorEmail;//医生邮箱

    private Button btn_update;//修改按钮




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {



        //接收登录界面传来的 name数据  并在数据库中 查找信息
        Intent getIntent=getActivity().getIntent();
        String name=getIntent.getStringExtra("doctorName");

        Doctor doctor=new Doctor();
        DoctorDao doctorDao=new DoctorDao(GalleryFragment.super.getContext());
        doctor = doctorDao.FindDoctorByName(name);
        System.out.println(""+doctor.toString()+"GalleryFragmentGalleryFragmentGalleryFragment");





//        galleryViewModel =
//                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(GalleryViewModel.class);
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);


//        final TextView textView = view.findViewById(R.id.et_doctorName);
//        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });


        Button btn_update = (Button) view.findViewById(R.id.btn_update);
        Button btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_update.setOnClickListener(this);
        btn_back.setOnClickListener(this);

        //找到对应的控件
        tv_doctorName = view.findViewById(R.id.et_doctorName);
        tv_doctorPhone = view.findViewById(R.id.et_doctorPhone);
        tv_doctorSex= view.findViewById(R.id.et_doctorSex);
        tv_doctorID= view.findViewById(R.id.et_doctorID);
        tv_doctorAge= view.findViewById(R.id.et_doctorAge);
        tv_doctorDept= view.findViewById(R.id.et_doctorDept);
        tv_doctorDuty= view.findViewById(R.id.et_doctorDuty);
        tv_doctorEmail= view.findViewById(R.id.et_doctorEmail);




        if(doctor.getDoctorName()!=null){
            tv_doctorName.setText(doctor.getDoctorName());
        }
        if(doctor.getDoctorPhone()!=null){
            tv_doctorPhone.setText(doctor.getDoctorPhone());
        }
        if(doctor.getDoctorSex()!=null){
            tv_doctorSex.setText(doctor.getDoctorSex().trim());
        }
        if(doctor.getDoctorID()!=null){
            tv_doctorID.setText(doctor.getDoctorID());
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





        return view;
    }


    //自定义修改医生信息对话框
    private void updateDialog(){
        AlertDialog.Builder updateDialog = new AlertDialog.Builder(GalleryFragment.super.getContext());
        final View dialogView = LayoutInflater.from(GalleryFragment.super.getContext()).inflate(R.layout.update_doctor_dialog,null);
        updateDialog.setTitle("个人信息修改");
        updateDialog.setView(dialogView);


        EditText update_doctorPhone=dialogView.findViewById(R.id.update_doctorPhone);
        EditText update_doctorID=dialogView.findViewById(R.id.update_doctorID);
        EditText update_doctorAge=dialogView.findViewById(R.id.update_doctorAge);
        EditText update_doctorDept=dialogView.findViewById(R.id.update_doctorDept);
        EditText update_doctorDuty=dialogView.findViewById(R.id.update_doctorDuty);
        EditText update_doctorEmail=dialogView.findViewById(R.id.update_doctorEmail);

        update_doctorPhone.setText(tv_doctorPhone.getText().toString());
        update_doctorID.setText(tv_doctorID.getText().toString());
        update_doctorAge.setText(tv_doctorAge.getText().toString());
        update_doctorDept.setText(tv_doctorDept.getText().toString());
        update_doctorDuty.setText(tv_doctorDuty.getText().toString());
        update_doctorEmail.setText(tv_doctorEmail.getText().toString());


        updateDialog.setNegativeButton("确定", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {



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
                    DoctorDao doctorDao=new DoctorDao(GalleryFragment.super.getContext());
                    Boolean b =doctorDao.updateDoctor(phone,id,age,dept,duty,email,name);
                    if (b){
                        Toast.makeText(GalleryFragment.super.getActivity(),"修改成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(GalleryFragment.super.getActivity(),"修改失败",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        updateDialog.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.i("TAG","点击了返回按钮");
            }
        });


        updateDialog.show();
    }

    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id){
            case R.id.btn_update:
                Log.i("TAG","点击了修改按钮");
                updateDialog();
                //fragment 跳转action
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);

                break;
            case R.id.btn_back:
                Log.i("TAG","点击了返回按钮");
                //fragment 跳转action btn_back
//                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                startActivity(intent);
                getActivity().getSupportFragmentManager().popBackStack();
                break;

        }
    }



}