package com.example.taskmanageras2;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button buttonLogin;
    private Button textViewSignUp;
    private SQLiteDatabase db;
    private UserHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        // Initialize views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        textViewSignUp = findViewById(R.id.button3);

        // Initialize database helper
        dbHelper = new UserHelper(this);
        db = dbHelper.getWritableDatabase(); // This will create the database if it doesn't exist

        // Set onClickListener for Login button
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        // Set onClickListener for SignUp TextView
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                finish();
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        Cursor cursor = db.rawQuery("SELECT * FROM " + UserHelper.TABLE_USERS +
                        " WHERE " + UserHelper.KEY_EMAIL + " = ? AND " + UserHelper.KEY_PASSWORD + " = ?",
                new String[]{email, password});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") long userId = cursor.getLong(cursor.getColumnIndex(UserHelper.KEY_ID));
            @SuppressLint("Range") String firstName = cursor.getString(cursor.getColumnIndex(UserHelper.KEY_FIRST_NAME));
            @SuppressLint("Range") String lastName = cursor.getString(cursor.getColumnIndex(UserHelper.KEY_LAST_NAME));

            startActivity(new Intent(LoginActivity.this , Dasboard.class)
                    .putExtra("UUID" , userId+"").putExtra("name" , firstName+" " +lastName));

            Toast.makeText(this, "Login successful for " + firstName + " " + lastName, Toast.LENGTH_SHORT).show();
            cursor.close();
        } else {
            Toast.makeText(this, "Invalid credentials. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

