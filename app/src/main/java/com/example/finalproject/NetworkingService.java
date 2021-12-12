package com.example.finalproject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class NetworkingService {
    private String api = "https://randomuser.me/api/";

    public static ExecutorService networkExecutorService = Executors.newFixedThreadPool(4);
    public static Handler networkingHandler = new Handler(Looper.getMainLooper());

    interface NetworkingListener{
        void dataListener(String jsonString);
        void imageListener(Bitmap image);
    }
    public NetworkingListener listener;

    public void getIF(){
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                HttpsURLConnection httpsURLConnection = null;
                try {
                    String jsonData="";

                    URL url = new URL(api);

                    //Open Connection
                    httpsURLConnection = (HttpsURLConnection ) url.openConnection();
                    httpsURLConnection.setRequestMethod("GET");
                    httpsURLConnection.setRequestProperty("Content-Type","application/json");

                    //get input stream
                    InputStream in = httpsURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    //Start reading from input
                    int inputSteamData = 0;
                    while ( (inputSteamData = reader.read()) != -1){// there is data in this stream
                        char current = (char)inputSteamData;
                        jsonData += current;
                    }
                    final String finalData = jsonData;

                    //Set the Handler to send jsonData Back to main thread
                    networkingHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.dataListener(finalData);

                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally{
                    httpsURLConnection.disconnect();
                }


            }
        });
    }

    public void getImage(String urlS) {
        networkExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                Bitmap image = null;

                try {
                    URL url = new URL(urlS);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                } catch (IOException e) {
                    System.out.println(e);
                }

                final Bitmap finalImage = image;

                networkingHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        listener.imageListener(finalImage);

                    }
                });
            }

        });
    }

}
