package ru.mikaev.dto;

import ru.mikaev.domain.Product;

import java.math.BigDecimal;

public class ProductInfo {
    private String name;
    private long price;

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static ProductInfo fromProduct(Product product){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setName(product.getName());
        productInfo.setPrice(product.getPrice());

        return productInfo;
    }
}
