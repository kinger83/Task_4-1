package com.example.workouttimer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    boolean inWorkout = false;

    public void OnWorkOutBeginButton(View view) {
        // dont process if work in progress
        if (inWorkout) return;

        // set variables
        EditText setsText = findViewById(R.id.editTextSetsNumber);
        EditText workoutText = findViewById(R.id.editTextWorkoutTimeNumber);
        EditText restText = findViewById(R.id.editTextRestTimeNumber);
        int setCountInput = -1;
        int workOutTimeInput = -1;
        int restTimeInput = -1;

        // Get user inputs
        setCountInput = getSetValue(setsText);
        workOutTimeInput = getWorkoutTimeValue(workoutText);
        restTimeInput = getRestTimeValue(restText);

        if(setCountInput >0 && workOutTimeInput >0 && restTimeInput >0){

            Intent intent = new Intent(this, WorkOutActivity.class);
            intent.putExtra("sets", setCountInput);
            intent.putExtra("workout", workOutTimeInput);
            intent.putExtra("rest", restTimeInput);
            startActivity(intent);

            //            Toast testToast = Toast.makeText(getApplicationContext(), "Swap activity", Toast.LENGTH_SHORT);
//            testToast.show();
        }




//        Toast testToast = Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT);
//        testToast.show();
//        inWorkout = true;
        }


    private int getSetValue(EditText input){
        try {
            int res = Integer.valueOf(input.getText().toString());
            return res;
        } catch (NumberFormatException e) {
            Toast invalSetNumber = Toast.makeText(getApplicationContext(), "Please enter a whole number of sets.", Toast.LENGTH_SHORT);
            invalSetNumber.show();
            return -1;
        }
    }

    private int getWorkoutTimeValue(EditText input){
        try {
            int res = Integer.valueOf(input.getText().toString());
            return res;
        } catch (NumberFormatException e) {
            Toast invalSetNumber = Toast.makeText(getApplicationContext(), "Please enter a whole number of seconds for Workout.", Toast.LENGTH_SHORT);
            invalSetNumber.show();
            return -1;
        }
    }

    private int getRestTimeValue(EditText input){
        try {
            int res = Integer.valueOf(input.getText().toString());
            return res;
        } catch (NumberFormatException e) {
            Toast invalSetNumber = Toast.makeText(getApplicationContext(), "Please enter a whole number of seconds for rest.", Toast.LENGTH_SHORT);
            invalSetNumber.show();
            return -1;
        }
    }
}