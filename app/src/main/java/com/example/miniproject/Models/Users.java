package com.example.miniproject.Models;

import java.io.Serializable;

public class Users implements Serializable {
    private String userName;
    private String passWord;
    private int coins;

    public Users(String userName, String passWord, int coins) {
        this.userName = userName;
        this.passWord = passWord;
        this.coins = coins;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", coins=" + coins +
                '}';
    }
}
