package service;

import java.time.LocalDateTime;

import pojo.Day;
import repository.OpeningHoursCSVreader;
import repository.PickUpTimeJSONhandler;

// ----------------- PURPOSE: Calculate pickuptime based on current time, shop opening times and product creation hours ---------------

public class ScheduleService {
    
    private Day[] workingDays;
    private LocalDateTime pickUpTime;
    private int totalWorkMinutes; // calculates with minutes instead of hours
    private int dayCounter = 0;

    private LocalDateTime startTime;

    private PickUpTimeJSONhandler jsonHandler;
    private OpeningHoursCSVreader csvReader;

    public ScheduleService(int totalWorkHours) {
        // initialisation of CSVreader, reading opening times
        csvReader = new OpeningHoursCSVreader();
        workingDays = csvReader.readCSV();

        // initialisation of JSONhandler, last pickup time = start time for this order
        jsonHandler = new PickUpTimeJSONhandler();
        startTime = jsonHandler.readJSON(); 

        // if last pickup time has passed, work from now
        if (startTime.isBefore(LocalDateTime.now())) {
            startTime = LocalDateTime.now();
        }

        totalWorkMinutes = totalWorkHours * 60;
    }

    public LocalDateTime calculatePickUpTime() {
        // find today's index, set minutesRemaining
        int dayIndex = getStartDayIndex();
        int minutesRemaining = 0;

        // get the opening and closing hour for the day 
        LocalDateTime openingTime = LocalDateTime.of(startTime.toLocalDate(), workingDays[dayIndex].getOpeningTime());
        LocalDateTime closingTime = LocalDateTime.of(startTime.toLocalDate(), workingDays[dayIndex].getClosingTime());

        // calulating the remaining work time in this day, taking opening times into account
        if (startTime.isAfter(openingTime)) {
            // calculate the remaining working hours (in minutes)
            minutesRemaining = (closingTime.getHour() - startTime.getHour()) * 60 - startTime.getMinute();
        } else {
            minutesRemaining = (closingTime.getHour() - openingTime.getHour()) * 60 + (closingTime.getMinute() - openingTime.getMinute());
        }

        // if there is enought time left in the day to complete it, return today, otherwise substract and go to the next day
        if (minutesRemaining >= totalWorkMinutes) {
            pickUpTime = startTime.plusMinutes(totalWorkMinutes);
        } else {
            dayCounter++;
            dayIndex++;
            pickUpTime = pickUpTime(totalWorkMinutes - minutesRemaining, dayIndex);
        }

        // save calculated pick up time as latest in JSON
        jsonHandler.saveJSON(pickUpTime);
        return pickUpTime;
    }

    private LocalDateTime pickUpTime(int totalWorkMinutes, int dayIndex) {

        // if it's Saturday, start at the beginning again
        if (dayIndex == workingDays.length) {
            dayIndex = 0;
        }

        // setting a new variable to mutate, so we still have access to the original value
        int productionMinutes = totalWorkMinutes;
        
        // get the opening and closing times for the day
        LocalDateTime openingTime = LocalDateTime.of(startTime.toLocalDate(), workingDays[dayIndex].getOpeningTime());
        LocalDateTime closingTime = LocalDateTime.of(startTime.toLocalDate(), workingDays[dayIndex].getClosingTime());

        // calculate amount of work minutes in this day and substract production time
        int workMinutesInThisDay = (closingTime.getHour() - openingTime.getHour()) * 60 + (closingTime.getMinute() - openingTime.getMinute());
        productionMinutes -= workMinutesInThisDay;

        if (productionMinutes <= 0) {
            // if the work fits this day, set pickUpTime to today and right opening hour
            pickUpTime = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), openingTime.getHour(), openingTime.getMinute());
            
            // add the remaining minutes and amount of days looped through to the pickup time 
            pickUpTime = pickUpTime.plusMinutes(totalWorkMinutes);
            pickUpTime = pickUpTime.plusDays(dayCounter);
        } else {
            // add a day to the index and counter and recalculate
            dayCounter++;
            dayIndex++;
            pickUpTime = pickUpTime(productionMinutes, dayIndex);
        }
        return pickUpTime;
    }

    private int getStartDayIndex() {
        // find at which day (row of CSV) to start calculating
        for (int i = 0; i < workingDays.length; i++) {
            if (workingDays[i].getDayName().equals(startTime.getDayOfWeek())) {
                return i;
            }
        }
        return -1; 
    }
}
