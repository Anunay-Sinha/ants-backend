package com.deeaae.ANTS.service;

import com.deeaae.ANTS.model.User;
import com.deeaae.ANTS.repo.UserRepo;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepo userRepo;

  public User addUser(User user) {
    if(user.getUserId() == null) {
      user.setUserId(UUID.randomUUID().toString());
    }
    if(user.getUsername() == null || user.getUsername().isEmpty()) {
      throw new RuntimeException("invalid or empty username");
    }

    if(getUserById(user.getUserId())==null && getUserByName(user.getUsername())==null) {
      return userRepo.save(user);

    } else {
      throw new RuntimeException("username or id already exist with us.");
    }

  }

  public Page<User> getAllUsers(Pageable pageable) {
    return userRepo.findAll(pageable);
  }

  public User getUserByName(String name) {
    return userRepo.findByUsername(name);
  }

  public User getUserById(String id) {
    return userRepo.findById(id).orElse(null);
  }


}
