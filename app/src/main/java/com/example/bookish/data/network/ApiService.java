package com.example.bookish.data.network;

import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("volumes")
    Call<BooksResponse> searchBooks(@Query("q") String query);

    @GET("volumes/{id}")
    Call<Book> getBookById(@Path("id") String id);
}
