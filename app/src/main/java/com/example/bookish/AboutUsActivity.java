package com.example.bookish;


import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bookish.R;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        // Set up the content text
        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        String description = "Welcome to MyBookApp!\n\n" +
                "Our Mission:\n" +
                "At MyBookApp, we are passionate about bringing the joy of reading to everyone. " +
                "Our mission is to make it easy for book lovers to find, read, and cherish books of all genres.\n\n" +
                "Features:\n" +
                "- Discover a vast collection of books\n" +
                "- Add books to your favorites list\n" +
                "- Get personalized book recommendations\n" +
                "- Rate and review books\n" +
                "- Share your favorite books with friends\n\n" +
                "Our Story:\n" +
                "Founded in 2023, MyBookApp started as a small project by a group of book enthusiasts. " +
                "We wanted to create a platform where book lovers could easily find and share their favorite reads. " +
                "Today, we are proud to serve a growing community of readers from around the world.\n\n" +
                "Join Us:\n" +
                "Whether you are an avid reader or just getting started, we invite you to join our community. " +
                "Happy reading!";
        descriptionTextView.setText(description);
    }
}
