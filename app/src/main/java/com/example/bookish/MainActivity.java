package com.example.bookish;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;

import java.util.List;

import kotlinx.coroutines.GlobalScope;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {
    private LocalDatabaseImpl localDatabase;
    private RemoteDataSource apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialize local db and api client
        localDatabase = new LocalDatabaseImpl(this);
        apiClient=new ApiClient();



        // Test database operations
        testDatabaseOperations();
    }

    private void testDatabaseOperations() {
        // Insert a user
        User user = new User("martina1", "password", "user@example.com");
        localDatabase.insertUser(user);

        //Retrieve a user by email
        User retrievedUser = localDatabase.getUserByEmail("user@example.com");
        Log.d("TestingDB", "Retrieved user: " + retrievedUser.getEmail());

        // Insert a book
//        Book book = new Book("1234567890", new Book.VolumeInfo(), new Book.SaleInfo(),new Book.AccessInfo());
//        localDatabase.insertBook(book);
//
//        // Check if the book exists
//        boolean bookExists = localDatabase.checkBookExists("1234567890");
//        Log.d("MainActivity", "Book exists: " + bookExists);
//
//        // Insert a user-favorite book relation
        UserFavBooks userFavBooks = new UserFavBooks(1, "1234567890");
        UserFavBooks userFavBook2 = new UserFavBooks(1, "feffef");
        UserFavBooks userFavBook3 = new UserFavBooks(1, "wawawa");


        localDatabase.insertIntoFavBooks(userFavBooks);
        localDatabase.insertIntoFavBooks(userFavBook2);
        localDatabase.insertIntoFavBooks(userFavBook3);


        List<String> favBooks = localDatabase.getBooksIdsByUserId(1);
        Log.d("TestingDB", "Favorite Books" + favBooks);

        Call<Book> randomBookCall = apiClient.getBookById("n3vng7gyGCYC");
        randomBookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Book book = response.body();
                    Log.d("TestingDB", "Get Book" + book.getVolumeInfo().getTitle());
                    // Process the book details
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }


        });

    }
}
