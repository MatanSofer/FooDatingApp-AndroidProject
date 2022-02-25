package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;
import com.example.finalproject_foodating.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class LoginFragment extends Fragment {
    EditText EmailEt,Password;
    Button loginBtn;
    ProgressBar progressBar;
    String UserPassword,UserEmail,UserImageURL;
    View view;
    private FirebaseAuth mAuth;
    public User u1;
    boolean isValid1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAuth = FirebaseAuth.getInstance();
        view =inflater.inflate(R.layout.fragment_login, container, false);

        EmailEt = view.findViewById(R.id.email_log_et);
        Password = view.findViewById(R.id.password_log_et);
        loginBtn = view.findViewById(R.id.loginscreen_btn);
        progressBar=view.findViewById(R.id.login_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        login();

            }
        });
         return view;
    }


    public void login(){
        if(SaveFields())
        {
            mAuth.signInWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(),"Login Successfull",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(ViewGroup.VISIBLE);
                            loginBtn.setEnabled(false);
                            EmailEt.setEnabled(false);
                            Password.setEnabled(false);
                            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_mainAppFragment);
                    }
                    else{
                        Toast.makeText(getActivity(),"Login Failed"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });


        }
    }

    public boolean SaveFields (){
        boolean isValid = true;
        UserPassword = Password.getText().toString();
        UserEmail = EmailEt.getText().toString();



        if(TextUtils.isEmpty(UserEmail)) {
            EmailEt.setError("Please Fill Your Email");
            Toast.makeText(getActivity(),"Missing Email , Try Again!",Toast.LENGTH_LONG).show();
            isValid = false;
        }
        else if(TextUtils.isEmpty(UserPassword)) {
            Password.setError("Please Fill Your Name");
            Toast.makeText(getActivity(),"Missing Password , Try Again!",Toast.LENGTH_LONG).show();
            isValid = false;
        }
        return isValid;
    }
    public boolean CheckIfDetailsCorrect(){
        mAuth.signInWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getActivity(),"Login Successfull",Toast.LENGTH_LONG).show();
                    }
            }
        });

        return isValid1;
    }



}