package repository;

import java.io.File;
import java.io.FilenameFilter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;

import pojo.Order;

// ----------------- PURPOSE: reading & writing the latest pickup time to JSON -----------------

public class PickUpTimeJSONhandler extends JSONhandler<LocalDateTime> {

    public PickUpTimeJSONhandler() {
        // set the file path on initialisation
        file = new File("data\\latestPickUpTime.json");

        // if there's no JSON storing the latest pickup time, create a new one from the most recent order
        if (!file.exists()) {

            // get all the files in data and filter on Order JSONs
            File directory = new File("data");
            FilenameFilter filter = (dir, name) -> name.startsWith("order");
            File[] orderFiles = directory.listFiles(filter);

            // sorting the files on time last modified in descending order (we want the latest pickup time)
            Arrays.sort(orderFiles, Comparator.comparingLong(File::lastModified).reversed());

            try {
                // read the first file and save it's pickup time to JSON
                Order order = mapper.readValue(orderFiles[0], Order.class); 
                LocalDateTime latestPickupTime = order.getPickUpTime();
                saveJSON(latestPickupTime);
            } catch (Exception exception) {
                System.out.println(exception);
            }
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
