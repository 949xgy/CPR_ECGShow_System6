package com.example.tjw.cpr_ecgshow_system.Dao;

import android.content.Context;

import com.example.tjw.cpr_ecgshow_system.domain.Patient;
import com.example.tjw.cpr_ecgshow_system.utils.DButils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 该类包含对病人信息表的操作
 *
 */
public class PatientDaoNew {
    private Connection connMysql;
    private Boolean bo =false;
    public PatientDaoNew(Context context){

    }


    //添加病人的方法
    public Boolean addPatient(String name,String phone,String sex,String ID,Integer age,String doc,Integer room,Integer bed,String pmh,String state){

        int result=0;
        bo = false;
        new Thread(){
            @Override
            public void run() {
                try {
                    connMysql = DButils.ConnMysql();
                    String sql="insert Into tb_patient(patientName,patientPhone,patientSex,patientID,patientAge,patientDoc,patientRoom,patientBed,patientPmh,patientState) values(?,?,?,?,?,?,?,?,?,?)";
                    PreparedStatement pst=connMysql.prepareStatement(sql);
                    pst.setString(1,name);
                    pst.setString(2,phone);
                    pst.setString(3,sex);
                    pst.setString(4,ID);
                    pst.setInt(5,age);
                    pst.setString(6,doc);
                    pst.setInt(7,room);
                    pst.setInt(8,bed);
                    pst.setString(9,pmh);
                    pst.setString(10,state);

                    int result=pst.executeUpdate();
                    if (result>0){
                        bo = true;
                    }
                    pst.close();
                    connMysql.close();

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("准备返回addPatient----->"+bo);
        return bo;

    }

    //检查病人是否存在
    public Boolean CheckIsPatientExists(String value){

        bo = false;
        new Thread(){
            @Override
            public void run() {
                try {

                    connMysql = DButils.ConnMysql();
                    String Query = "Select * from tb_patient where patientName =?";
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
        }.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("准备返回CheckIsPatientExists------->"+bo);
        return bo;

    }

    //根据姓名找到病人并返回一个对象
    public Patient findPatient(String name){

        Patient patient=new Patient();
        patient.setPatientName(name);

        new Thread(){
            @Override
            public void run() {
                try {
                    connMysql = DButils.ConnMysql();
                    String Query="select * from tb_patient where patientName=?";
                    PreparedStatement pst=connMysql.prepareStatement(Query);
                    pst.setString(1,name);
                    ResultSet rs=pst.executeQuery();
                    while(rs.next()){
                        // 通过字段检索
                        String phone = rs.getString("patientPhone");
                        String sex  = rs.getString("patientSex");
                        String ID = rs.getString("patientID");
                        int age = rs.getInt("patientAge");
                        String doc = rs.getString("patientDoc");
                        int room = rs.getInt("patientRoom");
                        int bed = rs.getInt("patientBed");
                        String pmh = rs.getString("patientPmh");
                        String state = rs.getString("patientState");

                        patient.setPatientPhone(phone);
                        patient.setPatientSex(sex);
                        patient.setPatientID(ID);
                        patient.setPatientAge(age);
                        patient.setPatientDoc(doc);
                        patient.setPatientRoom(room);
                        patient.setPatientBed(bed);
                        patient.setPatientPmh(pmh);
                        patient.setPatientState(state);

                        System.out.println("patient设置成功");
                    }

                    rs.close();
                    pst.close();
                    connMysql.close();

                } catch (SQLException  throwables) {
                    throwables.printStackTrace();
                }
            }
        }.start();



        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("FindDoctorByName---->准备返回doctor对象");
        return patient;


    }

    //修改病人基本信息
    public Boolean updatePatient(Integer room,Integer bed,String doc,String phone,String state,String name){

        int result=0;
        bo = false;
        new Thread(){
            @Override
            public void run() {
                try {

                    connMysql = DButils.ConnMysql();
                    String update = "update tb_patient set patientRoom=?,patientBed=?,patientDoc=?,patientPhone=?,patientState=? where patientName=?";
                    PreparedStatement pst=connMysql.prepareStatement(update);
                    pst.setInt(1,room);
                    pst.setInt(2,bed);
                    pst.setString(3,doc);
                    pst.setString(4,phone);
                    pst.setString(5,state);
                    pst.setString(6,name);

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
        }.start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("准备返回updatePatient---->"+bo);
        return bo;


    }

}
