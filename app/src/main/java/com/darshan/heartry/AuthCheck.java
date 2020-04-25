package com.darshan.heartry;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class AuthCheck extends AppCompatActivity {
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_check);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Intent intent;
                if (firebaseAuth.getCurrentUser() != null) {
                    String userID = firebaseAuth.getUid();
                    intent = new Intent(AuthCheck.this, MainActivity.class);
                    intent.putExtra("UserID", userID);

                } else {
                    intent = new Intent(AuthCheck.this, LoginActivity.class);
                }
                startActivity(intent);
            }
        };
    }
}
