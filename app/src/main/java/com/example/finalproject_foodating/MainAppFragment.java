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


public class MainAppFragment extends Fragment {
    TextView ownerName , foodName, ownerName1 , foodName1;
    String FoodName,OwnerId,ImageUrl,postOwnerName,LikeOrDislike;
    ProgressBar progressBar;
    ImageButton LikeBtn,DislikeBtn;
    Button moveMatch;
    MyAdapter adapter;
    RecyclerView list;
    AddPostFragmentViewModel viewModel;
    SwipeRefreshLayout swipeRefresh;
    ImageView postImage;
    View view;

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
         setFields();
         Model.instance.reloadPosts();



        list = view.findViewById(R.id.MainApp_rv);
        list.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        list.setLayoutManager(linearLayoutManager);
        adapter =new MyAdapter();
        list.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(list.getContext(), linearLayoutManager.getOrientation());
        list.addItemDecoration(dividerItemDecoration);

        adapter.setOnItemClickListener(new MainAppFragment.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Post p = viewModel.getAllData().getValue().get(position);
                 FoodName = p.getFoodName();
                 OwnerId = p.getOwner();
                 ImageUrl = p.getImageURL();
                Model.instance.GetUserById(OwnerId,(user)->{
                    postOwnerName=user.getName();
                    ownerName.setText(postOwnerName);
                    foodName.setText(FoodName);
                    if(!ImageUrl.equals("")){
                        Picasso.get()
                                .load(ImageUrl)
                                .placeholder(R.drawable.burgerchipsdrinkbackground)
                                .into(postImage);
                    }
                    makeFieldsVisble();
                    checkTheUser();
                });

            }
        });



        viewModel.getAllData().observe(getViewLifecycleOwner(),(Postlist)-> {
            Log.d("livedatabeforeadd",String.valueOf(Postlist.size()));
            adapter.notifyDataSetChanged();
        });

        swipeRefresh = view.findViewById(R.id.MainApp_Refresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        moveMatch=view.findViewById(R.id.movematchesbtn);
        moveMatch.setVisibility(View.INVISIBLE);
        moveMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_mainAppFragment_to_matchesFragment);
            }
        });


        LikeBtn = (ImageButton)view.findViewById(R.id.likebtn);
        LikeBtn.setVisibility(View.INVISIBLE);
        LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Like();
            }
        });

        DislikeBtn = (ImageButton)view.findViewById(R.id.dislikebtn);
        DislikeBtn.setVisibility(View.INVISIBLE);
        DislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Dislike();
            }
        });

         return view;
    }


    public void setFields(){
        progressBar = view.findViewById(R.id.MainApp_progressBar);
        progressBar.setVisibility(ViewGroup.GONE);
        Model.instance.reloadPosts();
        postImage= (ImageView)view.findViewById(R.id.MainAppFoodImage);
        ownerName = (TextView)view.findViewById(R.id.OwnerNameTV);
        ownerName1 = (TextView)view.findViewById(R.id.Owner);
        foodName = (TextView)view.findViewById(R.id.foodnameTV);
        foodName1 = (TextView)view.findViewById(R.id.FoodName);
        postImage.setImageResource(R.drawable.burgerchipsdrinkbackground);
        postImage.setVisibility(View.INVISIBLE);
        ownerName.setVisibility(View.INVISIBLE);
        ownerName1.setVisibility(View.INVISIBLE);
        foodName.setVisibility(View.INVISIBLE);
        foodName1.setVisibility(View.INVISIBLE);
    }
    public void makeFieldsVisble(){
        postImage.setVisibility(View.VISIBLE);
        ownerName.setVisibility(View.VISIBLE);
        ownerName1.setVisibility(View.VISIBLE);
        foodName.setVisibility(View.VISIBLE);
        foodName1.setVisibility(View.VISIBLE);
    }
    private void refreshData() {
        swipeRefresh.setRefreshing(true);
        Model.instance.reloadPosts();
//        viewModel.getAllData().observe(getViewLifecycleOwner(), (Postlist) -> {
//            Log.d("livedataafteradd",String.valueOf(Postlist.size()));
//            adapter.notifyDataSetChanged();
      //  });
        if (swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
//                }
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
    }



    public void checkTheUser(){
        if(OwnerId.equals(ModelFireBase.getCurrentUser())){
            LikeBtn.setVisibility(View.INVISIBLE);
            DislikeBtn.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"This is your post!",Toast.LENGTH_SHORT).show();
        }
        else{
            checkExistDislike();
        }
    }
    public void checkExistDislike(){
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(),(user1)->{
            if(user1.getUserDisLikes().contains(OwnerId)){
               LikeBtn.setVisibility(View.INVISIBLE);
               DislikeBtn.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity(),"Yours food taste is different , Match is not possible ",Toast.LENGTH_SHORT).show();
            }
            else{
                checkExistMatches();
            }
        });
    }
    public void checkExistMatches(){
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(),(user1)->{
            if(user1.getUserMatches().contains(OwnerId)){
                LikeBtn.setVisibility(View.INVISIBLE);
                DislikeBtn.setVisibility(View.INVISIBLE);
                moveMatch.setVisibility(View.VISIBLE);
                Toast.makeText(getActivity(),"You already have match with "+postOwnerName,Toast.LENGTH_SHORT).show();

            }
            else{
                moveMatch.setVisibility(View.INVISIBLE);
                LikeBtn.setVisibility(View.VISIBLE);
                DislikeBtn.setVisibility(View.VISIBLE);
            }
        });
    }

    public void Dislike(){
        LikeOrDislike="dislike";
        Model.instance.EditUserLikes(LikeOrDislike,OwnerId,ModelFireBase.getCurrentUser(),()->{
            Toast.makeText(getActivity(),"You disliked "+postOwnerName+" food post!",Toast.LENGTH_SHORT).show();
        });
        LikeBtn.setVisibility(View.INVISIBLE);
        DislikeBtn.setVisibility(View.INVISIBLE);
    }
    public void Like(){
        LikeOrDislike="like";
        Model.instance.EditUserLikes(LikeOrDislike,OwnerId,ModelFireBase.getCurrentUser(),()->{
            Toast.makeText(getActivity(),"You liked "+postOwnerName+" food post!",Toast.LENGTH_SHORT).show();
        });
        LikeBtn.setVisibility(View.INVISIBLE);
        DislikeBtn.setVisibility(View.INVISIBLE);

        Model.instance.GetUserById(OwnerId,(user1)->{
            if(user1.getUserLikes().contains(ModelFireBase.getCurrentUser())){
                LikeBtn.setVisibility(View.INVISIBLE);
                DislikeBtn.setVisibility(View.INVISIBLE);
                moveMatch.setVisibility(View.VISIBLE);
                LikeOrDislike="match";
                Model.instance.EditUserLikes(LikeOrDislike,OwnerId,ModelFireBase.getCurrentUser(),()->{
                    Toast.makeText(getActivity(),"You Got New Match with "+postOwnerName,Toast.LENGTH_SHORT).show();
                });

            }

        });
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
                        .placeholder(R.drawable.logocropped)
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