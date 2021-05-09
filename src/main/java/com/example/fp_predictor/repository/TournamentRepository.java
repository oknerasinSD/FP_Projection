package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentRepository extends CrudRepository<Tournament, Long>  {

    Tournament findById(long id);
}
