package com.Auton.gibg.response;

import com.Auton.gibg.entity.Product;

import java.util.List;

public class ProductListResponse {

    private List<Product> products;

    public ProductListResponse(List<Product> products) {
        this.products = products;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products){
        this.products = products;
    }
}
