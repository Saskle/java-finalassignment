package repository;

import java.io.File;
import java.time.LocalDateTime;

public class PickUpTimeJSONhandler extends JSONhandler {
    
    public void saveJSON(LocalDateTime object) {
        try {
            mapper.writeValueAsString(object);    
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
