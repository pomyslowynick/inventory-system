package com.inventory_system.model;

import com.inventory_system.exceptions.QuantityInputMismatch;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Price is mandatory")
    @Min(1)
    private Double price;

    @NotBlank(message = "Category is mandatory")
    private String category;

    @Range(min=0, max=5)
    @NotNull(message = "Quantity is mandatory")
    private int quantity;

    public Item() {
        this.name = null;
        this.price = null;
        this.category = null;
        this.quantity = 0;

    }

    public Item(String name, Double price, String category, int quantity) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;


    }

}
