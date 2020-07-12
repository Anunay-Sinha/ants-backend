package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.Activity;
import com.deeaae.ANTS.model.DataCaptureFrequency;
import java.util.zip.DataFormatException;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
public interface ValidateAgainstFrequency {
  @Data
  class Response {
    String activityIdToBeRemoved;
    boolean isThereActivityToBeRemoved;
    boolean isEntryValid;
  }
  public DataCaptureFrequency getDataCaptureFrequency();
  public Response validate (Activity activity, ActivityService activityService);


}

