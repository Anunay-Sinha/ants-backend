package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Status;
import com.deeaae.ANTS.model.Task;
import com.deeaae.ANTS.model.User;
import com.deeaae.ANTS.repo.TaskRepo;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private UserService userService;

  @Autowired
  private TaskRepo taskRepo;

  public Task save(Task task) {
    return taskRepo.save(task);
  }

  public Task createTask(Task task) {
    if(task.getTaskId() == null || task.getTaskId().isEmpty()) {
      task.setTaskId(UUID.randomUUID().toString());
    }

    if(getTaskById(task.getTaskId())!=null) {
      throw new RuntimeException("task with id " + task.getTaskId() + " is already present");
    }
    task.setStatus(Status.ACTIVE);
    if(userService.getUserById(task.getUserId())==null) {
      throw new RuntimeException("user with user id " + task.getUserId() + " , is not available");
    }
    return taskRepo.save(task);
  }

  public Task getTaskById(String taskId) {
    return taskRepo.findById(taskId).orElse(null);
  }

  public List<Task> getTaskByUserId(String userId) {
    return taskRepo.getByUserId(userId, null).stream().collect(Collectors.toList());
  }

  public List<Task> getTaskByUser(User user) {
    return getTaskByUserId(user.getUserId());
  }
}
