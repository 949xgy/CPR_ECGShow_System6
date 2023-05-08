package com.example.tjw.cpr_ecgshow_system;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.utils.DButils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 登录Activity
 *
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    private EditText username,password;//用户名，密码输入框
    private Button login,register;//登录注册按钮

    private ProgressDialog progressDialog = null;

    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕为竖屏, 设置后会锁定方向
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DoctorDao doctorDao=new DoctorDao(LoginActivity.this);

        if (!Settings.canDrawOverlays(this.getApplicationContext())) {
            Toast.makeText(this.getApplicationContext(), "当前无权限，请授权", Toast.LENGTH_SHORT);
//            showFloatingWindow();
            startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
        }
        // 组件初始化
        initView();
        // 事件挂接
        initListeners();
    }

    private void initListeners() {
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void initView() {
        username= findViewById(R.id.et_username);
        password= findViewById(R.id.et_password);
        login= findViewById(R.id.btn_login);
        register= findViewById(R.id.btn_register);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode==1&&resultCode==1){
            Bundle data= intent.getExtras();
            String userstr=data.getString("username");
            username.setText(userstr);
        }
        if (requestCode == 0) {
            if (!Settings.canDrawOverlays(this.getApplicationContext())) {
                Toast.makeText(this.getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this.getApplicationContext(), "授权成功", Toast.LENGTH_SHORT).show();
//                启动悬浮窗
//                startService(new Intent(MainActivity.this, FloatingService.class));
            }
        }
    }

    @Override
    public void onClick(View view) {
            if(view == this.login){
                // 创建进度对话框，提示用户
                progressDialog = new ProgressDialog(this);
                progressDialog.setTitle("提示");
                progressDialog.setMessage("登入中...");
                progressDialog.show();
                String name=username.getText().toString();
                String pass=password.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(pass)){
                    Toast.makeText(this,"账号和密码不能为空哦！",Toast.LENGTH_LONG).show();
                    return;
                }

                DoctorDao doctorDao = new DoctorDao(this);
                Doctor doctor=doctorDao.login(name, pass);

                if(doctor!=null){
                    // 登入成功
                    if(progressDialog.isShowing())progressDialog.dismiss();

                    //界面跳转
                    Intent intent=new Intent(LoginActivity.this,Main2Activity.class);


                    //保存登录状态
                    SharedPreferences sharedPreferences = getApplication().getSharedPreferences("is_first_in_data", 0x0000);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("isFirstIn", false);
                    editor.commit();

                    //保存 登录用户名
                    SharedPreferences sharedPreferences2 = getApplication().getSharedPreferences("is_doctorName", 0);
                    SharedPreferences.Editor editor2 = sharedPreferences2.edit();
                    editor2.putString("doctorName",name);
                    editor2.commit();


                    //将 用户名 传入下一个界面
                    intent.putExtra("doctorName",doctor.getDoctorName());
                    intent.putExtra("doctorEmail",doctor.getDoctorEmail());
                    startActivity(intent);


                }else{
                    // 登入失败
                    if(progressDialog.isShowing())progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_LONG).show();
                }
            }else if(view == this.register){
                Intent intent=new Intent(this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
    }
}
