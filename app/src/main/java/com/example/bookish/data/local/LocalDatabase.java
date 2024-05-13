package com.example.bookish.data.local;

import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;

import java.util.List;

public interface LocalDatabase {
    void insertUser(User user);

    void deleteUser(User user);

    User getUserByEmail(String userEmail);

    boolean checkFavBook(int userID, String bookID);

//    UserWithFavBooks getBooksWithUser(int userId);

    void insertIntoFavBooks(UserFavBooks userFavBooks);

//    void insertBook(Book book);
//
//    void deleteBook(Book book);

    void deleteFromFavBooks(UserFavBooks userFavBooks);
    List<String> getBooksIdsByUserId(int userID);

//    boolean checkBookExists(String bookID);
}
