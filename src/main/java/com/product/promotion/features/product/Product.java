package com.product.promotion.features.product;

import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import com.product.promotion.features.notice.Notice;
import com.product.promotion.features.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice noticeId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "mode_delivery", nullable = false)
    private String deliveryMode;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order orderId;


}
