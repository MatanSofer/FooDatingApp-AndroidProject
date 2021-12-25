package com.example.finalproject_foodating.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {

    public String Name="";
    public String Email="";
    public String Password="";
    public String Gender ="";
    //public List<User> Likes =new LinkedList<>();
    List<String> TheUserLikes = new ArrayList<>();
    List<String> TheUserDisLikes = new ArrayList<>();

    public User(){

    }
    public User(String name , String password , String email , String gender){
        this.Password=password;
        this.Name=name;
        this.Email=email;
        this.Gender = gender;

    }
    public User(String name , String password , String email , String gender,List<String> TheUserLikes,List<String> TheUserDisikes){
        this.Password=password;
        this.Name=name;
        this.Email=email;
        this.Gender = gender;
        this.TheUserLikes=TheUserLikes;
        this.TheUserDisLikes=TheUserDisikes;
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
    public List<String> getUserLikes(){
        return TheUserLikes;
    }
    public List<String> getUserDisLikes(){
        return TheUserDisLikes;
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
        json.put("user_dislike",getUserDisLikes());
        json.put("user_likes",getUserLikes());
        return json;
    }
    static User fromJson(Map<String,Object> json ){
        String email1 = (String)json.get("email");
        String name1 = (String)json.get("name");
        String password1 = (String)json.get("password");
        String gender1 = (String)json.get("gender");
        List<String> TheUserLikes = (List<String>)json.get("user_likes");
        List<String> TheUserDisLikes = (List<String>)json.get("user_dislike");

        User user = new User(name1,password1,email1,gender1,TheUserLikes,TheUserDisLikes);
        return user;

    }


}
