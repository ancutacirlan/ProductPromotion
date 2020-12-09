package com.product.promotion.features.producers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerRepository extends JpaRepository<Producer,Integer> {

    List<Producer> findAllByIsDeletedFalse();
    List<Producer> findAllByActiveIsTrueAndIsDeletedFalse();

}
