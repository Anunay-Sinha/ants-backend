package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.DataCaptureFrequency;
import com.deeaae.ANTS.model.Datatype;
import com.deeaae.ANTS.model.Task;
import com.deeaae.ANTS.repo.ActivityRepo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

  @Autowired
  private ValidateAndUpdateActivityAgainstFrequency validateAndUpdateActivityAgainstFrequency;

  @Autowired
  private ActivityRepo activityRepo;

  @Autowired
  private TaskService taskService;

  public Activity removeActivity(String id) {
    Activity activity = getActivityById(id);
    if(activity == null) {
      throw new RuntimeException("Activity with id " + id + ", not found. Delete request failed");
    }
    activityRepo.delete(activity);
    return activity;
  }

  public Activity save(Activity activity) {
    return activityRepo.save(activity);
  }

  public Activity addActivity(Activity activity) {
    Task task = taskService.getTaskById(activity.getTaskId());
    if (task == null) {
      throw new RuntimeException(("task id not found"));
    }
    if (activity.getId() == null || activity.getId().isEmpty()) {
      activity.setId(UUID.randomUUID().toString());
    }
    if (getActivityById(activity.getId()) != null) {
      throw new RuntimeException(("activity id already available"));
    }

    if(checkForData(activity, task)) {
      boolean wasEntryPersisted = validateAndUpdateActivityAgainstFrequency.verifyAndUpdateEntry(activity,this, task.getDataCapturingFreqType());
      if(wasEntryPersisted) {
        task.setLastEntry(activity.getData());
        task.setLastEntryTimestamp(activity.getTimestamp());
        taskService.save(task);
      }

    } else {
      throw new RuntimeException("data entry not allowed, invalid datatype");
    }
    return activity;
  }

  public List<Activity> getActivityBetween(LocalDateTime start, LocalDateTime end, String taskId) {
    return activityRepo.findAllByTaskIdAndTimestampBetween(taskId, start, end);
  }

  public Page<Activity> getActivityByTaskId(String taskId, Pageable pageable) {
    if(pageable == null) {
      pageable = PageRequest.of(0,50);
    }
    pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("timestamp").descending());
    return activityRepo.findByTaskIdOrderByTimestamp(taskId, pageable);
  }

  public Activity getActivityById(String activityId) {
    return activityRepo.findById(activityId).orElse(null);
  }

  public boolean checkForData(Activity activity, Task task) {
    switch (task.getDatatype()) {
      case STRING:
        return true;
      case INTEGER:
        try {
          Double data = Double.parseDouble(activity.getData());
          activity.setData(data.toString());
          return true;
        } catch (Exception ex) {
          return false;
        }
      case BOOLEAN:
        try {
          Boolean data = Boolean.parseBoolean(activity.getData());
          activity.setData(data.toString());
          return true;
        }catch (Exception ex) {
          return false;
        }
    }

    return true;

  }



  public boolean isEntryLegitAgainstFreq(LocalDateTime activityTime, LocalDateTime lastRecordedTime,
      DataCaptureFrequency dataCaptureFrequency, boolean isEnabled) {
    if(!isEnabled) {
      return true;
    }
    if (lastRecordedTime == null) {
      return true;
    }
    LocalDate activityDate = activityTime.toLocalDate();
    LocalDate lastRecordedDate = lastRecordedTime.toLocalDate();
    if (dataCaptureFrequency.equals(DataCaptureFrequency.NA)) {
      return true;
    } else if (dataCaptureFrequency.equals(DataCaptureFrequency.DAILY)) {
      return activityDate.equals(lastRecordedDate)? false:true;
    } else {
      return true;
    }

  }


}
