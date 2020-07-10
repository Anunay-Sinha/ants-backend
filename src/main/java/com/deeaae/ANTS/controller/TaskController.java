package com.deeaae.ANTS.controller;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.Task;
import com.deeaae.ANTS.model.User;
import com.deeaae.ANTS.service.ActivityService;
import com.deeaae.ANTS.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
  @Autowired
  private TaskService taskService;

  @Autowired
  private ActivityService activityService;

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<Task> createTask(@RequestBody Task task) {
    task = taskService.createTask(task);
    return ResponseEntity.status(201).body(task);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Task> getUserById(@PathVariable String id) {
    Task task = taskService.getTaskById(id);
    return ResponseEntity.ok(task);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
  public ResponseEntity<Task> deleteUserById(@PathVariable String id) {
    Task task = taskService.removeTask(id);
    return ResponseEntity.ok(task);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<List<Task>> getUserByUserName(@RequestParam String userId) {
    List<Task> tasks = taskService.getTaskByUserId(userId);
    return ResponseEntity.ok(tasks);
  }

  @RequestMapping(value = "/{id}/activities", method = RequestMethod.GET)
  public ResponseEntity<Page<Activity>> getActivitiesForTask(@PathVariable String id, Pageable pageable) {
    return ResponseEntity.ok(activityService.getActivityByTaskId(id,pageable));
  }

  @RequestMapping(value = "/{id}/activities", method = RequestMethod.POST)
  public ResponseEntity<Activity> addActivitiesForTask(@PathVariable String id, @RequestBody Activity activity) {
    return ResponseEntity.ok(activityService.addActivity(activity));
  }

}
