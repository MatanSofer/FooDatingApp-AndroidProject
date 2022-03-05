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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class MatchesFragment extends Fragment {
    TextView NameMatchesDetails, EmailMatchesDetails, NameLastMatch, EmailLastMatch;
    List<String> MatchesId = new ArrayList<>();
    String NameDetails = "", EmailDetails = "";
    String name, email;
    ImageView postImage;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_matches, container, false);
        setHasOptionsMenu(true);
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(),(user)->{
            if(user.getUserMatches().size()>0){
                setFields();
                MatchesAppear();
            }
        });


        return view;
    }


    public void setFields() {
        NameMatchesDetails = (TextView) view.findViewById(R.id.namematchesDetails);
        EmailMatchesDetails = (TextView) view.findViewById(R.id.emailmatchesDetails);
        NameLastMatch = (TextView) view.findViewById(R.id.lastmatchname);
        EmailLastMatch = (TextView) view.findViewById(R.id.lastmatchemail);
        postImage = (ImageView) view.findViewById(R.id.lastmatchphoto);
        postImage.setVisibility(View.INVISIBLE);
    }

    public void MatchesAppear() {
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(), (user) -> {
            MatchesId = user.getUserMatches();
            MatchDetails(MatchesId);

        });

    }

    public void MatchDetails(List<String> userId) {
        Model.instance.GetUserById(userId.get(userId.size() - 1), (user) -> {
            if (!user.getImageURL().equals("")) {
                Picasso.get()
                        .load(user.getImageURL())
                        .placeholder(R.drawable.burgerchipsdrinkbackground)
                        .into(postImage);
            }
            EmailLastMatch.setText(user.getEmail());
            NameLastMatch.setText(user.getName());

        });
        postImage.setVisibility(View.VISIBLE);

        for (String id : userId) {
            Model.instance.GetUserById(id, (user) -> {
                name = user.getName();
                email = user.getEmail();
                NameDetails += (name + "\n");
                EmailDetails += (email + "\n");
                NameMatchesDetails.setText(NameDetails);
                EmailMatchesDetails.setText(EmailDetails);

            });
        }

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
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