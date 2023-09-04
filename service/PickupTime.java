package service;

import java.time.LocalDateTime;

import javax.swing.text.html.StyleSheet;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTime {
    
    // private OpeningHours openingHours;
    private LocalDateTime now;
    private LocalDateTime pickupTime;
    private int totalWorkHours; // lets make this class independent of type order
    private int dayCounter = 0;

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
        this.now = LocalDateTime.now();
        this.totalWorkHours = totalWorkHours;
        setPickUpTime();
    }

    public LocalDateTime getPickupTime() {
        return this.pickupTime;
    }

    private void setPickUpTime() {

        // initializing hour count (which we will substract until 0)
        int productionHours = totalWorkHours;

        // find today's index
        int dayIndex = getStartDay();

        // get the opening and closing hour for the day 
        int openingHour = getOpeningtime(dayIndex, 1);
        int closeHour = getOpeningtime(dayIndex, 2);

        // check if the shop is still open today (between opening hours)
        if (now.getHour() >= openingHour && now.getHour() < closeHour) { 
            pickupTime = pickupTime(productionHours, dayIndex);
        } else {
            // if the shop's already closed, go to next day
            dayCounter++;
            dayIndex++;
            pickupTime = pickupTime(productionHours, dayIndex);
        }
    }

    private LocalDateTime pickupTime(int totalWorkHours, int dayIndex) {

        // setting a new variable to mutate, so we still have access to the original value
        int productionHours = totalWorkHours;
        
        // get the opening and closing hour for the day (first column is opening hour, second is closing hour)
        int openingHour = getOpeningtime(dayIndex, 1);
        int closeHour = getOpeningtime(dayIndex, 2);

        // calculate amount of work hours in this day and substract production time
        int workHoursInThisDay = closeHour - openingHour;
        productionHours -= workHoursInThisDay;

        if (productionHours <= 0) {
            // if the work fits this day, add the remaining hours to the pickup time
            pickupTime = now.plusHours(totalWorkHours);

            // if the pickup time is before opening (productionHours < 0), set it to opening time of this day
            if (pickupTime.getHour() < openingHour) {
                pickupTime = LocalDateTime.of(pickupTime.getYear(), pickupTime.getMonth(), pickupTime.getDayOfMonth(), openingHour, 0, pickupTime.getSecond());
            }
            // add amount of days passed (TODO what happens when it's sunday?)
            pickupTime = pickupTime.plusDays(dayCounter);
            return pickupTime;
        } else {
            // add a day to the index and counter and recalculate
            dayCounter++;
            dayIndex++;
            pickupTime = pickupTime(productionHours, dayIndex);
        }
        return pickupTime;
    }

    private int getStartDay() {
        // find at which day (row of CSV) to start calculating
        for (int i = 0; i < openingHours.length; i++) {
            if (openingHours[i][0].equals(now.getDayOfWeek().toString())) {
                return i;
            }
        }
        return -1; // TODO this should not happen
    }

    private int getOpeningtime(int dayIndex, int index) {

        // get the opening and closing hour for the day (split the string on ":" -> index 0 is hours and index 1 is minutes)
        String[] workingHours = openingHours[dayIndex][index].split(":");

        try {
            // TODO this is useless but think about implementing 09:30 and things
            return Integer.parseInt(workingHours[0]) + (Integer.parseInt(workingHours[1]) / 60);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return -1;

    }
}
