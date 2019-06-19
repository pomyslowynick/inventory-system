package com.inventory_system.controller;



import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class InventoryEditingControllerTest {

    @Autowired
    ItemRepository repository;


    // Test that item can be saved in database
    @Test
    public void testInsert() {

        Item item = new Item("Screw", 20.0, "DIY", 3);
        Item item2 = new Item("Note", 20.0, "DIY", 3);
        System.out.println("Test Starting \n\n\n");
        item = repository.save(item);
        Assertions.assertThat(repository.findById(item.getId())).hasValue(item).withFailMessage("Created item doesn't correspond to it's ID");
    }


    /**
     * Testing {@link ItemRepository} fragment.
     */
    @Test
    public void testInventoryLimit() {

        Item item = new Item("5 crayons", 3.0, "School", 5);
        Item itemJustOne = new Item("1 crayon", 0.6, "School", 1);

        // Make sure database is empty
        repository.deleteAll();
//        Assertions.assertThat(repository.getTotalQuantity()).isNull();

        // Fill database to maximum capacity
        repository.save(item);

        while(repository.getTotalQuantity() < 200) {
            repository.save(item);
        }

        // Make sure that inventory is full
        Assertions.assertThat(repository.getTotalQuantity()).isEqualTo(200);

        // Try to add one more item
        repository.save(itemJustOne);

        // Assert that item count is not above maximum
        Assertions.assertThat(repository.getTotalQuantity()).isEqualTo(200);

    }

    // Test if new category gets created.
    @Test
    public void testCategoryExists() {

        Item item = new Item("Stick", 3000.0, "Woodwork", 2);

        repository.save(item);

        Assertions.assertThat(repository.getAllCategories()).contains(item.getCategory());
    }

}
