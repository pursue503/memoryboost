package com.memoryboost.domain.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface OrderRepository extends JpaRepository<Order,Long> , OrderRepositoryCustom {
}
