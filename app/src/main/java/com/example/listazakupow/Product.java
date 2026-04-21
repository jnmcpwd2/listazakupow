package com.example.listazakupow;

public class Product {
    public int id;
    public String name;
    public int quantity;
    public String category;

    public Product(int id, String name, int quantity, String category) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.category = category;
    }
}