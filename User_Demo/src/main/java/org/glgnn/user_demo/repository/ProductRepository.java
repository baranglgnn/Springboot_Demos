package org.glgnn.user_demo.repository;

import org.glgnn.user_demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByStatusTrue();

    List<Product> findAllByOwnerUserIdAndStatusTrue(Long userId);

    Optional<Product> findByIdAndStatusTrue(Long id);

    boolean existsBySerialNumber(String serialNumber);
}
