package presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

import service.BasketService;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

// ----------------- PURPOSE: Print application info to user, interpretet user input, start & close app -----------------

public class ShopPresentation {
 
    private Scanner scan = new Scanner(System.in);
    private OrderService orderService; 
    private BasketService basketService;
    private CustomerService customerService;
    private ProductService productService;

    public void startApp() {
        // intialize services
        orderService = new OrderService();
        basketService = new BasketService(orderService);
        customerService = new CustomerService(orderService);
        productService = new ProductService();

        System.out.println("\nWelcome to PhotoShop! ");

        // if the user has closed the app without placing an order, ask to retrieve the basket
        if (basketService.hasSavedBasket() || customerService.hasSavedCustomer()) {
            System.out.println("There has been a previous basket saved since your last session.");
            System.out.println("Would you like to open it (1), or start with a new order? (2)");
            System.out.println("You can also close the application by entering 0.");

            int response = validateNumericalInput(2);
            switch (response) {
                case 0: closeApp();
                    
                case 1:
                    if (basketService.hasSavedBasket()) {
                        basketService.loadBasket();
                        System.out.println("Products in basket have been retrieved.");
                    }
                    if (customerService.hasSavedCustomer()) {
                        customerService.loadCustomer();
                        System.out.println("Customer data has been retrieved.");
                    }
                    showMainMenu();

                case 2: 
                    System.out.println("Creating new order...");
                    showMainMenu();
                
                default: throw new InputMismatchException("Input for startApp() isn't correctly validated!");
            }
        } else {
            showMainMenu();
        }      
    }

    public void closeApp() {
        scan.close();
        System.out.println("Application closing.");

        // if the order hasn't been placed yet, save the basket for next time
        if (!orderService.hasInvoice) {
            if (basketService.hasProducts()) {
                basketService.saveBasket(); 
            }
            if (customerService.hasCustomer()) {
                customerService.saveCustomer();
            }  
        }

        System.exit(0);
    }

    public void showMainMenu() {
        System.out.println("\nMAIN MENU");
        printMainMenu();

        //String menuInput = scan.nextLine(); // this introduces a scan.nextLine() bug ???
        int menuChoice = validateNumericalInput(5);
        switch (menuChoice) {
            case 1: showProductCatalogue();
            case 2: showCurrentOrder();
            case 3: showCustomerData();
            case 4: checkOut();
            case 5: showPlacedOrders();
            case 0: closeApp();
            default:
                System.out.println("Please enter a correct menu index."); // case 0 is not handled, so don't throw an exeception here
                showMainMenu();
            }
        }

    public void printMainMenu() {
        System.out.print(   "1 - Product Catalog\n" +
                            "2 - View Current Order\n" +
                            "3 - Customer Information\n" + 
                            "4 - Create Invoice\n" + 
                            "5 - View placed orders\n" + 
                            "0 - Close Application\n");
        System.out.print("Please enter the no. of the menu to proceed: ");
    }
    
    public void showProductCatalogue() {
        System.out.println("\nPRODUCT CATALOG");
        printProductCatalogue();

        System.out.println("You can add multiple products at once by specifying id or name and quantity like (2:3).");
        System.out.println("To return to the main menu, enter 0.");
        System.out.println("Please enter the corresponding no. or name of the product to add to your order: ");

        String product = "";
        int quantity = 1; // if not specified, the user wants to add 1 product

        // TODO can this be a method?
        scan.nextLine(); // throwaway next line
        String input = scan.nextLine();
        if (input.contains(":")) {
            // split imput into two strings
            String[] splitInput = input.split(":");
            product = splitInput[0];
            
            // check if the input after ":" is a number, larger than 0
            quantity = validateNumericalInput(splitInput[1], 99, false);

        } else {
            // if there's no ":", the input only specifies the product
            product = input;
        }

        if (isInteger(product)) {
            // if the user entered a product ID, validate and use that imput
            int index = validateNumericalInput(product, productService.catalogue.length, true);

            // index 0 is reserved for returning to the main menu
            if (index == 0) {
                showMainMenu();
            }

            // add product (product ID's start at 1 instead of 0)
            basketService.addProducts(index - 1, quantity); 
            System.out.println(quantity + " x " + productService.catalogue[index - 1].getName() + " has been added."); 

        } else {
            // product must be a product's name
            product = validateProductName(product);
            basketService.addProducts(product, quantity);
            System.out.println(quantity + " x " + product + " has been added.");
        }

        System.out.println("\n" + basketService.showBasket());
        System.out.println("Would you like to add another product? ");
        System.out.print("Enter 0 for no, 1 for yes: ");

        int response = validateNumericalInput(1);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            default: throw new InputMismatchException("Input for showProductCatalogue() isn't correctly validated!");
        }
    }
    
    public void printProductCatalogue() {
        System.out.println("ID \tName \t\t\t\tPrice");
        // printing all products in catalogue, adding 1 to i so 0 is available for going back to main menu
        for (int i = 0; i < productService.catalogue.length; i++) {
            System.out.println((i + 1) + ". " + productService.catalogue[i]);
        }
        System.out.println();
    }

    public void showCurrentOrder() {
        System.out.println("\nCURRENT ORDER OVERVIEW");
        //System.out.println(orderService.showOrder()); // TODO showOrder should be reserved for invoice

        System.out.println(customerService.showCustomer());
        System.out.println(basketService.showBasket());
        System.out.println( "Would you like to: \n" +
                            "\t 1 - add new products to this order\n" +
                            "\t 2 - remove products from this order\n" + 
                            "\t 3 - change customer data\n" + 
                            "\t 4 - place order and print invoice\n" + 
                            "\t 0 - return to the Main Menu"); 
        System.out.print("Please enter the corresponding no.: ");
        
        int response = validateNumericalInput(4);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            case 2: 
                System.out.println(basketService.showBasket());
                System.out.println("You can add remove products at once by specifying name and quantity like (Paper 10 x 15 mat:3).");
                System.out.println("To return to the main menu, enter 0.");
                System.out.print("Which product would you like to remove? ");
                
                String product = "";
                int quantity = 1; // if not specified, the user wants to add 1 product

                // TODO can this be a method?
                scan.nextLine(); // throwaway next line
                String input = scan.nextLine();
                if (input.contains(":")) {
                    // split imput into two strings
                    String[] splitInput = input.split(":");
                    product = splitInput[0];
                    
                    // check if the input after ":" is a number, larger than 0
                    quantity = validateNumericalInput(splitInput[1], 99, false);
        
                } else {
                    // if there's no ":", the input only specifies the product
                    product = input;
                }
        
                if (isInteger(product)) {
                    // if the user entered a product ID, validate and use that imput
                    int index = validateNumericalInput(product, productService.catalogue.length, true);
        
                    // index 0 is reserved for returning to the main menu
                    if (index == 0) {
                        showMainMenu();
                    }
        
                    // remove product (product ID's start at 1 instead of 0)
                    basketService.removeProducts(index - 1, quantity); 
                    System.out.println(quantity + " x " + productService.catalogue[index - 1].getName() + " has been removed."); 
        
                } else {
                    // product must be a product's name
                    product = validateProductName(product);
                    basketService.removeProducts(product, quantity);
                    System.out.println(quantity + " x " + product + " has been removed.");
                }
                showCurrentOrder();
            case 3: showCustomerData();
            case 4: checkOut();
            default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
        }
    }
    
    public void showCustomerData() {
        System.out.println("CUSTOMER INFO");
        
        if (customerService.hasCustomer()) {
            System.out.println(customerService.showCustomer());
            System.out.println("Do you want to change this info?");
            System.out.print("Enter 0 for returning to the main menu, 1 for editing customer data: ");

            int response = validateNumericalInput(1);
            switch (response) {
                case 0: showMainMenu();
                case 1: promptCustomerData();
                        System.out.println("Customer data updated.");
                        showCustomerData();
                    break;
                default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
            }

        } else {
            System.out.println("No customer info as been added to this order yet.");
            promptCustomerData();
            showCustomerData();
        }
    }

    public void checkOut() {

        // TODO ask user to confirm if order info is correct

        System.out.println("CREATE INVOICE");

        // making sure the basket contains products before proceeding
        if(!basketService.hasProducts()) {
            System.out.println("The basket contains no products yet!");
            showProductCatalogue();
        }

        // making sure there is customer data for this order
        if (!customerService.hasCustomer()) {
            System.out.println("There is no customer data for this order yet!");
            promptCustomerData();
        }   

        // creating an order
        orderService.createOrder();

        // passing current Customer & Basket to current order
        customerService.customerToOrder();
        basketService.basketToOrder();
        
        // print the order (invoice) to the console
        System.out.println(orderService.orderToInvoice());

        // save order to JSON
        orderService.saveOrder();

        // delete current Customer & Basket
        customerService.deleteCustomer();
        basketService.deleteBasket();
        closeApp();
    }

    public void promptCustomerData() {
        scan.nextLine(); // throwaway line for nextInt()
        System.out.print("Please add your first name: ");
        String firstName = scan.nextLine();
        System.out.print("Please add your last name: ");
        String lastName = scan.nextLine();
        System.out.print("Please add your email address: ");
        String email = scan.nextLine();

        customerService.createCustomer(firstName, lastName, email);
    }

    public void showPlacedOrders() {
        System.out.println("Please enter the no. of the order you would like to view.");
        System.out.println("Enter 0 to go back to the main menu. ");

        int orderID = validateNumericalInput(9999); // maximum ID possible is 9999

        // allow to go back to the main menu
        if (orderID == 0) {
            showMainMenu();
        }

        System.out.println(orderService.loadOrder(orderID)); // TODO validate if file exists
        System.out.println("Placed orders are final. If there's something wrong with your order, please contact customer service.\n");
        showPlacedOrders();
        }
    // TODO remove this overload?
    public int validateNumericalInput(int range) {
        // first check if there is actually numberical input
        while (!scan.hasNextInt()) {
            System.out.println("You haven't entered a number. Please try again. ");
            scan.next();
        }
        int response = scan.nextInt();

        // if there is, is it within the range (inclusive) we want?
        if (response < 0 || response > range) {
            System.out.println("You haven't entered a number in the correct range. Please try again. ");
            response = validateNumericalInput(range);
        }
        return response;
    }

    public int validateNumericalInput(String text, int range, boolean canBeNull) {
        // first check if there is actually numberical input
        while (!isInteger(text)) {
            System.out.println("You haven't entered a number. Please try again. ");
            text = scan.next();
        }
        int response = Integer.parseInt(text);

        // if there is, is it within the range (inclusive) we want (including or excluding 0)?
        if (canBeNull) {
            if (response < 0 || response > range) {
                System.out.println("You haven't entered a number in the correct range. Please try again. ");
                response = validateNumericalInput(range);
            }
        } else {
            if (response <= 0 || response > range) {
                System.out.println("You haven't entered a number in the correct range. Please try again. ");
                response = validateNumericalInput(range);
            }
        }
        return response;
    }

    public String validateProductName(String productName) {
        while (!productService.isProduct(productName)) {
            System.out.println("You haven't entered a correct product name. Please try again. ");
            productName = scan.nextLine();
        }
        return productName;
    }

    // TODO remove this
    public void validateMultipleInput(String response, int range) {

        String[] responseParts = response.split(":");
        int quantity;

        // check if input before ":" is numerical
        if (isInteger(responseParts[0])) {
            
            int productID = validateNumericalInput(responseParts[0], productService.catalogue.length, true);

            // if (isInteger(responseParts[1])) {
                
            //     quantity = Integer.parseInt(responseParts[1]);
            //     shopService.addProducts(productID - 1, quantity); // product id's start at 1 instead of 0                
            // } else {
            //     // if input after ":" isn't a number, ask again
            //     System.out.println("Please enter a number after : .");
            //     System.out.println("How many of " + shopService.productCatalog[productID].getName() + " would you like to add? ");
            //     quantity = validateNumericalInput(99); // let's assume no one would like to add more than 99 products at once
            //     shopService.addProducts(productID - 1, quantity);
            // }

            quantity = validateNumericalInput(responseParts[1], 99, false);
            basketService.addProducts(productID - 1, quantity);
            System.out.println(productService.catalogue[productID - 1].getName() + " has been added to the shopping cart.");

        // if input is not numerical, it should be a product's name
        } else if (productService.isProduct(responseParts[0])) {
            
            // if (isInteger(responseParts[1])) {
            //     quantity = Integer.parseInt(responseParts[1]);
            // } else {
            //     // if input after : isn't a number, ask again
            //     System.out.println("Please enter a number after : .");
            //     System.out.println("How many of " + shopService.getProduct(responseParts[0]).getName() + " would you like to add? ");
            //     quantity = validateNumericalInput(99); // let's assume no one would like to add more than 99 products at once
            // }

            quantity = validateNumericalInput(responseParts[1], 99, false);
            basketService.addProducts(responseParts[0], quantity);
            System.out.println(responseParts[0] + " has been added to the shopping cart.");

        } else {
            // first part of command is not understandable at all, give up and show menu instead
            System.out.println("Please enter a correct product name or id.");
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) { }
        return false;
    }
}
