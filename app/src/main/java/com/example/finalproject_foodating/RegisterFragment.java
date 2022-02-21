package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {
    View view;
    Button RegisterBtn;
    RadioButton MaleGenderBtn,FemaleGenderBtn;
    EditText NameEt,EmailEt,Password;
    ProgressBar progressBar;
    String UserName,UserPassword,UserEmail,UserGender;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_register, container, false);

        NameEt = view.findViewById(R.id.name_reg_et);
        EmailEt = view.findViewById(R.id.email_reg_et);
        Password = view.findViewById(R.id.password_reg);
        MaleGenderBtn = (RadioButton)view.findViewById(R.id.male_reg_radiobtn);
        FemaleGenderBtn = (RadioButton)view.findViewById(R.id.female_reg_radiobtn);
        RegisterBtn = view.findViewById(R.id.editsavebtn);
        progressBar=view.findViewById(R.id.register_progressBar);

        progressBar.setVisibility(ViewGroup.GONE);




        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });


          return view;

    }
    public void register(){
        if(SaveUserFields()){
            mAuth.createUserWithEmailAndPassword(UserEmail,UserPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getActivity(),"Registered successfully!",Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(ViewGroup.VISIBLE);
                            RegisterBtn.setEnabled(false);
                            User user = new User(UserName,UserEmail,UserGender);

                            Model.instance.addUser(user,()->{
                                Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainAppFragment);
                            });
                        }
                        else{
                            Toast.makeText(getActivity(),"Registered Failed"+task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                }
            });



        }
    }

    public boolean SaveUserFields(){
        boolean isValid=true;
        UserName = NameEt.getText().toString();
        UserPassword = Password.getText().toString();
        UserEmail = EmailEt.getText().toString();

        if(TextUtils.isEmpty(UserName)) {
            NameEt.setError("Please Fill Your Name");
            Toast.makeText(getActivity(),"Missing Name , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
        }
        else if(TextUtils.isEmpty(UserPassword)) {

            Password.setError("Please Fill Your Name");
            Toast.makeText(getActivity(),"Missing Password , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
        }
        else if(TextUtils.isEmpty(UserEmail)) {
            EmailEt.setError("Please Fill Your Email");
            Toast.makeText(getActivity(),"Missing Email , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
        }
        if(MaleGenderBtn.isChecked())
        {
            UserGender="male";
        }
        else if(FemaleGenderBtn.isChecked())
        {
            UserGender="female";
        }
        else{
            Toast.makeText(getActivity(),"Missing Gender , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
        }

        return isValid;
    }

}


