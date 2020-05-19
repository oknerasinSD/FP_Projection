package com.example.fp_predictor.repository;

import com.example.fp_predictor.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByName(String name);
}
