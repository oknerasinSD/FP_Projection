package com.example.mvc1.repos;

import com.example.mvc1.domain.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TestRepo extends CrudRepository<Test, Long> {

    List<Test> findByName(String name);
}
