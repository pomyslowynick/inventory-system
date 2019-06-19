package com.inventory_system.services;

import com.inventory_system.model.Item;

public interface InventoryEditor {

    void editItem(Item item);

    public void handleSaveException();

}
