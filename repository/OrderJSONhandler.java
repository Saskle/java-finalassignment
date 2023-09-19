package repository;

import java.io.File;
import java.io.IOException;

import pojo.Order;

public class OrderJSONhandler extends JSONhandler {

    public void saveJSON(Object order) {
        Order realOrder = (Order) order;
        int orderID = realOrder.getOrderID();
        file = new File("data//order" + orderID + ".json");

        // if the file already exists, modify the order ID
        while (file.exists()) {
            realOrder.setOrderID(realOrder.getOrderID() + 1);
            orderID = realOrder.getOrderID();
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

