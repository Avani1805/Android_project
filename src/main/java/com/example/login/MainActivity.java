package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    TextView verifyMsg;
    Button verifyEmailBtn;
    FirebaseAuth auth;
    AlertDialog.Builder reset_alert;
    LayoutInflater inflater;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();
        Button logout = findViewById(R.id.logoutBtn);

        verifyMsg = findViewById(R.id.verifyEmailBtn);
        verifyEmailBtn = findViewById(R.id.verifyEmailBtn);

        reset_alert = new AlertDialog.Builder(this);
        inflater = this.getLayoutInflater();

        if(!auth.getCurrentUser().isEmailVerified()){
            verifyEmailBtn.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);
        }
        verifyEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //send verification email
                auth.getCurrentUser().sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Verification email sent",Toast.LENGTH_SHORT).show();
                        verifyEmailBtn.setVisibility(View.GONE);
                        verifyMsg.setVisibility(View.GONE);
                    }
                });
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.resetUserPassword){
            startActivity(new Intent(getApplicationContext(),ResetPassword.class));

        }
        if(item.getItemId() == R.id.updateEmailMenu){
            View v = inflater.inflate(R.layout.reset_poup, null);
            reset_alert.setTitle("Update Email")
                    .setMessage("Enter new email address")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            //validate email address
                            EditText email = v.findViewById(R.id.reset_email_popup);
                            if(email.getText().toString().isEmpty()){
                                email.setError("Required Field");
                                return;
                            }
                            //send the reset link
                            FirebaseUser user = auth.getCurrentUser();
                            user.updateEmail(email.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText( MainActivity.this, "Email Updated", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    }).setNegativeButton( "Cancel", null)
                    .setView(v)
                    .create().show();

        }

        if(item.getItemId() == R.id.delete_account_menu){
            reset_alert.setTitle("Delete Account Permanantly?")
                    .setMessage("Are you sure?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            FirebaseUser user = auth.getCurrentUser();
                            user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText( MainActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                    auth.signOut();
                                    startActivity(new Intent(getApplicationContext(),Login.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText( MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }).setNegativeButton( "Cancel", null)
                    .create().show();
        }

        return super.onOptionsItemSelected(item);
    }
}