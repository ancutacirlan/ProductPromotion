package com.product.promotion.features.location;

import com.product.promotion.features.Audit;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name="locations")
public class Location extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "village")
    private String village;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "bloc")
    private String bloc;

    @Column(name = "stair")
    private String stair;

    @Column(name = "no", nullable = false)
    private String no;

}
