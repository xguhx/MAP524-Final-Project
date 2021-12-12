package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonService {
    ImaginaryFriendManager iFManager;


    public ImaginaryFriend getImaginaryFriendData(String jsonString) {
        ImaginaryFriend iFData = new ImaginaryFriend();

        try {
            String data = "";

            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray dataArray = jsonObject.getJSONArray("results");
            JSONObject iFDataObject = dataArray.getJSONObject(0); //get objects by index

            //get Name Info
            JSONObject IFNames = iFDataObject.getJSONObject("name");

            String titleValue = IFNames.getString("title");
            String nameValue = IFNames.getString("first");
            String lastNameValue = IFNames.getString("last");

            //get Location Info
            JSONObject IFLocation = iFDataObject.getJSONObject("location");

            JSONObject streetValues = IFLocation.getJSONObject("street");
            String streetValue = streetValues.getString("number") +" "+ streetValues.getString("name");

            String stateValue = IFLocation.getString("state");
            String cityValue = IFLocation.getString("city");

            //get email Info
            String emailValue = iFDataObject.getString("email");

            //get Age Info
            JSONObject IFDob = iFDataObject.getJSONObject("dob");

            String dateValue = IFDob.getString("date");
            String ageValue = IFDob.getString("age");

            //get Image Info
            JSONObject IFPicture = iFDataObject.getJSONObject("picture");
            String pictureValue = IFPicture.getString("large");


            Bitmap myBitmap = null;
            try {
                URL url = new URL(pictureValue);
                myBitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            } catch (IOException e) {
                System.out.println(e);
            }


            //Build Data String here
            data = titleValue + ". " + nameValue + " " + lastNameValue + "$" + streetValue + ", " + cityValue + " - "
                    + stateValue + "$" + emailValue + "$" + ageValue + " - " + dateValue + "$" + pictureValue;


            iFData = new ImaginaryFriend(nameValue + " " + lastNameValue,
                    data,
                    pictureValue,
                    myBitmap);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return iFData;
    }



    public String getImageUrl(String data){
        String nameString = "";
        String emailString= "";
        String cityString = "";
        String ageString = "";
        String imageString = "";


        //data = title name lastname $ street city state $ email $ age dob $ picture
        int counter = 0;
        for (int i = 0; i < data.toCharArray().length; i++) {

            if (data.toCharArray()[i] == '$') {

                if(nameString.isEmpty()) {

                    nameString = data.substring(0, i);
                    counter = i;
                }else if (cityString.isEmpty()) {

                    cityString = data.substring(counter+1, i );
                    counter = i;
                }else if (emailString.isEmpty()){

                    emailString = data.substring(counter+1, i );
                    counter = i;
                }else if (ageString.isEmpty()){

                    ageString = data.substring(counter+1, i );
                    imageString = data.substring(i+1, data.toCharArray().length);
                    counter = i;
                }


            }
        }


        return  imageString;
    }

}