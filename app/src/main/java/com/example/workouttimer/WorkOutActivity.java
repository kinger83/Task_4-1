package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;

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
        Button btn = findViewById(R.id.btnBeginWorkout);
        if(workoutStarted == false){
            for (int i = 1; i <= totSets; i++) {
                while (remaingWorkoutTime >= 0){
                    CountDownTimer timer = new CountDownTimer((totWorkoutTime * 1000L), 1) {
                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {

                        }
                    };
                    // Make button pause timer
                    // update progress value
                    // update progress display every second
                }
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