package com.example.miniproject.Models;

import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

public class Car {
    private String name;
    private LinearLayout checkBoxContainer;
    private CheckBox checkBox;
    private EditText etAmountForCar;
    private SeekBar seekBar;
    private ImageView badgeImage;

    public Car(String name, LinearLayout checkBoxContainer, CheckBox checkBox, EditText etAmountForCar, SeekBar seekBar, ImageView badgeImage) {
        this.name = name;
        this.checkBoxContainer = checkBoxContainer;
        this.checkBox = checkBox;
        this.badgeImage = badgeImage;
        this.seekBar = seekBar;
        this.etAmountForCar = etAmountForCar;
    }

    public Car(String name, SeekBar seekBar) {
        this.name = name;
        this.seekBar = seekBar;
    }

    public String getName() {
        return name;
    }
    public LinearLayout getCheckBoxContainer() {
        return checkBoxContainer;
    }
    public EditText getEtAmountForCar() {
        return etAmountForCar;
    }
    public SeekBar getSeekBar() {
        return seekBar;
    }
    public CheckBox getCheckBox() {
        return checkBox;
    }


}

