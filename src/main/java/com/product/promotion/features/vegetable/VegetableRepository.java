package com.product.promotion.features.vegetable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VegetableRepository extends JpaRepository<Vegetable,Integer> {

    List<Vegetable> findAllByIsDeletedFalse();

    Optional<Vegetable> findAllByNameAndIsDeletedFalse(String name);

    Optional<Vegetable> findByNameAndIsDeletedFalse(String name);
}
