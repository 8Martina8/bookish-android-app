package com.example.bookish.data.network;

import com.example.bookish.data.models.BooksResponse;

import retrofit2.Call;

public interface RemoteDataSource {
    Call<BooksResponse> searchBooks(String query);
}
