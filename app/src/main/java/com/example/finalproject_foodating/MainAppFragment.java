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
    String UserEmail,CurrentScreenUserEmail,UserImageURL;
    User CurrentScreenUser;
    Boolean LikeOrDislike;
    ImageButton LikeBtn,DislikeBtn;
    TextView forcheck;
    //User n1;
    List<User> UsersList ;
    List<String> AllEmailList; //to recognize users
    List<String> AllUsersLikes ; //to recognize users
    List<String> AllUserDislikes; //to recognize users


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view = inflater.inflate(R.layout.fragment_main_app, container, false);
         setHasOptionsMenu(true);

        //UserEmail = MainAppFragmentArgs.fromBundle(getArguments()).getUserEmail();
         //UserImageURL =MainAppFragmentArgs.fromBundle(getArguments()).getUserImageURL();
        forcheck = view.findViewById(R.id.useremailcheck);

        //getAllusers();

//        LikeBtn = (ImageButton)view.findViewById(R.id.likebtn);
//        LikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Like();
//            }
//        });

//        DislikeBtn = (ImageButton)view.findViewById(R.id.dislikebtn);
//        DislikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dislike();
//            }
//        });

         return view;

    }



//(Boolean LikeOrDislike,String UserEmail,String LikeOrDislikeUser,EditUserLikesListener listener)
//    public void getAllusers(){
//        Model.instance.GetUserByEmail(UserEmail,(user)->{
//            Model.instance.getAllUsers((data)->{
//                UsersList=data;
//                //forcheck.setText(UsersList.get(0).getEmail());
//                if(UsersList!=null){
//                    for(int i = 0 ; i <UsersList.size() ;i++){
//                        if(!(UsersList.get(i).getEmail().equals(UserEmail))){
//                            if(!(user.getUserLikes().contains(UsersList.get(i).getEmail())) && !(user.getUserDisLikes().contains(UsersList.get(i).getEmail()))){
//                                forcheck.setText(UsersList.get(i).getEmail());
//                                CurrentScreenUserEmail=UsersList.get(i).getEmail();
//                                CurrentScreenUser=UsersList.get(i);
//                                break;
//                            }
//
//                        }
//
//                    }
//
//                }
//                else{
//                    forcheck.setText("NO USERS TO SHOW!");
//                }
//
//
//
//            });
//
//        });

  //  }


//    public void Like(){
//        LikeOrDislike=true;
//
//        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{
//            if(CurrentScreenUser.getUserLikes().contains(UserEmail)){
//                Toast.makeText(getActivity(),"There is a match with " +CurrentScreenUser.getName() ,Toast.LENGTH_LONG).show();
//                MainAppFragmentDirections.ActionMainAppFragmentToMatchesFragment action =MainAppFragmentDirections.actionMainAppFragmentToMatchesFragment(UserEmail,UserImageURL);
//                Navigation.findNavController(view).navigate(action);
//            }
//            else{
//                MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail,UserImageURL);
//                Navigation.findNavController(view).navigate(action);
//            }
//
//        });
//
//    }
//    public void Dislike(){
//        LikeOrDislike=false;
//        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{
//
//            MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail,UserImageURL);
//            Navigation.findNavController(view).navigate(action);
//
//        });
//    }



    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_profileFragment);
                break;
            case R.id.messages_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_matchesFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }


}