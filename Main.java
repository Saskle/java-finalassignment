import java.math.BigDecimal;
import java.util.Scanner;

import pojo.Customer;
import pojo.Invoice;
import pojo.Order;
import pojo.Product;

public class Main {
    static Scanner scan = new Scanner(System.in);

    // hardcoded product info, remove when implementing csv reader!
    static Product[] productCatalog = new Product[] {
            new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1),
            new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1),
            new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2),
            new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2),
            new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12),
            new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2),
            new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16),
            new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16),
            new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14),
            new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14),
            new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20),
            new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20)
        };

    public static void main(String[] args) {
        
        //Product photo1 = new Product(1, "Paper 10 x 15 mat", new BigDecimal("1.40"), 1);
        //Product photo2 = new Product(2, "Paper 10 x 15 high gloss", new BigDecimal("1.50"), 1);
        //Product photo3 = new Product(3, "Paper 30 x 40 mat", new BigDecimal("4.50"), 2);
        //Product photo4 = new Product(4, "Paper 30 x 40 high gloss", new BigDecimal("5.00"), 2);
        //Product photo5 = new Product(5, "Canvas 30 x 40 mat", new BigDecimal("24.00"), 12);
        //Product photo6 = new Product(6, "Canvas 30 x 40 high gloss", new BigDecimal("27.50"), 2);
        //Product photo7 = new Product(7, "Canvas 100 x 150 mat", new BigDecimal("64.75"), 16);
        //Product photo8 = new Product(8, "Canvas 100 x 150 high gloss", new BigDecimal("72.50"), 16);
        //Product photo9 = new Product(9, "Glass 30 x 40 mat", new BigDecimal("27.50"), 14);
        //Product photo10 = new Product(10, "Glass 30 x 40 high gloss", new BigDecimal("27.50"), 14);
        //Product photo11 = new Product(11, "Glass 100 x 150 mat", new BigDecimal("82.50"), 20);
        //Product photo12 = new Product(12, "Glass 100 x 150 high gloss", new BigDecimal("82.50"), 20);

        

        

        System.out.print("Welcome to PhotoShop! \nDo you want to continue shopping, or create a new order? ");
        String response = scan.nextLine();

        if (response.equals("existing")) {
            // load in existing order (hardcode something for now)
            Order loadedOrder = new Order(4);
            loadedOrder.addProduct(productCatalog[3]);
            loadedOrder.setCustomer(new Customer("Saskia", "de Klerk", "saskle@calco.nl"));;

            System.out.println("The last saved order no. " + loadedOrder.getId() + " was made by " + loadedOrder.getCustomer().getFirstName() + " " + loadedOrder.getCustomer().getLastName());
            System.out.println("Please enter the order no. of the order you'd like to restore.");
            response = scan.nextLine();

            
            mainMenu(loadedOrder);

        } else if (response.equals("new")) {
            Order order = new Order(0);

            // go to main menu
            mainMenu(order);


        }


        

        scan.close();
    }

    public static void mainMenu(Order order) {
        System.out.println("\nMAIN MENU");
        System.out.print("1 - Product Catalog\n2 - View Current Order\n3 - Customer Information\n4 - Create Invoice\n5 - Close Application\n");
        System.out.print("Please enter the no. of the menu to proceed: ");
        int menuChoice = scan.nextInt(); // add argument validation

        // has int
        switch (menuChoice) {
            case 1: // product catalog
                System.out.println("PRODUCT CATALOG");

                for (int i = 0; i < productCatalog.length; i++) {
                    System.out.println(i + " - " + productCatalog[i]);
                }
                System.out.println("Please pick a product to add to your order.");
                
                // promptMainMenu()
                int index = scan.nextInt();
                order.addProduct(productCatalog[index]);
                System.out.println("Product has been added.");

                // again show products, prompt for new products to add?

                mainMenu(order);
                break;

            case 2: // view current order / shopping cart
                System.out.println("CURRENT ORDER");
                System.out.println(order);

                mainMenu(order);
                break;

            case 3: // customer information
                System.out.println("CUSTOMER INFO");
                scan.nextLine(); // throwaway line for nextInt()

                if (order.hasCustomer()) {
                    System.out.println(order.getCustomer());
                    System.out.println("Do you want to change this info?");
                    String response = scan.nextLine();

                    if (response.equals("yes")) {
                        System.out.print("Please add your first name: ");
                        String firstName = scan.nextLine();
                        System.out.print("Please add your last name: ");
                        String lastName = scan.nextLine();
                        System.out.print("Please add your email address: ");
                        String email = scan.nextLine();

                        Customer customer = new Customer(firstName, lastName, email);
                        order.setCustomer(customer);

                        mainMenu(order);

                    } else {
                        mainMenu(order);
                    }

                } else {
                    System.out.println("No customer info as been added to this order yet.");
                    
                }

                System.out.print("Please add your first name: ");
                String firstName = scan.nextLine();
                System.out.print("Please add your last name: ");
                String lastName = scan.nextLine();
                System.out.print("Please add your email address: ");
                String email = scan.nextLine();

                Customer customer = new Customer(firstName, lastName, email);
                order.setCustomer(customer);

                mainMenu(order);
                break;

            case 4: // create invoice
                System.out.println("CREATE INVOICE");
                Invoice invoice = new Invoice(1, order);
                System.out.println(invoice);

                mainMenu(order);
                break;
            case 5: // close application
                System.out.println("Application closing.");
                // saveOrder();
                System.exit(0);
                break;
            default:
                System.out.println("Please enter a correct menu index.");
                mainMenu(order);
                break;
        }
    }
}