package com.example.finalproject_foodating.model;

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
}
