package com.example.finalproject_foodating.model;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

public class ModelFireBase {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    static FirebaseUser currentFirebaseUser;
    static FirebaseAuth mAuth;
    static String current;
    static User user1;


    public static String getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
        currentFirebaseUser = mAuth.getCurrentUser();
        if (currentFirebaseUser != null) {
            current = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } else {
            current = null;
        }


        return current;
    }

    public static FirebaseAuth getAuthUser() {
        return mAuth;
    }

    public static User getCurrentUserObj() {
        Model.instance.GetUserById(current, (user) -> {
            user1 = user;
        });
        return user1;
    }

    //if inside the fragment will be import  firebase -10 points
    //because if the fragment want access to db it should be through the model
    public void getAllUsers(Model.GetAllUsersListener listener) {
        db.collection("user").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<User> UsersList = new LinkedList<>(); //might be error
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {

                        User u = User.fromJson(doc.getData());
                        if (u != null) {
                            UsersList.add(u); //add from document each user

                        }
                    }
                } else {

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
                .document(RegisteredUser.getUserId()).set(RegisteredUser.toJson())
                .addOnSuccessListener((successListener) -> {
                    listener.onComplete();
                })
                .addOnFailureListener((e) -> {
                    Log.d("TAG", e.getMessage());
                });

    }

    public void GetUserById(String UserId, Model.GetUserByIdListener listener) {
        DocumentReference docRef = db.collection("user").document(UserId);
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


    List<String> AllCurrentUsersMatches = new LinkedList<String>();
    List<String> AllPostUserMatches = new LinkedList<String>();
    List<String> AllCurrentUsersLikes = new LinkedList<String>();
    List<String> AllPostUserDislikes = new LinkedList<String>();
    List<String> AllCurrentUserDislikes = new LinkedList<String>();

    public void EditUserLikes(String LikeOrDislike, String PostUserId, String CurrentUser, Model.EditUserLikesListener listener) {
        DocumentReference EditPostUserIdLikes = db.collection("user").document(PostUserId);
        DocumentReference EditCurrentUserIdLikes = db.collection("user").document(CurrentUser);


        Model.instance.GetUserById(CurrentUser, (user1) -> {
            Log.d("current1",String.valueOf(AllCurrentUsersLikes.size()));
            AllCurrentUserDislikes = user1.getUserDisLikes();
            AllCurrentUsersMatches = user1.getUserMatches();
            AllCurrentUsersLikes = user1.getUserLikes();
            Log.d("current2",String.valueOf(AllCurrentUsersLikes.size()));

            Model.instance.GetUserById(PostUserId, (user) -> {
                Log.d("current3",String.valueOf(AllCurrentUsersLikes.size()));
                //  AllCurrentUsersLikes = user.getUserLikes();
                AllPostUserDislikes = user.getUserDisLikes();
                AllPostUserMatches = user.getUserMatches();
                if (LikeOrDislike.equals("like")) {
                    AllCurrentUsersLikes.add(PostUserId);
                    Log.d("current4",String.valueOf(AllCurrentUsersLikes.size()));
                    EditCurrentUserIdLikes.update("user_likes", AllCurrentUsersLikes)
                            .addOnSuccessListener((successListener) -> {
                                listener.onComplete();
                            })
                            .addOnFailureListener((e) -> {
                                Log.d("TAG", e.getMessage());
                            });
                } else if (LikeOrDislike.equals("dislike")) {
                    AllPostUserDislikes.add(CurrentUser);
                    EditPostUserIdLikes.update("user_dislike", AllPostUserDislikes)
                            .addOnSuccessListener((successListener) -> {
                                listener.onComplete();
                            })
                            .addOnFailureListener((e) -> {
                                Log.d("TAG", e.getMessage());
                            });

                    AllCurrentUserDislikes.add(PostUserId);
                    EditCurrentUserIdLikes.update("user_dislike", AllCurrentUserDislikes)
                            .addOnSuccessListener((successListener) -> {
                                listener.onComplete();
                            })
                            .addOnFailureListener((e) -> {
                                Log.d("TAG", e.getMessage());
                            });

                } else if (LikeOrDislike.equals("match")) {
                    AllPostUserMatches.add(CurrentUser);
                    EditPostUserIdLikes.update("user_matches", AllPostUserMatches)
                            .addOnSuccessListener((successListener) -> {
                                listener.onComplete();
                            })
                            .addOnFailureListener((e) -> {
                                Log.d("TAG", e.getMessage());
                            });

                    AllCurrentUsersMatches.add(PostUserId);
                    EditCurrentUserIdLikes.update("user_matches", AllCurrentUsersMatches)
                            .addOnSuccessListener((successListener) -> {
                                listener.onComplete();
                            })
                            .addOnFailureListener((e) -> {
                                Log.d("TAG", e.getMessage());
                            });

                }
            });
        });




    }
//////////////////////////////////////////////////////////////////////////////


    public void addPost(Post NewPost, String FoodId, Boolean bool, Model.AddPostListener listener) {
// Add a new document with a generated ID
        db.collection("posts")
                .document(FoodId).set(NewPost.toJson())
                .addOnSuccessListener((successListener) -> {
                    listener.onComplete();
                })
                .addOnFailureListener((e) -> {
                    Log.d("TAG", e.getMessage());
                });

    }

    public void getAllPosts(Long since, Model.GetAllPostsListener listener) {
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.lastUpdate1, new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Post> postsList = new LinkedList<>(); //might be error
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Post p = Post.fromJson(doc.getData());
                        if (p != null) {
                            postsList.add(p); //add from document each user
                        }
                    }
                } else {

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

    public void GetPostsById(Long since, String UserId, Model.GetPostsByIdListener listener) {
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.lastUpdate1, new Timestamp(since, 0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Post> postsList = new LinkedList<>(); //might be error
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        Post p = Post.fromJson(doc.getData());
                        // String str;
                        if (p != null && p.getOwner().equals(UserId))//&&p.getFlag()==true) {
                        {

                            postsList.add(p); //add from document each user
                        }
//                        else if( UserId.equals("all")){   //p.flag==true &&
//                            postsList.add(p);
//                        }

                        else {
                            continue;
                        }
                    }
                } else {

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



    public void EditUserPost(String FoodId, String FoodName, String Description, Boolean flag, Model.EditUserPostListener listener) {
        DocumentReference EditPost = db.collection("posts").document(FoodId);
        EditPost.update("food_description", Description);
        EditPost.update("food_name", FoodName);
        EditPost.update("deletedPost", flag)
                .addOnSuccessListener((successListener) -> {
                   // Model.instance.reloadPosts();
                    listener.onComplete();
                })
                .addOnFailureListener((e) -> {
                    Log.d("TAG", e.getMessage());
                });

    }


    public void saveFoodImage(String FoodId, Bitmap bitmap, Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("" + FoodId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(e -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            Uri downloadurl = uri;
                            listener.onComplete(downloadurl.toString());
                        }));

    }

    public void setPostImageURL(String FoodId, String UserImageURL, Model.SetPostImageUrlListener listener) {
        DocumentReference EditUser = db.collection("posts").document(FoodId);
        EditUser.update("imageURL", UserImageURL).addOnSuccessListener((successListener) -> {
            listener.onComplete();
        });
    }


    public void saveUserImage(String UserId, Bitmap bitmap, Model.SaveUserImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("" + UserId);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(e -> listener.onComplete(null))
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                        .addOnSuccessListener(uri -> {
                            Uri downloadurl = uri;
                            listener.onComplete(downloadurl.toString());
                        }));

    }

    public void setUserImageURL(String UserId, String UserImageURL, Model.SetPostImageUrlListener listener) {
        DocumentReference EditUser = db.collection("user").document(UserId);
        EditUser.update("imageURL", UserImageURL).addOnSuccessListener((successListener) -> {
            listener.onComplete();
        });
    }


}



