package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
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
    public Vibrator vibrator;
    MediaPlayer mediaPlayer = null;


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

        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
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
        mediaPlayer = MediaPlayer.create(this, R.raw.beep);


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


                // This code was not triggering as expected. So I trying to moe it to play
                // when the disply values change
//                if (millisUntilFinished <= 5000) {
//                    if (millisUntilFinished % 1000 == 0) {
//                        Log.v("<5 second check", "tick");
//                        if(Build.VERSION.PREVIEW_SDK_INT < 26) {
//                            vibrator.vibrate(100);
//                        }else {
//                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
//                        }
//                        mediaPlayer.start();
//                    }
//                }

                if(Integer.valueOf( txtViewToUpdate.getText().toString()) <= 6){
                    int tmp = 6;
                    if(Integer.valueOf( txtViewToUpdate.getText().toString()) != tmp){
                        tmp = Integer.valueOf( txtViewToUpdate.getText().toString());
                        if(Build.VERSION.PREVIEW_SDK_INT < 26) {
                            vibrator.vibrate(100);
                        }else {
                            vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                        }
                        mediaPlayer.start();
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

//                if (millisUntilFinished <= 5000) {
//                    if (millisUntilFinished % 1000 == 0) {
//                        vibrator.vibrate(100);
//                        mediaPlayer.start();
//                    }
//                }

            if(Integer.valueOf( txtViewToUpdate.getText().toString()) <= 6){
                int tmp = 6;
                if(Integer.valueOf( txtViewToUpdate.getText().toString()) != tmp){
                    tmp = Integer.valueOf( txtViewToUpdate.getText().toString());
                    if(Build.VERSION.PREVIEW_SDK_INT < 26) {
                        vibrator.vibrate(100);
                    }else {
                        vibrator.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
                    }
                    mediaPlayer.start();
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


