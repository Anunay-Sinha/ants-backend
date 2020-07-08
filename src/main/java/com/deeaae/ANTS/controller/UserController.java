package com.deeaae.ANTS.controller;

import com.deeaae.ANTS.model.User;
import com.deeaae.ANTS.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @Autowired
  UserService userService;

  @RequestMapping(value = "", method = RequestMethod.POST)
  public ResponseEntity<User> createUser(@RequestBody User user) {
    user = userService.addUser(user);
    return ResponseEntity.status(201).body(user);
  }

  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
  public ResponseEntity<User> getUserById(@PathVariable String id) {
    User user = userService.getUserById(id);
    return ResponseEntity.ok(user);
  }

  @RequestMapping(value = "", method = RequestMethod.GET)
  public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
    return ResponseEntity.ok(userService.getAllUsers(pageable));
  }

  @RequestMapping(value = "/username/{username}", method = RequestMethod.GET)
  public ResponseEntity<User> getUserByUserName(@PathVariable String username) {
    User user = userService.getUserByName(username);
    return ResponseEntity.ok(user);
  }


}
