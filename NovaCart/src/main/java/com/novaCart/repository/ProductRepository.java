package com.novaCart.repository;


import com.novaCart.entity.CustomersEntity;
import com.novaCart.entity.ProductsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductsEntity,Long> , JpaSpecificationExecutor<ProductsEntity> {
    boolean existsByNameAndDeletedAtIsNull(String name);
    Optional<ProductsEntity> findByIdAndDeletedAtIsNull(Long id);


    Page<ProductsEntity>  findByNameContainingIgnoreCaseAndCategoryContainingIgnoreCaseAndDeletedAtIsNull(String name,
                                                                                        String category, Pageable pageable);

    @Query("""
        SELECT p FROM ProductsEntity p
        WHERE p.deletedAt IS NULL
        AND (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:minPrice IS NULL OR p.price >= :minPrice)
        AND (:maxPrice IS NULL OR p.price <= :maxPrice)
        """)
    Page<ProductsEntity> findProducts(
            @Param("search") String search,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
