package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.User;

import java.util.LinkedList;
import java.util.List;


public class MainAppFragment extends Fragment {
    View view;
    String UserEmail,CurrentScreenUserEmail;
    User CurrentScreenUser;
    Boolean LikeOrDislike;
    ImageButton LikeBtn,DislikeBtn;
    TextView forcheck;
    User n1;
    List<User> UsersList ;
    List<String> AllEmailList; //to recognize users
    List<String> AllUsersLikes ; //to recognize users
    List<String> AllUserDislikes; //to recognize users


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_main_app, container, false);
         setHasOptionsMenu(true);

        UserEmail = MainAppFragmentArgs.fromBundle(getArguments()).getUserEmail();
        forcheck = view.findViewById(R.id.useremailcheck);



        getAllusers();
      //  UsersList.get(0).getEmail();

//
//
//
//
//

//
//



         return view;

    }



//(Boolean LikeOrDislike,String UserEmail,String LikeOrDislikeUser,EditUserLikesListener listener)
    public void getAllusers(){
        Model.instance.GetUserByEmail(UserEmail,(user)->{
            n1 = user;
        });
        Model.instance.getAllUsers( (data)->{
            UsersList=data;
            if(UsersList!=null){

                //forcheck.setText(UsersList.get(0).getEmail());
                if(UsersList!=null){
                    for(int i = 0 ; i <UsersList.size() ;i++){
                        if(!(UsersList.get(i).getEmail().equals(UserEmail))){
                            if(n1.getUserLikes().contains(UsersList.get(i).getEmail())==false || n1.getUserDisLikes().contains(UsersList.get(i).getEmail())==false){
                                forcheck.setText(UsersList.get(i).getEmail());
                                CurrentScreenUserEmail=UsersList.get(i).getEmail();
                                CurrentScreenUser=UsersList.get(i);
                                break;
                            }

                        }

                    }

                }
            }
            LikeBtn = (ImageButton)view.findViewById(R.id.likebtn);
            LikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Like();
                }
            });

            DislikeBtn = (ImageButton)view.findViewById(R.id.dislikebtn);
            DislikeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dislike();
                }
            });
            if(data!=null){
                Log.d("notnull","data");
            }
        });

            //Log.d("connect",UsersList.get(0).getEmail());
//        for(int i = 0 ; i < UsersList.size() ; i++){
//            AllEmailList.add(UsersList.get(i).getEmail());
//        }
    }


    public void Like(){
        LikeOrDislike=true;

        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{
            if(CurrentScreenUser.getUserLikes().contains(UserEmail)){
                Toast.makeText(getActivity(),"There is a match with" +CurrentScreenUser.getName() ,Toast.LENGTH_LONG).show();
                MainAppFragmentDirections.ActionMainAppFragmentToMatchesFragment action =MainAppFragmentDirections.actionMainAppFragmentToMatchesFragment(UserEmail);
                Navigation.findNavController(view).navigate(action);
            }
            MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail);
            Navigation.findNavController(view).navigate(action);
        });

    }
    public void Dislike(){
        LikeOrDislike=false;
        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{

            MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail);
            Navigation.findNavController(view).navigate(action);

        });
    }
    public Boolean match(){
        return true;
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