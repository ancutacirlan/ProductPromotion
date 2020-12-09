package com.product.promotion.features.order;

import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import com.product.promotion.features.location.Location;
import com.product.promotion.features.producers.Producer;
import com.product.promotion.features.vegetable.Vegetable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id",nullable = false)
    private Client clientId;

    @Column(name="confirmed", nullable = false)
    private Boolean confirmed;

    @Column(name="notice_order")
    private String details;

    @Column(name="total_price")
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "location_id",nullable = false)
    private Location locationId;

}
