package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class loginActivity extends AppCompatActivity {

    private EditText textMail,textPass;
    private Button btnLog;
    private TextView forgotpass;

    private FirebaseAuth auth;
    private FirebaseFirestore fstore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textMail = findViewById(R.id.etEmailL);
        textPass = findViewById(R.id.etPassL);
        forgotpass = findViewById(R.id.tvfrgtpass);

        btnLog = findViewById(R.id.btsignin);

        auth =FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();



        btnLog.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String Email = textMail.getText().toString().trim();
                String Pass = textPass.getText().toString().trim();
                loginUser(Email,Pass);

            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passresetDialog = new AlertDialog.Builder(view.getContext());
                passresetDialog.setTitle("Reset Password ?");
                passresetDialog.setMessage("Enter your email to receive the reset link");
                passresetDialog.setView(resetMail);

                passresetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //get the mail and send the reset link
                        String mail = resetMail.getText().toString().trim();
                        auth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(loginActivity.this,"Reset link has been sent to your mail ",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(loginActivity.this,"Error please try again " + e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passresetDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // close the dialog
                        dialogInterface.dismiss();

                    }
                });

                passresetDialog.create().show();
            }
        });

    }



    private void loginUser(String email, String pass) {
    auth.signInWithEmailAndPassword(email, pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
        @Override
        public void onSuccess(AuthResult authResult) {
            Toast.makeText(loginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
            checkUserAccessLevel(authResult.getUser().getUid());


        }


    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            Toast.makeText(loginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(loginActivity.this, StartActivity.class));
            finish();
        }
    });

    }

    private void checkUserAccessLevel(String uid) {
        DocumentReference df = fstore.collection("Users").document(uid);
        // extract the data from document of the user
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG", "onSuccess" + documentSnapshot.getData());
                //identify the user access level

                if(documentSnapshot.getString("isAdmin") != null)
                {
                    //user is admin
                    startActivity(new Intent(getApplicationContext(),AdminActivity.class));
                    finish();
                }

                if(documentSnapshot.getString("isUser") != null) {
                    //user is normal buyer
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }

            }
        });
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
                    startActivity(new Intent(getApplicationContext(),loginActivity.class));
                    finish();
                }
            });
        }
    }
}