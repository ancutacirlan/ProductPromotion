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

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String firstName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String lastName;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String phone;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String email;

    private String description;
    private String document;
    private boolean active;

}
