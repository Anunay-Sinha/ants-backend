package com.deeaae.ANTS.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class User {
  @Id
  String userId;
  String username;
}
