package com.example.ebookfrancis;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PurchaseActivity extends AppCompatActivity {

    private ImageView eightySixCover;
    private TextView eightySixTitle, eightySixDescription, eightySixStrengths, eightySixWeaknesses;
    private ImageView harryPotterCover;
    private TextView harryPotterTitle, harryPotterDescription, harryPotterStrengths, harryPotterWeaknesses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);

        // Initialize Views for Eighty-Six
        eightySixCover = findViewById(R.id.eightySixCover);
        eightySixTitle = findViewById(R.id.eightySixTitle);
        eightySixDescription = findViewById(R.id.eightySixDescription);
        eightySixStrengths = findViewById(R.id.eightySixStrengths);
        eightySixWeaknesses = findViewById(R.id.eightySixWeaknesses);

        // Initialize Views for Harry Potter
        harryPotterCover = findViewById(R.id.harryPotterCover);
        harryPotterTitle = findViewById(R.id.harryPotterTitle);
        harryPotterDescription = findViewById(R.id.harryPotterDescription);
        harryPotterStrengths = findViewById(R.id.harryPotterStrengths);
        harryPotterWeaknesses = findViewById(R.id.harryPotterWeaknesses);

        // Set the data for Eighty-Six book
        eightySixTitle.setText("Eighty-Six");
        eightySixDescription.setText("A thrilling story of a boy who is a soldier.");
        eightySixStrengths.setText("- Exciting and gripping story\n- Relatable characters\n- Strong world-building");
        eightySixWeaknesses.setText("- Some pacing issues\n- Minor plot holes");

        // Set the cover image for Eighty-Six (Replace with your actual drawable)
        eightySixCover.setImageResource(R.drawable.img_1);

        // Set the data for Harry Potter book
        harryPotterTitle.setText("Harry Potter");
        harryPotterDescription.setText("A boy with a mighty interest in magic who goes to an academy to learn more.");
        harryPotterStrengths.setText("- Imaginative magical world\n- Engaging characters\n- Timeless themes");
        harryPotterWeaknesses.setText("- Overly familiar tropes in places\n- Some parts are slow-paced");

        // Set the cover image for Harry Potter (Replace with your actual drawable)
        harryPotterCover.setImageResource(R.drawable.img_2);
    }
}
