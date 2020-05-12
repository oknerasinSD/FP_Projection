package com.example.mvc1.repository;

import com.example.mvc1.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findByName(String name);
}
