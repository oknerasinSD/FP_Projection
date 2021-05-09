package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.PlayerForecast;
import org.springframework.data.repository.CrudRepository;

public interface PlayerForecastRepository extends CrudRepository<PlayerForecast, Long> {
}
