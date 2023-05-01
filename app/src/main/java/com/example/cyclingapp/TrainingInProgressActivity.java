package com.example.cyclingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
/**
 * Class includes a training program and its features.
 * @author Veeti Sorakivi
 */
public class TrainingInProgressActivity extends AppCompatActivity {
    private boolean trainingInProgress = false;
    public static final String TIME_MESSAGE = "com.example.myfirstapp.TIME_MESSAGE";
    public static final String TRAVEL_MESSAGE = "com.example.myfirstapp.LENGHT_MESSAGE";
    public static final String CALORIES_MESSAGE = "com.example.myfirstapp.CALORIES_MESSAGE";

    //Timer updateDataTimer = new Timer();
    Handler handler = new Handler();
    boolean stopHandler = false;

    ImageView kuvake;
    TextView bpmText, kcalText, speedText, timeText, kmText, ohje;
    SimulateData simulateData;
    Button controlButton;
    Reitti myReitti;
    int travelled, stage;
    boolean onRoute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_in_progress);

        SharedPreferences prefPutData = getSharedPreferences("data" ,Activity.MODE_PRIVATE);

        bpmText = findViewById(R.id.bpmText);
        kcalText = findViewById(R.id.kcalText);
        speedText = findViewById(R.id.speedText);
        timeText = findViewById(R.id.timeText);
        kmText = findViewById(R.id.kmText);
        controlButton = findViewById(R.id.controlButton);

        simulateData = new SimulateData();

        onRoute = prefPutData.getBoolean("onRoute", false);



        // jos singleton on alustettu

        if (Valittu.haeSingle().alustettu()) {
            Log.d("MYAPP", "on alustettu");
            if (onRoute) {
                Log.d("MYAPP", "jatketaan");

                travelled = prefPutData.getInt("prog", 0);
                stage = prefPutData.getInt("stage", 0);
            } else {
                Log.d("MYAPP", "ei jatketa");

                travelled = 0;
                stage = 0;
            }
            setRoute();
        }

        if(!prefPutData.getBoolean("trainingProgress", false)) {
            simulateData = new SimulateData();
        }
        else {
                double lengthInDouble = (double) prefPutData.getLong("length", 0);
                simulateData = new SimulateData(
                        prefPutData.getInt("bpm", 0),
                        prefPutData.getInt("kcal", 0),
                        prefPutData.getInt("speed", 0),
                        lengthInDouble,
                        prefPutData.getInt("currentSeconds", 0),
                        prefPutData.getInt("totalSeconds", 0));
                bpmText.setText(String.valueOf(simulateData.getBPM()) + " bpm");
                kcalText.setText(String.valueOf(simulateData.getKcal()));
                speedText.setText(String.valueOf(simulateData.getSpeed()) + " km/h");
                timeText.setText(String.valueOf(simulateData.getTotalSeconds()) + " s");
                kmText.setText(String.valueOf(simulateData.getTravelLenghtMeters()) + " m");
                controlButton.setText("Continue");
                controlButton.setBackgroundColor(getResources().getColor(R.color.green));

                // route progress load

                stage = prefPutData.getInt("stage", 0);
                travelled = prefPutData.getInt("progress", 0);
            }
        }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences prefPutData = getSharedPreferences("data", Activity.MODE_PRIVATE);
        SharedPreferences.Editor prefDataEditor = prefPutData.edit();
        prefDataEditor.putInt("bpm", simulateData.getBPM());
        prefDataEditor.putInt("kcal", simulateData.getKcal());
        prefDataEditor.putInt("speed", simulateData.getSpeed());
        prefDataEditor.putInt("currentSeconds", simulateData.getCurrentSeconds());
        prefDataEditor.putInt("totalSeconds", simulateData.getTotalSeconds());
        prefDataEditor.putLong("length", simulateData.getTravelLenghtMeters().longValue());
        prefDataEditor.putBoolean("trainingProgress", trainingInProgress);

        // Route progress save

        prefDataEditor.putInt("stage", stage);
        prefDataEditor.putInt("progress", travelled);

        prefDataEditor.commit();
    }

    /**
     * Method checks training status when button is clicked
     * If button was clicked and training is not in progress. Start training
     * If button was clicked and training is in progress. Stop training
     */
    public void controlTraining(View v){
        if(!trainingInProgress){
            startTraining();
        }
        else{
            stopTraining();
        }
    }

    /**
     * Method starts training process by setting trainingInProgress variable to true
     * Method activates handler, which starts showing data every 1000ms (1s)
     * Sets controlButtons text to stop and changes its color to red
     */
    private void startTraining(){
        trainingInProgress = true;

        controlButton.setText("Stop");
        controlButton.setBackgroundColor(getResources().getColor(R.color.red));

        stopHandler = false;
        handler.post(runnable);
    }

    /**
     * Method stops training by setting trainingInProgress variable value to false
     * Method stops handler so data is no longer changing and not shown on screen
     * Method stores some of the training data to intent so it can be accessed on next activity
     * Method opens a new activity FinishedTrainingActivity
     */
    private void stopTraining(){
        trainingInProgress = false;
        stopHandler = true;

        stage = 0;
        travelled = 0;
        //Make new intent and store values. start new activity

        Intent intent = new Intent(this, FinishedTrainingActivity.class);
        intent.putExtra(CALORIES_MESSAGE, String.valueOf(simulateData.getKcal()));
        intent.putExtra(TIME_MESSAGE, String.valueOf(simulateData.getMinutes()));
        intent.putExtra(TRAVEL_MESSAGE, String.valueOf(simulateData.getTravelLenght()));
        Log.d("YYY", String.valueOf(simulateData.getTravelLenght()));

        startActivity(intent);
    }

    Runnable runnable = new Runnable() {
        /**
         * Method run is called every 1000 millisecond
         * Method calls UpdateUI method and gives textView and value
         * We get the value from simulateData constructors methods
         * Values are converted to strings
         * Before updating data, we check if training is still in progress so the data wont change after training
         */
        @Override
        public void run() {
            if(trainingInProgress){
                updateUI(bpmText, String.valueOf(simulateData.getRandomBPM()) + " bpm");
                updateUI(kcalText, String.valueOf(simulateData.getRandomKcal()));
                updateUI(speedText, String.valueOf(simulateData.getRandomCurrentSpeed()) + " km/h");
                updateUI(timeText, String.valueOf(simulateData.getRandomTime()));
                updateUI(kmText, simulateData.getRandomCurrentLenght());

                //  Ohjeiden paivitys

                // jos singleton on maaritetty
                if (Valittu.haeSingle().alustettu()) {
                    if (onRoute && !ohje.getText().toString().equals("MAALI!") && Integer.parseInt(ohje.getText().toString().split(" ")[0]) <= 0) {

                        // ohjeiden indeksi kasvatetaan ja kuljettu malla palautetaan nollaan

                        stage++;
                        travelled = 0;
                        Log.d("MYAPP", "Paivitetty");


                        switch (Valittu.haeSingle().annaReitti().annaOhje(stage).getMita()) {
                            case "Vasen":
                                kuvake.setImageResource(R.drawable.arrow_left);
                                break;
                            case "Oikea":
                                kuvake.setImageResource(R.drawable.arrow_right);
                                break;
                            default:
                                Log.d("MYAPP", "Something is fucky");
                                break;

                        }
                    }

                    if (stage >= Valittu.haeSingle().annaReitti().annaArrayLength()-1) {
                        updateUI(ohje, "MAALI!");
                        kuvake.setImageResource(R.drawable.flagtrans);
                    } else {

                        // muutetaan km/h -> m/s

                        double muunnettu = Integer.parseInt(speedText.getText().toString().split(" ")[0]) * 0.27;


                        travelled += muunnettu;

                        if (onRoute) {
                            if (Valittu.haeSingle().annaReitti().annaPituus(stage) - travelled > 0) {
                                ohje.setText(Valittu.haeSingle().annaReitti().annaPituus(stage) - travelled + " m");
                            } else {
                                ohje.setText("0 m");
                            }
                        }
                    }
                }
            }
            if (!stopHandler) {
                handler.postDelayed(this, 1000);
            }
        }
    };

    /**
     * Method takes textView and String as parameter
     * Sets textViews text to data
     */
    private void updateUI(TextView tv, String data){
        tv.setText(data);
    }

    /**
     * Asettaa oikeat luvut ja kuvat reittiohjeisiin, joko alusta, tai kesken reitin jos aiempaa sessiota jatketaan.
     */
    private void setRoute() {
        SharedPreferences sharedPref = getSharedPreferences("data",Activity.MODE_PRIVATE);
        if (sharedPref.getBoolean("onRoute", false)) {

            kuvake = findViewById(R.id.ohjeKuva);
            ohje = findViewById(R.id.etai);

            myReitti = Valittu.haeSingle().annaReitti();

            switch (Valittu.haeSingle().annaReitti().annaOhje(stage).getMita()) {
                case "Vasen":
                    kuvake.setImageResource(R.drawable.arrow_left);
                    break;
                case "Oikea":
                    kuvake.setImageResource(R.drawable.arrow_right);
                    break;
                default:
                    Log.d("MYAPP", "Vaara ohje");
                    break;
            }

            kuvake.setVisibility(View.VISIBLE);
            ohje.setText(Valittu.haeSingle().annaReitti().annaPituus(stage) + " m");
            ohje.setVisibility(View.VISIBLE);
        }
    }

}