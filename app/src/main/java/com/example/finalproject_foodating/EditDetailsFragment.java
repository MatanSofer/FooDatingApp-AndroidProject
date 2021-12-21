package com.example.finalproject_foodating;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;

import java.util.LinkedList;
import java.util.List;


public class EditDetailsFragment extends Fragment {
    String UserEmail ;
    View view;
    MyAdapter adapter;
    List<Post> posts = new LinkedList<Post>();
    //SwipeRefreshLayout swipeRefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_edit_details, container, false);

        UserEmail = MainAppFragmentArgs.fromBundle(getArguments()).getUserEmail();
        //get all the posts the user owns

        RecyclerView list = view.findViewById(R.id.post_edit_rv);
        list.setHasFixedSize(true);

        list.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MyAdapter();
        list.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override //click on item and what will heppen
            public void onItemClick(int position, View v) {
//
            }
        });

//        swipeRefresh = view.findViewById(R.id.studentlist_swipe_refresh);
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                refreshData();
//            }
//        });

       // setHasOptionsMenu(true);
        refreshData();
        return view;
    }
 //ot right function just to check
    private void refreshData() {
        Model.instance.getAllPosts(new Model.GetAllPostsListener() {
            @Override
            public void onComplete(List<Post> p) {
                posts = p;
                adapter.notifyDataSetChanged();
//                if (swipeRefresh.isRefreshing()) {
//                    swipeRefresh.setRefreshing(false);
//                }
            }
        });
    }


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.student_list_menu,menu);
//    }


    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView FoodName;
        TextView FoodDescription;
        CheckBox Profile;

        public MyViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.post_food_name);
            FoodDescription = itemView.findViewById(R.id.post_food_description);
            Profile = itemView.findViewById(R.id.post_cb);
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
            Post post = posts.get(position);
            holder.FoodName.setText(post.FoodName);
            holder.FoodDescription.setText(post.getDescription());
            //holder.Profile.setChecked(post.isFlag());
        }

        @Override
        public int getItemCount() {
            return posts.size();
        }
    }
}
