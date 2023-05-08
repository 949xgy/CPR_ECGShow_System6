package com.example.tjw.cpr_ecgshow_system.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.ECGIntroduceActivity;
import com.example.tjw.cpr_ecgshow_system.ECGShowActivity;
import com.example.tjw.cpr_ecgshow_system.ECG_heartActivity;
import com.example.tjw.cpr_ecgshow_system.Main2Activity;
import com.example.tjw.cpr_ecgshow_system.PatientActivity;
import com.example.tjw.cpr_ecgshow_system.R;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {




        homeViewModel =
                new ViewModelProvider(this, new ViewModelProvider.NewInstanceFactory()).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        Button btn_patient = (Button) root.findViewById(R.id.Image_patient);
        Button btn_ECGAnalysis = (Button) root.findViewById(R.id.Image_ECGAnalysis);
        Button ECGShowActivity = (Button) root.findViewById(R.id.Image_ECGShow);

        btn_patient.setOnClickListener(this);
        btn_ECGAnalysis.setOnClickListener(this);
        ECGShowActivity.setOnClickListener(this);





        return root;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent;

        switch (id) {
            case R.id.Image_patient:
                Log.i("TAG", "点击了病人信息按钮");
                //fragment 跳转action
                intent = new Intent(getActivity(), PatientActivity.class);
                startActivity(intent);
                break;
            case R.id.Image_ECGAnalysis:

                Log.i("TAG", "点击了常见心电图按钮");
                //fragment 跳转action
                intent = new Intent(getActivity(), ECGIntroduceActivity.class);
                startActivity(intent);

                break;
            case R.id.Image_ECGShow:

                Log.i("TAG", "点击了开始监测按钮");
                //fragment 跳转action
                intent = new Intent(getActivity(), ECGShowActivity.class);
                startActivity(intent);

                break;

        }

    }
}