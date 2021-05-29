package com.example.tjw.cpr_ecgshow_system;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.ui.home.HomeFragment;

public class Main2Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;


    @Override
    protected void onStart() {
        super.onStart();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//设置屏幕为竖屏, 设置后会锁定方向





//        HomeFragment a = new HomeFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString("key","888888888888888888");
//        a.setArguments(bundle);


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        Intent getIntent=getIntent();
        String name=getIntent.getStringExtra("doctorName");
//        Doctor doctor1=new Doctor();
//        DoctorDao doctorDao=new DoctorDao(Main2Activity.this);
//        doctor1 = doctorDao.FindDoctorByName(name);
//        System.out.println(""+doctor1.toString()+"Main2");


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

//        TextView h_name = findViewById(R.id.header_name);
//        TextView h_email = findViewById(R.id.header_email);
//        System.out.println("555"+h_name);
//        System.out.println("555"+h_name.getText());
//        h_name.setText(doctor1.getDoctorName());
//        h_email.setText(doctor1.getDoctorEmail());



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


//        View ff = navigationView.getHeaderView(1);
//        TextView h_name = ff.findViewById(R.id.header_name);
//
//
//        TextView h_email = findViewById(R.id.header_email);
//        System.out.println("555"+h_name);

//        TextView h_name = findViewById(R.id.header_name);
//        TextView h_email = findViewById(R.id.header_email);
//        System.out.println("555"+h_name.getText());
//        System.out.println(""+doctor1.getDoctorName()+"88888888888");
//        h_name.setText("sdfjqaw");
//        h_email.setText(doctor1.getDoctorEmail().toString().trim());




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }









}