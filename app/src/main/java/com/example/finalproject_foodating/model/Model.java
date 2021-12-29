package com.example.finalproject_foodating.model;

import java.util.ArrayList;
import java.util.List;

public class Model {
    public static final Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();

    private Model(){
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

    public interface AddPostListener{
        void onComplete();
    }
    public void addPost(Post post,String FoodId,AddPostListener listener){
        modelFireBase.addPost(post,FoodId,listener);
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
}
