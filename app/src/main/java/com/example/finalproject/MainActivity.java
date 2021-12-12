package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;

public class MainActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {
    ImaginaryFriendDBClient dbClient;
    ImaginaryFriendManager iFManager;
    NetworkingService networkingManager;
    Boolean checkFavourite;
    ImaginaryFriend iF;
    JsonService jsonService;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_favorite);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        checkFavourite = false;

         dbClient = new ImaginaryFriendDBClient(this);

        iFManager = ((myApp)getApplication()).getIFManager();
        networkingManager = ((myApp)getApplication()).getNetworkingService();
        networkingManager.listener = this;
        jsonService = ((myApp)getApplication()).getJsonService();


        //fetch data from api

        networkingManager.getIF();



        //Add To Favourites Function
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!checkFavourite) {
                    //get new Friend Name and Data from iFManager
                    iFManager.addNewImaginaryFriend(new ImaginaryFriend(iF));
                    checkFavourite = true;
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Imaginary Friend Favorited!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "This Imaginary Friend is already Favorited!",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
    }

    private void makeFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        MainFragment firstFragment = new MainFragment();

        bundle = new Bundle();
        //Put extra in the bundle - Strings for Name and Data
        firstFragment.setArguments(bundle);
        bundle.putString("name", iF.getFirstName());
        bundle.putString("data", iF.getData());
        bundle.putParcelable("image", iF.getImage());

        transaction.replace(R.id.add_remove_area, firstFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }

    public void fetchImaginaryFriend(View view) {
        networkingManager.getIF(); //this will call dataListener
        //makeFragment();
    }

    @Override
    public void dataListener(String jsonString) { //getIF
        checkFavourite = false;
        iF = new ImaginaryFriend(jsonService.getImaginaryFriendData(jsonString));
        makeFragment();

    }

    @Override
    public void imageListener(Bitmap image) {
    }

    //Creating the Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    //Setting Listeners to Menu options
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.favorites: {
                Intent intent = new Intent(this,FavouritesListActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.resetF: {
                iFManager.deleteAllImaginaryFriend();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Favorites Deleted",
                        Toast.LENGTH_SHORT);
                toast.show();
                break;
            }
        }
        return true;
    }

}