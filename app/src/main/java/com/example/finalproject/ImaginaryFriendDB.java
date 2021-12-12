package com.example.finalproject;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ImaginaryFriend.class}, version = 1 )
public abstract class ImaginaryFriendDB extends RoomDatabase {
    public abstract ImaginaryFriendDAO getImaginaryFriendDao();

}
