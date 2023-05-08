package com.example.tjw.cpr_ecgshow_system.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tjw.cpr_ecgshow_system.ECGShowActivity;
import com.example.tjw.cpr_ecgshow_system.R;
import com.example.tjw.cpr_ecgshow_system.Tools.DataProcessingClass;
import com.example.tjw.cpr_ecgshow_system.Tools.Device;
import com.example.tjw.cpr_ecgshow_system.Types.ReturnData;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment1 extends Fragment implements CompoundButton.OnCheckedChangeListener {
    public static String TAG = "Fragment1";
    public static int MSG_IS_ERROR = 111, MSG_IS_RIGHT = 112, MSG_DEVICE_OPEN_SUCCESS = 231, MSG_DEVICE_OPEN_FAIL = 223, MSG_GET_CONSOLE_DATA = 334;
    Switch m_switch;
    Thread th_listener_data; // 监听数据线程
    ImageView m_imageView_tip;
    TextView m_textView_tip, m_textView_title;
    EditText m_editText_console;
    int m_index = 0;
    List<DevideData> m_drawData = new ArrayList<DevideData>(); // 存储姿态传感器传来的数据！
    Handler m_messageHander = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what == Fragment1.MSG_IS_RIGHT) {
                m_imageView_tip.setImageResource(R.drawable.img_hand_press);
                m_textView_tip.setText(String.valueOf(message.obj));
            } else if (message.what == Fragment1.MSG_IS_ERROR) {
                m_imageView_tip.setImageResource(R.drawable.warning);
                m_textView_tip.setText(String.valueOf(message.obj));
            } else if (message.what == Fragment1.MSG_DEVICE_OPEN_SUCCESS) {
                Toast.makeText(ECGShowActivity.mContext, "姿态检测传感器打开成功!", Toast.LENGTH_SHORT).show();
            } else if (message.what == Fragment1.MSG_DEVICE_OPEN_FAIL) {
                Toast.makeText(ECGShowActivity.mContext, "姿态检测传感器打开失败!", Toast.LENGTH_SHORT).show();
            } else if (message.what == Fragment1.MSG_GET_CONSOLE_DATA) {
                DevideData data = (DevideData) message.obj;
                m_editText_console.append(data.toString());
                m_editText_console.append("---------------------------\n");
                if (m_editText_console.getLineCount() > 100) {
                    m_editText_console.setText("");
                }
            }
            return false;
        }
    });

    @Override
    public void onDestroy() {
        //Fragment1被销毁,终止线程创建!
        if (this.th_listener_data != null) {
            if (this.th_listener_data.isAlive())
                this.th_listener_data.interrupt();
        }
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.f1_layout, container, false);
        this.m_switch = v.findViewById(R.id.switch_open_device);
        this.m_textView_tip = v.findViewById(R.id.tv_f1_tip);
        this.m_imageView_tip = v.findViewById(R.id.iv_tip);
        this.m_textView_title = v.findViewById(R.id.tv_f1_title);
        this.m_editText_console = v.findViewById(R.id.et_fragment_console);

        if (this.m_switch != null) {
            this.m_switch.setOnCheckedChangeListener(this);
        }
        // 创建数据处理类

        return v;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        // 开关被打开
        if (b) {
            this.m_textView_title.setVisibility(View.VISIBLE);
            th_listener_data = null;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // 打开设备
                    ReturnData data = null;
                    try {
                        //打开设备
                        data = DataProcessingClass.OpenDevice(Device.CODE_MPU6050);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Message msg = new Message();
                    msg.obj = data.Msg;
                    msg.what = Fragment1.MSG_DEVICE_OPEN_SUCCESS;
                    m_messageHander.sendMessage(msg);
                    th_listener_data = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //-------------------------------------------------线程任务-------------------------------------------------------
                            while (true) {
                                // 判断当前线程是否中断
                                if (Thread.interrupted()) {
                                    break;
                                }
                                // 延迟当前线程
                                try {
                                    Thread.sleep(2000);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    break;
                                }
                                //判断数据队列是否满?
                                if (m_drawData.isEmpty()) {
                                    System.out.println("m_drawData.isEmpty() == true");
                                    ReturnData data1 = DataProcessingClass.GetDeviceData_Ex(Device.CODE_MPU6050);
                                    JSONArray data2 = null;
                                    for (int i = 0; i < data1.Data.length(); i++) {
                                        // 轴加速度,俯仰角角度,滚动角度,时间
                                        //[ 0.229004, 76.964722, 17.495728, -135697293.0 ]
                                        try {
                                            data2 = data1.Data.getJSONArray(i);
                                            DevideData devideData = new DevideData(data2.getDouble(0),
                                                    data2.getDouble(1), data2.getDouble(2),
                                                    (long) data2.getDouble(3));
                                            m_drawData.add(devideData);
                                        } catch (JSONException e) {
                                            break;
                                        }
                                    }
                                }
                                // 将数据显示到控制台
                                if (!m_drawData.isEmpty()) {
                                    Message dataMsg = new Message();
                                    dataMsg.obj = m_drawData.get(0);
                                    // to do....
                                    m_drawData.remove(0);
                                    dataMsg.what = Fragment1.MSG_GET_CONSOLE_DATA;
                                    m_messageHander.sendMessage(dataMsg);
                                }
                                // to do...
                                Random random = new Random();
                                Message message = new Message();
                                int resultInt = random.nextInt(100);
                                if (resultInt < 10) {
                                    message.obj = "姿态不正确！";
                                    message.what = Fragment1.MSG_IS_ERROR;
                                    message.arg1 = resultInt;
                                    m_messageHander.sendMessage(message);
                                } else if (resultInt >= 10 & resultInt < 50) {
                                    message.obj = "姿态正确！";
                                    message.arg1 = resultInt;
                                    message.what = Fragment1.MSG_IS_RIGHT;
                                    m_messageHander.sendMessage(message);
                                }
                            }
                            //---------------------------------------------------------------------------------------------------------------
                        }
                    });
                    // 获取数据线程启动失败
                    th_listener_data.start();
                }
            }).start();
            //while (th_listener_data == null){};
            // 启动线程

        } else {
            // 关闭线程
            if (this.th_listener_data != null) {
                this.m_textView_title.setVisibility(View.INVISIBLE);
                th_listener_data.interrupt();
                // 关闭设备
                if (!DataProcessingClass.OpenDevice(Device.CODE_MPU6050).State) {
                    Toast.makeText(this.getContext(), "姿态检测传感器关闭出现未知错误!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}

//轴加速度,俯仰角角度,滚动角度 av=加速度
class DevideData {
    double vertical_av;
    double y_av;
    double x_av;

    long time;

    public DevideData(double v_av, double y_av, double x_av, long time) {
        this.vertical_av = v_av;
        this.x_av = x_av;
        this.y_av = y_av;
        // time 设备端获取的时间为负数，这里转为正数。
        this.time = Math.abs(time);
    }

    public String toString() {
        return "gz(轴加速度):" + String.valueOf(vertical_av) + "\tRoll(俯仰角角度):" + String.valueOf(y_av) + "\tPitch(滚动角):" + String.valueOf(x_av)
                + "\ttime(毫秒):" + String.valueOf(time) + "\n";
    }
}

