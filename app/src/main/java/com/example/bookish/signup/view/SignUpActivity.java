package com.example.bookish.signup.view;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.User;
import com.example.bookish.home.view.HomeActivity;
import com.example.bookish.login.view.SignInActivity;

public class SignUpActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passwordEditText;
    private TextView loginTextView;
    private Button signUpButton;
    private LocalDatabaseImpl localDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        localDatabase = new LocalDatabaseImpl(this);
        nameEditText = findViewById(R.id.editTextText);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        signUpButton = findViewById(R.id.button);
        loginTextView = findViewById(R.id.loginTextView);

        loginTextView.setOnClickListener(v ->{
            Intent intent = new Intent(SignUpActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            if (validateInput(name,email,password)) {
                User user = new User(name, password, email);
                localDatabase.insertUser(user);
                Toast.makeText(SignUpActivity.this, "Sign Up Successful!", Toast.LENGTH_SHORT).show();
                saveUserIdToSharedPreferences(user.getUserID());
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); // Optional: finish SignUpActivity to prevent user from going back

            }
        });
    }
    private boolean validateInput(String name, String email,String password) {

        if (name.isEmpty()) {
            nameEditText.setError("Name is required");
            return false;
        }

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

        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters");
            return false;
        }

        return true;
    }
    private void saveUserIdToSharedPreferences(Integer userId) {
        SharedPreferences sharedPreferences = SignUpActivity.this.getSharedPreferences("BookPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("userId", userId != null ? userId : -1);
        editor.putBoolean("isUserLoggedIn", true);
        editor.apply();

        if (userId != null) {
            Log.d("SharedPreferences", "Saved userId: " + userId);
        }
    }
}