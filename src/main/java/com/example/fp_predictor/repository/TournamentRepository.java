package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.Tournament;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface TournamentRepository extends CrudRepository<Tournament, Long>  {

    Tournament findById(long id);

    @Query(value = "UPDATE tournament SET fanteam_id = ?1 WHERE id = ?2", nativeQuery = true)
    @Transactional
    @Modifying(clearAutomatically = true)
    void setFanteamId(long fanteam_id, long idToUpdate);
}
