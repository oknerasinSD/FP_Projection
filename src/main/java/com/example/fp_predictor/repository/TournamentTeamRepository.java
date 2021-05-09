package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.TournamentTeam;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TournamentTeamRepository extends CrudRepository<TournamentTeam, Long> {

    @Query(value = "SELECT team FROM tournament_team WHERE tournament_id = ?1", nativeQuery = true)
    List<String> findByTournamentId(long id);
}
