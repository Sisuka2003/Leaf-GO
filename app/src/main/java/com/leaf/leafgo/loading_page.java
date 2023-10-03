package com.leaf.leafgo;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class loading_page extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loading_page, container, false);


        Animation animation= AnimationUtils.loadAnimation(getActivity(),R.anim.shake_animation);
        view.findViewById(R.id.logo).startAnimation(animation);
        view.findViewById(R.id.textView3).startAnimation(animation);
        view.findViewById(R.id.loadinglayout).startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.findViewById(R.id.textView).setVisibility(View.VISIBLE);
                view.findViewById(R.id.loading_getStartedBtn).setVisibility(View.VISIBLE);
                view.findViewById(R.id.loading_skipBtn).setVisibility(View.VISIBLE);
                view.findViewById(R.id.lineView).setVisibility(View.VISIBLE);

                Animation animation2= AnimationUtils.loadAnimation(getActivity(),R.anim.top_animation);
                view.findViewById(R.id.textView).startAnimation(animation2);
                view.findViewById(R.id.loading_getStartedBtn).startAnimation(animation2);
                view.findViewById(R.id.loading_skipBtn).startAnimation(animation2);
                view.findViewById(R.id.lineView).startAnimation(animation2);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        view.findViewById(R.id.loading_getStartedBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                guide_01 guide_01=new guide_01();

                fragmentTransaction.replace(R.id.fragmentContainerMainActivity,guide_01,"guide_01_01");
                fragmentTransaction.commit();
            }
        });
        view.findViewById(R.id.loading_skipBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent login = new Intent(getActivity(), login.class);
                startActivity(login);
            }
        });


    }
}