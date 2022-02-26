package com.example.finalproject_foodating.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PostDao {

    @Query("select * from Post")
    List<Post> getAll();
    @Query("select * from Post where post.Owner LIKE:userID and post.flag==1"  )
    List<Post> getUserPosts(String userID);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Post... posts);
    @Delete
    void delete(Post post);
}