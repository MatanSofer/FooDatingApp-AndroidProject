package com.example.finalproject_foodating;

import android.os.Bundle;

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


public class RegisterFragment extends Fragment {
    View view;
    Button RegisterBtn;
    RadioButton MaleGenderBtn,FemaleGenderBtn;
    EditText NameEt,EmailEt,Password;
    ProgressBar progressBar;
    String UserName,UserPassword,UserEmail,UserGender;


   // Boolean AllFieldCompleted=false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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


       // RegisterBtn.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_mainAppFragment));

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SaveUserFields()){
                    progressBar.setVisibility(ViewGroup.VISIBLE);
                    RegisterBtn.setEnabled(false);
                    User user = new User(UserName,UserPassword,UserEmail,UserGender);
                    Model.instance.addUser(user,()->{
                        RegisterFragmentDirections.ActionRegisterFragmentToMainAppFragment action = RegisterFragmentDirections.actionRegisterFragmentToMainAppFragment(UserEmail,"");
                        Navigation.findNavController(view).navigate(action);
                    });

                }

               //SaveUserFields();
                //Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_mainAppFragment);


            }
        });


          // Missing DB update/saving data + Checking if user filled attributes properly/Checking if user already had registered
        //Possibility to add a Toast message
          return view;

    }
    public boolean SaveUserFields(){
        boolean isValid=true;

        UserName = NameEt.getText().toString();
        UserPassword = Password.getText().toString();
        UserEmail = EmailEt.getText().toString();


        if(TextUtils.isEmpty(UserName)) {
        //    AllFieldCompleted=false;
            NameEt.setError("Please Fill Your Name");
            Toast.makeText(getActivity(),"Missing Name , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
//            Navigation.findNavController(view)
//                    .navigate(R.id.action_registerFragment_self);
        }
        else if(TextUtils.isEmpty(UserPassword)) {
        //    AllFieldCompleted=false;
            Password.setError("Please Fill Your Name");
            Toast.makeText(getActivity(),"Missing Password , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
//            Navigation.findNavController(view)
//                    .navigate(R.id.action_registerFragment_self);

        }
        else if(TextUtils.isEmpty(UserEmail)) {
         //   AllFieldCompleted=false;
            EmailEt.setError("Please Fill Your Email");
            Toast.makeText(getActivity(),"Missing Email , Try Again!",Toast.LENGTH_LONG).show();
            isValid=false;
//            Navigation.findNavController(view)
//                    .navigate(R.id.action_registerFragment_self);

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



        //Log.d("finalres",String.valueOf(isValid));
        return isValid;
    }

}


