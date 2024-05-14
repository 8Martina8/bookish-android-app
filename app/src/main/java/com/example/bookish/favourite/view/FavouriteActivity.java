package com.example.bookish.favourite.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.UserFavBooks;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;
import com.example.bookish.favourite.viewmodel.FavouriteViewModel;
import com.example.bookish.repo.BookRepo;
import com.example.bookish.repo.BookRepoImpl;

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
//        //get current user id
//        sharedPreferences = this.getSharedPreferences("BookPrefs", Context.MODE_PRIVATE);
//        currentUserId = sharedPreferences.getInt("userId", -1);
//        favouriteViewModel = new ViewModelProvider(this).get(FavouriteViewModel.class);
//        // Get the LiveData from ViewModel
//        favoriteBooksLiveData = favouriteViewModel.getFavoriteBooks(1);
//
//        // Observe the LiveData
//        favoriteBooksLiveData.observe(this, new Observer<List<Book>>() {
//            @Override
//            public void onChanged(List<Book> books) {
//                Log.d("TestingFavoritesPage", "onChanged: "+favoriteBooksLiveData);
//                // Update UI with the new list of favorite books
//                // For example, update RecyclerView adapter
//            }
//        });

//        UserFavBooks userFavBooks = new UserFavBooks(1, "KeteAQAAQBAJ");
//        UserFavBooks userFavBook2 = new UserFavBooks(1, "8P8nDwAAQBAJ");
//        UserFavBooks userFavBook3 = new UserFavBooks(1, "iO5pApw2JycC");
        localDatabase = new LocalDatabaseImpl(this);
        apiClient=new ApiClient();

//        localDatabase.insertIntoFavBooks(userFavBooks);
//        localDatabase.insertIntoFavBooks(userFavBook2);
//        localDatabase.insertIntoFavBooks(userFavBook3);


        //BookRepo bookRepo=new BookRepoImpl(localDatabase,apiClient);
        List<String> favBooksIDs = localDatabase.getBooksIdsByUserId(1);
        Log.d("TestingDB", "Favorite Books" + favBooksIDs);

        List<Book> favoriteBooksList = new ArrayList<>();
        for (String bookID : favBooksIDs) {
            Call<Book> bookCall = apiClient.getBookById(bookID);
            bookCall.enqueue(new Callback<Book>() {
                @Override
                public void onResponse(Call<Book> call, Response<Book> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Book book = response.body();
                        if (book != null) {
                            favoriteBooksList.add(book);
                        }
                        Log.d("TestingDB", "Get Book" + book.getVolumeInfo().getTitle());
                        Log.d("TestingBooksList", "onResponse: "+favoriteBooksList);
                        // Process the book details
                    } else {
                        // Handle unsuccessful response
                    }
                }

                @Override
                public void onFailure(Call<Book> call, Throwable t) {

                }


            });
//            Book book = bookRepo.getBookById(bookID);
//            Log.d("TestingDB", "Favorite Book retreived: "+ book);


        }
        Log.d("TestingFavoritesPage", "onChanged: "+favoriteBooksList);
    }

}