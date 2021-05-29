package com.example.tjw.cpr_ecgshow_system.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tjw.cpr_ecgshow_system.EventType.CO2Concentration;
import com.example.tjw.cpr_ecgshow_system.EventType.ChestSpringBack;
import com.example.tjw.cpr_ecgshow_system.EventType.HreatData;
import com.example.tjw.cpr_ecgshow_system.EventType.PushDepth;
import com.example.tjw.cpr_ecgshow_system.EventType.PushFreqence;
import com.example.tjw.cpr_ecgshow_system.R;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class nomalFragment extends Fragment {

    private TextView tv_hreat_nomal,tv_Co2_normal,tv_Push_dep,tv_Push_freq,tv_Springback;
    public nomalFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        该方法可用于返回视图文件
        View view = inflater.inflate(R.layout.fragment_nomal,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        当view创建之后的一个回调方法
        super.onViewCreated(view, savedInstanceState);
        tv_hreat_nomal = view.findViewById(R.id.hreatdata);
        tv_Co2_normal = view.findViewById(R.id.Co2data);
        tv_Push_dep = view.findViewById(R.id.Puhdepdata) ;
        tv_Push_freq = view.findViewById(R.id.Pushfreqdata);
        tv_Springback = view.findViewById(R.id.Springbackdata) ;

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(HreatData string){
        tv_hreat_nomal.setText(string.toString());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(CO2Concentration string){
        tv_Co2_normal.setText(string.toString());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PushDepth string){
        tv_Push_dep.setText(string.toString());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(PushFreqence string){
        tv_Push_freq.setText(string.toString());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(ChestSpringBack string){
        tv_Springback.setText(string.toString());
    }
}
