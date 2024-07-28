package com.example.tolkhub.Model;


import androidx.annotation.NonNull;

public class AuthenticationModel {


    private String userPhoneNumber;
    private String userName;
private userStatus userStatus;


    public AuthenticationModel() {
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public com.example.tolkhub.Model.userStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(com.example.tolkhub.Model.userStatus userStatus) {
        this.userStatus = userStatus;
    }

    @NonNull
    @Override
    public String toString() {
        return "AuthenticationModel{" +
                "userPhoneNumber='" + userPhoneNumber + '\'' +
                ", userName='" + userName + '\'' +
                ", userStatus=" + userStatus +
                '}';
    }
}
