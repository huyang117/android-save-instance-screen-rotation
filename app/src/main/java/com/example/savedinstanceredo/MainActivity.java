package com.example.savedinstanceredo;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText username;
    private Button startBtn;
    private Button clickBtn;
    private TextView timeDisplay;
    private TextView scoreDisplay;

    private CountDownTimer countDownTimer;
    private int score = 0;
    private long timeLeftInMillis = 60000;
    private boolean isTimerRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username);
        startBtn = (Button)findViewById(R.id.startBtn);
        timeDisplay = (TextView)findViewById(R.id.timeDisplay);
        scoreDisplay = (TextView)findViewById(R.id.scoreDisplay);
        clickBtn = (Button)findViewById(R.id.clickBtn);

        clickBtn.setEnabled(false);

        clickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickBtn.setEnabled(true);
                startTimer();
            }
        });
    }

    private void increment() {
        score += 1;
        scoreDisplay.setText("Your Score: " + score);
    }

    private void startTimer(){
        countDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                isTimerRunning = true;
                timeLeftInMillis = millisUntilFinished;

                // update count down text
                int minutes = (int) (timeLeftInMillis/1000)/60;
                int seconds = (int) (timeLeftInMillis/1000)%60;
                String timeLeft = String.format(Locale.getDefault(),
                        "%02d:%02d",minutes,seconds);
                timeDisplay.setText("Time Left: "+timeLeft);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                clickBtn.setEnabled(false);
                timeDisplay.setText("Time is up");
            }
        }.start();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("username", username.getText().toString());
        outState.putInt("score",score);
        outState.putLong("timeLeftInMillis",timeLeftInMillis);
        outState.putBoolean("isTimerRunning",isTimerRunning);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        score = savedInstanceState.getInt("score");
        scoreDisplay.setText("Your Score: " + score);

        String usernameStr = savedInstanceState.getString("username");
        username.setText(usernameStr);

        timeLeftInMillis = savedInstanceState.getLong("timeLeftInMillis");
        isTimerRunning = savedInstanceState.getBoolean("isTimerRunning");
        if (isTimerRunning == true) {
            startTimer();
            clickBtn.setEnabled(true);
        }
    }
}
