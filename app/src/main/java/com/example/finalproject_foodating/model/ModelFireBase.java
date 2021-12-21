package com.example.finalproject_foodating.model;

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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ModelFireBase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //if inside the fragment will be import  firebase -10 points
    //because if the fragment want access to db it should be through the model
    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<User> studentsList = new LinkedList<>(); //might be error
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        User u = User.fromJson(doc.getData());
                        if (u != null) {
                            studentsList.add(u); //add from document each user
                        }
                    }
                }else{

                }
                listener.onComplete(studentsList);
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

//////////////////////////////////////////////////////////////////////////////


    public void addPost(Post NewPost, Model.AddPostListener listener) {
// Add a new document with a generated ID
        db.collection("posts")
                .document(NewPost.getOwner()).set(NewPost.toJson())
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
}



