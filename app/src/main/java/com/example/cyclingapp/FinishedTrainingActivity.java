package com.example.cyclingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/**
 * Class shows stats of finished training and stores training data to firebase
 * @author Veeti Sorakivi
 */
public class FinishedTrainingActivity extends AppCompatActivity {
    TextView totalTimeTV, totalLenghtTV, kcalLostTV;

    String travel, kcal;
    String userUID;

    //firebase variables
    FirebaseFirestore db;
    FirebaseAuth auth;

    /**
     * Method runs when activity starts
     * Method takes data from previous activity and stores them on variables
     * Sets data to on screen texts
     * Calls private method addDataToFirebase which stores some of the data to firebase (mCount)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_training);
        totalTimeTV = findViewById(R.id.totalTimeText);
        totalLenghtTV = findViewById(R.id.totalLenghtText);
        kcalLostTV = findViewById(R.id.kcalLostText);

        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        // Get data from previous Activity
        Intent intent = getIntent();
        travel = intent.getStringExtra(TrainingInProgressActivity.TRAVEL_MESSAGE);
        kcal = intent.getStringExtra(TrainingInProgressActivity.CALORIES_MESSAGE);
        String time = intent.getStringExtra(TrainingInProgressActivity.TIME_MESSAGE);

        totalLenghtTV.setText(travel + "m");
        kcalLostTV.setText(kcal);
        totalTimeTV.setText(time + "min");

        addDataToFirestore();
    }

    /**
     * Method opens MainActivity class
     */
    public void okButtonPressed(View v){
        startActivity(new Intent(FinishedTrainingActivity.this, MainActivity.class));
    }

    /**
     * Method stores data to firestore
     */
    private void addDataToFirestore(){
        if(auth.getCurrentUser() != null){
            userUID = auth.getCurrentUser().getUid();
        }
        DocumentReference docRef = db.collection("users").document(userUID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Integer previousKmCount = Integer.parseInt(document.getData().get("mCount").toString());
                        Integer previousKcalCount = Integer.parseInt(document.getData().get("totalKcalLost").toString());
                        Integer newKMCount = previousKmCount + Integer.parseInt(travel);
                        Integer newKcalCount = previousKcalCount + Integer.parseInt(kcal);
                        String username = document.getData().get("username").toString();

                        Map<String, String> updateFirestoreMap = new HashMap<>();
                        updateFirestoreMap.put("mCount", String.valueOf(newKMCount));
                        updateFirestoreMap.put("totalKcalLost", String.valueOf(newKcalCount));
                        updateFirestoreMap.put("username", username);

                        //Add data to firestore: users/userId/kmCount
                        db.collection("users").document(userUID).set(updateFirestoreMap);
                    } else {
                        Log.d("YYY", "No such document");
                    }
                } else {
                    Log.d("YYY", "get failed with ", task.getException());
                }
            }
        });
    }
}