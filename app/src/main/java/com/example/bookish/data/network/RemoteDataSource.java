package com.example.bookish.data.network;

import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;

import retrofit2.Call;
import retrofit2.http.Path;

public interface RemoteDataSource {
    Call<BooksResponse> searchBooks(String query);
    Call<Book> getBookById(String id);

}
