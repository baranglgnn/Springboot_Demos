package org.glgnn.user_demo.repository;

import org.glgnn.user_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatusTrue(Long id);
    List<User> findAllByStatusTrue();

    boolean existsByEmail(String email);

    boolean existsByTc(String tc);
}
