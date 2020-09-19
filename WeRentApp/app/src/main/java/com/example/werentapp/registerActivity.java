package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    private EditText txtEmail,txtPass,f_name,l_name,con_no,address;
    private Button btnReg;
    String UserID;

    private FirebaseAuth auth;
    private FirebaseFirestore fstore;

    private void cleartextfields()
    {
        txtEmail.setText("");
        txtPass.setText("");
        f_name.setText("");
        l_name.setText("");
        con_no.setText("");
        address.setText("");

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        txtEmail = findViewById(R.id.etEmailL);
        txtPass = findViewById(R.id.etPassL);
        f_name = findViewById(R.id.etfname);
        l_name = findViewById(R.id.etlname);
        con_no = findViewById(R.id.etconno);
        address = findViewById(R.id.etaddress);

        btnReg = findViewById(R.id.btnregis);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        if(auth.getCurrentUser() != null)
        {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        btnReg.setOnClickListener(new View.OnClickListener()
                                  {
                                    @Override
                                      public void onClick(View v)
                                    {
                                        String textEmail = txtEmail.getText().toString().trim();
                                        String textPass = txtPass.getText().toString().trim();
                                        final String fname = f_name.getText().toString().trim();
                                        final String lname = l_name.getText().toString().trim();
                                        final String contact_no = con_no.getText().toString().trim();
                                        final String uaddress = address.getText().toString().trim();


                                        if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPass)) {

                                            Toast.makeText(registerActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                                        }else if (textPass.length() < 6){
                                            Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();

                                        }else
                                        {


                                            auth.createUserWithEmailAndPassword(textEmail,textPass).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                                @Override
                                                public void onComplete(@NonNull Task<AuthResult> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        Toast.makeText(registerActivity.this, "User was registered successfully", Toast.LENGTH_SHORT).show();
                                                        UserID = auth.getCurrentUser().getUid();
                                                        DocumentReference documentReference = fstore.collection("Users").document(UserID);
                                                        Map<String, Object> user = new HashMap<>();

                                                        user.put("F_Name", fname);
                                                        user.put("L_Name", lname);
                                                        user.put("Con_no", contact_no);
                                                        user.put("uaddress", uaddress);
                                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void aVoid) {
                                                                Toast.makeText(registerActivity.this, "Succesful", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });
                                                        startActivity(new Intent(registerActivity.this,MainActivity.class));
                                                        finish();
                                                    }else{
                                                        cleartextfields();
                                                        Toast.makeText(registerActivity.this,"Registration failed", Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }
                                    }



    });
}


}