package com.aureadigitallabs.aurea_api.repository;

import com.aureadigitallabs.aurea_api.model.Order;
import com.aureadigitallabs.aurea_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserOrderByDateDesc(User user);
}