package com.product.promotion.features.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderDto implements Serializable {

    private Integer id;
    private Integer clientId;
    private Boolean confirmed;
    private String details;
    private Integer totalPrice;
    private Integer locationId;
    private LocalDateTime date;
}
