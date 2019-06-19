package com.inventory_system.controller;

import java.util.List;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.inventory_system.services.InventoryEditorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/*
    When declared the controller class takes over http requests< I couldnt make it cooperate with Vaadin.
 */
//@RestController
public class MainController {

    @Autowired
    private final ItemRepository itemRepository;

    private final InventoryEditorImpl editor;

    public MainController(ItemRepository itemRepository, InventoryEditorImpl editor) {
        this.itemRepository = itemRepository;
        this.editor = editor;
    }

    @GetMapping("/getAll/item")
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

    @GetMapping("/getQuantity")
    public int getQuantityInventory() {
        return itemRepository.getTotalQuantity();
    }

    @GetMapping("/getPriceBetween/{priceMin}and{priceMax}")
    public List<Item> getItemPriceBetween(@PathVariable("priceMin") double priceMin, @PathVariable("priceMax") double priceMax) {
        return itemRepository.findByPriceLessThanEqualAndPriceGreaterThanEqual(0, 100);
    }

}
