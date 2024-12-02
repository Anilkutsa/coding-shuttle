package com.codingshuttle.jpatutorial.repositories;

import com.codingshuttle.jpatutorial.entities.ProductEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    /** METHODS BEING CALLED FROM TEST CLASS - QUERY */
    List<ProductEntity> findByCreatedAtAfter(LocalDateTime of);

    List<ProductEntity> findByQuantityGreaterThanOrPriceLessThan(int i, BigDecimal bigDecimal);

    List<ProductEntity> findByTitleContainingIgnoreCase(String PEpsi);

    Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    // @Query("select e.title, e.price from ProductEntity e where e.title=:title and e.price=:price")
    // Optional<ProductEntity> findByTitleAndPrice(String title, BigDecimal price);

    /** METHODS BEING CALLED FROM CONTROLLER - SORTING */

    List<ProductEntity> findByOrderByPrice();

    List<ProductEntity> findBy(Sort sort);


}
