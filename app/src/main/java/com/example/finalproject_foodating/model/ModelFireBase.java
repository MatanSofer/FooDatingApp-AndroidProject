package com.example.finalproject_foodating.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFireBase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<String> AllUsersLikes = new LinkedList<String>();
    List<String> AllUserDislikes = new LinkedList<String>();



    //if inside the fragment will be import  firebase -10 points
    //because if the fragment want access to db it should be through the model
    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<User> UsersList = new LinkedList<>(); //might be error
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){

                        User u = User.fromJson(doc.getData());
                        if (u != null) {
                            Log.d("add","user");
                            UsersList.add(u); //add from document each user
                            Log.d("email",u.getEmail());
                        }
                    }
                }else{

                }
                listener.onComplete(UsersList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        });
    }
    //in fire base , if i bring the document i bring all of him
    //THE LISTENER WHILL ALERT US WHEN USER HAS BEEN ADDED TO DB
    public void addUser(User RegisteredUser, Model.AddUserListener listener) {
// Add a new document with a generated ID
        db.collection("user")
                .document(RegisteredUser.getEmail()).set(RegisteredUser.toJson())
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();
                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                });

    }

    public void GetUserByEmail(String UserEmail, Model.GetUserByEmailListener listener) {
        DocumentReference docRef = db.collection("user").document(UserEmail);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        User u = User.fromJson(document.getData());
                        listener.onComplete(u);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }



    public void setUserImageURL(String UserEmail, String UserImageURL, Model.SetUserImageUrlListener listener)
    {
        DocumentReference EditUser = db.collection("user").document(UserEmail);
        EditUser.update("imageURL",UserImageURL).addOnSuccessListener((successListener)->{
            listener.onComplete();
        });
    }

    public void EditUser(String UserEmail,String Name,String Password,String Email,Model.EditUserListener listener){
        DocumentReference EditUser = db.collection("user").document(UserEmail);
        EditUser.update("email", Email);
        EditUser.update("name", Name);
        EditUser.update("password", Password)
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();
                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                });
    }
    public void EditUserLikes(Boolean LikeOrDislike, String UserEmail,String LikeOrDislikeUser, Model.EditUserLikesListener listener){
        DocumentReference EditUserLikes = db.collection("user").document(UserEmail);

        Model.instance.GetUserByEmail(UserEmail,(user)->{
            AllUsersLikes=user.getUserLikes();
            AllUserDislikes = user.getUserDisLikes();

        });
        if(LikeOrDislike){
            Log.d("Like",LikeOrDislikeUser);
            AllUsersLikes.add(LikeOrDislikeUser);
            Log.d("Like",AllUsersLikes.get(0));
            EditUserLikes.update("gender", "adda");
            EditUserLikes.update("user_likes",AllUsersLikes)
                    .addOnSuccessListener((successListener)-> {
                        listener.onComplete();
                    })
                    .addOnFailureListener((e)-> {
                        Log.d("TAG", e.getMessage());
                    });
        }
        else{
                AllUserDislikes.add(LikeOrDislikeUser);
                EditUserLikes.update("user_dislike", AllUserDislikes )
                        .addOnSuccessListener((successListener)-> {
                            listener.onComplete();
                        })
                        .addOnFailureListener((e)-> {
                            Log.d("TAG", e.getMessage());
                        });

    }}
//////////////////////////////////////////////////////////////////////////////


    public void addPost(Post NewPost,String FoodId, Model.AddPostListener listener) {
// Add a new document with a generated ID
        db.collection("posts")
                .document(FoodId).set(NewPost.toJson())
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();
                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                });

    }
    public void getAllPosts(Model.GetAllPostsListener listener) {
        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Post> postsList = new LinkedList<>(); //might be error
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        Post p = Post.fromJson(doc.getData());
                        if (p != null) {
                            postsList.add(p); //add from document each user
                        }
                    }
                }else{

                }
                listener.onComplete(postsList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        });
    }

    public void GetPostByFoodId(String FoodPostId, Model.GetPostByFoodIdListener listener) {
        DocumentReference docRef = db.collection("posts").document(FoodPostId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Post p = Post.fromJson(document.getData());
                        listener.onComplete(p);
                    } else {
                        listener.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                    listener.onComplete(null);
                }
            }
        });
    }
    public void GetPostsByEmail(String UserEmail, Model.GetPostsByEmailListener listener) {
        db.collection("posts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Post> postsList = new LinkedList<>(); //might be error
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        Post p = Post.fromJson(doc.getData());
                        if (p != null && p.getOwner().equals(UserEmail)) {
                            postsList.add(p); //add from document each user
                        }
                        else{
                            continue;
                        }
                    }
                }else{

                }
                listener.onComplete(postsList);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        });
    }

    public void saveImage(String UserEmail,Bitmap bitmap, Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(""+UserEmail);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(e -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
            Uri downloadurl = uri;
            listener.onComplete(downloadurl.toString());
        }));

    }
}



