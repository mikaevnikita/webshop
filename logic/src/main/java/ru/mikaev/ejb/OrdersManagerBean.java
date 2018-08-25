package ru.mikaev.ejb;

import ru.mikaev.domain.Order;
import ru.mikaev.domain.Product;
import ru.mikaev.domain.ProductInOrder;
import ru.mikaev.dto.OrderInfo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class OrdersManagerBean {
    @PersistenceContext(unitName = "webshopPU")
    private EntityManager entityManager;

    public Order createOrder(OrderInfo orderInfo){
        Order order = Order.fromOrderInfo(orderInfo);
        entityManager.persist(order);

        return order;
    }


    public boolean addToOrder(long orderId, long productId, int quantity){
        Product product = entityManager.find(Product.class, productId);
        if(product == null){
            return false;
        }

        Order order = entityManager.find(Order.class, orderId);
        if(order == null){
            return false;
        }

        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setOrder(order);
        productInOrder.setProduct(product);
        productInOrder.setQuantity(quantity);
        entityManager.persist(productInOrder);

        return true;
    }

    public boolean addToOrder(Order order, Product product, int quantity){
        return addToOrder(order.getId(), product.getId(), quantity);
    }

    public List<Product> getProductsInOrder(long orderId){
        Order order = entityManager.find(Order.class, orderId);
        if(order == null){
            return Collections.emptyList();
        }

        return order.getProducts();
    }
}
