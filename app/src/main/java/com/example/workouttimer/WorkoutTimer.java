//
//final CountDownTimer restTimer = new CountDownTimer((totRestTime * 1000L), 1) {
//
//@Override
//public void onTick(long millisUntilFinished) {
//        setCurrentTimer(this);
//        NumberFormat f = new DecimalFormat();
//        long sec = (millisUntilFinished / 1000);
//        restRemaining.setText((f.format(sec)));
//        int progress = (int) (((totRestTime * 1000L) - millisUntilFinished) / ((totRestTime * 1000L) / 100));
//        progBar.setProgress(progress);
//
//        if (millisUntilFinished <= 5000) {
//        if (millisUntilFinished % 1000 == 0) {
//        //play beep here
//        }
//        }
//
//        }
//
//@Override
//public void onFinish() {
//        remainingSets--;
//        setsRemaining.setText(String.valueOf(remainingSets));
//        if(remainingSets > 0){
//        restRemaining.setText(String.valueOf(totRestTime));
//        workoutRunning = false;
//        //workout(view);
//        workoutTimer.start();
//        }
//        else{
//        restRemaining.setText(String.valueOf(totRestTime));
//        remainingSets = totSets;
//        setsRemaining.setText(String.valueOf(remainingSets));
//        button.setText("Start Workout");
//        workoutRunning = false;
//        }
//
//
//        }
//        };
//
////      Set up the rest timer which loops with the workout timer until sets finished
//final CountDownTimer workoutTimer = new CountDownTimer((totWorkoutTime * 1000L), 1) {
//@Override
//public void onTick(long millisUntilFinished) {
//        setCurrentTimer(this);
//        NumberFormat f = new DecimalFormat();
//        long sec = (millisUntilFinished / 1000);
//        workoutRemaining.setText((f.format(sec)));
//        int progress = (int) (((totWorkoutTime * 1000L) - millisUntilFinished) / ((totWorkoutTime * 1000L) / 100));
//        progBar.setProgress(progress);
//
//        if (millisUntilFinished <= 5000) {
//        if (millisUntilFinished % 1000 == 0) {
//        //play beep here
//        }
//        }
//        }
//
//@Override
//public void onFinish() {
//        workoutRemaining.setText(String.valueOf(totWorkoutTime));
//        restTimer.start();
//
//        }
//        };