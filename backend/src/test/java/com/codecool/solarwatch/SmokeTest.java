package com.codecool.solarwatch;

import com.codecool.solarwatch.controller.SunriseSunsetReportController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class SmokeTest {

  @Autowired
  private SunriseSunsetReportController sunriseSunsetReportController;

  @Test
  void contextLoads() throws Exception {
    assertThat(sunriseSunsetReportController).isNotNull();
  }
}
