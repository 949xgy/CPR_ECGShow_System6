package com.example.tjw.cpr_ecgshow_system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;


import java.util.Calendar;


public class Main2Activity extends AppCompatActivity {
    public static Context context;
    private AppBarConfiguration mAppBarConfiguration;

    NavigationView m_navView = null;
    TextView m_textView_name = null;

    private static final String TAG = "Main2Activity";


    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕为竖屏, 设置后会锁定方向
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Main2Activity.context = this;

        Intent getIntent=getIntent();
        String name=getIntent.getStringExtra("doctorName");
        String email = getIntent.getStringExtra("doctorEmail");//doctorEmail

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController((Activity) this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 获取侧边导航菜单的布局对象
        m_navView = findViewById(R.id.nav_view);
        View menu_header_layout =  m_navView.getHeaderView(0);
        m_textView_name = menu_header_layout.findViewById(R.id.header_name);
        //Toast.makeText((Context) this,String.valueOf(m_textView_name == null),Toast.LENGTH_LONG).show();
        // 将从登入界面传来的医生名，显示到侧边导航栏
        m_textView_name.setText(name + "," + (Calendar.getInstance().get(Calendar.AM_PM) == Calendar.AM ? "上午好!":"下午好!"));
        TextView textView_email = menu_header_layout.findViewById(R.id.header_email);
        textView_email.setText(email);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController((Activity) this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}