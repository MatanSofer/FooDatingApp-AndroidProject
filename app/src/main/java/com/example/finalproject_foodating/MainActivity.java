package com.example.finalproject_foodating;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.finalproject_foodating.model.Model;
import com.example.finalproject_foodating.model.ModelFireBase;
import com.example.finalproject_foodating.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    NavController navController;
   // static String userId=ModelFireBase.getCurrentUser();
 //   static User userObj=ModelFireBase.getCurrentUserObj();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //check if there is user that is already logged in from the device
        if(ModelFireBase.getCurrentUser() != null){
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.MainActivity_fragmentContainerView);
            navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this,navController);
            navController.navigate(R.id.mainAppFragment);
        }
        else{
            NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.MainActivity_fragmentContainerView);
            navController = navHostFragment.getNavController();
            NavigationUI.setupActionBarWithNavController(this,navController);
        }



    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                navController.navigateUp();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    //public static String getuserId(){return userId;}
    //public static User getuserObj(){return userObj;}
}