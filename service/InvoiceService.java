package service;

import pojo.Invoice;
import pojo.Order;
import pojo.Product;

// ----------------- PURPOSE: create & format invoices from orders for printing -----------------

public class InvoiceService {
    
    private Invoice invoice;
    private int totalWorkHours;
    private PickupTime pickupTime;

    public void createInvoice(Order order) {

        // set total production hours by iterating through all products
        this.totalWorkHours = order.basket.getTotalProductionHours();
        

        // intitalize PickUpTime & calculate pickup time
        this.pickupTime = new PickupTime(totalWorkHours);

        // create new invoice and set pickuptime & totalworkhours
        this.invoice = new Invoice(order.getId(), order.clone(), this.pickupTime.getPickUpTime());
        this.invoice.setTotalWorkHours(totalWorkHours);
    }

    // TODO create clone instead ??
    public Invoice getInvoice() {
        return this.invoice;
    }

    public int getTotalWorkHours() {
        return this.totalWorkHours;
    }
    public void setTotalWorkHours(int totalWorkHours) {
        this.totalWorkHours = totalWorkHours;
    }
    public PickupTime getPickupTime() {
        return this.pickupTime;
    }
    public void setPickupTime(PickupTime pickupTime) {
        this.pickupTime = pickupTime;
    }




    @Override
    public String toString() {
        return "";
    }
}
