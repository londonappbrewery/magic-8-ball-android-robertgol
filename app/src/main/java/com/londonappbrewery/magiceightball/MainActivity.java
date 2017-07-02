package com.londonappbrewery.magiceightball;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int previousNumber = 0;
    Random randomNumberGenerator = new Random();

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ShakeDetector mShakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mShakeDetector = new ShakeDetector();

        final ImageView ballDisplay = (ImageView) findViewById(R.id.image_eightBall);

        final int[] ballArray =
                {
                        R.drawable.ball1,
                        R.drawable.ball2,
                        R.drawable.ball3,
                        R.drawable.ball4,
                        R.drawable.ball5,
                };

        mShakeDetector.setOnShakelistener(new ShakeDetector.OnShakeListener()
        {
            @Override
            public void onShake(int count)
            {
                previousNumber = differentNumber();

                ballDisplay.setImageResource(ballArray[previousNumber]);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    public void onPause()
    {
        mSensorManager.unregisterListener(mShakeDetector);
        super.onPause();
    }

    protected int differentNumber()
    {
        int number = randomNumberGenerator.nextInt(5);

        if (previousNumber == number)
        {
            return differentNumber();
        }

        return number;
    }
}
