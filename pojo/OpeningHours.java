package pojo;

import java.time.LocalDateTime;
import java.util.HashMap;

// ----------------- PURPOSE: Handle & validate Opening Hour data from repository -----------------

public class OpeningHours {

    // hardcoded data, remove when CSVHandler works
    private String[][] openingHours = new String[][] {
        { "SUNDAY", "00:00", "00:00" },
        { "MONDAY", "09:00", "18:00" },
        { "TUESDAY", "09:00", "18:00" },
        { "WEDNESSDAY", "09:00", "18:00" },
        { "THURSDAY", "09:00", "18:00" },
        { "FRIDAY", "09:00", "21:00" },
        { "SATURDAY", "09:00", "16:00" }
    };

    private HashMap<String, Integer> workingHours;

    public OpeningHours(Order order) {
        this.workingHours = new HashMap<>();
        calculateWorkHours(order);
    }

        // decide which day it is (where to start in the hash table)
        // check if the shop is still open
        // if yes, substract the amount of hours left of productionTime and go to the next day
        // if no, go to the next day
        // when productionTime hits 0, that is the completionTime

        
        // right now, it adds the hours correctly but does not move on to the next day when working hours exceed closing time

    private void calculateWorkHours(Order order) {
        int totalWorkHours = 0;

        for (Product product : order.getAllProducts()) {
            totalWorkHours =+ product.getCreatingHours();
        }
        System.out.println("Total work hours for this order is: " + totalWorkHours);
        
        LocalDateTime now = LocalDateTime.now();

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
                String [] closeHours = openingHours[i][2].split(":");
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

    // TODO this is already in invoice, DRY!!!
    public void calculateProductionHours(Order order) {
        int totalWorkHours = 0;
        int calculatedTime = 0;

        // TODO remove this
        for (Product product : order.getAllProducts()) {
            totalWorkHours =+ product.getCreatingHours();
        }
        System.out.println("Total work hours for this order is: " + totalWorkHours);

        LocalDateTime now = LocalDateTime.now();
        // this.completionTime = now.plusHours((long)totalWorkHours);

        

        int index = 0; // which day to start
        for (int i = 0; i < openingHours.length; i++) { // lamda?
            if (openingHours[0][i].equals(now.getDayOfWeek().toString())) index = i;
        }

        for (int j = 0; j < openingHours.length; j++) {
            
        }
        // int beginHour = Integer.parseInt(openingHours[1][i]) + (Integer.parseInt(openingHours[1][i]) / 60); // this needs to be done bettter
        //         if (now.getHour() > beginHour) {
        //             System.out.println("The shop is still open.");
        //             // shop is open
        //         } else {
        //             System.out.println("Shop is closed");
        //             // go to next day
        //         }
    }
    

}