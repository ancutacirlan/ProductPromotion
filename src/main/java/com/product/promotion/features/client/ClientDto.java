package com.product.promotion.features.client;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClientDto {

    private Integer id;
    private String first_name;
    private String last_name;
    private String phone;
    private String email;
    private String password;
    private Integer location_id;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String city;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String district;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String country;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String village;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String street;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String bloc;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String stair;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String no;

    private Boolean valid_account;

}
