package com.example.dorei;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btn;
    private CountDownTimer countDownTimer;
    public boolean timerStopped;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTimer();
    }

    /** Starts the timer **/
    public void startTimer() {
        setTimerStartListener();
        timerStopped = false;
    }

    /** Stop the timer **/
    public void stopTimer() {
        countDownTimer.cancel();
        timerStopped = true;
    }

    private void setTimerStartListener() {
        // will be called at every 1500 milliseconds i.e. every 1.5 second.
        countDownTimer = new CountDownTimer(3000, 3000) {
            public void onTick(long millisUntilFinished) {

            }

            public void onFinish() {
                // Here do what you like...
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }.start();
    }

}
