package com.codecool.solarwatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Sql(scripts = "/test-data.sql")
public class SolarWatchIntegrationTest {
  @Autowired
  private MockMvc mockMvc;

  String token;

//  @MockBean
//  private
  @BeforeEach
  void initLogin() throws Exception {
    String userJson = """
            {
            "username": "testUser",
            "password": "123456"
            }
            """;
    String loginResponse = mockMvc.perform(post("/api/users/login")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(userJson))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
    ObjectMapper objectMapper = new ObjectMapper();
    token = objectMapper.readTree(loginResponse).get("jwt").asText();
  }

  @Test
  void shouldReturnSolarDataForCityInDatabase() throws Exception {

    mockMvc.perform(get("/api/sunrise-sunset?city=London&country=GB&date=2024-12-05")
                    .header("Authorization", "Bearer " + token))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.cityName").value("London"))
            .andExpect(jsonPath("$.date").value("2024-12-05"))
            .andExpect(jsonPath("$.sunrise").exists())
            .andExpect(jsonPath("$.sunset").exists());
  }
}
