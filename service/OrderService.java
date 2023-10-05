package service;

import java.time.LocalDateTime;

import repository.*;
import pojo.*;

// ----------------- PURPOSE: saving, retrieving & handling order data -----------------

public class OrderService extends IDservice {

    private OrderJSONhandler jsonHandler;
    private ScheduleService scheduleService;
    private Order order; // only current order is stored
    public boolean hasInvoice = false;

    public OrderService() {
        jsonHandler = new OrderJSONhandler();
    }

    public void createOrder() {
        int id = generateID();
        // check if order exists in json by checking equals / id (?)
        this.order = new Order(id);
    }

    // retrieving and saving order from json
    public String loadOrder(int orderID) {
        String orderToString = "";

        try {
            jsonHandler.setOrderID(orderID);
            Order oldOrder = jsonHandler.readJSON();
            orderToString =  oldOrder.toString();
        } catch (NullPointerException exception) {
            System.out.println("Order couldn't be deserialized: " + exception);
        }
        return orderToString;
    }
    public void saveOrder() {
        // save the current order to JSON, if an order has been created
        try {
            jsonHandler.saveJSON(order);    
        } catch (NullPointerException exception) {
            System.out.println("There is no order data to save to JSON: " + exception);
        }
    }
    public boolean isOrder(int orderID) {
        return jsonHandler.isJSON(orderID);
    }

    // setting customer & basket 
    public void setCustomer(Customer customer) {
        this.order.setCustomer(customer.clone());
    }
    public void setBasket(Basket basket) {
        this.order.setBasket(basket.clone());
    }
    
    public String orderToInvoice() {
        // intitalize ScheduleService & calculate pickup time
        scheduleService = new ScheduleService(order.getBasket().getTotalProductionHours());
        order.setOrderTime(LocalDateTime.now());
        order.setPickupTime(scheduleService.getPickUpTime());

        // flag the order as saved, it can't be changed anymore
        hasInvoice = true;
        return order.toString();
    }
}
