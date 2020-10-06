package com.example.callingapp;

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

import com.example.callingapp.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText ed_name,ed_email,ed_password,ed_phone;
    FirebaseAuth auth;
    DatabaseReference reference;
    Button mRegisterBtn;
    User user;
    String userID;
    FirebaseFirestore fStore;
    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_name = (EditText) findViewById(R.id.fullName);
        ed_email = (EditText) findViewById(R.id.Email);
        ed_password = (EditText) findViewById(R.id.password);
        ed_phone=(EditText)findViewById(R.id.phone);
        user=new User();
        mRegisterBtn= findViewById(R.id.registerBtn);
        fStore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Members");
        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email=ed_email.getText().toString().trim();
                String password= ed_password.getText().toString().trim();
                final String fullName = ed_name.getText().toString();
                final String phone    = ed_phone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    ed_email.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    ed_password.setError("Password is Required.");
                    return;
                }

                if(password.length() < 6){
                    ed_password.setError("Password Must be >= 6 Characters");
                    return;
                }
                //progressBar.setVisibility(View.VISIBLE);
                FirebaseUser fuser= auth.getCurrentUser();

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //FirebaseUser fuser= auth.getCurrentUser();
                            User nuser=new User(fullName,email,password,phone,fuser.getUid());
                            //reference.child(fuser.getUid())
                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG,"OnFailure: Email not sent"+ e.getMessage());
                                }
                            });
                            Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            userID = auth.getCurrentUser().getUid();


                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user=new HashMap<>();
                            user.put("fName",fullName);
                            user.put("email",email);
                            user.put("phone",phone);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: " + e.toString());


                                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });}
//        mLoginBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(),Login.class));
//            }
//        });


    public void register(View v){
        final String name=ed_name.getText().toString().trim();
        final String email=ed_email.getText().toString().trim();
        final String password=ed_password.getText().toString().trim();
        final String phone=ed_phone.getText().toString().trim();
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    User user=new User(name,email,password,phone,firebaseUser.getUid());
                    reference.child(firebaseUser.getUid()).push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ed_name=(EditText) findViewById(R.id.fullName);
        ed_email=(EditText) findViewById(R.id.Email);
        ed_password=(EditText) findViewById(R.id.password);
        ed_phone=(EditText)findViewById(R.id.phone);
        auth=FirebaseAuth.getInstance();
        mRegisterBtn=findViewById(R.id.registerBtn);
        reference= FirebaseDatabase.getInstance().getReference().child("Members");

        if (auth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

    }
    public void register(View v){


        final String name=ed_name.getText().toString().trim();
        final String email=ed_email.getText().toString().trim();
        final String password=ed_password.getText().toString().trim();
        final String phone=ed_phone.getText().toString().trim();
        //reference= FirebaseDatabase.getInstance().getReference().child("Users");

        if(TextUtils.isEmpty(email)){
            ed_email.setError("Email is Required.");
            return;
        }
        if(TextUtils.isEmpty(password)){
            ed_password.setError("Password is Required.");
            return;
        }

        if(password.length() < 6){
            ed_password.setError("Password Must be >= 6 Characters");
            return;
        }

        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser firebaseUser=auth.getCurrentUser();
                    User user=new User(name,email,password,phone,firebaseUser.getUid());
                    reference= FirebaseDatabase.getInstance().getReference().child("Members");
                    reference.child(firebaseUser.getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(RegisterActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    /*reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild("Members")){
                            reference.child(firebaseUser.getUid()).push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User created successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(RegisterActivity.this, "User could not be created", Toast.LENGTH_SHORT).show();
                                        Throwable e = null;
                                        Log.d(TAG,"Could not update database"+ e.getMessage());
                                    }
                                }
                            });
                        }else {
                                Toast.makeText(RegisterActivity.this, "child Users do not exist", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });*/
                    firebaseUser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(RegisterActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"OnFailure: Email not sent"+ e.getMessage());
                        }
                    });
                    Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }}
