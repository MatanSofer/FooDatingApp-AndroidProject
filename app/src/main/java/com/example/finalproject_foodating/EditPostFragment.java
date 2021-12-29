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

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;


public class EditPostFragment extends Fragment {

    String FoodPostId,Owner;
    EditText foodNameEt,descriptionEt,ownerEt;
    View view;
    ProgressBar progressBar;
    Button DeleteBtn,SaveBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_edit_post, container, false);
        FoodPostId=EditPostFragmentArgs.fromBundle(getArguments()).getFoodId();
        Log.d("foodclicked",FoodPostId);
        foodNameEt= view.findViewById(R.id.foodname_editpost_et);
        descriptionEt = view.findViewById(R.id.description_editpost_et);
        ownerEt = view.findViewById(R.id.owner_editpost_et);

        progressBar = view.findViewById(R.id.editpost_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);

       Model.instance.GetPostByFoodId(FoodPostId,(post)->{
           foodNameEt.setText(post.getFoodName());
           descriptionEt.setText(post.getDescription());
           ownerEt.setText(post.getOwner());
           Owner = post.getOwner();
       });




        SaveBtn = view.findViewById(R.id.save_edit_post_btn);
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);

               // saveEdit();

                EditPostFragmentDirections.ActionEditPostFragmentToEditDetailsFragment action = EditPostFragmentDirections.actionEditPostFragmentToEditDetailsFragment(Owner);
                Navigation.findNavController(v).navigate(action);

            }
        });

        DeleteBtn = view.findViewById(R.id.delete_edit_post_btn);
        DeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                SaveBtn.setEnabled(false);
                DeleteBtn.setEnabled(false);

                // saveDelete();

                EditPostFragmentDirections.ActionEditPostFragmentToEditDetailsFragment action = EditPostFragmentDirections.actionEditPostFragmentToEditDetailsFragment(Owner);
                Navigation.findNavController(v).navigate(action);

            }
        });





       return view;
    }

    public void saveEdit(){
        String fooddescription = descriptionEt.getText().toString();
        String foodname = foodNameEt.getText().toString();

       String foodId = foodname+Owner;

        Post post= new Post(Owner,foodname,fooddescription);

        Model.instance.addPost(post,foodId,()->{
        });


    }
}