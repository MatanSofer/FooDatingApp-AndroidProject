package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RegisterFragment extends Fragment {
    Button registerBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          View view =inflater.inflate(R.layout.fragment_register, container, false);
          registerBtn = view.findViewById(R.id.registerscreen_btn);
          registerBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_mainAppFragment));
          // Missing DB update/saving data + Checking if user filled attributes properly/Checking if user already had registered
        //Possibility to add a Toast message
          return view;

    }
}