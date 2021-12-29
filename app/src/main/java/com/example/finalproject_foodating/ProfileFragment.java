package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


public class ProfileFragment extends Fragment {
    ImageButton EditBtn,SettingsBtn;
    String UserEmail;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        UserEmail = ProfileFragmentArgs.fromBundle(getArguments()).getUserEmail();


        EditBtn = (ImageButton)view.findViewById(R.id.imageButton_editdatails);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragmentDirections.ActionProfileFragmentToEditDetailsFragment action = ProfileFragmentDirections.actionProfileFragmentToEditDetailsFragment(UserEmail);
                Navigation.findNavController(view).navigate(action);
            }
        });

        SettingsBtn = (ImageButton)view.findViewById(R.id.imageButton_settings);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragmentDirections.ActionProfileFragmentToSettingsFragment action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment(UserEmail);
                Navigation.findNavController(view).navigate(action);
            }
        });
        //Log.d("tag",UserEmail);
        return view;

    }
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.mainApp_menu_btn:
                Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMainAppFragment(UserEmail ));
                break;
            case R.id.messages_menu_btn:
                Navigation.findNavController(view).navigate(ProfileFragmentDirections.actionProfileFragmentToMatchesFragment(UserEmail ));
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}