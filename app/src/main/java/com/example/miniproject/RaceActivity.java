package com.example.miniproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class RaceActivity extends AppCompatActivity {
    private AlertDialog addCoinsDialog;
    Button btnLogout;
    Button btnAddmore;

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

        btnLogout = findViewById(R.id.btnLogOut);
        btnAddmore = findViewById(R.id.btnAddMore);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RaceActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        btnAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCoinsDialog();
            }
        });

    }

    private void showAddCoinsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(RaceActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_coins, null);
        final EditText edtCoins = dialogView.findViewById(R.id.edtCoins);

        builder.setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int coinsToAdd = Integer.parseInt(edtCoins.getText().toString());
                        updateCoins(coinsToAdd);
                    }
                })
                .setNegativeButton("Cancel", null);

        addCoinsDialog = builder.create();
        addCoinsDialog.show();
    }

    private void updateCoins(int coinsToAdd) {
        TextView txtCoins = findViewById(R.id.txtCoins);
        String coinsText = txtCoins.getText().toString();
        int currentCoins = Integer.parseInt(coinsText.replaceAll("[^0-9]", ""));
        int totalCoins = currentCoins + coinsToAdd;
        txtCoins.setText("Coins: " + totalCoins);

        String username = getIntent().getStringExtra("USERNAME");
        SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(getCoinsKey(username), totalCoins);
        editor.apply();
    }

    private String getCoinsKey(String username) {
        return "COINS_" + username;
    }

}
