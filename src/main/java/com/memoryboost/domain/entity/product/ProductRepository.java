package com.memoryboost.domain.entity.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> , ProductRepositoryCustom{

    @Query(value = "SELECT * FROM tb_product ORDER BY RAND() LIMIT 6", nativeQuery = true)
    List<Product> findByProductRandom();

}
