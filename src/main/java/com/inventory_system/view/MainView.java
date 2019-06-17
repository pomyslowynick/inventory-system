package com.inventory_system.view;

import com.inventory_system.controller.InventoryEditingControllerImpl;
import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.util.StringUtils;

@Route
public class MainView extends VerticalLayout {

    private final ItemRepository repo;

    private final InventoryEditingControllerImpl editor;

    final Select<String> select;

    final Grid<Item> grid;

    final TextField filter;

    final NumberField priceFilterMoreThan;

    final NumberField priceFilterLessThanOrEqual;

    private final Button addNewBtn;

    public MainView(ItemRepository repo, InventoryEditingControllerImpl editor) {
        this.repo = repo;
        this.editor = editor;
        this.priceFilterMoreThan = new NumberField();
        this.priceFilterLessThanOrEqual = new NumberField();
        this.grid = new Grid<>(Item.class);
        this.filter = new TextField();
        this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
        this.select = new Select<>("Show all", "By price", "By categories", "5 last added");


        // Build layout
        HorizontalLayout actions = new HorizontalLayout(select, filter, priceFilterMoreThan, priceFilterLessThanOrEqual, addNewBtn);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "name", "price", "category", "quantity");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Dropdown for filtering options
        select.setPlaceholder("Filter by...");
        select.setValue("Show all");
        filter.setPlaceholder("Filter text...");

        // Set price and String filters to  be not visible at start
        priceFilterMoreThan.setVisible(false);
        priceFilterLessThanOrEqual.setVisible(false);
        filter.setVisible(false);


        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listItems(e.getValue()));

        // Change products ordering when new filter option is selected
        select.addValueChangeListener(e -> listItems(e.getValue()));
        select.addValueChangeListener(e -> changeFilterVisibility());
        select.addValueChangeListener(e -> changePriceFilterVisibility());

        // Connect selected Item to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editItem(e.getValue());
        });

        // Instantiate and edit new Item the new button is clicked
        addNewBtn.addClickListener(e -> editor.editItem(new Item("", 0.0, "", 0)));

        // Listen changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItems(filter.getValue());
        });

        // Initialize listing
        listItems(null);
    }

//         tag::listItems[]
    void listItems(String filterText) {
        if (select.getValue().equals("Show all")) {
            grid.setItems(repo.findAll());
        } else if (StringUtils.isEmpty(filterText)) {
            grid.setItems(repo.findAll());
        } else if (select.getValue().equals("Filter text...")) {
            grid.setItems(repo.findAll());
        } else if (select.getValue().equals("5 last added")) {
            grid.setItems(repo.findTop5ByOrderByIdDesc());
        } else if (select.getValue().equals("By price")) {
            grid.setItems(repo.findAll());
        } else if (select.getValue().equals("By categories")) {
            grid.setItems(repo.findByCategoryContainsIgnoreCase(filterText));
        }
        else {
            grid.setItems(repo.findAll());
        }
    }

//         end::listItems[]
    void changeFilterVisibility() {
        if (select.getValue().equals("By categories")) {
            filter.setVisible(true);
        } else {
            filter.setVisible(false);
        }
    }

    void changePriceFilterVisibility() {
        if (select.getValue().equals("By price")) {
            priceFilterMoreThan.setVisible(true);
            priceFilterLessThanOrEqual.setVisible(true);
        } else {
            priceFilterMoreThan.setVisible(false);
            priceFilterLessThanOrEqual.setVisible(false);
        }
    }
    }

