package com.example.bookish.favourite.view;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bookish.R;
import com.example.bookish.adapters.BooksRecyclerViewAdapter;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.User;
import com.example.bookish.data.models.UserFavBooks;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;
import com.example.bookish.favourite.viewmodel.FavouriteViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteActivity extends AppCompatActivity {
    private FavouriteViewModel favouriteViewModel;
    private LiveData<List<Book>> favoriteBooksLiveData;
    private int currentUserId;
    private SharedPreferences sharedPreferences;
    private LocalDatabaseImpl localDatabase;
    private RemoteDataSource apiClient;
    private BooksRecyclerViewAdapter adapter;

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favourite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




    }

}