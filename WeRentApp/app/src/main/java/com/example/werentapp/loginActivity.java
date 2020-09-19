package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity {

    private EditText textMail,textPass;
    private Button btnLog;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textMail = findViewById(R.id.etEmailL);
        textPass = findViewById(R.id.etPassL);
        btnLog = findViewById(R.id.btsignin);

        auth =FirebaseAuth.getInstance();

        btnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String Email = textMail.getText().toString().trim();
                String Pass = textPass.getText().toString().trim();
                loginUser(Email,Pass);

            }
        });

    }

    private void loginUser(String email, String pass) {
    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(loginActivity.this, MainActivity.class));
            finish();
        }


    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(loginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(loginActivity.this, StartActivity.class));
            finish();
        }
    });

    }
}