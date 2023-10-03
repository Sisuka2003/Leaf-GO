package com.leaf.leafgo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class register extends AppCompatActivity {

    private static final String TAG = "registerActivity";
    private FirebaseAuth firebaseAuth;
    public static String emailPublic;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();


        EditText emailtextField = findViewById(R.id.registerEmailTextField);
        EditText passwordTextField = findViewById(R.id.registerPasswordTextField);

        progressbar=findViewById(R.id.progressBar4);

        //sign up button
        findViewById(R.id.btnRegisterNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressbar.setVisibility(View.VISIBLE);
                String emailText = emailtextField.getText().toString();
                String pswdText = passwordTextField.getText().toString();
                emailPublic= emailtextField.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(emailText, pswdText)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    SignUp(user);
                                } else {
                                    Log.d(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                    SignUp(null);
                                }
                            }
                        });
            }


            private void SignUp(FirebaseUser user) {
                if(user != null){
                    progressbar.setVisibility(View.INVISIBLE);
                    Intent intent = new Intent(register.this, user_profile.class);
                    startActivity(intent);
                }else{
                    progressbar.setVisibility(View.INVISIBLE);
                    Toast.makeText(register.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
            }
        });

        findViewById(R.id.registerBackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(register.this, login.class);
                startActivity(login);
            }
        });



        findViewById(R.id.haveAccountText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent login=new Intent(register.this, login.class);
                startActivity(login);
            }
        });
    }
}