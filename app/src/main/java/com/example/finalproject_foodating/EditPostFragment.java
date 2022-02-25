package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.Post;


public class EditPostFragment extends Fragment {

    String FoodPostId,Owner,UserName;
    EditText foodNameEt,descriptionEt,ownerEt;
    View view;
    ProgressBar progressBar;
    Button DeleteBtn,SaveBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_post, container, false);

        FoodPostId=EditPostFragmentArgs.fromBundle(getArguments()).getFoodId();
        foodNameEt= view.findViewById(R.id.foodname_editpost_et);
        descriptionEt = view.findViewById(R.id.description_editpost_et);
        ownerEt = view.findViewById(R.id.owner_editpost_et);
        ownerEt.setEnabled(false);
        progressBar = view.findViewById(R.id.editpost_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);

        Model.instance.GetUserById(ModelFireBase.getCurrentUser(),(user)->{
            UserName=user.getName();
        });


       Model.instance.GetPostByFoodId(FoodPostId,(post)->{
           foodNameEt.setText(post.getFoodName());
           descriptionEt.setText(post.getDescription());
           ownerEt.setText(UserName);
           Owner = post.getOwner();

       });



        SaveBtn = view.findViewById(R.id.save_edit_post_btn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);
                Toast.makeText(getActivity(),"Your post has been edited successfully!",Toast.LENGTH_SHORT).show();
                saveChanges();

;            }
        });

        DeleteBtn = view.findViewById(R.id.delete_edit_post_btn);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);
                Toast.makeText(getActivity(),"Your post has been deleted!",Toast.LENGTH_SHORT).show();
                deletePost();
            }
        });

       return view;
    }

    public void saveChanges(){
        String newFood_description = descriptionEt.getText().toString();
        String newFood_name = foodNameEt.getText().toString();
        Model.instance.EditUserPost(FoodPostId,newFood_name,newFood_description,true,()->{
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_editDetailsFragment);
        });
    }

    public void deletePost(){
        Model.instance.EditUserPost(FoodPostId,descriptionEt.getText().toString(),foodNameEt.getText().toString(),false,()->{
            Navigation.findNavController(view).navigate(R.id.action_editPostFragment_to_editDetailsFragment);
        });
    }
}