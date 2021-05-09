package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Long> {

    @Query(value = "SELECT * FROM player WHERE system_tournament_id = ?1", nativeQuery = true)
    List<Player> findByTournamentId(long id);
}
