package com.inventory_system.model;

import com.inventory_system.exceptions.QuantityInputMismatch;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Item {
    public static int numberOfItems;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String name;
    private Double price;
    private String category;
    private int quantity;

    public Item() {
        this.name = null;
        this.price = null;
        this.category = null;
        this.quantity = 0;

        numberOfItems++;
    }
    public Item(String name, Double price, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;

        numberOfItems++;

    }

    public void decrementItems() {
        numberOfItems--;
    }

}
