package com.example.bookish.data.network;

import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;

import retrofit2.Call;

public class ApiClient implements RemoteDataSource{
    private static final ApiService apiService = RetrofitHelper.getRetrofit().create(ApiService.class);
    @Override
    public Call<BooksResponse> searchBooks(String query) {
        return apiService.searchBooks(query);
    }

    @Override
    public Call<Book> getBookById(String id) {
        return apiService.getBookById(id);
    }
}
