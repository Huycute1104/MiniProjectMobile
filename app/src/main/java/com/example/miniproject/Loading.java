package com.example.miniproject;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Loading extends AppCompatActivity {
    private ProgressBar progressBar;
    private Button button;
    private TextView progressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        button = findViewById(R.id.btnStart);
        progressBar = findViewById(R.id.progressBar);
        progressText = findViewById(R.id.progressText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountDownTimer countDownTimer = new CountDownTimer(5000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        int current = progressBar.getProgress();
                        if (current >= progressBar.getMax()) {
                            current = 0;
                        }
                        current += 20;
                        progressBar.setProgress(current);
                        progressText.setText(current + "%");
                    }

                    @Override
                    public void onFinish() {
                        progressBar.setProgress(progressBar.getMax());
                        progressText.setText(progressBar.getMax() + "%");
                        Toast.makeText(Loading.this, "Loading complete", Toast.LENGTH_SHORT).show();
                        // Start LoginActivity
                        Intent intent = new Intent(Loading.this, LoginActivity.class);
                        startActivity(intent);
                        finish(); // Finish the current activity so the user can't go back to it
                    }
                };
                countDownTimer.start();
            }
        });
    }
}
