package com.example.bookish.signup.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bookish.R;
import com.example.bookish.data.local.LocalDatabaseImpl;
import com.example.bookish.data.models.User;
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
}