package com.inventory_system.services;

import com.inventory_system.model.Item;
import com.inventory_system.repositories.ItemRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * This class serves ass a service for editing UI and provides some validation with Vaadin's binder validators.
 * There is some basic exception handling for validation errors, but it should be developed way more, would od it If I had time.
 */
@SpringComponent
@UIScope
public class InventoryEditorImpl extends VerticalLayout implements KeyNotifier, InventoryEditor {

    private final ItemRepository itemRepository;
    private Item item;

    /* Fields to edit properties in Item entity */
    TextField name = new TextField("Name");
    NumberField price = new NumberField("Price");
    TextField category = new TextField("Category");
    TextField quantity= new TextField("Quantity");

    /* Action buttons */
    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button reset = new Button("Reset");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete, reset);
    Notification notification = new Notification("Error has occured, can't add new item.", 3000);

    Binder<Item> binder = new Binder<>(Item.class);
    private ChangeHandler changeHandler;

    @Autowired
    public InventoryEditorImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
        setupBinders();
        setupListeners();
    }

    void setupListeners() {

        add(name, price, category, quantity, actions);

        // Configure and style components
        setSpacing(true);

        // Show error notification if can't create new Item
    /*
        Could use exact error messags, consider implementing when I have more time.
     */
        try {
            save.getElement().getThemeList().add("primary");
        } catch(ConstraintViolationException error) {
            save.addClickListener(e -> notification.open());
        }

        delete.getElement().getThemeList().add("error");

        addKeyPressListener(Key.ENTER, e -> save());

        // wire action buttons to save, delete and reset
        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        reset.addClickListener(e -> editItem(item));
        cancel.addClickListener(e -> setVisible(false));
        setVisible(false);
    }


    void setupBinders() {
        // bind name
        binder.forField(name)
                .withValidator(name -> name.length() > 2,
                        "Name length must be at least two characters long.")
                .bind(Item::getName, Item::setName);
        binder.readBean(item);

        // bind price
        binder.forField(price)
                .withValidator(price -> price > 0.0,
                        "Price can't be negative or zero.")
                .bind(Item::getPrice, Item::setPrice);
        binder.readBean(item);

        // bind quantity
        binder.forField(quantity)
                .withConverter(new StringToIntegerConverter("Must be integer"))
                .withValidator(quantity -> quantity >= 0 && quantity <= 5,
                        "Quantity must be a number between 0 and 5 inclusive.")
                .withValidator(quantity -> (quantity + itemRepository.getTotalQuantity() <= 200),
                        "You can't have more than 200 items.")
                .bind(Item::getQuantity, Item::setQuantity);
        binder.readBean(item);

        // bind using naming convention
        binder.bindInstanceFields(this);
    }

    void delete() {
        itemRepository.delete(item);
        changeHandler.onChange();
    }

    void save() throws ConstraintViolationException {
        try {
            if(itemRepository.getTotalQuantity() + item.getQuantity() > 200) {
                throw new Exception();
            }
            itemRepository.save(item);
            changeHandler.onChange();
        } catch (Exception e) {
            notification.open();
        }
    }

    public interface ChangeHandler {
        void onChange();
    }

    public final void editItem(Item c) {
        if (c == null) {
            setVisible(false);
            return;
        }
        final boolean persisted = c.getId() != null;
        if (persisted) {
            // Find fresh entity for editing
            item = itemRepository.findById(c.getId()).get();
        }
        else {
            item = c;
        }
        reset.setVisible(persisted);

        // Bind Item properties to similarly named fields
        // Could also use annotation or "manual binding" or programmatically
        // moving values from fields to entities before saving
        binder.setBean(item);

        setVisible(true);

        // Focus first name initially
        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        // ChangeHandler is notified when either save or delete
        // is clicked
        changeHandler = h;
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public void handleSaveException() {
        save.addClickListener(e -> notification.open());
    }
}
