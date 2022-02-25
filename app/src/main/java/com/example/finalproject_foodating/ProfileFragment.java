package com.example.finalproject_foodating;

import static android.app.Activity.RESULT_OK;

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
import com.example.finalproject_foodating.model.User;
import com.squareup.picasso.Picasso;


public class ProfileFragment extends Fragment {
    ImageButton EditBtn,SettingsBtn, CameraBtn;
    ImageView ProfileImage;
    View view;
    Bitmap bitmap;
    User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        setHasOptionsMenu(true);

        user= ModelFireBase.getCurrentUserObj();



        ProfileImage = view.findViewById(R.id.profileFrag_profileImage);
        //LoadUserImage();

        EditBtn = (ImageButton)view.findViewById(R.id.imageButton_editdatails);
        EditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_editDetailsFragment);
            }
        });

        SettingsBtn = (ImageButton)view.findViewById(R.id.imageButton_settings);
        SettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_profileFragment_to_settingsFragment);
            }
        });

//        CameraBtn = (ImageButton) view.findViewById(R.id.profileFrag_cameraBtn);
//        CameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(intent,REQUEST_IMAGE_CAPTURE);
//
//            }
//        });


        return view;

    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK)
//        {
//            Bundle bundle = data.getExtras();
//            bitmap = (Bitmap) bundle.get("data");
//            ProfileImage.setImageBitmap(bitmap);
//            Model.instance.saveImage(UserEmail,bitmap,(URL)->{
//            Model.instance.setUserImageURL(UserEmail,URL,()->{
//                this.ImageURL = URL;
//            });
//
//            });
//        }
//    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_app_menu,menu);
    }

//    private void LoadUserImage()
//    {
//        Model.instance.GetUserById((user)->{
//            if(!user.getImageURL().equals(""))
//            {
//                Picasso.get().load(ImageURL).placeholder(R.drawable.burgerchipsdrinkbackground).into(ProfileImage);
//            }
//        });
//
//    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
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