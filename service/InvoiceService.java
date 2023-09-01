package service;

import java.time.LocalDateTime;

import pojo.Invoice;
import pojo.Order;
import pojo.Product;

// ----------------- PURPOSE: create & format invoices from orders -----------------

public class InvoiceService {
    
    private Invoice invoice;
    private int totalWorkHours;
    private PickupTime pickupTime;

    public void createInvoice(Order order) {

        // set total production hours by iterating through all products
        for (Product product : order.getAllProducts()) {
            this.totalWorkHours =+ product.getCreatingHours();
        }
        System.out.println("Total work hours for this order is: " + totalWorkHours);

        // intitalize PickUpTime
        this.pickupTime = new PickupTime(totalWorkHours);
        // calculate new pickuptime
        LocalDateTime now = LocalDateTime.now(); // TODO remove this when we calculate pickup time!

        // create new invoice and set pickuptime & totalworkhours
        this.invoice = new Invoice(order.getId(), order.clone(), now);
        this.invoice.setTotalWorkHours(totalWorkHours);
    }

    // TODO create clone instead ??
    public Invoice getInvoice() {
        return this.invoice;
    }

    @Override
    public String toString() {
        return "";
    }
}
