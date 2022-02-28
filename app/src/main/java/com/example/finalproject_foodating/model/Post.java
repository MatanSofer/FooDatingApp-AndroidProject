package com.example.finalproject_foodating.model;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.ColorSpace;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.finalproject_foodating.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Post{

    final static String foodName ="food_name";
    final static String foodDescription ="food_description";
    final static String foodOwner ="food_owner";
    public final static String lastUpdate1 = "lastupdate";
    final static String deletedPost="deletedPost";
    final static String ImageUrlJ ="imageURL";
    public boolean flag;
    @PrimaryKey
    @NonNull
    public String FoodName="";
    public String Owner ="";
    public String Description="";
    public String ImageURL ="";
    Long lastUpdate=new Long(0);






    //public String photo="";


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
    public Long getLastUpdate() {
        return lastUpdate;
    }
    public String getImageURL(){ return this.ImageURL;}

    public void setImageURL(String imageURL) {
        this.ImageURL = imageURL;
    }
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
    public void setLastUpdate(Long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Map<String,Object> toJson(){
        // Create a new user with different fields
        Map<String, Object> json = new HashMap<>();
        json.put(foodName,getFoodName());
        json.put(foodDescription,getDescription());
        json.put(foodOwner,getOwner());
        json.put(deletedPost,getFlag());
        json.put(lastUpdate1, FieldValue.serverTimestamp());
        json.put(ImageUrlJ,getImageURL());
        //json.put("photo",);
        return json;

    }

    static Post fromJson(Map<String,Object> json ){
        String foodname = (String)json.get(foodName);
        String description = (String)json.get(foodDescription);
        String owner = (String)json.get(foodOwner);
        Boolean flag = (Boolean)json.get(deletedPost);
        String imageURL = (String) json.get(ImageUrlJ);
        Post post = new Post(owner,foodname,description,flag);

        Timestamp ts = (Timestamp)json.get(lastUpdate1);
        post.setLastUpdate(new Long(ts.getSeconds()));
        post.setImageURL(imageURL);
        return post;
    }
    static Long getLocalLastUpdated(){
        Long localLastUpdate = MyApplication.getContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(lastUpdate1,0);
        return localLastUpdate;
    }

    static void setLocalLastUpdated(Long date){
        SharedPreferences.Editor editor = MyApplication.getContext()
                .getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        editor.putLong(lastUpdate1,date);
        editor.commit();
    }

}
