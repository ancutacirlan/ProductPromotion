package com.product.promotion.features.producer;


import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import com.product.promotion.features.notice.Notice;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "producers")
public class Producer extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id", unique = true)
    private Client clientId;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "description")
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active;

}
