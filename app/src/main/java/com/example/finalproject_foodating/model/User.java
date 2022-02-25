package com.example.finalproject_foodating.model;



import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
    final static String nameJ ="name";
    final static String emailJ ="email";
    final static String genderJ ="gender";
    final static String userDislikeJ ="user_dislike";
    final static String userLikeJ ="user_likes";
    final static String ImageUrlJ ="imageURL";
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
        json.put(nameJ,getName());
        json.put(emailJ,getEmail());
        json.put(genderJ,getGender());
        json.put(userDislikeJ,getUserDisLikes());
        json.put(userLikeJ,getUserLikes());
        json.put(ImageUrlJ,getImageURL());
        return json;
    }

    static User fromJson(Map<String,Object> json ){
        String email1 = (String)json.get(emailJ);
        String name1 = (String)json.get(nameJ);
        String gender1 = (String)json.get(genderJ);
        String imageURL = (String) json.get(ImageUrlJ);
        List<String> TheUserLikes = (List<String>)json.get(userLikeJ);
        List<String> TheUserDisLikes = (List<String>)json.get(userDislikeJ);

        User user = new User(name1,email1,gender1,TheUserLikes,TheUserDisLikes);
        user.setImageURL(imageURL);
        return user;

    }


}
