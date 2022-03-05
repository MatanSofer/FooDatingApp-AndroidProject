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
import android.widget.ProgressBar;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;

public class MainScreenFragment extends Fragment {

    Button LoginBtn, RegisterBtn;
    ProgressBar progressBar;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_main_screen, container, false);
        RegisterBtn = view.findViewById(R.id.register_btn);
        LoginBtn = view.findViewById(R.id.login_btn);
        progressBar = view.findViewById(R.id.mainscreen_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                LoginBtn.setEnabled(false);
                Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_loginFragment);

            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                RegisterBtn.setEnabled(false);
                Navigation.findNavController(view).navigate(R.id.action_mainScreenFragment_to_registerFragment);

            }
        });

        return view;

    }
}