package com.example.bookish.repo;

import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;

import java.util.List;

import retrofit2.Call;

public interface BookRepo {

    Call<BooksResponse> searchBooks(String query);

    Call<Book> getBookById(String id);

    void insertUser(User user);

    void deleteUser(User user);

    User getUserByEmail(String userEmail);

    boolean checkFavBook(int userID, String bookID);

    void insertIntoFavBooks(UserFavBooks userFavBooks);

    void deleteFromFavBooks(UserFavBooks userFavBooks);

    List<String> getBooksIdsByUserId(int userID);
}