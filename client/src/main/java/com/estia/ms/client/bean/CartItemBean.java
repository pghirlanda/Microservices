package com.estia.ms.client.bean;

public class CartItemBean {
    private Long id;
    private Long productId;
    private Integer quantity;
    private String name;
    private String description;
    private String illustration;
    private Double price;

    public CartItemBean(Long productId, Integer quantity, String name, String description, String illustration, Double price) {
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.illustration = illustration;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
