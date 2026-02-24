package com.novaCart.repository;

import com.novaCart.entity.CustomersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<CustomersEntity,Long> {
    @Query("SELECT c FROM CustomersEntity c WHERE c.email = :email AND c.deletedAt IS NULL")
    Optional<CustomersEntity> findActiveCustomerByEmail(@Param("email") String email);

    @Query("SELECT c FROM CustomersEntity c WHERE c.id = :customerId AND c.deletedAt IS NULL")
    Optional<CustomersEntity> findActiveCustomerById(@Param("customerId") Long customerId);
}
