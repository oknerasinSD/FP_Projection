package com.example.mvc1.repos;

import com.example.mvc1.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepo extends CrudRepository<Customer, Long> {

    List<Customer> findByName(String name);
}
