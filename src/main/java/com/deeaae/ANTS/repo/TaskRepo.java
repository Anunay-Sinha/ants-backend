package com.deeaae.ANTS.repo;


import com.deeaae.ANTS.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepo extends MongoRepository<Task, String> {
  Page<Task> getByUserId(String userId, Pageable pageable);
}
