package com.estia.ms.order.domain;

import javax.persistence.*;
import java.util.List;

@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> paniers;

    public Orders() { }

    private Double prix;

    public void setPaniers(List<OrderItem> paniers) {
        this.paniers = paniers;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public List<OrderItem> getPaniers() {
        return paniers;
    }

    public void addPaniers(OrderItem panier) {
        this.paniers.add(panier);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
