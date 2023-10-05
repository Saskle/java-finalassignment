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

        System.out.println(addBox("Welcome to the PhotoShop Ordering App! "));

        // if the user has closed the app without placing an order, ask to retrieve the basket and/or customer data
        if (basketService.hasSavedBasket() || customerService.hasSavedCustomer()) {
            System.out.println("There has been a previous basket and / or customer data saved since your last session.");
            System.out.println("Would you like to open it (" + YELLOW + 1 + RESET_COLOR + "), or start with a new order (" + YELLOW + 2 + RESET_COLOR + ") ?");
            System.out.println("You can also close the application by entering " + YELLOW + 0 + RESET_COLOR + " and pressing " + YELLOW + "Enter" + RESET_COLOR + ".");

            int response = validateNumericalInput(2);
            switch (response) {
                case 0: closeApp();
                    
                case 1:
                    if (basketService.hasSavedBasket()) {
                        basketService.loadBasket();
                        System.out.println(BLACK + "Products in basket have been retrieved." + RESET_COLOR);
                    }
                    if (customerService.hasSavedCustomer()) {
                        customerService.loadCustomer();
                        System.out.println(BLACK + "Customer data has been retrieved." + RESET_COLOR);
                    }
                    showMainMenu();

                case 2: 
                    System.out.println(BLACK + "Creating new order..." + RESET_COLOR);
                    showMainMenu();
                
                default: throw new InputMismatchException("Input for startApp() isn't correctly validated!");
            }
        } else {
            showMainMenu();
        }      
    }

    private void closeApp() {
        scan.close();
        System.out.println(BLACK + "Application closing." + RESET_COLOR);

        // if the order hasn't been placed yet, save the basket and or customer data for next time
        if (!orderService.hasInvoice) {
            if (basketService.hasProducts()) basketService.saveBasket(); 
            if (customerService.hasCustomer()) customerService.saveCustomer();
        }
        System.exit(0);
    }

    private void showMainMenu() {
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
            default: throw new InputMismatchException("Input for showMainMenu() isn't correctly validated!");
            }
        }

    private void printMainMenu() {
        System.out.print(   YELLOW + 1 + RESET_COLOR + " - Product Catalog\n" +
                            YELLOW + 2 + RESET_COLOR + " - View / Edit Current Order\n" +
                            YELLOW + 3 + RESET_COLOR + " - Customer Information\n" + 
                            YELLOW + 4 + RESET_COLOR + " - Proceed to Checkout\n" + 
                            YELLOW + 5 + RESET_COLOR + " - View previous orders\n" + 
                            YELLOW + 0 + RESET_COLOR + " - Close Application\n");
        System.out.print("Please enter the number of the menu followed by " + YELLOW + "Enter" + RESET_COLOR + " to proceed: ");
    }
    
    private void showProductCatalogue() {
        System.out.println("\n" + header("PRODUCT CATALOG"));
        printProductCatalogue();

        System.out.println("You can add multiple products at once by specifying ID or name and quantity, like " + YELLOW + "2:3" + RESET_COLOR + ".");
        System.out.println("To return to the main menu, enter " + YELLOW + 0 + RESET_COLOR + ".");
        System.out.println("Please enter the corresponding ID or name of the product to add to your order: ");

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
            System.out.println(BLACK + quantity + " x " + productService.catalogue[index - 1].getName() + " has been added." + RESET_COLOR); 

        } else {
            // product must be a product's name
            product = validateProductName(product);
            basketService.addProducts(product, quantity);
            System.out.println(BLACK + quantity + " x " + product + " has been added." + RESET_COLOR);
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
    
    private void printProductCatalogue() {
        System.out.println("ID \tName \t\t\t\tPrice");
        // printing all products in catalogue, adding 1 to i so 0 is available for going back to main menu
        for (int i = 0; i < productService.catalogue.length; i++) {
            System.out.println((i + 1) + ". " + productService.catalogue[i]);
        }
        System.out.println();
    }

    private void showCurrentOrder() {
        System.out.println("\n" + header("CURRENT ORDER OVERVIEW"));
        System.out.println(customerService.showCustomer());
        System.out.println(basketService.showBasket());

        System.out.println( "Would you like to: \n" +
                            "\t" + YELLOW + 1 + RESET_COLOR + " - add new products to this order\n" +
                            "\t" + YELLOW + 2 + RESET_COLOR + " - remove products from this order\n" + 
                            "\t" + YELLOW + 3 + RESET_COLOR + " - change customer data\n" + 
                            "\t" + YELLOW + 4 + RESET_COLOR + " - place order and print invoice\n" + 
                            "\t" + YELLOW + 0 + RESET_COLOR + " - return to the Main Menu"); 
        System.out.print("Please enter the corresponding number: ");
        
        int response = validateNumericalInput(4);
        switch (response) {
            case 0: showMainMenu();
            case 1: showProductCatalogue();
            case 2: 

                // if basket is empty, say so and return to Current Order menu
                if (!basketService.hasProducts()) {
                    System.out.println(RED + "There are no products in the basket to remove!" + RESET_COLOR);
                    showCurrentOrder();
                }

                System.out.println(basketService.showBasket());
                System.out.println("You can remove products at once by specifying name and quantity, like " + YELLOW + "Paper 10 x 15 mat:3" + RESET_COLOR + ".");
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
                    System.out.println(BLACK + quantity + " x " + productService.catalogue[index - 1].getName() + " has been removed." + RESET_COLOR); 
        
                } else {
                    // product must be a product's name
                    product = validateProductName(product);
                    basketService.removeProducts(product, quantity);
                    System.out.println(BLACK + quantity + " x " + product + " has been removed." + RESET_COLOR);
                }
                showCurrentOrder();
            case 3: showCustomerData();
            case 4: checkOut();
            default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
        }
    }
    
    private void showCustomerData() {
        System.out.println("\n" + header("CUSTOMER INFO"));
        
        // if there is customer data, ask for changes
        if (customerService.hasCustomer()) {
            System.out.println(customerService.showCustomer());
            System.out.println("Do you want to change any data?");
            System.out.print("Enter " + YELLOW + 0 + RESET_COLOR + " for returning to the main menu, "  + YELLOW + 1 + RESET_COLOR + " for editing customer data: ");

            int response = validateNumericalInput(1);
            switch (response) {
                case 0: showMainMenu();
                case 1: scan.nextLine(); // throwaway line for nextInt()
                        editCustomerData();
                        showCustomerData();
                default: throw new InputMismatchException("Input for showCurrentOrder() isn't correctly validated!");
            }

        } else { // otherwise, prompt to add customer data and load the menu again
            System.out.println(RED + "No customer information as been added to this order yet." + RESET_COLOR);
            scan.nextLine(); // throwaway line for nextInt()
            promptCustomerData();
            showCustomerData();
        }
    }

    private void checkOut() {

        System.out.println("\n" + header("CHECKOUT"));

        // making sure the basket contains products before proceeding
        if(!basketService.hasProducts()) {
            System.out.println(RED + "There are no products in the basket yet!" + RESET_COLOR);
            showProductCatalogue();
        }

        // making sure there is customer data for this order
        if (!customerService.hasCustomer()) {
            System.out.println(RED + "There is no customer data for this order yet!\n" + RESET_COLOR);
            scan.nextLine(); // gets eaten by nextInt();
            promptCustomerData();
        }   

        // ask user to confirm if the order is complete
        System.out.println(customerService.showCustomer());
        System.out.println(basketService.showBasket());
        System.out.println("Please check if your order is ready for checkout.");
        System.out.print("Enter " + YELLOW + 0 + RESET_COLOR + " for to go back to the Main Menu, " + YELLOW + 1 + RESET_COLOR + " for proceeding to payment: ");
        
        int response = validateNumericalInput(1);
        switch (response) {
            case 0: showMainMenu();
            case 1: break;
            default: throw new InputMismatchException("Input for checkout() isn't correctly validated!"); 
        }

        // creating a new order
        orderService.createOrder();

        // passing current Customer & Basket to current order
        customerService.customerToOrder();
        basketService.basketToOrder();
        
        // print the order (invoice) to the console
        System.out.println(orderService.orderToInvoice());

        // save order to JSON
        orderService.saveOrder();

        // delete current Customer & Basket, close the app
        customerService.deleteCustomer();
        basketService.deleteBasket();
        closeApp();
    }

    private void showPlacedOrders() {
        System.out.println("Please enter the no. of the order you would like to view.");
        System.out.println("Enter " + YELLOW + 0 + RESET_COLOR + " to go back to the main menu. ");

        int orderID = validateNumericalInput(9999); // maximum possible order ID is 9999

        // allow the user to go back to the main menu
        if (orderID == 0) {
            showMainMenu();
        }

        while (!orderService.isOrder(orderID)) {
            System.out.print("Order could not be found. Please enter a valid order ID: ");
            orderID = validateNumericalInput(9999);
            // again allow for returning to menu?
        }

        System.out.println(orderService.loadOrder(orderID)); // TODO validate if file exists
        System.out.println("Placed orders are final. If there's something wrong with your order, please contact customer service.\n");
        showPlacedOrders();
    }

    private void promptCustomerData() {
        // start with the obligatory fields
        String firstName = promptCustomerFirstName();
        String lastName = promptCustomerLastName();
        String email = promptCustomerEmail();
        
        customerService.createCustomer(firstName, lastName, email);
        System.out.println(BLACK + "Customer data updated." + RESET_COLOR);
    }
    
    private void editCustomerData() {
        System.out.println();

        // split the customer string on lines so we can add menu numbers in front of each line
        String[] lines = customerService.showCustomer().split("\\r?\\n");

        // skip the first line (customer ID isn't changeable) + 0 is for cancelling editing
        for (int i = 1; i < lines.length; i++) {
            System.out.println(YELLOW + i + RESET_COLOR + lines[i]);
        }

        System.out.println("\nTo cancel editing and go back to the main menu, type " + YELLOW + 0 + RESET_COLOR + ".");
        System.out.print("Please specify which field you'd like to edit: ");

        int response = validateNumericalInput(6); // we assume here that customer.toString() always contains 6 lines = 6 fields to edit
        switch (response) {
            case 1: // line 1 is first name + last name
                scan.nextLine(); // eaten by nextInt()
                String firstName = promptCustomerFirstName();
                String lastName = promptCustomerLastName();
                customerService.setFirstName(firstName);
                customerService.setLastName(lastName);
                System.out.println(BLACK + "Name has been set to " + firstName + " " + lastName + "." + RESET_COLOR);
                editCustomerData();

            case 2: // line 2 is address
                scan.nextLine(); // eaten by nextInt()
                String address = promptCustomerAddress();
                customerService.setAddress(address);
                System.out.println(BLACK + "Address has been set to " + address + "." + RESET_COLOR);
                editCustomerData();
                editCustomerData();

            case 3: // line 3 is postal code
                scan.nextLine(); // eaten by nextInt()
                String postalCode = promptCustomerPostalCode();
                customerService.setPostalCode(postalCode);
                System.out.println(BLACK + "Postal code has been set to " + postalCode + "." + RESET_COLOR);
                editCustomerData();

            case 4: // line 4 is city
                scan.nextLine(); // eaten by nextInt()
                String city = promptCustomerCity();                
                customerService.setCity(city);
                System.out.println(BLACK + "City has been set to " + city + "." + RESET_COLOR);
                editCustomerData();

            case 5: // line 5 is email address
                scan.nextLine(); // eaten by nextInt()
                String email = promptCustomerEmail();
                customerService.setEmail(email);
                System.out.println(BLACK + "Email has been set to " + email + "." + RESET_COLOR);
                editCustomerData();

            case 6: // line 6 is phone number
                scan.nextLine(); // eaten by nextInt()
                String phoneNr = promptCustomerPhoneNr();
                customerService.setPhoneNr(phoneNr);
                System.out.println(BLACK + "Phone number has been set to " + phoneNr + "." + RESET_COLOR);
                editCustomerData();

            case 0: showMainMenu();
            default: throw new InputMismatchException("Input for editCustomerData() isn't correctly validated!");
        }
    }

    private String promptCustomerFirstName() {
        System.out.print("Please enter your first name: ");
        return scan.nextLine();
    }
    private String promptCustomerLastName() {
        System.out.print("Please enter your last name: ");
        return scan.nextLine();
    }
    private String promptCustomerEmail() {
        System.out.print("Please enter your email address: ");
        String email = scan.nextLine();
        while(!customerService.isEmail(email)) {
            System.out.println(RED + "The email you've entered isn't valid. Please try again:" + RESET_COLOR);
            email = scan.nextLine();
        }
        return email;
    }
    private String promptCustomerAddress() {
        System.out.print("Please enter your address (street and house nr.): ");
        return scan.nextLine();
    }
    private String promptCustomerCity() {
        System.out.print("Please enter your city: ");
        return scan.nextLine();
    }
    private String promptCustomerPostalCode() {
        System.out.print("Please enter your postal code (1234AB): ");
        String postalCode = scan.nextLine();
        while(!customerService.isPostalCode(postalCode)) {
            System.out.println(RED + "The postal code you've entered isn't valid. Please try again:" + RESET_COLOR);
            postalCode = scan.nextLine();
        }
        return postalCode;
    }
    private String promptCustomerPhoneNr() {
        System.out.print("Please enter your phone number: ");
        String phoneNr = scan.nextLine();
        while (!customerService.isPhoneNr(phoneNr)) {
            System.out.println(RED + "The phone number you've entered isn't valid. Please try again:" + RESET_COLOR);
            phoneNr = scan.nextLine();
        }
        return phoneNr;
    }

    private int validateNumericalInput(int range) {
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

    private int validateNumericalInput(String text, int range, boolean canBeNull) {
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

    private String validateProductName(String productName) {
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
