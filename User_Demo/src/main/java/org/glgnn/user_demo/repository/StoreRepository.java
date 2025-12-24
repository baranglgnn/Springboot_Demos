package org.glgnn.user_demo.repository;

import org.glgnn.user_demo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {
    boolean existsByName(String name);
}