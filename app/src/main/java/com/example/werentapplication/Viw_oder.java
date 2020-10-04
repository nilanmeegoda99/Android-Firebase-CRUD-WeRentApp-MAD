package com.example.werentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Viw_oder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viw_oder);


        final Button order1 = findViewById(R.id.order1);
        order1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Viw_oder.this, Order1.class);
                startActivity(in);

            }
        });


        Button order2 = findViewById(R.id.order2);
        order2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Viw_oder.this, Order2.class);
                startActivity(in);
            }
        });


    final Button oder_3 = findViewById(R.id.order3);
        oder_3.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){
        Intent in = new Intent(Viw_oder.this, Oder_3.class);
        startActivity(in);

        }
        });
    }
}
