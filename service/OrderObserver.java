package service;

import pojo.Order;

public interface OrderObserver {
    void Update(Order updatedOrder);
}
