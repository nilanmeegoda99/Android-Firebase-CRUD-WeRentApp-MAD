package com.example.werentapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.werentapplication.dbHelper.NewOrder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class sample_order_new extends AppCompatActivity {

    Button buttonAdd, calTot;
    EditText editText1, editText2, editText3, editText4, editText5;
    DatabaseReference dbRep;
    NewOrder newOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_order_new);

        editText1 = findViewById(R.id.et01);
        editText2 = findViewById(R.id.et02);
        editText3 = findViewById(R.id.et03);
        editText4 = findViewById(R.id.et04);
        editText5 = findViewById(R.id.et05);

        buttonAdd = findViewById(R.id.addButtonnew);

        newOrder = new NewOrder();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbRep = FirebaseDatabase.getInstance().getReference().child("orderNew");
                try {
                    if (TextUtils.isEmpty(editText1.getText().toString())){
                        System.out.println("Empty 01");
                    }
                    else if (TextUtils.isEmpty(editText2.getText().toString())){
                        System.out.println("Empty 02");
                    }else if (TextUtils.isEmpty(editText3.getText().toString())){
                        System.out.println("Empty 03");
                    }else if (TextUtils.isEmpty(editText4.getText().toString())){
                        System.out.println("Empty 04");
                    }
                    else if(TextUtils.isEmpty((editText5.getText().toString()))){
                        System.out.println("Empty 05");
                    }
                    else {
                        newOrder.setOrderNumber(Integer.parseInt(editText1.getText().toString().trim()));
                        newOrder.setProduct(editText2.getText().toString().trim());
                        newOrder.setCost(Integer.parseInt(editText3.getText().toString().trim()));
                        newOrder.setDays(Integer.parseInt(editText4.getText().toString().trim()));
                        newOrder.setTotCost(Integer.parseInt(editText5.getText().toString().trim()));

                        dbRep.child("data1").setValue(newOrder);
                        Toast.makeText(getApplicationContext(), "Data added", Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e){
                    System.out.println("Error .............. "+e);
                }
            }
        });



    }
    public void calTot(View view){
        calTot = findViewById(R.id.calTot);
        editText3 = findViewById(R.id.et03);
        editText4 = findViewById(R.id.et04);
        editText5 = findViewById(R.id.et05);

        calTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number1 = Integer.parseInt(editText3.getText().toString());
                int number2 = Integer.parseInt(editText4.getText().toString());
                int finalCal = number1 * number2;
                editText5.setText(String.valueOf(finalCal));
            }
        });
    }
}