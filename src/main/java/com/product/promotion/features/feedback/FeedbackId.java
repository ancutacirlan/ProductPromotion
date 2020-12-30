package com.product.promotion.features.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FeedbackId implements Serializable {

    @Column(name = "client_id")
    private Integer clientId;

    @Column(name = "notices_id")
    private Integer noticeId;

}
