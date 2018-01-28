package com.n00blife.parkalot;

/**
 * Created by arvind on 1/27/18.
 */

public class TimeSlot {

    private String slotName;
    private boolean isSlotBooked;
    private boolean isSelected;

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public TimeSlot(String slotName, boolean isSlotBooked) {
        this.slotName = slotName;
        this.isSlotBooked = isSlotBooked;
    }

    public String getTimeSlotName() {
        return slotName;
    }

    public boolean isSlotBooked() {
        return isSlotBooked;
    }

    public void setSlotBooked(boolean slotBooked) {
        isSlotBooked = slotBooked;
    }
}
