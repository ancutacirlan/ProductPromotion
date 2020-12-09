package com.product.promotion.features.producers;


import com.product.promotion.features.Audit;
import com.product.promotion.features.client.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

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
    @JoinColumn(name = "client_id")
    private Client clientId;

    @Column(name = "document", nullable = false)
    private String document;

    @Column(name = "active", nullable = false)
    private boolean active;

}
