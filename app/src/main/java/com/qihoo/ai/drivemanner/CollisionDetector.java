package com.qihoo.ai.drivemanner;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import java.util.Calendar;


/**
 * Created by panjunwei-iri on 2016/10/11.
 */

public class CollisionDetector implements SensorEventListener{
    private static final String TAG = CollisionDetector.class.getSimpleName();

    public static int mX, mY, mZ;
    private long lasttimestamp = 0;
    private Calendar mCalendar;

    CollisionDetector(){
        super();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        synchronized (this){
            if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
                int x = (int)event.values[0];
                int y = (int)event.values[1];
                int z = (int)event.values[2];
                mCalendar = Calendar.getInstance();
                long stamp = mCalendar.getTimeInMillis()/10001;

                int second = mCalendar.get(Calendar.SECOND);

                int px = Math.abs(mX - x);
                int py = Math.abs(mY - y);
                int pz = Math.abs(mZ - z);

                Log.e(TAG, "pX:" + px + "  pY:" + py + "  pZ:" + pz + "    stamp:"
                        + stamp + "  second:" + second);
                int maxvalue = getMaxValue(px, py, pz);

                if (maxvalue > 2 && (stamp - lasttimestamp) > 30) {
                    lasttimestamp = stamp;
                    Log.e(TAG, " sensor isMoveorchanged....");
                }else {
                    Log.e(TAG, " sensor isStill....");
                }

                mX = x;
                mY = y;
                mZ = z;

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
        } else if (py > px && py > pz) {
            max = py;
        } else if (pz > px && pz > py) {
            max = pz;
        }

        return max;
    }
}
