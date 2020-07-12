package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.DataCaptureFrequency;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ValidateAgainstDailyFrequency implements ValidateAgainstFrequency {

  private static DataCaptureFrequency dataCaptureFrequency = DataCaptureFrequency.DAILY;

  @Override
  public DataCaptureFrequency getDataCaptureFrequency() {
    return dataCaptureFrequency;
  }

  @Override
  public Response validate(Activity activity, ActivityService activityService) {
    // do we have an activity for the requested date.

    Response response = new Response();
    List<Activity> activities = activityService.getActivityBetween(activity.getTimestamp().toLocalDate().atStartOfDay(),
        activity.getTimestamp().toLocalDate().plusDays(1).atStartOfDay(),activity.getTaskId());
    if(activities == null ||activities.size()==0) {
      response.isEntryValid = true;
      response.isThereActivityToBeRemoved = false;
      return response;
    } else {
      if (!activity.isForced()) {
        response.isEntryValid = false;
      } else {
        if (activities.size() == 1) {
          response.isEntryValid = true;
          response.isThereActivityToBeRemoved = true;
          response.activityIdToBeRemoved = activities.get(0).getId();

        } else {
          response.isEntryValid = false;
          log.error("found more than one entry in time range. It was not expected");

        }

      }
    }


    return response;


  }
}
