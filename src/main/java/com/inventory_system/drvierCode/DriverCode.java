package com.inventory_system.drvierCode;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DriverCode implements CommandLineRunner {

    private final ItemRepository itemRepository;

    public DriverCode( ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Item data");

        itemRepository.save(new Item("nail", 7.99, "DIY"));
        itemRepository.save(new Item("pen", 5.49, "Office/School"));
        itemRepository.save(new Item("pencil", 4.50, "Office/School"));
        itemRepository.save(new Item("ruler", 3.00, "Office/School"));
        itemRepository.save(new Item("notebook", 10.99, "Office/School"));

        System.out.println(" Items saved: " + itemRepository.count());
    }
}