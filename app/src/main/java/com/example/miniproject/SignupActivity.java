package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Models.Users;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    private EditText txtUsername;
    private EditText txtPassword;
    private EditText txtConfirmPassword;
    private Button btnSignup;

    private Button btnLogin;
    private ArrayList<Users> listUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);
        btnSignup = findViewById(R.id.btnSignup);

        listUsers = (ArrayList<Users>) getIntent().getSerializableExtra("USER_LIST");


        btnSignup.setOnClickListener(v -> {
            if (validateInput()) {
                String username = txtUsername.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();
                listUsers.add(new Users(username, password, 0));
                Toast.makeText(SignupActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();

                Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });

        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            Intent loginIntent = new Intent(SignupActivity.this, LoginActivity.class);
            startActivity(loginIntent);
            finish();
        });
    }

    private boolean validateInput() {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        String confirmPassword = txtConfirmPassword.getText().toString().trim();

        if (isEmptyField(username, txtUsername, "Username is required")) return false;
        if (isEmptyField(password, txtPassword, "Password is required")) return false;
        if (!isValidPassword(password)) return false;
        if (isEmptyField(confirmPassword, txtConfirmPassword, "Confirm Password is required"))
            return false;
        if (!password.equals(confirmPassword)) {
            txtConfirmPassword.setError("Passwords do not match");
            txtConfirmPassword.requestFocus();
            return false;
        }
        if (existUser(username, txtUsername)) {
            Toast.makeText(SignupActivity.this, "Username already exists", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isEmptyField(String value, EditText field, String errorMessage) {
        if (TextUtils.isEmpty(value)) {
            field.setError(errorMessage);
            field.requestFocus();
            return true;
        }
        return false;
    }

    private boolean isValidPassword(String password) {
        if (password.length() < 2) {
            txtPassword.setError("Password must be at least 2 characters");
            txtPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean existUser(String username, EditText field) {
        for (Users user : listUsers) {
            if (user.getUserName().equals(username)) {
                field.requestFocus();
                return true;
            }
        }
        return false;
    }
}
