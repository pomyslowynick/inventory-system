package com.inventory_system.controller;

import com.inventory_system.model.Item;

public interface InventoryEditingController {

    Item addItem(Item item);

    Item updateItem(Item item);

    void deleteItemByID(Long id);

}
