package com.example.finalproject;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.URL;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v =  inflater.inflate(R.layout.main_fragment,container,false);
        //get views by Id
        TextView iFName = v.findViewById(R.id.iFName);
        TextView ifAge = v.findViewById(R.id.ifAge);
        TextView ifEmail = v.findViewById(R.id.ifEmail);
        TextView ifCityState = v.findViewById(R.id.ifCityState);
        ImageView image = v.findViewById(R.id.image);



        Bundle extras = getArguments();
        //Set Fragment View Image and Text
        String data = extras.getString("data");Boolean flag = extras.getBoolean("flag");

            String nameString = "";
            String emailString = "";
            String cityString = "";
            String ageString = "";
            String imageString = "";


            //data = title name lastname $ street city state $ email $ age dob $ picture
            int counter = 0;
            for (int i = 0; i < data.toCharArray().length; i++) {

                if (data.toCharArray()[i] == '$') {

                    if (nameString.isEmpty()) {

                        nameString = data.substring(0, i);
                        counter = i;
                    } else if (cityString.isEmpty()) {

                        cityString = data.substring(counter + 1, i);
                        counter = i;
                    } else if (emailString.isEmpty()) {

                        emailString = data.substring(counter + 1, i);
                        counter = i;
                    } else if (ageString.isEmpty()) {

                        ageString = data.substring(counter + 1, i);
                        counter = i;
                    } else if (imageString.isEmpty()) {
                        imageString = data.substring(counter + 1, i);
                        counter = i;
                    }

                }
            }


            iFName.setText(nameString);
            ifAge.setText(ageString);
            ifEmail.setText(emailString);
            ifCityState.setText(cityString);


            image.setImageBitmap(extras.getParcelable("image"));


        return v;

    }
}
