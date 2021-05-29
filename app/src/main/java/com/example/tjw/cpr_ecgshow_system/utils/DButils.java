package com.example.tjw.cpr_ecgshow_system.utils;



import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DButils {




    public static Connection ConnMysql (){
        //要连接的数据库url,注意：此处连接的应该是服务器上的MySQl的地址
        String url = "jdbc:mysql://1.116.196.98:3306/CPR_system?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
        //连接数据库使用的用户名
        String userName = "root";
        //连接的数据库时使用的密码
        String password = "root.949";
        Connection connection=null;
        try {
            //1、加载驱动
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("驱动加载成功！！！");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        try {
            //2、获取与数据库的连接
            connection = DriverManager.getConnection(url, userName, password);
            System.out.println("连接数据库成功！！！");
//            //3.sql语句
//            String sql = "INSERT INTO driver (id, name) VALUES ( '24100413', 'ljy')";
//            //4.获取用于向数据库发送sql语句的ps
//            PreparedStatement ps=connection.prepareStatement(sql);
//            ps.execute(sql);

//            String sql = "select * from tb_doctor where doctorName=?";
//            PreparedStatement pst=connection.prepareStatement(sql);
//            pst.setString(1,"xu");
//            ResultSet rs=pst.executeQuery();
//            System.out.println("Mysql查询成功");
//            if(rs.next()){
//                //Log.e(TAG, rs.getString("doctorAge") );
//                System.out.println(rs.getString("doctorAge"));
//            }
//            rs.close();
//            pst.close();



        }catch (Exception e) {
            System.out.println("连接数据库失败！！！");
            e.printStackTrace();
        }
//        finally {
//            if(connection!=null){
//                try {
//                    connection.close();
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }
        System.out.println("准备返回conn！！"+connection);
        return connection;
    }



    }







