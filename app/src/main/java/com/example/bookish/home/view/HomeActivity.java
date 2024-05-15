package com.example.bookish.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.LinearLayoutManager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookish.BookItemAdapter;
import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.BooksResponse;
import com.example.bookish.data.network.ApiClient;
import com.example.bookish.data.network.RemoteDataSource;
import com.example.bookish.details.view.DetailsActivity;

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
    TextView txtBookTitle;
    TextView txtAuthorName;
    ImageView imgView;
    TextView txtRating;
    CardView mainBookCard;
    private static final String[] FAMOUS_BOOK_TITLES = {
            "To Kill a Mockingbird",
            "The Great Gatsby",
            "1984",
            "The Catcher in the Rye",
            "Pride and Prejudice",
            "The Hobbit",
            "The Lord of the Rings",
            "Harry Potter"
            // Add more famous book titles
    };
    private String getRandomBookTitle() {
        Random random = new Random();
        int index = random.nextInt(FAMOUS_BOOK_TITLES.length);
        return FAMOUS_BOOK_TITLES[index];
    }

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
        txtBookTitle = findViewById(R.id.txt_Mbook_title);
        txtAuthorName = findViewById(R.id.txt_month_author);
        imgView = findViewById(R.id.img_month_book);
        txtRating=findViewById(R.id.txt_month_rating);
        mainBookCard=findViewById(R.id.cardView);

        Call<Book> bookCall = apiClient.getBookById("FmwwDQAAQBAJ");
        bookCall.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Book book = response.body();
                    if (book != null) {
                        txtBookTitle.setText(book.getVolumeInfo().getTitle());
                        txtAuthorName.setText(book.getVolumeInfo().getAuthors().get(0));
                        String modifiedURL=book.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://");
                        Glide.with(HomeActivity.this)
                                .load(modifiedURL)
                                .apply(new RequestOptions()
                                        .placeholder(R.drawable.book_placeholder)
                                        .error(R.drawable.baseline_broken_image_24))
                                .into(imgView);

//                        // Set item click listener
//                        mainBookCard.setOnClickListener(v -> {
//                            if (onItemClickListener != null) {
//                                // Invoke onItemClick with the current data item
//                                onItemClickListener.onItemClick(currentBook);

                    }
                } else {
                    // Handle unsuccessful response
                }
            }
            @Override
            public void onFailure(Call<Book> call, Throwable t) {

            }


        });


        //char randLetter = (char) ('A' + new Random().nextInt(26));
        String randomBookTitle = getRandomBookTitle();
        Call<BooksResponse> homeCall = apiClient.searchBooks(randomBookTitle);
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
                    bookItemAdapter.setOnItemClickListener(new BookItemAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Book book) {
                            Intent intent = new Intent(HomeActivity.this, DetailsActivity.class);
                            intent.putExtra("BOOK", book);
                            HomeActivity.this.startActivity(intent);
                        }

                        });

                    for (Book book : searchedBooks) {
                        //Log.d("TestingSearchBooks", "Search Result: "+searchedBooks);
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
