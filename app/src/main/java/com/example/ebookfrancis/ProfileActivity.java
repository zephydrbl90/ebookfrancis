package com.example.ebookfrancis;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    // UI Components
    private EditText editName, editStatus;
    private Button btnTakePicture, btnSelectGenres, btnSaveProfile;
    private TextView txtSelectedGenres, txtSavedName, txtSavedStatus, txtSavedGenres;
    private ImageView imgProfilePicture, imgSavedProfilePicture;

    // Genre Selection Data
    private final String[] genres = {
            "Fantasy", "Science Fiction", "Mystery", "Thriller", "Horror", "Historical Fiction",
            "Romance", "Adventure", "Contemporary Fiction", "Literary Fiction", "Dystopian",
            "Post-Apocalyptic", "Magical Realism", "Paranormal", "Gothic Fiction", "Humor/Satire",
            "Coming-of-Age"
    };
    private boolean[] checkedGenres;
    private ArrayList<String> selectedGenres = new ArrayList<>();
    private static final int MAX_GENRE_SELECTION = 3;

    // Camera Request Code
    private static final int CAMERA_REQUEST_CODE = 111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize UI Components
        editName = findViewById(R.id.name);
        editStatus = findViewById(R.id.status);
        btnSelectGenres = findViewById(R.id.btn_select_genres);
        txtSelectedGenres = findViewById(R.id.selected_genres);
        btnTakePicture = findViewById(R.id.btn_take_picture);
        btnSaveProfile = findViewById(R.id.save_button);
        imgProfilePicture = findViewById(R.id.img_view);

        // Saved profile data UI components
        txtSavedName = findViewById(R.id.saved_name);
        txtSavedStatus = findViewById(R.id.saved_status);
        txtSavedGenres = findViewById(R.id.saved_genres);
        imgSavedProfilePicture = findViewById(R.id.saved_profile_picture);

        // Initialize Genre Selection
        checkedGenres = new boolean[genres.length];

        // Handle Genre Selection Button Click
        btnSelectGenres.setOnClickListener(v -> openGenreSelectionDialog());

        // Handle Take Picture Button Click
        btnTakePicture.setEnabled(false);
        checkCameraPermission();

        btnTakePicture.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(intent, CAMERA_REQUEST_CODE);
            } else {
                Toast.makeText(ProfileActivity.this, "No camera app found", Toast.LENGTH_SHORT).show();
            }
        });

        // Handle Save Profile Button Click
        btnSaveProfile.setOnClickListener(v -> saveProfileData());
    }

    // Open Multi-Select Dialog for Genres
    private void openGenreSelectionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Up to 3 Genres");

        builder.setMultiChoiceItems(genres, checkedGenres, (dialog, which, isChecked) -> {
            if (isChecked) {
                if (selectedGenres.size() < MAX_GENRE_SELECTION) {
                    selectedGenres.add(genres[which]);
                } else {
                    // Prevent more selections
                    checkedGenres[which] = false;
                    ((AlertDialog) dialog).getListView().setItemChecked(which, false);
                    Toast.makeText(this, "You can only select up to 3 genres!", Toast.LENGTH_SHORT).show();
                }
            } else {
                selectedGenres.remove(genres[which]);
            }
        });

        builder.setPositiveButton("OK", (dialog, which) -> updateSelectedGenres());
        builder.setNegativeButton("Cancel", null);

        builder.create().show();
    }

    // Update TextView with Selected Genres
    private void updateSelectedGenres() {
        if (selectedGenres.isEmpty()) {
            txtSelectedGenres.setText("Selected Genres: None");
        } else {
            txtSelectedGenres.setText("Selected Genres: " + String.join(", ", selectedGenres));
        }
    }

    // Save Profile Data and Display Saved Data
    private void saveProfileData() {
        String name = editName.getText().toString().trim();
        String status = editStatus.getText().toString().trim();

        if (name.isEmpty() || status.isEmpty() || selectedGenres.isEmpty()) {
            Toast.makeText(this, "Please fill all fields and select up to 3 genres!", Toast.LENGTH_SHORT).show();
        } else {
            // Save the profile data into the saved profile section
            txtSavedName.setText("Name: " + name);
            txtSavedStatus.setText("Status: " + status);
            txtSavedGenres.setText("Genres: " + String.join(", ", selectedGenres));


            // Disable editing and buttons after saving
            editName.setEnabled(false);
            editStatus.setEnabled(false);
            btnSelectGenres.setEnabled(false);
            btnTakePicture.setEnabled(false);
            btnSaveProfile.setEnabled(false);

            Toast.makeText(this, "Profile Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    // Check Camera Permission
    private void checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST_CODE);
        } else {
            btnTakePicture.setEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                btnTakePicture.setEnabled(true);
            } else {
                Toast.makeText(this, "Camera permission is required to take pictures", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Bitmap picture = (Bitmap) data.getExtras().get("data");
            if (picture != null) {
                imgProfilePicture.setImageBitmap(picture);
            } else {
                Toast.makeText(this, "Failed to capture image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
