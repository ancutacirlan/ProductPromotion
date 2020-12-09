package com.product.promotion.features.notice;

import com.product.promotion.features.Audit;
import com.product.promotion.features.producer.Producer;
import com.product.promotion.features.vegetable.Vegetable;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "notices")
public class Notice extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "producer_id",nullable = false)
    private Producer producerId;

    @ManyToOne
    @JoinColumn(name = "vegetable_id",nullable = false)
    private Vegetable vegetableId;

    @Column(name="quantity_available", nullable = false)
    private Integer avaibleQuantity;

    @Column(name="unit", nullable = false)
    private String unit;

    @Column(name="image")
    private String image;

    @Column(name="home_delivery", nullable = false)
    private Boolean homeDelivery;

    @Column(name="product_notice")
    private String details;

    @Column(name="valid", nullable = false)
    private Boolean valid;

    @Column(name="alive", nullable = false)
    private Boolean alive;

    @Column(name="price_per_unit", nullable = false)
    private Integer pricePerUnit;

}
