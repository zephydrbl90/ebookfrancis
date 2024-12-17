package com.example.ebookfrancis;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button; // Change to Button
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // Declare the Button variables
    private Button profileButton;
    private Button bookListButton;
    private Button purchaseButton;
    private Button signupButton;
    private Button proofofsensorButton;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize Buttons
        profileButton = findViewById(R.id.profile_button);
        bookListButton = findViewById(R.id.book_list_button);
        purchaseButton = findViewById(R.id.purchase_button);
        signupButton = findViewById(R.id.signup_button);
        proofofsensorButton = findViewById(R.id.proofofsensor_button);

        // Set onClickListeners for the Buttons
        profileButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
            startActivity(intent);
        });

        bookListButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, listbook.class);
            startActivity(intent);
        });

        purchaseButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, PurchaseActivity.class);
            startActivity(intent);
        });

        proofofsensorButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SensorActivity.class);
            startActivity(intent);
        });

        // Set onClickListener for Sign Up button
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SignInActivity.class); // Navigate to SignInActivity
            startActivity(intent);
        });
    }
}
