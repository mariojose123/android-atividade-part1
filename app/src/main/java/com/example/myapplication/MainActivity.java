package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.location.Location;
import android.os.Bundle;
import android.hardware.TriggerEventListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager sensorManager;
    private TriggerEventListener triggerEventListener;
    Sensor sensor;
    Sensor sensor2;
    Sensor light;
    Sensor magnetism;
    float x1;
    float y1;
    float z1;
    float x1old;
    float y1old;
    float z1old;
    float threshold = 12.5f;
    Boolean firstupdate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button getGPSBtn =  findViewById(R.id.button2);
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        getGPSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTrack g = new GPSTrack(getApplicationContext());
                Location l = g.getLocation();
                if(l!=null)
                {
                    double lat = l.getLatitude();
                    double longi = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LAT: "+lat + "LONG: " +
                            longi, Toast.LENGTH_LONG).show();
                }
            }
        });

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        magnetism = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        sensorManager.registerListener(MainActivity.this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,light, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(MainActivity.this,magnetism, SensorManager.SENSOR_DELAY_NORMAL);


        // The activity is created
    }

    @Override
    protected void onResume() {
        super.onResume();
        //The activity has become visible (now it "resumes").
    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            EditText mEdit1 = findViewById(R.id.edittext1);
            EditText mEdit2 = findViewById(R.id.edittext2);
            EditText mEdit3 = findViewById(R.id.edittext3);
            mEdit1.setText("" + event.values[0]);
            mEdit2.setText("" + event.values[1]);
            mEdit3.setText("" + event.values[2]);
            ChangeAccelerometer(event.values[0], event.values[1], event.values[2]);

            if (CheckMovement()) {
                Intent i = new Intent(this, MainActivity2.class);
                startActivity(i);
            }
        }
        Sensor sensor = event.sensor;
        TextView textview2 = findViewById(R.id.textView5);
        if(sensor.getType() == Sensor.TYPE_GRAVITY)
        {
            textview2.setText("Gravity Intensity: " + event.values[0]);
        }
        TextView textview = findViewById(R.id.textView4);
        if(sensor.getType() == Sensor.TYPE_LIGHT) {
            textview.setText("Light Intensity: " + event.values[0]);
        }

    }

    private boolean CheckMovement(){
        float deltaX = Math.abs(x1old - x1);
        float deltaY = Math.abs(y1old - y1);
        float deltaZ = Math.abs(z1old - z1);
        if(deltaX>threshold || deltaY>threshold || deltaZ>threshold){
                return true;
        }
        return false;
    }

    private void ChangeAccelerometer(float x1new , float y1new , float z1new){
        if(firstupdate){
            x1old = x1new;
            y1old = y1new;
            z1old = z1new;
            firstupdate = false;
        }
        else{
            x1old = x1;
            y1old = y1;
            z1old = z1;
        }
        x1 = x1new;
        y1 = y1new;
        z1 = z1new;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}