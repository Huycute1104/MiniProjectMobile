package com.example.miniproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class RaceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        String username = getIntent().getStringExtra("USERNAME");
        int coins = getIntent().getIntExtra("COINS", 0);

        TextView txtWelcome = findViewById(R.id.txtUsername);
        txtWelcome.setText("Welcome, " + username + "!");

        TextView txtCoins = findViewById(R.id.txtCoins);
        txtCoins.setText("Coins: " + coins);
    }
}
