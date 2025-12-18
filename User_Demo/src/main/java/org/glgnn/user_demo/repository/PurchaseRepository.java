package org.glgnn.user_demo.repository;

import org.glgnn.user_demo.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    boolean existsByProductIdAndActiveTrue(Long productId);

    Optional<Purchase> findByIdAndActiveTrue(Long id);

    List<Purchase> findAllByBuyerId(Long userId);
}
