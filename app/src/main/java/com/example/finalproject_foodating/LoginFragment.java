package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class LoginFragment extends Fragment {

    Button loginBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View view =inflater.inflate(R.layout.fragment_login, container, false);
         loginBtn = view.findViewById(R.id.loginscreen_btn);
         loginBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainAppFragment));
         //Missing check if username + pass are correct
         return view;
    }
}