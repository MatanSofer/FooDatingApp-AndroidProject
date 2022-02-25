package com.example.finalproject_foodating.model;

import android.graphics.Bitmap;

import java.util.List;

public class Model {
    public static final Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();


    private Model() {


    }


    public interface GetAllUsersListener {
        void onComplete(List<User> data);
    }

    public void getAllUsers(GetAllUsersListener listener) {
        modelFireBase.getAllUsers(listener);
    }


    public interface AddUserListener {
        void onComplete();
    }

    public void addUser(User user, AddUserListener listener) {
        modelFireBase.addUser(user, listener);

    }


    public interface GetUserByIdListener {
        void onComplete(User user);
    }

    public void GetUserById(String userID,GetUserByIdListener listener) {
        modelFireBase.GetUserById(userID,listener);
    }







    public interface GetAllPostsListener {
        void onComplete(List<Post> posts);
    }
     public void GetAllPosts(GetAllPostsListener listener){
      modelFireBase.getAllPosts(listener);
     }






    public interface GetPostsByIdListener{
        void onComplete(List<Post> posts);
    }
    public void GetPostsById(String UserId,GetPostsByIdListener listener){
        modelFireBase.GetPostsById(UserId ,listener);

    }




    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post post, String FoodId, AddPostListener listener) {
        modelFireBase.addPost(post, FoodId,true, () -> {
            listener.onComplete();
        });
    }




    public interface SetUserImageUrlListener {
        void onComplete();
    }

    public void setUserImageURL(String UserEmail, String UserImageURL, SetUserImageUrlListener listener) {
        modelFireBase.setUserImageURL(UserEmail, UserImageURL, listener);
    }

    public interface GetUserImageUrlListener {
        void onComplete(String UserURL);
    }
//    public void GetUserImageURL(String UserEmail,GetUserImageUrlListener listener)
//    {
//        ModelFireBase.getUserImageURL(UserEmail,listener);
//    }
//    public interface EditUserLikesListener{
//        void onComplete();
//    }
//    public void EditUserLikes(Boolean LikeOrDislike,String UserEmail,String LikeOrDislikeUser,EditUserLikesListener listener){
//        modelFireBase.EditUserLikes(LikeOrDislike,UserEmail,LikeOrDislikeUser,listener);
//    }


    public interface GetPostByFoodIdListener {
        void onComplete(Post post);
    }

    public void GetPostByFoodId(String FoodPostId, GetPostByFoodIdListener listener) {
        modelFireBase.GetPostByFoodId(FoodPostId, listener);
    }
    public interface EditUserPostListener{
        void onComplete();
    }
    public void EditUserPost(String FoodId,String FoodName,String Description,Boolean flag,EditUserPostListener listener){
        modelFireBase.EditUserPost(FoodId,FoodName,Description,flag,listener);
    }

    public interface SaveImageListener {
        void onComplete(String URL);
    }

    public void saveImage(String UserEmail, Bitmap bitmap, SaveImageListener listener) {
        modelFireBase.saveImage(UserEmail, bitmap, listener);
    }




}
