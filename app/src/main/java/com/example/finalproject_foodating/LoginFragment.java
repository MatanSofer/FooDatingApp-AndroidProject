package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;


public class LoginFragment extends Fragment {
    EditText EmailEt,Password;
    Button loginBtn;
    ProgressBar progressBar;
    String UserPassword,UserEmail;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view =inflater.inflate(R.layout.fragment_login, container, false);

        EmailEt = view.findViewById(R.id.email_log_et);
        Password = view.findViewById(R.id.password_log_et);
        loginBtn = view.findViewById(R.id.loginscreen_btn);
        progressBar=view.findViewById(R.id.login_progressBar);

        progressBar.setVisibility(ViewGroup.GONE);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                loginBtn.setEnabled(false);
                SaveFields();
                CheckIfDetailsCorrect(UserEmail);



                //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainAppFragment);




            }
        });
        //loginBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_mainAppFragment));

         return view;
    }
    public void SaveFields (){
        UserPassword = Password.getText().toString();
        UserEmail = EmailEt.getText().toString();
        if(TextUtils.isEmpty(UserPassword)) {
            Password.setError("Please Fill Your Name");
            //Toast.makeText(getActivity(),"Missing Password , Try Again!",Toast.LENGTH_LONG).show();
        }
        if(TextUtils.isEmpty(UserEmail)) {
            EmailEt.setError("Please Fill Your Email");
            //Toast.makeText(getActivity(),"Missing Email , Try Again!",Toast.LENGTH_LONG).show();
        }

    }
    public void CheckIfDetailsCorrect(String UserEmail){

                Model.instance.GetUserByEmail(UserEmail,(user)->{
                    if(user == null){
                        //Toast.makeText(getActivity(),"Email doesn't exist,try again",Toast.LENGTH_LONG).show();
                    }
                    if(user.getPassword().equals(UserPassword)){
                        LoginFragmentDirections.ActionLoginFragmentToMainAppFragment action = LoginFragmentDirections.actionLoginFragmentToMainAppFragment(user.getEmail());
                        Navigation.findNavController(view).navigate(action);
                    }
                    else{
                        //Toast.makeText(getActivity(),"Wrong Password,try again",Toast.LENGTH_LONG).show();
                    }
                });
            }



}