package com.example.finalproject;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class ImaginaryFriend {
    @PrimaryKey(autoGenerate = true)
    public int id;

    //Options:
    //Only one String member, which will hold all the data from the API, separate by $.
    //Many String members, each will hold a specific data from the API.

    //this will be used to search
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "data")
    public String data;

    @ColumnInfo(name = "url")
    public String url;

    @Ignore
    public Bitmap image;

    public ImaginaryFriend(){
        this.firstName = "Empty Imaginary Friend";
        this.data = "No Data";
        this.url = "No url";
    }

    public ImaginaryFriend(String name, String data, String url, Bitmap image){
        this.firstName = name;
        this.data = data;
        this.url = url;
        this.image = image;
    }

    public ImaginaryFriend(ImaginaryFriend c) {
        this.firstName = c.firstName;
        this.data = c.data;
        this.url = c.url;
        this.image = c.image;
    }

    public String getFirstName(){
        return firstName;
    }

    public String getData(){
        return data;
    }

    public int getId(){
        return id;
    }


    public String getURL(){
        return url;
    }

    public Bitmap getImage(){
        return image;
    }


}
