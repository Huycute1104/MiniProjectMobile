package com.example.miniproject;

import android.content.DialogInterface;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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

import nl.dionsegijn.konfetti.xml.KonfettiView;

public class RaceActivity extends AppCompatActivity {
    public AnimationCustom anim = new AnimationCustom();
    public KonfettiView kftView;
    private String username;
    private TextView txtUsername;
    private TextView txtBalance;
    private List<Car> cars;
    private Button btnStart;
    private AlertDialog addCoinsDialog;
    Button btnLogout;
    Button btnAddmore;
    private MediaPlayer mediaPlayer;
    private final String REQUIRE = "Require";
    private boolean isPlaying = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);

        Button btnToggleMusic = findViewById(R.id.btnToggleMusic);

        // Khởi tạo MediaPlayer với file âm thanh trong thư mục raw
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        String username = getIntent().getStringExtra("USERNAME");
        int coins = getIntent().getIntExtra("COINS", 0);

        TextView txtWelcome = findViewById(R.id.txtUsername);
        txtWelcome.setText("Welcome, " + username + "!");

        TextView txtCoins = findViewById(R.id.txtCoins);
        txtCoins.setText("Coins: " + coins);

        btnLogout = findViewById(R.id.btnLogOut);
        btnAddmore = findViewById(R.id.btnAddMore);
        mediaPlayer.start();

        kftView = findViewById(R.id.kftView);

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

        btnAddmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCoinsDialog();
            }
        });

        btnToggleMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPlaying) {
                    mediaPlayer.pause();
                    isPlaying = false;
                } else {
                    mediaPlayer.start();
                    isPlaying = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
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

    private void map() {
        txtUsername = findViewById(R.id.txtUsername);
        btnStart = findViewById(R.id.btnStart);

        LinearLayout option1 = findViewById(R.id.option1);
        LinearLayout option2 = findViewById(R.id.option2);
        LinearLayout option3 = findViewById(R.id.option3);
        CheckBox cbCar1 = findViewById(R.id.cbCar1);
        CheckBox cbCar2 = findViewById(R.id.cbCar2);
        CheckBox cbCar3 = findViewById(R.id.cbCar3);
        EditText etAmountForCar1 = findViewById(R.id.etAmountForCar1);
        EditText etAmountForCar2 = findViewById(R.id.etAmountForCar2);
        EditText etAmountForCar3 = findViewById(R.id.etAmountForCar3);
        SeekBar sbCar1 = findViewById(R.id.sbCar1);
        SeekBar sbCar2 = findViewById(R.id.sbCar2);
        SeekBar sbCar3 = findViewById(R.id.sbCar3);
        cars = new ArrayList<>();
        cars.add(new Car("Car 1", option1, cbCar1, etAmountForCar1, sbCar1));
        cars.add(new Car("Car 2", option2, cbCar2, etAmountForCar2, sbCar2));
        cars.add(new Car("Car 3", option3, cbCar3, etAmountForCar3, sbCar3));
    }

    private void animateProgression(int progress, int duration, SeekBar seekBar) {
        final ObjectAnimator animation = ObjectAnimator.ofInt(seekBar, "progress", 0, progress);
        animation.setDuration(duration);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
        seekBar.clearAnimation();
    }

    private void init() {
        Log.i("car amount", Integer.toString(cars.size()));
        for (Car car : cars) {
            car.getSeekBar().setOnTouchListener((v, event) -> true);
            CheckBox checkBox = car.getCheckBox();
            car.getCheckBoxContainer().setOnTouchListener((v, event) -> {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        break;
                    case MotionEvent.ACTION_UP:
                        checkBox.setChecked(!checkBox.isChecked());
                        break;
                }
                return true;
            });

            checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                car.getEtAmountForCar().setEnabled(isChecked);
                updateBtnStartEnabled();
            });
        }

        btnStart.setOnClickListener(v -> {
            if (btnStart.getText().toString().toLowerCase().equals("reset")) {
                reset();
                return;
            }

            if (!checkAmountOfBetting()) {
                Log.i("check betting amount", Boolean.toString(checkAmountOfBetting()));
                return;
            }

            for (Car car : cars) {
                car.getCheckBoxContainer().setEnabled(false);
                car.getCheckBox().setEnabled(false);
                car.getEtAmountForCar().setEnabled(false);
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
                TextView txtCoins = findViewById(R.id.txtCoins);
                String coinsText = txtCoins.getText().toString();
                int currentCoins = Integer.parseInt(coinsText.replaceAll("[^0-9]", ""));
                int changedAmount = 0;
                boolean atLeastOneWin = false;
                if (rank1.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank1.getEtAmountForCar().getText().toString());
                    currentCoins += betAmount;
                    changedAmount += betAmount;
                    atLeastOneWin = true;
                }

                if (rank2.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank2.getEtAmountForCar().getText().toString());
                    currentCoins -= betAmount;
                    changedAmount -= betAmount;
                }

                if (rank3.getCheckBox().isChecked()) {
                    int betAmount = Integer.parseInt(rank3.getEtAmountForCar().getText().toString());
                    currentCoins -= betAmount;
                    changedAmount -= betAmount;
                }

                txtCoins.setText("Coins: " + currentCoins);

                String username = getIntent().getStringExtra("USERNAME");
                SharedPreferences prefs = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt(getCoinsKey(username), currentCoins);
                editor.apply();

                AlertDialog.Builder builder = new AlertDialog.Builder(RaceActivity.this);

                String message =
                        rank1.getName() + " is the 1st racer!\n"
                                + rank2.getName() + " is the 2nd racer!\n"
                                + rank3.getName() + " is the 3rd racer!\n"
                                + (changedAmount < 0 ? " - $" : " + $") + Math.abs(changedAmount) + "\nYour new balance is $" + currentCoins + ".";

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
                    anim.CongratAnimation(kftView,this);
                }, 1200);


            }, 1600);
        });
    }
    private void updateBtnStartEnabled() {
        boolean status = false;
        for (Car car : cars) {
            status = status || car.getCheckBox().isChecked();
        }
        btnStart.setEnabled(status);
    }
    private void reset () {
        btnStart.setText("START");
        btnStart.setEnabled(true);
        for (Car car : cars) {
            car.getCheckBox().setEnabled(true);
            car.getCheckBox().setChecked(false);
            car.getSeekBar().setProgress(0);
        }
    }

    private boolean checkAmountOfBetting() {
        if (!checkBalanceForTotalBetting(cars)) {
            Toast.makeText(this, "Not enough balance for the total betting amount!", Toast.LENGTH_SHORT).show();
            return false;
        }
        for (Car car : cars) {
            if (car.getCheckBox().isChecked()) {
                String amountStr = car.getEtAmountForCar().getText().toString();
                if (amountStr.isEmpty()) {
                    car.getEtAmountForCar().setError(REQUIRE);
                    return false;
                } else {
                    int amount = Integer.parseInt(amountStr);
                    if (amount < 1 || amount > 100000000) {
                        Toast.makeText(this, "Please enter a betting amount for " + car.getName().toLowerCase() + " between 1 and 100,000,000!", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean checkBalanceForTotalBetting(List<Car> cars) {
        int totalBettingAmount = 0;
        TextView txtCoins = findViewById(R.id.txtCoins);
        String coinsText = txtCoins.getText().toString();
        int currentCoins = Integer.parseInt(coinsText.replaceAll("[^0-9]", ""));

        for (Car car : cars) {
            if (car.getCheckBox().isChecked()) {
                String amountStr = car.getEtAmountForCar().getText().toString();
                if (amountStr.isEmpty()) {
                    car.getEtAmountForCar().setError(REQUIRE);
                    return false;
                } else {
                    int amount = Integer.parseInt(amountStr);
                    totalBettingAmount += amount;
                }
            }
        }

        return totalBettingAmount <= currentCoins;
    }
}
