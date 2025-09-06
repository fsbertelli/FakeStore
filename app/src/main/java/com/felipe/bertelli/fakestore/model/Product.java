package com.felipe.bertelli.fakestore.model;

public class Product {

    public int id;
    public String title;
    public double price;
    public String description;
    public String category;
    public String imageUrl;

    @Override
    public String toString(){
        return title + " - $" + price;
    }
}

