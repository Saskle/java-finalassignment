package repository;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import pojo.Order;

// ----------------- PURPOSE: Reading & Writing to JSON -----------------

public class JSONhandler {
    
    // mapping object that allows serializing & deserializing POJO's (JavaTimeModule for LocalDateTime support)
    private ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
    private File file;

    public void saveJSON(Order order) {
        int orderID = order.getOrderID();
        file = new File("data//order" + orderID + ".json");

        // if the file already exists, modify the order ID
        while (file.exists()) {
            order.setOrderID(order.getOrderID() + 1);
            orderID = order.getOrderID();
            String path = "data//order" + orderID + ".json";
            file = new File(path);
        }

        try {
            mapper.writeValue(file, order);
        } catch (IOException exception) {
            System.out.println(exception);
        }
        
    }

    public Order readJSON(int orderID) {
        file = new File("data//order" + orderID + ".json");
        
        try {
            Order order = mapper.readValue(file, Order.class);  
            return order;  
        } catch (IOException exception) {
            System.out.println(exception);
        }
        return null; // TODO is this safe?
        
    }
}
