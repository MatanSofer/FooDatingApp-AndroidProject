package com.example.finalproject_foodating;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;

import java.util.LinkedList;
import java.util.List;

public class AddPostFragmentViewModel extends ViewModel {
    LiveData<List<Post>> posts = Model.instance.getAllUserPosts();
    LiveData<List<Post>> allposts = Model.instance.getAllPosts();

    public LiveData<List<Post>> getData() {
        return posts;
    }

    public LiveData<List<Post>> getAllData() {
        return allposts;
    }


    public void setUserPostData(LiveData<List<Post>> p) {
        this.posts = p;
    }

    public void setAllUsersPostsData(LiveData<List<Post>> p) {
        this.allposts = p;
    }

}
