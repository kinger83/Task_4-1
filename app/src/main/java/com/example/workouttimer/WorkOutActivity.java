package com.example.workouttimer;

import static android.media.AudioManager.STREAM_NOTIFICATION;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class WorkOutActivity extends AppCompatActivity {
    // Set class variables
    public int totSets = 4;
    public int totWorkoutTime = 20;
    public int totRestTime = 20;

    int remainingSets = -1;
    public Boolean workoutRunning = false;
    public CountDownTimer currTimer = null;
    Boolean paused = false;



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

// this function is called when user clicks start workout
    public void workout(View view) {

        // declare variables
        TextView setsRemaining = findViewById(R.id.activeSetsVal);
        TextView workoutRemaining = findViewById(R.id.activeWorkoutValue);
        TextView restRemaining = findViewById(R.id.activeRestValue);
        Button button = (Button) view;
        ProgressBar progBar = findViewById(R.id.progressBar);



        // setup the workout timer - which loops with the rest timer below
        final CountDownTimer restTimer = new CountDownTimer((totRestTime * 1000L), 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                setCurrentTimer(this);
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                restRemaining.setText((f.format(sec)));
                int progress = (int) (((totRestTime * 1000L) - millisUntilFinished) / ((totRestTime * 1000L) / 100));
                progBar.setProgress(progress);

                if (millisUntilFinished <= 5000) {
                    if (millisUntilFinished % 1000 == 0) {
                        //play beep here
                    }
                }

            }

            @Override
            public void onFinish() {
                remainingSets--;
                setsRemaining.setText(String.valueOf(remainingSets));
                if(remainingSets > 0){
                    restRemaining.setText(String.valueOf(totRestTime));
                    workoutRunning = false;
                    workout(view);
                }
                else{
                    restRemaining.setText(String.valueOf(totRestTime));
                    remainingSets = totSets;
                    setsRemaining.setText(String.valueOf(remainingSets));
                    button.setText("Start Workout");
                    workoutRunning = false;
                }


            }
        };


//      Set up the rest timer which loops with the workout timer until sets finished
        final CountDownTimer workoutTimer = new CountDownTimer((totWorkoutTime * 1000L), 1) {
            @Override
            public void onTick(long millisUntilFinished) {
                setCurrentTimer(this);
                NumberFormat f = new DecimalFormat();
                long sec = (millisUntilFinished / 1000);
                workoutRemaining.setText((f.format(sec)));
                int progress = (int) (((totWorkoutTime * 1000L) - millisUntilFinished) / ((totWorkoutTime * 1000L) / 100));
                progBar.setProgress(progress);

                if (millisUntilFinished <= 5000) {
                    if (millisUntilFinished % 1000 == 0) {
                        //play beep here
                    }
                }
            }

            @Override
            public void onFinish() {
                workoutRemaining.setText(String.valueOf(totWorkoutTime));
                restTimer.start();

            }
        };


        // when start clicked, if not currently working out, this starts the work out
        if(workoutRunning == false) {
            workoutTimer.start();
            button.setText("Pause");
            workoutRunning = true;

        }
        // if work out is happening the button changes to a pause/resume button
        else{
            // if not paused, this pauses workout
            if(paused == false) {
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
                if(currTimer == workoutTimer){
                    tempTotTime = totWorkoutTime;
                    totWorkoutTime = Integer.parseInt((workoutRemaining.getText().toString()));
                    currTimer.start();
                    //totWorkoutTime = (int) tempTotTime;
                }else{
                    tempTotTime = totRestTime;
                    totRestTime = Integer.parseInt((restRemaining.getText().toString()));
                    currTimer.start();
                    //totRestTime = (int) tempTotTime;
                }


            }
        }


    }
    // function wll be called to return to main activity
    private void returnToMain(View view){
        currTimer.cancel();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //sets the current countdown timer to a variable for us to access.
    private void setCurrentTimer(CountDownTimer curTimer){
        currTimer = curTimer;
    }

} // end class
