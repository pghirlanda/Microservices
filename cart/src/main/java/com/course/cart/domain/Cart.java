package com.course.cart.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<CartItem> products;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public void addProduct(CartItem product) {
        this.products.add(product);
    }

    public void setProducts(List<CartItem> products) {
        this.products = products;
    }

    public void clearPanier(){
        this.products.clear();
    }
}
