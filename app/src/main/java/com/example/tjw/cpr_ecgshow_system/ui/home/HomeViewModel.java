package com.example.tjw.cpr_ecgshow_system.ui.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Intent;
import android.util.Log;

import com.example.tjw.cpr_ecgshow_system.Dao.DoctorDao;
import com.example.tjw.cpr_ecgshow_system.domain.Doctor;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;





    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}