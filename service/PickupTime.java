package service;

import java.time.LocalDateTime;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTime {
    
    // private OpeningHours openingHours;
    private LocalDateTime now;
    private LocalDateTime pickUpTime;
    private int totalWorkMinutes; // this class is independent of type order, and calculates with minutes instead of hours
    private int dayCounter = 0;

    // hardcoded data, remove when CSVHandler works (and add OpeningHours)
    public String[][] openingHours = new String[][] {
            { "SUNDAY", "00:00", "00:00" },
            { "MONDAY", "09:00", "18:00" },
            { "TUESDAY", "10:00", "18:00" },
            { "WEDNESDAY", "09:00", "18:00" },
            { "THURSDAY", "09:00", "18:00" },
            { "FRIDAY", "09:00", "21:00" },
            { "SATURDAY", "09:00", "16:00" }
    };

    public PickupTime(int totalWorkHours) {
        this.now = LocalDateTime.now();
        this.totalWorkMinutes = totalWorkHours * 60;
        setPickUpTime();
    }

    public LocalDateTime getPickUpTime() {
        return this.pickUpTime;
    }

    /*
    private void setPickUpTime() {

        // find today's index
        int dayIndex = getStartDay();

        // get the opening and closing hour for the day 
        double openingHour = getOpeningtime(dayIndex, 1);
        double closeHour = getOpeningtime(dayIndex, 2);

        // check if the shop is open right now (between opening hours)
        if (now.getHour() >= openingHour && now.getHour() < closeHour) { 

            // if yes, calculate the remaining working hours
            // double hoursRemaining = now.getHour() - openingHour;
            double hoursRemaining = closeHour - now.getHour() - (now.getMinute() / 60);
            
            // if there is enought time left today to complete it, return today, otherwise substract and go to the next day
            if (hoursRemaining >= totalWorkHours) {
                pickUpTime = now.plusHours(totalWorkHours);
            } else {
                dayCounter++;
                dayIndex++;
                pickUpTime = pickupTime(totalWorkHours - hoursRemaining, dayIndex);
            }
            
        } else if (now.getHour() < openingHour) {
            // if the shop is not yet open, start calculating from the beginning
            pickUpTime = pickupTime(totalWorkHours, dayIndex);

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
    } */

     private void setPickUpTime() {

        // find today's index
        int dayIndex = getStartDay();

        // get the opening and closing hour for the day
        LocalDateTime openingTime = getShopTime(dayIndex, 1);
        LocalDateTime closingTime = getShopTime(dayIndex, 2);

        // check if the shop is open right now (between opening hours)
        if (now.isAfter(openingTime) && now.isBefore(closingTime)) { 

            // if yes, calculate the remaining working hours (in minutes)
            int minutesRemaining = (closingTime.getHour() - now.getHour()) * 60 - now.getMinute();
            
            // if there is enought time left today to complete it, return today, otherwise substract and go to the next day
            if (minutesRemaining >= totalWorkMinutes) {
                pickUpTime = now.plusMinutes(totalWorkMinutes);
            } else {
                dayCounter++;
                dayIndex++;
                pickUpTime = pickupTime(totalWorkMinutes - minutesRemaining, dayIndex);
            }
            
        } else if (now.isBefore(openingTime)) {
            // if the shop is not yet open, start calculating from the beginning
            pickUpTime = pickupTime(totalWorkMinutes, dayIndex);

        } else {
            // if the shop's already closed, go to next day
            dayCounter++;
            dayIndex++;
            pickUpTime = pickupTime(totalWorkMinutes, dayIndex);
        }
    } 

    private LocalDateTime pickupTime(int totalWorkMinutes, int dayIndex) {

        // setting a new variable to mutate, so we still have access to the original value
        int productionMinutes = totalWorkMinutes;
        
        // get the opening and closing times for the day (first column is opening hour, second is closing hour)
        LocalDateTime openingTime = getShopTime(dayIndex, 1);
        LocalDateTime closingTime = getShopTime(dayIndex, 2);

        // calculate amount of work minutes in this day and substract production time
        int workMinutesInThisDay = (closingTime.getHour() - openingTime.getHour()) * 60 + (closingTime.getMinute() - openingTime.getMinute());
        productionMinutes -= workMinutesInThisDay;

        if (productionMinutes <= 0) {
            // if the work fits this day, set pickUpTime to today and right opening hour
            pickUpTime = LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), openingTime.getHour(), openingTime.getMinute());
            
            // add the remaining minutes and amount of days looped through to the pickup time 
            pickUpTime = pickUpTime.plusMinutes(totalWorkMinutes);
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
            pickUpTime = pickupTime(productionMinutes, dayIndex);
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

    private LocalDateTime getShopTime(int dayIndex, int index) {
        // get the opening and closing hour for the day (split the string on ":" -> index 0 is hours and index 1 is minutes)
        String[] workingHours = openingHours[dayIndex][index].split(":");
        
        int openingHour = 0;
        int openingMinute = 0;

        try {
            openingHour = Integer.parseInt(workingHours[0]);
            openingMinute = Integer.parseInt(workingHours[1]);
        } catch (NumberFormatException exception) {
            System.out.println(exception);
        }

        return LocalDateTime.of(now.getYear(), now.getMonth(), now.getDayOfMonth(), openingHour, openingMinute);
    }
}
