import repository.*;

public class ShopService {
    
    private CSVhandler csvHandler;
    private JSONhandler jsonHandler;


    // Order
    // retrieveCustomer() -> check if null? would it make any difference from here instead of inside order?
    // create boolean order.hasCustomer?
    // I think main / main menu will be cleaner if I can call getCustomer and let service check if there is one; isn't that encapsulation?

    // CSV
    // methods for loading csv info to products, also into catalogue?
    // methods for loading opening hours from csv into class

    // JSON
    // method saveOrder() -> save to json via jsonHandler
    // method loadOrder()

}
