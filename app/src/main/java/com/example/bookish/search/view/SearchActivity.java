package com.example.bookish.search.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish.R;
import com.example.bookish.adapters.BooksRecyclerViewAdapter;
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

    private RecyclerView recyclerView;
    private BooksRecyclerViewAdapter adapter;
    private ArrayList<Book> books;

    private Button searchBtn;
    private EditText searchQueryET;

    @SuppressLint("NotifyDataSetChanged")
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
        apiClient = new ApiClient();

        books = new ArrayList<>();
        adapter = new BooksRecyclerViewAdapter(this, books);
        recyclerView = findViewById(R.id.searchBooksRecyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        searchQueryET = findViewById(R.id.searchQueryET);

        searchBtn = findViewById(R.id.searchBooksBtn);
        searchBtn.setOnClickListener(v -> {
            String query = searchQueryET.getText().toString();

            searchBooks(query);
        });
    }

    private void searchBooks(String query) {
        Call<BooksResponse> searchCall = apiClient.searchBooks(query);
        searchCall.enqueue(new Callback<BooksResponse>() {
            @Override
            public void onResponse(Call<BooksResponse> call, Response<BooksResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BooksResponse booksResponse = response.body();
                    List<Book> searchedBooks = booksResponse.getItems();

                    books = (ArrayList<Book>) searchedBooks;
                    updateSearchResults();
                } else {
                    toast("No Result!");
                }
            }

            @Override
            public void onFailure(Call<BooksResponse> call, Throwable t) {
                toast("API Call Failed!");
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void updateSearchResults() {
        adapter.setBooks(books);
        adapter.notifyDataSetChanged();
    }

    private void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}