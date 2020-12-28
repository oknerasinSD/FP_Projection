package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.Customer;
import com.example.fp_predictor.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Репозиторий для доступа к таблице Player БД.
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {

    List<Customer> findByName(String name);
}
