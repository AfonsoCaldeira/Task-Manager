package com.example.taskmanageras2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFirstName, editTextLastName, editTextEmail, editTextPassword;
    private Button buttonSignUp;
    private UserHelper userHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_page);

        // Initialize views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignUp = findViewById(R.id.buttonSignUp);

        // Initialize UserHelper
        userHelper = new UserHelper(this);

        // Set onClickListener for SignUp button
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        String firstName = editTextFirstName.getText().toString().trim();
        String lastName = editTextLastName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Validate input fields (You can add more validation as per your requirement)
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignUpActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email is already registered
        if (userHelper.getUserByEmail(email) != null) {
            Toast.makeText(SignUpActivity.this, "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insert user into database
        long id = userHelper.insertUser(firstName, lastName, email, password);

        // Show success or failure message
        if (id == -1) {
            Toast.makeText(SignUpActivity.this, "Failed to sign up", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(SignUpActivity.this, "Sign up successful with ID: " + id, Toast.LENGTH_SHORT).show();
            // Clear EditText fields after successful sign up
            editTextFirstName.getText().clear();
            editTextLastName.getText().clear();
            editTextEmail.getText().clear();
            editTextPassword.getText().clear();
        }
    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
        finish();
    }

}
