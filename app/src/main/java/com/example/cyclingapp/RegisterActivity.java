package com.example.cyclingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class creates new user to firebase using auth instance
 * Logs user in automatically
 * @author Veeti Sorakivi
 */
public class RegisterActivity extends AppCompatActivity {
    private EditText emailField, passField, usernameField;
    // Firebase variables
    FirebaseAuth auth;
    FirebaseFirestore db;
    // Validation rules
    private String passwordExpression = "";
    private String emailExpression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passField);
        usernameField = findViewById(R.id.usernameField);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    /**
     * 1st method takes 3 inputs from the user.
     * 2nd mehtod validates the inputs.
     * 3rd method creates a new user
     * 4th method creates a new document named of users uid to firestore
     * 5th method stores username and traveled meters(0) to document
     * 6th Starts a new activity
     */
    public void createUser(View v){
        // When register button is pressed
        String email = emailField.getText().toString();
        String password = passField.getText().toString();
        String username = usernameField.getText().toString();

        if(!validateEmail(email)){
            emailField.setError("Email is not valid");
            emailField.requestFocus();
        }
        else if(!validatePassword(password)){
            passField.setError("Password is not valid");
            passField.requestFocus();
        }// TODO Validate username?
        else{
            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        // Add default data to firebase
                        String userId = auth.getCurrentUser().getUid(); // Get created users uid

                        // Create 2 HashMaps for different type of data and store data to hashmaps
                        Map<String, String> userStringMap = new HashMap<>();
                        userStringMap.put("username", username);
                        userStringMap.put("mCount", "0");
                        userStringMap.put("totalKcalLost", "0");

                        //Add data to firestore: users/userId/userName
                        db.collection("users").document(userId).set(userStringMap);

                        // Inform user that account is created and open a new activity
                        Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(RegisterActivity.this, "User creation failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Method runs when button was pressed
     * Method changes activity to login, if user already has an account.
     */
    public void changeActivityToLogin(View v){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }


    private Boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(emailExpression);
        Matcher matcher = pattern.matcher(email);

        if(email.contains("@") && email.length() >= 6){
            return true;
        }else{
            return false;
        }
    }
    private Boolean validatePassword(String password){
        Pattern pattern = Pattern.compile(passwordExpression);
        Matcher matcher = pattern.matcher(password);

        if(password.length() > 6){
            return true;
        }else{
            return false;
        }
    }
}