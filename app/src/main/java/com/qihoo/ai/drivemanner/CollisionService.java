package com.qihoo.ai.drivemanner;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by panjunwei-iri on 2016/10/11.
 */

public class CollisionService extends Service{
    private static final String TAG = CollisionService.class.getSimpleName();
    public static Boolean flag = false ;
    public CollisionDetector collisionDetector;
    private SensorManager mSensorManager;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Received start id " + startId + ": " + intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread( new Runnable() {
            public void run() {
                startCollisionDetector();
            }
        }).start();
        Toast.makeText(this, "service start", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        flag = false;
        if(collisionDetector != null){
            mSensorManager.unregisterListener(collisionDetector);
        }
        Toast.makeText(this, "service stop", Toast.LENGTH_SHORT).show();
    }

    private void startCollisionDetector(){
        flag = true;
        Log.e(TAG,"start and registerListener CollisionDetector");
        collisionDetector = new CollisionDetector();
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(collisionDetector, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}
