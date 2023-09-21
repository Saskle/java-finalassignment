package presentation;

import java.util.InputMismatchException;
import java.util.Scanner;

import service.BasketService;
import service.CustomerService;
import service.OrderService;
import service.ProductService;

import static presentation.PrintFormatter.*;

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

        System.out.println("\nWelcome to the PhotoShop Ordering App! ");

        // if the user has closed the app without placing an order, ask to retrieve the basket and/or customer data
        if (basketService.hasSavedBasket() || customerService.hasSavedCustomer()) {
            System.out.println("There has been a previous basket and / or customer data saved since your last session.");
            System.out.println("Would you like to open it (" + YELLOW + 1 + RESET_COLOR + "), or start with a new order? (" + YELLOW + 2 + RESET_COLOR + ")");
            System.out.println("You can also close the application by entering " + YELLOW + 0 + RESET_COLOR + ".");

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

        // if the order hasn't been placed yet, save the basket and or customer data for next time
        if (!orderService.hasInvoice) {
            if (basketService.hasProducts()) basketService.saveBasket(); 
            if (customerService.hasCustomer()) customerService.saveCustomer();
        }

        System.exit(0);
    }

    public void showMainMenu() {
        System.out.println("\n" + header("MAIN MENU"));
        printMainMenu();

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
        System.out.print(   YELLOW + 1 + RESET_COLOR + " - Product Catalog\n" +
                            YELLOW + 2 + RESET_COLOR + " - View Current Order\n" +
                            YELLOW + 3 + RESET_COLOR + " - Customer Information\n" + 
                            YELLOW + 4 + RESET_COLOR + " - Proceed to Checkout\n" + 
                            YELLOW + 5 + RESET_COLOR + " - View previous orders\n" + 
                            YELLOW + 0 + RESET_COLOR + " - Close Application\n");
        System.out.print("Please enter the no. of the menu to proceed: ");
    }
    
    public void showProductCatalogue() {
        System.out.println("\n" + header("PRODUCT CATALOG"));
        printProductCatalogue();

        System.out.println("You can add multiple products at once by specifying id or name and quantity like (" + YELLOW + "2:3" + RESET_COLOR + ").");
        System.out.println("To return to the main menu, enter " + YELLOW + 0 + RESET_COLOR);
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
        System.out.print("Enter " + YELLOW + 0 + RESET_COLOR + " for no, " + YELLOW + 1 + RESET_COLOR + " for yes: ");

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
        System.out.println("\n" + header("CURRENT ORDER OVERVIEW"));
        System.out.println(customerService.showCustomer());
        System.out.println(basketService.showBasket());

        System.out.println( "Would you like to: \n" +
                            "\t" + YELLOW + 1 + RESET_COLOR + " - add new products to this order\n" +
                            "\t" + YELLOW + 2 + RESET_COLOR + " - remove products from this order\n" + 
                            "\t" + YELLOW + 3 + RESET_COLOR + " - change customer data\n" + 
                            "\t" + YELLOW + 4 + RESET_COLOR + " - place order and print invoice\n" + 
                            "\t" + YELLOW + 0 + RESET_COLOR + " - return to the Main Menu"); 
        System.out.print("Please enter the corresponding no.: ");
        
        int response = validateNumericalInput(4);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            case 2: 

                // TODO if basket is empty, say so!

                System.out.println(basketService.showBasket());
                System.out.println("You can add remove products at once by specifying name and quantity like (" + YELLOW + "Paper 10 x 15 mat:3" + RESET_COLOR + ").");
                System.out.println("To return to the main menu, enter " + YELLOW + 0 + RESET_COLOR + ".");
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
        System.out.println("\n" + header("CUSTOMER INFO"));
        
        if (customerService.hasCustomer()) {
            System.out.println(customerService.showCustomer());
            System.out.println("Do you want to change this info?");
            System.out.print("Enter " + YELLOW + 0 + RESET_COLOR + " for returning to the main menu, "  + YELLOW + 1 + RESET_COLOR + " for editing customer data: ");

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
            System.out.println(RED + "No customer info as been added to this order yet." + RESET_COLOR);
            promptCustomerData();
            showCustomerData();
        }
    }

    public void checkOut() {

        System.out.println("\n" + header("CHECKOUT"));

        // making sure the basket contains products before proceeding
        if(!basketService.hasProducts()) {
            System.out.println(RED + "The basket contains no products yet!" + RESET_COLOR);
            showProductCatalogue();
        }

        // making sure there is customer data for this order
        if (!customerService.hasCustomer()) {
            System.out.println(RED + "There is no customer data for this order yet!" + RESET_COLOR);
            promptCustomerData();
        }   

        // TODO ask user to confirm if order info is correct

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
        System.out.println("Enter " + YELLOW + 0 + RESET_COLOR + " to go back to the main menu. ");

        int orderID = validateNumericalInput(9999); // maximum possible order ID is 9999

        // allow the user to go back to the main menu
        if (orderID == 0) {
            showMainMenu();
        }

        System.out.println(orderService.loadOrder(orderID)); // TODO validate if file exists
        System.out.println("Placed orders are final. If there's something wrong with your order, please contact customer service.\n");
        showPlacedOrders();
    }
    
    public int validateNumericalInput(int range) {
        // first check if there is actually numberical input
        while (!scan.hasNextInt()) {
            System.out.println(RED + "You haven't entered a number. Please try again. " + RESET_COLOR);
            scan.next();
        }
        int response = scan.nextInt();

        // if there is, is it within the range (inclusive) we want?
        if (response < 0 || response > range) {
            System.out.println(RED + "You haven't entered a number in the correct range. Please try again. " + RESET_COLOR);
            response = validateNumericalInput(range);
        }
        return response;
    }

    public int validateNumericalInput(String text, int range, boolean canBeNull) {
        // first check if there is actually numberical input
        while (!isInteger(text)) {
            System.out.println(RED + "You haven't entered a number. Please try again. " + RESET_COLOR);
            text = scan.next();
        }
        int response = Integer.parseInt(text);

        // if there is, is it within the range (inclusive) we want (including or excluding 0)?
        if (canBeNull) {
            if (response < 0 || response > range) {
                System.out.println(RED + "You haven't entered a number in the correct range. Please try again. " + RESET_COLOR);
                response = validateNumericalInput(range);
            }
        } else {
            if (response <= 0 || response > range) {
                System.out.println(RED + "You haven't entered a number in the correct range. Please try again. " + RESET_COLOR);
                response = validateNumericalInput(range);
            }
        }
        return response;
    }

    public String validateProductName(String productName) {
        while (!productService.isProduct(productName)) {
            System.out.println(RED + "You haven't entered a correct product name. Please try again. " + RESET_COLOR);
            productName = scan.nextLine();
        }
        return productName;
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) { }
        return false;
    }
}
