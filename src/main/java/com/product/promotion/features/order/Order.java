package com.product.promotion.features.order;

import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import com.product.promotion.features.location.Location;
import com.product.promotion.features.product.Product;
import com.product.promotion.features.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

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

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="confirmed", nullable = false)
    private Boolean confirmed;

    @Column(name="notice_order")
    private String details;

    @Column(name="total_price")
    private Float totalPrice;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location locationId;

}
