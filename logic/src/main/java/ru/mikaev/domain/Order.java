package ru.mikaev.domain;

import ru.mikaev.dto.OrderInfo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "ordertable")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER)
    private List<ProductInOrder> productsInOrder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public static Order fromOrderInfo(OrderInfo orderInfo){
        Order order = new Order();

        return order;
    }

    public OrderInfo toOrderInfo(){
        return OrderInfo.fromOrder(this);
    }

    public List<ProductInOrder> getProductsInOrder() {
        return productsInOrder;
    }

    public void setProductsInOrder(List<ProductInOrder> products) {
        this.productsInOrder = products;
    }

    public List<Product> getProducts(){
        List<Product> products = new ArrayList<>();
        List<ProductInOrder> productsInOrderList = getProductsInOrder();
        for(ProductInOrder productInOrder : productsInOrderList){
            Product product = productInOrder.getProduct();
            products.add(product);
        }
        return products;
    }
}