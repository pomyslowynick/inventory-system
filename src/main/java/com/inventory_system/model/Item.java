package com.inventory_system.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;
import java.math.BigDecimal;

/**
 * Core of my application, basic class of every object in inventory, features automatically
 * generated id and some validation thanks to Spring annotations. No parameters constructor is
 * needed for Spring.
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Item {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotNull(message = "Price is mandatory")
  @DecimalMin(value="0.01")
  private BigDecimal price;

  @NotBlank(message = "Category is mandatory")
  private String category;

  @Range(min = 0, max = 5)
  @NotNull(message = "Quantity is mandatory")
  private int quantity;
  
}
