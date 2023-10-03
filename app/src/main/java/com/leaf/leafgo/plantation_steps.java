package com.leaf.leafgo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.leaf.leafgo.model.planting_steps;

import java.util.ArrayList;

public class plantation_steps extends AppCompatActivity {
    public String step01=user_profile.step01;
    public String step02=user_profile.step02;
    public String step03=user_profile.step03;
    public String step04=user_profile.step04;
    public String step05=user_profile.step05;
    public String step06=user_profile.step06;
    public String step07=user_profile.step07;
    public String step08=user_profile.step08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantation_steps);



        ArrayList<planting_steps> plantingArray=new ArrayList<>();
        plantingArray.add(new planting_steps(step01));
        plantingArray.add(new planting_steps(step02));
        plantingArray.add(new planting_steps(step03));
        plantingArray.add(new planting_steps(step04));
        plantingArray.add(new planting_steps(step05));
        plantingArray.add(new planting_steps(step06));
        plantingArray.add(new planting_steps(step07));
        plantingArray.add(new planting_steps(step08));



        RecyclerView recyclerView = findViewById(R.id.plantationRecyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        plantation_method_adapter myAdap=new plantation_method_adapter(plantingArray);
        recyclerView.setAdapter(myAdap);
    }
}