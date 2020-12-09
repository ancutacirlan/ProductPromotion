package com.product.promotion.features.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto implements Serializable {

    private Integer id;
    private String city;
    private String district;
    private String country;
    private String village;
    private String street;
    private String bloc;
    private String stair;
    private String no;

}
