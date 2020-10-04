package com.example.werentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Order1 extends AppCompatActivity {

    EditText et01,et02,et03,et04,et05;
    private void clearControls(){
        et01.setText("");
        et02.setText("");
        et03.setText("");
        et04.setText("");
        et05.setText("");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order1);

        et01 = findViewById(R.id.et01);
        et02 = findViewById(R.id.et02);
        et03 = findViewById(R.id.et03);
        et04 = findViewById(R.id.et04);
        et05 = findViewById(R.id.et05);


        DatabaseReference dbView = FirebaseDatabase.getInstance().getReference().child("orderNew").child("order1");

        dbView.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    et01.setText(snapshot.child("orderNumber").getValue().toString());
                    et02.setText(snapshot.child("product").getValue().toString());
                    et03.setText(snapshot.child("cost").getValue().toString());
                    et04.setText(snapshot.child("days").getValue().toString());
                    et05.setText(snapshot.child("totCost").getValue().toString());

                }else{
                    Toast.makeText(getApplicationContext(), "No source to Display", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}