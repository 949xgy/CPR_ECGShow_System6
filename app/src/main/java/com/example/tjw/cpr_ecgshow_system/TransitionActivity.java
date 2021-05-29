package com.example.tjw.cpr_ecgshow_system;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Anonymous on 2016/3/25.
 */
public class TransitionActivity extends Activity {

    boolean isFirstIn = false;
    private Intent intent;

   private TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.transition_view);

        text = findViewById(R.id.start_text);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/KT.ttf");
        text.setTypeface(typeface);

        //缩放
        ObjectAnimator scaleAnim = ObjectAnimator.ofFloat(text, "scaleX", 1f, 2f, 1f);
        ObjectAnimator scaleAnim1 = ObjectAnimator.ofFloat(text, "scaleY", 1f, 2f, 1f);
        scaleAnim.setDuration(8000);
        scaleAnim.start();
        scaleAnim1.setDuration(8000);
        scaleAnim1.start();


        final SharedPreferences sharedPreferences = getSharedPreferences("is_first_in_data",MODE_PRIVATE);
        final SharedPreferences sharedPreferences2 = getSharedPreferences("is_doctorName",0);
        isFirstIn = sharedPreferences.getBoolean("isFirstIn",true);









        new Handler().postDelayed(new Runnable() {







            @Override
            public void run() {
                if (isFirstIn) {
                   Log.i("TGA","第一次进入");
                    intent = new Intent(TransitionActivity.this, LoginActivity.class);
                    TransitionActivity.this.startActivity(intent);
                    TransitionActivity.this.finish();
                } else {
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

}

