package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.DataCaptureFrequency;
import com.deeaae.ANTS.repo.ActivityRepo;
import com.deeaae.ANTS.service.ValidateAgainstFrequency.Response;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValidateAndUpdateActivityAgainstFrequency {
  @Autowired
  private ValidateAgainstDailyFrequency validateAgainstDailyFrequency;

  @Autowired
  private DefaultValidateAgainstFreq defaultValidateAgainstFreq;

  public boolean verifyAndUpdateEntry(Activity activity, ActivityService activityService,
      DataCaptureFrequency dataCaptureFrequency) {
    ValidateAgainstFrequency validateAgainstFrequency = resolve(dataCaptureFrequency);
    activity = updateTimestamp(activity);
    ValidateAgainstFrequency.Response response = validateAgainstFrequency.validate(activity, activityService);
    handleResponse(response, activityService, activity);
    return true;

  }

  public void handleResponse(Response response, ActivityService activityService, Activity activity) {
    if(response.isEntryValid()) {
      if(response.isThereActivityToBeRemoved) {
        activityService.removeActivity(response.getActivityIdToBeRemoved());
      }
      activityService.save(activity);

    } else {
      throw new RuntimeException("Activity is not valid against the expected frequency");
    }

  }

  public Activity updateTimestamp(Activity activity) {
    if(activity.isForced() && activity.getTimestamp() == null) {
      throw new RuntimeException("timestamp is required for forced entries");
    }
    if(activity.getTimestamp() == null ) {
      activity.setTimestamp(LocalDateTime.now());
    }
    return activity;
  }

  public ValidateAgainstFrequency resolve(DataCaptureFrequency dataCaptureFrequency) {
    switch (dataCaptureFrequency) {
      case DAILY:
        return validateAgainstDailyFrequency;
      default:
        return defaultValidateAgainstFreq;
    }
  }

}
