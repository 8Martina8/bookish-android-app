package com.example.bookish.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.bookish.data.db.BooksDao;
import com.example.bookish.data.db.UsersDao;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {User.class, UserFavBooks.class}, version = 1)
public abstract class UsersDB extends RoomDatabase {

    public abstract UsersDao getDao();
    public abstract BooksDao getBooksDao();

    private static volatile UsersDB INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    //To make database request run in background
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static UsersDB getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (UsersDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    UsersDB.class,
                                    "users_database"
                            )
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
