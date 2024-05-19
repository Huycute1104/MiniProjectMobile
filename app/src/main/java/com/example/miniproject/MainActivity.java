package com.example.miniproject;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.miniproject.Models.Users;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Users> listUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_loading);

        listUsers = new ArrayList<>();
        listUsers.add(new Users("huy","123",1000));
        listUsers.add(new Users("tri","123",1000));
        listUsers.add(new Users("phuoc","123",1000));
        listUsers.add(new Users("nhat","123",1000));

//        final ArrayAdapter arrayAdapter = new ArrayAdapter(
//                MainActivity.this,
//                android.R.layout.simple_list_item_1,
//                listUsers
//        );
    }
}