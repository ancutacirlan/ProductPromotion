package com.product.promotion.features.product;

import com.product.promotion.features.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    List<Product> findAllByIsDeletedFalse();
    List<Product> findAllByOrderIdAndIsDeletedFalse(Order orderId);

}
