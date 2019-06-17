package com.inventory_system.exceptions;

public class QuantityInputMismatch extends Exception {
    private int number;

    public QuantityInputMismatch(int x) {
        this.number = x;
    }

    public String toString() {
        return "Your number [" + number + "]" + " is outside acceptable range for quantity. Try numbers between 0 and 5 (Inclusive).";
    }}
