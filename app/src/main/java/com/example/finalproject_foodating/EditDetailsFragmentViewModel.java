package com.example.finalproject_foodating;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;

import java.util.LinkedList;
import java.util.List;

public class EditDetailsFragmentViewModel extends ViewModel {
    LiveData<List<Post>> posts = Model.instance.GetAllPosts();


    public LiveData<List<Post>> getAllPosts ()
    {
        return posts;
    }



}
