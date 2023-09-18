package service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import pojo.Day;
import repository.OpeningHoursCSVhandler;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class PickupTime {
    
    private Day[] workingDays;
    private LocalDateTime now;
    private LocalDateTime pickUpTime;
    private int totalWorkMinutes; // this class is independent of type order, and calculates with minutes instead of hours
    private int dayCounter = 0;

    private final static Path openingTimesPath = Paths.get("data\\PhotoShop_OpeningHours.csv");

    public PickupTime(int totalWorkHours) {
        OpeningHoursCSVhandler csvReader = new OpeningHoursCSVhandler(openingTimesPath);
        workingDays = csvReader.readFile();
        this.now = LocalDateTime.now();
        this.totalWorkMinutes = totalWorkHours * 60;
        setPickUpTime();
    }
    public LocalDateTime getPickUpTime() {
        return this.pickUpTime;
    }

    private void setPickUpTime() {

        // find today's index
        int dayIndex = getStartDay();

        // get the opening and closing hour for the day
        LocalDateTime openingTime = LocalDateTime.of(now.toLocalDate(), workingDays[dayIndex].getOpeningTime());
        LocalDateTime closingTime = LocalDateTime.of(now.toLocalDate(), workingDays[dayIndex].getClosingTime());

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
        LocalDateTime openingTime = LocalDateTime.of(now.toLocalDate(), workingDays[dayIndex].getOpeningTime());
        LocalDateTime closingTime = LocalDateTime.of(now.toLocalDate(), workingDays[dayIndex].getClosingTime());

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
            if (dayIndex == workingDays.length) {
                dayIndex = 0;
            }
            pickUpTime = pickupTime(productionMinutes, dayIndex);
        }
        return pickUpTime;
    }

    private int getStartDay() {
        // find at which day (row of CSV) to start calculating
        for (int i = 0; i < workingDays.length; i++) {
            if (workingDays[i].getDayName().equals(now.getDayOfWeek())) {
                return i;
            }
        }
        return -1; // TODO this should not happen
    }

}
