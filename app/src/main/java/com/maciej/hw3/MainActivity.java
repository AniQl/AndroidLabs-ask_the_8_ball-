package com.maciej.hw3;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;


import static java.lang.Math.random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static int[] state = {10, 20, 30};
    static public SensorManager mSensorManager;
    public List<Sensor> SensorList;
    boolean sToggledL = false, sToggledP = false;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        SensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);

    }

    public void generateAnswer() {

        TextView response = findViewById(R.id.Response);

            int rndExpression;
                rndExpression = (int) (random() * 29) + 1;   //acc not random
            int s = 0; // state
            for (int i = 0; i < state.length; i++) {
                if (rndExpression >= state[i])
                    s = i+1;

            String Response[] = new String[10];
            switch (s) {
                case 0:
                    Response = getResources().getStringArray(R.array.AnswersPositive);
                    break;
                case 1:
                    Response = getResources().getStringArray(R.array.AnswersNeutral);
                    break;
                case 2:
                    Response = getResources().getStringArray(R.array.AnswersNegative);
                    break;
            }
            response.setText(Response[(int) (random() * Response.length)]);
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        for ( int i = 0; i < SensorList.size(); i++ )
            mSensorManager.unregisterListener(this, SensorList.get(i) );
    }

    @Override
    protected void onResume() {
        super.onResume();
        for ( int i = 0; i < SensorList.size(); i++ )
            if ( SensorList.get(i).getType() == Sensor.TYPE_ACCELEROMETER )
                mSensorManager.registerListener(this, SensorList.get(i), 5000);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float sensVal = event.values[0];

        if ((event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) && (sensVal < 3) && !sToggledL) {
            ListenState();
            sToggledL = true;

        }else if ((event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) && (sensVal > 3) && sToggledL) {
            sToggledL = false;
            if ( !sToggledP )
                generateAnswer();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
    public void ListenState() {

        TextView response = findViewById(R.id.Response);
//        response.setText(R.string.listening);
    }
}
