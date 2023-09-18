package test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import pojo.Order;

public class OrderTest {
    

    Order order = new Order(2);

    @Test
    void givenNegativeNumber_whenValidatingID_thenTrue() {
        order.setOrderID(-1);
    }
    
}
