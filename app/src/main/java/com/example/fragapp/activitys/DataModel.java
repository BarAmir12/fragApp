package com.example.fragapp.activitys;

public class DataModel {
    private String name;
    private int image;
    private int id_;
    private String price;
    private int amount ;

    public DataModel() {

    }

    public DataModel(String name, Integer image,String price, Integer id_) {
        this.name =name;
        this.image =image;
        this.price =price;
        this.id_=id_;
        this.amount =0;


    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
    public int getImage() {
        return image;
    }
    public int getId_() {
        return id_;
    }
}
