package service;

import java.time.LocalDateTime;

import pojo.OpeningHours;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTime {
    
    // private OpeningHours openingHours;
    private LocalDateTime now;
    private LocalDateTime pickupTime;
    private int totalWorkHours; // lets make this class independent of type order

        // hardcoded data, remove when CSVHandler works (and add OpeningHours)
        public String[][] openingHours = new String[][] {
            { "SUNDAY", "00:00", "00:00" },
            { "MONDAY", "09:00", "18:00" },
            { "TUESDAY", "09:00", "18:00" },
            { "WEDNESSDAY", "09:00", "18:00" },
            { "THURSDAY", "09:00", "18:00" },
            { "FRIDAY", "09:00", "21:00" },
            { "SATURDAY", "09:00", "16:00" }
        };

    public PickupTime(int totalWorkHours) {
        calculatePickUpTime();
    }

    private void calculatePickUpTime() {

    }

}
