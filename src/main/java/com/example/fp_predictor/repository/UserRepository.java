package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для доступа к таблице User БД.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
