package com.example.werentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class main_page extends AppCompatActivity {
    Button button01;
    Button button02;
    Button button03;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        button01 = findViewById(R.id.oder_viw);
        button01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Viw_oder.class);
                startActivity(intent);
            }
        });

        button02 = findViewById(R.id.edit_and_delete_button);
        button02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), S_od.class);
                startActivity(intent);
            }
        });

        button03 = findViewById(R.id.add_sample_order);
        button03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), sample_order_new.class);
                startActivity(i);
            }
        });
    }
}