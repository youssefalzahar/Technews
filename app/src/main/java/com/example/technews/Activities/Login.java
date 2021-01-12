package com.example.technews.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {
    EditText email;
    EditText password;
    FirebaseAuth auth;
    FirebaseFirestore db;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.editTextTextEmailAddress2);
        password=findViewById(R.id.editTextTextPassword2);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();
        login=findViewById(R.id.button4);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createlogin();
            }
        });
    }

    private void createlogin() {
        String emails=email.getText().toString().trim();
        String passws=password.getText().toString().trim();
        auth.signInWithEmailAndPassword(emails,passws).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                //Toast.makeText(Login.this, "Welcome to Tech news", Toast.LENGTH_SHORT).show();
                checkusers(authResult.getUser().getUid());

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Login.this, "check again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkusers(String uid) {
        FirebaseFirestore dbs=FirebaseFirestore.getInstance();
        FirebaseUser user=auth.getCurrentUser();
        uid=user.getUid();

        //Toast.makeText(this, ""+uid, Toast.LENGTH_LONG).show();
        dbs.collection("Userinfo").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful())
                {


                DocumentSnapshot documentSnapshot=task.getResult();
                String emails=documentSnapshot.get("emails").toString();
                String passwords=documentSnapshot.get("passwords").toString();
                String isAdmin=documentSnapshot.get("isAdmin").toString();
                Toast.makeText(Login.this, ""+isAdmin, Toast.LENGTH_SHORT).show();


            }
                else
            {
                Toast.makeText(Login.this, "faild", Toast.LENGTH_SHORT).show();
            }
            }
        });


    }
}