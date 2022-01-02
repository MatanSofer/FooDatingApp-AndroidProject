package com.example.finalproject_foodating.model;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();
    MutableLiveData<List<Post>> AllPostsListLD  = new MutableLiveData<List<Post>>();

    private Model(){
    ReloadPostList();
    }




    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(GetAllUsersListener listener){
        modelFireBase.getAllUsers(listener);
    }



    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(User user,AddUserListener listener){
        modelFireBase.addUser(user,listener);
    }


    public interface GetUserByEmailListener{
        void onComplete(User user);
    }
    public void GetUserByEmail(String UserEmail ,GetUserByEmailListener listener){
        modelFireBase.GetUserByEmail(UserEmail ,listener);
    }



    public interface GetAllPostsListener{
        void onComplete(List<Post> posts);
    }
    public void getAllPosts(GetAllPostsListener listener){
        modelFireBase.getAllPosts(listener);
    }


    private void ReloadPostList() {
        modelFireBase.getAllPosts((list)->{
            AllPostsListLD.setValue(list);
        });

    }

    public LiveData<List<Post>> GetAllPosts()
    {
        return AllPostsListLD;
    }

    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post post,String FoodId,int Index,AddPostListener listener){
        modelFireBase.addPost(post,FoodId,()->{
            ReloadPostList();
            listener.onComplete();
        });
    }

    public interface GetPostsByEmailListener{
        void onComplete(List<Post> posts);
    }
    public void GetPostsByEmail(String UserEmail ,GetPostsByEmailListener listener){
        modelFireBase.GetPostsByEmail(UserEmail ,listener);

    }

    public interface EditUserListener{
        void onComplete();
    }
    public void EditUser(String UserEmail,String Name,String Password,String Email,EditUserListener listener){
        modelFireBase.EditUser(UserEmail,Name,Password,Email,listener);
    }

    public interface SetUserImageUrlListener{
        void onComplete();
    }
    public void setUserImageURL(String UserEmail,String UserImageURL,SetUserImageUrlListener listener){
        modelFireBase.setUserImageURL(UserEmail,UserImageURL,listener);
    }
    public interface GetUserImageUrlListener{
        void onComplete(String UserURL);
    }
//    public void GetUserImageURL(String UserEmail,GetUserImageUrlListener listener)
//    {
//        ModelFireBase.getUserImageURL(UserEmail,listener);
//    }
    public interface EditUserLikesListener{
        void onComplete();
    }
    public void EditUserLikes(Boolean LikeOrDislike,String UserEmail,String LikeOrDislikeUser,EditUserLikesListener listener){
        modelFireBase.EditUserLikes(LikeOrDislike,UserEmail,LikeOrDislikeUser,listener);
    }


    public interface GetPostByFoodIdListener{
        void onComplete(Post post);
    }
    public void GetPostByFoodId(String FoodPostId ,GetPostByFoodIdListener listener){
        modelFireBase.GetPostByFoodId(FoodPostId ,listener);
    }


    public interface SaveImageListener
    {
        void onComplete(String URL);
    }
    public void saveImage(String UserEmail,Bitmap bitmap,SaveImageListener listener) {
        modelFireBase.saveImage(UserEmail,bitmap,listener);
    }


}
