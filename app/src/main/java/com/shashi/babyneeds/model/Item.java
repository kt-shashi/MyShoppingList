package com.shashi.babyneeds.model;

public class Item {

    private int id;
    private String itemName;
    private String itemColor;
    private int itemQuantity;
    private int itemPrice;
    private String dateItemAdded;

    public Item() {
    }

    public Item(String itemName, String itemColor, int itemQuantity, int itemPrice, String dateItemAdded) {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.dateItemAdded = dateItemAdded;
    }

    public Item(int id, String itemName, String itemColor, int itemQuantity, int itemPrice, String dateItemAdded) {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
        this.dateItemAdded = dateItemAdded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getDateItemAdded() {
        return dateItemAdded;
    }

    public void setDateItemAdded(String dateItemAdded) {
        this.dateItemAdded = dateItemAdded;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }
}
