package com.example.miniproject;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject.Models.Car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RaceActivity extends AppCompatActivity {

    private String username;
    private TextView txtUsername;
    private TextView txtBalance;
    private List<Car> cars;
    private Button btnStart;
    Button btnLogout;

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

        map();
        init();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RaceActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void map() {
        txtUsername = findViewById(R.id.txtUsername);
        btnStart = findViewById(R.id.btnStart);

        SeekBar sbCar1 = findViewById(R.id.sbCar1);
        SeekBar sbCar2 = findViewById(R.id.sbCar2);
        SeekBar sbCar3 = findViewById(R.id.sbCar3);
        cars = new ArrayList<>();
        cars.add(new Car("Car 1", sbCar1));
        cars.add(new Car("Car 2", sbCar2));
        cars.add(new Car("Car 3", sbCar3));
    }

    private void animateProgression(int progress, int duration, SeekBar seekBar) {
        final ObjectAnimator animation = ObjectAnimator.ofInt(seekBar, "progress", 0, progress);
        animation.setDuration(duration);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        seekBar.clearAnimation();
    }

    private void init() {
        for (Car car : cars) {
            // Make seekbars unable to be changed when touching
            car.getSeekBar().setOnTouchListener((v, event) -> true);
        }

        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().toLowerCase().equals("reset")) {
                reset();
                return;
            }


            btnStart.setEnabled(false);

            Collections.shuffle(cars);

            Car rank1 = cars.get(0);
            Car rank2 = cars.get(1);
            Car rank3 = cars.get(2);

            animateProgression(100, 1100, findViewById(rank1.getSeekBar().getId()));
            animateProgression(100, 1500, findViewById(rank2.getSeekBar().getId()));
            animateProgression(100, 1800, findViewById(rank3.getSeekBar().getId()));

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank1.getName() + " is the 1st racer!", Toast.LENGTH_SHORT).show();
            }, 1000);

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank2.getName() + " is the 2nd racer!", Toast.LENGTH_SHORT).show();
            }, 1200);

            new Handler().postDelayed(() -> {
                Toast.makeText(this, rank1.getName() + " is the 3rd racer!", Toast.LENGTH_SHORT).show();
            }, 1500);

            new Handler().postDelayed(() -> {
                btnStart.setText("RESET");
                btnStart.setEnabled(true);
            }, 1600);

            new Handler().postDelayed(() -> {
                // INFORMING
                AlertDialog.Builder builder = new AlertDialog.Builder(RaceActivity.this);

                String message =
                        rank1.getName() + " is the 1st racer!\n"
                                + rank2.getName() + " is the 2nd racer!\n"
                                + rank3.getName() + " is the 3rd racer!\n";

                TextView titleTextView = new TextView(RaceActivity.this);
                titleTextView.setText("Round done!");
                titleTextView.setTextSize(20);
                titleTextView.setGravity(Gravity.CENTER);

                TextView messageTextView = new TextView(RaceActivity.this);
                messageTextView.setText(message);
                messageTextView.setGravity(Gravity.CENTER);
                messageTextView.setPadding(20, 20, 20, 20);
                builder.setCustomTitle(titleTextView);
                builder.setView(messageTextView);
                builder.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                });
                builder.setCancelable(false);
                AlertDialog alertDialog = builder.create();
                new Handler().postDelayed(() -> {
                    alertDialog.show();
                }, 1200);


            }, 1600);
        });
    }

    private void reset () {
        btnStart.setText("START");
        btnStart.setEnabled(true);
        for (Car car : cars) {
            car.getSeekBar().setProgress(0);
        }
    }
}
