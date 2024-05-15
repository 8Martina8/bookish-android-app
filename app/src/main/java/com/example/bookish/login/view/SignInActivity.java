package com.example.bookish.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.bookish.R;
import com.example.bookish.custom.views.HalfPressableSentence;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.User;
import com.example.bookish.favourite.MusicService;
import com.example.bookish.home.view.HomeActivity;
import com.example.bookish.signup.view.SignUpActivity;

public class SignInActivity extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button signInButton;
    private HalfPressableSentence halfPressableSentence;

    private LocalDatabaseImpl localDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        localDatabase = new LocalDatabaseImpl(this);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        signInButton = findViewById(R.id.button);
        halfPressableSentence = findViewById(R.id.halfPressableSentence);

        halfPressableSentence.setClickableText("Sign Up", v -> {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });

        signInButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (validateInput(email, password)) {
                User retrievedUser = localDatabase.getUserByEmail(email);

                if (retrievedUser != null && retrievedUser.getPassword().equals(password)) {
                    Toast.makeText(SignInActivity.this, "Sign In Successful!", Toast.LENGTH_SHORT).show();
                    saveUserIdToSharedPreferences(user.getUserID());
                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SignInActivity.this, "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    private void saveUserIdToSharedPreferences(Integer userId) {
        SharedPreferences sharedPreferences = SignInActivity.this.getSharedPreferences("BookPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId != null ? userId : -1);
        editor.putBoolean("isUserLoggedIn", true);
        editor.apply();

        if (userId != null) {
            Log.d("SharedPreferences", "Saved userId: " + userId);
        }
    }
    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email is required");
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Invalid email address");
            return false;
        }

        if (password.isEmpty()) {
            passwordEditText.setError("Password is required");
            return false;
        }

        return true;
    }

}