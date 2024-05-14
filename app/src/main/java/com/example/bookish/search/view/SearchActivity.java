package com.example.bookish.search.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    private LocalDatabaseImpl localDatabase;
    private RemoteDataSource apiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        localDatabase = new LocalDatabaseImpl(this);
        apiClient=new ApiClient();


        Call<BooksResponse> searchCall = apiClient.searchBooks("Harry");
        searchCall.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    BooksResponse booksResponse = response.body();
                    List<Book> searchedBooks = booksResponse.getItems();
                    // Process the list of books
                    for (Book book : searchedBooks) {
                        Log.d("TestingSearchBooks", "Search Result: "+searchedBooks);
                        // Do something with each book
                    }

                } else {
                    Log.d("TestingSearchBooks", "No Result");
                    // Handle unsuccessful response
                    // For example, display an error message
                }
            }


            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                // Handle network errors or other failures
            }
        });
//            Book book = bookRepo.getBookById(bookID);
//            Log.d("TestingDB", "Favorite Book retreived: "+ book);


        }

}