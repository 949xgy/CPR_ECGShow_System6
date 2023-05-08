package com.example.tjw.cpr_ecgshow_system;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.tjw.cpr_ecgshow_system.domain.Doctor;
import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;


public class MenuActivity extends Activity {
    private ImageButton Image_doctor,Image_patient,Image_ECGShow,Image_ECGAnalysis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Image_doctor=findViewById(R.id.Image_doctor);
        Image_patient=findViewById(R.id.Image_patient);
        Image_ECGShow=findViewById(R.id.Image_ECGShow);
        Image_ECGAnalysis=findViewById(R.id.Image_ECGAnalysis);



        Image_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent getIntent=getIntent();
                String name=getIntent.getStringExtra("doctorName");
                Doctor doctor1=new Doctor();
                DoctorDao doctorDao=new DoctorDao(MenuActivity.this);
                doctor1 = doctorDao.FindDoctorByName(name);
                System.out.println(""+doctor1.toString());
                Intent intent=new Intent(MenuActivity.this,DoctorActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("doctorObject",doctor1);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        Image_patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,PatientActivity.class);
                startActivity(intent);
            }
        });

        Image_ECGShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ECGShowActivity.class);
                startActivity(intent);

            }
        });
        Image_ECGAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MenuActivity.this,ECGIntroduceActivity.class);
                startActivity(intent);
            }
        });


    }
}
