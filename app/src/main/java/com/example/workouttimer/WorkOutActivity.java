package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WorkOutActivity extends AppCompatActivity {
    public int totSets = 4;
    public int totWorkoutTime = 20;
    public int totRestTime = 20;

    int remainingSets = -1;
    //int remainWorkoutTime = totWorkoutTime;
    //int remainingRestTime = totRestTime;
   // public boolean workoutStarted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_out);
        Intent intent = getIntent();
        totSets = intent.getIntExtra("sets", 0);
        remainingSets = totSets;
        totWorkoutTime = intent.getIntExtra("workout", 0);
        totRestTime = intent.getIntExtra("rest", 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView setView = findViewById(R.id.setValueTxt);
        TextView workoutView = findViewById(R.id.WorkoutValueTxt);
        TextView restView = findViewById(R.id.restsValueTxt);
        String setsString = String.valueOf(totSets);
        String workoutString = String.valueOf(totWorkoutTime);
        String restString = String.valueOf(totRestTime);
        setView.setText(setsString);
        workoutView.setText(workoutString);
        restView.setText(restString);


        TextView setsRemaining = findViewById(R.id.activeSetsVal);
        TextView workoutRemaining = findViewById(R.id.activeWorkoutValue);
        TextView restRemaining = findViewById(R.id.activeRestValue);
        setsRemaining.setText(String.valueOf(totSets));
        workoutRemaining.setText(String.valueOf(totWorkoutTime));
        restRemaining.setText(String.valueOf(totRestTime));
    }


    public void workout(View view) {

        // declare variables
        TextView setsRemaining = findViewById(R.id.activeSetsVal);
        TextView workoutRemaining = findViewById(R.id.activeWorkoutValue);
        TextView restRemaining = findViewById(R.id.activeRestValue);


        CountDownTimer restTimer = new CountDownTimer((totRestTime * 1000L), 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                restRemaining.setText((f.format(sec)));
            }

            @Override
            public void onFinish() {
                remainingSets--;
                setsRemaining.setText(String.valueOf(remainingSets));
                if(remainingSets > 0){
                    restRemaining.setText(String.valueOf(totRestTime));
                    workout(view);
                }
                else{
                    restRemaining.setText(String.valueOf(totRestTime));
                    remainingSets = totSets;
                    setsRemaining.setText(String.valueOf(remainingSets));
                }


            }
        };



        CountDownTimer workoutTimer = new CountDownTimer((totWorkoutTime * 1000L), 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                workoutRemaining.setText((f.format(sec)));
            }

            @Override
            public void onFinish() {
                workoutRemaining.setText(String.valueOf(totWorkoutTime));
                restTimer.start();

            }
        };
        workoutTimer.start();


    }

    public void returnToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

} // end class




// Make button pause timer
// update progress value
// update progress display every second

// -1 on remaining workout details


// Make button pause timer
// update progress value
// update progress display every second


// loop set count times