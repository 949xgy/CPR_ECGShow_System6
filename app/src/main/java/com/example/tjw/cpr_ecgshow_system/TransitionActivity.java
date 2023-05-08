package com.example.tjw.cpr_ecgshow_system;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anonymous on 2016/3/25.
 */
public class TransitionActivity extends Activity {

    boolean isFirstIn = false;
    private Intent intent;

   private TextView m_tv_stratText;
   private ImageView m_iv_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transition_view);
        initView();

        // 动画设置
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        this.m_iv_icon.startAnimation(animation);
        this.m_tv_stratText.startAnimation(animation);




        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        final SharedPreferences sharedPreferences2 = getSharedPreferences("is_doctorName",0);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstIn) {
                    // 第一次登入app
                    intent = new Intent(TransitionActivity.this, LoginActivity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                } else {
                    // 不是第一次登入app
                    String name=sharedPreferences2.getString("doctorName",null);
                    Log.i("不是第一次",name);
                    intent = new Intent(TransitionActivity.this, Main2Activity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                    intent.putExtra("doctorName",name);
                    startActivity(intent);

                }
            }
        }, 2000);

    }

    private void initView() {
        m_tv_stratText = findViewById(R.id.start_text);
        m_iv_icon = findViewById(R.id.img_icon);
    }



    @Override
    protected void onStart() {
        super.onStart();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.getWindow().setStatusBarColor(Color.rgb(255,255,255));
        }
    }

}

