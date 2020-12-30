package com.product.promotion.features.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FeedbackDto {

    private Integer clientId;
    private Integer noticeId;
    private String message;
    private Integer qualifying;

}
