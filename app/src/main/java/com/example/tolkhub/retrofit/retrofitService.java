package com.example.tolkhub.retrofit;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitService {
//    private static final String TAG = "RetrofitService";
    private Retrofit retrofit;

    public retrofitService() {
        initializeRetrofit();
    }

    private void initializeRetrofit() {
        // Configure Gson with lenient mode
        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.14:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
