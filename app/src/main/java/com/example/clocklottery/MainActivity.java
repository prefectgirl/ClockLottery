package com.example.clocklottery;

import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.clocklottery.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private long startTime = 0;
    private long endTime = 0;
    private long result;
    private SimpleDateFormat simpleDateFormat;
    private boolean timeFlag = true;
    int count = 0;//按了几下按钮
    private ActivityMainBinding binding;
    private Time time;
    private TimeThread timeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        simpleDateFormat = new SimpleDateFormat("ss:SS");
        time = new Time("00:00");//双向数据绑定
        binding.setTime(time);
//        timeThread = new TimeThread(); //use handler
    }

    public void start(View view) {
        timeFlag = true;
        startTime = System.currentTimeMillis();
        new myThread().start();
//        useHandler();
    }

    public void stop(View view) {
        timeFlag = false;
    }

    class myThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (timeFlag) {
                try {
                    Thread.sleep(100);
                    time.setTime(simpleDateFormat.format(new Date(System.currentTimeMillis() - startTime)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 使用handler 更新text
     */
    private void useHandler() {
        startTime = System.currentTimeMillis();
        timeFlag = true;
        timeThread.start();//启动线程
    }

    //写一个新的线程每隔一秒发送一次消息,这样做会和系统时间相差1秒
    public class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(10);
                    Message msg = new Message();
                    msg.what = 1;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (timeFlag);
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    binding.text.setText(simpleDateFormat.format(new Date(System.currentTimeMillis() - startTime)));
                    break;
            }
            return false;
        }
    });
}
