package com.memoryboost.domain.entity.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> , OrderRepositoryCustom {

    List<Order> findByOrderStLessThan(int orderSt);

}
