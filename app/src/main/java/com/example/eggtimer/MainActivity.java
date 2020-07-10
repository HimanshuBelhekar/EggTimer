package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView countdownTextView;
    Button startButton;
    CountDownTimer countDownTimer;
    boolean counterStarted = false;
    int min;
    int max;

    public void updateTimer(int secondsLeft){
        int minutes,seconds;
        minutes = secondsLeft/60;
        seconds = secondsLeft%60;
        String sec = Integer.toString(seconds);
        if (seconds<10){
            sec = "0" + sec;
        }
        countdownTextView.setText(String.format("%02d", minutes) + ":" + sec);
    }

    public void resetTimer(){
        countDownTimer.cancel();
        countdownTextView.setText("00:30");
        timerSeekBar.setProgress(min);
        startButton.setText("Start");
        timerSeekBar.setEnabled(true);
        counterStarted = false;
    }

    public void startFunction(View view){
        if(counterStarted){
            resetTimer();
        }else{

            startButton.setText("Stop");
            timerSeekBar.setEnabled(false);
            counterStarted = true;

            int time;
            time = Integer.parseInt(String.valueOf(timerSeekBar.getProgress()));

            countDownTimer = new CountDownTimer(time*1000 + 200,1000) {
                @Override
                public void onTick(long l) {
                    updateTimer((int)l/1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.clock_beeps);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        countdownTextView = (TextView) findViewById(R.id.countdownTextView);
        startButton = (Button) findViewById(R.id.startButton);

        min = 30;
        max = 600;

        timerSeekBar.setProgress(min);
        timerSeekBar.setMax(max);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerSeekBar.setProgress(i);
                updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}