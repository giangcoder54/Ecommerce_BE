package org.example.ecommerce_be.Repository;


import org.example.ecommerce_be.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where name like %:keyword% or description like %:keyword%", nativeQuery = true)
     Page<Product> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

    @Query(value = "select * from product where category_id = :categoryId", nativeQuery = true)

    List<Product> findByCategory_id(@Param("categoryId") Long categoryId);
}
