package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class BuyerMenu extends AppCompatActivity {
 ImageButton viewprofileBt,viewCartBt;
 TextView WelUname;

 private FirebaseAuth authU;
 private FirebaseFirestore fstore;
 private String umail;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buyer_menu);

        viewprofileBt = findViewById(R.id.btProfile);
        viewCartBt = findViewById(R.id.btCart);
        WelUname = findViewById(R.id.tVUname);

        authU = FirebaseAuth.getInstance();
        fstore =  FirebaseFirestore.getInstance();


        viewprofileBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), UserProfile.class));

            }
        });


        umail = authU.getCurrentUser().getEmail();
        DocumentReference dref =fstore.collection("Users").document(umail);
        dref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                WelUname.setText("Welcome " + documentSnapshot.getString("F_Name"));


            }
        });


    }
}