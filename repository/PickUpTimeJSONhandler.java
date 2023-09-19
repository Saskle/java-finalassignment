package repository;

import java.io.File;
import java.time.LocalDateTime;

public class PickUpTimeJSONhandler extends JSONhandler {
    
    // TODO check if the file actually exists, and if it doesn't, create a new one?

    public void saveJSON(LocalDateTime pickUpTime) {
        file = new File("data\\latestPickUpTime.json");
        try {
            mapper.writeValue(file, pickUpTime);    
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    public LocalDateTime readJSON() {
        file = new File("data\\latestPickUpTime.json");
        try {
            return mapper.readValue(file, LocalDateTime.class);    
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null; // TODO is this safe? 
    }
}
