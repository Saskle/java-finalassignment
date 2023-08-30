package service;

import java.time.LocalDateTime;

import pojo.OpeningHours;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTimeCalculator {
    
    private OpeningHours openingHours;
    private LocalDateTime now;
    private LocalDateTime pickupTime;
    private int totalWorkHours; // lets make this class independent of type order

    public PickupTimeCalculator(int totalWorkHours) {
        
    }


}
