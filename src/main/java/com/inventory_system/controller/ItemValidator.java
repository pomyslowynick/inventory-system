package com.inventory_system.controller;

import com.inventory_system.model.Item;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class c) {
        return Item.class.equals(c);
    }

    @Override
    public void validate(Object target, Errors errors) {

    }
}
//        @Override
//        public void validate(Object object, Errors errors) {
//            Item myClass = (Item) object;
//
//            if (Item.getA() == null) {
//                errors.rejectValue("avalue", "avalue.empty", "'A' value cannot be empty");
//            }
//            else if (Item.getA() == true && (myClass.getB() == null || myClass.getB() < 0)) {
//                errors.rejectValue("bvalue", "bvalue.notvalid", "'B' value is not valid");
//            }
//        }

