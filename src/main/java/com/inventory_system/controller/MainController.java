package com.inventory_system.controller;

import java.math.BigDecimal;
import java.util.List;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.inventory_system.services.InventoryEditorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * This controller class should serve http requests in case UI is not needed, I haven't figured out
 * how to make same requests using Vaadin URI's, so this class stays as a potential extension or if
 * UI support should be dropped.
 */
/*
   When declared the controller class takes over http requests< I couldnt make it cooperate with Vaadin.
*/
//@RestController
@Component
public class MainController {

  @Autowired private final ItemRepository itemRepository;


  public MainController(ItemRepository itemRepository) {
    this.itemRepository = itemRepository;
  }

  @GetMapping("/topCategory")
  public String getTopCategory() { return itemRepository.findFirstCategory(); }

  @GetMapping("/")
  public List<Item> getAllItems() {
    return itemRepository.findAll();
  }

  @GetMapping("/getByCategory/{category}")
  public List<Item> getItemsByCategory(@PathVariable String category) {
    return itemRepository.findByCategoryEquals(category);
  }

  @GetMapping("/getMostRecent")
  public List<Item> getFiveMostRecent() {
    return itemRepository.findTop5ByOrderByIdDesc();
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Item saveItem(@RequestBody Item item) {
    return itemRepository.save(item);
  }

  @GetMapping("/remove/{id}")
  public void deleteItemById(@PathVariable Long id) {
    itemRepository.deleteById(id);
  }

  @GetMapping("/Total")
  public int getQuantityInventory() {
    return itemRepository.getTotalQuantity();
  }

  @GetMapping("/getPriceBetween/{priceMin}and{priceMax}")
  public List<Item> getItemPriceBetween(
      @PathVariable("priceMin") double priceMin, @PathVariable("priceMax") double priceMax) {
    BigDecimal priceMinBD = new BigDecimal(priceMin);
    BigDecimal priceMaxBD = new BigDecimal(priceMax);
    return itemRepository.findByPriceGreaterThanEqualAndPriceLessThan(priceMinBD, priceMaxBD);
  }
}
