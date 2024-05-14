package com.example.bookish.repo;

import android.util.Log;

import com.example.bookish.data.local.LocalDatabase;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;
import com.example.bookish.data.network.RemoteDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookRepoImpl implements BookRepo {

    private final LocalDatabase localDataSource;
    private final RemoteDataSource remoteDataSource;


    public BookRepoImpl(LocalDatabase localDatabase, RemoteDataSource remoteDataSource) {
        this.localDataSource = localDatabase;
        this.remoteDataSource=remoteDataSource;
    }

    @Override
    public void insertUser(User user) {
        localDataSource.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        localDataSource.deleteUser(user);
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return localDataSource.getUserByEmail(userEmail);
    }

    @Override
    public boolean checkFavBook(int userID, String bookID) {
        return localDataSource.checkFavBook(userID, bookID);
    }

    @Override
    public void insertIntoFavBooks(UserFavBooks userFavBooks) {
        localDataSource.insertIntoFavBooks(userFavBooks);
    }

    @Override
    public void deleteFromFavBooks(UserFavBooks userFavBooks) {
        localDataSource.deleteFromFavBooks(userFavBooks);
    }

    @Override
    public List<String> getBooksIdsByUserId(int userID) {
        return localDataSource.getBooksIdsByUserId(userID);
    }

    // RemoteDataSource methods
    @Override
    public Call<BooksResponse> searchBooks(String query) {
        return remoteDataSource.searchBooks(query);
    }

    @Override
    public Book getBookById(String bookId) {
        Call<Book> bookCall = remoteDataSource.getBookById(bookId);
        final Book[] book = new Book[1];
        bookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                     book[0] = response.body();
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }


        });
        return book[0];
    }
}
