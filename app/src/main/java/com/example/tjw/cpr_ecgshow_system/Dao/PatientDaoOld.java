package com.example.tjw.cpr_ecgshow_system.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.tjw.cpr_ecgshow_system.domain.Patient;
import com.example.tjw.cpr_ecgshow_system.utils.DatabaseHelper;

/**
 * 该类包含对病人信息表的操作
 *
 */
public class PatientDaoOld {
    private DatabaseHelper dbHelper;
    public PatientDaoOld(Context context){
        dbHelper=new DatabaseHelper(context);
    }
    //添加病人的方法
    public void addPatient(String name,String phone,String sex,String ID,Integer age,String doc,Integer room,Integer bed,String pmh,String state){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String sql="insert Into tb_patient(patientName,patientPhone,patientSex,patientID,patientAge,patientDoc,patientRoom,patientBed,patientPmh,patientState) values(?,?,?,?,?,?,?,?,?,?)";
        db.execSQL(sql,new Object[]{name,phone,sex,ID,age,doc,room,bed,pmh,state});

    }
    //检查病人是否存在
    public Boolean CheckIsPatientExists(String value){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String Query = "Select * from tb_patient where patientName =?";
        Cursor cursor = db.rawQuery(Query, new String[]{value});
        if (cursor.getCount() > 0) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    //根据姓名找到病人并返回一个对象
    public Patient findPatient(String name){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String Query="select * from tb_patient where patientName=?";

        Patient patient=new Patient();
        patient.setPatientName(name);

        Cursor cursor=db.rawQuery(Query,new String[]{name});
        if(cursor.moveToFirst()){

            int phoneColumn=cursor.getColumnIndex("patientPhone");
            String phone = cursor.getString(phoneColumn);
            patient.setPatientPhone(phone);

            int sexColumn=cursor.getColumnIndex("patientSex");
            String sex = cursor.getString(sexColumn);
            patient.setPatientSex(sex);

            int IDColumn=cursor.getColumnIndex("patientID");
            String ID = cursor.getString(IDColumn);
            patient.setPatientID(ID);

            int ageColumn=cursor.getColumnIndex("patientAge");
            Integer age=cursor.getInt(ageColumn);
            patient.setPatientAge(age);

            int docColumn=cursor.getColumnIndex("patientDoc");
            String doc=cursor.getString(docColumn);
            patient.setPatientDoc(doc);

            int roomColumn=cursor.getColumnIndex("patientRoom");
            Integer room=cursor.getInt(roomColumn);
            patient.setPatientRoom(room);

            int bedColumn=cursor.getColumnIndex("patientBed");
            Integer bed=cursor.getInt(bedColumn);
            patient.setPatientBed(bed);

            int pmhColumn=cursor.getColumnIndex("patientPmh");
            String pmh = cursor.getString(pmhColumn);
            patient.setPatientPmh(pmh);


            int stateColumn=cursor.getColumnIndex("patientState");
            String state = cursor.getString(stateColumn);
            patient.setPatientState(state);
        }

        return patient;
    }
    //修改病人基本信息
    public void updatePatient(Integer room,Integer bed,String doc,String phone,String state,String name){
        SQLiteDatabase db=dbHelper.getReadableDatabase();
        String updateSql="update tb_patient set patientRoom=?,patientBed=?,patientDoc=?,patientPhone=?,patientState=? where patientName=?";
        db.execSQL(updateSql,new Object[]{room,bed,doc,phone,state,name});

    }

}
