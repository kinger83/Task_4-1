package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WorkOutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);
    }

    public int totSets = 4;
    public int totWorkoutTime = 20;
    public int totRestTime = 20;

    int remainingSets = totSets;
    int remaingWorkoutTime = totWorkoutTime;
    int remainingRestTime = totRestTime;
    public boolean workoutStarted = false;

    public void workout(){

        // declare variables
        Button btn = findViewById(R.id.btnBeginWorkout);
        TextView workoutRemaining = findViewById(R.id.WorkoutValueTxt);


        if(workoutStarted == false){
            for (int i = 1; i <= totSets; i++) {
                CountDownTimer timer = new CountDownTimer((totWorkoutTime * 1000L), 1) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        NumberFormat f = new DecimalFormat("00");
                        long sec = (millisUntilFinished / 1000);
                        workoutRemaining.setText((f.format(sec)));
                    }

                    @Override
                    public void onFinish() {

                    }
                };
                    // Make button pause timer
                    // update progress value
                    // update progress display every second

                // -1 on remaining workout details

                while (remainingRestTime >= 0){
                    // Make button pause timer
                    // update progress value
                    // update progress display every second
                }

                // loop set count times
            }
        }

    }
}