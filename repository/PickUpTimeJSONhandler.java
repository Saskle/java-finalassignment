package repository;

import java.io.File;
import java.time.LocalDateTime;

public class PickUpTimeJSONhandler extends JSONhandler<LocalDateTime> {
    
    // TODO check if the file actually exists, and if it doesn't, create a new one?

    private void hasPickUpTime() {
        file = new File("data\\latestPickUpTime.json");
        if (!file.exists()) {
            // cycle through orders and find latest pickup time
        }
    }

    @Override
    public void saveJSON(LocalDateTime pickUpTime) {
        file = new File("data\\latestPickUpTime.json");
        try {
            mapper.writeValue(file, pickUpTime);    
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    @Override
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
