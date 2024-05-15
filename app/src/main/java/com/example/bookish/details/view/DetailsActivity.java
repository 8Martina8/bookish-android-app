package com.example.bookish.details.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.Book;
import com.example.bookish.data.models.UserFavBooks;
import com.example.bookish.home.view.HomeActivity;

public class DetailsActivity extends AppCompatActivity {
    TextView txtBookTitle;
    TextView txtAuthorName;
    ImageView imgView;
    TextView txtRating;

    TextView txtPages;
    TextView txtDescription;
    CheckBox checkBoxFav;
    Boolean isFav;
    private int currentUserId;

    private SharedPreferences sharedPreferences;

    private LocalDatabaseImpl localDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Retrieve the Book object from the intent
        Book book = (Book) getIntent().getSerializableExtra("BOOK");

        sharedPreferences = this.getSharedPreferences("BookPrefs", Context.MODE_PRIVATE);
        currentUserId = sharedPreferences.getInt("userId", -1);

        localDatabase = new LocalDatabaseImpl(this);

        txtBookTitle = findViewById(R.id.bookTitleTextView);
        txtAuthorName = findViewById(R.id.authorNameTextView);
        imgView = findViewById(R.id.bookImageView);
        txtRating=findViewById(R.id.reviewTextView);
        txtPages=findViewById(R.id.numberOfPagesTextView);
        txtDescription=findViewById(R.id.descriptionTextView);

        checkBoxFav=findViewById(R.id.checkbox_favourite);

        txtBookTitle.setText(book.getVolumeInfo().getTitle());
        if (book.getVolumeInfo().getAuthors()!=null) {
            txtAuthorName.setText(book.getVolumeInfo().getAuthors().get(0));
        } else {
            txtAuthorName.setText("Unknown Author");

        }
        if(imgView!=null) {
            String modifiedURL = book.getVolumeInfo().getImageLinks().getThumbnail().replace("http://", "https://");
            Glide.with(DetailsActivity.this)
                    .load(modifiedURL)
                    .apply(new RequestOptions()
                            .placeholder(R.drawable.book_placeholder)
                            .error(R.drawable.baseline_broken_image_24))
                    .into(imgView);
        }
        txtRating.setText(String.valueOf(book.getVolumeInfo().getAverageRating()));
        txtPages.setText(String.valueOf(book.getVolumeInfo().getPageCount()));
        if (book.getVolumeInfo().getDescription()!=null && book.getVolumeInfo().getDescription().length()!=0) {
            txtDescription.setText(book.getVolumeInfo().getDescription());
        } else {
            txtDescription.setText("No Description Provided");
        }

        isFav=localDatabase.checkFavBook(currentUserId, book.getId());
        checkBoxFav.setChecked(isFav);

        checkBoxFav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    localDatabase.insertIntoFavBooks(new UserFavBooks(currentUserId, book.getId()));
                } else {
                    localDatabase.deleteFromFavBooks(new UserFavBooks(currentUserId, book.getId()));
                    Toast.makeText(DetailsActivity.this, "The book is removed from favourites", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}