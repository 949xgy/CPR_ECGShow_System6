package com.example.tjw.cpr_ecgshow_system.ui.quit;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class QuitFragment extends Fragment {




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        //killProcess(android.os.Process.myPid());
        //Runtime.getRuntime().exit(0);

        //保存登录状态
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("is_first_in_data", 0x0000);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstIn",true);
        editor.commit();

        System.exit(0);
        return null;
    }



}
