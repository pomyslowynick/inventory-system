package com.inventory_system;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class InventorySystemApplicationTests {

  @Test
  public void contextLoads() {}

  @Autowired ItemRepository repository;

  // Test that item can be saved in database
  @Test
  public void testInsert() {

    Item item = new Item("Screw", 20.0, "DIY", 3);
    item = repository.save(item);
    Assertions.assertThat(repository.findById(item.getId()))
        .hasValue(item)
        .withFailMessage("Created item doesn't correspond to it's ID");
    System.out.println("Test Successful \n\n\n");
  }

  // Test that after creating item with new category the new category gets created.
  @Test
  public void testCategoryExists() {

    Item item = new Item("Stick", 3000.0, "Woodwork", 2);

    repository.save(item);

    Assertions.assertThat(repository.getAllCategories()).contains(item.getCategory());
  }

  @Test
  public void testInventoryLimit() {

    Item item = new Item("5 crayons", 3.0, "School", 5);
    Item itemJustOne = new Item("1 crayon", 1.0, "School", 1);

    // Make sure database is empty
    repository.deleteAll();
    System.out.println(repository.getTotalQuantity());
    Assertions.assertThat(repository.getTotalQuantity()).isNull();

    // Fill database to maximum capacity
    repository.save(item);
    int i = 0;
    while (i < 39) {
      repository.save(new Item("5 crayons", 3.0, "School", 5));
      i++;
    }
    System.out.println(repository.getTotalQuantity());
    // Make sure that inventory is full
    Assertions.assertThat(repository.getTotalQuantity()).isEqualTo(200);

    // Try to add one more item
    repository.save(itemJustOne);

    // Assert that item count is not above maximum
    Assertions.assertThat(repository.getTotalQuantity()).isEqualTo(200);
  }
}
