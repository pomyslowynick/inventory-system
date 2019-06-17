package com.inventory_system.model;

import lombok.Data;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Item {
    private static int numberOfItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Double price;
    private String category;

    public Item() {
        this.name = null;
        this.price = null;
        this.category = null;

    }
    public Item(String name, Double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;

    }

}
