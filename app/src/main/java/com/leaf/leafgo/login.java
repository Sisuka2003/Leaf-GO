package com.leaf.leafgo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.GetSignInIntentRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class login extends AppCompatActivity {

    private static final String TAG = "login";
    public static String emailPublic;
    private FirebaseAuth firebaseauth;
    private ProgressBar progressBar;
    private SignInClient signinclient;
    CallbackManager callbackManager;
    private LoginButton fblogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseauth=FirebaseAuth.getInstance();

        //initialize the sign-in client
        signinclient = Identity.getSignInClient(getApplicationContext());


        EditText email = findViewById(R.id.emailLoginTextField);
        EditText pswd = findViewById(R.id.passwordLoginPasswordField);



        findViewById(R.id.googleSignInLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();
            }
        });



        findViewById(R.id.loginbackBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backtoBegin = new Intent(login.this, MainActivity.class);
                startActivity(backtoBegin);
                finish();
            }
        });

        findViewById(R.id.noAccountText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.loginButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                String emailText = email.getText().toString();
                String pswdText = pswd.getText().toString();
                emailPublic = emailText;


                if (emailText.isEmpty()) {
                    Toast.makeText(login.this, "Please Enter Your Email Address", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (pswdText.isEmpty()) {
                    Toast.makeText(login.this, "Please Enter Your Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {

                    firebaseauth.signInWithEmailAndPassword(emailText, pswdText)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        Log.d(TAG, "signInWithEmail:success");
                                        FirebaseUser user = firebaseauth.getCurrentUser();
                                        SignIn(user);
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(login.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        SignIn(null);
                                    }
                                }
                            });
                }
            }
        });



        callbackManager = CallbackManager.Factory.create();
        fblogin = findViewById(R.id.login_button);
        fblogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                String imageURL = "https://graph.facebook.com/" + loginResult.getAccessToken().getUserId() + "/picture?return_ssl_resources=1";
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(@NonNull FacebookException e) {
                Toast.makeText(login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseauth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = firebaseauth.getCurrentUser();
                    startActivity(new Intent(login.this,user_profile.class));
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(login.this, "Authentication failed.",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }



    @Override
    protected void onStart() {   //already signed in user kenek nam aye login page ekakata enna denne naha.
        super.onStart();
        FirebaseUser user = firebaseauth.getCurrentUser();
        updateUI(user);
    }


    private void SignIn(FirebaseUser user) {
        if(user != null){
            progressBar.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(login.this, user_profile.class);
            startActivity(intent);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
            Log.d(TAG, "signInWithEmail:success");
            Toast.makeText(login.this,"Sign-In Failed",Toast.LENGTH_SHORT).show();
        }
    }

    private final ActivityResultLauncher<IntentSenderRequest> signinlauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    handleSignInresult(result.getData());
//                    Toast.makeText(MainActivity.this, result.getResultCode(),Toast.LENGTH_SHORT).show();
                }
            });


    private void handleSignInresult(Intent intent) {
        try {
            SignInCredential credentials = signinclient.getSignInCredentialFromIntent(intent);
            String idToken = credentials.getGoogleIdToken();
            firebaseAuthWithGoogle(idToken);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void SignIn(){
        GetSignInIntentRequest signIntentRequest = GetSignInIntentRequest.builder()
                .setServerClientId(getString(R.string.web_client_id)).build();

        Task<PendingIntent> signInIntent = signinclient.getSignInIntent(signIntentRequest);

        signInIntent.addOnSuccessListener(new OnSuccessListener<PendingIntent>() { //success wunoth pending intent ekak labenawa


            @Override
            public void onSuccess(PendingIntent pendingIntent) {

                launchSignIn(pendingIntent);
            }


        }).addOnFailureListener(new OnFailureListener() {  //fail wunoth exception ekak throw wenawa.


            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(login.this,"Sign-in Failed",Toast.LENGTH_LONG).show();
            }
        });

    }


    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(idToken, null);
        Task<AuthResult> authResultTask = firebaseauth.signInWithCredential(authCredential);
        authResultTask.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    FirebaseUser user = firebaseauth.getCurrentUser();
                    Toast.makeText(login.this, "Welcome, "+user.getEmail(),Toast.LENGTH_SHORT).show();
                    emailPublic=user.getEmail();
                    updateUI(user);

                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void updateUI(FirebaseUser user) {

        if(user != null){
            Intent intent = new Intent(login.this, user_profile.class);
            startActivity(intent);
        }
    }


    private void launchSignIn(PendingIntent pendingIntent) {
        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(pendingIntent).build();
        signinlauncher.launch(intentSenderRequest);
    }
}