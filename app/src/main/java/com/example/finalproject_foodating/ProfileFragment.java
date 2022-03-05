package com.example.finalproject_foodating;

import static android.app.Activity.RESULT_OK;

import static com.example.finalproject_foodating.AddPostFragment.REQUEST_IMAGE_CAPTURE;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    ImageButton EditBtn, LogoutBtn, CameraBtn;
    ImageView ProfileImage;
    View view;
    Bitmap bitmap;
    String ImageURL;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);


        ProfileImage = view.findViewById(R.id.profileFrag_profileImage);
        LoadUserImage();

        EditBtn = (ImageButton) view.findViewById(R.id.imageButton_editdatails);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editDetailsFragment);
            }
        });
        LogoutBtn = (ImageButton) view.findViewById(R.id.imageButton_logout);
        mAuth = FirebaseAuth.getInstance();
        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        CameraBtn = (ImageButton) view.findViewById(R.id.profileFrag_cameraBtn);
        CameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

            }
        });
        return view;
    }

    private void LoadUserImage() {
        Model.instance.GetUserById(ModelFireBase.getCurrentUser(), (user) -> {
            if (!user.getImageURL().equals("")) {
                Picasso.get().load(user.getImageURL()).
                        placeholder(R.drawable.logocropped).
                        into(ProfileImage);
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            ProfileImage.setImageBitmap(bitmap);
            Model.instance.saveUserImage(ModelFireBase.getCurrentUser(), bitmap, (URL) -> {
                Model.instance.setUserImageURL(ModelFireBase.getCurrentUser(), URL, () -> {
                    this.ImageURL = URL;
                });

            });
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu, menu);
    }

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_mainScreenFragment);
        }


    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mainApp_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_mainAppFragment);
                break;
            case R.id.messages_menu_btn:
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_matchesFragment);
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        return true;
    }

}