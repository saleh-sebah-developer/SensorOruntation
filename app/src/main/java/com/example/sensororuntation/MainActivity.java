package com.example.sensororuntation;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    Sensor sensorAcc;
    Sensor sensorGoe;
    SensorManager sensorManager;
    float rotationM[]=new float[9];
    float accM[]=new float[3];
    float geoM[]=new float[3];
    float val[]=new float[3];
    TextView textView;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        sensorManager=(SensorManager) getSystemService(this.SENSOR_SERVICE);
        sensorAcc=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorGoe=sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        if(sensorAcc !=null && sensorGoe!=null){
            sensorManager.registerListener(this,sensorAcc,sensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this,sensorGoe,sensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            accM[0]=sensorEvent.values[0];
            accM[1]= sensorEvent.values[1];
            accM[2]=sensorEvent.values[2];
        }else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            geoM[0]=sensorEvent.values[0];
            geoM[1]= sensorEvent.values[1];
            geoM[2]=sensorEvent.values[2];
        }
        SensorManager.getRotationMatrix(rotationM,null,accM,geoM);
        SensorManager.getOrientation(rotationM,val);
        textView.setText("Val is "+(int)Math.toDegrees(val[0]));
        imageView.setRotation((int)Math.toDegrees(val[0]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}
