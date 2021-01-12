package com.example.technews.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.technews.MainActivity;
import com.example.technews.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Signup extends AppCompatActivity {
    EditText name,email,pass;
    Button signup;
    FirebaseAuth auth;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=findViewById(R.id.nameperson);
        email=findViewById(R.id.emailperson);
        pass=findViewById(R.id.passwordperson);
        signup=findViewById(R.id.singupbutton);

        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAuth();
            }
        });
    }

    private void createAuth() {
        String names=name.getText().toString().trim();
        String emails=email.getText().toString().trim();
        String passes=pass.getText().toString().trim();

        auth.createUserWithEmailAndPassword(emails,passes).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {

                    HashMap<String,String>usersmap=new HashMap<>();
                    usersmap.put("names",names);
                    usersmap.put("emails",emails);
                    usersmap.put("passwords",passes);

                    db.collection("Userinfo").add(usersmap);
                    Toast.makeText(Signup.this, "Welcome to Technews", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(Signup.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(Signup.this, "Check again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}