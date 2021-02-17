package com.product.promotion.features.order;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto implements Serializable {

    private Integer id;
    private Integer clientId;
    private String clientFistName;
    private String clientLastName;
    private Boolean confirmed;
    private String details;
    private Float totalPrice;
    private Integer locationId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime date;
}
