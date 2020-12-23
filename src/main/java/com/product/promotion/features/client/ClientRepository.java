package com.product.promotion.features.client;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository  extends JpaRepository<Client,Integer> {

    List<Client> findAllByIsDeletedFalse();
    List<Client> findAllByRoleAndIsDeletedFalse(String role);
    Optional<Client> findByEmailAndIsDeletedFalse(String email);
}
