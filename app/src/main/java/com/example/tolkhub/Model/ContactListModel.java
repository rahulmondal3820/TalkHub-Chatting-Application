package com.example.tolkhub.Model;

public class ContactListModel {

    private String ContactName;
    private String ContactNumber;
    private String ContactImg;
    public ContactListModel(String contactName, String  ContactImg, String contactNumber) {
        this.ContactName = contactName;
       this.ContactNumber = contactNumber;
       this.ContactImg=ContactImg;

    }

    public ContactListModel(String contactName, String contactNumber) {
        ContactName = contactName;
        ContactNumber = contactNumber;
    }

    public String getContactImg() {
        return ContactImg;
    }

    public void setContactImg(String contactImg) {
        ContactImg = contactImg;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
