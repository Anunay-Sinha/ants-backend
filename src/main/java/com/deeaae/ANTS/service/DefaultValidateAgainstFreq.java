package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.DataCaptureFrequency;
import org.springframework.stereotype.Service;

@Service
public class DefaultValidateAgainstFreq implements ValidateAgainstFrequency {

  @Override
  public DataCaptureFrequency getDataCaptureFrequency() {
    return null;
  }

  @Override
  public Response validate(Activity activity, ActivityService activityService) {
    Response response = new Response();
    response.isEntryValid = true;
    response.isThereActivityToBeRemoved=false;
    return response;
  }
}
