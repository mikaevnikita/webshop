package ru.mikaev.ejb;


import ru.mikaev.domain.Product;
import ru.mikaev.dto.ProductInfo;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
@LocalBean
public class ProductsManagerBean {
    @PersistenceContext(unitName = "webshopPU")
    private EntityManager entityManager;

    public Product createProduct(ProductInfo productInfo){
        Product product = Product.fromProductInfo(productInfo);
        entityManager.persist(product);

        return product;
    }

    public List<Product> getProducts(int offset, int limit){
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p", Product.class);

        return query.setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

}
