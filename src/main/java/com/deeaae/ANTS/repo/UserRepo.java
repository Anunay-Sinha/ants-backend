package com.deeaae.ANTS.repo;

import com.deeaae.ANTS.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, String> {
  User findByUsername(String username);

}
