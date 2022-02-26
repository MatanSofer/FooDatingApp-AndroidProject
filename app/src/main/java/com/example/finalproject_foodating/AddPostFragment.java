package com.example.finalproject_foodating;

import android.content.Context;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.Post;
import com.example.finalproject_foodating.model.User;

import java.util.LinkedList;
import java.util.List;


public class AddPostFragment extends Fragment {
    Button SavePostBtn;
    EditText FoodName,Description;
    ProgressBar progressBar;
   // User user;
    String DescriptionStr,FoodNameStr;
    String foodId;
    View view;
    MyAdapter adapter;
    RecyclerView list;
    AddPostFragmentViewModel viewModel;
    SwipeRefreshLayout swipeRefresh;


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

        FoodName = view.findViewById(R.id.NewFoodName_et);
        Description = view.findViewById(R.id.NewDescription_et);
        SavePostBtn = view.findViewById(R.id.save_post_btn);
        SavePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePostDetails();
            }
        });

        Log.d("current",ModelFireBase.getCurrentUser());
        //get all the posts the user owns
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
                foodId = p.getFoodName()+p.getOwner();
                AddPostFragmentDirections.ActionEditDetailsFragmentToEditPostFragment action = AddPostFragmentDirections.actionEditDetailsFragmentToEditPostFragment(foodId);
                Navigation.findNavController(v).navigate(action);
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
        if(viewModel.getData().getValue()==null){refreshData();}

        viewModel.getData().observe(getViewLifecycleOwner(),(Postlist)-> {
            adapter.notifyDataSetChanged();
        });

        return view;
    }

    //ot right function just to check
    private void refreshData() {
      //  swipeRefresh.setRefreshing(true);



//        Model.instance.GetPostsById(ModelFireBase.getCurrentUser(),new Model.GetPostsByIdListener() {
//            @Override
//            public void onComplete(List<Post> p) {
//                viewModel.setData(p);
//                adapter.notifyDataSetChanged();
//                if (swipeRefresh.isRefreshing()) {
//                    swipeRefresh.setRefreshing(false);
//                }
//            }
//        });
    }
    //    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.student_list_menu,menu);
//    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView FoodName;
        TextView FoodDescription;
        //CheckBox Profile;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.post_food_name);
            FoodDescription = itemView.findViewById(R.id.post_food_description);
            //Profile = itemView.findViewById(R.id.post_cb);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(pos,v);
                    }
                }
            });
        }
    }
    interface OnItemClickListener{
        void onItemClick(int position, View v);
    }
    class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
        OnItemClickListener listener;
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.user_post,parent,false);
            MyViewHolder holder = new MyViewHolder(view,listener);
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            Post post = viewModel.getData().getValue().get(position);
            holder.FoodName.setText(post.FoodName);
            holder.FoodDescription.setText(post.getDescription());
            //holder.Profile.setChecked(post.isFlag());
        }
        @Override
        public int getItemCount() {
            if(viewModel.getData().getValue()==null){
                return 0;
            }
            return viewModel.getData().getValue().size();
        }
    }

    public void SavePostDetails() {
        DescriptionStr = Description.getText().toString();
        FoodNameStr = FoodName.getText().toString();
        foodId = FoodNameStr + ModelFireBase.getCurrentUser();

        Post post = new Post(ModelFireBase.getCurrentUser(), FoodNameStr, DescriptionStr, true);
        Model.instance.addPost(post, foodId, () -> {
            Toast.makeText(getActivity(),"Your post has been added successfully!",Toast.LENGTH_SHORT).show();
        });


    }
    }