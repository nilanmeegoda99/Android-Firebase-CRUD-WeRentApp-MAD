package com.example.werentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private static final String KEY_TITLE = "title";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_AMOUNT = "Amount";
    private static final String KEY_BORROWED_DATE = "BorrowedDate";
    private static final String KEY_DESCRIPTION = "description";

    private EditText editTextTitle;
    private EditText edit_text_Address;
    private EditText edit_text_Amount;
    private EditText edit_text_Borrowed_Date;
    private EditText editTextDescription;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        edit_text_Address = findViewById(R.id.edit_text_Address);
        edit_text_Amount = findViewById(R.id.edit_text_Amount);
        edit_text_Borrowed_Date = findViewById(R.id.edit_text_Borrowed_Date);
        editTextDescription = findViewById(R.id.edit_text_description);

    }

    public void saveNote(View v)
    {
        String title = editTextTitle.getText().toString();
        String Address = edit_text_Address.getText().toString();
        String Amount = edit_text_Amount.getText().toString();
        String BorrowedDate = edit_text_Borrowed_Date.getText().toString();
        String description = editTextDescription.getText().toString();

        Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, title);
        note.put(KEY_ADDRESS, Address);
        note.put(KEY_AMOUNT, Amount);
        note.put(KEY_BORROWED_DATE, BorrowedDate);
        note.put(KEY_DESCRIPTION, description);


        db.collection("Delivery").document("Delivery 01").set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                        Toast.makeText(MainActivity.this, "Delivery Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, e.toString());
                        
                    }
                });
    }
}