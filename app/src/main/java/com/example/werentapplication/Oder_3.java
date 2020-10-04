package com.example.werentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Oder_3 extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viw_oder);


        final Button order1 = findViewById(R.id.order1);
        order1.setOnClickListener(new View.OnClickListener() {

    @Override
    public void onClick(View v) {


    }
        });

    }
}