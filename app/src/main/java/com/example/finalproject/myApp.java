package com.example.finalproject;


import android.app.Application;

public class myApp extends Application {
    private ImaginaryFriendManager iFManager = new ImaginaryFriendManager();

    public ImaginaryFriendManager getIFManager() {
        return iFManager;
    }

    private NetworkingService networkingService = new NetworkingService();

    public JsonService getJsonService() {
        return jsonService;
    }

    private JsonService jsonService = new JsonService();


    public NetworkingService getNetworkingService() {
        return networkingService;
    }

}
