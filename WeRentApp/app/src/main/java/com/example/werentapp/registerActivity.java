package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class registerActivity extends AppCompatActivity {
    private EditText txtEmail,txtPass;
    private Button btnReg;

    private FirebaseAuth auth;

    private void cleartextfields()
    {
        txtEmail.setText("");
        txtPass.setText("");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.etEmailL);
        txtPass = findViewById(R.id.etPassL);
        btnReg = findViewById(R.id.btnregis);

        auth = FirebaseAuth.getInstance();

        btnReg.setOnClickListener(new View.OnClickListener()
                                  {
                                    @Override
                                      public void onClick(View v)
                                    {
                                        String textEmail = txtEmail.getText().toString().trim();
                                        String textPass = txtPass.getText().toString().trim();

                                        if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPass)) {

                                            Toast.makeText(registerActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                                        }else if (textPass.length() < 6){
                                            Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();

                                        }else
                                        {
                                            registerUser(textEmail, textPass);
                                        }
                                    }



    });
}

    private void registerUser(String Email, String Pass) {
        auth.createUserWithEmailAndPassword(Email,Pass).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
              if(task.isSuccessful())
              {
                  Toast.makeText(registerActivity.this, "User was registered successfully", Toast.LENGTH_SHORT).show();
                  startActivity(new Intent(registerActivity.this,MainActivity.class));
                  finish();
              }else{
                  cleartextfields();
                  Toast.makeText(registerActivity.this,"Registration failed", Toast.LENGTH_SHORT).show();
              }
            }
        });
    }
}