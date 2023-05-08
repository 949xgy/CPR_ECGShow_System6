package com.example.tjw.cpr_ecgshow_system;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.Dao.PatientDao;
import com.example.tjw.cpr_ecgshow_system.adapters.InpatientListAdapter;
import com.example.tjw.cpr_ecgshow_system.domain.Patient;

import java.util.List;
import java.util.Objects;
import java.util.zip.Inflater;

/**
 * author:hpx
 * date:2022年12月2日
 */

public class PatientActivity extends Activity implements View.OnClickListener{
    Button m_btn_searchInpatient = null;
    EditText m_et_dataOfInpatient = null;
    ImageButton m_imgBtn_addInpatient;
    ListView m_listView;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };
    private Toolbar toolbar;    //导航栏

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        initView();

        iniListener();


    }

    private void iniListener() {
        this.m_btn_searchInpatient.setOnClickListener(this);
        //监听导航栏的返回
        toolbar.setNavigationOnClickListener(this);
        this.m_imgBtn_addInpatient.setOnClickListener(this);
    }

    private void initView() {
        this.m_btn_searchInpatient = findViewById(R.id.btn_searchInpatient);
        this.m_imgBtn_addInpatient = findViewById(R.id.imgBtn_addInpatient);
        this.m_et_dataOfInpatient = findViewById(R.id.et_searchInpatient);
        this.m_listView  = findViewById(R.id.list_showInpatientInfo);
        this.toolbar = findViewById(R.id.toolbar);

    }

    @Override
    public void onClick(View view) {
        // 判断事件来源
        if(view.getParent().toString().contains("toolbar")){
            // 导航栏返回键被点击
            finish();
        }else if(view == m_btn_searchInpatient){
            // 搜索按钮被点击
            // 弹出对话款选择搜索内容
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("搜索类型");
            View view1 = this.getLayoutInflater().inflate(R.layout.dialog_search_type_layout,null);
            RadioGroup radioGroup = null;
            radioGroup = view1.findViewById(R.id.radioG_searchTypes);

            builder.setView(view1);
            builder.setCancelable(true);

            // 确定
            RadioGroup finalRadioGroup = radioGroup;
            Context currentContext = this;
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(currentContext,"搜索中,请等待!",Toast.LENGTH_SHORT).show();
                    PatientDao patientDao = new PatientDao(null);
                    List<Patient> list = null;
                    if(m_listView.getAdapter()!=null){
                        InpatientListAdapter l_inpatientListAdapter = (InpatientListAdapter)m_listView.getAdapter();
                        l_inpatientListAdapter.clearAll();
                    }
                    if(finalRadioGroup.getCheckedRadioButtonId() == R.id.radioB_name)
                    {
                        String patientName = m_et_dataOfInpatient.getText().toString().trim();
                        list = patientDao.findPatients(patientName);
                        InpatientListAdapter inpatientListAdapter = new InpatientListAdapter(currentContext,list);
                        m_listView.setAdapter(inpatientListAdapter);
                    }else if(finalRadioGroup.getCheckedRadioButtonId() == R.id.radioB_id){
                        String patientId = m_et_dataOfInpatient.getText().toString().trim();
                        list = patientDao.findPatients2(patientId);
                        InpatientListAdapter inpatientListAdapter = new InpatientListAdapter(currentContext,list);
                        m_listView.setAdapter(inpatientListAdapter);
                    }

                }
            });
            // 取消
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Toast.makeText(Main2Activity.context,"取消了搜索!",Toast.LENGTH_LONG).show();
                }
            });

            builder.create().show();

        }else if(view == m_imgBtn_addInpatient){
            // 添加病人信息按钮被点击
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
            builder.setTitle("添加病人信息");
            View view1 = LayoutInflater.from(this).inflate(R.layout.add_patient_dialog,null);
            builder.setView(view1);

            builder.setCancelable(true);
            builder.setPositiveButton((CharSequence) "确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 添加用户信息

                }
            });
            builder.create().show();
        }
    }
}
