package com.example.bookish.data.local;

import android.content.Context;

import com.example.bookish.data.db.BooksDao;
import com.example.bookish.data.db.UsersDB;
import com.example.bookish.data.db.UsersDao;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;

import java.util.List;

public class LocalDatabaseImpl implements LocalDatabase{
    private final UsersDao usersDao;
    private final BooksDao booksDao;
    public LocalDatabaseImpl(Context context) {
        UsersDB db = UsersDB.getInstance(context);
        usersDao = db.getDao();
        booksDao = db.getBooksDao();
    }
    @Override
    public void insertUser(User user) {
         usersDao.insertUser(user);
    }

    @Override
    public void deleteUser(User user) {
        usersDao.deleteUser(user);

    }

    @Override
    public User getUserByEmail(String userEmail) {
        return usersDao.getUserByEmail(userEmail);
    }

    @Override
    public boolean checkFavBook(int userID, String bookID) {
        return booksDao.checkFavBook(userID,bookID);
    }

//    @Override
//    public UserWithFavBooks getBooksWithUser(int userId) {
//        return booksDao.getBooksWithUser(userId);
//    }

    @Override
    public void insertIntoFavBooks(UserFavBooks userFavBooks) {
         booksDao.insertIntoFavBooks(userFavBooks);
    }

//    @Override
//    public void insertBook(Book book) {
//        booksDao.insertBook(book);
//
//    }
//
//    @Override
//    public void deleteBook(Book book) {
//        booksDao.deleteBook(book);
//    }

    @Override
    public void deleteFromFavBooks(UserFavBooks userFavBooks) {
        booksDao.deleteFromFavBooks(userFavBooks);
    }

    @Override
    public List<String> getBooksIdsByUserId(int userID) {
        return booksDao.getBookIdsByUserId(userID);
    }

//    @Override
//    public boolean checkBookExists(String bookID) {
//        return booksDao.checkBookExists(bookID);
//    }
}
