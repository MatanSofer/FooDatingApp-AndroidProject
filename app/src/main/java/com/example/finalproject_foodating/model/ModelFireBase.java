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
    List<String> AllUsersLikes = new LinkedList<String>();
    List<String> AllUserDislikes = new LinkedList<String>();
    static FirebaseUser currentFirebaseUser ;
    static FirebaseAuth mAuth;
    //FirebaseUser currentUser = mAuth.getCurrentUser();
    static String current;
    static User user1;
   // private FirebaseAuth mAuth;

public static String getCurrentUser(){
    mAuth = FirebaseAuth.getInstance();
    currentFirebaseUser= mAuth.getCurrentUser();
    if(currentFirebaseUser!=null){
       current = FirebaseAuth.getInstance().getCurrentUser().getUid() ;}
    else{current=null;}
    //if(current==null){Log.d("currenINGET","null");}
    //else{Log.d("currenINGET",current);}

   return current;
}
public static FirebaseAuth getAuthUser(){
    return mAuth;
}
    public static User getCurrentUserObj(){
        Model.instance.GetUserById(current,(user)->{
               user1=user;
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
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){

                        User u = User.fromJson(doc.getData());
                        if (u != null) {
                            UsersList.add(u); //add from document each user

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
                .document(RegisteredUser.getUserId()).set(RegisteredUser.toJson())
                .addOnSuccessListener((successListener)-> {
                    listener.onComplete();
                })
                .addOnFailureListener((e)-> {
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





//    public void EditUserLikes(Boolean LikeOrDislike, String UserEmail,String LikeOrDislikeUser, Model.EditUserLikesListener listener){
//        DocumentReference EditUserLikes = db.collection("user").document(UserEmail);
//
//        Model.instance.GetUserById(UserEmail,(user)->{
//            AllUsersLikes=user.getUserLikes();
//            AllUserDislikes = user.getUserDisLikes();
//
//        });
//        if(LikeOrDislike){
//            Log.d("Like",LikeOrDislikeUser);
//            AllUsersLikes.add(LikeOrDislikeUser);
//            Log.d("Like",AllUsersLikes.get(0));
//            EditUserLikes.update("gender", "adda");
//            EditUserLikes.update("user_likes",AllUsersLikes)
//                    .addOnSuccessListener((successListener)-> {
//                        listener.onComplete();
//                    })
//                    .addOnFailureListener((e)-> {
//                        Log.d("TAG", e.getMessage());
//                    });
//        }
//        else{
//                AllUserDislikes.add(LikeOrDislikeUser);
//                EditUserLikes.update("user_dislike", AllUserDislikes )
//                        .addOnSuccessListener((successListener)-> {
//                            listener.onComplete();
//                        })
//                        .addOnFailureListener((e)-> {
//                            Log.d("TAG", e.getMessage());
//                        });
//
//    }}
//////////////////////////////////////////////////////////////////////////////


    public void addPost(Post NewPost,String FoodId,Boolean bool, Model.AddPostListener listener) {
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
    public void getAllPosts(Long since, Model.GetAllPostsListener listener) {
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.lastUpdate1,new Timestamp(since,0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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
    public void GetPostsById(Long since , String UserId, Model.GetPostsByIdListener listener) {
        db.collection("posts")
                .whereGreaterThanOrEqualTo(Post.lastUpdate1,new Timestamp(since,0))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                LinkedList<Post> postsList = new LinkedList<>(); //might be error
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot doc: task.getResult()){
                        Post p = Post.fromJson(doc.getData());
                       // String str;
                        if (p != null && p.getOwner().equals(UserId))//&&p.getFlag()==true) {
                        {

                            postsList.add(p); //add from document each user
                        }
//                        else if( UserId.equals("all")){   //p.flag==true &&
//                            postsList.add(p);
//                        }

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
    String str;
    public void EditUserPost(String FoodId,String FoodName,String Description,Boolean flag,Model.EditUserPostListener listener){
        DocumentReference EditPost = db.collection("posts").document(FoodId);
        EditPost.update("food_description", Description);
        EditPost.update("food_name", FoodName);
        EditPost.update("deletedPost", flag)
                .addOnSuccessListener((successListener)-> {
            str =new Boolean (flag).toString();
            Log.d("Edituserbool",str);
            //Model.instance.reloadPosts();
                    listener.onComplete();
                })
                .addOnFailureListener((e)-> {
                    Log.d("TAG", e.getMessage());
                });

    }







    public void saveFoodImage(String FoodId,Bitmap bitmap, Model.SaveImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child(""+FoodId);

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
    public void setPostImageURL(String FoodId, String UserImageURL, Model.SetPostImageUrlListener listener)
    {
        DocumentReference EditUser = db.collection("posts").document(FoodId);
        EditUser.update("imageURL",UserImageURL).addOnSuccessListener((successListener)->{
            listener.onComplete();
        });
    }





//    public void setPostImageURL(String UserId, String UserImageURL, Model.SetUserImageUrlListener listener)
//    {
//        DocumentReference EditUser = db.collection("user").document(UserId);
//        EditUser.update("imageURL",UserImageURL).addOnSuccessListener((successListener)->{
//            listener.onComplete();
//        });
//    }

}



