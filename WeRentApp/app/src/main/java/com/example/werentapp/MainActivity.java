package com.example.werentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {
    TextView fname,lname,address,con_no ;
    private Button btnLogOut,backbtn;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogOut =findViewById(R.id.btnLogout);
        backbtn =findViewById(R.id.back);

        fname = findViewById(R.id.tvfname);
        lname = findViewById(R.id.tvlname);
        address = findViewById(R.id.tvuadd);
        con_no = findViewById(R.id.tvconNo);

        fauth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userid = fauth.getCurrentUser().getUid();
        DocumentReference dref =fstore.collection("Users").document(userid);
        dref.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                fname.setText(documentSnapshot.getString("F_Name"));
                lname.setText(documentSnapshot.getString("L_Name"));
                address.setText(documentSnapshot.getString("uaddress"));
                con_no.setText(documentSnapshot.getString("Con_no"));

            }
        });




        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), StartActivity.class));
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                try {
                    FirebaseAuth.getInstance().signOut();
                    Toast.makeText(MainActivity.this, "Logged out !", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, StartActivity.class));
                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Some error occured!", Toast.LENGTH_SHORT).show();
                }
                }

        });
    }
}