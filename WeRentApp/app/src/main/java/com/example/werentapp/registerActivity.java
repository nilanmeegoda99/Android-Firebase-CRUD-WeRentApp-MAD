package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    private EditText txtEmail,txtPass,f_name,l_name,con_no,address;
    private Button btnReg;
    boolean valid = true;
    private CheckBox isBuyer,isRenter;
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

        isBuyer = findViewById(R.id.chkBB);
        isRenter = findViewById(R.id.chkBR);

        btnReg = findViewById(R.id.btnregis);

        auth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        //checkbox logic for only one box to be checked

        isBuyer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    isRenter.setChecked(false);
                }
            }
        });

        isRenter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    isBuyer.setChecked(false);
                }
            }
        });










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

                                        checkField(f_name);
                                        checkField(l_name);
                                        checkField(con_no);
                                        checkField(address);

                                        //checkBox validation
                                        if(!(isBuyer.isChecked() || isRenter.isChecked()))
                                        {
                                            Toast.makeText(getApplicationContext(),"Choose the account type",Toast.LENGTH_SHORT).show();
                                            return;
                                        }


                                        if(TextUtils.isEmpty(textEmail) || TextUtils.isEmpty(textPass)) {

                                            Toast.makeText(registerActivity.this, "Empty Credentials", Toast.LENGTH_SHORT).show();
                                        }else if (textPass.length() < 6){
                                            Toast.makeText(registerActivity.this, "Password too short", Toast.LENGTH_SHORT).show();

                                        }else {

                                            if (valid) {
                                                auth.createUserWithEmailAndPassword(textEmail, textPass).addOnCompleteListener(registerActivity.this, new OnCompleteListener<AuthResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(registerActivity.this, "User was registered successfully", Toast.LENGTH_SHORT).show();
                                                            UserID = auth.getCurrentUser().getUid();
                                                            DocumentReference documentReference = fstore.collection("Users").document(UserID);
                                                            Map<String, Object> user = new HashMap<>();

                                                            user.put("F_Name", fname);
                                                            user.put("L_Name", lname);
                                                            user.put("Con_no", contact_no);
                                                            user.put("uaddress", uaddress);

                                                            //specify user type

                                                            if(isBuyer.isChecked())
                                                            {
                                                                user.put("isUser","1");
                                                            }

                                                            if(isRenter.isChecked())
                                                            {
                                                                user.put("isAdmin", "1");
                                                            }


                                                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                    Toast.makeText(registerActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                                                                }
                                                            });
                                                            if(isBuyer.isChecked())
                                                            {
                                                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                                                finish();
                                                            }
                                                            if(isRenter.isChecked())
                                                            {
                                                                startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                                                                finish();
                                                            }



                                                        } else {
                                                            cleartextfields();
                                                            Toast.makeText(registerActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                            }
                                        }
                                    }



    });
}

    public boolean checkField(EditText textField) {
        if(textField.getText().toString().isEmpty())
        {
            textField.setError("Error");
            valid = false;
        }else{
            valid = true;
        }
        return  valid;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null)
        {
            DocumentReference dref = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            dref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.getString("isAdmin") != null)
                    {
                        //if user is a renter
                        startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                        finish();
                    }

                    if(documentSnapshot.getString("isUser") != null)
                    {
                        //if user is a normal buyer
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),registerActivity.class));
                    finish();
                }
            });
        }
    }

}