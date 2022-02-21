package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;

public class MainScreenFragment extends Fragment {

    Button LoginBtn,RegisterBtn;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_main_screen, container, false);
        RegisterBtn = view.findViewById(R.id.register_btn);
        LoginBtn = view.findViewById(R.id.login_btn);


        LoginBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainScreenFragment_to_loginFragment));
        RegisterBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_mainScreenFragment_to_registerFragment));
        return view;

    }
}