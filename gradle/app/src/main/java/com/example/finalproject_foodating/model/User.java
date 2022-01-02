package com.example.finalproject_foodating.model;

import androidx.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String Name="";
    public String Email="";
    public String Password="";
    public String Gender ="";

    public User(){

    }
    public User(String name , String password , String email , String gender){
        this.Password=password;
        this.Name=name;
        this.Email=email;
        this.Gender = gender;
    }

    public String getName() {
        return Name;
    }

    public String getPassword() {
        return Password;
    }

    public String getEmail() {
        return Email;
    }

    public String getGender() {
        return Gender;
    }

    public void setName(String name) {
        this.Name=name;
    }

    public void setPassword(String password) {
        this.Password=password;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setGender(String gender) {
        this.Gender = gender;
    }
    
    public Map<String,Object> toJson(){

        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put("name",getName());
        json.put("password",getPassword());
        json.put("email",getEmail());
        json.put("gender",getGender());
        return json;
    }
    static User fromJson(Map<String,Object> json ){
        String email1 = (String)json.get("email");
        String name1 = (String)json.get("name");
        String password1 = (String)json.get("password");
        String gender1 = (String)json.get("gender");
        User user = new User(name1,password1,email1,gender1);
        return user;

    }


}
