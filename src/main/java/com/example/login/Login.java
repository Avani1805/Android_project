package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    Button createAccountBtn,loginBtn,forget_password_btn;
    EditText username,password;
    FirebaseAuth firebaseAuth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        reset_alert = new AlertDialog.Builder(this);
        cd = new ConnectionDetector(this);
        inflater = this.getLayoutInflater();
        createAccountBtn = findViewById(R.id.createAccountBtn);
        createAccountBtn.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick (View view){
                        if(cd.isConnected()) {

                            startActivity(new Intent(getApplicationContext(), Register.class));
                        }
                    }
                });

        username = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);
        loginBtn = findViewById(R.id.loginBtn);
        forget_password_btn = findViewById(R.id.forget_password_btn);
        forget_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start alert dialog
                if (cd.isConnected()) {
                    View v = inflater.inflate(R.layout.reset_poup, null);
                    reset_alert.setTitle("Reset Forgot Password?")
                            .setMessage("Enter your Email to get password reset link")
                            .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    //validate email address
                                    EditText email = v.findViewById(R.id.reset_email_popup);
                                    if (email.getText().toString().isEmpty()) {
                                        email.setError("Required Field");
                                        return;
                                    }
                                    //send the reset link
                                    firebaseAuth.sendPasswordResetEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            if (cd.isConnected()) {
                                                Toast.makeText(Login.this, "Reset_Email_Sent", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                }
                            }).setNegativeButton("Cancel", null)
                            .setView(v)
                            .create().show();
                }
            }});

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //extract / validate
                if (cd.isConnected()) {
                    if (username.getText().toString().isEmpty()) {
                        username.setError("Error is missing");
                        return;
                    }
                    if (password.getText().toString().isEmpty()) {
                        password.setError("Password is missing");
                        return;
                    }
                    //Login is valid
                    firebaseAuth.signInWithEmailAndPassword(username.getText().toString(), password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //Login successfull
                            if (cd.isConnected()) {
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }});
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }
    }

}
