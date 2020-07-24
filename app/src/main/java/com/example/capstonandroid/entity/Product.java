package com.example.capstonandroid.entity;

public class Product {
    private String productID;
    private String productName;
    private String catalogs;
    private String productImage;
    private int unitInStock;
    private int unitPrice;
    private boolean isDeleted;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Product(String productName, String catalogs, String productImage, int unitInStock, int unitPrice, boolean isDeleted, String description) {
        this.productName = productName;
        this.catalogs = catalogs;
        this.productImage = productImage;
        this.unitInStock = unitInStock;
        this.unitPrice = unitPrice;
        this.isDeleted = isDeleted;
        this.description = description;
    }

    public Product() {
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCatalogs() {
        return catalogs;
    }

    public void setCatalogs(String catalogs) {
        this.catalogs = catalogs;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public int getUnitInStock() {
        return unitInStock;
    }

    public void setUnitInStock(int unitInStock) {
        this.unitInStock = unitInStock;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(int unitPrice) {
        this.unitPrice = unitPrice;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public void copyFrom(Product from){
        this.setProductID(from.getProductID());
        this.setProductName(from.getProductName());
        this.setCatalogs(from.getCatalogs());
        this.setProductImage(from.getProductImage());
        this.setUnitInStock(from.getUnitInStock());
        this.setUnitPrice(from.getUnitPrice());
        this.setDeleted(from.isDeleted());
    }
}
