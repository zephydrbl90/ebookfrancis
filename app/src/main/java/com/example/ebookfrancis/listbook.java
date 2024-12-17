package com.example.ebookfrancis;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class listbook extends AppCompatActivity {

    private int eightySixQuantity = 0;
    private int harryPotterQuantity = 0;

    private TextView eightySixQuantityTextView;
    private TextView harryPotterQuantityTextView;
    private Button purchaseButton;

    private static final int EIGHTY_SIX_PRICE = 15;
    private static final int HARRY_POTTER_PRICE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        // Initialize Views
        eightySixQuantityTextView = findViewById(R.id.bookEightySixQuantity);
        harryPotterQuantityTextView = findViewById(R.id.bookHarryPotterQuantity);
        ImageButton minusEightySixButton = findViewById(R.id.buttonMinusEightySix);
        ImageButton plusEightySixButton = findViewById(R.id.buttonPlusEightySix);
        ImageButton minusHarryPotterButton = findViewById(R.id.buttonMinusHarryPotter);
        ImageButton plusHarryPotterButton = findViewById(R.id.buttonPlusHarryPotter);
        purchaseButton = findViewById(R.id.purchaseButton);

        // Set up click listeners for Eighty-Six buttons
        minusEightySixButton.setOnClickListener(v -> {
            if (eightySixQuantity > 0) {
                eightySixQuantity--;
                updateQuantityDisplay();
            }
        });

        plusEightySixButton.setOnClickListener(v -> {
            eightySixQuantity++;
            updateQuantityDisplay();
        });

        // Set up click listeners for Harry Potter buttons
        minusHarryPotterButton.setOnClickListener(v -> {
            if (harryPotterQuantity > 0) {
                harryPotterQuantity--;
                updateQuantityDisplay();
            }
        });

        plusHarryPotterButton.setOnClickListener(v -> {
            harryPotterQuantity++;
            updateQuantityDisplay();
        });

        // Set up purchase button click listener
        purchaseButton.setOnClickListener(v -> {
            int totalCost = (eightySixQuantity * EIGHTY_SIX_PRICE) +
                    (harryPotterQuantity * HARRY_POTTER_PRICE);

            Toast.makeText(listbook.this, "Total cost: $" + totalCost, Toast.LENGTH_SHORT).show();
        });

        // Initialize quantities in the display
        updateQuantityDisplay();
    }

    private void updateQuantityDisplay() {
        eightySixQuantityTextView.setText(String.valueOf(eightySixQuantity));
        harryPotterQuantityTextView.setText(String.valueOf(harryPotterQuantity));
    }
}
