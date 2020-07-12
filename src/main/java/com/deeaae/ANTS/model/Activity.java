package com.deeaae.ANTS.model;

import java.time.LocalDateTime;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
public class Activity {
  @Id
  private String id;
  @NonNull
  private String data;
  private LocalDateTime timestamp;
  @NonNull
  private String taskId;
  @Transient
  boolean isForced = false;
}
