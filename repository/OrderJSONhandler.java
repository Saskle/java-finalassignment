package repository;

import java.io.File;
import java.io.IOException;

import pojo.Order;

// ----------------- PURPOSE: reading & writing Orders to JSON -----------------

public class OrderJSONhandler extends JSONhandler<Order> {

    // I need to save the current orderID separately, 
    // as readJSON() can't have parameters as a override method
    private int orderID;

    @Override
    public void saveJSON(Order order) {
        orderID = order.getOrderID();
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

    public void setOrderID(int orderID) {
        if (orderID <= 0) {
            throw new IllegalArgumentException("Order's ID cannot be 0 or negative.");
        }
        this.orderID = orderID;
    }

    @Override
    public Order readJSON() {
        file = new File("data//order" + orderID + ".json");

        try {
            Order order = mapper.readValue(file, Order.class);  
            return order;  
        } catch (IOException exception) {
            System.out.println(exception);
        }
        return null; // client's responsibility to account for null values
    }

    public boolean isJSON(int orderID) {
        file = new File("data//order" + orderID + ".json");
        return file.exists();
    }
}

