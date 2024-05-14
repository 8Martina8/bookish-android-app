package com.example.bookish.home.view;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.example.bookish.BookItemAdapter;
import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {
    private LocalDatabaseImpl localDatabase;
    private RemoteDataSource apiClient;
    RecyclerView recyclerView;
    BookItemAdapter bookItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        localDatabase = new LocalDatabaseImpl(this);
        apiClient=new ApiClient();

        recyclerView=findViewById(R.id.book_rv);

        char randLetter = (char) ('A' + new Random().nextInt(26));
        Call<BooksResponse> homeCall = apiClient.searchBooks(String.valueOf(randLetter));
        homeCall.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Handle successful response
                    BooksResponse booksResponse = response.body();
                    List<Book> searchedBooks = booksResponse.getItems();
                    bookItemAdapter=new BookItemAdapter(searchedBooks);
                    recyclerView.setAdapter(bookItemAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this, RecyclerView.HORIZONTAL, false));
                    // Process the list of books
                    for (Book book : searchedBooks) {
                        Log.d("TestingSearchBooks", "Search Result: "+searchedBooks);
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
