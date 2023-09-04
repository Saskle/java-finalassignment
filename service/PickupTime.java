package service;

import java.time.LocalDateTime;

import pojo.OpeningHours;

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
        calculatePickUpTime2();
    }

    public LocalDateTime getPickupTime() {
        return this.pickupTime;
    }

        // decide which day it is (where to start in the 2d array)
        // check if the shop is still open (now.gethours > closing time)
        // if yes, substract the amount of hours left of productionTime and go to the next day
        // if no, go to the next day
        // when productionTime hits 0, that is the completionTime

        
        // right now, it adds the hours correctly but does not move on to the next day when working hours exceed closing time

    private void calculatePickUpTime() {
        System.out.println("Total work hours for this order is: " + totalWorkHours);

        // iterate through each row of 2d array and fetch weekday + workingHours
        for (int i = 0; i < openingHours.length; i++) { // row

            // break the for loop when totalWorkHours is empty
            if (totalWorkHours <= 0) {
                break;
            }

            if (openingHours[i][0].equals(now.getDayOfWeek().toString())) { // if we are at the right weekday (today)

                // split the string on ":" -> index 0 is hours and index 1 is minutes
                String[] beginHours = openingHours[i][1].split(":"); // openihg time
                int beginHour = Integer.parseInt(beginHours[0]) + (Integer.parseInt(beginHours[1]) / 60); // this is useless but think about implementing 09:30 and things
                String[] closeHours = openingHours[i][2].split(":");
                int closeHour = Integer.parseInt(closeHours[0]) + (Integer.parseInt(closeHours[1]) / 60);
                // TODO catch exceptions here for non-integers

                while (totalWorkHours > 0) {
                    if (now.getHour() > beginHour && now.getHour() < closeHour) { // current hour is during opening time
                        int workHoursInThisDay = closeHour - now.getHour();
                        totalWorkHours -= workHoursInThisDay;
                        System.out.println(totalWorkHours + " left to do!");

                    } else { // shop is closed, move to next day?
                        break; 
                    }
                }
            }
            
        }
        System.out.println("Pickup time is " + now);
        // format this correclty
    }

    private void calculatePickUpTime2() {

        // initializing hour count (which we will substract until 0)
        int productionHours = totalWorkHours;

        // find today's index
        int dayIndex = findStartDay();

        // get the opening and closing hour for the day 
        int openingHour = findOpeningtime(dayIndex, 1);
        int closeHour = findOpeningtime(dayIndex, 2);

        // check if the shop is still open today (between opening hours)
        if (now.getHour() >= openingHour && now.getHour() < closeHour) { 

            pickupTime = pickupTime(productionHours, dayIndex);

            // int workHoursInThisDay = closeHour - now.getHour();
            // productionHours -= workHoursInThisDay;
            // if (productionHours <= 0) {
            //     // pick it up the same day at now + totalworkhours
            //     pickupTime = now.plusHours(totalWorkHours);
        } else {
            // if the shop's already closed, go to next day
            dayCounter++;
            dayIndex++;
            pickupTime = pickupTime(productionHours, dayIndex);
        }
            

            // if yes, substract the amount of hours left of productionTime and go to the next day
            // if no, go to the next day

         }

    private int findStartDay() {
        // find at which day (row of CSV) to start calculating
        for (int i = 0; i < openingHours.length; i++) {
            if (openingHours[i][0].equals(now.getDayOfWeek().toString())) {
                return i;
            }
        }
        return -1; // TODO this should not happen
    }

    private int findOpeningtime(int dayIndex, int index) {
        // get the opening and closing hour for the day (split the string on ":" -> index 0 is hours and index 1 is minutes)
        String[] beginHours = openingHours[dayIndex][index].split(":"); // opening time
        return Integer.parseInt(beginHours[0]) + (Integer.parseInt(beginHours[1]) / 60); // this is useless but think about implementing 09:30 and things

        // TODO catch exceptions here for non-integers

    }

    private LocalDateTime pickupTime(int totalWorkHours, int dayIndex) {

        int productionHours = totalWorkHours;
        
        // get the opening and closing hour for the day (first column is opening hour, second is closing hour)
        int openingHour = findOpeningtime(dayIndex, 1);
        int closeHour = findOpeningtime(dayIndex, 2);

        // calculate amount of work hours in this day and substract
        int workHoursInThisDay = closeHour - openingHour;
        productionHours -= workHoursInThisDay;
        if (productionHours <= 0) {
            // pick it up at calculated time + count of days
            pickupTime = now.plusHours(totalWorkHours);

            // if the pickup time is before opening, set it to opening time of this day
            if (pickupTime.getHour() < openingHour) {
                pickupTime = LocalDateTime.of(pickupTime.getYear(), pickupTime.getMonth(), pickupTime.getDayOfMonth(), openingHour, 0, pickupTime.getSecond());
            }
            // add amount of days passed (TODO what happens when it's sunday?)
            pickupTime = pickupTime.plusDays(dayCounter);
            return pickupTime;
        } else {
            // go to next day
            // parameters totalWorkHours (left) + (dayindex + 1)
            dayCounter++;
            dayIndex++;
            pickupTime = pickupTime(productionHours, dayIndex);
        }

        return pickupTime;
    }
}
