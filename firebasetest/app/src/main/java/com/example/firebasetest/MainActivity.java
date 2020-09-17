package com.example.firebasetest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText txtID, txtName, txtAdd, txtConNo;
    Button btnSave, btnShow, btnUpdate,btnDelete;
    DatabaseReference dbref;
    Student std;

    private void clearControls()
    {
        txtID.setText("");
        txtName.setText("");
        txtAdd.setText("");
        txtConNo.setText("");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtID = findViewById(R.id.etID);
        txtName = findViewById(R.id.etName);
        txtAdd = findViewById(R.id.etadd);
        txtConNo = findViewById(R.id.etcno);

        btnSave = findViewById(R.id.btsave);
        btnShow = findViewById(R.id.btshow);
        btnDelete = findViewById(R.id.btdelete);
        btnUpdate = findViewById(R.id.btupdate);

        std= new Student();

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dbref = FirebaseDatabase.getInstance().getReference().child("Student");

                try {

                    if(TextUtils.isEmpty(txtID.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an ID", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Name", Toast.LENGTH_SHORT).show();
                    else if(TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter an Name", Toast.LENGTH_SHORT).show();
                    else
                    {
                        std.setID(txtID.getText().toString().trim());
                        std.setName(txtName.getText().toString().trim());
                        std.setAddress(txtAdd.getText().toString().trim());
                        std.setConNo(Integer.parseInt(txtConNo.getText().toString().trim()));

                        dbref.child("std3").setValue(std);

                        Toast.makeText(getApplicationContext(), "Data Entered succesfulyl", Toast.LENGTH_SHORT).show();
                        clearControls();



                    }



                }
                catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Invalid Contact Number", Toast.LENGTH_SHORT).show();
                }
                }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DatabaseReference readref = FirebaseDatabase.getInstance().getReference().child("Student").child("std3");
                readref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChildren())
                        {
                            txtID.setText(dataSnapshot.child("id").getValue().toString());
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtAdd.setText(dataSnapshot.child("address").getValue().toString());
                            txtConNo.setText(dataSnapshot.child("conNo").getValue().toString());


                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source Found !", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });




    }
}