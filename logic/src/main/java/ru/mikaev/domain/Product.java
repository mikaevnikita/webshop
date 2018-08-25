package ru.mikaev.domain;

import ru.mikaev.dto.ProductInfo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private long price;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    private List<ProductInOrder> productsInOrder;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public static Product fromProductInfo(ProductInfo productInfo){
        Product product = new Product();
        product.setName(productInfo.getName());
        product.setPrice(productInfo.getPrice());

        return product;
    }

    public ProductInfo toProductInfo(){
        return ProductInfo.fromProduct(this);
    }
}
