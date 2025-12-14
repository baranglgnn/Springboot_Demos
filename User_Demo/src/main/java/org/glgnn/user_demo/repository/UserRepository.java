package org.glgnn.user_demo.repository;

import org.glgnn.user_demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}