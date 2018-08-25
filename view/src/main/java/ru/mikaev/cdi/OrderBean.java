package ru.mikaev.cdi;

import ru.mikaev.domain.Order;
import ru.mikaev.domain.Product;
import ru.mikaev.dto.OrderInfo;
import ru.mikaev.dto.ProductInfo;
import ru.mikaev.ejb.OrdersManagerBean;
import ru.mikaev.ejb.ProductsManagerBean;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

@Named
@SessionScoped
public class OrderBean implements Serializable {
    private Order order;
    private String name;
    private long price;

    private int offset;
    private int limit;

    @EJB
    private OrdersManagerBean ordersManagerBean;

    @EJB
    private ProductsManagerBean productsManagerBean;

    @PostConstruct
    public void init(){
        offset = 0;
        limit = 10;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void createOrder(){
        if(order == null){
            order = ordersManagerBean.createOrder(new OrderInfo());
        }

    }

    public void createProduct(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName(name);
        productInfo.setPrice(price);

        productsManagerBean.createProduct(productInfo);
    }

    public List<Product> getProducts(int offset, int limit){
        return productsManagerBean.getProducts(offset, limit);
    }

    public List<Product> getNextProducts(){
        if(offset == 0){
            return getProducts(offset, limit);
        }
        offset += limit;
        return getProducts(offset, limit);
    }

    public List<Product> getLastProducts(){
        if(offset == 0){
            return getProducts(offset, limit);
        }
        offset -= limit;
        return getProducts(offset, limit);
    }

    public List<Product> getCurrentProducts(){
        return getProducts(offset, limit);
    }

    public List<Product> getProductsInOrder(){
        if(order == null) {
            return Collections.emptyList();
        }

        return ordersManagerBean.getProductsInOrder(order.getId());
    }

    public void addProduct(Product product){
        if(order == null){
            return;
        }
        ordersManagerBean.addToOrder(order.getId(), product.getId(), 1);
    }


}
