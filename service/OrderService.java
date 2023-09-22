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

    public String loadOrder(int orderID) {
        // retrieve order from json
        jsonHandler.setOrderID(orderID);
        Order oldOrder = jsonHandler.readJSON();
        return oldOrder.toString();
    }
    
    public void saveOrder() {
        // if any order has been initialized (created or loaded), save it
        if (order != null) {
            jsonHandler.saveJSON(order);    
        }
        
    }
    public boolean isOrder(int orderID) {
        // TODO search id in current orders and open the file if it exists
        return false;
    }

    public int getOrderID() {
        return order.getOrderID();
    }

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

        hasInvoice = true;
        return order.toString();
    }

}
