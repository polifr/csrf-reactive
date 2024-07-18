package it.poli.csrf;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import it.poli.csrf.model.health.HealthModel;
import it.poli.csrf.model.loggers.LoggerLevelsDescriptorModel;
import it.poli.csrf.model.loggers.LoggersDescriptorModel;
import lombok.Getter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers;
import org.springframework.test.web.reactive.server.WebTestClient;

public abstract class CsrfReactiveApplicationCommonTests {

  @Autowired(required = false)
  private ApplicationContext applicationContext;

  @Getter private WebTestClient webClient;

  @BeforeEach
  public void init() {
    webClient =
        WebTestClient.bindToApplicationContext(applicationContext).configureClient().build();
  }

  @Test
  void contextLoads() {
    assertNotNull(applicationContext, "null applicationContext");
  }

  @Test
  final void testHealthGetOk() {
    webClient
        .get()
        .uri("/actuator/health")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(HealthModel.class);
  }

  @Test
  final void testLogsGetOk() {
    webClient
        .get()
        .uri("/actuator/loggers")
        .exchange()
        .expectStatus()
        .isOk()
        .expectBody(LoggersDescriptorModel.class);
  }

  @Test
  final void testLogsGetLoggers() {
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = this.getLoggerLevelsDescriptor("it.poli");
    assertNotNull(loggerLevelsDescriptor.getConfiguredLevel(), "null configuredLevel");
  }

  protected LoggerLevelsDescriptorModel getLoggerLevelsDescriptor(String logger) {
    LoggersDescriptorModel loggersDescriptor =
        webClient
            .get()
            .uri("/actuator/loggers")
            .exchange()
            .expectStatus()
            .isOk()
            .expectBody(LoggersDescriptorModel.class)
            .returnResult()
            .getResponseBody();
    assertNotNull(loggersDescriptor, "null loggersDescriptor");
    assertNotNull(loggersDescriptor.getLoggers(), "null loggers");
    LoggerLevelsDescriptorModel loggerLevelsDescriptor = loggersDescriptor.getLoggers().get(logger);
    assertNotNull(loggerLevelsDescriptor, "null loggerLevelsDescriptor");

    return loggerLevelsDescriptor;
  }

  protected void setLoggerLevel(String logger, String level) {
    webClient
        .post()
        .uri("/actuator/loggers/{logger}", logger)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(LoggerLevelsDescriptorModel.builder().configuredLevel(level).build())
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }

  protected void setLoggerLevelWithCsrf(String logger, String level) {
    webClient
        .mutateWith(SecurityMockServerConfigurers.csrf())
        .post()
        .uri("/actuator/loggers/{logger}", logger)
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(LoggerLevelsDescriptorModel.builder().configuredLevel(level).build())
        .exchange()
        .expectStatus()
        .is2xxSuccessful();
  }
}
