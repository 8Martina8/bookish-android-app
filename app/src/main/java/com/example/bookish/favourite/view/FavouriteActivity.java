package com.example.bookish.favourite.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish.R;
import com.example.bookish.adapters.BooksRecyclerViewAdapter;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.User;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity {
    private int currentUserId;
    private SharedPreferences sharedPreferences;
    private LocalDatabaseImpl localDatabase;
    private RemoteDataSource apiClient;
    private BooksRecyclerViewAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        localDatabase = new LocalDatabaseImpl(this);

        User retrievedUser = localDatabase.getUserByEmail("user@example.com");
        Log.d("TestingDB", "Retrieved user: " + retrievedUser.getEmail());
        List<String> favBooksIDs = localDatabase.getBooksIdsByUserId(retrievedUser.getUserID());
        Log.d("TestingDB", "Favorite Books: " + favBooksIDs);

        ArrayList<Book> favoriteBooksList = new ArrayList<>();
        recyclerView = findViewById(R.id.favrecyclerView);
        adapter = new BooksRecyclerViewAdapter(this, favoriteBooksList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        apiClient = new ApiClient();

        for (String bookID : favBooksIDs) {
            Call<Book> bookCall = apiClient.getBookById(bookID);
            bookCall.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Book book = response.body();
                        favoriteBooksList.add(book);
                        adapter.notifyDataSetChanged(); // Notify adapter of data change

                    } else {
                        Log.e("TestingDB", "Failed to retrieve book details");
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {
                    Log.e("TestingDB", "Error: " + t.getMessage());
                }
            });
        }
    }
}
