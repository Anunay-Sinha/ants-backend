package com.deeaae.ANTS.repo;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.Task;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepo extends MongoRepository<Activity, String> {
  Page<Activity> findByTaskIdOrderByTimestamp(String taskId, Pageable pageable);
  List<Activity> findAllByTaskIdAndTimestampBetween(String taskId, LocalDateTime start, LocalDateTime end);

}
