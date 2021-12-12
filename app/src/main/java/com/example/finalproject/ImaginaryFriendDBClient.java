package com.example.finalproject;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import androidx.room.Room;


import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class ImaginaryFriendDBClient {
    static ImaginaryFriendDB dbClient;
    static Context dbContext;


    interface DataBaseListener{
        //Implement this interface in any activity that will use this query
        public void ListOfImaginaryFriendsListener(List<ImaginaryFriend> iFList);
        public void ImaginaryFriendsListener(ImaginaryFriend iF);

    }

   static DataBaseListener listener;

    public static final ExecutorService dbExectuor = Executors.newFixedThreadPool(4);
    public static Handler handler = new Handler(Looper.getMainLooper()); //Get a pointer to main Thread
    //A different option from Handler would be creating a Thread object

    ImaginaryFriendDBClient(Context context){
        dbContext = context;
        dbClient= Room.databaseBuilder(context, ImaginaryFriendDB.class, "database-ImaginaryFriend").build();
    }

    //Make sure we create only one instance of the Database
    //Singleton Design Pattern, because db is an expansive resource
    public static ImaginaryFriendDB getDbClient(){
        if(dbClient == null){
            dbClient = new ImaginaryFriendDBClient(dbContext).dbClient;
        }
        return dbClient;
    }

    //This happens on bg thread.
    public static void insertNewImaginaryFriend(ImaginaryFriend newImaginaryFriend){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getImaginaryFriendDao().insert(newImaginaryFriend);
            }
        });
    }

    //This happens on bg thread.
    public static void deleteImaginaryFriend(int id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getImaginaryFriendDao().deleteImaginaryFriend(id);
            }
        });
    }

    //This happens on bg thread.
    public static void deleteAllImaginaryFriend(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                dbClient.getImaginaryFriendDao().deleteAllImaginaryFriend();
            }
        });
    }



    //To send back data, I need Runnable inside Runnable
    public static void getAllImaginaryFriends(){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
                List<ImaginaryFriend> listOfIF = dbClient.getImaginaryFriendDao().getAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.ListOfImaginaryFriendsListener(listOfIF);

                    }
                });
            }
        });
    }

    public static void getImaginaryFriendsbyName(String name){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
               List<ImaginaryFriend> iF = dbClient.getImaginaryFriendDao().findByName(name);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.ListOfImaginaryFriendsListener(iF);

                    }
                });
            }
        });
    }

    public static void getImaginaryFriendsbyId(int Id){
        dbExectuor.execute(new Runnable() {
            @Override
            public void run() {
                //Handler and looper will give the data back to main Thread
                ImaginaryFriend iF = dbClient.getImaginaryFriendDao().getImaginaryFriendById(Id);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //On the second Runnable, I need an Interface to send data to main Thread
                        listener.ImaginaryFriendsListener(iF);

                    }
                });
            }
        });
    }



}
