package com.deeaae.ANTS.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
public class Task {
  @Id
  private String taskId;
  @NonNull
  private String userId;
  @NonNull
  private String subject;
  @NonNull
  private String description;
  @NonNull
  private Datatype datatype;
  private Status status;
  private DataCaptureFrequency dataCapturingFreqType;
  private LocalDateTime lastEntryTimestamp;
  private String lastEntry;
}
