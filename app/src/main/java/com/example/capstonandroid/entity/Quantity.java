package com.example.capstonandroid.entity;

public class Quantity {
    //long
    private String productID;
    private int quantity;
    private boolean checked;

    public Quantity() {
    }

    public Quantity(String productID, int quantity, boolean checked) {
        this.productID = productID;
        this.quantity = quantity;
        this.checked = checked;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "Quantity{" +
                "productID='" + productID + '\'' +
                ", quantity=" + quantity +
                ", checked=" + checked +
                '}';
    }
}
