package com.inventory_system.controller;

import java.util.List;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class MainController {

    @Autowired
    private final ItemRepository itemRepository;

    public MainController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @GetMapping("/getAll/item")
    public List<Item> getAlltems() {
        return itemRepository.findAll();
    }

    @GetMapping("/getByCategory/{category}")
    public List<Item> getItemsByCategory(@PathVariable String category) {
        return itemRepository.findByCategoryEquals(category);
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


}
