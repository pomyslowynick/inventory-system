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
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.web.bind.annotation.RequestMapping;


@Route
@RequestMapping(MainView.BASE_URL)
public class MainView extends VerticalLayout {

    public static final String BASE_URL = "api/v1/main";

    private final ItemRepository repo;

    private final InventoryEditingControllerImpl editor;

    final Select<String> selectFilterCategory;

    final Select<String> selectCategory;

    final Label totalQuantity;

    final Grid<Item> grid;

    final NumberField priceFilterMoreThan;

    final NumberField priceFilterLessThanOrEqual;

    private final Button addNewBtn;

    public MainView(ItemRepository repo, InventoryEditingControllerImpl editor) {
        this.repo = repo;
        this.editor = editor;
        this.priceFilterMoreThan = new NumberField();
        this.priceFilterLessThanOrEqual = new NumberField();
        this.grid = new Grid<>(Item.class);
        this.totalQuantity = new Label("Total items: " + repo.getTotalQuantity());
        this.addNewBtn = new Button("New item", VaadinIcon.PLUS.create());
        this.selectFilterCategory = new Select<>("Show all", "By price", "By categories", "5 last added");
        this.selectCategory = new Select<String>();
        selectCategory.setItems(repo.getAllCategories());

        buildUI();
    }


    void buildUI() {
        // Build layout
        grid.setHeight("300px");
        HorizontalLayout actions = new HorizontalLayout(selectFilterCategory, selectCategory, priceFilterMoreThan,
                priceFilterLessThanOrEqual, addNewBtn, totalQuantity);
        add(actions, grid, editor);

        grid.setHeight("300px");
        grid.setColumns("id", "name", "price", "category", "quantity");
        grid.getColumnByKey("id").setWidth("50px").setFlexGrow(0);

        // Dropdown for filtering options
        selectFilterCategory.setPlaceholder("Filter by...");
        selectFilterCategory.setValue("Show all");

        // Set price, text, category filters to be not visible at start
        priceFilterMoreThan.setVisible(false);
        priceFilterLessThanOrEqual.setVisible(false);
        selectCategory.setVisible(false);

        // Set default category for category filter
        /*
            This is hardcoded value, if I will have time I will change to to custom query that returns
            first category.
         */
        selectCategory.setValue("DIY");

        // Set default values for price filters
        priceFilterMoreThan.setValue(0.0);
        priceFilterLessThanOrEqual.setValue(100.0);



        // Change products ordering when new filter option is selected
        selectFilterCategory.addValueChangeListener(e -> listItems());

        // Change filters visibility when new filter is selected
        selectFilterCategory.addValueChangeListener(e -> changePriceFilterVisibility());
        selectFilterCategory.addValueChangeListener(e -> changeCategoryFilterVisibility());

        // Add listeners for price filters
        priceFilterLessThanOrEqual.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilterLessThanOrEqual.addValueChangeListener(e -> listItems());

        priceFilterMoreThan.setValueChangeMode(ValueChangeMode.EAGER);
        priceFilterMoreThan.addValueChangeListener(e -> listItems());

        // Add listeners for category select filter
        selectCategory.addValueChangeListener(e -> listItems());


        // Connect selected Item to editor or hide if none is selected
        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editItem(e.getValue());
        });

        // Instantiate and edit new Item the new button is clicked
        addNewBtn.addClickListener(e -> editor.editItem(new Item("", 0.0, "", 0)));

        // Listen to changes made by the editor, refresh data from backend
        editor.setChangeHandler(() -> {
            editor.setVisible(false);
//            listItems(filter.getValue());

            // Update totalQuantity label each time edit happens
            totalQuantity.setText("Total items: " + repo.getTotalQuantity());

            // Store default category
            String tempCategory = selectCategory.getValue();
            // Update the categories each time some edit happens
            selectCategory.setItems(repo.getAllCategories());


            // Setting default value again, gotta refactor that later
            selectCategory.setValue(tempCategory);
        });

        // Initialize listing
        listItems();
    }

    // Method to populate grid with filtered queries.
    void listItems() {
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
            grid.setItems(repo.findByCategoryEquals(selectCategory.getValue()));
        } else {
            grid.setItems(repo.findAll());
        }
    }


    // Hide filter text unless we filter by categories
    void changeCategoryFilterVisibility() {
        if (selectFilterCategory.getValue().equals("By categories")) {
            selectCategory.setVisible(true);
        } else {
            selectCategory.setVisible(false);
        }
    }

    // Hide price filters when "By price" is not selected
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

