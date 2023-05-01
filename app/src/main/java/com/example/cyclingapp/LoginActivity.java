package com.example.cyclingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
 * Class does log in a user using firebase auth instance
 * @author Veeti Sorakivi
 */
public class LoginActivity extends AppCompatActivity {
    private EditText emailField, passField;
    // Firebase variables
    FirebaseAuth auth;
    // Validation rules
    private String passwordExpression = "";
    private String emailExpression = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = findViewById(R.id.emailField);
        passField = findViewById(R.id.passField);

        auth = FirebaseAuth.getInstance();
    }

    /**
     * Method runs when button is clicked.
     * Validates input fields
     */
    public void loginUser(View v){
        // When login button is pressed
        String email = emailField.getText().toString();
        String password = passField.getText().toString();
        if(!validateEmail(email)){
            emailField.setError("Email is not valid");
            emailField.requestFocus();
        }
        else if(!validatePassword(password)){
            passField.setError("Password is not valid");
            passField.requestFocus();
        }
        else{
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Logging in failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    /**
     * Method runs when button is pressed. if user does not have an account.
     * Method changes activity to register
     */
    public void changeActivityToRegister(View v){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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