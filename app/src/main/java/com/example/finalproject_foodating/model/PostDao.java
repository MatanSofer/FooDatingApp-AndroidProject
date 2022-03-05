package com.example.finalproject_foodating.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post where post.flag==1")
    List<Post> getAll();
    @Query("UPDATE Post SET Description=:description ,Flag=:flag"
            +" where Owner LIKE:PostOwn and FoodName LIKE:foodname  "
    )
    void update (String PostOwn,String foodname,String description, Boolean flag);
    @Query("UPDATE Post SET ImageURL=:url"
            +" where Owner LIKE:PostOwn and FoodName LIKE:foodname  "
    )
    void updatePostPhoto(String PostOwn,String foodname,String url);
    @Query("select * from Post where post.Owner LIKE:userID and post.flag==1"  )
    List<Post> getUserPosts(String userID);
    @Query("DELETE FROM Post where Post.FoodName LIKE:food1")
    void delete1(String food1);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);
    @Delete
    void delete(Post post);

}