package com.example.ebookfrancis;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // Inisialisasi DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        CheckBox adminCheckbox = findViewById(R.id.admin_checkbox);
        EditText codeEditText = findViewById(R.id.code);
        EditText usernameEditText = findViewById(R.id.username);
        EditText passwordEditText = findViewById(R.id.password);
        Button signUpButton = findViewById(R.id.signin_button);

        // Tampilkan atau sembunyikan kolom "Code" berdasarkan pilihan admin
        adminCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            codeEditText.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        // Logika ketika tombol Sign Up diklik
        signUpButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();
            String codeInput = codeEditText.getText().toString();

            // Validasi input pengguna
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignInActivity.this, "Username dan password harus diisi", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean isAdmin = adminCheckbox.isChecked() && "ADMIN".equals(codeInput);
            if (adminCheckbox.isChecked() && !isAdmin) {
                Toast.makeText(SignInActivity.this, "Kode admin tidak valid", Toast.LENGTH_SHORT).show();
                return;
            }

            // Tambahkan pengguna ke database
            boolean isInserted = dbHelper.addUser(username, password, isAdmin);
            if (isInserted) {
                Toast.makeText(SignInActivity.this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignInActivity.this, "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
            }
        });
    }
}