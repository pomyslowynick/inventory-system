package com.inventory_system.repositories;

import com.inventory_system.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

  List<Item> findByPriceLessThanEqualAndPriceGreaterThanEqual(BigDecimal priceMin, BigDecimal priceMax);

  List<Item> findTop5ByOrderByIdDesc();

  List<Item> findByCategoryEquals(String category);

  @Query("SELECT category FROM Item WHERE id = ( select min(id) from Item)")
  String findFirstCategory();

  @Query("SELECT SUM(quantity) FROM Item")
  int getTotalQuantity();

  @Query("SELECT DISTINCT category FROM Item")
  List<String> getAllCategories();

  List<String> findByIdEquals(Long id);
}
