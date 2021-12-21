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


public class MainAppFragment extends Fragment {
    View view;
    String UserEmail;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_main_app, container, false);
         setHasOptionsMenu(true);

        UserEmail = MainAppFragmentArgs.fromBundle(getArguments()).getUserEmail();

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
            case R.id.profile_menu_btn:
                Navigation.findNavController(view).navigate(MainAppFragmentDirections.actionMainAppFragmentToProfileFragment(UserEmail));
                break;
            case R.id.messages_menu_btn:
                Navigation.findNavController(view).navigate(MainAppFragmentDirections.actionMainAppFragmentToMatchesFragment(UserEmail));
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }









}