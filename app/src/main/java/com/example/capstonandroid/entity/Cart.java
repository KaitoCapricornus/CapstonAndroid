package com.example.capstonandroid.entity;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    //long
    private String username;
    private List<Product> products;
    private List<Quantity> quantities;

    public Cart() {

    }

    public Cart(String username) {
        this.username = username;
        products = new ArrayList<>();
        quantities = new ArrayList<>();
    }

//    public Cart(String username, Product product,String productID) {
//        this.username = username;
//
//        if(products == null) {
//            products = new ArrayList<>();
//            quantities = new ArrayList<>();
//        }
//
//        product.setProductID(productID);
//        products.add(product);
//        quantities.add(new Quantity(productID, 1, true));
//    }

    public void addProductToList(Product product, String productID){
        product.setProductID(productID);
        products.add(product);
        quantities.add(new Quantity(productID, 1, true));
    }

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<Quantity> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Quantity> quantities) {
        this.quantities = quantities;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "username='" + username + '\'' +
                ", products=" + products +
                ", quantities=" + quantities +
                '}';
    }
}
