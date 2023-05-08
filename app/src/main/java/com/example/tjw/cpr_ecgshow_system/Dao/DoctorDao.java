package com.example.tjw.cpr_ecgshow_system.Dao;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.LoginActivity;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.utils.DButils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类包含对医生表操作的方法
 *
 */
public class DoctorDao {
    private Connection connMysql;
    private Boolean bo =false;
    public DoctorDao(Context context){

    }

    //登录逻辑判断
    public Doctor login(String username,String password){
            Doctor doctor = new Doctor();
            bo = false;
                Thread aa =new Thread(){
                    @Override
                    public void run() {
                        try {
                           connMysql = DButils.ConnMysql();
                            String sql="select * from tb_doctor where doctorName=? and doctorPass=?";
                            PreparedStatement pst=connMysql.prepareStatement(sql);
                            pst.setString(1,username);
                            pst.setString(2,password);
                            ResultSet rs=pst.executeQuery();

                            bo = rs.next();
                            doctor.setDoctorName(rs.getString(2));
                            doctor.setDoctorEmail(rs.getString(10));
                            rs.close();
                            pst.close();
                            connMysql.close();
                        } catch (SQLException  throwables) {
                            throwables.printStackTrace();
                        }
                    }
                };

                aa.start();


        while (aa.isAlive()){

        }
        if(bo){
            return doctor;
        }else {
            return null;
        }
    }

    //注册逻辑判断
    public boolean register(Doctor doctor){

        int result=0;
        bo = false;
      Thread aa = new Thread(){
            @Override
            public void run() {
                try {

                    connMysql = DButils.ConnMysql();
                    String sql="insert into tb_doctor(doctorName,doctorPass,doctorAge,doctorSex,doctorDuty,doctorPhone,doctorID,doctorDept,doctorEmail) values(?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst=connMysql.prepareStatement(sql);
                    pst.setString(1,doctor.getDoctorName());
                    pst.setString(2,doctor.getDoctorPass());
                    pst.setInt(3,doctor.getDoctorAge());
                    pst.setString(4,doctor.getDoctorSex());
                    pst.setString(5,doctor.getDoctorDuty());
                    pst.setString(6,doctor.getDoctorPhone());
                    pst.setString(7,doctor.getDoctorID());
                    pst.setString(8,doctor.getDoctorDept());
                    pst.setString(9,doctor.getDoctorEmail());

                    int result=pst.executeUpdate();
                    if (result>0){
                        bo = true;
                    }
                    pst.close();
                    connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        };
      aa.start();


        while (aa.isAlive()){
            System.out.println("准备返回aa.isAlive()----->"+aa.isAlive());
        }

        System.out.println("准备返回register----->"+bo);
        return bo;
    }

    //检查用户名是否存在
    public boolean CheckIsDataAlreadyInDBorNot(String value) {

        bo = false;
       Thread aa=new Thread(){
            @Override
            public void run() {
                try {

                    connMysql = DButils.ConnMysql();
                    String Query = "Select * from tb_doctor where doctorName =?";
                    PreparedStatement pst=connMysql.prepareStatement(Query);
                    pst.setString(1,value);
                    ResultSet result = pst.executeQuery();

                    if (result.next()){
                        bo = true;
                    }

                    result.close();
                    pst.close();
                    connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        };
       aa.start();


        while (aa.isAlive()){
            System.out.println("准备返回aa.isAlive()----->"+aa.isAlive());
        }

        System.out.println("准备返回CheckIsDataAlreadyInDBorNot------->"+bo);
        return bo;
    }

    //根据医生姓名查询并返回该医生数据对象
    public Doctor FindDoctorByName(String name){

        Doctor doctor=new Doctor();
        doctor.setDoctorName(name);

      Thread aa= new Thread(){
            @Override
            public void run() {
                try {
                   connMysql = DButils.ConnMysql();
                    String sql="Select * from tb_doctor where doctorName =?";
                    PreparedStatement pst=connMysql.prepareStatement(sql);
                    pst.setString(1,name);
                    ResultSet rs=pst.executeQuery();
                    while(rs.next()){
                        // 通过字段检索
                        String pass = rs.getString("doctorPass");
                        int age  = rs.getInt("doctorAge");
                        String sex = rs.getString("doctorSex");
                        String duty = rs.getString("doctorDuty");
                        String phone = rs.getString("doctorPhone");
                        String ID = rs.getString("doctorID");
                        String dept = rs.getString("doctorDept");
                        String email = rs.getString("doctorEmail");

                        doctor.setDoctorPass(pass);
                        doctor.setDoctorAge(age);
                        doctor.setDoctorSex(sex);
                        doctor.setDoctorDuty(duty);
                        doctor.setDoctorPhone(phone);
                        doctor.setDoctorID(ID);
                        doctor.setDoctorDept(dept);
                        doctor.setDoctorEmail(email);
                        System.out.println("doctor设置成功");

                    }

                    rs.close();
                    pst.close();
                  connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        };
        aa.start();

        while (aa.isAlive()){

        }

        return doctor;
    }

    //修改医生基本信息2.0
    public Boolean updateDoctor(String phone,String id,String age,String dept,String duty,String email,String name){
        int result=0;
        bo = false;
       Thread aa= new Thread(){
            @Override
            public void run() {
                try {
                    connMysql = DButils.ConnMysql();
                    String update = "update tb_doctor set doctorPhone=?,doctorID=?,doctorAge=?,doctorDept=?,doctorDuty=?,doctorEmail=? where doctorName =?";
                    PreparedStatement pst=connMysql.prepareStatement(update);
                    pst.setString(1,phone);
                    pst.setString(2,id);
                    pst.setString(3,age);
                    pst.setString(4,dept);
                    pst.setString(5,duty);
                    pst.setString(6,email);
                    pst.setString(7,name);

                    int result=pst.executeUpdate();
                    if (result>0){
                        bo = true;
                    }
                    pst.close();
                    connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        };
       aa.start();


        while (aa.isAlive()){

        }

        return bo;



    }

    //修改密码
    public Boolean updatePass(String newPass1, String name) {

        int result=0;
        bo = false;
        Thread aa=new Thread(){
            @Override
            public void run() {
                try {

                    connMysql = DButils.ConnMysql();
                    String update = "update tb_doctor set doctorPass=? where doctorName =?";
                    PreparedStatement pst=connMysql.prepareStatement(update);
                    pst.setString(1,newPass1);
                    pst.setString(2,name);

                    int result=pst.executeUpdate();
                    if (result>0){
                        bo = true;
                    }
                    pst.close();
                    connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        };
        aa.start();


        while (aa.isAlive()){
            System.out.println("准备返回aa.isAlive()----->"+aa.isAlive());
        }

        System.out.println("准备返回updatePass----->"+bo);
        return bo;
    }



}
