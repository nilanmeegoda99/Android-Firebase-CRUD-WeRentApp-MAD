package com.example.werentapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.werentapplication.dbHelper.NewOrder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class S_od extends AppCompatActivity {
   Button viewBtn, updateBtn,deleteBtn;
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
        setContentView(R.layout.activity_s_od);


        viewBtn = findViewById(R.id.viewBtn);
        et01 = findViewById(R.id.et01);
        et02 = findViewById(R.id.et02);
        et03 = findViewById(R.id.et03);
        et04 = findViewById(R.id.et04);
        et05 = findViewById(R.id.et05);

        updateBtn = findViewById(R.id.updateBtn);
        deleteBtn = findViewById(R.id.deleteBtn);

        final NewOrder newOrder = new NewOrder();
        //view method
        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbView = FirebaseDatabase.getInstance().getReference().child("orderNew").child("data1");

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



        });

        //update
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference updRef = FirebaseDatabase.getInstance().getReference().child("orderNew");
                updRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChild("data1")){
                            try{

                                    newOrder.setOrderNumber(Integer.parseInt(et01.getText().toString().trim()));
                                    newOrder.setProduct(et02.getText().toString().trim());
                                    newOrder.setCost(Integer.parseInt(et03.getText().toString().trim()));
                                    newOrder.setDays(Integer.parseInt(et04.getText().toString().trim()));
                                    newOrder.setTotCost(Integer.parseInt(et05.getText().toString().trim()));

                                    DatabaseReference dbRead = FirebaseDatabase.getInstance().getReference().child("orderNew").child("data1");
                                    dbRead.setValue(newOrder);
                                    clearControls();
                                    Toast.makeText(getApplicationContext(), "Data updated successfully", Toast.LENGTH_SHORT).show();




                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Invalid", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(getApplicationContext(), "Np source to update", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

     //delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("orderNew");
             delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                 @Override
                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                     if(snapshot.hasChild("data1")){
                         DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("orderNew").child("data1");
                         dbRef.removeValue();
                         clearControls();
                         Toast.makeText(getApplicationContext(), "Data deleted Successfully", Toast.LENGTH_SHORT).show();

                     }else{
                         Toast.makeText(getApplicationContext(), "No source to delete", Toast.LENGTH_SHORT).show();
                     }
                 }

                 @Override
                 public void onCancelled(@NonNull DatabaseError error) {

                 }
             });

         }
     });
    }
}