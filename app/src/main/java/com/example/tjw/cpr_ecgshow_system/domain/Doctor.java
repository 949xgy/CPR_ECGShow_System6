package com.example.tjw.cpr_ecgshow_system.domain;


import java.io.Serializable;

/**
 * 医生实体类
 */
public class Doctor implements Serializable{
    private int doctorNo;//主键
    private String doctorName;//医生姓名
    private String doctorPass;//医生登录密码
    private Integer doctorAge;//医生年龄
    private String doctorSex;//医生性别
    private String doctorDuty;//职务
    private String doctorPhone;//医生联系电话
    private String doctorID;//医生身份证
    private String doctorDept;//医生所在科室
    private String doctorEmail;//医生邮箱
    public Doctor(){

    }
    public Doctor(int doctorNo, String doctorName, String doctorPass, Integer doctorAge, String doctorSex, String doctorDuty, String doctorPhone, String doctorID, String doctorDept,String doctorEmail) {
        this.doctorNo = doctorNo;
        this.doctorName = doctorName;
        this.doctorPass = doctorPass;
        this.doctorAge = doctorAge;
        this.doctorSex = doctorSex;
        this.doctorDuty = doctorDuty;
        this.doctorPhone = doctorPhone;
        this.doctorID = doctorID;
        this.doctorDept = doctorDept;
        this.doctorEmail=doctorEmail;
    }

    public int getDoctorNo() {
        return doctorNo;
    }

    public void setDoctorNo(int doctorNo) {
        this.doctorNo = doctorNo;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }


    public String getDoctorPass() {
        return doctorPass;
    }

    public void setDoctorPass(String doctorPass) {
        this.doctorPass = doctorPass;
    }


    public Integer getDoctorAge() {
        return doctorAge;
    }

    public void setDoctorAge(Integer doctorAge) {
        this.doctorAge = doctorAge;
    }


    public String getDoctorSex() {
        return doctorSex;
    }

    public void setDoctorSex(String doctorSex) {
        this.doctorSex = doctorSex;
    }


    public String getDoctorDuty() {
        return doctorDuty;
    }

    public void setDoctorDuty(String doctorDuty) {
        this.doctorDuty = doctorDuty;
    }



    public String getDoctorPhone() {
        return doctorPhone;
    }

    public void setDoctorPhone(String doctorPhone) {
        this.doctorPhone = doctorPhone;
    }


    public String getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }


    public String getDoctorDept() {
        return doctorDept;
    }

    public void setDoctorDept(String doctorDept) {
        this.doctorDept = doctorDept;
    }


    public String getDoctorEmail() {
        return doctorEmail;
    }

    public void setDoctorEmail(String doctorEmail) {
        this.doctorEmail = doctorEmail;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "doctorNo=" + doctorNo +
                ", doctorName='" + doctorName + '\'' +
                ", doctorPass='" + doctorPass + '\'' +
                ", doctorAge=" + doctorAge +
                ", doctorSex='" + doctorSex + '\'' +
                ", doctorDuty='" + doctorDuty + '\'' +
                ", doctorPhone='" + doctorPhone + '\'' +
                ", doctorID=" + doctorID +
                ", doctorDept='" + doctorDept + '\'' +
                ", doctorEmail='" + doctorEmail + '\'' +
                '}';
    }
}
