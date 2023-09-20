package repository;

import java.io.File;
import java.io.IOException;

import pojo.Basket;

// ----------------- PURPOSE: reading & writing current Basket to JSON -----------------

public class BasketJSONhandler extends JSONhandler<Basket> {

    public BasketJSONhandler() {
        // set the file path on initialisation
        file = new File("data//current_basket.json");
    }
    
    @Override
    public void saveJSON(Basket basket) {
        try {
            mapper.writeValue(file, basket);
        } catch (IOException exception) {
            System.out.println(exception);
        } 
    }

    @Override
    public Basket readJSON() {
        try {
            return mapper.readValue(file, Basket.class);
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return null; // TODO is this allowed?
    }

    public boolean fileExists() {
        return file.exists();
    }

    public void deleteFile() {
        file.delete();
    }
}
