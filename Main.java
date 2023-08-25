import pojo.Customer;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello");

        Customer one = new Customer("Saskia", "de Klerk", "sas@calco.nl");
        Customer two = new Customer("Saskia", "de Klerk", "sas@calco.nl");
        Customer three = new Customer("Saskia", "de Klerk", "sas@calco.nl");
        
        System.out.println(one.getId());
        System.out.println(two.getId());
        System.out.println(three.getId());

    }
}