package com.product.promotion.features;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Audit implements Serializable {

    @CreatedDate
    @Column(name = "date_created", nullable = false)
    @JsonIgnore
    @Transient
    private LocalDateTime dateCreated;

    @LastModifiedDate
    @Column(name = "date_updated", nullable = false)
    @JsonIgnore
    @Transient
    private LocalDateTime dateUpdated;

    @Column(name = "is_deleted", nullable = false)
    @Setter
    @JsonIgnore
    private Boolean isDeleted = false;

}