package com.inventory_system.repositories;

import com.inventory_system.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByPriceLessThanEqualAndPriceGreaterThanEqual(double priceMin, double priceMax);

    List<Item> findTop5ByOrderByIdDesc();

    List<Item> findByCategoryContainsIgnoreCase(String category);

    @Query("SELECT SUM(quantity) FROM Item")
    int getTotalQuantity();

    @Query("SELECT category FROM Item")
    List<String> getAllCategories();
}
