package com.example.tjw.cpr_ecgshow_system;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ECG_heartActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private DemoImitationLoopPagerAdapter mAdapter;
    private ViewPager mViewPager;
    private ArrayList<String> mItems;
    private ArrayList<Integer> mIcons;

    private Toolbar toolbar;    //导航栏
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.init("TAB").logLevel(LogLevel.FULL);
        ButterKnife.bind(this);
        mItems = new ArrayList<>();
        mIcons = new ArrayList<>();
        mItems.add("正常心电图");
        mItems.add("心室颤动");
        mItems.add("心房颤动");
        mItems.add("心房扑动");
        mItems.add("窦性心动过缓");
        mIcons.add(R.drawable.tab1);
        mIcons.add(R.drawable.tab1);
        mIcons.add(R.drawable.tab1);
        mIcons.add(R.drawable.tab1);
        mIcons.add(R.drawable.tab1);



        mAdapter = new DemoImitationLoopPagerAdapter(getSupportFragmentManager());
        mAdapter.addAllTitle(mItems);
        mAdapter.addAllIcon(mIcons);



        mViewPager = (ViewPager)findViewById(R.id.view_pager);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(mAdapter.getCenterPosition(0));
        mViewPager.addOnPageChangeListener(this);

        toolbar=findViewById(R.id.toolbar);

        RecyclerTabLayout recyclerTabLayout = (RecyclerTabLayout)
                findViewById(R.id.recycler_tab_layout);
        recyclerTabLayout.setUpWithAdapter(new DemoCustomView01Adapter(this, mViewPager));


        //监听导航栏的返回
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //got to center
        boolean nearLeftEdge = (position <= mItems.size());
        boolean nearRightEdge = (position >= mAdapter.getCount() - mItems.size());
        if (nearLeftEdge || nearRightEdge) {
            mViewPager.setCurrentItem(mAdapter.getCenterPosition(0), false);
        }
        Logger.e("滑动到" + position % mItems.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
