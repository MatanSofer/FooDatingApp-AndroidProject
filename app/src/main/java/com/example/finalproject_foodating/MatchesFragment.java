package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class MatchesFragment extends Fragment {
    String UserEmail;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_matches, container, false);
        setHasOptionsMenu(true);
        UserEmail = MatchesFragmentArgs.fromBundle(getArguments()).getUserEmail();
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
                Navigation.findNavController(view).navigate(MatchesFragmentDirections.actionMatchesFragmentToMainAppFragment(UserEmail));
                break;
            case R.id.profile_menu_btn:
                Navigation.findNavController(view).navigate(MatchesFragmentDirections.actionMatchesFragmentToProfileFragment(UserEmail));
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}