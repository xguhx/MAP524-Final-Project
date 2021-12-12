package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URL;

public class ImaginaryFriendDetailsActivity extends AppCompatActivity implements NetworkingService.NetworkingListener {
        JsonService jsonService;
        NetworkingService networkingManager;
        Bitmap image;
        Bundle bundle;
        ImaginaryFriend iF;
        ImaginaryFriendManager iFManager;
    FragmentManager fm = getFragmentManager();
    FragmentTransaction transaction = fm.beginTransaction();
    MainFragment firstFragment = new MainFragment();
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imaginary_friend_details);

        iFManager = ((myApp)getApplication()).getIFManager();

        networkingManager = ((myApp)getApplication()).getNetworkingService();
        networkingManager.listener = this;

        jsonService = ((myApp)getApplication()).getJsonService();
        FloatingActionButton fab = findViewById(R.id.fab2); //fab

        String data = "";
        String URLString  = "";

        //Because im Calling Network Manager here,
        //And it will get the image in the bg Thread
        //I need to do everything I need before calling it
        //And on its listener I set the image and do the transaction for the fragment.
        if (getIntent().hasExtra("data")) {
            data = getIntent().getStringExtra("data");
            id = getIntent().getIntExtra("ID", 0);
            bundle = new Bundle();
            //Put extra in the bundle - Strings for Name and Data\
            firstFragment.setArguments(bundle);
            bundle.putString("data", data);
            URLString = jsonService.getImageUrl(data);

            Intent intent = new Intent(this,FavouritesListActivity.class);

            FragmentManager fm = getFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            MainFragment firstFragment = new MainFragment();
            //Add To Favourites Function
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iFManager.deleteImaginaryFriend(id);

                    startActivity(intent);



                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Imaginary Friend Deleted!",
                            Toast.LENGTH_SHORT);
                    toast.show();


                }
            });
            networkingManager.getImage(URLString);

        }

    }

    @Override
    public void dataListener(String jsonString) {

    }

    @Override
    public void imageListener(Bitmap imagea) {
        bundle.putParcelable("image", imagea);

        transaction.replace(R.id.add_remove_area, firstFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
    }
}