package com.example.tjw.cpr_ecgshow_system.domain;

import java.io.Serializable;
/**
 * 病人实体类
 */
public class Patient implements Serializable {
    private int PatientNo;//病人主键
    private String patientName;//病人姓名
    private String patientSex;//病人性别
    private Integer patientAge;//病人年龄
    private Integer patientRoom;//病人房间号
    private Integer patientBed;//病人病床号
    private String patientPhone;//病人家属联系方式
    private String patientID;//病人身份证
    private String patientState;//病人病况
    private String patientDoc;//病人主治医生
    private String patientPmh;//过往病史
    public Patient(){

    }

    public int getPatientNo() {
        return PatientNo;
    }

    public void setPatientNo(int patientNo) {
        PatientNo = patientNo;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public Integer getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(Integer patientAge) {
        this.patientAge = patientAge;
    }

    public Integer getPatientRoom() {
        return patientRoom;
    }

    public void setPatientRoom(Integer patientRoom) {
        this.patientRoom = patientRoom;
    }

    public Integer getPatientBed() {
        return patientBed;
    }

    public void setPatientBed(Integer patientBed) {
        this.patientBed = patientBed;
    }

    public String getPatientPhone() {
        return patientPhone;
    }

    public void setPatientPhone(String patientPhone) {
        this.patientPhone = patientPhone;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getPatientState() {
        return patientState;
    }

    public void setPatientState(String patientState) {
        this.patientState = patientState;
    }

    public String getPatientDoc() {
        return patientDoc;
    }

    public void setPatientDoc(String patientDoc) {
        this.patientDoc = patientDoc;
    }

    public String getPatientPmh() {
        return patientPmh;
    }

    public void setPatientPmh(String patientPmh) {
        this.patientPmh = patientPmh;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "PatientNo=" + PatientNo +
                ", patientName='" + patientName + '\'' +
                ", patientSex='" + patientSex + '\'' +
                ", patientAge=" + patientAge +
                ", patientRoom=" + patientRoom +
                ", patientBed=" + patientBed +
                ", patientPhone='" + patientPhone + '\'' +
                ", patientID='" + patientID + '\'' +
                ", patientState='" + patientState + '\'' +
                ", patientDoc='" + patientDoc + '\'' +
                ", patientPmh='" + patientPmh + '\'' +
                '}';
    }
}
