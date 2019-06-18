package com.inventory_system.controller;

import com.inventory_system.model.Item;

public interface InventoryEditingController {

    void save();

    void editItem(Item item);

    void delete();

}
