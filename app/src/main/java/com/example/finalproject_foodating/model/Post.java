package com.example.finalproject_foodating.model;
import java.util.HashMap;
import java.util.Map;

public class Post{

    public String Owner ="";
    public String FoodName="";
    public String Description="";
    //public String photo="";


    public Post(){
    }
    public Post(String Owner,String FoodName , String Description ){
        this.FoodName=FoodName;
        this.Description=Description;
        this.Owner=Owner;
        //this.Photo=Photo;
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
        json.put("food_name",getFoodName());
        json.put("food_description",getDescription());
        json.put("food_owner",getOwner());
        //json.put("photo",);
        return json;
    }

    static Post fromJson(Map<String,Object> json ){
        String foodname = (String)json.get("food_name");
        String description = (String)json.get("food_description");
        String owner = (String)json.get("food_owner");
        Post post = new Post(owner,foodname,description);
        return post;
    }


}
