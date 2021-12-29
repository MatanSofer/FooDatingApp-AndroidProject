package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class SettingsFragment extends Fragment {
    Button LogoutBtn ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_settings, container, false);
        String UserEmail = SettingsFragmentArgs.fromBundle(getArguments()).getUserEmail();
        LogoutBtn = view.findViewById(R.id.settingsFrag_LogoutBtn);
        LogoutBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_settingsFragment_to_mainScreenFragment));
        return view;

    }

}