package com.example.finalproject_foodating;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;

import java.util.LinkedList;
import java.util.List;

public class AddPostFragmentViewModel extends ViewModel {
   LiveData<List<Post>> posts = Model.instance.getAllUserPosts();

//    public AddPostFragmentViewModel(){
//        data=Model
//    }

    public LiveData<List<Post>> getData() {
        return posts;
    }

    public void setData(LiveData<List<Post>> p) {
        this.posts=p;
    }
}
