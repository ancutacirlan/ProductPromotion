package com.product.promotion.features.notice;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.product.promotion.features.producers.Producer;
import com.product.promotion.features.vegetable.Vegetable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NoticeDto implements Serializable {

    private Integer id;
    private Integer producerId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String  producerFirstName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String  producerLastName;

    private Integer vegetableId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String vegetableName;

    private Integer avaibleQuantity;
    private String unit;
    private String image;
    private Boolean homeDelivery;
    private String details;
    private Boolean valid;
    private Boolean alive;
    private Integer pricePerUnit;

}
