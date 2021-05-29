package com.example.tjw.cpr_ecgshow_system.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 	该类是一个操作Sqlite数据库的帮助类，SQLiteOpenHelper是一个管理数据库的工具类
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	static String name="test1.db";//数据库名
	static int dbVersion=1;//数据库版本号
	public DatabaseHelper(Context context) {
		super(context, name, null, dbVersion);
	}

	public void onCreate(SQLiteDatabase db) {
		//创建医生信息表
		String sql="create table tb_doctor(doctorNo integer primary key autoincrement,doctorName varchar(255),doctorPass varchar(20),doctorAge integer,doctorSex varchar(2),doctorDuty varchar(100),doctorID varchar(100),doctorPhone varchar(20),doctorDept varchar(255),doctorEmail varchar(255))";
		db.execSQL(sql);
		//创建病人信息表
        String sql1="create table tb_patient(PatientNo integer primary key autoincrement,patientName varchar(255),patientSex varchar(20),patientAge integer,patientRoom integer,patientBed integer,patientPhone varchar(100),patientID varchar(100),patientState varchar(100),patientDoc varchar(100),patientPmh varchar(50))";
		db.execSQL(sql1);


	}
	//用于升级软件时更新表结构
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
