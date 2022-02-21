package com.example.finalproject_foodating;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.Post;
import com.example.finalproject_foodating.model.User;

import java.util.LinkedList;
import java.util.List;


public class EditDetailsFragment extends Fragment {
    Button SaveDetailsBtn,SavePostBtn;
    EditText NameEt,EmailEt,Password,FoodName,Description;
    ProgressBar progressBar;
    User user;
    String UserNewName,UserNewPassword,UserNewEmail,DescriptionStr,FoodNameStr;
    String UserEmail ;
    String foodId;
    View view;
    MyAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView list;
    EditDetailsFragmentViewModel viewModel;
    //SwipeRefreshLayout swipeRefresh;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        viewModel = new ViewModelProvider(this).get(EditDetailsFragmentViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_edit_details, container, false);
        user= ModelFireBase.getCurrentUserObj();

        NameEt = view.findViewById(R.id.name_edit_et);
        EmailEt = view.findViewById(R.id.email_edit_et);
        Password = view.findViewById(R.id.password_edit_et);
        SaveDetailsBtn = view.findViewById(R.id.save_edit_btn);
        progressBar = view.findViewById(R.id.edit_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);
        swipeRefreshLayout = view.findViewById(R.id.EditFrag_Refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        EmailEt.setEnabled(false);
        PerformUserFields();


        SaveDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveUserDetails();
            }
        });


        FoodName = view.findViewById(R.id.NewFoodName_et);
        Description = view.findViewById(R.id.NewDescription_et);
        SavePostBtn = view.findViewById(R.id.save_post_btn);

        SavePostBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SavePostDetails();
            }
        });


        //get all the posts the user owns

        list = view.findViewById(R.id.post_edit_rv);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter(getContext());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), linearLayoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override //click on item and what will heppen
            public void onItemClick(int position, View v) {
                Post p = viewModel.getAllPosts().getValue().get(position);
                foodId = p.getFoodName() + p.getOwner();
                EditDetailsFragmentDirections.ActionEditDetailsFragmentToEditPostFragment action = EditDetailsFragmentDirections.actionEditDetailsFragmentToEditPostFragment(foodId);
                Navigation.findNavController(v).navigate(action);
            }
        });
        list.setAdapter(adapter);
//        swipeRefresh = view.findViewById(R.id.studentlist_swipe_refresh);
        //       swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

        // setHasOptionsMenu(true);
        if (viewModel.getAllPosts() == null){
            refreshData();
        }

        viewModel.getAllPosts().observe(getViewLifecycleOwner(),(postList)->{
            adapter.notifyDataSetChanged();
        });

        return view;
    }

        private void refreshData() {
//            swipeRefreshLayout.setRefreshing(true);

//        Model.instance.GetPostsByEmail(UserEmail,new Model.GetPostsByEmailListener() {
//            @Override
//            public void onComplete(List<Post> p) {
//                 viewModel.setPostsList(p);
//                adapter.notifyDataSetChanged();
//                swipeRefreshLayout.setRefreshing(false);
////                if (swipeRefresh.isRefreshing()) {
////                    swipeRefresh.setRefreshing(false);
////                }
//            }
//        });
    }




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
        LayoutInflater inflater;
        OnItemClickListener listener;
        public MyAdapter(Context context)
        {
            this.inflater = LayoutInflater.from(context);
        }
        public void setOnItemClickListener(OnItemClickListener listener){
            this.listener = listener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.user_post,parent,false);
            return new MyViewHolder(view,listener);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Post post = viewModel.getAllPosts().getValue().get(position);
            holder.FoodName.setText(post.getFoodName());
            holder.FoodDescription.setText(post.getDescription());
            //holder.Profile.setChecked(post.isFlag());
        }

        @Override
        public int getItemCount() {
            if(viewModel.getAllPosts() ==null) {
                return 0;
            }
            return viewModel.getAllPosts().getValue().size();
        }
    }



    public void PerformUserFields(){
        Model.instance.GetUserById((user)->{
          //  Password.setText("user.getpassword()");
            NameEt.setText(user.getName());
            EmailEt.setText(user.getEmail());
        });
    }
    public void SaveUserDetails(){
        UserEmail=EmailEt.getText().toString();
        UserNewName = NameEt.getText().toString();
        UserNewPassword= Password.getText().toString();
        UserNewEmail = EmailEt.getText().toString();
        Model.instance.EditUser(UserEmail,UserNewName, UserNewPassword,UserNewEmail,()->{
        } );

    }
    public void SavePostDetails(){
        DescriptionStr = Description.getText().toString();
        FoodNameStr= FoodName.getText().toString();
        foodId=FoodNameStr+user.getUserId();
        Post post= new Post(user.getUserId(),FoodNameStr,DescriptionStr);
        viewModel.getAllPosts().getValue().add(0,post);
        Toast.makeText(getContext(),"Post Saved",Toast.LENGTH_SHORT).show();
        Model.instance.addPost(post,foodId,0,()->{
        });

    }
}
