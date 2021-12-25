package com.example.finalproject_foodating.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Match {
    List<String> AllPotentialUsers = new ArrayList<>();
    List<String> TheUserLikes = new ArrayList<>();
    List<String> TheUserDisLikes = new ArrayList<>();
    //List<String> AllPotentialUsers = new ArrayList<>();
    User user;
    Post post;
//    String Description;
//    String Name;
//    String OwnerEmail;
//    String FoodName;
    public Match(List<String> TheUserLikes,List<String> TheUserDisLikes){
        //this.AllPotentialUsers=AllPotentialUsers;
        this.TheUserLikes=TheUserLikes;
        this.TheUserDisLikes=TheUserDisLikes;
        //this.user=user;
      //  this.post=post;
    }
    public List<String> getUserLikes(){
        return TheUserLikes;
    }
    public List<String> getUserDisLikes(){
        return TheUserDisLikes;
    }
    public Map<String,Object> toJson(){
        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put("user_dislike",getUserDisLikes());
        json.put("user_likes",getUserLikes());

        //json.put("photo",);
        return json;
    }

//    static Match fromJson(Map<String,Object> json ){
//        List<String> TheUserLikes1 = (List<String>) json.get("food_name");
//        List<String> TheUserDisLikes1 = (List<String>)json.get("food_description");
//        String owner = (String)json.get("food_owner");
//
//        //Match match = new Macth(TheUserLikes1,TheUserDisLikes1);
//        //return match;
//    }

}
