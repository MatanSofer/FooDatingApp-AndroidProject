package com.example.finalproject_foodating;

import static android.app.Activity.RESULT_OK;
import static com.example.finalproject_foodating.AddPostFragment.REQUEST_IMAGE_CAPTURE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject_foodating.model.AppLocalDB;
import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.Post;
import com.squareup.picasso.Picasso;


public class EditPostFragment extends Fragment {

    String FoodPostId,Owner,UserName;
    EditText foodNameEt,descriptionEt,ownerEt;
    View view;
    ProgressBar progressBar;
    Button DeleteBtn,SaveBtn;
    ImageView postPhoto;
    ImageButton cameraBtn;
    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        FoodPostId=EditPostFragmentArgs.fromBundle(getArguments()).getFoodId();
        foodNameEt= view.findViewById(R.id.foodname_editpost_et);
        descriptionEt = view.findViewById(R.id.description_editpost_et);
        ownerEt = view.findViewById(R.id.owner_editpost_et);
        postPhoto= view.findViewById(R.id.editpost_imageview);
        cameraBtn = view.findViewById(R.id.editpost_camera_btn);
        ownerEt.setEnabled(false);
        progressBar = view.findViewById(R.id.editpost_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);



        Model.instance.GetUserById(ModelFireBase.getCurrentUser(),(user)->{
            UserName=user.getName();
            Model.instance.GetPostByFoodId(FoodPostId,(post)->{
                    setDetailsVisble(post);
            });
        });




        SaveBtn = view.findViewById(R.id.save_edit_post_btn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);
                descriptionEt.setEnabled(false);
                foodNameEt.setEnabled(false);
                Toast.makeText(getActivity(),"Your post has been edited successfully!",Toast.LENGTH_SHORT).show();
                saveChanges();

            }
        });

        DeleteBtn = view.findViewById(R.id.delete_edit_post_btn);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);
                descriptionEt.setEnabled(false);
                foodNameEt.setEnabled(false);
                Toast.makeText(getActivity(),"Your post has been deleted!",Toast.LENGTH_SHORT).show();
                deletePost();
            }
        });

        cameraBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
        });

       return view;
    }

    public void onActivityResult(int requestCode,int resultCode ,@NonNull Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Bundle bundle=data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            postPhoto.setImageBitmap(bitmap);
        }
    }







    public void setDetailsVisble(Post post){
        foodNameEt.setText(post.getFoodName());
        descriptionEt.setText(post.getDescription());
        String url = post.getImageURL();
        if(!url.equals("")){
            Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.burgerchipsdrinkbackground)
                    .into(postPhoto);
        }
        ownerEt.setText(UserName);
        Owner = post.getOwner();
    }
    public void saveChanges(){
        String newFood_description = descriptionEt.getText().toString();
        String newFood_name = foodNameEt.getText().toString();
        if(bitmap!=null){
            Model.instance.saveImage(FoodPostId, bitmap, (URL) -> {
                //post.setImageURL(URL);
                Model.instance.setPostImageURL(FoodPostId, URL, () -> {
                    //  Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_editDetailsFragment);
                });

            });
        }
        Model.instance.EditUserPost(FoodPostId,newFood_name,newFood_description,true,()->{
            //Model.instance.reloadPosts();
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_editDetailsFragment);
        });


    }

    public void deletePost(){
        Model.instance.EditUserPost(FoodPostId,foodNameEt.getText().toString(),descriptionEt.getText().toString(),false,()->{
           // Model.instance.reloadPosts();
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_editDetailsFragment);
        });
    }
}