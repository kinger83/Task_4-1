package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.NumberFormat;


public class WorkOutActivity extends AppCompatActivity {
    // Set class variables
    public int totSets = 0;
    public int totWorkoutTime = 0;
    public int totRestTime = 0;

    int remainingSets = -1;
    public Boolean workoutRunning = false;
    public CountDownTimer currTimer = null;
    public Boolean isWorkout = true;
    Boolean paused = false;

    TextView setsRemaining = null;
    TextView workoutRemaining = null;
    TextView restRemaining = null;
    ProgressBar progBar = null;



// Set some variable from the intent
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

    // set up and display correct values in the view
    @Override
    protected void onStart() {
        super.onStart();
        // sets totals to correct values
        TextView setView = findViewById(R.id.setValueTxt);
        TextView workoutView = findViewById(R.id.WorkoutValueTxt);
        TextView restView = findViewById(R.id.restsValueTxt);
        String setsString = String.valueOf(totSets);
        String workoutString = String.valueOf(totWorkoutTime);
        String restString = String.valueOf(totRestTime);
        setView.setText(setsString);
        workoutView.setText(workoutString);
        restView.setText(restString);


        setsRemaining = findViewById(R.id.activeSetsVal);
        workoutRemaining = findViewById(R.id.activeWorkoutValue);
        restRemaining = findViewById(R.id.activeRestValue);
        setsRemaining.setText(String.valueOf(totSets));
        workoutRemaining.setText(String.valueOf(totWorkoutTime));
        restRemaining.setText(String.valueOf(totRestTime));
        progBar = findViewById(R.id.progressBar);
    }

// this function is called when user clicks start workout
    public void workout(View view) {

        // declare variables

        Button button = (Button) view;
        // when start clicked, if not currently working out, this starts the work out
        if (workoutRunning == false) {
            isWorkout = true;
            runCountdowns(view);
            button.setText("Pause");
            workoutRunning = true;
        }
        // if work out is happening the button changes to a pause/resume button
        else {
            // if not paused, this pauses workout
            if (paused == false) {
                Log.v("checking", "false / true");
                paused = true;
                currTimer.cancel();

                button.setText("Resume");
            }
            // if it was paused this resumes the workout
            else{
                long tempTotTime = -1;
                paused = false;
                button.setText("Pause");

               // trying to set the resume value to the value that was remaining when workout paused
                if(isWorkout == true)
                {
                    tempTotTime = totWorkoutTime;
                    totWorkoutTime = Integer.parseInt((workoutRemaining.getText().toString()));
                    runCountdowns(view);
                    totWorkoutTime = (int) tempTotTime;
                }
                else
                {
                    tempTotTime = totRestTime;
                    totRestTime = Integer.parseInt((restRemaining.getText().toString()));
                    runCountdowns(view);
                    totRestTime = (int) tempTotTime;
                }
           }
        }
    }
    // function wll be called to return to main activity
    public void returnToMain(View view){
        currTimer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //sets the current countdown timer to a variable for us to access.
    private void setCurrentTimer(CountDownTimer curTimer){
        currTimer = curTimer;
    }

    public void runCountdowns(View view){
        if(remainingSets == 0){
            workoutRunning = false;
            remainingSets = totSets;
            Button button = (Button) view;
            button.setText("Start Workout");
            setsRemaining.setText(String.valueOf(remainingSets));
            return;
        }


        if(isWorkout == true) {
            startWorkoutTimer(totWorkoutTime, workoutRemaining, progBar, view);
        }
        else {
            startRestTimer(totRestTime, restRemaining, progBar, view);
        }

    }
    public void startWorkoutTimer(long timeToRun, TextView txtViewToUpdate, ProgressBar progBarToUpdate, View view){
        CountDownTimer workoutTimer = new CountDownTimer((timeToRun * 1000L), 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                setCurrentTimer(this);
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                txtViewToUpdate.setText((f.format(sec)));
                int progress = (int) (((totWorkoutTime * 1000L) - millisUntilFinished) / ((totWorkoutTime * 1000L) / 100));
                progBarToUpdate.setProgress(progress);

                if (millisUntilFinished <= 5000) {
                    if (millisUntilFinished % 1000 == 0) {
                        //play beep here
                    }
                }
            }

            @Override
            public void onFinish() {
                workoutRemaining.setText(String.valueOf(totWorkoutTime));
                isWorkout = false;
                runCountdowns(view);

            }
        }.start();
    }
    public void startRestTimer(long timeToRun, TextView txtViewToUpdate, ProgressBar progBarToUpdate, View view){
        CountDownTimer restTimer = new CountDownTimer((timeToRun * 1000L), 1) {

        @Override
        public void onTick(long millisUntilFinished) {
                setCurrentTimer(this);
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                txtViewToUpdate.setText((f.format(sec)));
                int progress = (int) (((totRestTime * 1000L) - millisUntilFinished) / ((totRestTime * 1000L) / 100));
                progBarToUpdate.setProgress(progress);

                if (millisUntilFinished <= 5000) {
                    if (millisUntilFinished % 1000 == 0) {
                        //play beep here
                    }
                }
        }

        @Override
        public void onFinish() {
            isWorkout = true;
            remainingSets--;
            setsRemaining.setText(String.valueOf(remainingSets));
            txtViewToUpdate.setText(String.valueOf(totRestTime));

            runCountdowns(view);;
        }
        }.start();
    }

} // end class


