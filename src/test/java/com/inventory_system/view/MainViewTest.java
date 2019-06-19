package com.inventory_system.view;

import com.inventory_system.repositories.ItemRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MainViewTest {

    @Autowired
    ItemRepository itemRepository;

    @Autowired

    @Test
    public void listItems() {

    }

    @Test
    public void changeStringFilterVisibility() {

    }

    @Test
    public void changeCategoryFilterVisibility() {
        assertThat();
    }

    @Test
    public void changePriceFilterVisibility() {
    }
}