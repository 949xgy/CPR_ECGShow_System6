package com.example.tjw.cpr_ecgshow_system;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * 心电图介绍activity
 *
 */
public class ECGIntroduceActivity extends AppCompatActivity {
    private Button btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn10,btn11,btn12;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ecg_introduce);
        init();

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn1Dialog();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn2Dialog();
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn3Dialog();
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn4Dialog();
            }
        });
        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn5Dialog();
            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn6Dialog();
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn7Dialog();
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn8Dialog();
            }
        });
        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn9Dialog();
            }
        });
        btn10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn10Dialog();
            }
        });
        btn11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn11Dialog();
            }
        });
        btn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn12Dialog();
            }
        });
    }



    //找到对应的控件
    private void init(){
        btn1=findViewById(R.id.button1);
        btn2=findViewById(R.id.button2);
        btn3=findViewById(R.id.button3);
        btn4=findViewById(R.id.button4);
        btn5=findViewById(R.id.button5);
        btn6=findViewById(R.id.button6);
        btn7=findViewById(R.id.button7);
        btn8=findViewById(R.id.button8);
        btn9=findViewById(R.id.button9);
        btn10=findViewById(R.id.button10);
        btn11=findViewById(R.id.button11);
        btn12=findViewById(R.id.button12);
    }
    //自定义正常心电图Dialog
    private void btn1Dialog(){
        AlertDialog.Builder btnDialog1 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog1View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog1, null);
        btnDialog1.setView(btnDialog1View);
        btnDialog1.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog1.show();
    }
    //自定义心室颤动心电图Dialog
    private void btn2Dialog(){
        AlertDialog.Builder btnDialog2 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog2View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog2, null);
        btnDialog2.setView(btnDialog2View);
        btnDialog2.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog2.show();
    }
    //自定义心室颤动心电图Dialog
    private void btn3Dialog(){
        AlertDialog.Builder btnDialog3 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog3View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog3, null);
        btnDialog3.setView(btnDialog3View);
        btnDialog3.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog3.show();
    }
    //自定义心房颤动心电图Dialog
    private void btn4Dialog(){
        AlertDialog.Builder btnDialog4 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog3View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog4, null);
        btnDialog4.setView(btnDialog3View);
        btnDialog4.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog4.show();
    }
    //自定义窦性心动过速心电图Dialog
    private void btn5Dialog(){
        AlertDialog.Builder btnDialog5 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog5View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog5, null);
        btnDialog5.setView(btnDialog5View);
        btnDialog5.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog5.show();
    }
    //自定义窦性心动过缓心电图Dialog
    private void btn6Dialog(){
        AlertDialog.Builder btnDialog6 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog6View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog6, null);
        btnDialog6.setView(btnDialog6View);
        btnDialog6.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog6.show();
    }
    //自定义室性早搏心电图Dialog
    private void btn7Dialog(){
        AlertDialog.Builder btnDialog7 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog7View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog7, null);
        btnDialog7.setView(btnDialog7View);
        btnDialog7.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog7.show();
    }
    //自定义房性早搏心电图Dialog
    private void btn8Dialog(){
        AlertDialog.Builder btnDialog8 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog8View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog8, null);
        btnDialog8.setView(btnDialog8View);
        btnDialog8.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog8.show();
    }
    //自定义室性心动过速心电图Dialog
    private void btn9Dialog(){
        AlertDialog.Builder btnDialog9 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog9View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog9, null);
        btnDialog9.setView(btnDialog9View);
        btnDialog9.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog9.show();
    }
    //自定义房性心动过速心电图Dialog
    private void btn10Dialog(){
        AlertDialog.Builder btnDialog10 = new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog10View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog10, null);
        btnDialog10.setView(btnDialog10View);
        btnDialog10.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog10.show();
    }
    //自定义交界性早搏心电图Dialog
    private void btn11Dialog(){
        AlertDialog.Builder btnDialog11= new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog11View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog11, null);
        btnDialog11.setView(btnDialog11View);
        btnDialog11.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog11.show();
    }
    //自定义心博骤停心电图Dialog
    private void btn12Dialog(){
        AlertDialog.Builder btnDialog12= new AlertDialog.Builder(ECGIntroduceActivity.this);
        final View btnDialog12View = LayoutInflater.from(ECGIntroduceActivity.this).inflate(R.layout.ecg_dialog12, null);
        btnDialog12.setView(btnDialog12View);
        btnDialog12.setPositiveButton("返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        btnDialog12.show();
    }
}
