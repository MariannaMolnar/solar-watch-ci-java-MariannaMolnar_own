package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder encoder;

  public UserService(UserRepository userRepository, PasswordEncoder encoder) {
    this.userRepository = userRepository;
    this.encoder = encoder;
  }

  public UserEntity findCurrentUser() {
    UserDetails contextUser = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    String username = contextUser.getUsername();
    return userRepository.findByUsername(username)
            .orElseThrow(()-> new IllegalArgumentException(format("could not find user %s in the database", username)));
  }

  public void addRoleForUser(String userName, Role role) {
    UserEntity user = userRepository.findByUsername(userName)
            .orElseThrow(() -> new IllegalArgumentException(format("could not find user %s in the database", userName)));
    Set<Role> oldRoles = user.getRoles();
    Set<Role> copiedRoles = new HashSet<>(oldRoles);
    copiedRoles.add(role);
    UserEntity updatedUser = new UserEntity(
            user.getId(),
            user.getUsername(),
            user.getPassword(),
            copiedRoles
    );
    userRepository.save(updatedUser);
//    userRepository.updateUser(new UserEntity(user.getUsername(), user.getPassword(), Set.copyOf(copiedRoles)));
  }


  public void createUser(UserRequest signUpRequest) {
    if (userRepository.findByUsername(signUpRequest.getUsername()).isPresent()) {
      throw new IllegalArgumentException(format("user %s already exists", signUpRequest.getUsername()));
    }

    UserEntity user = new UserEntity(
            signUpRequest.getUsername(),
            encoder.encode(signUpRequest.getPassword()),
            Set.of(Role.ROLE_USER)
    );
    userRepository.save(user);
  }
}
