package com.example.da1_group6.ui_onboard;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.da1_group6.activities.Activity_Login;
import com.example.da1_group6.activities.MySharedPreferences;
import com.example.da1_group6.R;

public class Fragment3 extends Fragment {
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_3, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn = view.findViewById(R.id.btn_getstart);

        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.anim_alpha);
        btn.startAnimation(animation);

        final MySharedPreferences preference = new MySharedPreferences(getContext());

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Activity_Login.class));
                getActivity().overridePendingTransition(R.anim.animation1, R.anim.animation2);

                preference.putValues("first", true);
            }
        });
    }
}