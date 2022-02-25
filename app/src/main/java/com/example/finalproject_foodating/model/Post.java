package com.example.finalproject_foodating.model;
import android.graphics.ColorSpace;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;
@Entity
public class Post{

    final static String foodName ="food_name";
    final static String foodDescription ="food_description";
    final static String foodOwner ="food_owner";
    final static String lastUpdate1 = "lastupdate";
    final static String deletedPost="deletedPost";
    public boolean flag;
    public String Owner ="";
    public String FoodName="";
    public String Description="";
    Long lastUpdate=new Long(0);

    public Long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    //public String photo="";

    @Ignore
    public Post(String Owner,String FoodName , String Description,boolean flag ){
        this.FoodName=FoodName;
        this.Description=Description;
        this.Owner=Owner;
        this.flag=flag;
        //this.Photo=Photo;
    }
    public Post(){
    }

    public String getFoodName() {
        return FoodName;
    }
    public String getDescription() {
        return Description;
    }
    public String getOwner() {
        return Owner;
    }
    public boolean getFlag(){return flag;}

    public void setFlag(Boolean bool){this.flag=bool;}
    public void setFoodName(String FoodName) {
        this.FoodName=FoodName;
    }
    public void setDescription(String Description) {
        this.Description=Description;
    }
    public void setOwner(String Owner) {
        this.Owner=Owner;
    }


    public Map<String,Object> toJson(){
        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put(foodName,getFoodName());
        json.put(foodDescription,getDescription());
        json.put(foodOwner,getOwner());
        json.put(deletedPost,getFlag());
        //json.put(lastUpdate1, FieldValue.serverTimestamp());
        //json.put("photo",);
        return json;

    }

    static Post fromJson(Map<String,Object> json ){
        String foodname = (String)json.get(foodName);
        String description = (String)json.get(foodDescription);
        String owner = (String)json.get(foodOwner);
        Boolean flag = (Boolean)json.get(deletedPost);
        Post post = new Post(owner,foodname,description,flag);

       // Timestamp ts = (Timestamp)json.get(lastUpdate1);
       // post.setLastUpdate(new Long(ts.getSeconds()));

        return post;
    }


}
