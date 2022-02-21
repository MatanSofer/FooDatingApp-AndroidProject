package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.finalproject_foodating.model.ModelFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SettingsFragment extends Fragment {
    Button LogoutBtn ;
    private FirebaseAuth mAuth;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =inflater.inflate(R.layout.fragment_settings, container, false);

        LogoutBtn = view.findViewById(R.id.settingsFrag_LogoutBtn);
        mAuth=FirebaseAuth.getInstance();

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        return view;

    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null) {
            Navigation.findNavController(view).navigate(R.id.action_settingsFragment_to_mainScreenFragment);
        }


    }

}