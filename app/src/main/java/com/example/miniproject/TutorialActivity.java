package com.example.miniproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class TutorialActivity extends AppCompatActivity {
    Button btnTutorial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tutorial);
        btnTutorial =findViewById(R.id.backButton);
        btnTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}