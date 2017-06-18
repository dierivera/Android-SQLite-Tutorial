package com.dierivera.ubp.unidaddos.Models;

/**
 * Created by dierivera on 6/17/17.
 */

public class Client {
    private int mId;
    private String mName;
    private String mLastName;
    private String mEmail;
    private String mPhoneNumber;

    public Client (String name, String lastName, String email, String phoneNumber){
        this.setName(name);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }

    public Client (int id, String name, String lastName, String email, String phoneNumber){
        this.setId(id);
        this.setName(name);
        this.setLastName(lastName);
        this.setEmail(email);
        this.setPhoneNumber(phoneNumber);
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPhoneNumber() {
        return mPhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        mPhoneNumber = phoneNumber;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

}
