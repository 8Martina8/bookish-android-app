package com.example.bookish.data.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;

import java.util.List;

@Dao
public interface BooksDao {

    @Query("SELECT EXISTS(SELECT * FROM UserFavBooks WHERE userID = :userID AND bookID = :bookID) ")
    boolean checkFavBook(int userID, String bookID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertIntoFavBooks(UserFavBooks userFavBooks);

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertBook(Book book);
//
//    @Delete
//    void deleteBook(Book book);

    @Delete
    void deleteFromFavBooks(UserFavBooks userFavBooks);

//    @Query("SELECT * FROM User WHERE userID = :userId")
//    UserWithFavBooks getBooksWithUser(int userId);

//    @Query("SELECT EXISTS(SELECT * FROM Book WHERE id = :bookID) ")
//    boolean checkBookExists(String bookID);

    @Query("SELECT bookID FROM UserFavBooks WHERE userID = :userID")
    List<String> getBookIdsByUserId(int userID);
}
