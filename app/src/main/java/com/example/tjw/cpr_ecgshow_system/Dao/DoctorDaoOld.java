package com.example.tjw.cpr_ecgshow_system.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.utils.DatabaseHelper;

/**
 * 该类包含对医生表操作的方法
 *
 */
public class DoctorDaoOld {
    private DatabaseHelper dbHelper;
    public DoctorDaoOld(Context context){
        dbHelper=new DatabaseHelper(context);
    }





    //登录逻辑判断
    public boolean login(String username,String password){
        SQLiteDatabase db=dbHelper.getReadableDatabase();//打开一个可读的数据库
        String sql="select * from tb_doctor where doctorName=? and doctorPass=?";
        Cursor cursor=db.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            cursor.close();
            return true;
        }
        return false;
    }
    //注册逻辑判断
    public boolean register(Doctor doctor){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="insert into tb_doctor(doctorName,doctorPass,doctorAge,doctorSex,doctorDuty,doctorPhone,doctorID,doctorDept,doctorEmail) values(?,?,?,?,?,?,?,?,?)";
        Object obj[]={doctor.getDoctorName(),doctor.getDoctorPass(),doctor.getDoctorAge(),doctor.getDoctorSex(),doctor.getDoctorDuty(),doctor.getDoctorPhone(),doctor.getDoctorID(),doctor.getDoctorDept(),doctor.getDoctorEmail()};
        db.execSQL(sql, obj);
        return true;
    }
    //检查用户名是否存在
    public boolean CheckIsDataAlreadyInDBorNot(String value) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String Query = "Select * from tb_doctor where doctorName =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //根据医生姓名查询并返回该医生数据对象
    public Doctor FindDoctorByName(String name){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        System.out.println(">>>>>>>>>>>>>>> "+dbHelper);
        String Query = "Select * from tb_doctor where doctorName =?";
        Doctor doctor=new Doctor();
        doctor.setDoctorName(name);

        Cursor cursor=db.rawQuery(Query,new String[]{name});
        if(cursor.moveToFirst()){
            int passColumn=cursor.getColumnIndex("doctorPass");
            String pass = cursor.getString(passColumn);
            doctor.setDoctorPass(pass);

            int ageColumn=cursor.getColumnIndex("doctorAge");
            int age = cursor.getInt(ageColumn);
            doctor.setDoctorAge(age);

            int sexColumn=cursor.getColumnIndex("doctorSex");
            String sex = cursor.getString(sexColumn);
            doctor.setDoctorSex(sex);

            int dutyColumn=cursor.getColumnIndex("doctorDuty");
            String duty=cursor.getString(dutyColumn);
            doctor.setDoctorDuty(duty);

            int phoneColumn=cursor.getColumnIndex("doctorPhone");
            String phone=cursor.getString(phoneColumn);
            doctor.setDoctorPhone(phone);

            int IDColumn=cursor.getColumnIndex("doctorID");
            String ID=cursor.getString(IDColumn);
            doctor.setDoctorID(ID);

            int deptColumn=cursor.getColumnIndex("doctorDept");
            String dept=cursor.getString(deptColumn);
            doctor.setDoctorDept(dept);

            int emailColumn=cursor.getColumnIndex("doctorEmail");
            String email=cursor.getString(emailColumn);
            doctor.setDoctorEmail(email);
        }
        return doctor;
    }

    //修改医生基本信息
    public void updataDoctor(String pass,String phone,String duty,String dept,String email,String name){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String update = "update tb_doctor set doctorPass=?,doctorPhone=?,doctorDuty=?,doctorDept=?,doctorEmail=? where doctorName =?";
        db.execSQL(update,new String[]{pass,phone,duty,dept,email,name});
    }

    public void updateDoctor(String phone,String id,String age,String dept,String duty,String email,String name){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String update = "update tb_doctor set doctorPhone=?,doctorID=?,doctorAge=?,doctorDept=?,doctorDuty=?,doctorEmail=? where doctorName =?";
        db.execSQL(update,new String[]{phone,id,age,dept,duty,email,name});
    }

    public void updatePass(String newPass1, String name) {
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String update = "update tb_doctor set doctorPass=? where doctorName =?";
        db.execSQL(update, new String[]{newPass1, name});

    }
}
