package com.qihoo.ai.drivemanner;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView gSensorTextX;
    private TextView gSensorTextY;
    private TextView gSensorTextZ;
    private Intent intent;

    private View view;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mThread();
        gSensorTextX = (TextView)findViewById(R.id.gSensorTextX);
        gSensorTextY = (TextView)findViewById(R.id.gSensorTextY);
        gSensorTextZ = (TextView)findViewById(R.id.gSensorTextZ);
    }

    @Override
    protected void onResume() {
        super.onResume();
        intent = new Intent(this, CollisionService.class);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopService(intent);
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }

    };

    private void mThread() {
        if (thread == null) {

            thread = new Thread(new Runnable() {
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (CollisionService.flag) {
                            Message msg = new Message();
                            handler.sendMessage(msg);
                        }
                    }
                }
            });
            thread.start();
        }
    }
}
