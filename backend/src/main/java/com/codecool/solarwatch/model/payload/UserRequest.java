package com.codecool.solarwatch.model.payload;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data

public class UserRequest {
  private String username;
  private String password;

  public UserRequest(String username, String password) {
    this.username = username;
    this.password = password;
  }
}
