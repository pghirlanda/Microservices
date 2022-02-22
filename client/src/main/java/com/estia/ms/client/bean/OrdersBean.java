package com.estia.ms.client.bean;

import java.util.List;

public class OrdersBean {
    private Long id;
    private List<OrderItemBean> paniers;
    private Double prix;

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrderItemBean> getPaniers() {
        return paniers;
    }

    public void setPaniers(List<OrderItemBean> paniers) {
        this.paniers = paniers;
    }

    public OrdersBean() {
    }
}
