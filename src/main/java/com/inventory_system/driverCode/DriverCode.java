package com.inventory_system.driverCode;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 *  Class made in order to give initial population to database, there's not much here.
 */
@Component
public class DriverCode implements CommandLineRunner {

    private final ItemRepository itemRepository;

    /**
     * @param itemRepository
     */
    public DriverCode( ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        System.out.println("Loading Item data");

        itemRepository.save(new Item("nail", 7.99, "DIY", 5));
        itemRepository.save(new Item("pen", 5.49, "Office/School", 4));
        itemRepository.save(new Item("pencil", 4.50, "Office/School", 0));
        itemRepository.save(new Item("ruler", 3.00, "Office/School", 2));
        itemRepository.save(new Item("notebook", 10.99, "Office/School", 5));
        itemRepository.save(new Item("Work trousers", 10.99, "Clothes", 5));
        itemRepository.save(new Item("Microwave", 10.99, "Kitchen Appliances", 5));

        System.out.println(" Items saved: " + itemRepository.count());
    }
}
