package com.example.tjw.cpr_ecgshow_system.ECG_heart;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tjw.cpr_ecgshow_system.R;

/**
 * Created by wushange on 2016/07/22.
 */
public class TestFragment extends Fragment {
    protected View mView = null;

    static TestFragment fragment;

    public TestFragment newInstance() {
        if (fragment == null) {
            fragment = new TestFragment();
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.test, null);
        return mView;
    }
}
