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

import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.User;


public class MatchesFragment extends Fragment {
    User user;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        user= ModelFireBase.getCurrentUserObj();
        view = inflater.inflate(R.layout.fragment_matches, container, false);
        setHasOptionsMenu(true);

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
                Navigation.findNavController(view).navigate(R.id.action_matchesFragment_to_mainAppFragment);
                break;
            case R.id.profile_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_matchesFragment_to_profileFragment);

                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }
}