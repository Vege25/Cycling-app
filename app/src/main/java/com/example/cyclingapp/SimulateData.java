package com.example.cyclingapp;

import android.util.Log;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Class simulates TrainingInProgressActivity's values
 * Values are random but made realistic as possible
 * @author Veeti Sorakivi
 */
public class SimulateData {
    private Integer currentBPM, kcalLost, currentSpeed, currentSeconds, totalSeconds;
    private double travelLenghtMeters;
    private Integer bpmMin = 140, bpmMax = 180;
    private Integer speedMin = 10, speedMax = 25;

    /**
     * Constructor sets default values when training is started
     */
    public SimulateData(){
        currentBPM = getRandomNumber(bpmMin, bpmMax + 1);
        kcalLost = 0;
        currentSpeed = getRandomNumber(speedMin, speedMax + 1);
        travelLenghtMeters = 0.0;
        currentSeconds = -1; // koska heti kun treeni alkaan niin vaihtuu sekunniksi
        totalSeconds = -1;
    }
    /**
     * Constructor sets values from previous training when training is started
     * This constructor runs when app was paused and re opened when training is still on progress
     */
    public SimulateData(int currentBPM, int kcalLost, int currentSpeed, double travelLenghtMeters,  int currentSeconds, int totalSeconds){
        this.currentBPM = currentBPM;
        this.kcalLost = kcalLost;
        this.currentSpeed = currentSpeed;
        this.travelLenghtMeters = travelLenghtMeters;
        this.currentSeconds = currentSeconds;
        this.totalSeconds = totalSeconds;

    }

    /**
     * 1st method randomizes a value between 0 and 1. If value is 0 then we decrease bpm, if value is 1 we increase it.
     * 2nd we randomize another value between 1 and 3, which gets added or decreased of bpm depending of first value.
     * We set currentBPM variable to its value.
     * @return currentBPM which was increased or decreased so heart rate stays about the same.
     */
    public Integer getRandomBPM(){
        // Get random number 0 or 1 -> 0 decreases bpm, 1 increases bpm
        Integer increaseOrDecrease = getRandomNumber(0, 2);
        if(increaseOrDecrease == 0){
            Integer randomVal = getRandomNumber(1, 4); // random value between 1-3
            if(currentBPM - randomVal >= bpmMin){
                currentBPM -= randomVal;
            }
        }
        else if(increaseOrDecrease == 1){
            Integer randomVal = getRandomNumber(1, 4); // random value between 1-3
            if(currentBPM + randomVal <= bpmMax){
                currentBPM += randomVal;
            }
        }
        return currentBPM;
    }

    /**
     * !!!Disabled temporarily!!!
     * 1st method randomizes a value between 0 and 1. If value is 0 then we decrease speed, if value is 1 we increase it.
     * 2nd we randomize another value between 1 and 2, which gets added or decreased of speed depending of first value.
     * We set currentSpeed variable to its value.
     * @return currentSpeed which was increased or decreased so speed stays about the same.
     */
    public Integer getRandomCurrentSpeed(){
        /*Integer increaseOrDecrease = getRandomNumber(0, 2);
        if(increaseOrDecrease == 0){
            Integer randomVal = getRandomNumber(1, 3); // random value between 1-2
            if(currentSpeed - randomVal >= speedMin){
                currentSpeed -= randomVal;
            }
        }
        else if(increaseOrDecrease == 1){
            Integer randomVal = getRandomNumber(1, 4); // random value between 1-2
            if(currentSpeed + randomVal <= speedMax){
                currentSpeed += randomVal;
            }
        }*/
        return currentSpeed;
    }

    /**
     * Method randomizes value between 1 and 2.
     * @return kcalLost which was incremented by a value
     */
    public Integer getRandomKcal(){
        kcalLost += getRandomNumber(1, 3); // change kcal by 1 or 2
        return kcalLost;
    }

    /**
     * Method counts seconds and adds 1 to totalSeconds
     * If seconds gets greater than 60 we show minutes and seconds
     * @return minutes and seconds
     */
    public String getRandomTime(){
        totalSeconds++;
        if(totalSeconds < 60){
            return totalSeconds + "s";
        }
        else{
            int minutes = (int)Math.floor(totalSeconds / 60);
            if(currentSeconds < 59){
                currentSeconds++;
            }else{
                currentSeconds = 0;
            }
            return minutes + "min " + currentSeconds + "s";
        }
    }

    /**
     * Method calculates distance from speed * time
     * 1st it converts currentSpeed (km/h) to (m/s) by dividing currentSpeed by 3.6
     * 2nd we check if distance is less than 1 km, then we show meters to user. If it is over 1km we show kilometers
     * @return travelLenght
     */
    public String getRandomCurrentLenght(){
        double speedInMPS = currentSpeed / 3.6;
        if(totalSeconds * speedInMPS < 999.0){
            travelLenghtMeters = totalSeconds * speedInMPS;
            Log.d("YYY", "1. meters: " +travelLenghtMeters + " speed: " + currentSpeed + " sec: " + totalSeconds);
            return (int)Math.round(travelLenghtMeters) + "m";
        }
        else if(totalSeconds * speedInMPS >= 999.0){
            travelLenghtMeters = totalSeconds * speedInMPS;
            Log.d("YYY", "2. meters: " +travelLenghtMeters + " speed: " + currentSpeed + " sec: " + totalSeconds);
            return (int)Math.round(travelLenghtMeters) / 1000.0 + "km";
        }
        else{
            return "Error";
        }
    }

    /**
     * When method is called it was given min and max parameters.
     * Method randomizes a value between min and max
     * @return value that was randomized
     */
    public Integer getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    // After finishing training return variables without changing values
    /**
     * @return currentBPM variable
     */
    public Integer getBPM(){
        return currentBPM;
    }

    /**
     * @return currentSpeed variable
     */
    public Integer getSpeed(){
        return currentSpeed;
    }

    /**
     * @return kcalLost variable
     */
    public Integer getKcal(){
        return kcalLost;
    }

    /**
     * @return currentSeconds variable
     */
    public Integer getCurrentSeconds(){
        return currentSeconds;
    }

    /**
     * @return totalSeconds variable
     */
    public Integer getTotalSeconds(){
        return totalSeconds;
    }

    /**
     * @return kcalLost variable
     */
    public Double getTravelLenghtMeters(){
        return travelLenghtMeters;
    }

    /**
     * @return totalSeconds variable floored
     */
    public Integer getMinutes(){
        return (int)Math.floor(totalSeconds / 60);
    }

    /**
     * @return travelLenght variable rounded
     */
    public Integer getTravelLenght(){
        return (int)Math.round(travelLenghtMeters);
    }
}
