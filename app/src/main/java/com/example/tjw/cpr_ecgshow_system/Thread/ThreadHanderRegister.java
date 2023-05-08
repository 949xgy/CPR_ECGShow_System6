package com.example.tjw.cpr_ecgshow_system.Thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.Types.UserRegisterData;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;


public class ThreadHanderRegister extends Thread{
    Handler handler = null; // UI线程的Handler
    UserRegisterData userRegisterData; // 登入参数
    Context context;
    public ThreadHanderRegister(Handler arg_handler,UserRegisterData arg_userRegisterData,Context arg_context){
        this.handler = arg_handler;
        this.userRegisterData = arg_userRegisterData;
        this.context = arg_context;
    }
    @Override
    public void run() {
        super.run();
        DoctorDao doctorDao=new DoctorDao(this.context);
        Doctor doctor=new Doctor();

        doctor.setDoctorName(this.userRegisterData.name);
        doctor.setDoctorPass(this.userRegisterData.password);
        doctor.setDoctorAge(this.userRegisterData.age);

        doctor.setDoctorDuty(this.userRegisterData.job);
        doctor.setDoctorID(this.userRegisterData.id);
        doctor.setDoctorDept(this.userRegisterData.department);
        doctor.setDoctorEmail(this.userRegisterData.email);
        doctor.setDoctorSex(this.userRegisterData.sex);
        doctor.setDoctorPhone(this.userRegisterData.phone);

        try{
            //判断医生是否存在数据库
            if(!doctorDao.CheckIsDataAlreadyInDBorNot(this.userRegisterData.name)){
                doctorDao.register(doctor);
                // 向主线程提交注册成功消息
                Message message = new Message();
                message.what = 111;
                message.obj = doctor.getDoctorName();
                handler.sendMessage(message);
                /**/
            }else{
                handler.sendEmptyMessage(112);
            }

        }catch (Exception e){
            handler.sendEmptyMessage(113);
        }
    }
}
