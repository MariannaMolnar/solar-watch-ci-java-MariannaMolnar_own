package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.entity.Role;
import com.codecool.solarwatch.model.entity.UserEntity;
import com.codecool.solarwatch.model.payload.JwtResponse;
import com.codecool.solarwatch.model.payload.UserRequest;
import com.codecool.solarwatch.repository.UserRepository;
import com.codecool.solarwatch.security.jwt.JwtUtils;
import com.codecool.solarwatch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
public class UserController {
  private final UserService userService;
  private final PasswordEncoder encoder;
  private final AuthenticationManager authenticationManager;
  private final JwtUtils jwtUtils;
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

  @Autowired
  public UserController(UserService userService, PasswordEncoder encoder, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
    this.userService = userService;
    this.encoder = encoder;
    this.authenticationManager = authenticationManager;
    this.jwtUtils = jwtUtils;
  }

  @PostMapping("/register")
  public ResponseEntity<Void> createUser(@RequestBody UserRequest signUpRequest) {
//    UserEntity user = new UserEntity(
//            signUpRequest.getUsername(),
//            encoder.encode(signUpRequest.getPassword()),
//            Set.of(Role.ROLE_USER)
//    );
    LOGGER.info("Creating user: {}", signUpRequest);
    System.out.println("Request received: " + signUpRequest);
    userService.createUser(signUpRequest);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("/test")
  public ResponseEntity<String> testController() {
    return ResponseEntity.ok("Controller is working");
  }

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@RequestBody UserRequest loginRequest) {
    Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    User userDetails = (User) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
            .toList();

    return ResponseEntity
            .ok(new JwtResponse(jwt, userDetails.getUsername(), roles));
  }

  @PostMapping("/{username}/roles")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<?> addRole(@PathVariable String username, @RequestBody Role role) {
    userService.addRoleForUser(username, role);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

}
