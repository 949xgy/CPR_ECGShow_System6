package com.example.tjw.cpr_ecgshow_system.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.LoginActivity;
import com.example.tjw.cpr_ecgshow_system.R;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.ui.gallery.GalleryFragment;

import java.util.Objects;

public class SlideshowFragment extends Fragment implements View.OnClickListener{

    private SlideshowViewModel slideshowViewModel;

    private TextView  tv_doctorOldPass;//原密码框
    private TextView  tv_doctorNewPass1;//新密码框
    private TextView  tv_doctorNewPass2;//确认新密码框

    private Button btn_update_pass;//修改按钮
    private Button btn_back;//返回按钮

    String DatePass,OldPass,NewPass1,NewPass2;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);


//        final TextView textView = root.findViewById(R.id.text_slideshow);
//        slideshowViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        btn_update_pass = (Button) view.findViewById(R.id.btn_update_pass);
        btn_back = (Button) view.findViewById(R.id.btn_back);
        btn_update_pass.setOnClickListener(this);
        btn_back.setOnClickListener(this);



        return view;
    }



    private void update_pass() {

        //接收登录界面传来的 name数据  并在数据库中 查找信息
        Intent getIntent=getActivity().getIntent();
        String name=getIntent.getStringExtra("doctorName");//获取用户名
        Doctor doctor=new Doctor();
        DoctorDao doctorDao=new DoctorDao(SlideshowFragment.super.getContext());
        doctor = doctorDao.FindDoctorByName(name);
        DatePass=doctor.getDoctorPass();     //旧密码

        tv_doctorOldPass=getView().findViewById(R.id.update_doctorOldPass);
        tv_doctorNewPass1=getView().findViewById(R.id.update_doctorNewPass1);
        tv_doctorNewPass2=getView().findViewById(R.id.update_doctorNewPass2);

        OldPass=tv_doctorOldPass.getText().toString();      //  输入的旧密码
        NewPass1=tv_doctorNewPass1.getText().toString();    //  输入的新密码
        NewPass2=tv_doctorNewPass2.getText().toString();    //  输入的再次确定新密码

        Log.i("测试:",OldPass+"\\"+NewPass1+"\\"+NewPass2);


        if(Objects.equals(OldPass, "") || Objects.equals(NewPass1, "") || Objects.equals(NewPass2, ""))
        {
            //修改密码不能为空
            Toast.makeText(SlideshowFragment.super.getActivity(), "不能为空", Toast.LENGTH_LONG).show();
        }
        else if(!Objects.equals(DatePass, OldPass))
        {
            //原始密码正确
            Toast.makeText(SlideshowFragment.super.getActivity(), "密码错误，请重新输入", Toast.LENGTH_LONG).show();
        }
        else if(!Objects.equals(NewPass1, NewPass2))
        {
            //两次密码要相同
            Toast.makeText(SlideshowFragment.super.getActivity(), "两次密码不一致，请重新输入", Toast.LENGTH_LONG).show();
        }else {

            try{

                Boolean b =doctorDao.updatePass(NewPass1, name);
                if (b){
                    Toast.makeText(SlideshowFragment.super.getActivity(), "修改成功", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(SlideshowFragment.super.getActivity(), "修改失败", Toast.LENGTH_LONG).show();
                }


                getActivity().getSupportFragmentManager().popBackStack();
            }catch (Exception e){
                e.printStackTrace();
            }


        }














    }



    @Override
    public void onClick(View view) {
        int id  = view.getId();
        switch (id){
            case R.id.btn_update_pass:
                Log.i("TAG","点击了修改密码按钮");
                update_pass();
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