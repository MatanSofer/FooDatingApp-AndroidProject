package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainScreenFragment extends Fragment {

    Button LoginBtn,RegisterBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_main_screen, container, false);
        RegisterBtn = view.findViewById(R.id.register_btn);
        LoginBtn = view.findViewById(R.id.login_btn);
        LoginBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainScreenFragment_to_loginFragment));
        RegisterBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainScreenFragment_to_registerFragment));
        return view;
    }
}