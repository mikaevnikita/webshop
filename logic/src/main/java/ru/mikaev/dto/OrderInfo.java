package ru.mikaev.dto;

import ru.mikaev.domain.Order;

public class OrderInfo {

    public static OrderInfo fromOrder(Order order){
        return new OrderInfo();
    }
}
