package repository;

import java.io.File;
import java.time.LocalDateTime;

public class PickUpTimeJSONhandler extends JSONhandler<LocalDateTime> {

    public PickUpTimeJSONhandler() {
        file = new File("data\\latestPickUpTime.json");
        if (!file.exists()) {
            // TODO cycle through orders and find latest pickup time
            // create a new json with latest pickup time
        }
    }

    @Override
    public void saveJSON(LocalDateTime pickUpTime) {
        try {
            mapper.writeValue(file, pickUpTime);    
        } catch (Exception exception) {
            System.out.println(exception);
        }
    }

    @Override
    public LocalDateTime readJSON() {
        try {
            return mapper.readValue(file, LocalDateTime.class);    
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null; // all exceptions are being caught, so I suppose this never happens
    }
}
