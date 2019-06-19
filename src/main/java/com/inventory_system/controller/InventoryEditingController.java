package com.inventory_system.controller;

import com.inventory_system.model.Item;

public interface InventoryEditingController {

    void editItem(Item item);

    public void handleSaveException();

}
