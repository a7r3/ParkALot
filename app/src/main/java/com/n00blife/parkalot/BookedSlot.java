package com.n00blife.parkalot;

/**
 * Created by arvind on 1/28/18.
 */

public class BookedSlot {

    private String amount;
    private boolean hasPaid;

    public BookedSlot(String amount, boolean hasPaid) {
        this.amount = amount;
        this.hasPaid = hasPaid;
    }

    public String getAmount() {
        return amount;
    }

    public boolean isHasPaid() {
        return hasPaid;
    }
}
