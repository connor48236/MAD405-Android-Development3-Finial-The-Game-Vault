package com.example.lab15and17.API;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class GameSingleton {

    public static GameSingleton instance;
    private RequestQueue requestQueue;
    private static Context context;

    private GameSingleton(Context context){
        this.context = context;
    }

    public static GameSingleton getInstance(Context context){
        if (instance == null){
            instance = new GameSingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }

        return requestQueue;
    }
}
