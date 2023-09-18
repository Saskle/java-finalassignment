package service;

import repository.*;

import java.time.LocalDateTime;

import pojo.*;

// ----------------- PURPOSE: handling order data & starting app -----------------

public class OrderService extends Service {

    private JSONhandler jsonHandler;
    private ScheduleService scheduleService;
    private Order order; // only current order is stored
 

    public OrderService() {
    }

    public void createOrder() {
        int id = generateID();
        // check if order exists in json by checking equals / id (?)
        this.order = new Order(id);
    }

    public void loadOrder() {
        // retrieve order from json
    }
    public void saveOrder() {
        // save order to json
    }

    // TODO this is needed for InvoiceService
    public Order getOrder() {
        return this.order.clone();
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
        // set total production hours by iterating through all products
        //this.totalWorkHours = order.basket.getTotalProductionHours();

        // intitalize ScheduleService & calculate pickup time
        scheduleService = new ScheduleService(order.basket.getTotalProductionHours());
        order.setOrderTime(LocalDateTime.now());
        order.setPickupTime(scheduleService.getPickUpTime());

        // create new invoice and set pickuptime & totalworkhours
        //this.invoice = new Invoice(order.getOrderID(), order.clone(), this.pickupTime.getPickUpTime());
        //this.invoice.setTotalWorkHours(totalWorkHours);

        return order.toString();
    }


}
