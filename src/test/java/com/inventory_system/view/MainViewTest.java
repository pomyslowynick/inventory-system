package com.inventory_system.view;

import com.inventory_system.services.InventoryEditorImpl;
import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MainViewTest {

  @Mock MainView view;

  @Mock ItemRepository itemRepository;

  @Mock InventoryEditorImpl editor;

  @Mock Item item;

  //
  @Test
  public void testCategory() {
    view.listItems();
    when(item.getCategory()).thenReturn("DIY");

    // use mock in test....
    assertEquals(item.getCategory(), "DIY");
  }

  @Test
  public void testItemRepository() {
    when(itemRepository.getTotalQuantity()).thenReturn(200);
    Mockito.doThrow(new Exception()).when(editor).editItem(item);
  }

  @Test
  public void listItems() {}

  @Test
  public void changeStringFilterVisibility() {}

  @Test
  public void changeCategoryFilterVisibility() {
    Assertions.assertThat(1).isEqualTo(1);
  }

  @Test
  public void changePriceFilterVisibility() {}
}
