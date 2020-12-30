package com.product.promotion.features.feedback;

import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import com.product.promotion.features.notice.Notice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name="feedbacks")
public class Feedback  extends Audit implements Serializable {
   
    @EmbeddedId
    private FeedbackId feedbackId;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("clientId")
    @JoinColumn(name = "client_id")
    private Client clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("noticeId")
    @JoinColumn(name = "notices_id")
    private Notice noticeId;

    @Column(name = "message")
    private String message;

    @Column(name = "qualifying")
    private Integer qualifying;

}

