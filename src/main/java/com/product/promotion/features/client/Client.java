package com.product.promotion.features.client;

import com.product.promotion.features.Audit;
import com.product.promotion.features.location.Location;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@Entity
@Table(name="clients")
public class Client extends Audit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name="first_name", nullable = false)
    private String first_name;

    @Column(name="last_name", nullable = false)
    private String last_name;

    @Column(name="phone", nullable = false, unique = true)
    private String phone;

    @Column(name="email", nullable = false,unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location_id;

    @Column(name="valid_account", nullable = false)
    private Boolean valid_account;

    @Column(name="is_admin")
    private Boolean isAdmin;

}
