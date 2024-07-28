package com.example.tolkhub.Model;

import androidx.lifecycle.LiveData;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "message_contact_table")
public class messageContactModel  {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String senderName;
    private String senderNumber;

    // Constructor with arguments
    public messageContactModel(String senderName, String senderNumber) {
        this.senderName = senderName;
        this.senderNumber = senderNumber;
    }

    // No-argument constructor
    public messageContactModel() {
        // Required empty constructor for Room
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    @Override
    public String toString() {
        return "messageContactModel{" +
                "id=" + id +
                ", senderName='" + senderName + '\'' +
                ", senderNumber='" + senderNumber + '\'' +
                '}';
    }
}
