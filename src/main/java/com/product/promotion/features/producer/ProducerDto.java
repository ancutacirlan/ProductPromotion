package com.product.promotion.features.producer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProducerDto {

    private Integer id;
    private Integer clientId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Integer locationId;
    private String city;
    private String district;
    private String country;
    private String village;
    private String street;
    private String bloc;
    private String stair;
    private String no;
    private Boolean validAccount;
    private String role;
    private String description;
    private String document;
    private boolean active;

}
