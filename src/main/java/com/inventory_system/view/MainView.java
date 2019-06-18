package com.inventory_system.view;

import com.inventory_system.controller.InventoryEditingControllerImpl;
import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private final ItemRepository repo;

    private final InventoryEditingControllerImpl editor;

    final Select<String> selectFilterCategory;

//    final Select<String> selectCategory;

    final Label totalQuantity;

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
        this.totalQuantity = new Label("Total items: " + repo.getTotalQuantity());
        this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
        this.selectFilterCategory = new Select<>("Show all", "By price", "By categories", "5 last added");
//        this.selectCategory = new Select<>(repo.getCategory())


        // Build layout
        grid.setHeight("300px");
        HorizontalLayout actions = new HorizontalLayout(selectFilterCategory, filter, priceFilterMoreThan, priceFilterLessThanOrEqual
                , addNewBtn, totalQuantity);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "name", "price", "category", "quantity");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Set label style
//        totalQuantity.setStyleName( "big_label"); // For control in CSS using .big_label{}  (for example)
//        totalQuantity.setContentMode( Label.CONTENT_XHTML );  // Interpret the label's contents as XHTML rather than literally.
//        totalQuantity.setValue( text.toString() );  // Fill the contents of the Label.


        // Dropdown for filtering options
        selectFilterCategory.setPlaceholder("Filter by...");
        selectFilterCategory.setValue("Show all");
        filter.setPlaceholder("Filter text...");

        // Set price and String filters to  be not visible at start
        priceFilterMoreThan.setVisible(false);
        priceFilterLessThanOrEqual.setVisible(false);
        filter.setVisible(false);

        // Set default values for price filters
        priceFilterMoreThan.setValue(0.0);
        priceFilterLessThanOrEqual.setValue(100.0);


        // Hook logic to components

        // Replace listing with filtered content when user changes filter
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(e -> listItems(e.getValue()));

        // Change products ordering when new filter option is selected
        selectFilterCategory.addValueChangeListener(e -> listItems(""));
        selectFilterCategory.addValueChangeListener(e -> changeFilterVisibility());
        selectFilterCategory.addValueChangeListener(e -> changePriceFilterVisibility());

        // Add listeners for price filters
        priceFilterLessThanOrEqual.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilterLessThanOrEqual.addValueChangeListener(e -> listItems(null));

        priceFilterMoreThan.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilterMoreThan.addValueChangeListener(e -> listItems(null));


        // Connect selected Item to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editItem(e.getValue());
        });

        // Instantiate and edit new Item the new button is clicked
        addNewBtn.addClickListener(e -> editor.editItem(new Item("", 0.0, "", 0)));

        // Listen to changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            listItems(filter.getValue());

            //update totalQuantity label each time something changes
            totalQuantity.setText("Total items: " + repo.getTotalQuantity());
        });

        // Initialize listing
        listItems(null);
    }

    void listItems(String filterText) {
        if (selectFilterCategory.getValue().equals("Show all")) {
            grid.setItems(repo.findAll());
        } else if (selectFilterCategory.getValue().equals("5 last added")) {
            grid.setItems(repo.findTop5ByOrderByIdDesc());
        } else if (selectFilterCategory.getValue().equals("By price")) {
            grid.setItems(repo.findAll());
            double temp1 = priceFilterLessThanOrEqual.getValue();
            double temp2 = priceFilterMoreThan.getValue();
            grid.setItems(repo.findByPriceLessThanEqualAndPriceGreaterThanEqual(temp1, temp2));
        } else if (selectFilterCategory.getValue().equals("By categories")) {
            grid.setItems(repo.findByCategoryContainsIgnoreCase(filterText));
        } else {
            grid.setItems(repo.findAll());
        }
    }

    //         tag::listItems[]
//    void filterByPrice(Double minValue, Double maxValue) {
//
//         end::listItems[]

    // Hide filter text unless we filter by categories
    void changeFilterVisibility() {
        if (selectFilterCategory.getValue().equals("By categories")) {
            filter.setVisible(true);
        } else {
            filter.setValue("");
            filter.setVisible(false);
        }
    }

//    }

    void changePriceFilterVisibility() {
        if (selectFilterCategory.getValue().equals("By price")) {
            priceFilterMoreThan.setVisible(true);
            priceFilterLessThanOrEqual.setVisible(true);
        } else {
            priceFilterMoreThan.setVisible(false);
            priceFilterLessThanOrEqual.setVisible(false);
            priceFilterMoreThan.setValue(0.0);
            priceFilterLessThanOrEqual.setValue(100.0);
        }
    }
    }

