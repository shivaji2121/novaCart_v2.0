package com.novaCart.repository;


import com.novaCart.entity.ProductsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity,Long> {
    boolean existsByNameAndDeletedAtIsNull(String name);
}
