package com.product.promotion.features.order;


import com.product.promotion.features.product.Product;
import com.product.promotion.features.product.ProductDto;
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
    private Integer totalPrice;
    private Integer locationId;
    private LocalDateTime date;
}
