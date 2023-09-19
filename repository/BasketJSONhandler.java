package repository;

import java.io.File;
import java.io.IOException;

import pojo.Basket;

public class BasketJSONhandler extends JSONhandler {
    
    public void saveJSON(Object basket) {
        file = new File("data//current_basket.json");

        try {
            mapper.writeValue(file, basket);
        } catch (IOException exception) {
            System.out.println(exception);
        } 
    }

    public Basket readJSON() {
        return new Basket();
    }
}
