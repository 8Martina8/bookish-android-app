package com.example.bookish.data.network;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private static final Gson gson = new GsonBuilder().serializeNulls().create();
    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://www.googleapis.com/books/v1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static Retrofit getRetrofit() {
        return retrofit;
    }
}


