package com.inventory_system.repositories;

import com.inventory_system.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByPriceLessThan(int price);

    List<Item> findTop5ByOrderByIdDesc();

    List<Item> findByCategoryContainsIgnoreCase(String category);

}
