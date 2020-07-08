package com.deeaae.ANTS.controller;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.service.ActivityService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activities")
public class ActivityController {

  @Autowired
  private ActivityService activityService;

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<Activity> getActivityById(@PathVariable String id) {
    return ResponseEntity.ok(activityService.getActivityById(id));
  }

}
