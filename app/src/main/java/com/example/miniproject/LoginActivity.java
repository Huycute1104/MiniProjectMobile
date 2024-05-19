package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Models.Users;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    ArrayList<Users> listUsers;
    EditText txtUsername, txtPassword;
    Button btnLogin, btnCreateAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.button);
        btnCreateAccount = findViewById(R.id.CreateAccountButton);

        listUsers = new ArrayList<>();
        listUsers.add(new Users("huy", "123", 1000));
        listUsers.add(new Users("tri", "123", 1000));
        listUsers.add(new Users("phuoc", "123", 1000));
        listUsers.add(new Users("nhat", "123", 1000));

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                if (validateLogin(username, password)) {
                    Intent intent = new Intent(LoginActivity.this, RaceActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle create account action here
                Toast.makeText(LoginActivity.this, "Create account clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        for (Users user : listUsers) {
            if (user.getUserName().equals(username) && user.getPassWord().equals(password)) {
                return true;
            }
        }
        return false;
    }
}
