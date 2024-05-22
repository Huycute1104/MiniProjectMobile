package com.example.miniproject;

import android.content.Intent;
import android.content.SharedPreferences;
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
        listUsers.add(new Users("huy", "123", getCoinsForUser("huy")));
        listUsers.add(new Users("tri", "123", getCoinsForUser("tri")));
        listUsers.add(new Users("phuoc", "123", getCoinsForUser("phuoc")));
        listUsers.add(new Users("nhat", "123", getCoinsForUser("nhat")));
        listUsers.add(new Users("hai", "123", getCoinsForUser("hai")));

//        listUsers.add(new Users("huy", "123", 1000));
//        listUsers.add(new Users("tri", "123", 1000));
//        listUsers.add(new Users("phuoc", "123", 1000));
//        listUsers.add(new Users("nhat", "123", 1000));
        if (getIntent().getSerializableExtra("USER_LIST") != null) {
            listUsers = (ArrayList<Users>) getIntent().getSerializableExtra("USER_LIST");
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = txtUsername.getText().toString();
                String password = txtPassword.getText().toString();

                Users loggedInUser = validateLogin(username, password);
                if (loggedInUser != null) {
                    Intent intent = new Intent(LoginActivity.this, RaceActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("COINS", loggedInUser.getCoins());
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
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                intent.putExtra("USER_LIST", listUsers);
                startActivity(intent);
            }
        });
    }

//    private boolean validateLogin(String username, String password) {
//        for (Users user : listUsers) {
//            if (user.getUserName().equals(username) && user.getPassWord().equals(password)) {
//                return true;
//            }
//        }
//        return false;
//    }

    private int getCoinsForUser(String username) {
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return prefs.getInt(getCoinsKey(username), 1000);
    }

    private String getCoinsKey(String username) {
        return "COINS_" + username;
    }

    private Users validateLogin(String username, String password) {
        for (Users user : listUsers) {
            if (user.getUserName().equals(username) && user.getPassWord().equals(password)) {
                int coins = getCoinsForUser(username);
                return new Users(username, password, coins);
            }
        }
        return null;
    }
}
