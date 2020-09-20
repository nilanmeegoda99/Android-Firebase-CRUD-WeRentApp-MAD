package com.example.werentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button btnReg,btnLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnReg = findViewById(R.id.signup);
        btnLog = findViewById(R.id.signin);

        btnReg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                startActivity(new Intent(StartActivity.this, registerActivity.class));

            }
                                  }
        );

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(StartActivity.this, loginActivity.class));

            }
        });
    }
}