package com.leaf.leafgo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class user_profile extends AppCompatActivity{
    public static String step01;
    public static String step02;
    public static String step03;
    public static String step04;
    public static String step05;
    public static String step06;
    public static String step07;
    public static String step08;
    private ProgressBar progressbaruser;
    private FirebaseAuth firebaseAuth;


    private static final String TAG="user_profile";
    private FirebaseFirestore firestore;
    private FirebaseStorage firebasestorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);

        firebaseAuth = FirebaseAuth.getInstance();

        firestore=FirebaseFirestore.getInstance();
        firebasestorage=FirebaseStorage.getInstance();


        TextView bannerView=findViewById(R.id.shopBannerView);


        findViewById(R.id.viewShopsBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shopmap = new Intent(user_profile.this, shopmap.class);
                startActivity(shopmap);
            }
        });

        findViewById(R.id.logoutUserBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent backtologin = new Intent(user_profile.this, login.class);
                startActivity(backtologin);
                finish();
            }
        });

        findViewById(R.id.img1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                carrot();
            }
        });



        findViewById(R.id.img2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Banana();
            }
        });


        findViewById(R.id.img3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Apple();
            }
        });



        findViewById(R.id.img4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orange();
            }
        });



        findViewById(R.id.img5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tomato();
            }
        });



        findViewById(R.id.img6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Grapes();
            }
        });


    }




    public void carrot(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the carrot seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your carrots and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }


    public void Apple(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the Apple seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your Apple and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }


    public void orange(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the orange seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your Orange and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }


    public void Banana(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the Banana seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your banana and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }


    public void Grapes(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the Grapes seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your Grapes and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }


    public void tomato(){
        step01="Choose a variety with a root size and shape appropriate for your soil.";
        step02="Select the tomato seed type which you prefer to grow in your home garden.";
        step03="Prepare your home garden even with a small section which contains partial or full soil.";
        step04="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step05="If you have the relevant equipments, check the pH value of the Soil (Optional).";
        step06="For preparing that selected section of the Garden, First Loosen the soil in that area";
        step07="Then, fertilize the soil with manure, compost, or any other organic fertilizer";
        step08="Start planting your tomato and your good to go.";

        Intent plantation_steps=new Intent(user_profile.this,plantation_steps.class);
        startActivity(plantation_steps);

    }
}