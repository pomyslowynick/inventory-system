package com.inventory_system.view;

import com.inventory_system.controller.InventoryEditingControllerImpl;
import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

@Route
public class MainView extends VerticalLayout {

    private final ItemRepository repo;

    private final InventoryEditingControllerImpl editor;

    final Grid<Item> grid;

    final TextField filter;

    private final Button addNewBtn;

    public MainView(ItemRepository repo, InventoryEditingControllerImpl editor) {
        this.repo = repo;
        this.editor = editor;
        this.grid = new Grid<>(Item.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());

        // build layout
        HorizontalLayout actions = new HorizontalLayout(filter, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "name", "price", "category");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        filter.setPlaceholder("Filter by price");

        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listItems(e.getValue()));

        // Connect selected Customer to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editItem(e.getValue());
        });

        // Instantiate and edit new Customer the new button is clicked
        addNewBtn.addClickListener(e -> editor.editItem(new Item("", 0.0, "")));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItems(filter.getValue());
        });

        // Initialize listing
        listItems(null);
    }

//         tag::listCustomers[]
    void listItems(String filterText) {
        if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        }
        else {
//            grid.setItems(repo.findAll());
            grid.setItems(repo.findByCategoryContains(filterText));
        }
    }
//         end::listCustomers[]
    }

