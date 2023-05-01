package com.example.cyclingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

/**
 * Class gets logged users username and mCount data from firestore and shows them to user
 * You can also log out
 * @author Veeti Sorakivi
 */
public class ProfileActivity extends AppCompatActivity {
    TextView nameText;
    TextView mCountText;
    TextView totalKcalBurned;

    String userUID;

    // Firebase variables
    FirebaseAuth auth;
    FirebaseFirestore db;

    /**
     * Method runs when activity opens, and that is when we want to get data from firebase.
     * 1st we get users ID to access his data
     * 2nd we get reference to the document to it's data
     * if document was found we show the data on textViews
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        nameText = findViewById(R.id.usernameText);
        mCountText = findViewById(R.id.kmOnRoad);
        totalKcalBurned = findViewById(R.id.totalKcalBurned);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

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
                        nameText.setText(document.getData().get("username").toString());
                        double mCount = Integer.parseInt(document.getData().get("mCount").toString()) / 1000.0;
                        mCountText.setText(mCount + "km");
                        totalKcalBurned.setText(document.getData().get("totalKcalLost").toString());
                    } else {
                        Log.d("YYY", "No such document");
                    }
                } else {
                    Log.d("YYY", "get failed with ", task.getException());
                }
            }
        });
    }

    /**
     * When log out button was pressed, we log out from firebase auth
     * Also we start a new activity
     */
    public void logOut(View v){
        auth.signOut();
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }
}