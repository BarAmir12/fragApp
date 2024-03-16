package com.example.fragapp.models;

import android.content.ClipData;

import com.example.fragapp.activitys.DataModel;
import java.util.List;
import java.util.ArrayList;

public class User
{
    private String email;
    private String pass;
    private String phone;
    private String age;
    private String user;
    private List<DataModel> item;


    public User(){

    }

    public List<DataModel> getItem() {
        return item;
    }

    public void setItem(List<DataModel> item) {
        this.item = item;
    }

    public String getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public String getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public User( String user,String email, String pass, String phone, String age) {
        this.email = email;
        this.pass = pass;
        this.phone = phone;
        this.age = age;
        this.user = user;
        this.item = new ArrayList<DataModel>();
    }
}
