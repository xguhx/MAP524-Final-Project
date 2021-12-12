package com.example.finalproject;

import java.util.ArrayList;

public class ImaginaryFriendManager {

    String firstName;
    String data;

    //This needs to return data to main Thread, so it will be implemented in main Thread.
    //public ArrayList<ImaginaryFriend> getFavourites () {}

    //Add New Favourite
    public void addNewImaginaryFriend(ImaginaryFriend iF){
        ImaginaryFriendDBClient.insertNewImaginaryFriend(iF);
    }

    //Delete Favourite
    public void deleteImaginaryFriend(int id){
        ImaginaryFriendDBClient.deleteImaginaryFriend(id);
    }

    //Delete All Favourite
    public void deleteAllImaginaryFriend(){
        ImaginaryFriendDBClient.deleteAllImaginaryFriend();
    }

    public String getfirstName() {
        return firstName;
    }


    public String getData() {
        return data;
    }



}
