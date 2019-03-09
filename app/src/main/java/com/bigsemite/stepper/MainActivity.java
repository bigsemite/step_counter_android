package com.bigsemite.stepper;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener {

    TextView detView, countView;
    SensorManager sm;
    Sensor detSensor, stpSensor;
    int detCount =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //create the views
        detView = (TextView)findViewById(R.id.textView);
        countView = (TextView) findViewById(R.id.textView2);

        //initialize the sensormanager class
        sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        //initialize the step detector and counter classes
        detSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        stpSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        //check if the sensors exist
        if (detSensor != null){
            sm.registerListener(this,detSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        else {
            detView.setText("No Step Detector detected");
        }

        if (stpSensor != null){
            sm.registerListener(this,stpSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
        else{
            countView.setText("No Step Conter detected");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        switch (event.sensor.getType()){
            case Sensor.TYPE_STEP_COUNTER:
                int f = (int)event.values[0];
                countView.setText(f+" Steps");
            case Sensor.TYPE_STEP_DETECTOR:
                detCount++;
                detView.setText(detCount + "");
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        sm.unregisterListener(this);
    }
}
