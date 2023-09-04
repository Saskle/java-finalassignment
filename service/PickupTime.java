package service;

import java.time.LocalDateTime;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTime {
    
    // private OpeningHours openingHours;
    private LocalDateTime now;
    private LocalDateTime pickUpTime;
    private int totalWorkHours; // lets make this class independent of type order
    private int dayCounter = 0;

    // hardcoded data, remove when CSVHandler works (and add OpeningHours)
    public String[][] openingHours = new String[][] {
            { "SUNDAY", "00:00", "00:00" },
            { "MONDAY", "09:00", "18:00" },
            { "TUESDAY", "09:30", "18:00" },
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

    public LocalDateTime getPickUpTime() {
        return this.pickUpTime;
    }

    private void setPickUpTime() {

        // initializing hour count (which we will substract until 0)
        //double productionHours = totalWorkHours;
        // can I remove this? am I substracting?

        // find today's index
        int dayIndex = getStartDay();

        // get the opening and closing hour for the day 
        double openingHour = getOpeningtime(dayIndex, 1);
        double closeHour = getOpeningtime(dayIndex, 2);

        // check if the shop is still open right now (between opening hours)
        if (now.getHour() >= openingHour && now.getHour() < closeHour) { 
            double hoursRemaining = now.getHour() - openingHour;
            
            // if there is enought time left today to complete it, return today, otherwise substract and go to the next day
            if (hoursRemaining >= totalWorkHours) {
                pickUpTime = now.plusHours(totalWorkHours);
            } else {
                dayCounter++;
                dayIndex++;
                pickUpTime = pickupTime(totalWorkHours - hoursRemaining, dayIndex);
            }
            
        } else {
            // if the shop's already closed, go to next day
            dayCounter++;
            dayIndex++;
            pickUpTime = pickupTime(totalWorkHours, dayIndex);
        }
    }

    private LocalDateTime pickupTime(double totalWorkHours, int dayIndex) {

        // setting a new variable to mutate, so we still have access to the original value
        double productionHours = totalWorkHours;
        
        // get the opening and closing hour for the day (first column is opening hour, second is closing hour)
        double openingHour = getOpeningtime(dayIndex, 1);
        double closeHour = getOpeningtime(dayIndex, 2);

        // calculate amount of work hours in this day and substract production time
        double workHoursInThisDay = closeHour - openingHour;
        productionHours -= workHoursInThisDay;

        if (productionHours <= 0) {
            // if the work fits this day, set pickUpTime to today and right opening hour
            int minutesToAdd = (int)(totalWorkHours * 60);
            pickUpTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), (int)openingHour, now.getMinute());
            
            // add the remaining hours (in minutes) and days to the pickup time 
            pickUpTime = pickUpTime.plusMinutes(minutesToAdd);
            pickUpTime = pickUpTime.plusDays(dayCounter);
            return pickUpTime;
        } else {
            // add a day to the index and counter and recalculate
            dayCounter++;
            dayIndex++;

            // if it's Saturday, start at the beginning again
            if (dayIndex == openingHours.length) {
                dayIndex = 0;
            }
            pickUpTime = pickupTime(productionHours, dayIndex);
        }
        return pickUpTime;
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

    private double getOpeningtime(int dayIndex, int index) {

        // get the opening and closing hour for the day (split the string on ":" -> index 0 is hours and index 1 is minutes)
        String[] workingHours = openingHours[dayIndex][index].split(":");

        try {
            return Double.parseDouble(workingHours[0]) + (Double.parseDouble(workingHours[1]) / 60);
            //return Integer.parseInt(workingHours[0]) + (Integer.parseInt(workingHours[1]) / 60);
        } catch (NumberFormatException e) {
            System.out.println(e);
        }
        return -1;

    }
}
