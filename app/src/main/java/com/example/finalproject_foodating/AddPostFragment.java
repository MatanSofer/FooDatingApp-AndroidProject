package com.example.finalproject_foodating;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.Post;
import com.example.finalproject_foodating.model.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class AddPostFragment extends Fragment {
    Button SavePostBtn;
    EditText FoodName, Description;
    ProgressBar progressBar;
    // User user;
    String DescriptionStr, FoodNameStr;
    String foodId;
    View view;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView list;
    AddPostFragmentViewModel viewModel;
    SwipeRefreshLayout swipeRefresh;
    ImageView postPhoto;
    Bitmap bitmap;
    ImageButton cameraBtn;
    static final Integer REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(AddPostFragmentViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_add_post, container, false);

        //user= ModelFireBase.getCurrentUserObj();
        progressBar = view.findViewById(R.id.edit_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);
        Model.instance.reloadPosts();

        postPhoto = view.findViewById(R.id.post_imageview);
        FoodName = view.findViewById(R.id.NewFoodName_et);
        Description = view.findViewById(R.id.NewDescription_et);
        SavePostBtn = view.findViewById(R.id.save_post_btn);

        SavePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check()){
                    SavePostDetails();
                }else{
                    Toast.makeText(getActivity(), "please fill all details ", Toast.LENGTH_SHORT).show();
                }
                Model.instance.reloadPosts();
            }
        });


        list = view.findViewById(R.id.post_edit_rv);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), linearLayoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);


        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override //click on item and what will heppen
            public void onItemClick(int position, View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                FoodName.setEnabled(false);
                Description.setEnabled(false);
                Post p = viewModel.getData().getValue().get(position);
                foodId = p.getFoodName() + p.getOwner();
                EditPostFragment.setFoodId(foodId);
                Navigation.findNavController(view).navigate(R.id.action_editDetailsFragment_to_editPostFragment);
//                AddPostFragmentDirections.ActionEditDetailsFragmentToEditPostFragment action = AddPostFragmentDirections.actionEditDetailsFragmentToEditPostFragment(foodId);
//                Navigation.findNavController(v).navigate(action);
            }
        });

        swipeRefresh = view.findViewById(R.id.EditFrag_Refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        // setHasOptionsMenu(true);
        if (viewModel.getData().getValue() == null) {
            refreshData();
        }

        viewModel.getData().observe(getViewLifecycleOwner(), (Postlist) -> {
            adapter.notifyDataSetChanged();
        });


        cameraBtn = view.findViewById(R.id.post_camera_btn);
        cameraBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            postPhoto.setImageBitmap(bitmap);
        }
    }

    //ot right function just to check
    private void refreshData() {
        swipeRefresh.setRefreshing(true);
        Model.instance.reloadPosts();
//
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
    }

    //    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.student_list_menu,menu);
//    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView FoodName;
        TextView FoodDescription;
        ImageView postImg;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.post_food_name);
            FoodDescription = itemView.findViewById(R.id.post_food_description);
            postImg = itemView.findViewById(R.id.post_food_photo);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(pos, v);
                    }
                }
            });
        }

        public void bind(Post post) {
            FoodName.setText(post.FoodName);
            FoodDescription.setText(post.getDescription());
            String url = post.getImageURL();
            if (!url.equals("")) {
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.logocropped)
                        .into(postImg);
            }
        }
    }

    interface OnItemClickListener {
        void onItemClick(int position, View v);
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        OnItemClickListener listener;

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.user_post, parent, false);
            MyViewHolder holder = new MyViewHolder(view, listener);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = viewModel.getData().getValue().get(position);
            holder.bind(post);

        }

        @Override
        public int getItemCount() {
            if (viewModel.getData().getValue() == null) {
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }
    public boolean check(){
        if(bitmap!=null && Description.getText().toString()!=null
          &&FoodName.getText().toString()!=null){
            return true;
        }
        return false;
    }
    public void SavePostDetails() {
        DescriptionStr = Description.getText().toString();
        FoodNameStr = FoodName.getText().toString();
        foodId = FoodNameStr + ModelFireBase.getCurrentUser();
        Post post = new Post(ModelFireBase.getCurrentUser(), FoodNameStr, DescriptionStr, true);

        if (bitmap != null) {
            Model.instance.saveImage(foodId, bitmap, (URL) -> {
                post.setImageURL(URL);
                Model.instance.setPostImageURL(foodId, URL,FoodNameStr, () -> {
                    //this.ImageURL = URL;
                });

            });
        }


        Model.instance.addPost(post, foodId, () -> {
            Toast.makeText(getActivity(), "Your post has been added successfully!", Toast.LENGTH_SHORT).show();

        });

        Description.getText().clear();
        FoodName.getText().clear();
    }
}