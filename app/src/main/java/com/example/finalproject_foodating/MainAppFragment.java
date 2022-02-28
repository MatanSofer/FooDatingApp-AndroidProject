package com.example.finalproject_foodating;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.Post;
import com.example.finalproject_foodating.model.User;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;


public class MainAppFragment extends Fragment {
    View view;
    ProgressBar progressBar;
    String UserEmail,CurrentScreenUserEmail,UserImageURL;
    User CurrentScreenUser;
    Boolean LikeOrDislike;
    ImageButton LikeBtn,DislikeBtn;
    TextView forcheck;
    List<User> UsersList ;
    List<String> AllEmailList; //to recognize users
    List<String> AllUsersLikes ; //to recognize users
    List<String> AllUserDislikes; //to recognize users
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

         view = inflater.inflate(R.layout.fragment_main_app, container, false);
         setHasOptionsMenu(true);
        progressBar = view.findViewById(R.id.MainApp_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);
        Model.instance.reloadPosts();

        list = view.findViewById(R.id.MainApp_rv);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        adapter = new MyAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), linearLayoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        adapter.setOnItemClickListener(new MainAppFragment.OnItemClickListener() {
            @Override //click on item and what will heppenj
            public void onItemClick(int position, View v) {
                progressBar.setVisibility(ViewGroup.VISIBLE);
                Post p = viewModel.getAllData().getValue().get(position);
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_matchesFragment);
            }
        });

        swipeRefresh = view.findViewById(R.id.MainApp_Refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        if(viewModel.getAllData().getValue()==null){refreshData();}

        viewModel.getAllData().observe(getViewLifecycleOwner(),(Postlist)-> {
            adapter.notifyDataSetChanged();
        });


















//        forcheck = view.findViewById(R.id.useremailcheck);
//
//        getAllusers();
//
//        LikeBtn = (ImageButton)view.findViewById(R.id.likebtn);
//        LikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Like();
//            }
//        });
//
//        DislikeBtn = (ImageButton)view.findViewById(R.id.dislikebtn);
//        DislikeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Dislike();
//            }
//        });

         return view;

    }
//    public void getAllusers(){
//        Model.instance.GetUserByEmail(UserEmail,(user)->{
//            Model.instance.getAllUsers((data)->{
//                UsersList=data;
//                //forcheck.setText(UsersList.get(0).getEmail());
//                if(UsersList!=null){
//                    for(int i = 0 ; i <UsersList.size() ;i++){
//                        if(!(UsersList.get(i).getEmail().equals(UserEmail))){
//                            if(!(user.getUserLikes().contains(UsersList.get(i).getEmail())) && !(user.getUserDisLikes().contains(UsersList.get(i).getEmail()))){
//                                forcheck.setText(UsersList.get(i).getEmail());
//                                CurrentScreenUserEmail=UsersList.get(i).getEmail();
//                                CurrentScreenUser=UsersList.get(i);
//                                break;
//                            }
//
//                        }
//
//                    }
//
//                }
//                else{
//                    forcheck.setText("NO USERS TO SHOW!");
//                }
//
//
//
//            });
//
//        });

    //  }


//    public void Like(){
//        LikeOrDislike=true;
//
//        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{
//            if(CurrentScreenUser.getUserLikes().contains(UserEmail)){
//                Toast.makeText(getActivity(),"There is a match with " +CurrentScreenUser.getName() ,Toast.LENGTH_LONG).show();
//                MainAppFragmentDirections.ActionMainAppFragmentToMatchesFragment action =MainAppFragmentDirections.actionMainAppFragmentToMatchesFragment(UserEmail,UserImageURL);
//                Navigation.findNavController(view).navigate(action);
//            }
//            else{
//                MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail,UserImageURL);
//                Navigation.findNavController(view).navigate(action);
//            }
//
//        });
//
//    }
//    public void Dislike(){
//        LikeOrDislike=false;
//        Model.instance.EditUserLikes(LikeOrDislike,UserEmail,CurrentScreenUserEmail,()->{
//
//            MainAppFragmentDirections.ActionMainAppFragmentSelf action =MainAppFragmentDirections.actionMainAppFragmentSelf(UserEmail,UserImageURL);
//            Navigation.findNavController(view).navigate(action);
//
//        });
//    }

    private void refreshData() {
         // swipeRefresh.setRefreshing(true);


//        Model.instance.GetPostsById(ModelFireBase.getCurrentUser(),new Model.GetPostsByIdListener() {
//            @Override
//            public void onComplete(List<Post> p) {
//                viewModel.setData(p);
               // adapter.notifyDataSetChanged();
             //   if (swipeRefresh.isRefreshing()) {
              //      swipeRefresh.setRefreshing(false);
//                }
//            }
//        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView FoodName;
        TextView FoodDescription;
        ImageView postImg;

        public MyViewHolder(@NonNull View itemView, MainAppFragment.OnItemClickListener listener) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.post_food_name);
            FoodDescription = itemView.findViewById(R.id.post_food_description);
            postImg = itemView.findViewById(R.id.post_food_photo);
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
        public void bind(Post post){
            FoodName.setText(post.FoodName);
            FoodDescription.setText(post.getDescription());
            String url = post.getImageURL();
            if(!url.equals("")){
                Picasso.get()
                        .load(url)
                        .placeholder(R.drawable.burgerchipsdrinkbackground)
                        .into(postImg);
            }
        }
    }
    interface OnItemClickListener{
        void onItemClick(int position, View v);
    }


    class MyAdapter extends RecyclerView.Adapter<MainAppFragment.MyViewHolder>{
        MainAppFragment.OnItemClickListener listener;
        public void setOnItemClickListener(MainAppFragment.OnItemClickListener listener){
            this.listener = listener;
        }
        @NonNull
        @Override
        public MainAppFragment.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.user_post,parent,false);
            MainAppFragment.MyViewHolder holder = new MainAppFragment.MyViewHolder(view,listener);
            return holder;
        }
        @Override
        public void onBindViewHolder(@NonNull MainAppFragment.MyViewHolder holder, int position) {

            Post post = viewModel.getAllData().getValue().get(position);
            holder.bind(post);
        }
        @Override
        public int getItemCount() {
            if(viewModel.getAllData().getValue()==null){
                return 0;
            }
            return viewModel.getAllData().getValue().size();
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_profileFragment);
                break;
            case R.id.messages_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_matchesFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }


}