package com.novaCart.repository;


import com.novaCart.entity.CustomersEntity;
import com.novaCart.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity,Long> {
    boolean existsByNameAndDeletedAtIsNull(String name);
    Optional<ProductsEntity> findByIdAndDeletedAtIsNull(Long id);
}
