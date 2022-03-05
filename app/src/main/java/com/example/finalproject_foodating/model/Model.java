package com.example.finalproject_foodating.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalproject_foodating.AddPostFragmentViewModel;
import com.example.finalproject_foodating.MyApplication;

import java.util.List;

public class Model {
    public static final Model instance = new Model();
    ModelFireBase modelFireBase = new ModelFireBase();


    private Model() {
        reloadPosts();
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

    public void GetUserById(String userID, GetUserByIdListener listener) {
        modelFireBase.GetUserById(userID, listener);
    }


    public interface GetAllPostsListener {
        void onComplete(List<Post> posts);
    }

    public void GetAllPosts(Long l, GetAllPostsListener listener) {
        modelFireBase.getAllPosts(l, listener);
    }


    public interface GetPostsByIdListener {
        void onComplete(List<Post> posts);
    }

    public void GetPostsById(Long l, String UserId, GetPostsByIdListener listener) {
        modelFireBase.GetPostsById(l, UserId, listener);

    }


    MutableLiveData<List<Post>> postListLD = new MutableLiveData<List<Post>>();
    MutableLiveData<List<Post>> postListLD1 = new MutableLiveData<List<Post>>();

    public void reloadPosts() {

        Long localLastUpdate = Post.getLocalLastUpdated();
        ;
        modelFireBase.GetPostsById(localLastUpdate, ModelFireBase.getCurrentUser(), (list) -> {
            MyApplication.executorService.execute(() -> {
                String str;
                Long lLastUpdate = new Long(0);
                for (Post p : list) {
                    if (p.getFlag() == false) {
                        AppLocalDB.db.postDao().delete(p);

                    } else {
                        AppLocalDB.db.postDao().insertAll(p);
                    }
                    if (p.getLastUpdate() > lLastUpdate) {
                        lLastUpdate = p.getLastUpdate()
                        ;
                    }
                }
                Post.setLocalLastUpdated(lLastUpdate);

                List<Post> postList = AppLocalDB.db.postDao().getUserPosts(ModelFireBase.getCurrentUser());
                postListLD.postValue(postList);
            });
        });

        modelFireBase.getAllPosts(localLastUpdate, (list) -> {
            MyApplication.executorService.execute(() -> {
                String str;
                Long lLastUpdate = new Long(0);
                for (Post p : list) {
                    if (p.getFlag() == false) {
                        AppLocalDB.db.postDao().delete(p);
                    } else {
                        AppLocalDB.db.postDao().insertAll(p);
                        Log.d("model true", "t");
                    }
                    if (p.getLastUpdate() > lLastUpdate) {
                        lLastUpdate = p.getLastUpdate();
                    }
                }
                Post.setLocalLastUpdated(lLastUpdate);
                List<Post> postList1 = AppLocalDB.db.postDao().getAll();
                postListLD1.postValue(postList1);

            });
        });


//        modelFireBase.GetPostsById(ModelFireBase.getCurrentUser() ,(list)->{
//            postListLD.setValue(list);
//        });
    }


    public LiveData<List<Post>> getAllUserPosts() {
        return postListLD;
    }

    public LiveData<List<Post>> getAllPosts() {
        return postListLD1;
    }


    public interface AddPostListener {
        void onComplete();
    }

    public void addPost(Post post, String FoodId, AddPostListener listener) {
        modelFireBase.addPost(post, FoodId, true, () -> {
            reloadPosts();
            listener.onComplete();
        });
    }


    public interface EditUserPostListener {
        void onComplete();
    }

    public void EditUserPost(String FoodId, String FoodName, String Description, Boolean flag, EditUserPostListener listener) {
        modelFireBase.EditUserPost(FoodId, FoodName, Description, flag, () -> {
            MyApplication.executorService.execute(() -> {
                AppLocalDB.db.postDao().update(ModelFireBase.getCurrentUser(),FoodName,Description,flag);
                if(flag==false){
                    AppLocalDB.db.postDao().delete1(FoodName);
                    reloadPosts();
                }
            });

            listener.onComplete();
        });
    }

    public interface GetPostByFoodIdListener {
        void onComplete(Post post);
    }

    public void GetPostByFoodId(String FoodPostId, GetPostByFoodIdListener listener) {
        modelFireBase.GetPostByFoodId(FoodPostId, listener);
    }

    public interface SetPostImageUrlListener {
        void onComplete();
    }

    public void setPostImageURL(String FoodId, String UserImageURL,String foodName1,  SetPostImageUrlListener listener) {
        modelFireBase.setPostImageURL(FoodId, UserImageURL, () -> {
            MyApplication.executorService.execute(() -> {
                AppLocalDB.db.postDao().updatePostPhoto(ModelFireBase.getCurrentUser(),foodName1,UserImageURL);
            });
            reloadPosts();
            listener.onComplete();
        });
    }

//////////////

//    public interface GetUserImageUrlListener {
//        void onComplete(String UserURL);
//    }
//    public void GetUserImageURL(String UserEmail,GetUserImageUrlListener listener)
//    {
//        ModelFireBase.getUserImageURL(UserEmail,listener);
//    }

    public interface EditUserLikesListener {
        void onComplete();
    }

    public void EditUserLikes(String LikeOrDislike, String UserId, String LikeOrDislikeUser, EditUserLikesListener listener) {
        modelFireBase.EditUserLikes(LikeOrDislike, UserId, LikeOrDislikeUser, listener);
    }


    public interface SaveImageListener {
        void onComplete(String URL);
    }

    public void saveImage(String Id, Bitmap bitmap, SaveImageListener listener) {
        modelFireBase.saveFoodImage(Id, bitmap, listener);
    }


    public interface SaveUserImageListener {
        void onComplete(String URL);
    }

    public void saveUserImage(String UserId, Bitmap bitmap, SaveUserImageListener listener) {
        modelFireBase.saveUserImage(UserId, bitmap, listener);


    }

    public interface SetUserImageUrlListener {
        void onComplete();
    }

    public void setUserImageURL(String UserId, String UserImageURL, SetUserImageUrlListener listener) {
        modelFireBase.setUserImageURL(UserId, UserImageURL, () -> {
            //reloadPosts();
            listener.onComplete();
        });
    }
}
