package service;

import repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pojo.*;

// ----------------- PURPOSE: saving, retrieving & handling order data -----------------

public class OrderService extends Service {

    private List<OrderObserver> observers = new ArrayList<>();

    private JSONhandler jsonHandler;
    private ScheduleService scheduleService;
    private Order order; // only current order is stored
 

    public OrderService() {
        jsonHandler = new JSONhandler();
    }

    // observers
    public void registerObserver(OrderObserver observer) {
        observers.add(observer);
    }
    public void unregisterObserver(OrderObserver observer) {
        observers.remove(observer);
    }

    public void createOrder() {
        int id = generateID();
        // check if order exists in json by checking equals / id (?)
        this.order = new Order(id);
    }

    public void loadOrder(int orderID) {
        // retrieve order from json
        Order oldOrder = jsonHandler.readJSON(orderID);
        // update the current order and observers
        updateOrderData(oldOrder);
    }
    public void saveOrder() {
        // save order to json
        jsonHandler.saveJSON(order);
    }
    public void findOrder(int id) {
        // search id in current orders and open the file if it exists
    }

    public int getOrderID() {
        return order.getOrderID();
    }



    // remove this when csv loader is implemented!
    public void setOrder(Order order) {
        this.order = order.clone();
    }

    public void setCustomer(Customer customer) {
        this.order.setCustomer(customer.clone());
    }
    public void setBasket(Basket basket) {
        this.order.setBasket(basket.clone());
    }
    
    public String orderToInvoice() {

        // intitalize ScheduleService & calculate pickup time
        scheduleService = new ScheduleService(order.basket.getTotalProductionHours());
        order.setOrderTime(LocalDateTime.now());
        order.setPickupTime(scheduleService.getPickUpTime());

        return order.toString();
    }

    public void updateOrderData(Order loadedOrder) {
        // update the current order
        this.order = loadedOrder.clone();

        // notify all registered observers
        for (OrderObserver observer : observers) {
            observer.Update(loadedOrder);
        }
    }

}
