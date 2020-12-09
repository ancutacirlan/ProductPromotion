package com.product.promotion.features.product;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {

    private Integer id;
    private Integer noticeId;
    private String producerFirstName;
    private String producerLastName;
    private String vegetableName;
    private Integer quantity;
    private String deliveryMode;
    private Integer orderId;

}
