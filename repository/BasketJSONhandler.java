package repository;

import java.io.File;
import java.io.IOException;

import pojo.Basket;

public class BasketJSONhandler extends JSONhandler {

    public BasketJSONhandler() {
        file = new File("data//current_basket.json");
    }
    
    public void saveJSON(Basket basket) {
        try {
            mapper.writeValue(file, basket);
        } catch (IOException exception) {
            System.out.println(exception);
        } 
    }

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
