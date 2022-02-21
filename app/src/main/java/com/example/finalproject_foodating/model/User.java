package com.example.finalproject_foodating.model;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class User {

    public String Name="";
    public String Email="";
    public String Gender ="";
    public String ImageURL ="";
    public String UserId;
    FirebaseUser currentFirebaseUser;
    List<String> TheUserLikes = new ArrayList<>();
    List<String> TheUserDisLikes = new ArrayList<>();

    public User(){
    }
    public User(String name , String email , String gender){
        this.Name=name;
        this.Email=email;
        this.Gender = gender;
        this.UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    }
    public User(String name  , String email , String gender,List<String> TheUserLikes,List<String> TheUserDisikes){
        this.Name=name;
        this.Email=email;
        this.Gender = gender;
        this.TheUserLikes=TheUserLikes;
        this.TheUserDisLikes=TheUserDisikes;
        this.UserId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public String getName() {
        return Name;
    }
    public String getEmail() {
        return Email;
    }
    public String getUserId(){return UserId; }
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
    public void setEmail(String email) {
        this.Email = email;
    }
    public void setGender(String gender) {
        this.Gender = gender;
    }
    public String getImageURL(){ return this.ImageURL;}
    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }


    public Map<String,Object> toJson(){
        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put("name",getName());
        json.put("email",getEmail());
        json.put("gender",getGender());
        json.put("user_dislike",getUserDisLikes());
        json.put("user_likes",getUserLikes());
        json.put("imageURL",getImageURL());
        return json;
    }

    static User fromJson(Map<String,Object> json ){
        String email1 = (String)json.get("email");
        String name1 = (String)json.get("name");
        String gender1 = (String)json.get("gender");
        String imageURL = (String) json.get("imageURL");
        List<String> TheUserLikes = (List<String>)json.get("user_likes");
        List<String> TheUserDisLikes = (List<String>)json.get("user_dislike");

        User user = new User(name1,email1,gender1,TheUserLikes,TheUserDisLikes);
        user.setImageURL(imageURL);
        return user;

    }


}
