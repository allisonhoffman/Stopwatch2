package com.hfad.stopwatch;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;
import android.util.Log;
import java.util.Locale;

public class StopwatchActivity extends Activity {

    private static final int SECS_PER_MIN = 60;
    private static final int MINS_PER_HOUR = 60;
    private static final int SECS_PER_HOUR = SECS_PER_MIN * MINS_PER_HOUR;

    // Number of seconds displayed on the stopwatch.
    private int seconds = 0;
    // Is the stopwatch running?
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        Log.d("StopwatchActivity", "In onCreate");
        if (savedInstanceState != null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            wasRunning = savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("StopwatchActivity", "In onSavedInstanceState");
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("wasRunning", wasRunning);
    }

    @Override
    protected void onPause() {
        super.onPause();
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (wasRunning) {
            running = true;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    // Start the stopwatch running when the Start button is clicked.
    public void onClickStart(View view) {
        Log.d("StopwatchActivity", "In onClickStart");
        running = true;
    }

    // Stop the stopwatch running when the Stop button is clicked.
    public void onClickStop(View view) {
        Log.d("StopwatchActivity", "In onClickStop");
        running = false;
    }

    // Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view) {
        Log.d("StopwatchActivity", "In onClickReset");
        running = false;
        seconds = 0;
    }

    private void runTimer() {
        final TextView timeView = (TextView)findViewById(R.id.time_view);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / SECS_PER_HOUR;
                int minutes = (seconds % SECS_PER_HOUR) / MINS_PER_HOUR;
                int secs = seconds % SECS_PER_MIN;
                String time = String.format(Locale.getDefault(), "%d:%02d:%02d",
                        hours, minutes, secs);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }


}
